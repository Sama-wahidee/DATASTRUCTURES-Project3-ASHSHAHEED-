//Name:sama wahidee
//Id:1211503
//Section:1
package application;

public class DoubleNode {
	Location location;
	DoubleNode next;
	DoubleNode previous;
//Double linked list node Constructor 
	public DoubleNode(Location location) {
		this.location = location;
		}

	// getter for location
	public Location getLocation() {
		return location;
	}

	// setter for location
	public void setLocation(Location location) {
		this.location = location;
	}

}
