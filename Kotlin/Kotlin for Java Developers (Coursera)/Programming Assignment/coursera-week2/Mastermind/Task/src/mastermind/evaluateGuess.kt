package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    if (secret.length != guess.length) throw IllegalArgumentException("secret.length != guess.length")
    val commonElements = mutableListOf<Char>()
    for (char in guess) {
        if (char in secret && char !in commonElements) commonElements.add(char)
    }

    var rightPosition = 0
    var wrongPosition = 0
    val secretList = secret.toMutableList()
    val guessList = guess.toMutableList()
    for (element in guess.withIndex()) {
        if (element.value in commonElements) {
            if (element.value == secret[element.index]) {
                secretList.remove(element.value)
                guessList.remove(element.value)
                rightPosition += 1
            }
        }
    }
    for (element in guess.withIndex()) {
        if (element.value in commonElements) {
            if (element.value in secretList && element.value in guessList) {
                secretList.remove(element.value)
                guessList.remove(element.value)
                wrongPosition += 1
            }
        }
    }
    return Evaluation(rightPosition, wrongPosition)
}
