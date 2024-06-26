//Name:sama wahidee
//Id:1211503
//Section:1
package application;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LinkedList {
	private Node first;
	private Node last;
	private int count;

	public LinkedList() {

	}

	public int getCount() {
		return count;
	}

	// function to return the first node of the linked list
	public Node getfirst() {
		if (count != 0) {
			return first;
		}
		return null;
	}

	// function to return the last node of the linked list
	public Node getLast() {
		if (count != 0) {
			return last;
		}
		return null;

	}

	// function that add node into the head of the linked list that holds a cars
	// object
	public void addFirst(Martyr c) {
		if (count == 0) {
			first = last = new Node(c);
		} else {
			Node temp = new Node(c);
			temp.next = first;
			first = temp;
		}
		count++;
	}

	// function that add node into the tail of the linked list that holds a cars
	// object
	public void addLast(Martyr o) {
		if (count == 0) {
			first = last = new Node(o);
		} else {
			Node temp = new Node(o);
			temp.next = last;
			last = temp;
		}
		count++;
	}

	// function that add node after a specific index in the linked list that holds a
	// cars object
	public void add(Martyr c, int index) {
		if (index == 0) {
			addFirst(c);
		} else {
			if (index >= count) {
				addLast(c);
			} else {
				Node temp = new Node(c);
				Node current = first;
				for (int i = 0; i < index - 1; i++) {
					current = current.next;
				}
				temp.next = current.next;
				current.next = temp;
				count++;
			}
		}
	}

	// function that removes the first node of the linked list
	public boolean removeFirst() {
		if (count == 0) {
			return false;
		} else {
			if (count == 1) {
				first = last = null;
			} else {
				first = first.next;
			}
			count--;
			return true;

		}

	}

	// function that removes the last node of the linked list
	public boolean removeLast() {
		if (count == 0) {
			return false;
		} else {
			if (count == 1) {
				first = last = null;
			} else {
				Node current = first;
				for (int i = 1; i <= count - 2; i++) {
					current = current.next;
				}
				last = current;
				current.next = null;
			}
			count--;
			return true;

		}

	}

	// function that removes a specific node in the linked list due to it's index
	public boolean remove(int index) {
		if (count == 0) {
			return false;
		} else {
			if (index == 1) {
				return removeFirst();
			} else if (index == count) {
				return removeLast();

			} else if (index <= 0 || index > count) {
				return false;
			} else {
				Node current = first;
				for (int i = 0; i < index - 1; i++) {
					current = current.next;
				}
				current.next = (current.next).next;
				count--;
				return true;
			}
		}
	}

	// function that removes a specific node in the linked list due to it's cars
	// object
	public boolean remove(Martyr c) {
		if (count == 0) {
			return false;
		} else {
			if (first.element.equals(c)) {
				return removeFirst();
			} else {
				Node previous = first;
				Node current = first.next;
				while (current != null && !current.element.equals(getNode(c))) {
					previous = current;
					current = current.next;
				}
				if (current != null) {
					previous.next = current.next;
					count--;
					return true;
				} else {
					return false;
				}
			}
		}

	}

	// function that searchs for a specific node due to it's cars object name
	public boolean search(String m) {
		if (count == 0) {
			return false;
		} else {
			Node current = first;
			while (current != null && !(((Martyr) current.element).getName().equals(m))) {
				current = current.next;
			}
			if (current != null) {
				return true;
			}

		}
		return false;

	}

	// function that returns a specific node due to an object
	public Node getNode(Object o) {
		for (Node current = first; current != null; current = current.next) {
			// ((Cars) current.element).setUrl(((Cars)o).getUrl());
			Martyr c = ((Martyr) current.element);
			if (c.compareTo(((Martyr) o)) == 0) {
				return current;
			}
		}
		return null; // Object not found in the linked list
	}
}
