package com.test.testkompetensi

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.test.testkompetensi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialization View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set a listener for the CHECK button
        binding.checkButton.setOnClickListener {
            checkPalindrome()
        }

        // set a listener for the NEXT button
        binding.nextButton.setOnClickListener {
            navigateToSecondScreen()
        }
    }

    // Function to check palindromes
    private fun checkPalindrome() {
        val palindromeText = binding.etPalindrome.text.toString().trim()

        if (palindromeText.isEmpty()) {
            Toast.makeText(this, "Please enter text to check Palindrome.", Toast.LENGTH_SHORT).show()
            return
        }

        // Cleans the string from spaces and converts to lowercase for checking
        val cleanedText = palindromeText.replace(" ", "").lowercase()
        val reversedText = cleanedText.reversed()

        if  (cleanedText == reversedText) {
            Toast.makeText(this, "IsPalindrome", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Not Palindrome", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to navigate to SecondScreen
    private fun navigateToSecondScreen() {
        val name = binding.etName.text.toString().trim()

        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter your name before proceeding.", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, SecondScreen::class.java)
        intent.putExtra("USER_NAME", name)
        startActivity(intent)
    }
}