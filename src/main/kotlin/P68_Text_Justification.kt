/**
 * 68. Text Justification
 * 
 * Given an array of strings words and a width maxWidth, format the text such that 
 * each line has exactly maxWidth characters and is fully (left and right) justified.
 * 
 * You should pack your words in a greedy approach; that is, pack as many words as 
 * you can in each line. Pad extra spaces ' ' when necessary so that each line has 
 * exactly maxWidth characters.
 * 
 * Extra spaces between words should be distributed as evenly as possible. If the 
 * number of spaces on a line does not divide evenly between words, the empty slots 
 * on the left will be assigned more spaces than the slots on the right.
 * 
 * For the last line of text, it should be left-justified, and no extra space is 
 * inserted between words.
 * 
 * Example 1:
 * Input: words = ["This", "is", "an", "example", "of", "text", "justification."], maxWidth = 16
 * Output:
 * [
 *    "This    is    an",
 *    "example  of text",
 *    "justification.  "
 * ]
 * 
 * Example 2:
 * Input: words = ["What","must","be","acknowledgment","shall","be"], maxWidth = 16
 * Output:
 * [
 *   "What   must   be",
 *   "acknowledgment  ",
 *   "shall be        "
 * ]
 * 
 * Example 3:
 * Input: words = ["Science","is","what","we","understand","well","enough","to","explain","to","a","computer.","Art","is","everything","else","we","do"], maxWidth = 20
 * Output:
 * [
 *   "Science  is  what we",
 *   "understand      well",
 *   "enough to explain to",
 *   "a  computer.  Art is",
 *   "everything  else  we",
 *   "do                  "
 * ]
 * 
 * Constraints:
 * 1 <= words.length <= 300
 * 1 <= words[i].length <= 20
 * words[i] consists of only English letters and symbols.
 * 1 <= maxWidth <= 100
 */

fun main() {
    val testCases = listOf(
        Pair(
            arrayOf("This", "is", "an", "example", "of", "text", "justification."),
            16
        ),
        Pair(
            arrayOf("What", "must", "be", "acknowledgment", "shall", "be"),
            16
        ),
        Pair(
            arrayOf("Science", "is", "what", "we", "understand", "well", "enough", "to", "explain", "to", "a", "computer.", "Art", "is", "everything", "else", "we", "do"),
            20
        ),
        Pair(
            arrayOf("Listen", "to", "many,", "speak", "to", "a", "few."),
            6
        )
    )
    
    for ((words, maxWidth) in testCases) {
        println("Input: words = ${words.contentToString()}, maxWidth = $maxWidth")
        println("Brute Force Result:")
        fullJustifyBruteForce(words.toList(), maxWidth).forEach { println("  \"$it\"") }
        println("Optimized Result:")
        fullJustifyOptimized(words.toList(), maxWidth).forEach { println("  \"$it\"") }
        println("------")
    }
}

/**
 * Brute Force Approach
 * 
 * Idea: Process words one by one, building lines greedily. For each line:
 * 1. Add words until adding another word would exceed maxWidth
 * 2. Calculate spaces needed and distribute them evenly
 * 3. Handle last line specially (left-justified)
 * 
 * Algorithm:
 * - Iterate through words, grouping them into lines
 * - For each line, calculate total characters and spaces needed
 * - Distribute spaces evenly, with extra spaces going to the left
 * - Last line: left-justify with single spaces between words
 * 
 * 🧮 Complexity Analysis:
 * Time Complexity: O(n * m)
 *   - n = number of words
 *   - m = average length of words
 *   - We iterate through each word once: O(n)
 *   - For each line, we build the string which takes O(m) per word
 *   - Total: O(n * m) where n*m represents total characters processed
 * 
 * Space Complexity: O(n * maxWidth)
 *   - Result list can contain up to n lines (worst case: one word per line)
 *   - Each line has maxWidth characters
 *   - Total: O(n * maxWidth)
 */
fun fullJustifyBruteForce(words: List<String>, maxWidth: Int): List<String> {
    val result = mutableListOf<String>()
    var index = 0
    
    while (index < words.size) {
        // Step 1: Determine which words fit in the current line
        var totalChars = words[index].length
        var last = index + 1
        
        // Greedily add words until we can't fit more
        while (last < words.size) {
            // Check if adding next word (with space) would exceed maxWidth
            if (totalChars + 1 + words[last].length > maxWidth) break
            totalChars += 1 + words[last].length  // +1 for space between words
            last++
        }
        
        // Step 2: Build the line
        val line = StringBuilder()
        val numberOfWords = last - index
        
        // Special case: last line or single word line (left-justified)
        if (last == words.size || numberOfWords == 1) {
            // Left-justify: single space between words, pad with spaces at the end
            for (i in index until last) {
                line.append(words[i])
                if (i < last - 1) {
                    line.append(" ")
                }
            }
            // Pad remaining spaces
            while (line.length < maxWidth) {
                line.append(" ")
            }
        } else {
            // Fully justify: distribute spaces evenly
            // Calculate spaces: totalChars includes (numberOfWords - 1) single spaces
            // We need to replace those with more spaces to reach maxWidth
            val totalSpacesNeeded = maxWidth - totalChars + (numberOfWords - 1)
            val spacesBetweenWords = totalSpacesNeeded / (numberOfWords - 1)
            val extraSpaces = totalSpacesNeeded % (numberOfWords - 1)
            
            for (i in index until last) {
                line.append(words[i])
                if (i < last - 1) {
                    // Distribute spaces: base spaces + extra spaces for left slots
                    val spacesToApply = spacesBetweenWords + if (i - index < extraSpaces) 1 else 0
                    repeat(spacesToApply) {
                        line.append(" ")
                    }
                }
            }
        }
        
        result.add(line.toString())
        index = last
    }
    
    return result
}

/**
 * Optimized Approach
 * 
 * Idea: Same greedy approach but with cleaner code structure and better variable naming.
 * Separates concerns: line building logic is more modular.
 * 
 * Improvements:
 * - Better separation of concerns
 * - More readable variable names
 * - Helper function for building justified line
 * - Slightly more efficient string building
 * 
 * Algorithm:
 * Same as brute force, but with cleaner implementation
 * 
 * 🧮 Complexity Analysis:
 * Time Complexity: O(n * m)
 *   - Same as brute force: O(n) iterations, O(m) work per word
 *   - n = number of words, m = average word length
 *   - Total: O(n * m)
 * 
 * Space Complexity: O(n * maxWidth)
 *   - Same as brute force: result storage
 *   - Total: O(n * maxWidth)
 * 
 * Note: The brute force approach is already quite efficient for this problem.
 * This "optimized" version focuses on code clarity and maintainability rather
 * than algorithmic improvements, as the problem inherently requires O(n*m) time.
 */
fun fullJustifyOptimized(words: List<String>, maxWidth: Int): List<String> {
    val result = mutableListOf<String>()
    var wordIndex = 0
    
    while (wordIndex < words.size) {
        val lineWords = mutableListOf<String>()
        var lineLength = 0
        
        // Greedily collect words for current line
        while (wordIndex < words.size) {
            val word = words[wordIndex]
            val spaceNeeded = if (lineWords.isEmpty()) 0 else 1
            
            if (lineLength + spaceNeeded + word.length <= maxWidth) {
                lineWords.add(word)
                lineLength += spaceNeeded + word.length
                wordIndex++
            } else {
                break
            }
        }
        
        // Build the justified line
        val line = buildJustifiedLine(lineWords, lineLength, maxWidth, wordIndex == words.size)
        result.add(line)
    }
    
    return result
}

/**
 * Helper function to build a justified line
 * 
 * @param words List of words in the line
 * @param lineLength Current length including single spaces
 * @param maxWidth Target width for the line
 * @param isLastLine Whether this is the last line (left-justified)
 * @return Formatted line string
 */
private fun buildJustifiedLine(
    words: List<String>,
    lineLength: Int,
    maxWidth: Int,
    isLastLine: Boolean
): String {
    if (words.isEmpty()) return " ".repeat(maxWidth)
    
    val result = StringBuilder()
    
    if (isLastLine || words.size == 1) {
        // Last line or single word: left-justify
        result.append(words.joinToString(" "))
        result.append(" ".repeat(maxWidth - result.length))
    } else {
        // Fully justify: distribute spaces evenly
        // lineLength includes (words.size - 1) single spaces
        // Calculate total spaces needed: maxWidth - actual_char_count
        // actual_char_count = lineLength - (words.size - 1)
        // totalSpaces = maxWidth - (lineLength - (words.size - 1)) = maxWidth - lineLength + (words.size - 1)
        val actualCharCount = lineLength - (words.size - 1)
        val totalSpaces = maxWidth - actualCharCount
        val baseSpaces = totalSpaces / (words.size - 1)
        val extraSpaces = totalSpaces % (words.size - 1)
        
        for (i in words.indices) {
            result.append(words[i])
            if (i < words.size - 1) {
                val spacesToAdd = baseSpaces + if (i < extraSpaces) 1 else 0
                result.append(" ".repeat(spacesToAdd))
            }
        }
    }
    
    return result.toString()
}

