package ca.ece.ubc.cpen221.mp5;

import java.util.ArrayList;
import java.util.Set;

public class Coordinates {
	// Rep Invariant: Each coordinate must have an id, an X value, and a Y value
	// None of these can be null or non-existant
	
	// Abstraction Function: Represents the coordinates of a certain object named
	// by its id
	
	private String id;
	private final double x;
	private final double y;
	
	/**
	 * Constructs a coordinate for an object, as in the X and Y-values on the cartesian plane
	 * 
	 * @param business_id is the name/id of this coordinate
	 * @param latitude	is the X-value
	 * @param longitude is the Y-value
	 */
	public Coordinates(String id, double latitude, double longitude) {
		this.id = new String(id);
		this.x = latitude;
		this.y = longitude;
	}
	
	/**
	 * Constructs a new coordinate that is equal to the one provided
	 * 
	 * @param other is a coordinate that this object will be equal to
	 */
	public Coordinates(Coordinates other) {
		this.id = new String(other.getName());
		this.x = other.getX();
		this.y = other.getY();
	}
	
	/**
	 * Finds the distance between two coordinates
	 * 
	 * @param other is the coordinate you wish to find distance to
	 * @return a double value which represents the distance between two coordinates. Distance is
	 * 			scalar
	 */
	public double getDistance(Coordinates other) {
		double xDifference = Math.pow(this.x - other.getX(),2);
		double yDifference = Math.pow(this.y - other.getY(),2);
		double difference = Math.pow(xDifference + yDifference, 0.5);
		
		return difference;
	}
	
	/**
	 * Takes a set of coordinates, and finds the two points in it that have the greatest distance.
	 * If there are multiple points of maximum distance, it will return the latest one found in
	 * the set
	 * 
	 * @param cluster is a set of coordinates you wish to find the two furthest points in
	 * 
	 * @return an ArrayList with the two coordinates that have furthest distance from each other
	 * in the set. They will be placed in index 0 and index 1. 
	 */
	public static ArrayList<Coordinates> furthestCornersOfGroup(Set<Coordinates> cluster){
		
		ArrayList<Coordinates> toTest = new ArrayList<Coordinates>(cluster);
		ArrayList<Coordinates> answer = new ArrayList<Coordinates>();
		double largestDistance = 0;
		
		for(int index = 0; index < toTest.size(); index++) {
			Coordinates start = new Coordinates(toTest.get(index));
			
			for(int index2 = index + 1; index2 < toTest.size(); index2++) {
				Coordinates end = new Coordinates(toTest.get(index2));
				double distance = start.getDistance(end);
				
				if(distance >= largestDistance) {
					answer.clear();
					largestDistance = distance;
					answer.add(start);
					answer.add(end);
				}
			}
		}
		return answer;
	}
	
	/**
	 * Gives a string representation of this Coordinate, include id, X-value, and Y-value
	 * 
	 * @return a string representation of this coordinate
	 */
	public String toString() {
		return new String("id: " + this.id + " x: " + this.x + " " + "y: " + this.y);
	}
	
	/**
	 * Represents the X-value of this coordinate, as in the X-value on the cartesian plane
	 * 
	 * @return a double rep of this coordinate's X-value
	 */
	public double getX() {
		double x = this.x;
		return x;
	}
	
	/**
	 * Represents the Y value of this coordinate, as in the Y value on the cartesian plane
	 * 
	 * @return a double rep of this coordinate's Y-value
	 */
	public double getY() {
		double y = this.y;
		return y;
	}
	
	@Override
	public boolean equals(Object other) {
		boolean truth = false;
		if (!(other instanceof Coordinates))
			return truth;
		else {
			Coordinates check = new Coordinates ((Coordinates) other);
			truth = (this.x == check.getX()) && (this.y == check.getY()
					&& this.id.equals(check.getName()));
		}
		return truth;
	}
	
	@Override
	public int hashCode() {
		return (int) Math.round((this.x + this.y));
	}
	
	/**
	 * Gives the id of the function
	 * 
	 * @return String representation of this coordinates id
	 */
	public String getName() {
		return new String(this.id);
	}
}
