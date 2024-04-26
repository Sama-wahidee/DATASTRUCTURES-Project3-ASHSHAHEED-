package application;

import java.util.Date;

public class AVL<T extends Comparable<T>> {
	private AVLNode<T> root;
	int maxDate;
	Date max = new Date();

	public AVL() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	public AVLNode<Martyr> findByName(String s) {
		return findByName(s, root);
	}

	private AVLNode<Martyr> findByName(String s, AVLNode<T> t) {
		if (t == null) {
			return null;
		}
		int compareResult = s.compareTo(((Martyr) t.data).getName());
		if (compareResult < 0) {
			return findByName(s, t.left);
		} else if (compareResult > 0) {
			return findByName(s, t.right);
		}
		return (AVLNode<Martyr>) t;
	}

	public AVLNode<DateStack> findByDate(Date date) {
		return findByDate(date, root);
	}

	private AVLNode<DateStack> findByDate(Date date, AVLNode<T> t) {
		if (t == null) {
			return null;
		}
		if (t.data instanceof DateStack) {
			Date temp = ((DateStack) t.data).getDateOfDeath();
			int compareResult = date.compareTo(temp);
			if (compareResult < 0) {
				return findByDate(date, t.left);
			} else if (compareResult > 0) {
				return findByDate(date, t.right);
			}
			return (AVLNode<DateStack>) t;
		} else {
			return null;
		}
	}

	public int height() {
		return height(root);
	}

	private int height(AVLNode<T> e) {
		if (e == null) {
			return -1;
		}
		return e.height;
	}

	private AVLNode<T> rotateWithLeftChild(AVLNode<T> k2) {
		AVLNode<T> k1 = k2.left;
		k2.left = k1.right;
		k1.right = k2;
		k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
		k1.height = Math.max(height(k1.left), k2.height) + 1;
		return k1;
	}

	private AVLNode<T> rotateWithRightChild(AVLNode<T> k1) {
		AVLNode<T> k2 = k1.right;
		k1.right = k2.left;
		k2.left = k1;
		k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
		k2.height = Math.max(height(k2.right), k1.height) + 1;
		return k2;
	}

	private AVLNode<T> doubleWithLeftChild(AVLNode<T> k3) {
		k3.left = rotateWithRightChild(k3.left);
		return rotateWithLeftChild(k3);
	}

	private AVLNode<T> doubleWithRightChild(AVLNode<T> k1) {
		k1.right = rotateWithLeftChild(k1.right);
		return rotateWithRightChild(k1);
	}

	public void insertByName(Martyr data) {
		root = insertByName(root, (T) data);
	}

	private AVLNode<T> insertByName(AVLNode<T> node, T data) {
		if (node == null) {
			return new AVLNode<>(data);
		}

		int compareResult = ((Martyr) data).getName().compareTo(((Martyr) node.data).getName());
		if (compareResult < 0) {
			node.left = insertByName(node.left, data);
		} else if (compareResult > 0) {
			node.right = insertByName(node.right, data);
		} else {
			// Duplicate keys are not allowed in AVL tree
			return node;
		}

		node.height = 1 + Math.max(height(node.left), height(node.right));
		int balance = getBalance(node);

		if (balance > 1 && ((Martyr) data).getName().compareTo(((Martyr) node.left.data).getName()) < 0) {
			return rotateWithLeftChild(node);
		}

		if (balance < -1 && ((Martyr) data).getName().compareTo(((Martyr) node.right.data).getName()) > 0) {
			return rotateWithRightChild(node);
		}

		if (balance > 1 && ((Martyr) data).getName().compareTo(((Martyr) node.left.data).getName()) > 0) {
			return doubleWithLeftChild(node);
		}

		if (balance < -1 && ((Martyr) data).getName().compareTo(((Martyr) node.right.data).getName()) < 0) {
			return doubleWithRightChild(node);
		}

		return node;
	}

	public boolean insertByDate(DateStack data) {

		root = insertByDate(root, (T) data);
		if (root == null) {
			return false;
		}
		return true;
	}

	private AVLNode<T> insertByDate(AVLNode<T> node, T data) {
		if (node == null) {
			return new AVLNode<>(data);
		}

		int compareResult = ((DateStack) data).getDateOfDeath().compareTo(((DateStack) node.data).getDateOfDeath());
		if (compareResult < 0) {
			node.left = insertByDate(node.left, data);
		} else if (compareResult > 0) {
			node.right = insertByDate(node.right, data);
		} else {
			// Duplicate keys are not allowed in AVL tree
			return node;
		}

		node.height = 1 + Math.max(height(node.left), height(node.right));
		int balance = getBalance(node);

		if (balance > 1
				&& (((DateStack) data).getDateOfDeath().compareTo(((DateStack) node.left.data).getDateOfDeath()) < 0)) {
			return rotateWithLeftChild(node);
		}

		if (balance < -1
				&& ((DateStack) data).getDateOfDeath().compareTo(((DateStack) node.right.data).getDateOfDeath()) > 0) {
			return rotateWithRightChild(node);
		}

		if (balance > 1
				&& ((DateStack) data).getDateOfDeath().compareTo(((DateStack) node.left.data).getDateOfDeath()) > 0) {
			return doubleWithLeftChild(node);
		}

		if (balance < -1
				&& ((DateStack) data).getDateOfDeath().compareTo(((DateStack) node.right.data).getDateOfDeath()) < 0) {
			return doubleWithRightChild(node);
		}

		return node;
	}

	private int getBalance(AVLNode<T> node) {
		if (node == null) {
			return 0;
		}
		return height(node.left) - height(node.right);
	}

	public AVLNode<T> delete(T info) {
		root = delete(root, info);
		return root;
	}

	private AVLNode<T> delete(AVLNode<T> t, T info) {
		if (t == null) {
			System.out.println("Element not found!");
			return null;
		}

		int compareResult = info.compareTo(t.data);
		if (compareResult < 0) {
			t.left = delete(t.left, info);
		} else if (compareResult > 0) {
			t.right = delete(t.right, info);
		} else {
			if (t.left == null) {
				return t.right;
			} else if (t.right == null) {
				return t.left;
			}

			AVLNode<T> temp = findMin(t.right);
			t.data = temp.data;
			t.right = delete(t.right, t.data);
		}

		// Update the height and balance factor
		t.height = 1 + Math.max(height(t.left), height(t.right));
		int balance = getBalance(t);

		if (balance > 1 && getBalance(t.left) >= 0) {
			return rotateWithLeftChild(t);
		}

		if (balance > 1 && getBalance(t.left) < 0) {
			return doubleWithLeftChild(t);
		}

		if (balance < -1 && getBalance(t.right) <= 0) {
			return rotateWithRightChild(t);
		}

		if (balance < -1 && getBalance(t.right) > 0) {
			return doubleWithRightChild(t);
		}

		return t;
	}

	private AVLNode<T> findMin(AVLNode<T> node) {
		while (node.left != null) {
			node = node.left;
		}
		return node;
	}

	public AVLNode<T> findMin() {
		return findMin(root);
	}

	public AVLNode<T> findMax() {
		AVLNode<T> t = root;
		if (t != null) {
			while (t.right != null) {
				t = t.right;
			}
		}
		return t;
	}

	public void inorder() {
		inorder(root);
	}

	private void inorder(AVLNode<T> r) {
		if (r != null) {
			inorder(r.left);
			System.out.println(((Martyr) r.data).getName() + " ");
			inorder(r.right);
		}
	}

	public void preorder() {
		preorder(root);
	}

	private void preorder(AVLNode<T> r) {
		if (r != null) {
			System.out.print(r.data + " ");
			preorder(r.left);
			preorder(r.right);
		}
	}

	public void postorder() {
		postorder(root);
	}

	private void postorder(AVLNode<T> r) {
		if (r != null) {
			postorder(r.left);
			postorder(r.right);
			System.out.print(r.data + " ");
		}
	}

	public int countNodes() {
		return countNodes(root);
	}

	private int countNodes(AVLNode<T> r) {
		if (r == null) {
			return 0;
		} else {
			int l = 1;
			l += countNodes(r.left);
			l += countNodes(r.right);
			return l;
		}
	}

	public int countLeaves() {
		return countLeaves(root);
	}

	private int countLeaves(AVLNode<T> r) {
		if (r == null) {
			return 0;
		} else {
			if (isLeaf(r)) {
				return 1;
			}

			int leftCount = countLeaves(r.left);
			int rightCount = countLeaves(r.right);

			return leftCount + rightCount;
		}
	}

	public boolean isLeaf(AVLNode<T> node) {
		return (node != null && node.left == null && node.right == null);
	}

	public StringBuilder printLevelOrder(String location) {
		StringBuilder sb = new StringBuilder();
		int h = height(root);
		int i;
		for (i = 1; i <= h; i++) {
			sb.append(printCurrentLevel(root, i, location));
		}
		return sb;
	}
//print the current 
	private String printCurrentLevel(AVLNode<T> root, int level, String location) {
		if (root == null) {
			return ""; 
		}
		if (level == 1) {
			return (((Martyr) root.data).getName() + ", " + ((Martyr) root.data).getAge() + ", " + location + ", "
					+ ((Martyr) root.data).getDateOfDeath() + ", " + ((Martyr) root.data).getGender() + ". \n");
		} else if (level > 1) {
			String left = printCurrentLevel(root.left, level - 1, location);
			String right = printCurrentLevel(root.right, level - 1, location);
			return left + right; // Concatenate the results of left and right subtrees
		}
		return "";
	}

	private StringBuilder printDescendingOrder(StringBuilder sb, AVLNode<T> node, String loc) {
		if (node != null) {
			printDescendingOrder(sb, node.right, loc);
			sb.append(((DateStack) node.data).getStack().PrintStack(loc));
			if (((DateStack) node.data).getStack().size() >= maxDate) {
				maxDate = ((DateStack) node.data).getStack().size();
				max = ((DateStack) node.data).getDateOfDeath();
			}
			printDescendingOrder(sb, node.left, loc);
		}
		return sb;
	}

	public StringBuilder printDescendingOrder(String loc) {
		return printDescendingOrder(new StringBuilder(), root, loc);
	}

	public String maxDate() {
		return "\n" + max + ", There was about " + maxDate + " martyr.";
	}

	public StringBuilder searchByPartialName(String partialName, String loc) {
		StringBuilder sb = new StringBuilder();
		return searchByPartialName(root, partialName, sb, loc);
	}

	private StringBuilder searchByPartialName(AVLNode<T> root2, String partialName, StringBuilder resultBuilder,
			String loc) {
		if (root2 == null)
			return resultBuilder;

		if (((Martyr) root2.data).getName().toLowerCase().contains(partialName.toLowerCase())) {
			resultBuilder.append(((Martyr) root2.data).getName() + ", " + ((Martyr) root2.data).getAge() + ", " + loc
					+ ", " + ((Martyr) root2.data).getDateOfDeath() + ", " + ((Martyr) root2.data).getGender() + ". \n");
		}

		if (((Martyr) root.data).getName().toLowerCase().compareTo(partialName.toLowerCase()) > 0)
			return searchByPartialName(root2.left, partialName, resultBuilder,loc);
		else
			return searchByPartialName(root2.right, partialName, resultBuilder,loc);
	}
}
