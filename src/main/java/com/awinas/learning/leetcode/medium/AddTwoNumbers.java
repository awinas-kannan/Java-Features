package com.awinas.learning.leetcode.medium;

// 2 : https://leetcode.com/problems/add-two-numbers/

// While doing adding we will add from last numbers only... So no need to reverse the List

public class AddTwoNumbers {

	public static void main(String[] args) {
		AddTwoNumbers addTwoNumbers = new AddTwoNumbers();

		ListNode l1 = new ListNode(2);
		ListNode l2 = new ListNode(4);
		ListNode l3 = new ListNode(3);
		l1.next = l2;
		l2.next = l3;

		ListNode l4 = new ListNode(5);
		ListNode l5 = new ListNode(6);
		ListNode l6 = new ListNode(4);

		l4.next = l5;
		l5.next = l6;

		System.out.println("Adding Two Numbers");

		System.out.println(addTwoNumbers.addTwoNumbers(l1, l4));
		System.out.println(addTwoNumbers.addTwoNumbersOptimised(l1, l4));

	}

	// 243 -> 342
	// 4 * 10 + 2 => 42
	// 3 * 100 + 42 = 342

	// Wont Work for Large number of nodes.. (Because of Int Max value)

	public ListNode addTwoNumbers(ListNode l1, ListNode l2) {

		int numberOne = l1.val;
		int numberTwo = l2.val;
		int i = 1;
		while (l1.next != null) {
			l1 = l1.next;
			numberOne = l1.val * ((int) Math.pow(10, i)) + numberOne;
			i++;
		}
		i = 1;
		while (l2.next != null) {
			l2 = l2.next;
			numberTwo = l2.val * ((int) Math.pow(10, i)) + numberTwo;
			i++;
		}

		int resultnum = numberOne + numberTwo;

		System.out.println("numberOne " + numberOne);
		System.out.println("numberTwo " + numberTwo);

		System.out.println("resultnum " + resultnum);
		ListNode resultNode = new ListNode();
		ListNode orig = resultNode;
		while (resultnum % 10 != resultnum) {

			System.out.println("resultnum / 10 -" + resultnum / 10);
			System.out.println("resultnum % 10 -" + resultnum % 10);
			resultNode.val = resultnum % 10;
			resultNode.next = new ListNode();
			resultNode = resultNode.next;
			resultnum = resultnum / 10;
		}

		resultNode.val = resultnum;
		return orig;
	}

	public ListNode addTwoNumbersOptimised(ListNode l1, ListNode l2) {

		int carry = 0;
		int sum = 0;

		ListNode resultNode = new ListNode();
		ListNode head = resultNode;

		while (l1 != null || l2 != null) {
			int num1 = 0;
			int num2 = 0;
			if (l1 != null) {
				num1 = l1.val;
				l1 = l1.next;
			}
			if (l2 != null) {
				num2 = l2.val;
				l2 = l2.next;
			}
			sum = num1 + num2 + carry;
			resultNode.val = sum % 10;
			carry = sum / 10;

			if (l1 != null || l2 != null) {
				resultNode.next = new ListNode();
				resultNode = resultNode.next;
			}
		}
		if (carry != 0) {
			resultNode.next = new ListNode();
			resultNode = resultNode.next;
			resultNode.val = carry;
		}

		return head;
	}

}

class ListNode {
	int val;
	ListNode next;

	ListNode() {
	}

	ListNode(int val) {
		this.val = val;
	}

	ListNode(int val, ListNode next) {
		this.val = val;
		this.next = next;
	}

	@Override
	public String toString() {
		return "ListNode [val=" + val + ", next=" + next + "]";
	}

}