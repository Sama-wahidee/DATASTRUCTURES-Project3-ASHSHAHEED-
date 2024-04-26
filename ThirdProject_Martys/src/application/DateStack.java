package application;

import java.util.Date;

public class DateStack implements Comparable<DateStack> {
	private Date dateOfDeath;
	private Stack stack;

	public DateStack(Date dateOfDeath, Stack stack) {
		super();
		this.dateOfDeath = dateOfDeath;
		this.stack = stack;
	}

	public Date getDateOfDeath() {
		return dateOfDeath;
	}

	public void setDateOfDeath(Date dateOfDeath) {
		this.dateOfDeath = dateOfDeath;
	}

	public Stack getStack() {
		return stack;
	}

	public void setStack(Stack stack) {
		this.stack = stack;
	}

	public int compareTo(DateStack o) {
		return this.dateOfDeath.compareTo(((DateStack) o).dateOfDeath);
	}
}
