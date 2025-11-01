package com.awinas.learning.datastructure.nonlinear.bst;

import java.util.LinkedList;
import java.util.Queue;

//https://www.geeksforgeeks.org/insert-a-node-in-binary-search-tree-iteratively/

//https://www.geeksforgeeks.org/binary-search-tree-set-1-search-and-insertion/

//https://examples.javacodegeeks.com/java-tree-example/

public class BinarySearchTree {

	Node root;

	public BinarySearchTree() {
		this.root = null;
	}

	public void insert(int value) {

		Node newNode = new Node(value);

		if (root == null) {
			root = newNode;
			return;
		}
		Node temp = root;

		Node nodeWhoseChildProducedNull = null;

		while (temp != null) {
			nodeWhoseChildProducedNull = temp;
			if (value < temp.value) {
				temp = temp.left;
			} else if (value > temp.value) {
				temp = temp.right;
			} else {
				// if duplication
				return;
			}
		}

		if (value < nodeWhoseChildProducedNull.value) {
			nodeWhoseChildProducedNull.left = newNode;
		} else if (value > nodeWhoseChildProducedNull.value) {
			nodeWhoseChildProducedNull.right = newNode;
		}
	}

	// Search a value

	boolean search(int value) {
		Node temp = root;
		while (temp != null) {
			if (temp.value == value) {
				return true;
			}

			if (value < temp.value) {
				temp = temp.left;
			} else {
				temp = temp.right;
			}
		}
		return false;

	}

	// This method mainly calls InorderRec()
	void inorder() {
		inOrderTraversalRec(root);
	}

// A utility function to
// do inorder traversal of BST
//	#      50
//	#   /     \
//	#  30     70
//	#  / \   / \
//	# 20 40 60 80

	void inOrderTraversalRec(Node node) {
		if (node != null) {
			inOrderTraversalRec(node.left); // 50 30 20 null (20) (30) (40) (50) 70 60 null (60) (70) (80)
			System.out.println(node.value);
			inOrderTraversalRec(node.right);
		}
	}


	private class Node {

		int value;
		Node left, right;

		public Node(int value) {
			this.value = value;
			left = right = null;
		}

		@Override
		public String toString() {
			return "Node [value=" + value + ", left=" + left + ", right=" + right + "] \n";
		}

	}

	@Override
	public String toString() {
		return "BinarySearchTree [root=" + root + "]";
	}

}
