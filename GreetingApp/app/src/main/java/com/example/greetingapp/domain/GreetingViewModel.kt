package com.example.greetingapp.domain

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.greetingapp.repository.model.GreetingModel


//App's ViewModel
class GreetingViewModel(application: Application): AndroidViewModel(application) {
    //connect to app's model
    val appModel = GreetingModel(application)
    //initialize livedata for connect to views
    //fields livedata
    var nameState = MutableLiveData<String>()
    var nameFieldState = MutableLiveData<Int>()
    var surnameState = MutableLiveData<String>()
    var surnameFieldState = MutableLiveData<Int>()
    var dateState = MutableLiveData<String>()
    var dateFieldState = MutableLiveData<Int>()
    var passwordState = MutableLiveData<String>()
    var passwordFieldState = MutableLiveData<Int>()
    var passwordReplyState = MutableLiveData<String>()
    var passwordReplyFieldState = MutableLiveData<Int>()
    //livedata for button
    var registrationButtonState = MutableLiveData<Boolean>()
    //livedata tha define skipping registration
    var skipRegistrationState = MutableLiveData<Boolean>()


    init {
        //open database
        appModel.openDB()
        //check : if last id exist, skip registration
        val lastUserId = appModel.takeLastId()

        if (lastUserId != -1) {
            nameState.postValue(appModel.readNameFromDBById(lastUserId))
            skipRegistrationState.postValue(true)
        } else{
            //initialization start values
            registrationButtonState.postValue(false)
            skipRegistrationState.postValue(false)
            nameState.postValue("")
            surnameState.postValue("")
            dateState.postValue("")
            passwordState.postValue("")
            passwordReplyState.postValue("")
        }
        nameFieldState.postValue(0)
        surnameFieldState.postValue(0)
        dateFieldState.postValue(0)
        passwordFieldState.postValue(0)
        passwordReplyFieldState.postValue(0)
    }

    //functions checks values and updates field states
    fun onNameChange() {
        nameFieldState.postValue(appModel.checkName(nameState.value.toString()))
    }
    fun onSurnameChange() {
        surnameFieldState.postValue(appModel.checkSurname(surnameState.value.toString()))
    }
    fun onDateChange() {
        dateFieldState.postValue(appModel.checkDate(dateState.value.toString()))
    }
    fun onPasswordChange() {
        passwordFieldState.postValue(appModel.checkPassword(passwordState.value.toString()))
    }
    fun onPasswordReplyChange() {
        passwordReplyFieldState.postValue(appModel.checkPasswordReply(passwordReplyState.value.toString(), passwordState.value.toString()))
    }

    fun onSomeValueChange(){
        registrationButtonState.postValue(appModel.checkConditionForButton(nameState.value.toString(), surnameState.value.toString(),
            dateState.value.toString(), passwordState.value.toString(), passwordReplyState.value.toString()))
    }



    // saves data in database and closes database
    fun onRegistrationButtonChange() {
        appModel.insertToDB(nameState.value.toString(),surnameState.value.toString(), dateState.value.toString(), passwordState.value.toString())
        appModel.closeDB()
    }



}