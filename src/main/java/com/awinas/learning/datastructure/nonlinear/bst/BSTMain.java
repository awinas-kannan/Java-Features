package com.awinas.learning.datastructure.nonlinear.bst;

public class BSTMain {

	public static void main(String[] args) {
//		System.out.println(50 < 50);
//		System.out.println(50 > 50);
		BinarySearchTree tree = new BinarySearchTree();

//		# Let us create the following BST
//		#      50
//		#   /     \
//		#  30     70
//		#  / \   / \
//		# 20 40 60 80
		System.out.println("100 " + tree.search(100));
		System.out.println("*******Inserting ************");
		tree.insert(50);
		tree.insert(50);
		tree.insert(30);
		tree.insert(20);
		tree.insert(40);
		tree.insert(70);
		tree.insert(60);
		tree.insert(80);

		System.out.println(tree.toString());

		System.out.println("********Searching ***********");
		System.out.println("100 " + tree.search(100));
		System.out.println("80 " + tree.search(80));
		System.out.println("50 " + tree.search(50));
		System.out.println("200 " + tree.search(200));

		System.out.println("#################### Depth First Search (DFS) #######################################");

		System.out.println("*************in order traversal*************");
		System.out.println(
				"The in-order traversal consists of first visiting the left sub-tree, then the root node, and finally the right sub-tree:");
		tree.inorder();
		System.out.println("*************pre order traversal*************");
		System.out.println(
				"Pre-order traversal visits first the root node, then the left subtree, and finally the right subtree:");
		tree.preorder();
		System.out.println(
				"Post-order traversal visits the left subtree, the right subtree, and the root node at the end:");
		System.out.println("*************post order traversal*************");

		tree.postOrder();

		System.out.println("Level Order Traversal:");
		System.out.println("*************Level Order Traversal:*************");

		tree.levelOrderTraversal();
	}

}
