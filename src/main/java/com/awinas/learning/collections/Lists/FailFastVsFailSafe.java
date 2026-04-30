package com.awinas.learning.collections.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class FailFastVsFailSafe {

    public static void main(String[] args) {
        // ===== FAIL-FAST (ArrayList) =====
        List<String> list = new ArrayList<>(Arrays.asList("A", "B", "C"));
        Iterator<String> it = list.iterator();
        list.add("D");      // structural modification!
        it.next();           // throws ConcurrentModificationException!

// Why? Because internally:
//   - ArrayList has a field: modCount (modification counter)
//   - Every add/remove increments modCount
//   - Iterator stores expectedModCount = modCount at creation
//   - On every next(), it checks: modCount != expectedModCount → throw!


// ===== FAIL-SAFE (CopyOnWriteArrayList) =====
        CopyOnWriteArrayList<String> cowList = new CopyOnWriteArrayList<>(Arrays.asList("A", "B", "C"));
        Iterator<String> cowIt = cowList.iterator();
        cowList.add("D");    // modification — creates a new internal array
        cowIt.next();        // "A" — no exception! Iterating on the OLD snapshot
        cowIt.next();        // "B" — still on the old snapshot

        // Iterator sees: [A, B, C] — not [A, B, C, D]
    }
}
