/**
1. Two Sum
Solved
Easy
Topics
premium lock icon
Companies
Hint
Given an array of integers nums and an integer target, return indices of the two numbers such that they add up to target.

You may assume that each input would have exactly one solution, and you may not use the same element twice.

You can return the answer in any order.



Example 1:

Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
Example 2:

Input: nums = [3,2,4], target = 6
Output: [1,2]
Example 3:

Input: nums = [3,3], target = 6
Output: [0,1]


Constraints:

2 <= nums.length <= 104
-109 <= nums[i] <= 109
-109 <= target <= 109
Only one valid answer exists.


Follow-up: Can you come up with an algorithm that is less than O(n2) time complexity?
 * */

fun main() {

    val arr = intArrayOf(2, 7, 11, 15)
    val arr1 = intArrayOf(3, 2, 4)
    val target = 9
    val target1 = 6

    println(twoSumBruteForce(arr, target).contentToString())
    println(twoSumBruteForce(arr1, target1).contentToString())
    println(twoSumOptimized(arr, target).contentToString())
    println(twoSumOptimized(arr1, target1).contentToString())
}

//time complexity = O(n^2)
//space complexity = O(1)
fun twoSumBruteForce(arr: IntArray, target: Int): IntArray {
    for (i in arr.indices) {
        for (j in i + 1 until arr.size) {
            if (arr[i] + arr[j] == target) {
                return intArrayOf(i, j)
            }
        }
    }
    return intArrayOf() // if no pair found
}


//Time: O(n) — each element visited once
//Space: O(n) — for the HashMap storage
fun twoSumOptimized(nums: IntArray, target: Int): IntArray {
    val map = HashMap<Int, Int>() // value -> index
    for (i in nums.indices) {
        val complement = target - nums[i]
        if (map.containsKey(complement)) {
            return intArrayOf(map[complement]!!, i)
        }
        map[nums[i]] = i
    }
    return intArrayOf()
}
