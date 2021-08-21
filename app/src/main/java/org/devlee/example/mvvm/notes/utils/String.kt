package org.devlee.example.mvvm.notes.utils

fun String.containsTags(tags: String) =
    containsTags(tags.split(Regex("\\s")))

fun String.containsTags(tags: List<String>): Boolean {
    val whiteSpaces = Regex("\\s")
    val stringParts = split(whiteSpaces)
    val result = tags.all { tag ->
        stringParts.any { part ->
            val partContainsTag = tag in part

            partContainsTag
        }
    }

    return result
}