package application;

public class Stack {

	LinkedList list = new LinkedList();
	Node top;

	public Stack() {
	}

	public Node getTop() {
		return list.getfirst();
	}

	public void push(Martyr x) {
		list.addFirst(x);
	}

	public Node pop() {
		Node poped = list.getfirst();
		list.removeFirst();
		return poped;
	}

	public boolean isEmpty() {
		if (list.getfirst() == null) {
			return true;
		}
		return false;
	}

	public int size() {
		int x = 0;
		Stack temp = new Stack();
		while (!isEmpty()) {
			temp.push(pop().element);
			x++;
		}
		while (!(temp.isEmpty())) {
			push(temp.pop().element);
		}
		return x;
	}

	public StringBuilder PrintStack(String loc) {
		StringBuilder s = new StringBuilder();
		if (isEmpty())
			return s;
		Stack temp = new Stack();
		while (!isEmpty()) {
			Martyr x = null;
			if (getTop().element instanceof Martyr) {
				x = (Martyr) getTop().element;
			}
			temp.push(this.pop().element);
			s.append(x.getName() + ", " + x.getAge() + ", " + loc + ", " + x.getDateOfDeath() + ", " + x.getGender()
					+ "\n");
		}
		while (!temp.isEmpty()) {
			this.push(temp.pop().element);
		}
		return s;
	}

	public Node findNode(String s) {
		if (isEmpty())
			return null;

		Stack temp = new Stack();
		Node foundNode = null;

		while (!isEmpty()) {
			Node current = pop();
			if (s.compareTo(current.element.getName()) == 0) {
				foundNode = current;
				break;
			} else {
				temp.push(current.element);
			}
		}

		while (!temp.isEmpty()) {
			push(temp.pop().element);
		}

		return foundNode;
	}

	public Node removeNode(String s) {
		if (isEmpty())
			return null;
		Stack temp = new Stack();
		Node removedNode = null;
		while (!isEmpty()) {
			Node currentNode = pop();
			if (s.equals(currentNode.element.getName())) {
				removedNode = currentNode;
				break;
			} else {
				temp.push(currentNode.element);
			}
		}

		while (!temp.isEmpty()) {
			push(temp.pop().element);
		}

		return removedNode;
	}
}