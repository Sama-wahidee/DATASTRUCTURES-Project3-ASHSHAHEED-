package application;

public class AVLNode<T> {
	T data;
	AVLNode<T> left;
	AVLNode<T> right;
	int height;

	public AVLNode(T data) {
		this(data, null, null);
	}

	public AVLNode(T data, AVLNode<T> left, AVLNode<T> right) {
		this.data = data;
		this.left = left;
		this.right = right;
		height = 0;
	}
}