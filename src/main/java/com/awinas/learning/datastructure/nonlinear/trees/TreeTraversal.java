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

	// Print the binary tree visually (rotated 90° counterclockwise)
	static void printTreeSideWays(Node root) {
		printTreeSideWays(root, 0);
	}

	private static void printTreeSideWays(Node node, int level) {
		if (node == null)
			return;

		// Print right subtree first (so it appears on top)
		printTreeSideWays(node.right, level + 1);

		// Print current node with indentation
		for (int i = 0; i < level; i++) {
			System.out.print("    "); // 4 spaces per level
		}
		System.out.println(node.val);

		// Print left subtree
		printTreeSideWays(node.left, level + 1);
	}
	

	static void printTree(Node root) {
	    if (root == null) return;

	    // Level order traversal to get nodes level by level
	    Queue<Node> queue = new LinkedList<>();
	    queue.add(root);

	    int height = getHeight(root);
	    int level = 1;

	    while (!queue.isEmpty() && level <= height) {
	        int levelSize = queue.size();
	        int spacesBefore = (int) Math.pow(2, height - level + 1) - 2;
	        int spacesBetween = (int) Math.pow(2, height - level + 2) - 2;

	        printSpaces(spacesBefore);

	        List<Node> nextLevel = new ArrayList<>();
	        for (int i = 0; i < levelSize; i++) {
	            Node node = queue.poll();
	            if (node != null) {
	                System.out.print(node.val);
	                nextLevel.add(node.left);
	                nextLevel.add(node.right);
	            } else {
	                System.out.print(" ");
	                nextLevel.add(null);
	                nextLevel.add(null);
	            }
	            printSpaces(spacesBetween);
	        }
	        System.out.println();
	        queue.addAll(nextLevel);
	        level++;
	    }
	}

	private static void printSpaces(int count) {
	    for (int i = 0; i < count; i++) System.out.print(" ");
	}

	private static int getHeight(Node root) {
	    if (root == null) return 0;
	    return 1 + Math.max(getHeight(root.left), getHeight(root.right));
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
		
		// Don't Focus on These two methods.
		System.out.println("########## BINARY TREE #######");
		
		printTree(root);
		
		System.out.println("########## BINARY TREE #######");
		printTreeSideWays(root);
		
		
		System.out.println("########## FOCUS ONLY THIS #######");
		
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
