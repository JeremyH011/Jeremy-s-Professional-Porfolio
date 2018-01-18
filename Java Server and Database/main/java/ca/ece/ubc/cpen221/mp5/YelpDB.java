package ca.ece.ubc.cpen221.mp5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.function.ToDoubleBiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.json.Json;

import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class YelpDB implements Database, MP5Db<Data> {
	private LinkedHashMap<String, ReviewData> reviews;
	private LinkedHashMap<String, RevieweeData> reviewees;
	private LinkedHashMap<String, ReviewerData> reviewers;
	
	// Rep Invariant: 
		// - Each reviewee must only have one review from a specific
		//   reviewer, every review must be associated with a reviewee and reviewer.
		// - Reviews need to have the necessary categories in them to be valid,
		//   this includes business_id, user_id, stars, text, type, votes, date
		//		- date must be possible, as in no dates in the future or before the 
		//		  restaurant's creation
		//      - Review ID must be a unique ID
		//		- stars must be between 1 and 5
		//		- type must be review
		// - Reviewees must have the necessary categories in them to be valid, including
		//   business_id, type, open, price, categories, schools, url, latitude, longitude, full_address, etc.
		//  	- Latitude must be between -90 to 90
		//		- Longitude must be between -180 to 180
		//		- price must be between 1 to 4
		//		- schools and full_address must exist 
		//		- business ID should be unique even if name is not
		//		- type must be business
		//	- Reviewers must have necessary categories to be valid, including
		//	  user_id, url, votes, average_stars, review_count, type, etc.
		//		- average stars must be btween 1 and 5
		//		- user_id must be unique to user, alongside url
		//		- review_count must reflect the number of reviews
		// 
		// Abstraction Function: Represents the reviews that is made by the different
		// reviewers for the different reviewees.
		//
		// Thread Safety Arguments:
		//	- reviews, reviewees, and reviewers are private objects that can only be 
		//	  mutated through synchronized methods (synchronized on this YelpDB object
		//	  itself), so only one thread will read or write at once. 
		//	- All other pieces inside the methods are local, so they are thread-safe
		//	- All methods return primitives or defensively copied objects

	/**
	 * Creates a Yelp database that contains the information passed from the inputs
	 * of the function
	 * 
	 * @param restaurants
	 *            the filepath to the json file containing the restaurant
	 *            information
	 * @param reviews
	 *            the filepath to the json file containing the review information
	 * @param userList
	 *            the filepath to the json file containing the user information
	 * @throws IOException
	 *             when there is an error with reading the files
	 */
	public YelpDB(String restaurants, String reviews, String userList) throws IOException {
		this.reviews = new LinkedHashMap<String, ReviewData>();
		this.reviewees = new LinkedHashMap<String, RevieweeData>();
		this.reviewers = new LinkedHashMap<String, ReviewerData>();

		// Parses the json file of restaurants and creates relevant reviewee data
		// for use
		String rest = new String(restaurants);
		try {
			InputStream res = new FileInputStream(rest);
			JsonParser jsonParser = Json.createParser(res);
			
			String keyName = null;
			ArrayList<Object> toAdd = null;
			RevieweeData info = new RevieweeData();

			// All initialized in the cases that require nested json objects
			// or nested arrays
			int innerJson = 0;
			int innerArray = 0;

			Stack<LinkedHashMap<String, Object>> inner = new Stack<LinkedHashMap<String, Object>>();
			Stack<String> keyNamesToRemember = new Stack<String>();

			Stack<ArrayList<Object>> nestedArray = new Stack<ArrayList<Object>>();
			Stack<Boolean> nestedTruth = new Stack<Boolean>();

			while (jsonParser.hasNext()) {
				Event event = jsonParser.next();

				switch (event) {
				// If this is the case, it is either the start of a json object that
				// contains info about the restaurant or an object within an object
				case START_OBJECT:
					if (innerJson == 0) {

						// If no inner objects, we are starting a new object, as in
						// a new restaurant
						info = new RevieweeData();
						keyNamesToRemember.clear();
						inner.clear();

					} else {
						// else this is an inner object, and we need to create
						// a hashmap for its use
						inner.push(new LinkedHashMap<String, Object>());
						keyNamesToRemember.push(keyName);
					}
					innerJson++;
					break;

				case KEY_NAME:
					keyName = new String(jsonParser.getString());
					// Name of this category for use
					break;

				case VALUE_STRING:
					String value = new String(jsonParser.getString());

					// If string is part of an array, we add it to that array
					if (!nestedTruth.isEmpty())
						toAdd.add(value);

					// If string is part of an inner object, we add it to the
					// inner object's hashMap
					else if (innerJson > 1) {
						LinkedHashMap<String, Object> toUse = new LinkedHashMap<String, Object>(inner.pop());
						toUse.put(keyName, value);
						inner.push(toUse);

						// else string is just a normal value and is associated with the
						// previous keyName
					} else
						info.storeInfo(keyName, value);

					break;

				case VALUE_NUMBER:
					BigDecimal numberValue = jsonParser.getBigDecimal();

					// If in array, add to corresponding array
					if (!nestedTruth.isEmpty())
						toAdd.add(numberValue);

					// If in inner object, add to corresponding HashMap
					else if (innerJson > 1) {
						LinkedHashMap<String, Object> toUse = new LinkedHashMap<String, Object>(inner.pop());
						toUse.put(keyName, numberValue);
						inner.push(toUse);

					}

					// A normal value, associate with previous KeyName
					else
						info.storeInfo(keyName, numberValue);

					break;

				case VALUE_TRUE:
					boolean ans = true;

					// cases are of similar idea to previous
					if (!nestedTruth.isEmpty())
						toAdd.add(ans);

					else if (innerJson > 1) {
						LinkedHashMap<String, Object> toUse = new LinkedHashMap<String, Object>(inner.pop());
						toUse.put(keyName, ans);
						inner.push(toUse);
					}

					else
						info.storeInfo(keyName, ans);

					break;

				case VALUE_FALSE:
					boolean fal = false;

					// cases are of similar idea to previous
					if (!nestedTruth.isEmpty())
						toAdd.add(fal);
					else if (innerJson > 1) {
						LinkedHashMap<String, Object> toUse = new LinkedHashMap<String, Object>(inner.pop());
						toUse.put(keyName, fal);
						inner.push(toUse);
					} else
						info.storeInfo(keyName, fal);

					break;

				case START_ARRAY:
					// Start of an array within the json object
					// Add true to stack, meaning that we have an additional array
					nestedTruth.push(true);

					// Counts how many inner arrays we must worry about
					innerArray++;

					// Initalizes the new array
					toAdd = new ArrayList<Object>();
					nestedArray.push(toAdd);
					break;

				case END_ARRAY:
					// Pop, an array has ended
					nestedTruth.pop();
					innerArray--;

					// If no more inner arrays, meaning add it to reviweedata
					if (innerArray == 0)
						info.storeInfo(keyName, nestedArray.pop());
					else {
						// This situation means there is an array within an array
						ArrayList<Object> arrayToAdd = new ArrayList<Object>(nestedArray.pop());
						ArrayList<Object> outerArray = new ArrayList<Object>(nestedArray.pop());
						outerArray.add(arrayToAdd);
						nestedArray.push(outerArray);
					}

					break;

				// Json object has ended, we store it as port of reviewees with its
				// unique business_id as its key
				case END_OBJECT:
					innerJson--;
					if (innerJson == 0) {
						String uniqueID = (String) info.getInfo("business_id");
						info.storeID(uniqueID);
						this.reviewees.put(uniqueID, info);
					}
					// This is for cases where we end an inner object
					else {
						LinkedHashMap<String, Object> toUse = new LinkedHashMap<String, Object>(inner.pop());
						String innerKeyName = new String(keyNamesToRemember.pop());
						info.storeInfo(innerKeyName, toUse);
					}
					break;
				}
			}
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			throw new IOException();
		}

		// The idea of this parser is almost identical to that for restaurants
		String revs = new String(reviews);
		try {
			InputStream res2 = new FileInputStream(revs);
			JsonParser jsonParser2 = Json.createParser(res2);

			String keyName = null;
			ReviewData info2 = new ReviewData();

			int innerJson = 0;

			Stack<LinkedHashMap<String, Object>> inner = new Stack<LinkedHashMap<String, Object>>();
			Stack<String> keyNamesToRemember = new Stack<String>();

			while (jsonParser2.hasNext()) {
				Event event2 = jsonParser2.next();

				switch (event2) {

				case START_OBJECT:
					if (innerJson == 0) {
						info2 = new ReviewData();
						keyNamesToRemember.clear();
						inner.clear();
					} else {
						inner.push(new LinkedHashMap<String, Object>());
						keyNamesToRemember.push(keyName);
					}
					innerJson++;
					break;

				case KEY_NAME:

					keyName = new String(jsonParser2.getString());
					break;

				case VALUE_STRING:
					String value = new String(jsonParser2.getString());

					if (innerJson == 1)
						info2.storeInfo(keyName, value);
					else {
						LinkedHashMap<String, Object> toUse = new LinkedHashMap<String, Object>(inner.pop());
						toUse.put(keyName, value);
						inner.push(toUse);
					}
					break;

				case VALUE_NUMBER:
					BigDecimal numberValue = jsonParser2.getBigDecimal();

					if (innerJson == 1)
						info2.storeInfo(keyName, numberValue);
					else {
						LinkedHashMap<String, Object> toUse = new LinkedHashMap<String, Object>(inner.pop());
						toUse.put(keyName, numberValue);
						inner.push(toUse);
					}

					break;

				case END_OBJECT:
					innerJson--;
					if (innerJson == 0) {
						String uniqueID = (String) info2.getInfo("review_id");
						info2.storeID(uniqueID);
						this.reviews.put(uniqueID, info2);
					} else {
						LinkedHashMap<String, Object> toUse = new LinkedHashMap<String, Object>(inner.pop());
						String innerKeyName = new String(keyNamesToRemember.pop());
						info2.storeInfo(innerKeyName, toUse);
					}
					break;
				}
			}
		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			throw new IOException();
		}

		// The idea of this parser is almost identical to that for restaurants
		String user = new String(userList);
		try {
			InputStream res3 = new FileInputStream(user);
			JsonParser jsonParser3 = Json.createParser(res3);

			String keyName = null;
			ReviewerData info3 = new ReviewerData();
			ArrayList<Object> toAdd = null;

			int innerJson = 0;
			int innerArray = 0;

			Stack<LinkedHashMap<String, Object>> inner = new Stack<LinkedHashMap<String, Object>>();
			Stack<String> keyNamesToRemember = new Stack<String>();

			Stack<ArrayList<Object>> nestedArray = new Stack<ArrayList<Object>>();
			Stack<Boolean> nestedTruth = new Stack<Boolean>();

			while (jsonParser3.hasNext()) {
				Event event3 = jsonParser3.next();

				switch (event3) {

				case START_OBJECT:
					if (innerJson == 0) {
						info3 = new ReviewerData();
						keyNamesToRemember.clear();
						inner.clear();
					} else {
						inner.push(new LinkedHashMap<String, Object>());
						keyNamesToRemember.push(keyName);
					}
					innerJson++;
					break;

				case KEY_NAME:

					keyName = new String(jsonParser3.getString());
					break;

				case VALUE_STRING:
					String value = new String(jsonParser3.getString());

					if (!nestedTruth.isEmpty())
						toAdd.add(value);
					else if (innerJson == 1)
						info3.storeInfo(keyName, value);
					else {
						LinkedHashMap<String, Object> toUse = new LinkedHashMap<String, Object>(inner.pop());
						toUse.put(keyName, value);
						inner.push(toUse);
					}
					break;

				case START_ARRAY:
					// Start of an array within the json object
					// Add true to stack, meaning that we have an additional array
					nestedTruth.push(true);

					// Counts how many inner arrays we must worry about
					innerArray++;

					// Initalizes the new array
					toAdd = new ArrayList<Object>();
					nestedArray.push(toAdd);
					break;

				case END_ARRAY:
					// Pop, an array has ended
					nestedTruth.pop();
					innerArray--;

					// If no more inner arrays, meaning add it to reviweedata
					if (innerArray == 0)
						info3.storeInfo(keyName, nestedArray.pop());
					else {
						// This situation means there is an array within an array
						ArrayList<Object> arrayToAdd = new ArrayList<Object>(nestedArray.pop());
						ArrayList<Object> outerArray = new ArrayList<Object>(nestedArray.pop());
						outerArray.add(arrayToAdd);
						nestedArray.push(outerArray);
					}

					break;

				case VALUE_NUMBER:
					BigDecimal numberValue = jsonParser3.getBigDecimal();

					if (!nestedTruth.isEmpty())
						toAdd.add(numberValue);
					else if (innerJson == 1)
						info3.storeInfo(keyName, numberValue);
					else {
						LinkedHashMap<String, Object> toUse = new LinkedHashMap<String, Object>(inner.pop());
						toUse.put(keyName, numberValue);
						inner.push(toUse);
					}

					break;

				case END_OBJECT:
					innerJson--;
					if (innerJson == 0) {
						String uniqueID = (String) info3.getInfo("user_id");
						info3.storeID(uniqueID);
						this.reviewers.put(uniqueID, info3);
					} else {
						LinkedHashMap<String, Object> toUse = new LinkedHashMap<String, Object>(inner.pop());
						String innerKeyName = new String(keyNamesToRemember.pop());
						info3.storeInfo(innerKeyName, toUse);
					}
					break;
				}
			}

		} catch (FileNotFoundException e) {
			// e.printStackTrace();
			throw new IOException();
		}
	}

	/**
	 * Creates an empty Yelp database
	 */
	public YelpDB() {
		reviews = new LinkedHashMap<String, ReviewData>();
		reviewees = new LinkedHashMap<String, RevieweeData>();
		reviewers = new LinkedHashMap<String, ReviewerData>();
	}
	
	/**
	 * Method tells whether restaurant exists in database or not
	 * 
	 * @param business_id of the restaurant
	 * @return whether the restaurant of that unique ID exists within the database
	 */
	public synchronized boolean restaurantExists(String business_id) {
		return reviewees.containsKey(business_id);
	}
	/**
	 * Method tells whether user exists in database or not
	 * 
	 * @param user_id of the person in question
	 * @return whether the person in question exists within the databse
	 */
	public synchronized boolean userExists(String user_id) {
		return reviewers.containsKey(user_id);
	}
	
	/**
	 * Adds the review to the database only of the reviewee and reviewer exists in
	 * the database
	 * 
	 * @param review
	 *            the review as a revieweData that you want to add to the datbase
	 */
	public synchronized void addReview(ReviewData review) {
		if (reviewees.containsKey(review.getReviewee()) && reviewers.containsKey(review.getReviewer())) {
			reviews.put(review.getID(), review);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Adds the reviewee to the database
	 * 
	 * @param reviewee
	 *            the reviewee as revieweeData that you want to add to the database
	 */
	public synchronized void addReviewee(RevieweeData reviewee) {
		reviewees.put(reviewee.getID(), reviewee);
	}

	/**
	 * Adds the reviewer to the database
	 * 
	 * @param reviewer
	 *            the reviewer as a reviewerData that you want to add to the
	 *            database
	 */
	public synchronized void addReviewer(ReviewerData reviewer) {
		reviewers.put(reviewer.getID(), reviewer);
	}

	@Override
	public synchronized Data getEntry(String ID) {
		// Finds data that have the same ID as the entry
		Data returnData = null;
		if (reviews.containsKey(ID)) {
			returnData = reviews.get(ID);
		} else if (reviewees.containsKey(ID)) {
			returnData = reviewees.get(ID);
		} else if (reviewers.containsKey(ID)) {
			returnData = reviewers.get(ID);
		}
		return returnData;
	}

	@Override
	public synchronized Set<Data> getMatches(String queryString) {
		Set<Data> returnSet = new HashSet<>();
		// Gets all the reviews that match the string
		Stream<ReviewData> getReviews = reviews.values().stream();
		returnSet.addAll(getReviews.filter(s -> s.matchesInfo(queryString)).collect(Collectors.toSet()));
		// Gets all the reviewees that match the string
		Stream<RevieweeData> getReviewees = reviewees.values().stream();
		returnSet.addAll(getReviewees.filter(s -> s.matchesInfo(queryString)).collect(Collectors.toSet()));
		// Gets all reviewers that match the string
		Stream<ReviewerData> getReviewers = reviewers.values().stream();
		returnSet.addAll(getReviewers.filter(s -> s.matchesInfo(queryString)).collect(Collectors.toSet()));
		return returnSet;
	}

	public synchronized Set<RevieweeData> getResMatches(String queryString) {
		Set<RevieweeData> returnSet = new HashSet<>();
		// Gets all the reviewees that match the string
		Stream<RevieweeData> getReviewees = reviewees.values().stream();
		returnSet.addAll(getReviewees.filter(s -> s.matchesInfo(queryString)).collect(Collectors.toSet()));
		return returnSet;
	}

	public synchronized Set<RevieweeData> getRes() {
		Set<RevieweeData> returnSet = new HashSet<>(reviewees.values());
		// Gets all the reviewees that match the string
		return returnSet;
	}

	/**
	 * Helper function that helps find the cluster that has the greatest variance,
	 * as in the cluster with the greatest distance between two points in it out of
	 * all clusters.
	 * 
	 * @param clusters
	 *            is an ArrayList of sets that represent the various clusters of a
	 *            map
	 * 
	 * @return an index of the cluster with the largest variance in that list
	 * 
	 **/
	private int largestClusterVariance(ArrayList<Set> clusters) {

		int largestVariance = 0;
		double largestDistance = 0;

		ArrayList<Set> test = new ArrayList<Set>(clusters);

		for (Set cluster : test) {
			if (cluster.size() > 1) {
				ArrayList<Coordinates> ab = new ArrayList<Coordinates>(Coordinates.furthestCornersOfGroup(cluster));
				Coordinates a = ab.get(0);
				Coordinates b = ab.get(1);
				double distance = a.getDistance(b);

				if (distance >= largestDistance) {
					largestDistance = distance;
					largestVariance = test.indexOf(cluster);
				}
			}
		}
		return largestVariance;
	}

	/**
	 * Method that creates an arrayList of sets, where each set is a cluster of
	 * objects that are close together according to their x,y coordinates. Each
	 * member of the set is closer to that set's average coordinates that any other
	 * set's average.
	 * 
	 * @param k
	 *            is an integer of how many cluster, cannot be larger than number of
	 *            restaurants
	 * 
	 * @return an ArrayList of sets, where each set represents a cluster
	 * 
	 **/
	public synchronized ArrayList<Set> kMeansToDo(int k) {

		int numberOfCluster = k;

		ArrayList<Coordinates> toIterateOver = new ArrayList<Coordinates>();
		ArrayList<Coordinates> centroids = new ArrayList<Coordinates>();

		ArrayList<Set> result = new ArrayList<Set>();

		LinkedHashMap<Coordinates, LinkedHashMap<Coordinates, Double>> forUse = new LinkedHashMap<Coordinates, LinkedHashMap<Coordinates, Double>>();

		// latitude = x, longitude = y
		for (String ids : reviewees.keySet()) {

			// For every restaurant, we create its coordinates from latitude and longitude
			// assigned to its unique business id
			BigDecimal x = (BigDecimal) reviewees.get(ids).getInfo("latitude");
			BigDecimal y = (BigDecimal) reviewees.get(ids).getInfo("longitude");
			Coordinates restaurant = new Coordinates(ids, x.doubleValue(), y.doubleValue());
			toIterateOver.add(restaurant);
		}

		// Shuffles our list, so we will start with random centroids each time
		Collections.shuffle(toIterateOver);

		int changes = toIterateOver.size() / numberOfCluster;
		int start = 0;

		// This is how we set our starting centroids, take them from the arrayList
		// of all possible coordinates
		for (int index = 0; index < numberOfCluster; index++) {

			Coordinates startingCentroid = new Coordinates(toIterateOver.get(start));

			Set groupings = new HashSet<Coordinates>();

			// HashMap contains the distances from that particular centroid, we
			// will use this later to observe the distances from each centroid
			// Each centroid will have one of these hashmaps
			LinkedHashMap<Coordinates, Double> distanceFromCentroid = new LinkedHashMap<Coordinates, Double>();

			// For each possible coordinate, we find the distance from this
			// particular centroid
			for (Coordinates a : toIterateOver) {
				distanceFromCentroid.put(a, a.getDistance(startingCentroid));
			}

			// Connect the startingCentroid to its own hashmap of distances
			forUse.put(startingCentroid, distanceFromCentroid);

			// Add the empty set to the arrayList
			result.add(groupings);

			centroids.add(startingCentroid);

			// Find the next starting centroid
			start += changes;
		}

		boolean flag = true;

		// While we can still find better centroids, we continue iterating
		do {

			// For every coordinate, we will compare its distance to each centroid's
			// distance hashMap to find the minimum
			for (Coordinates rest : toIterateOver) {
				double minDistance = Double.POSITIVE_INFINITY;
				Coordinates closestCentroid = null;

				// We will see the distance of this particular coordinate from each
				// centroid
				for (Coordinates centroid : forUse.keySet()) {
					LinkedHashMap<Coordinates, Double> distanceFromCentroid = forUse.get(centroid);
					double distance = distanceFromCentroid.get(rest);

					// Find minimum
					if (distance < minDistance) {
						minDistance = distance;
						closestCentroid = new Coordinates(centroid);
					}
				}

				// When we get the minimum, we add it to the corresponding set
				int check = centroids.indexOf(closestCentroid);
				result.get(check).add(rest);
			}

			for (Set<Coordinates> a : result) {
				// If we have empty clusters we have a problem
				if (a.isEmpty()) {

					// Find the cluster with the largest variance, as in the cluster with the widest
					// distance between its two furthest points
					int indexA = largestClusterVariance(result);
					int indexB = result.indexOf(a);

					// We get the two corners of that cluster
					ArrayList<Coordinates> cornersOfCluster = new ArrayList<Coordinates>(
							Coordinates.furthestCornersOfGroup(result.get(indexA)));

					ArrayList<Coordinates> toChange = new ArrayList<Coordinates>(result.get(indexA));

					Set<Coordinates> splitA = new HashSet<Coordinates>();
					Set<Coordinates> splitB = new HashSet<Coordinates>();

					Coordinates startCorner = new Coordinates(cornersOfCluster.get(0));
					Coordinates endCorner = new Coordinates(cornersOfCluster.get(1));

					// We have split the cluster into two clusters, one containing each corner, now
					// we add the rest of the former cluster to whichever end they are closer to
					for (Coordinates point : toChange) {
						double distanceToStart = point.getDistance(startCorner);
						double distanceToEnd = point.getDistance(endCorner);

						if (distanceToStart < distanceToEnd) {
							splitA.add(point);

						} else if (distanceToEnd <= distanceToStart && !splitA.contains(point)) {
							splitB.add(point);

						} else
							continue;
					}

					// Cluster has been split successfully and we no longer have an empty cluster
					result.set(indexA, splitB);
					result.set(indexB, splitA);
				}
			}

			ArrayList<Coordinates> finalCheck = new ArrayList<Coordinates>();
			// Calculate centroid of each Cluster, check to see if there are any misplaced
			// restaurants
			for (Set s : result) {

				double X = 0;
				double Y = 0;
				for (Object points : s) {
					Coordinates a = new Coordinates((Coordinates) points);
					X += a.getX();
					Y += a.getY();
				}
				X = X / s.size();
				Y = Y / s.size();
				Coordinates tempCentroid = new Coordinates(Integer.toString(result.indexOf(s)), X, Y);
				finalCheck.add(tempCentroid);
			}

			// Does final check if it belongs to the correct clusters
			ArrayList<Set> tempResult = new ArrayList<Set>(result);

			for (Set s : result) {
				for (Object points : s) {
					double minDistance = Integer.MAX_VALUE;
					int index = 0;

					Coordinates a = new Coordinates((Coordinates) points);

					// Find minimal distance to point to any cluster
					for (Coordinates cent : finalCheck) {
						double distance = a.getDistance(cent);

						if (distance < minDistance) {
							minDistance = distance;
							index = finalCheck.indexOf(cent);
						}
					}

					int resultIndex = result.indexOf(s);

					// If it does not belong in the correct cluster, we must move it to the correct
					// one (unless
					// it is of size 1, which means that there is somekind of repeated coordinate)
					if (index != result.indexOf(s) && s.size() > 1) {
						Set<Coordinates> toRemove = new HashSet<Coordinates>(result.get(resultIndex));
						Set<Coordinates> toAdd = new HashSet<Coordinates>(result.get(index));

						toRemove.remove(a);
						toAdd.add(a);
						tempResult.set(resultIndex, toRemove);
						tempResult.set(index, toAdd);
					}
				}
			}
			result = new ArrayList<Set>(tempResult);

			ArrayList<Coordinates> temp = new ArrayList<Coordinates>();

			// Calculate the new centroids from the average coordinates of each set
			for (Set<Coordinates> a : result) {

				double sumX = 0;
				double sumY = 0;

				for (Coordinates b : a) {
					sumX += b.getX();
					sumY += b.getY();
				}

				sumX = sumX / a.size();
				sumY = sumY / a.size();

				temp.add(new Coordinates(new String(Integer.toString(result.indexOf(a))), sumX, sumY));
			}

			if (temp.toString().equals(centroids.toString()))
				flag = false;
			else {
				// If new centroids are different, we clear lists and
				// repeat the clustering
				centroids = new ArrayList<Coordinates>(temp);

				forUse.clear();
				result.clear();

				for (Coordinates cent : centroids) {

					Set groupings = new HashSet<Coordinates>();

					LinkedHashMap<Coordinates, Double> distanceFromCentroid = new LinkedHashMap<Coordinates, Double>();

					// We find new distances from each centroid and we put it in the hashMap to
					// check
					for (Coordinates a : toIterateOver) {
						distanceFromCentroid.put(a, a.getDistance(cent));
					}
					forUse.put(cent, distanceFromCentroid);
					result.add(groupings);
				}
			}
		} while (flag);

		return result;
	}

	@Override
	public String kMeansClusters_json(int k) {

		int numberOfCluster = k;
		// latitude = x, longitude = y

		ArrayList<Set> result = new ArrayList<Set>(kMeansToDo(numberOfCluster));

		// Print the arrayList of sets of clustered restaurants into json format
		// all of them are weighted the same
		String answer = "[";
		int index = 0;
		for (Set cc : result) {
			for (Coordinates dd : (HashSet<Coordinates>) cc) {
				answer += "{" + "\"x\": " + dd.getX() + ", \"y\": " + dd.getY() + ", \"name\": " + "\""
						+ reviewees.get(dd.getName()).getInfo("name") + "\"" + ", \"cluster\": " + index
						+ ", \"weight\": 1.0}, ";
			}

			index++;
		}
		answer = answer.substring(0, answer.length() - 2) + "]";

		return answer;
	}

	@Override
	public ToDoubleBiFunction<MP5Db<Data>, String> getPredictorFunction(String user) {
		double sxx = 0;
		double sxy = 0;
		double b;
		double a;
		double meanx;
		double meany;
		double elements;

		// Collecting every review that is done by the user
		Set<ReviewData> reviewSet = reviews.values().stream().filter(s -> user.equals(s.getReviewer()))
				.collect(Collectors.toSet());

		elements = reviewSet.size();

		// Getting all the prices of the reviewees and summing them together then
		// dividing it by the number of elements to get the average prices of the
		// restaurants the user has been to.
		meanx = (double) reviewSet.stream().map(x -> reviewees.get(x.getReviewee()).getPrice()).reduce(0.0, Double::sum)
				/ elements;

		// Getting all the ratings that the user has given out and summing them together
		// then dividing it by the number of elements to get the average rating the user
		// has given.
		meany = (double) reviewSet.stream().map(x -> x.getRating()).reduce(0.0, Double::sum) / elements;

		// For every element, find the three sums
		for (ReviewData re : reviewSet) {
			sxx += Math.pow(reviewees.get(re.getReviewee()).getPrice() - meanx, 2);
			sxy += (reviewees.get(re.getReviewee()).getPrice() - meanx) * (re.getRating() - meany);
		}

		// Calculating values used for linear approximation
		b = sxy / sxx;
		if (Double.isNaN(b)) {
			throw new IllegalArgumentException();
		}
		a = meany - b * meanx;

		// The return function
		ToDoubleBiFunction<MP5Db<Data>, String> predictorFunction = new ToDoubleBiFunction<MP5Db<Data>, String>() {

			/**
			 * Calculates the function that represents the approximation function
			 * 
			 * @param arg0
			 *            the database that the information is located in
			 * @param arg1
			 *            the restaurant that the user will review
			 * @return the approximate rating that the user will give the business
			 */
			@Override
			public double applyAsDouble(MP5Db<Data> arg0, String arg1) {
				double returnVal = 0;
				Set<Data> matches = arg0.getMatches(arg1);
				Set<Data> restuarants = matches.stream().filter(x -> x.getType().equals("reviewee"))
						.collect(Collectors.toSet());
				// There should only be one entry
				for (Data re : restuarants) {
					returnVal = a + b * Double.valueOf(re.getInfo("price").toString());
				}
				// Automatically corrects the value if it is out of the range of possible
				// answers
				if (returnVal < 1) {
					returnVal = 1;
				}
				if (returnVal > 5) {
					returnVal = 5;
				}
				return returnVal;
			}

		};

		return predictorFunction;
	}

}