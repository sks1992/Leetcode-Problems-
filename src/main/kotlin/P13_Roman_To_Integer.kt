/**
13. Roman to Integer

Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.

Symbol       Value
I             1
V             5
X             10
L             50
C             100
D             500
M             1000
For example, 2 is written as II in Roman numeral, just two ones added together. 12 is written as XII, which is simply X + II. The number 27 is written as XXVII, which is XX + V + II.

Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII. Instead, the number four is written as IV. Because the one is before the five we subtract it making four. The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:

I can be placed before V (5) and X (10) to make 4 and 9.
X can be placed before L (50) and C (100) to make 40 and 90.
C can be placed before D (500) and M (1000) to make 400 and 900.
Given a roman numeral, convert it to an integer.



Example 1:

Input: s = "III"
Output: 3
Explanation: III = 3.
Example 2:

Input: s = "LVIII"
Output: 58
Explanation: L = 50, V= 5, III = 3.
Example 3:

Input: s = "MCMXCIV"
Output: 1994
Explanation: M = 1000, CM = 900, XC = 90 and IV = 4.


Constraints:

1 <= s.length <= 15
s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M').
It is guaranteed that s is a valid roman numeral in the range [1, 3999].
* */

fun main() {
    val tests = listOf("III", "IV", "IX", "LVIII", "MCMXCIV", "XL", "XCIX")
    for (t in tests) {
        println("Input: $t")
        println("  Bruteforce: ${romanToIntBrute(t)}")
        println("  Recursive : ${romanToIntRecursive(t)}")
        println("  Optimized : ${romanToIntOptimized(t)}")
        println("  Optimized : ${romanToIntTwoPointer(t)}")
        println("  Optimized : ${romanToIntTwoPointerRecursive(t)}")
        println()
    }
}
/*
1) Brute-force approach (clear but inefficient)

Idea: for each character at index i, check all characters to the right; if any later character has a larger value, subtract current; otherwise add it.
This is very wasteful (you do an inner loop for every character) but simple to reason about.
* Complexity:
Time: O(n²) (n = length of string) because of nested loops.
Space: O(1) extra (map is constant size).
* */
fun romanToIntBrute(s: String): Int {
    val map = mapOf(
        'I' to 1, 'V' to 5, 'X' to 10,
        'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000
    )
    var sum = 0
    val n = s.length
    for (i in 0 until n) {
        val cur = map[s[i]] ?: 0
        var hasLargerOnRight = false
        for (j in i + 1 until n) {
            if ((map[s[j]] ?: 0) > cur) {
                hasLargerOnRight = true
                break
            }
        }
        sum += if (hasLargerOnRight) -cur else cur
    }
    return sum
}

/*
* 2) Recursive approach (clean, linear time)

Idea: recursively parse left-to-right. If current symbol forms a subtractive pair with the next symbol, consume both and add (next - cur); otherwise add cur and move one step.

Kotlin (recursive, O(n) time, O(n) stack):
*
* Complexity:

Time: O(n) — each character is processed at most once.

Space: O(n) due to recursion stack (depth up to n). If input can be long, recursion might risk stack overflow.
* */
fun romanToIntRecursive(s: String): Int {
    val map = mapOf(
        'I' to 1, 'V' to 5, 'X' to 10,
        'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000
    )

    fun helper(i: Int): Int {
        if (i >= s.length) return 0
        if (i + 1 < s.length) {
            val cur = map[s[i]] ?: 0
            val nxt = map[s[i + 1]] ?: 0
            if (cur < nxt) {
                // subtractive pair: consume two characters
                return (nxt - cur) + helper(i + 2)
            }
        }
        // normal case: consume one character
        return (map[s[i]] ?: 0) + helper(i + 1)
    }

    return helper(0)
}

/*
* 3) Optimized iterative approach (recommended)

Idea: scan from right to left, maintain prev (value of previous processed char). For current val:

if val < prev ⇒ subtract val

else ⇒ add val and set prev = val

This is the typical optimal solution: single pass, constant extra space.

Kotlin (optimized, O(n) time, O(1) space):
*
* Complexity:

Time: O(n) — one pass.

Space: O(1) extra (map constant).

Micro-optimizations: for hot code you can replace map[...] with a when (ch) returning ints (avoids map allocation/lookup), or use an IntArray indexed by ASCII code for constant-time array access with minimal overhead.
* */

fun romanToIntOptimized(s: String): Int {
    // Using a small fixed map; could also use a when() for slightly faster lookup
    val map = mapOf(
        'I' to 1, 'V' to 5, 'X' to 10,
        'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000
    )

    var total = 0
    var prev = 0
    for (i in s.length - 1 downTo 0) {
        val cur = map[s[i]] ?: 0
        if (cur < prev) {
            total -= cur
        } else {
            total += cur
            prev = cur
        }
    }
    return total
}

/*
⚙️ Two-Pointer Approach – Concept

We can use two pointers (i and j) to compare current and next Roman symbols while moving left to right.

Steps:

Initialize i = 0, j = 1, and sum = 0.

While j < s.length:

Compare the value of s[i] and s[j].

If s[i] < s[j] → subtract s[i] (since it forms a subtractive pair).

Else → add s[i].

Move both pointers forward: i++, j++.

After the loop, add the last value (s[i]), since it’s always positive.

This way, each character is checked only once — linear time, constant space.

*🧮 Complexity Analysis
Aspect	Complexity	Explanation
Time	O(n)	One pass through the string
Space	O(1)	Only constant variables + fixed-size map
* */
fun romanToIntTwoPointer(s: String): Int {
    val map = mapOf(
        'I' to 1, 'V' to 5, 'X' to 10,
        'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000
    )

    var i = 0
    var j = 1
    var total = 0

    while (j < s.length) {
        val cur = map[s[i]] ?: 0
        val next = map[s[j]] ?: 0
        if (cur < next) {
            total -= cur   // Subtractive pattern like IV or IX
        } else {
            total += cur   // Normal addition
        }
        i++
        j++
    }

    // Add the last symbol's value
    total += map[s[i]] ?: 0

    return total
}

/*
Recursive Two-Pointer Approach – Concept

We use two indices (i and j = i + 1) and process the Roman numeral recursively:

Base case: if I reach the end (i == s.length - 1), return the value of s[i].

Recursive step:

Compare s[i] and s[j].

If s[i] < s[j] → subtract current value and move to next index:
return -cur + helper(i + 1)

Else → add current value and move to next index:
return cur + helper(i + 1)

Essentially, we simulate the same logic as iterative two-pointer, but with recursive calls instead of loops.

| Aspect    | Complexity | Reason                              |
| :-------- | :--------- | :---------------------------------- |
| **Time**  | **O(n)**   | Each character is processed once    |
| **Space** | **O(n)**   | Recursive call stack up to n levels |

* */

fun romanToIntTwoPointerRecursive(s: String): Int {
    val map = mapOf(
        'I' to 1, 'V' to 5, 'X' to 10,
        'L' to 50, 'C' to 100, 'D' to 500, 'M' to 1000
    )

    fun helper(i: Int): Int {
        // Base case: last character → just return its value
        if (i == s.length - 1) return map[s[i]] ?: 0
        if (i >= s.length) return 0

        val cur = map[s[i]] ?: 0
        val next = map[s[i + 1]] ?: 0

        return if (cur < next) {
            -cur + helper(i + 1)
        } else {
            cur + helper(i + 1)
        }
    }

    return helper(0)
}
