package com.example.wordle

object FourLetterWordList {
    private val words = listOf("star", "moon", "mars", "love", "rose","nose") // Add your word list here

    fun getRandomFourLetterWord(): String {
        return words.random()
    }
}
