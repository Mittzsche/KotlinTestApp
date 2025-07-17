package com.test.testkompetensi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.test.testkompetensi.databinding.ActivitySecondScreenBinding
import androidx.activity.result.contract.ActivityResultContracts

class SecondScreen : AppCompatActivity() {
    private lateinit var binding : ActivitySecondScreenBinding

    // Register for activity result
    private val selectUserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedUserName = result.data?.getStringExtra("SELECTED_USER_NAME")
            binding.selectedUser.text = selectedUserName ?: "Selected User Name"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get the username from the Intent sent from MainActivity
        val userName = intent.getStringExtra("USER_NAME")

        // Display the username in tv_username
        // If userName is null or empty, display "John Doe"
        binding.tvUsername.text = userName ?: "John Doe"

        // Set a listener for the back button in the Toolbar
        binding.secondScreenToolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Back to First Screen
        }
        // Set a listener for the "Choose a User" button
        binding.buttonChoose.setOnClickListener {
            navigateToThirdScreen()
        }
    }

    // Function to navigate to ThirdScreen
    private fun navigateToThirdScreen() {
        val intent = Intent(this, ThirdScreen::class.java)
        selectUserLauncher.launch(intent)
    }
}