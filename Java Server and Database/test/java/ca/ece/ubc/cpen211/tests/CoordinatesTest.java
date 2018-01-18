package ca.ece.ubc.cpen211.tests;

import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.Coordinates;

public class CoordinatesTest {
	
	//Functionality test of toString and distance methods
	@Test
	public void test0() {
		Coordinates A = new Coordinates("A",1,2);
		Coordinates B = new Coordinates("B",5,7);
		assertEquals("id: A x: 1.0 y: 2.0",A.toString());
		double ans = Math.pow(41, 0.5);
		assertTrue(B.getDistance(A) == ans);
	}
	
	//Equality check
	@Test
	public void test1() {
		Coordinates A = new Coordinates("A",1,2);
		Coordinates B = new Coordinates(A);
		Coordinates C = new Coordinates("A",2,1);
		assertEquals(A,B);
		assertTrue(A.hashCode() == B.hashCode());
		assertFalse(A.equals(B.toString()));
		assertFalse(A.equals(C));
	}
	
	//Tests static function to find the two coordinates with the largest distance in between them
	@Test
	public void test2() {
		Coordinates A = new Coordinates("A",1,1);
		Coordinates B = new Coordinates(A);
		Coordinates C = new Coordinates("A",-2,1);
		Coordinates D = new Coordinates("D", 100, 1);
		HashSet<Coordinates> test = new HashSet<Coordinates>();
		test.add(A);
		test.add(B);
		test.add(C);
		test.add(D);
		
		Coordinates A2 = new Coordinates("A",-5,1);
		Coordinates B2 = new Coordinates(A);
		Coordinates C2 = new Coordinates("A",-2,1);
		Coordinates D2 = new Coordinates("D", 100, 1);
		HashSet<Coordinates> test2 = new HashSet<Coordinates>();
		test2.add(A2);
		test2.add(B2);
		test2.add(C2);
		test2.add(D2);
		
		assertEquals("[id: A x: -2.0 y: 1.0, id: D x: 100.0 y: 1.0]",
				Coordinates.furthestCornersOfGroup(test).toString());
		
		assertEquals("[id: A x: -5.0 y: 1.0, id: D x: 100.0 y: 1.0]",
				Coordinates.furthestCornersOfGroup(test2).toString());
	}
}
