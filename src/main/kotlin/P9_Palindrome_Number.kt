/**
9. Palindrome Number
Given an integer x, return true if x is a palindrome, and false otherwise.

Example 1:

Input: x = 121
Output: true
Explanation: 121 reads as 121 from left to right and from right to left.
Example 2:

Input: x = -121
Output: false
Explanation: From left to right, it reads -121. From right to left, it becomes 121-. Therefore it is not a palindrome.
Example 3:

Input: x = 10
Output: false
Explanation: Reads 01 from right to left. Therefore it is not a palindrome.


Constraints:

-231 <= x <= 231 - 1


Follow up: Could you solve it without converting the integer to a string?
* */

fun main() {
    val numbers = listOf(121, -121, 10, 0, 12321)

    for (x in numbers) {
        println("Number: $x")
        println("Brute Force Result: ${isPalindromeBruteForce(x)}")
        println("Optimized Result: ${isPalindromeOptimized(x)}")
        println("------")
    }
}
//🧮 Complexity:
//Type	Complexity	Explanation
//Time	O(n)	You traverse the number’s digits twice (once to reverse)
//Space	O(n)	Because you create a reversed copy of the string

fun isPalindromeBruteForce(x: Int): Boolean {
    val str = x.toString()
    val reversed = str.reversed()
    return str == reversed
}

//🧮 Complexity:
//Type	Complexity	Explanation
//Time	O(log₁₀(n))	We process half the digits
//Space	O(1)	No extra space — only integer variables
fun isPalindromeOptimized(x: Int): Boolean {
    //Negative numbers are never palindrome.
    //Numbers ending in 0 (except 0 itself) are not palindrome.
    //Reverse half of the digits and compare.
    if (x < 0 || (x % 10 == 0 && x != 0)) return false

    var num = x
    var reversedHalf = 0

    while (num > reversedHalf) {
        val digit = num % 10
        reversedHalf = reversedHalf * 10 + digit
        num /= 10
    }

    // For even length numbers: num == reversedHalf
    // For odd length numbers: num == reversedHalf / 10
    return num == reversedHalf || num == reversedHalf / 10
}
