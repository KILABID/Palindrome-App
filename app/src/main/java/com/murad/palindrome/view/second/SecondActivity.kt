package com.murad.palindrome.view.second

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.murad.palindrome.R
import com.murad.palindrome.databinding.ActivitySecondBinding
import com.murad.palindrome.view.third.ThirdActivity

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private lateinit var username: String
    private val resultLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val firstname = data?.getStringExtra("SELECTED_USER_FIRST_NAME") ?: ""
                val lastname = data?.getStringExtra("SELECTED_USER_LAST_NAME") ?: ""
                val avatar = data?.getStringExtra("SELECTED_USER_AVATAR") ?: ""
                val email = data?.getStringExtra("SELECTED_USER_EMAIL") ?: ""

                Log.d("SecondActivity", email)

                // Show the LinearLayout and hide the TextView
                binding.layoutDetail.visibility = View.VISIBLE
                binding.selectAUser.visibility = View.GONE

                // Set the user details
                binding.selectedEmail.text = email
                binding.selectedUsername.text = getString(R.string.user_full_name, firstname, lastname)
                Glide.with(this)
                    .load(avatar)
                    .circleCrop()
                    .into(binding.selectedProfile)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = intent.getStringExtra("name").toString()

        binding.tvUsername.text = username

        binding.appBar.setNavigationOnClickListener {
            finish()
        }

        binding.buttonChooseUser.setOnClickListener {
            val thirdIntent = Intent(this, ThirdActivity::class.java)
            resultLauncher.launch(thirdIntent)  // Start ThirdActivity for result
        }
    }
}
