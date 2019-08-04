package mastermind

data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {
    if (secret.length != guess.length) throw IllegalArgumentException("secret.length != guess.length")

    val rightPosition = guess.withIndex().filter { it.value == secret[it.index] }.size

    // 战五渣的我想不出别的做法了
    val notRightIndexes = guess.withIndex().filter { it.value != secret[it.index] }.map { it.index }
    val notRightSecret = secret.withIndex().filter { notRightIndexes.contains(it.index) }.map { it.value }.sorted()
    val notRightGuess = guess.withIndex().filter { notRightIndexes.contains(it.index) }.map { it.value }.sorted()
    val intersectionElements = notRightGuess.filter { notRightGuess.contains(it) }.distinct()
    var wrongPosition = 0
    for (element in intersectionElements) {
        wrongPosition +=
                (listOf(notRightSecret.count { it == element }, notRightGuess.count { it == element }).min() ?: 0)
    }
    return Evaluation(rightPosition, wrongPosition)
}
