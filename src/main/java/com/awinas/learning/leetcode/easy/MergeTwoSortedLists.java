package com.awinas.learning.leetcode.easy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.awinas.learning.leetcode.utils.ListNode;

// 21 : https://leetcode.com/problems/merge-two-sorted-lists/description/

public class MergeTwoSortedLists {

	public static void main(String[] args) {

		ListNode l1 = new ListNode(1);
		l1.next = new ListNode(2);
		l1.next.next = new ListNode(4);
//		l1.next.next.next = new ListNode(4);
//		l1.next.next.next.next = new ListNode(5);

		ListNode l2 = new ListNode(1);
		l2.next = new ListNode(3);
		l2.next.next = new ListNode(4);
//		l2.next.next.next = new ListNode(4);
//		l2.next.next.next.next = new ListNode(5);

		System.out.println(mergeTwoLists(l1, l2));
		System.out.println(mergeTwoListsOptimized(l1, l2));
	}

	// list1 = [5]
	// list2 = [1,2,4]

	// Time Complexity: O(nlogn) (sorting step).
	// Space Complexity : O(n)(extra space for the ArrayList).
	public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
		List<Integer> list = new ArrayList<>();
		while (l1 != null) {
			list.add(l1.val);
			l1 = l1.next;
		}
		while (l2 != null) {
			list.add(l2.val);
			l2 = l2.next;
		}
		Collections.sort(list);
		ListNode dummy = new ListNode(0);
		ListNode current = dummy;
		for (int val : list) {
			current.next = new ListNode(val);
			current = current.next;
		}
		return dummy.next;
	}

	// :)
	// Time Complexity:
	// Space Complexity :
	public static ListNode mergeTwoListsOptimized(ListNode list1, ListNode list2) {
		if (list1 == null && list2 == null) {
			return null;
		}
		ListNode result = new ListNode();
		ListNode orig = result;
		while (list1 != null || list2 != null) {
			if (list1 != null && list2 != null) {
				if (list1.val < list2.val) {
					result.val = list1.val;
					list1 = list1.next;
				} else {
					result.val = list2.val;
					list2 = list2.next;
				}
			} else if (list1 != null) {
				result.val = list1.val;
				list1 = list1.next;
			} else if (list2 != null) {
				result.val = list2.val;
				list2 = list2.next;
			}
			if (list1 != null || list2 != null) {
				result.next = new ListNode();
				result = result.next;
			}
		}
		return orig;

	}

	// Two Pointer Techique
	// Time Complexity: O(n)
	// Space Complexity : O(1)

	// Use two pointers to merge the lists in-place.
	// Compare values and link the smaller node to the merged list.
	// Continue until one list is empty.
	// Add the remaing at the end

	public static ListNode mergeTwoListsMoreOptimized(ListNode list1, ListNode list2) {
		ListNode dummy = new ListNode(0);
		ListNode current = dummy;
		while (list1 != null && list2 != null) {
			if (list1.val < list2.val) {
				current.next = list1;
				list1 = list1.next;
			} else {
				current.next = list2;
				list2 = list2.next;
			}
			current = current.next;
		}

		if (list1 != null)
			current.next = list1;
		if (list2 != null)
			current.next = list2;

		return dummy.next;

	}

}
