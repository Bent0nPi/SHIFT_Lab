package com.example.greetingapp.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged

import androidx.lifecycle.ViewModelProvider
import com.example.greetingapp.R
import com.example.greetingapp.domain.GreetingViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

//registration activity
class RegistrationActivity: AppCompatActivity() {
    lateinit var mViewModel: GreetingViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        //connect ViewModel
        mViewModel = ViewModelProvider(this).get(GreetingViewModel::class.java)
        // connect views
        val loginName: TextInputLayout = findViewById(R.id.nameTextField)
        val loginNameText: TextInputEditText = findViewById(R.id.nameET)
        val loginSurname: TextInputLayout = findViewById(R.id.surnameTextField)
        val loginSurnameText: TextInputEditText= findViewById(R.id.surnameET)
        val loginBirthDate: TextInputLayout = findViewById(R.id.birthDateTextField)
        val loginBirthDateText: TextInputEditText = findViewById(R.id.birthDateET)
        val loginPassword: TextInputLayout = findViewById(R.id.passwordTextField)
        val loginPasswordText: TextInputEditText = findViewById(R.id.passwordET)
        val loginPasswordReply: TextInputLayout = findViewById(R.id.passwordReplyTextField)
        val loginPasswordReplyText: TextInputEditText = findViewById(R.id.passwordReplyET)
        val registrationButton: Button = findViewById(R.id.bRegistration)
        
        //make registration button unable
        registrationButton.setEnabled(false)
        // if registration button clicked - go to main activity
        registrationButton.setOnClickListener{
            mViewModel.onRegistrationButtonChange()
            transmitToNextActivity(this)
            finish()
        }
        // updates name livedata when typing in text field
        loginNameText.doOnTextChanged { text, start, before, count ->
            //loginName.isErrorEnabled = true
            mViewModel.nameState.postValue(text.toString())
        }
        // updates surname livedata when typing in text field
        loginSurnameText.doOnTextChanged { text, start, before, count ->
            //loginSurname.isErrorEnabled = true
            mViewModel.surnameState.postValue(text.toString())
        }
        // updates birth date livedata when typing in text field
        loginBirthDateText.doOnTextChanged {text, start, before, count ->
            //loginBirthDate.isErrorEnabled = true
            mViewModel.dateState.postValue(text.toString())
        }
        // updates password livedata when typing in text field
        loginPasswordText.doOnTextChanged { text, start, before, count ->
            //loginPassword.isErrorEnabled = true
            mViewModel.passwordState.postValue(text.toString())
        }
        // updates password reply livedata when typing in text field
        loginPasswordReplyText.doOnTextChanged { text, start, before, count ->
            //loginPasswordReply.isErrorEnabled = true
            mViewModel.passwordReplyState.postValue(text.toString())
        }
        // observes function that handles livedata updates
        //observes name's update and update field livedata after it
        mViewModel.nameState.observe(this) {
            mViewModel.onNameChange()
            mViewModel.onSomeValueChange()
        }
        //observes name field's update and update view after it
        mViewModel.nameFieldState.observe(this) {
            loginName.error = when (mViewModel.nameFieldState.value) {
                0 -> null
                1 -> resources.getString(R.string.nameLengthError)
                2 -> resources.getString(R.string.nameConsistError)
                else -> null
            }
        }
        //observes surname's update and update field livedata after it
        mViewModel.surnameState.observe(this) {
            mViewModel.onSurnameChange()
            mViewModel.onSomeValueChange()
        }
        //observes surname field's update and update view after it
        mViewModel.surnameFieldState.observe(this) {
            loginSurname.error = when (mViewModel.surnameFieldState.value) {
                0 -> null
                1 -> resources.getString(R.string.surnameLengthError)
                2 -> resources.getString(R.string.surnameConsistError)
                else -> null
            }
        }
        //observes birth date's update and update field livedata after it
        mViewModel.dateState.observe(this) {
            mViewModel.onDateChange()
            mViewModel.onSomeValueChange()
        }
        //observes birth data field's update and update view after it
        mViewModel.dateFieldState.observe(this) {
            loginBirthDate.error = when (mViewModel.dateFieldState.value) {
                0 -> null
                3 -> resources.getString(R.string.dateYearError)
                4 -> resources.getString(R.string.dateMonthError)
                5 -> resources.getString(R.string.dateDayError)
                else -> resources.getString(R.string.dateFormatError)
            }
        }
        //observes password's update and update password field livedata  after it
        mViewModel.passwordState.observe(this) {
            mViewModel.onPasswordChange()
            mViewModel.onSomeValueChange()
        }
        //observes password field's update and update view after it
        mViewModel.passwordFieldState.observe(this) {
            loginPassword.error = when (mViewModel.passwordFieldState.value) {
                0 -> null
                1 -> resources.getString(R.string.passwordLengthError)
                2 -> resources.getString(R.string.passwordSpecSymbolsError)
                3 -> resources.getString(R.string.passwordUpperCaseError)
                4 -> resources.getString(R.string.passwordLowerCaseErroe)
                5 -> resources.getString(R.string.passwordDigitsError)
                else -> null
            }
        }
        //observes password's update and update field livedata after it
        mViewModel.passwordReplyState.observe(this) {
            mViewModel.onPasswordReplyChange()
            mViewModel.onSomeValueChange()
        }
        //observes password reply field's update and update view after it
        mViewModel.passwordReplyFieldState.observe(this) {
            loginPasswordReply.error = when (mViewModel.passwordReplyFieldState.value) {
                0 -> null
                else -> resources.getString(R.string.passwordCompareError)
            }
        }
        //observes button's state and makes it enable if state changes
        mViewModel.registrationButtonState.observe(this) {
            registrationButton.setEnabled(mViewModel.registrationButtonState.value ?: true)
        }
        //observes skipRegistrationState's update and go to main activity if it is true
        mViewModel.skipRegistrationState.observe(this) {
            if (mViewModel.skipRegistrationState.value!!) {
                transmitToNextActivity(this)
                finish()
            }
        }

    }
}
// function makes intent and transmits to main activity
fun transmitToNextActivity(context: Context){
    val intent = Intent(context, MainActivity::class.java)
    context.startActivity(intent)
}