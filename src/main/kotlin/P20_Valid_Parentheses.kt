/**
 * 20. Valid Parentheses
 * 
 * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', 
 * determine if the input string is valid.
 * 
 * An input string is valid if:
 * 1. Open brackets must be closed by the same type of brackets.
 * 2. Open brackets must be closed in the correct order.
 * 3. Every close bracket has a corresponding open bracket of the same type.
 * 
 * Example 1:
 * Input: s = "()"
 * Output: true
 * 
 * Example 2:
 * Input: s = "()[]{}"
 * Output: true
 * 
 * Example 3:
 * Input: s = "(]"
 * Output: false
 * 
 * Example 4:
 * Input: s = "([)]"
 * Output: false
 * 
 * Example 5:
 * Input: s = "{[]}"
 * Output: true
 * 
 * Constraints:
 * 1 <= s.length <= 10^4
 * s consists of parentheses only '()[]{}'.
 */

fun main() {
    val testCases = listOf(
        "()",
        "()[]{}",
        "(]",
        "([)]",
        "{[]}",
        "((()))",
        "(((",
        ")))",
        ""
    )
    
    for (test in testCases) {
        println("Input: \"$test\"")
        println("  Brute Force Result: ${isValidBruteForce(test)}")
        println("  Optimized Result: ${isValidOptimized(test)}")
        println("------")
    }
}

/**
 * Brute Force Approach
 * 
 * Idea: Repeatedly remove valid pairs of parentheses until no more can be removed.
 * If the string becomes empty, it's valid; otherwise, it's invalid.
 * 
 * Algorithm:
 * 1. Keep replacing valid pairs ("()", "[]", "{}") with empty strings
 * 2. Continue until no more replacements can be made
 * 3. If the string is empty, return true; otherwise false
 * 
 * 🧮 Complexity Analysis:
 * Time Complexity: O(n^2)
 *   - In the worst case (e.g., "((((("), we iterate n times
 *   - Each iteration scans the entire string (O(n))
 *   - Total: O(n^2)
 * 
 * Space Complexity: O(n)
 *   - We create a mutable string that can be up to n characters
 */
fun isValidBruteForce(s: String): Boolean {
    if (s.isEmpty()) return true
    if (s.length % 2 != 0) return false // Odd length can't be valid
    
    var str = s
    var changed = true
    
    while (changed && str.isNotEmpty()) {
        changed = false
        val before = str
        str = str.replace("()", "")
            .replace("[]", "")
            .replace("{}", "")
        if (str != before) {
            changed = true
        }
    }
    
    return str.isEmpty()
}

/**
 * Optimized Approach using Stack
 * 
 * Idea: Use a stack to track opening brackets. When we encounter a closing bracket,
 * check if it matches the most recent opening bracket.
 * 
 * Algorithm:
 * 1. Create a stack to store opening brackets
 * 2. For each character:
 *    - If it's an opening bracket, push it onto the stack
 *    - If it's a closing bracket:
 *      a. If stack is empty, return false (no matching opening bracket)
 *      b. Pop from stack and check if it matches the closing bracket
 *      c. If not matching, return false
 * 3. After processing all characters, return true only if stack is empty
 * 
 * 🧮 Complexity Analysis:
 * Time Complexity: O(n)
 *   - We traverse the string once, processing each character exactly once
 *   - Stack operations (push/pop) are O(1)
 *   - Total: O(n)
 * 
 * Space Complexity: O(n)
 *   - In the worst case (all opening brackets), stack can contain up to n/2 elements
 *   - Example: "(((((" would require stack of size 5 for string of length 5
 *   - Total: O(n)
 */
fun isValidOptimized(s: String): Boolean {
    if (s.isEmpty()) return true
    if (s.length % 2 != 0) return false // Odd length can't be valid
    
    val stack = mutableListOf<Char>()
    val pairs = mapOf(
        ')' to '(',
        ']' to '[',
        '}' to '{'
    )
    
    for (char in s) {
        when (char) {
            '(', '[', '{' -> {
                // Opening bracket: push onto stack
                stack.add(char)
            }
            ')', ']', '}' -> {
                // Closing bracket: check if it matches the most recent opening bracket
                if (stack.isEmpty() || stack.removeAt(stack.size - 1) != pairs[char]) {
                    return false
                }
            }
        }
    }
    
    // Valid only if all brackets are matched (stack is empty)
    return stack.isEmpty()
}

