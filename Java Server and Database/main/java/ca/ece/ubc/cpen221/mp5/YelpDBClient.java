package ca.ece.ubc.cpen221.mp5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;

public class YelpDBClient {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	// Rep invariant: socket, in, out != null

	/**
	 * Make a FibonacciClient and connect it to a server running on hostname at the
	 * specified port.
	 * 
	 * @throws IOException
	 *             if can't connect
	 */
	public YelpDBClient(String hostname, int port) throws IOException {
		socket = new Socket(hostname, port);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	/**
	 * Send a request to the server. Requires this is "open".
	 * 
	 * @param x
	 *            to find Fibonacci(x)
	 * @throws IOException
	 *             if network or server failure
	 */
	public void sendRequest(String x) throws IOException {
		out.print(x + "\n");
		out.flush(); // important! make sure x actually gets sent
	}

	/**
	 * Get a reply from the next request that was submitted. Requires this is
	 * "open".
	 * 
	 * @return the requested Fibonacci number
	 * @throws IOException
	 *             if network or server failure
	 */
	public String getReply() throws IOException {
		String reply = in.readLine();
		
		if (reply == null) {
			throw new IOException("connection terminated unexpectedly");
		}
		try {
			return new String(reply);
		} catch (NullPointerException e) {
			throw new IOException("No Answer");
		}
	}

	/**
	 * Closes the client's connection to the server. This client is now "closed".
	 * Requires this is "open".
	 * 
	 * @throws IOException
	 *             if close fails
	 */
	public void close() throws IOException {
		in.close();
		out.close();
		socket.close();
	}

	public static void main(String[] args) {
		try {
			YelpDBClient client = new YelpDBClient("localhost", Integer.valueOf(args[0]));

			System.out.print("request: GETRESTAURANT gclB3ED6uk6viWlolSb_uA");
			client.sendRequest("GETRESTAURANT gclB3ED6uk6viWlolSb_uA");
			System.out.println(client.getReply());
			
			System.out.print("request: ADDUSER {\"name\": \"Sathish G.\"}");
			client.sendRequest("ADDUSER {\"name\": \"Sathish G.\"}");
			System.out.println(client.getReply());
			
			System.out.print("request: ADDRESTAURANT {\"open\": true, \"url\": \"http://www.yelp.com/biz/cafe-3-berkeley\", \"longitude\": -122.260408, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"business_id\": \"gclB3ED6uk6viWlolSb_uA\", \"name\": \"Cafe 3\", \"categories\": [\"Cafes\", \"Restaurants\"], \"state\": \"CA\", \"type\": \"business\", \"stars\": 2.0, \"city\": \"Berkeley\", \"full_address\": \"2400 Durant Ave\\nTelegraph Ave\\nBerkeley, CA 94701\", \"review_count\": 9, \"photo_url\": \"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.867417, \"price\": 1}");
			client.sendRequest("ADDRESTAURANT {\"open\": true, \"url\": \"http://www.yelp.com/biz/cafe-3-berkeley\", \"longitude\": -122.260408, \"neighborhoods\": [\"Telegraph Ave\", \"UC Campus Area\"], \"business_id\": \"gclB3ED6uk6viWlolSb_uA\", \"name\": \"Cafe 3\", \"categories\": [\"Cafes\", \"Restaurants\"], \"state\": \"CA\", \"type\": \"business\", \"stars\": 2.0, \"city\": \"Berkeley\", \"full_address\": \"2400 Durant Ave\\nTelegraph Ave\\nBerkeley, CA 94701\", \"review_count\": 9, \"photo_url\": \"http://s3-media1.ak.yelpcdn.com/bphoto/AaHq1UzXiT6zDBUYrJ2NKA/ms.jpg\", \"schools\": [\"University of California at Berkeley\"], \"latitude\": 37.867417, \"price\": 1}");
			System.out.println(client.getReply());
			
			System.out.print("request: ADDREVIEW {\"type\": \"review\", \"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"votes\": {\"cool\": 0, \"useful\": 0, \"funny\": 0}, \"review_id\": \"0a-pCW4guXIlWNpVeBHChg\", \"text\": \"The pizza is terrible, but if you need a place to watch a game or just down some pitchers, this place works.\\n\\nOh, and the pasta is even worse than the pizza.\", \"stars\": 2, \"user_id\": \"90wm_01FAIqhcgV_mPON9Q\", \"date\": \"2006-07-26\"}");
			client.sendRequest("ADDREVIEW {\"type\": \"review\", \"business_id\": \"1CBs84C-a-cuA3vncXVSAw\", \"votes\": {\"cool\": 0, \"useful\": 0, \"funny\": 0}, \"review_id\": \"0a-pCW4guXIlWNpVeBHChg\", \"text\": \"The pizza is terrible, but if you need a place to watch a game or just down some pitchers, this place works.\\n\\nOh, and the pasta is even worse than the pizza.\", \"stars\": 2, \"user_id\": \"90wm_01FAIqhcgV_mPON9Q\", \"date\": \"2006-07-26\"}");
			System.out.println(client.getReply());
			
			// Works when all above is commented out, doesn't work when it isn't. Please fix server implementation
			System.out.print("request: QUERY in(Telegraph Ave) && (category(Chinese) || category(Italian)) && price <= 2");
			client.sendRequest("QUERY in(Telegraph Ave) && (category(Chinese) || category(Italian)) && price <= 2");
			System.out.println(client.getReply());
			
			client.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
