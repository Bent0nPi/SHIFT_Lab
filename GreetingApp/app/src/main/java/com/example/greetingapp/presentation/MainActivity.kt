package com.example.greetingapp.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.greetingapp.R
import com.example.greetingapp.domain.GreetingViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
//App's main Activity
class MainActivity : AppCompatActivity() {
    lateinit var mViewModel: GreetingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //connect to ViewModel
        mViewModel = ViewModelProvider(this).get(GreetingViewModel::class.java)
        //connect buttonView
        val greetingButton: Button = findViewById(R.id.greetingBtn)

        // start AlertDialog after button clicked
        greetingButton.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle(resources.getString(R.string.greetingText, mViewModel.nameState.value.toString()))
                .setPositiveButton(resources.getString(R.string.positiveBtn)) { dialog, which ->
                }
                .show()
        }


    }


}

