package com.example.preferences

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.preferences.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity()
{
    private lateinit var binding: ActivityMainBinding
    private lateinit var userSharedPreferences: UserSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main)
        {
            v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        userSharedPreferences = UserSharedPreferences(this)
        initComponents()
        readPreferences()
    }

    private fun readPreferences()
    {
        val userPref = userSharedPreferences.getUserPreferences()
        if (userPref.remember)
        {
            binding.etName.setText(userPref.name)
            binding.etPassword.setText(userPref.password)
            binding.switchRemember.isChecked = true
        }
    }

    private fun initComponents()
    {
        // Handle Save button click
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val remember = binding.switchRemember.isChecked

            if (remember) {
                // Save user preferences if switch is ON
                val userPref = UserPreferences(name, password, remember)
                userSharedPreferences.saveUserPreferences(userPref)
                Toast.makeText(this, "Preferences saved successfully!", Toast.LENGTH_SHORT).show()
            } else {
                // Clear preferences if switch is OFF when clicking Save
                userSharedPreferences.clearPreferences()
                Toast.makeText(this, "Preferences cleared!", Toast.LENGTH_SHORT).show()
            }
        }

        // Automatically clear stored preferences whenever the switch is toggled OFF
        binding.switchRemember.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                userSharedPreferences.clearPreferences()
            }
        }
    }
}