package com.murad.palindrome.view.first

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.murad.palindrome.databinding.ActivityMainBinding
import com.murad.palindrome.view.second.SecondActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isPalindrome: Boolean = false
    private lateinit var editTextName: String
    private lateinit var editTextPalindrome: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction(){
        binding.buttonCheck.setOnClickListener {
            editTextPalindrome = binding.editTextPalindrome.text.toString()
            isPalindrome = checkPalindrome(editTextPalindrome)
            if(editTextPalindrome.isEmpty()){
                showDialogError("Error", "Kolom input tidak boleh kosong")
                Toast.makeText(this, "tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else if (isPalindrome){
                showDialogError("Berhasil", "Kalimat tersebut adalah palindrome")
                Toast.makeText(this, "input adalah palindrome", Toast.LENGTH_SHORT).show()
            }else{
                showDialogError("Error", "Kalimat yang dimasukkan bukan palindrome")
                Toast.makeText(this, "input bukan palindrome", Toast.LENGTH_SHORT).show()
            }
        }

        binding.buttonNext.setOnClickListener {
            editTextName = binding.editTextName.text.toString()
            editTextPalindrome = binding.editTextPalindrome.text.toString()
            isPalindrome = checkPalindrome(editTextPalindrome)
            if(!isPalindrome){
                showDialogError("Error", "Kalimat yang dimasukkan bukan palindrome")
                Toast.makeText(this, "input bukan palindrome", Toast.LENGTH_SHORT).show()
            }else if(editTextName.isEmpty()){
                showDialogError("Error", "Kolom input tidak boleh kosong")
                Toast.makeText(this, "Nama tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }else{
                val secondIntent = Intent(this, SecondActivity::class.java)
                secondIntent.putExtra("name", editTextName)
                startActivity(secondIntent)
            }
        }
    }

    private fun checkPalindrome(palindrome:String):Boolean {
        val cleanString = palindrome.replace(" ", "").lowercase()
        val reversedString = cleanString.reversed()
        return cleanString == reversedString
    }

    private fun showDialogError(title:String, msg:String) {
        AlertDialog.Builder(this).apply {
            setTitle(title)
            setMessage(msg)
            setPositiveButton("OKE") { _, _ -> }
            create()
            show()
        }
    }
}