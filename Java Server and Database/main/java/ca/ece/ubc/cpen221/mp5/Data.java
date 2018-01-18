package ca.ece.ubc.cpen221.mp5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class Data {
	// Rep invariant: Must have fields inside the info that represents the ID of the
	// data
	// Abstraction Function: Represents a chunk of data grouped together where each
	// entry in the HashMap specifies a property of the object that the data
	// describes.

	private LinkedHashMap<String, Object> info;
	private String Datatype;
	private String uniqueID;

	/**
	 * The Constructor
	 */
	public Data(String type) {
		this.info = new LinkedHashMap<String, Object>();
		this.Datatype = new String(type);
	}
	

	public String toJson() {
		
		String json = "{";
		
		for(String item: info.keySet()) {
			json += "\"" + item + "\": ";
			
			if(info.get(item) instanceof String)
				json +=  "\"" + info.get(item).toString() + "\"" + ", ";
			
			else if(info.get(item) instanceof LinkedHashMap) {
				json += "{";
				LinkedHashMap <String, Object> temp = (LinkedHashMap<String, Object>) info.get(item);
				
				for(String name:  temp.keySet()) {
					json += "\"" + name + "\": " + temp.get(name) + ", ";
				}
				
				if(temp.keySet().size() != 0 )
					json = json.substring(0,json.length()-2);
				
				json += "}, ";
			}
			else
				json += info.get(item).toString() + ", ";
		}
		
		json = json.substring(0,json.length()-2);
		json += "}";
		
		return json;
	}
	
	/**
	 * Stores the string as the unique ID of the data
	 * 
	 * @param ID
	 *            the unique ID string of the data
	 */
	public void storeID(String ID) {
		this.uniqueID = ID;
	}

	/**
	 * Gets the unique ID of the data
	 * 
	 * @return returns the unique ID of the data represented as a string
	 */
	public String getID() {
		return new String(uniqueID);
	}

	/**
	 * Stores the presented information into the HashMap that stores all the data
	 * 
	 * @param type
	 *            the type of data as a string that is going to be stored into the
	 *            object
	 * @param info
	 *            the information as a string that is to be stored
	 */
	public void storeInfo(String type, Object info) {
		
		this.info.put(type, info);
	}

	/**
	 * Gets the info of that type
	 * 
	 * @param type
	 *            the type of info you are looking for, cannot be null
	 * @return the info stored as a string
	 */
	public Object getInfo(String type) {

		Object info = this.info.get(type);

		return info;
	}

	public ArrayList<Object> allInfo() {

		ArrayList<Object> aa = new ArrayList<Object>();
		for (String a : info.keySet()) {
			aa.add(a + ": " + info.get(a).toString());
		}
		return aa;
	}

	/**
	 * Returns all the information inside the data as its own element even if it was
	 * contained inside a collection
	 * 
	 * @return an ArrayList of Strings containing the information
	 */
	private ArrayList<String> allInfoShort() {

		ArrayList<String> aa = new ArrayList<String>();

		for (String a : info.keySet()) {

			// If the data is some kind of collection, split every element of the of the
			// data into its own entry in the list
			if (info.get(a).toString().startsWith("[")) {
				String b = info.get(a).toString().replaceAll("\\[", "").replaceAll("\\]", "").trim();
				String[] c = b.split(",");

				// Cleaning the string
				for (int i = 0; i < c.length; i++) {
					c[i] = c[i].trim();
				}

				aa.addAll(Arrays.asList(c));
			} else {
				// If it isn't add it to the list
				aa.add(info.get(a).toString());
			}
		}
		return aa;
	}

	/**
	 * Gets the type of data
	 * 
	 * @return a string representation of the data's type
	 */
	public String getType() {
		return new String(this.Datatype);
	}

	/**
	 * Checks if the info matches the info that is stored inside the data
	 * 
	 * @param arg
	 *            the information type to be found in the data, cannot be null.
	 * @return true if the argument can be found and false otherwise
	 */
	public boolean matchesInfo(String info) {
		return allInfoShort().contains(info);
	}
	
	@Override
	public String toString() {
		return new String(uniqueID);
	}
}
