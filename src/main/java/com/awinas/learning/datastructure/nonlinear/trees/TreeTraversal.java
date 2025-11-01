package com.awinas.learning.datastructure.nonlinear.trees;

import java.util.*;

// Node definition
class Node {
	int val;
	Node left, right;

	Node(int val) {
		this.val = val;
	}
}

public class TreeTraversal {

	// Method to build a perfect binary tree of height 4 (values 1 to 15)
	static Node buildTree() {
		Node[] nodes = new Node[16]; // index 0 unused
		for (int i = 1; i <= 15; i++) {
			nodes[i] = new Node(i);
		}
		for (int i = 1; i <= 7; i++) {
			nodes[i].left = nodes[2 * i];
			nodes[i].right = nodes[2 * i + 1];
		}
		return nodes[1]; // root
	}

	// PREORDER (Root → Left → Right)
	static void preorder(Node root) {
		if (root == null)
			return;
		System.out.print(root.val + " ");
		preorder(root.left);
		preorder(root.right);
	}

	// INORDER (Left → Root → Right)
	static void inorder(Node root) {
		if (root == null)
			return;
		inorder(root.left);
		System.out.print(root.val + " ");
		inorder(root.right);
	}

	// POSTORDER (Left → Right → Root)
	static void postorder(Node root) {
		if (root == null)
			return;
		postorder(root.left);
		postorder(root.right);
		System.out.print(root.val + " ");
	}

	// BFS / LEVEL ORDER
	static void levelOrder(Node root) {
		if (root == null)
			return;
		Queue<Node> q = new LinkedList<>();
		q.add(root);
		while (!q.isEmpty()) {
			Node curr = q.poll();
			System.out.print(curr.val + " ");
			if (curr.left != null)
				q.add(curr.left);
			if (curr.right != null)
				q.add(curr.right);
		}
	}

	// DFS (using Stack)
	// PREORDER Traversal
	static void dfs(Node root) {
		if (root == null)
			return;
		Stack<Node> stack = new Stack<>();
		stack.push(root);
		while (!stack.isEmpty()) {
			Node curr = stack.pop();
			System.out.print(curr.val + " ");
			// push right first so left is processed first
			if (curr.right != null)
				stack.push(curr.right);
			if (curr.left != null)
				stack.push(curr.left);
		}
	}

	public static void main(String[] args) {
		Node root = buildTree();

		System.out.println("PREORDER (Root → Left → Right):");
		preorder(root);
		System.out.println("\n");

		System.out.println("INORDER (Left → Root → Right):");
		inorder(root);
		System.out.println("\n");

		System.out.println("POSTORDER (Left → Right → Root):");
		postorder(root);
		System.out.println("\n");

		System.out.println("BFS / LEVEL ORDER:");
		levelOrder(root);
		System.out.println("\n");

		System.out.println("DFS (Using Stack):");
		dfs(root);
		System.out.println();
	}
}
