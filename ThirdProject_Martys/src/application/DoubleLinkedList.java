//Name:sama wahidee
//Id:1211503
//Section:1
package application;

public class DoubleLinkedList {
	private DoubleNode first;
	private DoubleNode last;
	int count;

	public DoubleLinkedList() {
	}

	// function to return the first node of the linked list
	public DoubleNode getfirst() {
		if (count != 0) {
			return first;
		}
		return null;

	}

	// function to return the last node of the linked list
	public DoubleNode getLast() {
		if (count != 0) {
			return last;
		}
		return null;

	}

	public void addFirst(Location s) {
		if (count == 0) {
			first = last = new DoubleNode(s);
			first.next = first;
			first.previous = first;
		} else {
			DoubleNode temp = new DoubleNode(s);
			temp.next = first;
			temp.previous = last;
			first.previous = temp;
			last.next = temp;
			first = temp;
		}
		count++;
	}

	// function that adds a double node into the tail of the double linked list
	public void addLast(Location s) {
		if (count == 0) {
			first = last = new DoubleNode(s);
			first.next = first;
			first.previous = first;
		} else {
			DoubleNode temp = new DoubleNode(s);
			temp.next = first;
			temp.previous = last;
			last.next = temp;
			first.previous = temp;
			last = temp;
		}
		count++;
	}

	// function that adds a double node after a specific index in the double linked
	// list
	public void add(Location s, int index) {
		if (index == 0) {
			addFirst(s);
		} else {
			if (index >= count) {
				addLast(s);
			} else {
				DoubleNode temp = new DoubleNode(s);
				DoubleNode current = first;
				for (int i = 0; i < index - 1; i++) {
					current = current.next;
				}
				temp.next = current.next;
				temp.previous = current;
				current.next.previous = temp;
				current.next = temp;
				count++;
			}
		}
	}

	// function that removes the first doublenode of the linked list
	public boolean removeFirst() {
		if (count == 0) {
			return false;
		} else {
			if (count == 1) {
				first = last = null;
			} else {
				first = first.next;
				first.previous = last;
				last.next = first;
			}
			count--;
			return true;

		}

	}

	// function that removes the last doublenode of the linked list
	public boolean removeLast() {
		if (count == 0) {
			return false;
		} else {
			if (count == 1) {
				first = last = null;
			} else {
				DoubleNode current = first;
				for (int i = 1; i <= count - 2; i++) {
					current = current.next;

				}
				last = current;
				current.next = first;
				first.previous = last;
			}
			count--;
			return true;

		}

	}

	// function that removes a specific doublenode in the linked list due to it's
	// object
	public boolean remove(String o) {
		if (count == 0) {
			return false;
		} else {
			if (first.location.getLocation().equals(o)) {
				return removeFirst();
			} else {
				DoubleNode current = first;
				for (int i = 1; i < count; i++) {
					current = current.next;
					if (current.location.getLocation().equals(o)) {
						current.previous.next = current.next;
						current.next.previous = current.previous;
						count--;
						return true;
					}
				}
				return false;
			}
		}
	}

	// function that searches for a specific doublenode due to it's location
	public boolean search(String l) {
		if (count == 0) {
			return false;
		} else {
			DoubleNode current = first;
			int i = 1;
			while ((i <= count) && (!(current.location.getLocation().equals(l)))) {
				current = current.next;
				i++;
			}
			if (i <= count) {
				return true;
			}

		}
		return false;

	}

	// function that returns a specific doublenode due to it's martyr object
	public DoubleNode getNode(String o) {
		if (count == 0) {
			return null;
		} else {
			DoubleNode current = first;
			int i = 1;
			while ((i <= count) && (!(current.location.getLocation().equals(o)))) {
				current = current.next;
				i++;
			}
			if (i <= count) {
				return current;
			}
		}
		return null;

	}

	// function to update the location
	public void update(DoubleNode old, String neww) {
		old.location.setLocation(neww);
	}

	// function to print the double linkedlist
	public void PrintList() {
		if (count > 0) {
			DoubleNode current = first;
			int i = 1;
			while (i <= count) {
				System.out.println(current.location.getLocation());
				current = current.next;
				i++;
			}

		} else {
			System.out.println("The list is empty!!");
		}
	}

	public void sort() {
		DoubleNode t, s;
		int v, i;
		if (first == last && first == null) {
			System.out.println("The List is empty, nothing to sort");
			return;
		}
		s = first;
		for (i = 0; i < count; i++) {
			t = s.next;
			while (t != first) {
				if (s.location.getLocation().compareTo(t.location.getLocation()) < 0) {
					// Swap the nodes
					DoubleNode temp = new DoubleNode(s.location);
					s.location = t.location;
					t.location = temp.location;
				}
				t = t.next;
			}
			s = s.next;
		}
		System.out.println("List sorted");
	}
}
