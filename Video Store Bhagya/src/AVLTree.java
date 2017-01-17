//package com.videostore.datastructures;

import java.util.ArrayList;
//import com.videostore.model.Video;

public class AVLTree {

	protected AvlNode root; // the root node

	public void insert(String key, Video video) {
		// create new node
		AvlNode n = new AvlNode(key, video);
		// start recursive procedure for inserting the node
		insertAVL(this.root, n);
	}

	public void insertAVL(AvlNode p, AvlNode q) {
		// If node to compare is null, the node is inserted. If the root is
		// null, it is the root of the tree.
		if (p == null) {
			this.root = q;
		} else {
			int cmp = q.key.compareTo(p.key);
			if (cmp == 0) {
				q = p;
				p = q;
				return;
			}
			// If compare node is smaller, continue with the left node
			if (cmp < 0) {
				if (p.left == null) {
					p.left = q;
					q.parent = p;

					// Node is inserted now, continue checking the balance
					recursiveBalance(p);
				} else {
					insertAVL(p.left, q);
				}

			} else if (cmp > 0) {
				if (p.right == null) {
					p.right = q;
					q.parent = p;

					// Node is inserted now, continue checking the balance
					recursiveBalance(p);
				} else {
					insertAVL(p.right, q);
				}
			} else {
				// do nothing: This node already exists
			}
		}
	}

	public void recursiveBalance(AvlNode cur) {

		setBalance(cur);
		int balance = cur.balance;

		if (balance == -2) {

			if (height(cur.left.left) >= height(cur.left.right)) {
				cur = rotateRight(cur);
			} else {
				cur = doubleRotateLeftRight(cur);
			}
		} else if (balance == 2) {
			if (height(cur.right.right) >= height(cur.right.left)) {
				cur = rotateLeft(cur);
			} else {
				cur = doubleRotateRightLeft(cur);
			}
		}

		if (cur.parent != null) {
			recursiveBalance(cur.parent);
		} else {
			this.root = cur;
			System.out
					.println("------------ Balancing finished ----------------");
		}
	}

	public AvlNode rotateLeft(AvlNode n) {

		AvlNode v = n.right;
		v.parent = n.parent;

		n.right = v.left;

		if (n.right != null) {
			n.right.parent = n;
		}

		v.left = n;
		n.parent = v;

		if (v.parent != null) {
			if (v.parent.right == n) {
				v.parent.right = v;
			} else if (v.parent.left == n) {
				v.parent.left = v;
			}
		}

		setBalance(n);
		setBalance(v);

		return v;
	}

	public AvlNode rotateRight(AvlNode n) {

		AvlNode v = n.left;
		v.parent = n.parent;

		n.left = v.right;

		if (n.left != null) {
			n.left.parent = n;
		}

		v.right = n;
		n.parent = v;

		if (v.parent != null) {
			if (v.parent.right == n) {
				v.parent.right = v;
			} else if (v.parent.left == n) {
				v.parent.left = v;
			}
		}

		setBalance(n);
		setBalance(v);

		return v;
	}

	public AvlNode doubleRotateLeftRight(AvlNode u) {
		u.left = rotateLeft(u.left);
		return rotateRight(u);
	}

	public AvlNode doubleRotateRightLeft(AvlNode u) {
		u.right = rotateRight(u.right);
		return rotateLeft(u);
	}

	public AvlNode successor(AvlNode q) {
		if (q.right != null) {
			AvlNode r = q.right;
			while (r.left != null) {
				r = r.left;
			}
			return r;
		} else {
			AvlNode p = q.parent;
			while (p != null && q == p.right) {
				q = p;
				p = q.parent;
			}
			return p;
		}
	}

	private int height(AvlNode cur) {
		if (cur == null) {
			return -1;
		}
		if (cur.left == null && cur.right == null) {
			return 0;
		} else if (cur.left == null) {
			return 1 + height(cur.right);
		} else if (cur.right == null) {
			return 1 + height(cur.left);
		} else {
			return 1 + maximum(height(cur.left), height(cur.right));
		}
	}

	private int maximum(int a, int b) {
		if (a >= b) {
			return a;
		} else {
			return b;
		}
	}

	private void setBalance(AvlNode cur) {
		cur.balance = height(cur.right) - height(cur.left);
	}

	public void inorder(int status) {
		inorder(root, status);
	}

	protected void inorder(AvlNode n, int status) {
		if (n == null) {
			return;
		}
		inorder(n.left, status);
		if (status == 1) {
			System.out.println(n.value.getTitle());
		} else if (status == 2) {
			System.out.print(n.value.getTitle() + "\t\t"
					+ n.value.getRentPrice() + "\t\t");
			if (n.value.isOnRent()) {
				System.out.println("Not Available");
			} else {
				System.out.println("Available");
			}
		}
		inorder(n.right, status);
	}

	public ArrayList<Video> getInList() {
		ArrayList<Video> ret = new ArrayList<Video>();
		getList(root, ret);
		return ret;
	}

	final protected void getList(AvlNode n, ArrayList<Video> list) {
		if (n == null) {
			return;
		}
		getList(n.left, list);
		list.add(n.value);
		getList(n.right, list);
	}

	public boolean checkVideo(String key) {
		return checkVideoAuxillary(root, key);
	}

	public boolean checkVideoAuxillary(AvlNode root, String key) {
		if (root == null) {
			return false;
		}
		int cmp = key.compareTo(root.value.getTitle());
		if (cmp == 0) {
			return true;
		} else if (cmp < 0) {
			return checkVideoAuxillary(root.left, key);
		} else {
			return checkVideoAuxillary(root.right, key);
		}
	}

}

class AvlNode {
	public AvlNode left;
	public AvlNode right;
	public AvlNode parent;
	public String key;
	public Video value;
	public int balance;

	public AvlNode(String k, Video v) {
		left = right = parent = null;
		balance = 0;
		key = k;
		this.value = v;
	}

	public String toString() {
		return "" + key;
	}

}