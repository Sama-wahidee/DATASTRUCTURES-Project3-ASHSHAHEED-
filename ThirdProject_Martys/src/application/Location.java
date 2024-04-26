package application;

public class Location {
	private String location;
	private AVL<Martyr> namesAVL;
	private AVL<DateStack> datesAVL;

	public Location(String location, AVL<Martyr> namesAVL, AVL<DateStack> datesAVL) {
		super();
		this.location = location;
		this.namesAVL = namesAVL;
		this.datesAVL = datesAVL;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public AVL<Martyr> getNamesAVL() {
		return namesAVL;
	}

	public void setNamesAVL(AVL<Martyr> namesAVL) {
		this.namesAVL = namesAVL;
	}

	public AVL<DateStack> getDatesAVL() {
		return datesAVL;
	}

	public void setDatesAVL(AVL<DateStack> datesAVL) {
		this.datesAVL = datesAVL;
	}

}
