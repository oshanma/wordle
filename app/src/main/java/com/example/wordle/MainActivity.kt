package com.example.wordle

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var randomWord: String
    private var attempts = 0
    private val maxAttempts = 3
    private val guesses = mutableListOf<String>()
    private lateinit var textViewGuesses: TextView
    private lateinit var buttonSubmit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextGuess = findViewById<EditText>(R.id.editTextGuess)
        textViewGuesses = findViewById<TextView>(R.id.textViewGuesses)
        buttonSubmit = findViewById<Button>(R.id.buttonSubmit)

        initializeGame()

        buttonSubmit.setOnClickListener {
            val guess = editTextGuess.text.toString().trim().uppercase()
            editTextGuess.text.clear()

            if (guess.length == 4) {
                if (attempts < maxAttempts) {
                    attempts++
                    guesses.add("Guess #$attempts\n$guess")
                    updateGuesses()
                    val correctness = checkGuess(guess, randomWord)
                    guesses.add("Guess #$attempts Check\n$correctness")
                    updateGuesses()

                    if (guess == randomWord) {
                        endGame("Congratulations! You've guessed the word correctly.")
                    } else if (attempts == maxAttempts) {
                        endGame("Sorry, you've exceeded your number of guesses. The word was $randomWord.")
                    }
                }
            } else {
                guesses.add("Invalid input. Please enter a 4-letter word.")
                updateGuesses()
            }
        }
    }

    private fun initializeGame() {
        randomWord = FourLetterWordList.getRandomFourLetterWord().uppercase()
        attempts = 0
        guesses.clear()
        buttonSubmit.text = "Guess!"
        textViewGuesses.text = ""
    }

    private fun endGame(message: String) {
        guesses.add(message)
        updateGuesses()
        buttonSubmit.text = "Reset"
        buttonSubmit.setOnClickListener {
            initializeGame()
        }
    }

    private fun updateGuesses() {
        textViewGuesses.text = guesses.joinToString(separator = "\n\n")
    }

    private fun checkGuess(guess: String, targetWord: String): String {
        return guess.mapIndexed { index, c ->
            when {
                c == targetWord[index] -> 'O' // Correct letter in the correct spot
                c in targetWord -> '+' // Correct letter in the wrong spot
                else -> 'X' // Letter not in the word
            }
        }.joinToString("")
    }
}
