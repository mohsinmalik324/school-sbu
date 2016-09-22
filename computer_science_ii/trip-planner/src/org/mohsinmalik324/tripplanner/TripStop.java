package org.mohsinmalik324.tripplanner;

/**
 * <code>TripStop</code> represents a stop on a trip.
 * 
 * @author Mohsin Malik
 *    <dd>Email: mohsin.malik@stonybrook.edu
 *    <dd>Stony Brook ID: 110880864
 */
public class TripStop {
	
	private String location;
	private String activity;
	private int distance;
	
	/**
	 * Returns an instance of <code>TripStop</code>.
	 */
	public TripStop() {
		this(null, null, 0);
	}
	
	/**
	 * Returns an instance of <code>TripStop</code>.
	 * 
	 * @param location
	 *    The location of the stop.
	 *    
	 * @param activity
	 *    The activity associated with the stop.
	 *    
	 * @param distance
	 *    The distance to travel.
	 */
	public TripStop(String location, String activity, int distance) {
		setLocation(location);
		setActivity(activity);
		setDistance(distance);
	}
	
	/**
	 * Sets a new location.
	 * 
	 * @param location
	 *    The new location.
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
	 * Sets a new activity.
	 * 
	 * @param activity
	 *    The new activity.
	 */
	public void setActivity(String activity) {
		this.activity = activity;
	}
	
	/**
	 * Sets a new distance.
	 * 
	 * @param distance
	 *    The new distance.
	 *    
	 * <dt>Precondition:
	 *    <dd><code>price</code> must be greater than 0.
	 * 
	 * @throws IllegalArgumentException
	 *    <code>price</code> was not greater than 0.
	 */
	public void setDistance(int distance) throws IllegalArgumentException {
		if(distance < 0) {
			throw new IllegalArgumentException("Distance must be "
			  + "greater than 0.");
		}
		this.distance = distance;
	}
	
	/**
	 * Returns the location.
	 * 
	 * @return
	 *    The location.
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Returns the activity.
	 * 
	 * @return
	 *    The activity.
	 */
	public String getActivity() {
		return activity;
	}
	
	/**
	 * Returns the distance.
	 * 
	 * @return
	 *    The distance.
	 */
	public int getDistance() {
		return distance;
	}
	
	/**
	 * Returns a formatted row of the information in this trip stop.
	 * 
	 * @return
	 *    A formatted row of the information in this trip stop.
	 */
	public String toString() {
		return String.format("%-25s%-75s%12s\n",
		  location, activity, distance + " mile" +
		  (distance != 1 ? "s" : " "));
	}
	
	/**
	 * Returns a deep clone of this trip stop.
	 * 
	 * @return
	 *    A deep clone of this trip stop.
	 */
	public Object clone() {
		return new TripStop(location, activity, distance);
	}
	
}