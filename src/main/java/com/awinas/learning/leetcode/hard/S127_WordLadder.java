package com.awinas.learning.leetcode.hard;

import java.util.*;


/*

Why is it a Graph?

There is no explicit graph given.

Example:

beginWord = hit

endWord = cog

wordList =

hot
dot
dog
lot
log
cog

Think of each word as a node.

hit
 |
hot
 / \
dot lot
 |    |
dog  log
  \  /
   cog

An edge exists if two words differ by exactly one character.

So this is an implicit graph.
 */
public class S127_WordLadder {

    public static int ladderLength(String beginWord,
                                   String endWord,
                                   List<String> wordList) {

        // Convert word list to HashSet for O(1) lookup
        Set<String> wordSet = new HashSet<>(wordList);
        wordSet.remove(beginWord);
        // Initialize BFS queue with the starting word
        Queue<String> queue = new ArrayDeque<>();
        queue.offer(beginWord);

        // Track the transformation sequence length (starts at 1 for beginWord)
        int transformationSteps = 1;

        // BFS traversal
        while (!queue.isEmpty()) {
            // Increment steps for the next level of transformations
            transformationSteps++;

            // Process all words at the current level
            int currentLevelSize = queue.size();
            for (int i = 0; i < currentLevelSize; i++) {
                String currentWord = queue.poll();
                char[] charArray = currentWord.toCharArray();

                // Try changing each character position
                for (int charIndex = 0; charIndex < charArray.length; charIndex++) {
                    // Store original character to restore later
                    char originalChar = charArray[charIndex];

                    // Try all possible lowercase letters
                    for (char newChar = 'a'; newChar <= 'z'; newChar++) {
                        charArray[charIndex] = newChar;
                        String transformedWord = new String(charArray);

                        // Skip if the transformed word is not in the word list
                        if (!wordSet.contains(transformedWord)) {
                            continue;
                        }

                        // Check if we've reached the target word
                        if (endWord.equals(transformedWord)) {
                            return transformationSteps;
                        }

                        // Add valid transformation to queue for next level
                        queue.offer(transformedWord);

                        // Remove from set to avoid revisiting (mark as visited)
                        wordSet.remove(transformedWord);
                    }

                    // Restore the original character for next position
                    charArray[charIndex] = originalChar;
                }
            }
        }

        // No transformation sequence found
        return 0;
    }

    public static void main(String[] args) {

        List<String> words1 = Arrays.asList("hot", "dot", "dog", "lot", "log", "cog");

        System.out.println(ladderLength("hit", "cog", words1));     // 5


        List<String> words2 = Arrays.asList("hot", "dot", "dog", "lot", "log");

        System.out.println(ladderLength("hit", "cog", words2));     // 0


        List<String> words3 = Arrays.asList("a", "b", "c");

        System.out.println(ladderLength("a", "c", words3));         // 2


        List<String> words4 = Arrays.asList("most", "mist", "miss", "mass", "moss", "lost", "cost");

        System.out.println(ladderLength("lost", "miss", words4));
    }
}
