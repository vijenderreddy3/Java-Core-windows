//package com.videostore.datastructures;

import java.util.ArrayList;

//import com.videostore.model.Video;

public class BinarySearchTree {

	private Node root;

	private class Node {
		private String key;
		private Video val;
		private Node left, right;
		private int N;

		public Node(String key, Video val, int N) {
			this.key = key;
			this.val = val;
			this.N = N;
		}
	}

	public int size() {
		return size(root);
	}

	public int size(Node x) {
		if (x == null)
			return 0;
		else
			return x.N;
	}

	public Video get(String key) {
		return get(root, key);
	}

	private Video get(Node x, String key) {
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			return get(x.left, key);
		else if (cmp < 0)
			return get(x.right, key);
		else
			return x.val;
	}

	public boolean checkIfPresent(String key) {
		return checkIfPresentAuxillary(root, key);
	}

	public boolean checkIfPresentAuxillary(Node root, String key) {
		if (root == null) {
			return false;
		}
		int cmp = key.compareTo(root.key);
		if (cmp == 0) {
			return true;
		} else if (cmp < 0) {
			return checkIfPresentAuxillary(root.left, key);
		} else {
			return checkIfPresentAuxillary(root.right, key);
		}
	}

	public Node put(String key, Video val) {
		return root = put(root, key, val);
	}

	private Node put(Node x, String key, Video val) {
		if (x == null)
			return new Node(key, val, 1);
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			x.left = put(x.left, key, val);
		else if (cmp > 0)
			x.right = put(x.right, key, val);
		else
			x.val = val;
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}

	public String min() {
		return min(root).key;
	}

	private Node min(Node x) {
		if (x.left == null)
			return x;
		return min(x.left);
	}

	public String max() {
		return max(root).key;// min() changed to max()
	}

	private Node max(Node x) {
		if (x.right == null)
			return x;
		return max(x.right);
	}

	public void deleteMin() {
		root = deleteMin(root);
	}

	private Node deleteMin(Node x) {
		if (x.left == null)
			return x.right;
		x.left = deleteMin(x.left);
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}

	public void deleteMax() {
		root = deleteMax(root);// recursive call deleteMax()
	}

	private Node deleteMax(Node x) {
		if (x.right == null)
			return x.left;
		x.right = deleteMax(x.right);
		x.N = size(x.right) + size(x.left) + 1;
		return x;
	}

	public void delete(String key) {
		root = delete(root, key);
	}

	private Node delete(Node x, String key) {
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			x.left = delete(x.left, key);
		else if (cmp > 0)
			x.right = delete(x.right, key);
		else {
			if (x.right == null)
				return x.left;
			if (x.left == null)
				return x.right;
			Node t = x;
			x = min(t.right);
			x.right = deleteMin(t.right);
			x.left = t.left;
		}
		x.N = size(x.left) + size(x.right) + 1;
		return x;
	}

	public void inOrder(int status) {
		inOrderAuxillary(this.root, status);
	}

	private void inOrderAuxillary(Node root, int status) {
		if (root == null) {
			return;
		}
		inOrderAuxillary(root.left, status);
		if (status == 1) {
			System.out.println(root.val.getTitle());
		} else if (status == 2) {
			System.out.print(root.val.getTitle() + "\t\t"
					+ root.val.getRentPrice() + "\t\t");
			if (root.val.isOnRent()) {
				System.out.println("Not Available");
			} else {
				System.out.println("Available");
			}
		}
		inOrderAuxillary(root.right, status);
	}

	public ArrayList<Video> getInList() {
		ArrayList<Video> ret = new ArrayList<Video>();
		getList(root, ret);
		return ret;
	}

	protected void getList(Node n, ArrayList<Video> list) {
		if (n == null) {
			return;
		}
		getList(n.left, list);
		list.add(n.val);
		getList(n.right, list);
	}
}
