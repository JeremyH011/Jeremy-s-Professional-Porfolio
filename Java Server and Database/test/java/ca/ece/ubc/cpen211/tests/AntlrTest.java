package ca.ece.ubc.cpen211.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.grammar.Parser;
import ca.ece.ubc.cpen221.mp5.grammar.QueFormatException;
import ca.ece.ubc.cpen221.mp5.grammar.ResFormatException;
import ca.ece.ubc.cpen221.mp5.grammar.RevFormatException;
import ca.ece.ubc.cpen221.mp5.grammar.UsFormatException;

public class AntlrTest {

	@Test
	public void test0() {
		Parser test;
		try {
			test = new Parser("GETRESTAURANT  avbcx fdsaf_dsa");
			// Ensures results are correct
			assertEquals("GETRESTAURANT", test.getCommand());
			assertEquals("avbcx fdsaf_dsa", test.getArugments());
		} catch (QueFormatException | ResFormatException | RevFormatException | UsFormatException e) {
			fail("Exception not expected");
			e.printStackTrace();
		}
		
	}

	@SuppressWarnings("unused")
	@Test
	public void test1() {
		try {
			Parser test = new Parser("GETRESTfUARANT  avbcx fdsaf_ds.a");
			fail("Exception expected");

		} catch (IllegalArgumentException | QueFormatException | ResFormatException | RevFormatException | UsFormatException e) {
			// Do nothing
		}
	}

	@Test
	public void test2() {
		Parser test;
		try {
			test = new Parser(
					"ADDREVIEW {\"test\":\"fdsfds_+-j f,  ,s.dfas_-dfasdf\", \"num\":2.3, \"teste\": 3, \"test4\":43}");
			// Ensures results are correct
			assertEquals("ADDREVIEW", test.getCommand());
			assertEquals("{\"test\":\"fdsfds_+-j f,  ,s.dfas_-dfasdf\",\"num\":2.3,\"teste\":3,\"test4\":43}",
					test.getJson());
		} catch (QueFormatException | ResFormatException | RevFormatException | UsFormatException e) {
			fail("Exception not expected");
		}

	}

	@Test
	public void test3() {
		Parser test;
		try {
			test = new Parser("QUERY in(Telegraph Ave) && (category(Chinese) || category(Italian)) && price <= 2");
			// Ensures results are correct
			assertEquals("QUERY", test.getCommand());
			assertEquals("((price<=2&&(categories(Italian)||categories(Chinese)))&&neighborhoods(Telegraph Ave))",
					test.getExpression().toString());
		} catch (QueFormatException | ResFormatException | RevFormatException | UsFormatException e) {
			fail("Exception not expected");
		}

	}

	@SuppressWarnings("unused")
	@Test
	public void test4() {
		try {
			Parser test = new Parser(
					"QUERY in(Telegraph Ave) & (category(Chinese) || category(Italian)) && price <= 2");
			fail("Exception expected");
		} catch (IllegalArgumentException | QueFormatException | ResFormatException | RevFormatException | UsFormatException e) {
			// Do nothing
		}

	}
	
	@SuppressWarnings("unused")
	@Test
	public void test5() {
		try {
			Parser test = new Parser(
					"ADDREVIEW {\"test\"::\"fdsfds_+-j f,  ,s.dfas_-dfasdf\", \"num\":2.3, \"teste\": 3, \"test4\":43}");
			fail("Exception expected");
		} catch (IllegalArgumentException | QueFormatException | ResFormatException | RevFormatException | UsFormatException e) {
			// Do nothing
		}

	}
	
	@SuppressWarnings("unused")
	@Test
	public void test6() {
		try {
			Parser test = new Parser(
					"ADDUSER {\"test\"::\"fdsfds_+-j f,  ,s.dfas_-dfasdf\", \"num\":2.3, \"teste\": 3, \"test4\":43}");
			fail("Exception expected");
		} catch (IllegalArgumentException | QueFormatException | ResFormatException | RevFormatException | UsFormatException e) {
			// Do nothing
		}

	}
	
	@SuppressWarnings("unused")
	@Test
	public void test7() {
		try {
			Parser test = new Parser(
					"ADDRESTAURANT {\"test\"::\"fdsfds_+-j f,  ,s.dfas_-dfasdf\", \"num\":2.3, \"teste\": 3, \"test4\":43}");
			fail("Exception expected");
		} catch (IllegalArgumentException | QueFormatException | ResFormatException | RevFormatException | UsFormatException e) {
			// Do nothing
		}

	}

}
