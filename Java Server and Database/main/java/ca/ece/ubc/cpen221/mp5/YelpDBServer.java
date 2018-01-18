package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import ca.ece.ubc.cpen221.mp5.expression.Expression;
import ca.ece.ubc.cpen221.mp5.grammar.Parser;
import ca.ece.ubc.cpen221.mp5.grammar.QueFormatException;
import ca.ece.ubc.cpen221.mp5.grammar.ResFormatException;
import ca.ece.ubc.cpen221.mp5.grammar.RevFormatException;
import ca.ece.ubc.cpen221.mp5.grammar.UsFormatException;
/*
 * Is a server that processes multiple basic requests by interacting with a basic YelpDB database
 */
public class YelpDBServer {
	/** Default port number where the server listens for connections. */
	public final static int SERVER_PORT = 4949;

	private ServerSocket serverSocket;
	
	private static int userId = 1; //Add USER to front
	private static int restId = 1; //Add REST to front
	private static int revId = 1;  //Add REV to front
	private static YelpDB database;

	private static final List<String> restRequirements = 
		    Arrays.asList("open", "url", "longitude", "neighborhoods", "name", 
		    		"categories", "state",
		    		"city", "full_address", "photo_url", "schools", "latitude", "price"); 
	
	private static final List<String> restParts = 
		    Arrays.asList("open", "url", "longitude", "neighborhoods", "business_id", 
		    		"name", "categories", "state", "type", "stars",
		    		"city", "full_address", "review_count", "photo_url", "schools", "latitude", "price");
	
	private static final List<String> revRequirements = 
			Arrays.asList("business_id", "text", "stars", "user_id", "date");
	
	private static final List<String> revParts = 
			Arrays.asList("type", "business_id", "votes", "review_id", "text", "stars",  "user_id", "date");
	
	// Rep invariant: 
	// - serverSocket != null 
	// - no two requests processed should ever have the same ID. 
	// - Requests of different types must have their corresponding ID types assigned
	// - If the request is for ADDUSER, JSON object must have name
	// - If the request is for ADDRESTAURANT, JSON object must contain all categories
	//	 in restParts above
	// - If request if for ADDREVIEW, JSON object must have contain all categories
	//	 in revParts above
	// - database should not be null
	//
	// Abstraction Function:
	// 	Represents a server that is connected to a YelpDB databse that can process multiple
	//	basic requests such as GETRESTAURANT, ADDUSER, ADDRESTAURANT, ADDREVIEW, and QUERY
	// 	by interacting with that databse.
	
	// Thread safety argument:
	// - SERVER_PORT is thread safe because it is a static final primitive, meaning it is
	//	 immutable
	// - serverSocket is only ever interacted with once, and no method returns it for
	//	 rep exposure
	// - socket objects are final in serve, and all methods in handle() that interact with it
	//   create a new bufferedReader and Writer first, while all methods used in it are
	//   synchronized to database, buffers are also closed at the end. Then finally socket is
	//	 closed as well.
	// - all objects used in handle() are new for each thread and should all be thread local
	// - getUserID, getRevID, and getRestID are all syncrhonized to server, so no 
	//	 two requests should have the same ID
	// - All the lists that display category requirements are threadsafe because they are
	//	 only read within this class (no mutation), and private so no rep exposure.
	// - All methods that interact with the database in handle() are synchronized to 
	//   database, so we should have no dataraces or deadlocks (because they have the same lock)
	//		- For more details see YelpDB thread-safety arguments 
	

	/**
	 * Make a YelpDBServer that listens for connections on port.
	 * 
	 * @param port
	 *            port number, requires 0 <= port <= 65535
	 */
	public YelpDBServer(int port) throws IOException {
		serverSocket = new ServerSocket(port);
	}

	/**
	 * Run the server, listening for connections and handling them.
	 * 
	 * @throws IOException
	 *             if the main server socket is broken
	 */
	public void serve() throws IOException {
		
		while (true) {
			// block until a client connects
			final Socket socket = serverSocket.accept();
			// create a new thread to handle that client
			
			Thread handler = new Thread(new Runnable() {
				public void run() {
					try {
						
						try {
							handle(socket);
						} finally {
							socket.close();
						}
					} catch (IOException ioe) {
						// this exception wouldn't terminate serve(),
						// since we're now on a different thread, but
						// we still need to handle it
						ioe.printStackTrace();
					}
				}
			});
			// start the thread
			handler.start();
		}
	}

	/**
	 * Handle one client connection. Returns when client disconnects.
	 * 
	 * @param socket
	 *            socket where client is connected
	 * @throws IOException
	 *             if connection encounters an error
	 */
	private void handle(Socket socket) throws IOException {
		System.err.println("\nclient connected");

		// get the socket's input stream, and wrap converters around it
		// that convert it from a byte stream to a character stream,
		// and that buffer it so that we can read a line at a time
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		// similarly, wrap character=>bytestream converter around the
		// socket output stream, and wrap a PrintWriter around that so
		// that we have more convenient ways to write Java primitive
		// types to it.
		PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		
		try {
			//each request is a single line
			for (String line = in.readLine(); line != null; line = in.readLine()) {
				
				System.err.println("\nrequest: " + line);
				try {
					Parser parser;
					try {
						//Creation of a custom parser to find the arguments and method 
						//asked for
						parser = new Parser(line);
						
						//Gets the command that client has sent
						String command = new String(parser.getCommand());
						
						//Find a restaurant by their business_id
						if (command.equals("GETRESTAURANT")) {
							//Get the argument from the parser
							String businessId = new String(parser.getArugments());
							Data restaurant = database.getEntry(businessId);
							
							//If restaurant does not exist or is not a business, then
							//no match
							if(restaurant.equals(null) || !restaurant.getInfo("type").equals("business")) {
								System.err.println("ERR: NO_SUCH_BUSINESS");
								out.println("ERR: NO_SUCH_BUSINESS");
							}
							//If it does exist, we send it to the printWriter
							else {
								System.out.println(restaurant.toJson());
								out.println(restaurant.toJson().replaceAll("\n", " "));
							}
						}
						//If command wants to adduser, we must ensure that it has the field
						//name inside the JSON object, all other categories can be ignored
						else if (command.equals("ADDUSER")) {
							
							JsonReader jsonReader = Json.createReader(new StringReader(parser.getJson()));
							JsonObject object = jsonReader.readObject();
							
							if(!object.keySet().contains("name")) {
								jsonReader.close();
								throw new UsFormatException();
							}
							else {
								
								ReviewerData userData = new ReviewerData();
								int id = getUserID();
								
								//Assign a new user_id, and all other relevant fields
								userData.storeInfo("url", "http://www.yelp.com/user_details?userid=USER" + id);
								userData.storeInfo("votes", new LinkedHashMap<String, Object>());
								
								BigDecimal start = new BigDecimal(0);
								userData.storeInfo("review_count", start);
								userData.storeInfo("type", "user");
								userData.storeInfo("user_id", "USER" + Integer.toString(id));
								userData.storeInfo("name", object.get("name"));
								
								BigDecimal starting = new BigDecimal(0);
								
								userData.storeInfo("average_stars", starting);
								userData.storeID("USER" + Integer.toString(id));
								
								//Add the reviewer to the database, close the jsonReader.
								database.addReviewer(userData);
								jsonReader.close();
								System.out.println(userData.toJson());
								out.println(userData.toJson().replaceAll("\n", " "));
							}
						}
						//Add a new restaurant to the database
						else if(command.equals("ADDRESTAURANT")) {
							
							JsonReader jsonReader = Json.createReader(new StringReader(parser.getJson()));
							JsonObject object = jsonReader.readObject();
							
							//JSON object given must contain this information (see list above) for it to be added
							if(!object.keySet().containsAll(restRequirements)) {
								jsonReader.close();
								throw new ResFormatException();
							}
							else {
								RevieweeData restaurantData = new RevieweeData();
								
								//For every part, we either take it from the input user has given us
								//or create a new one (such as rest_id, votes, stars, etc.).
								for(String category: restParts) {
									
									if(category.equals("open")) {
										
										//If object is not of type boolean or does not exist, 
										//then JSON object is not valid
										try {
											boolean trial = object.getBoolean(category);
											restaurantData.storeInfo(category, trial);
											
										}catch (ClassCastException cce) {
											throw new ResFormatException();
										}
										catch(NullPointerException npe) {
											throw new ResFormatException();
										}
									}
									else if(Arrays.asList("url", "name", "state", "type", "city", 
											"full_address", "photo_url", "business_id").contains(category)) {
										
										//Assign a new id
										if(category.equals("business_id")) {
											String id = new String("REST" + getRestID());
											restaurantData.storeInfo(category, id);
											restaurantData.storeID(id);
										}
										else if(category.equals("type")) {
											restaurantData.storeInfo("type", "business");
										}
										else {
											try {
												
												String trial = object.getString(category);
												restaurantData.storeInfo(category, trial);
												
											}catch (ClassCastException cce) {
												throw new ResFormatException();
											}
											catch(NullPointerException npe) {
												throw new ResFormatException();
											}
										}
									}
									//These values should be BigDecimal values rather than strings
									else if(Arrays.asList("longitude","latitude","review_count",
											"stars","price").contains(category)) {
										
										if(category.equals("longitude")) {
											
											try {
											BigDecimal longVal = object.getJsonNumber("longitude").bigDecimalValue();
											//Longitude must be between these limits to be valid
											if(longVal.doubleValue() <= 180.0 && longVal.doubleValue() >= -180.0) {
												restaurantData.storeInfo(category, longVal);
											}
											else
												throw new ResFormatException();
											
											}catch(ClassCastException cce) {
												throw new ResFormatException();
											}
											catch(NullPointerException npe) {
												throw new ResFormatException();
											}
										}
										else if(category.equals("latitude")) {
											try {
												BigDecimal longVal = object.getJsonNumber("latitude").bigDecimalValue();
												//Latitude must be between these limits to be valid
												if(longVal.doubleValue() <= 90.0 && longVal.doubleValue() >= -90.0) {
													restaurantData.storeInfo(category, longVal);
												}
												else
													throw new ResFormatException();
												
												}catch(ClassCastException cce) {
													throw new ResFormatException();
												}catch(NullPointerException npe) {
													throw new ResFormatException();
												}
										}
										else if(category.equals("price")) {
											try {
												Integer price = object.getInt("price");
												//Price must be between these limits to be valid
												if(price > 0 && price <= 4) {
													restaurantData.storeInfo(category, price);
												}
												else
													throw new ResFormatException();
												
												}catch(ClassCastException cce) {
													throw new ResFormatException();
												}catch(NullPointerException npe) {
													throw new ResFormatException();
												}
										}
										else {
											restaurantData.storeInfo(category, new BigDecimal(0));
										}
									}
									//These categories must be reprsented as arraylists 
									else if(Arrays.asList("neighborhoods", "schools", "categories").contains(category)) {
										
										try {
										ArrayList<Object> list = new ArrayList<Object>(Arrays.asList(object.get(category).asJsonArray().toArray()));
										restaurantData.storeInfo(category, list);
										
										} catch (ClassCastException cce) {
											throw new ResFormatException();
										}
										catch(NullPointerException npe) {
											throw new ResFormatException();
										}
									}
								}
								//Add new business to database, print it on screen for user to see
								database.addReviewee(restaurantData);
								System.out.println(restaurantData.toJson());
								jsonReader.close();
								out.println(restaurantData.toJson().replaceAll("\n", " "));
							}
							
						}
						//Add new review to database
						else if(command.equals("ADDREVIEW")) {
							
							JsonReader jsonReader = Json.createReader(new StringReader(parser.getJson()));
							JsonObject object = jsonReader.readObject();
							
							//If it does not have the relevant info, throw an exception
							if(!object.keySet().containsAll(revRequirements)) {
								jsonReader.close();
								throw new RevFormatException();
							}
							else {
								ReviewData review = new ReviewData();
								
								for(String category: revParts) {
									
									//This must be treated as string objects
									if(Arrays.asList("type", "business_id", "text", 
											"user_id", "date", "review_id").contains(category)) {
																				
										try {
											String value = object.getString(category);
											
											//business_id must exist in database
											if(category.equals("business_id")) {
												boolean exists = database.restaurantExists(value);
												
												if(!exists) {
													System.err.println("ERR: NO_SUCH_RESTAURANT");
													out.println("ERR: NO_SUCH_RESTAURANT");
												}
												else
													review.storeInfo(category, value);
											}
											//user_id must exists in database
											else if(category.equals("user_id")) {
												boolean exists = database.userExists(value);
												
												if(!exists) {
													System.err.println("ERR: NO_SUCH_USER");
													out.println("ERR: NO_SUCH_USER");
												}
												else
													review.storeInfo(category, value);
											}
											else if(category.equals("type")) {
												review.storeInfo(category, "review");
											}
											//Assign a new unique id
											else if(category.equals("review_id")) {
												String id = new String("REV" + getRevID());
												review.storeInfo(category, id);
												review.storeID(id);
											}
											else
												review.storeInfo(category, value);
											
											} catch (ClassCastException cce) {
												throw new RevFormatException();
											}
											catch(NullPointerException npe) {
												throw new RevFormatException();
											}
									}
									//Votes must all start at 0
									else if(category.equals("votes")) {
										LinkedHashMap<String,Object> votes = new LinkedHashMap<String,Object>();
										votes.put("cool", 0);
										votes.put("useful",0);
										votes.put("funny", 0);
										review.storeInfo(category, votes);
									}
									//Stars must be possible between 1 and 5
									else if(category.equals("stars")){
										BigDecimal starting = new BigDecimal(object.getInt("stars"));
										
										if(starting.doubleValue() >=1 && starting.doubleValue() <=5)
											review.storeInfo(category, starting);
										else
											throw new RevFormatException();
									}
								}
								
								database.addReview(review);
								System.out.println(review.toJson());
								
								RevieweeData restaurantChanged = (RevieweeData) database.getEntry(review.getReviewee());
								
								//System.err.println("\n" + restaurantChanged.toJson() + "\n");
								//Since review has no been added, this means restaurant data has to updated
								//Review count and stars will be updated
								BigDecimal value = new BigDecimal(restaurantChanged.getInfo("review_count").toString());
								value = value.add(new BigDecimal(1));
								restaurantChanged.storeInfo("review_count", value);
								
								BigDecimal starValue = new BigDecimal(review.getInfo("stars").toString());
								starValue = starValue.subtract(new BigDecimal(restaurantChanged.getInfo("stars").toString()));
								starValue = starValue.divide(value, 2, RoundingMode.HALF_UP);
								restaurantChanged.storeInfo("stars", starValue.add(new BigDecimal(restaurantChanged.getInfo("stars").toString())));
								
								//System.err.println("\n" + restaurantChanged.toJson() + "\n");
								database.addReviewee(restaurantChanged);
								
								ReviewerData userChanged = (ReviewerData) database.getEntry(review.getReviewer());
								
								//System.err.println(userChanged.toJson());
								//Since review has been added for a user, their review count and average stars
								//has to be updated
								BigDecimal reviewCount = new BigDecimal(userChanged.getInfo("review_count").toString());
								reviewCount = reviewCount.add(new BigDecimal(1));
								userChanged.storeInfo("review_count", reviewCount);
								
								BigDecimal averageStars = new BigDecimal(review.getInfo("stars").toString());
								averageStars = averageStars.subtract(new BigDecimal(userChanged.getInfo("average_stars").toString()));
								averageStars = averageStars.divide(reviewCount, 14, RoundingMode.HALF_UP);
								averageStars = averageStars.add(new BigDecimal(userChanged.getInfo("average_stars").toString()));
								userChanged.storeInfo("average_stars", averageStars);
								
								//System.err.println(userChanged.toJson());
								
								database.addReviewer(userChanged);
								jsonReader.close();
								out.println(review.toJson().replaceAll("\n", " "));
							}
						}
						//If query, find the relevant information from database
						//using a grammer parser and expression evaluator
						else if(command.equals("QUERY")) {
							Expression query = parser.getExpression();
							Set<RevieweeData> lists = query.eval(database);
							
							if(lists.isEmpty()) {
								System.err.println("ERR: NO_MATCH");
								out.println("ERR: NO_MATCH");
							}
							//For every restaurant that fulfills categories, turn it into
							//relevant json 
							else {
								String answer = "";
								for(RevieweeData restaurant : lists) {
									answer += restaurant.toJson() + ", ";
								}
								answer = answer.substring(0,answer.length()-2);
								System.out.println(answer.replaceAll("\n", " "));
								out.println(answer.replaceAll("\n", " "));
							}
						}
						else
							throw new IllegalArgumentException();
						
					} 
					catch (QueFormatException err0) {
						System.err.println("ERR: INVALID_QUERY_STRING");
						out.println("ERR: INVALID_QUERY_STRING");
					} 
					catch (ResFormatException err1) {
						System.err.println("ERR: INVALID_RESTAURANT_STRING");
						out.println("ERR: INVALID_RESTAURANT_STRING");
					} 
					catch (RevFormatException err2) {
						System.err.println("ERR: INVALID_REVIEW_STRING");
						out.println("ERR: INVALID_REVIEW_STRING");
					} 
					catch (UsFormatException err3) {
						System.err.println("ERR: INVALID_USER_STRING");
						out.println("ERR: INVALID_USER_STRING");
					}
				} catch (IllegalArgumentException e) {
					// complain about ill-formatted request
					
					System.err.println("ERR: ILLEGAL_REQUEST");
					out.print("ERR: ILLEGAL_REQUEST\n");
				}
				// important! our PrintWriter is auto-flushing, but if it were
				// not:
				// out.flush();
			}
		} finally {
			out.close();
			in.close();
		}
	}
	
	/**
	 * Gets a new unique USERID
	 * @return a user_id
	 */
	private synchronized int getUserID() {
		
		int newID = YelpDBServer.userId;
		userId++;
		
		return newID;
	}
	
	/**
	 * Gets a new unique restID
	 * @return a business_id
	 */
	private synchronized int getRestID() {
		
		int newID = YelpDBServer.restId;
		restId++;
		
		return newID;
	}
	
	/**
	 * Gets a new unique revID
	 * @return a review_id
	 */
	private synchronized int getRevID() {
		
		int newID = YelpDBServer.revId;
		revId++;
		
		return newID;
	}
	
	/**
	 * Start a YelpDBServer running on the input port after initializing database.
	 */
	public static void main(String[] args) {
		
		try {
			database = new YelpDB("data/restaurants.json", "data/reviews.json", "data/users.json");
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		try {
			
			YelpDBServer server = new YelpDBServer(Integer.valueOf(args[0]));
			server.serve();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
