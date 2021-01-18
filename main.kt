fun main() {
    val codeRun = true
    while (codeRun) {
        // Take an input from the user to encrypt or decrypt
        print("Would you like to encrypt or decrypt? [e/d]")
        val option = readLine()

        when {
            // If they want to encrypt, encrypt
            option!!.toLowerCase() == "e" -> {

                // Take an input from the user of a string
                print("What word would you like to encrypt?")
                val string = readLine().toString()

                // Take an input from the user of a integer
                print("How much would you like to shift it by?")
                val integer =  readLine()!!.toInt()

                // Print the output of the encrypted string
                println(encrypt(string, integer))

            }
            // If they want to decrypt, decrypt
            option.toLowerCase() == "d" -> {

                // Take an input from the user of a string
                print("What word would you like to decrypt?")
                val string = readLine().toString()

                // Print the output of the decrypted string
                println(decrypt(string))
            }
            // Anything other than "e", "E", "d" or "D" is an invalid input
            else -> {
                println("Invalid input")
            }
        }
        // Asks if the user would like to encrypt or decrypt something else
        print("Would you like to encrypt or decrypt something else? [y/n]")
        val question = readLine()
        // Ends the program if the input is anything other than "y" or "Y"
        if (question!!.toLowerCase() != "y") {
            break
        }
    }
}

// Make a variable with an array of the alphabet
val alphabet = arrayOf(
    'a',
    'b',
    'c',
    'd',
    'e',
    'f',
    'g',
    'h',
    'i',
    'j',
    'k',
    'l',
    'm',
    'n',
    'o',
    'p',
    'q',
    'r',
    's',
    't',
    'u',
    'v',
    'w',
    'x',
    'y',
    'z'
)

/**
 * Takes a string and rotates all its letters by the specified offset.  Uses a helper
 * function to rotate each character in turn, and uses recursion to continue the process
 * until all characters have been processed.
 * */
fun encrypt(s: String, n: Int): String {

    // There are only 26 letters in the alphabet, so ensure the input is within that range
    require(n in 0..25) { "The value of integer n must be between 0 and 25." }

    // Checks if the input is blank
    return if (s.isBlank()) {
        s
    // If the input is not blank, encrypt the string
    } else {
        encipherHelper(s[0], n) + encrypt(s.substring(1), n)
    }
}

/**
 * Helper function called by encipher for each character in its input string.
 * Checks if the input is a letter, stores if it is uppercase and normalises
 * it to lower case.  Looks up the character's index in an array of the alphabet
 * and adds the offset to that index to determine the rotated character, switching
 * it back to upper case if necessary.  Returns non-letter characters unchanged.
 * */
fun encipherHelper(c: Char, n: Int): Char {

    if (!c.isLetter()) {
        return c
    }

    val isUpperCase = c == (c.toUpperCase())
    val normalised = c.toLowerCase()

    val inputCharIdx = alphabet.indexOf(normalised)

    return if (isUpperCase) alphabet[(inputCharIdx + n) % 26].toUpperCase()
    else alphabet[(inputCharIdx + n) % 26]
}

/**
 * Takes a String and attempts to decipher it using the cumulative relative frequency of its letters for each possible
 * enciphering of it, using the percentage figures taken from Wikipedia (https://en.wikipedia.org/wiki/Letter_frequency)
 * which cites Robert Lewand's "Cryptological Mathematics".  Returns the enciphering of the input string which has the
 * highest score.
 * */
fun decrypt(s: String): String {

    fun relativeFrequencies(letter: Char): Double = when (letter) {
        'a' -> 8.167
        'b' -> 1.492
        'c' -> 2.782
        'd' -> 4.253
        'e' -> 12.702
        'f' -> 2.228
        'g' -> 2.015
        'h' -> 6.094
        'i' -> 6.966
        'j' -> 0.153
        'k' -> 0.772
        'l' -> 4.025
        'm' -> 2.406
        'n' -> 6.749
        'o' -> 7.507
        'p' -> 1.929
        'q' -> 0.095
        'r' -> 5.987
        's' -> 6.327
        't' -> 9.056
        'u' -> 2.758
        'v' -> 0.978
        'w' -> 2.360
        'x' -> 0.150
        'y' -> 1.974
        'z' -> 0.074
        else -> 0.0
    }

    var bestScore = 0.0
    var bestMatch = ""

    for (x in 1 until 26) {

        val candidate = encrypt(s, x)
        val candidateNormalised = candidate.toLowerCase()

        var score = 0.0

        for (letter in candidateNormalised) {
            score += relativeFrequencies(letter)
        }

        if (score > bestScore) {
            bestScore = score
            bestMatch = candidate
        }
    }

    return bestMatch

}