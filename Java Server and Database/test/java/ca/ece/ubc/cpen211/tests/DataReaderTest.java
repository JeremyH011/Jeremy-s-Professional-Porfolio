package ca.ece.ubc.cpen211.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.*;

public class DataReaderTest {

	@Test
	public void test0() {

		try {

			YelpDB test = new YelpDB("data/restaurants.json", "data/reviews.json", "data/users.json");
			
			//Tests that all values from 1 to 40 are done properly
			for (int k = 1; k <= 44; k++) {
				ArrayList<Set> testResults = test.kMeansToDo(k);
				String result = test.kMeansClusters_json(k);
				ArrayList<Coordinates> centroid = new ArrayList<Coordinates>();

				for (Set s : testResults) {
					if (s.isEmpty())
						fail("No empty clusters allowed");
					else {
						double X = 0;
						double Y = 0;
						for (Object points : s) {
							Coordinates a = new Coordinates((Coordinates) points);
							X += a.getX();
							Y += a.getY();
						}
						X = X / s.size();
						Y = Y / s.size();
						Coordinates centroids = new Coordinates(Integer.toString(testResults.indexOf(s)), X, Y);
						centroid.add(centroids);
					}
				}
				
				int totalNumber = 0;
				int numberOfClusters = 0;
				for (Set s : testResults) {
					
					if(s.isEmpty())
						fail("No-empty clusters");
					
					totalNumber += s.size();
					numberOfClusters++;
					
					for (Object points : s) {
						double minDistance = Integer.MAX_VALUE;
						int index = 0;

						Coordinates a = new Coordinates((Coordinates) points);

						for (Coordinates cent : centroid) {
							double distance = a.getDistance(cent);

							if (distance < minDistance) {
								minDistance = distance;
								index = centroid.indexOf(cent);
							}
						}
						if(s.size() > 1)
							assertEquals(index, testResults.indexOf(s));
						else
							continue;
					}
				}
				
				assertEquals(totalNumber,135);
				assertEquals(numberOfClusters, k);
			}
			
			// Prints result for JSON visualizer
			// System.out.println(result);

			// You cannot use fully assert here, because the helper method for
			// kMeansClusters produces
			// an arrayList of Sets, and sets don't preserve order, so order may be
			// different
			// each time you test. You have to do this by eye through visualizer.

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void test1() {

		try {
			YelpDB test = new YelpDB("data/rest3.json", "data/reviews.json", "data/users.json");

			ArrayList<Set> testResults = test.kMeansToDo(3);
			//String result = test.kMeansClusters_json(3);
			ArrayList<Coordinates> centroid = new ArrayList<Coordinates>();

			for (Set s : testResults) {
				if (s.isEmpty())
					fail("No empty clusters allowed");
				else {
					double X = 0;
					double Y = 0;
					for (Object points : s) {
						Coordinates a = new Coordinates((Coordinates) points);
						X += a.getX();
						Y += a.getY();
					}
					X = X / s.size();
					Y = Y / s.size();
					Coordinates centroids = new Coordinates(Integer.toString(testResults.indexOf(s)), X, Y);
					centroid.add(centroids);
				}
			}

			for (Set s : testResults) {
				for (Object points : s) {
					double minDistance = Integer.MAX_VALUE;
					int index = 0;

					Coordinates a = new Coordinates((Coordinates) points);

					for (Coordinates cent : centroid) {
						double distance = a.getDistance(cent);

						if (distance < minDistance) {
							minDistance = distance;
							index = centroid.indexOf(cent);
						}
					}
					if(s.size() > 1)
						assertEquals(index, testResults.indexOf(s));
					else
						continue;
				}
			}

			// You cannot use assert here, because the helper method for kMeansClusters
			// produces
			// an arrayList of Sets, and sets don't preserve order, so order may be
			// different
			// each time you test. You have to do this by eye through visualizer.
		} catch (IOException e) {
			fail("Exception not expected");
			e.printStackTrace();
		}

	}

	@Test
	public void test2() {
		try {
			// Testing the functionality of the database implementation

			// Suedo-testing parsing data
			YelpDB test = new YelpDB("data/restaurants.json", "data/reviews.json", "data/users.json");
			assertTrue(test.getEntry("zqcTeWwRe7HjbwDaWJGjCw") != null);
			assertEquals("La Fiesta Mexican Restaurant", test.getEntry("zqcTeWwRe7HjbwDaWJGjCw").getInfo("name"));
			assertEquals("zqcTeWwRe7HjbwDaWJGjCw", test.getEntry("zqcTeWwRe7HjbwDaWJGjCw").getID());

			// Testing the functionality of getMatches
			HashSet<Data> dataSet = new HashSet<>();
			dataSet.add(test.getEntry("wZzb_tz28khJSgavfMmEYA"));
			dataSet.add(test.getEntry("Y9WMS7zv9BhKozIfEvL4_Q"));
			dataSet.add(test.getEntry("RJP92d1DSY47lMkV4gIePg"));
			dataSet.add(test.getEntry("wETBU8Qnn-jyOyMCHL_ScA"));
			dataSet.add(test.getEntry("mHrIrBU2w_si0AyK8gfk1Q"));
			dataSet.add(test.getEntry("zqcTeWwRe7HjbwDaWJGjCw"));
			dataSet.add(test.getEntry("QZeRPF40_EJkoHnrxi7bAA"));
			assertEquals(dataSet, test.getMatches("zqcTeWwRe7HjbwDaWJGjCw"));

			// Testing some functionality of data
			assertTrue(test.getEntry("zqcTeWwRe7HjbwDaWJGjCw").allInfo().toString()
					.contains("neighborhoods: [Telegraph Ave, UC Campus Area]"));
			assertTrue(test.getEntry("zqcTeWwRe7HjbwDaWJGjCw").matchesInfo("La Fiesta Mexican Restaurant"));
			assertTrue(test.getEntry("zqcTeWwRe7HjbwDaWJGjCw").matchesInfo("University of California at Berkeley"));
			try {
				// Testing getPredictorFunction with invalid input
				test.getPredictorFunction("hQ8To_s10kfhi2rc4js5cg").applyAsDouble(test, "La Fiesta Mexican Restaurant");
				fail("Exception expected");
			} catch (IllegalArgumentException e) {
				// Do nothing
			}
		} catch (IOException e) {
			fail("Exception not expected");
		}
	}

	@Test
	public void test3() {
		try {
			// Testing getPredictorFunction with valid input
			YelpDB test = new YelpDB("data/miniRestaurants.json", "data/miniReviews.json", "data/miniUsers.json");
			// Suedo-testing parsing
			assertTrue(test.getEntry("_NH7Cpq3qZkByP5xR4gXog") != null);
			assertTrue(test.getEntry("fdsfadsByP5xR4gXog") == null);
			try {
				// Testing if getPredictorFunction returns the proper function
				assertEquals(3.25, test.getPredictorFunction("_NH7Cpq3qZkByP5xR4gXog").applyAsDouble(test,
						"gOp_w9qmLq6B8YRypTPp8g"), 0.0);
				assertEquals(4.75, test.getPredictorFunction("_NH7Cpq3qZkByP5xR4gXog").applyAsDouble(test,
						"gOp_w9qmLq6B8YRypTPp8h"), 0.0);
			} catch (IllegalArgumentException e) {
				fail("Exception not expected");
			}
		} catch (IOException e) {
			fail("Exception not expected");
		}
	}

	@Test
	public void test4() {
		// Testing adding information to the
		YelpDB test = new YelpDB();

		// Creating instances of the review, reviewee, and reviewer to test the storing
		// functionality of the database
		ReviewData review1 = new ReviewData();
		review1.storeID("A");
		review1.putReviewee("B");
		review1.putReviewer("C");
		RevieweeData reviewee1 = new RevieweeData();
		reviewee1.storeID("B");
		ReviewerData reviewer1 = new ReviewerData();
		reviewer1.storeID("C");

		try {
			test.addReview(review1);
			fail("Excpetion expected");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		// Testing addReviewee
		test.addReviewee(reviewee1);
		assertTrue(test.getEntry("B") != null);
		assertEquals("B", test.getEntry("B").getID());

		try {
			test.addReview(review1);
			fail("Excpetion expected");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}

		// Testing addReviewer
		test.addReviewer(reviewer1);
		assertTrue(test.getEntry("C") != null);
		assertEquals("C", test.getEntry("C").getID());

		try {
			test.addReview(review1);
		} catch (IllegalArgumentException e) {
			fail("Excpetion not expected");
		}
		assertTrue(test.getEntry("A") != null);
		assertEquals("A", test.getEntry("A").getID());
	}

	@Test
	public void test5() {
		// Testing adding information to the
		YelpDB test = new YelpDB();

		// Creating instances of the review, reviewee, and reviewer to test the storing
		// functionality of the database
		ReviewData review1 = new ReviewData();
		review1.storeID("AcDF");
		review1.putReviewee("BcDF");
		review1.putReviewer("CcDF");
		RevieweeData reviewee1 = new RevieweeData();
		reviewee1.storeID("BcDF");
		ReviewerData reviewer1 = new ReviewerData();
		reviewer1.storeID("CcDF");

		try {
			test.addReview(review1);
			fail("Excpetion expected");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}
		// Testing addReviewer
		test.addReviewer(reviewer1);
		assertTrue(test.getEntry("CcDF") != null);
		assertEquals("CcDF", test.getEntry("CcDF").getID());

		try {
			test.addReview(review1);
			fail("Excpetion expected");
		} catch (IllegalArgumentException e) {
			// Do nothing
		}

		// Testing addReviewee
		test.addReviewee(reviewee1);
		assertTrue(test.getEntry("BcDF") != null);
		assertEquals("BcDF", test.getEntry("BcDF").getID());

		try {
			test.addReview(review1);
		} catch (IllegalArgumentException e) {
			fail("Excpetion not expected");
		}
		assertTrue(test.getEntry("AcDF") != null);
		assertEquals("AcDF", test.getEntry("AcDF").getID());
	}

	@Test
	public void test6() {

		try {
			YelpDB test = new YelpDB("data/nested.json", "data/nestedRev.json", "data/nestedUser.json");

			assertEquals("[review_id: A, nested: {one=1, two=2, three=three}]",
					test.getEntry("A").allInfo().toString());
			assertEquals("[user_id: B, nested: {one=1, two=2, three=three}, friends: [[A, 1]]]",
					test.getEntry("B").allInfo().toString());
			assertEquals(
					"[business_id: C, nested: {one=1, two=2, three=three, true=true, false=false}, nestedTruth: false, array: [[cow, 1, false, true]]]",
					test.getEntry("C").allInfo().toString());

		} catch (IOException e) {
			fail("Exception not expected");
			// e.printStackTrace();
		}

	}

	// Test checks that constructor throws appropriate exceptions when required
	@Test
	public void test7() {

		try {
			YelpDB test = new YelpDB("", "data/nestedRev.json", "data/nestedUser.json");
			fail("Exception expected");

		} catch (IOException e) {
			// e.printStackTrace();
		}
		try {
			YelpDB test = new YelpDB("data/nested.json", "", "data/nestedUser.json");
			fail("Exception expected");

		} catch (IOException e) {
			// e.printStackTrace();
		}
		try {
			YelpDB test = new YelpDB("data/nested.json", "data/nestedRev.json", "");
			fail("Exception expected");

		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	@Test
	public void test8() {
		try {
			// Although input is invalid, the point of this is to see if the function keeps
			// the values within the valid range
			YelpDB test = new YelpDB("data/miniRestaurants2.json", "data/miniReviews2.json", "data/miniUsers2.json");
			// Suedo-testing parsing
			assertTrue(test.getEntry("_NH7Cpq3qZkByP5xR4gXog") != null);
			assertTrue(test.getEntry("fdsfadsByP5xR4gXog") == null);
			try {
				// Testing if getPredictorFunction returns the proper function
				assertEquals(5, test.getPredictorFunction("_NH7Cpq3qZkByP5xR4gXog").applyAsDouble(test,
						"gOp_w9qmLq6B8YRypTPp8g"), 0.0);
				assertEquals(5, test.getPredictorFunction("_NH7Cpq3qZkByP5xR4gXog").applyAsDouble(test,
						"gOp_w9qmLq6B8YRypTPp8h"), 0.0);
			} catch (IllegalArgumentException e) {
				fail("Exception not expected");
			}
		} catch (IOException e) {
			fail("Exception not expected");
		}
	}

	@Test
	public void test9() {
		try {
			// Although input is invalid, the point of this is to see if the function keeps
			// the values within the valid range
			YelpDB test = new YelpDB("data/miniRestaurants3.json", "data/miniReviews3.json", "data/miniUsers3.json");
			// Suedo-testing parsing
			assertTrue(test.getEntry("_NH7Cpq3qZkByP5xR4gXog") != null);
			assertTrue(test.getEntry("fdsfadsByP5xR4gXog") == null);
			try {
				// Testing if getPredictorFunction returns the proper function
				assertEquals(1, test.getPredictorFunction("_NH7Cpq3qZkByP5xR4gXog").applyAsDouble(test,
						"gOp_w9qmLq6B8YRypTPp8g"), 0.0);
				assertEquals(1, test.getPredictorFunction("_NH7Cpq3qZkByP5xR4gXog").applyAsDouble(test,
						"gOp_w9qmLq6B8YRypTPp8h"), 0.0);
			} catch (IllegalArgumentException e) {
				fail("Exception not expected");
			}
		} catch (IOException e) {
			fail("Exception not expected");
		}
	}

	@Test
	public void test10() {

		try {

			YelpDB test = new YelpDB("data/restaurants.json", "data/reviews.json", "data/users.json");
			ArrayList<Set> testResults = test.kMeansToDo(45);
			//String result = test.kMeansClusters_json(45);
			ArrayList<Coordinates> centroid = new ArrayList<Coordinates>();

			for (Set s : testResults) {
				if (s.isEmpty())
					fail("No empty clusters allowed");
				else {
					double X = 0;
					double Y = 0;
					for (Object points : s) {
						Coordinates a = new Coordinates((Coordinates) points);
						X += a.getX();
						Y += a.getY();
					}
					X = X / s.size();
					Y = Y / s.size();
					Coordinates centroids = new Coordinates(Integer.toString(testResults.indexOf(s)), X, Y);
					centroid.add(centroids);
				}
			}

			for (Set s : testResults) {
				for (Object points : s) {
					double minDistance = Integer.MAX_VALUE;
					int index = 0;

					Coordinates a = new Coordinates((Coordinates) points);

					for (Coordinates cent : centroid) {
						double distance = a.getDistance(cent);

						if (distance < minDistance) {
							minDistance = distance;
							index = centroid.indexOf(cent);
						}
					}
					if(s.size() > 1)
						assertEquals(index, testResults.indexOf(s));
					else
						continue;
				}
			}

			// System.out.println(result);

			// You cannot use fully assert here, because the helper method for
			// kMeansClusters produces
			// an arrayList of Sets, and sets don't preserve order, so order may be
			// different
			// each time you test. You have to do this by eye through visualizer. In
			// addition,
			// since starting centroids will be different each time, we will not always have
			// the
			// same clusterings

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
