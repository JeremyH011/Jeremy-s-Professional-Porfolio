package ca.ece.ubc.cpen211.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashSet;

import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.RevieweeData;
import ca.ece.ubc.cpen221.mp5.YelpDB;
import ca.ece.ubc.cpen221.mp5.expression.AndExpression;
import ca.ece.ubc.cpen221.mp5.expression.IneqExpression;
import ca.ece.ubc.cpen221.mp5.expression.OrExpression;
import ca.ece.ubc.cpen221.mp5.expression.UnaryExpression;

public class ExpressionTest {

	@Test
	public void test0() {
		try {
			// Testing inequality expression with price
			YelpDB test = new YelpDB("data/miniRestaurants.json", "data/miniReviews.json", "data/miniUsers.json");
			
			assertEquals("gclB3ED6uk6viWlolSb_uA", test.getEntry("gclB3ED6uk6viWlolSb_uA").toString());
			
			IneqExpression result1 = new IneqExpression("=", "price", 1);
			HashSet<RevieweeData> test1 = new HashSet<>();
			test1.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			assertEquals(test1, result1.eval(test));
			
			IneqExpression result2 = new IneqExpression(">=", "price", 1);
			HashSet<RevieweeData> test2 = new HashSet<>();
			test2.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			test2.add((RevieweeData) test.getEntry("BJKIoQa5N2T_oDlLVf467Q"));
			test2.add((RevieweeData) test.getEntry("h_we4E3zofRTf4G0JTEF0A"));
			test2.add((RevieweeData) test.getEntry("FWadSZw0G7HsgKXq7gHTnw"));
			test2.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8g"));
			test2.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8h"));
			assertEquals(test2, result2.eval(test));

			IneqExpression result3 = new IneqExpression(">", "price", 1);
			HashSet<RevieweeData> test3 = new HashSet<>();
			test3.add((RevieweeData) test.getEntry("BJKIoQa5N2T_oDlLVf467Q"));
			test3.add((RevieweeData) test.getEntry("h_we4E3zofRTf4G0JTEF0A"));
			test3.add((RevieweeData) test.getEntry("FWadSZw0G7HsgKXq7gHTnw"));
			test3.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8g"));
			test3.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8h"));
			assertEquals(test3, result3.eval(test));
			
			IneqExpression result4 = new IneqExpression("<=", "price", 1);
			HashSet<RevieweeData> test4 = new HashSet<>();
			test4.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			assertEquals(test4, result4.eval(test));
			
			IneqExpression result5 = new IneqExpression("<", "price", 1);
			HashSet<RevieweeData> test5 = new HashSet<>();
			assertEquals(test5, result5.eval(test));
		} catch (IOException e) {
			fail("Exception not expected");
		}
	}

	@Test
	public void test1() {
		try {
			// Testing inequality expression with rating
			YelpDB test = new YelpDB("data/miniRestaurants.json", "data/miniReviews.json", "data/miniUsers.json");

			IneqExpression result1 = new IneqExpression("=", "rating", 2);
			HashSet<RevieweeData> test1 = new HashSet<>();
			test1.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			assertEquals(test1, result1.eval(test));

			IneqExpression result2 = new IneqExpression(">=", "rating", 2);
			HashSet<RevieweeData> test2 = new HashSet<>();
			test2.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			test2.add((RevieweeData) test.getEntry("BJKIoQa5N2T_oDlLVf467Q"));
			test2.add((RevieweeData) test.getEntry("h_we4E3zofRTf4G0JTEF0A"));
			test2.add((RevieweeData) test.getEntry("FWadSZw0G7HsgKXq7gHTnw"));
			test2.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8g"));
			test2.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8h"));
			assertEquals(test2, result2.eval(test));

			IneqExpression result3 = new IneqExpression(">", "rating", 2);
			HashSet<RevieweeData> test3 = new HashSet<>();
			test3.add((RevieweeData) test.getEntry("BJKIoQa5N2T_oDlLVf467Q"));
			test3.add((RevieweeData) test.getEntry("h_we4E3zofRTf4G0JTEF0A"));
			test3.add((RevieweeData) test.getEntry("FWadSZw0G7HsgKXq7gHTnw"));
			test3.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8g"));
			test3.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8h"));
			assertEquals(test3, result3.eval(test));
			
			IneqExpression result4 = new IneqExpression("<=", "rating", 2);
			HashSet<RevieweeData> test4 = new HashSet<>();
			test4.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			assertEquals(test4, result4.eval(test));
			
			IneqExpression result5 = new IneqExpression("<", "rating", 2);
			HashSet<RevieweeData> test5 = new HashSet<>();
			assertEquals(test5, result5.eval(test));
		} catch (IOException e) {
			fail("Exception not expected");
		}
	}

	@Test
	public void test2() {
		try {
			// Testing unaryExpression
			YelpDB test = new YelpDB("data/miniRestaurants.json", "data/miniReviews.json", "data/miniUsers.json");
			UnaryExpression result1 = new UnaryExpression("name", "Cafe 3");
			HashSet<RevieweeData> test1 = new HashSet<>();
			test1.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			assertEquals(test1, result1.eval(test));

			UnaryExpression result2 = new UnaryExpression("type", "business");
			HashSet<RevieweeData> test2 = new HashSet<>();
			test2.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			test2.add((RevieweeData) test.getEntry("BJKIoQa5N2T_oDlLVf467Q"));
			test2.add((RevieweeData) test.getEntry("h_we4E3zofRTf4G0JTEF0A"));
			test2.add((RevieweeData) test.getEntry("FWadSZw0G7HsgKXq7gHTnw"));
			test2.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8g"));
			test2.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8h"));
			assertEquals(test2, result2.eval(test));
		} catch (IOException e) {
			fail("Exception not expected");
		}
	}
	
	@SuppressWarnings("unused")
	@Test
	public void test4() {
		try {
			// Testing AndExpression
			YelpDB test = new YelpDB("data/miniRestaurants.json", "data/miniReviews.json", "data/miniUsers.json");
			
			// Setting up data
			IneqExpression result1 = new IneqExpression("=", "price", 1);
			HashSet<RevieweeData> test1 = new HashSet<>();
			test1.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			
			IneqExpression result2 = new IneqExpression(">=", "price", 1);
			HashSet<RevieweeData> test2 = new HashSet<>();
			test2.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			test2.add((RevieweeData) test.getEntry("BJKIoQa5N2T_oDlLVf467Q"));
			test2.add((RevieweeData) test.getEntry("h_we4E3zofRTf4G0JTEF0A"));
			test2.add((RevieweeData) test.getEntry("FWadSZw0G7HsgKXq7gHTnw"));
			test2.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8g"));
			test2.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8h"));

			IneqExpression result3 = new IneqExpression(">", "price", 1);
			HashSet<RevieweeData> test3 = new HashSet<>();
			test3.add((RevieweeData) test.getEntry("BJKIoQa5N2T_oDlLVf467Q"));
			test3.add((RevieweeData) test.getEntry("h_we4E3zofRTf4G0JTEF0A"));
			test3.add((RevieweeData) test.getEntry("FWadSZw0G7HsgKXq7gHTnw"));
			test3.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8g"));
			test3.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8h"));
			
			IneqExpression result4 = new IneqExpression("<=", "price", 1);
			HashSet<RevieweeData> test4 = new HashSet<>();
			test4.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			
			IneqExpression result5 = new IneqExpression("<", "price", 1);
			HashSet<RevieweeData> test5 = new HashSet<>();
			
			// Checking results
			AndExpression and1 = new AndExpression(result1, result5);
			assertEquals(test5, and1.eval(test));
			
			AndExpression and2 = new AndExpression(result1, result2);
			assertEquals(test1, and2.eval(test));
			
			AndExpression and3 = new AndExpression(result3, result2);
			assertEquals(test3, and3.eval(test));
		} catch (IOException e) {
			fail("Exception not expected");
		}
	}

	@SuppressWarnings("unused")
	@Test
	public void test5() {
		try {
			// Testing AndExpression
			YelpDB test = new YelpDB("data/miniRestaurants.json", "data/miniReviews.json", "data/miniUsers.json");
			
			// Setting up data
			IneqExpression result1 = new IneqExpression("=", "price", 1);
			HashSet<RevieweeData> test1 = new HashSet<>();
			test1.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			
			IneqExpression result2 = new IneqExpression(">=", "price", 1);
			HashSet<RevieweeData> test2 = new HashSet<>();
			test2.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			test2.add((RevieweeData) test.getEntry("BJKIoQa5N2T_oDlLVf467Q"));
			test2.add((RevieweeData) test.getEntry("h_we4E3zofRTf4G0JTEF0A"));
			test2.add((RevieweeData) test.getEntry("FWadSZw0G7HsgKXq7gHTnw"));
			test2.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8g"));
			test2.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8h"));

			IneqExpression result3 = new IneqExpression(">", "price", 1);
			HashSet<RevieweeData> test3 = new HashSet<>();
			test3.add((RevieweeData) test.getEntry("BJKIoQa5N2T_oDlLVf467Q"));
			test3.add((RevieweeData) test.getEntry("h_we4E3zofRTf4G0JTEF0A"));
			test3.add((RevieweeData) test.getEntry("FWadSZw0G7HsgKXq7gHTnw"));
			test3.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8g"));
			test3.add((RevieweeData) test.getEntry("gOp_w9qmLq6B8YRypTPp8h"));
			
			IneqExpression result4 = new IneqExpression("<=", "price", 1);
			HashSet<RevieweeData> test4 = new HashSet<>();
			test4.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			
			IneqExpression result5 = new IneqExpression("<", "price", 1);
			HashSet<RevieweeData> test5 = new HashSet<>();
			
			// Checking results
			OrExpression or1 = new OrExpression(result1, result5);
			assertEquals(test1, or1.eval(test));
			
			OrExpression or2 = new OrExpression(result1, result2);
			assertEquals(test2, or2.eval(test));
			
			OrExpression or3 = new OrExpression(result3, result2);
			assertEquals(test2, or3.eval(test));
		} catch (IOException e) {
			fail("Exception not expected");
		}
	}
	
	@Test
	public void test6() {
		try {
			// Testing AndExpression
			YelpDB test = new YelpDB("data/miniRestaurants.json", "data/miniReviews.json", "data/miniUsers.json");
			
			// Setting up data
			UnaryExpression result1 = new UnaryExpression("neighborhoods", "Telegraph Ave");
			HashSet<RevieweeData> test1 = new HashSet<>();
			test1.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			
			assertEquals(test1, result1.eval(test));
		} catch (IOException e) {
			fail("Exception not expected");
		}
	}
	
	@Test
	public void test7() {
		try {
			// Testing AndExpression
			YelpDB test = new YelpDB("data/miniRestaurants.json", "data/miniReviews.json", "data/miniUsers.json");
			
			// Setting up data
			UnaryExpression result1 = new UnaryExpression("categories", "Telegraph Ave");
			HashSet<RevieweeData> test1 = new HashSet<>();
			
			assertEquals(test1, result1.eval(test));
		} catch (IOException e) {
			fail("Exception not expected");
		}
	}
	
	@Test
	public void test8() {
		try {
			// Testing AndExpression
			YelpDB test = new YelpDB("data/miniRestaurants.json", "data/miniReviews.json", "data/miniUsers.json");
			
			// Setting up data
			UnaryExpression result1 = new UnaryExpression("categories", "Cafes");
			HashSet<RevieweeData> test1 = new HashSet<>();
			test1.add((RevieweeData) test.getEntry("gclB3ED6uk6viWlolSb_uA"));
			
			assertEquals(test1, result1.eval(test));
		} catch (IOException e) {
			fail("Exception not expected");
		}
	}
}
