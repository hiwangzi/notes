package nicestring

fun String.isNice(): Boolean {
    val condition1 = !this.contains(Regex("b[uea]{1}"))
    val condition2 = this.filter { listOf('a', 'e', 'i', 'o', 'u').contains(it) }.length >= 3
    val condition3 = this.groupBy { it }.filter { it.value.size > 1 }.map { it.key }
            .any { this.contains(Regex("$it{2,}")) }
    return listOf(condition1, condition2, condition3).filter { it }.size >= 2
}