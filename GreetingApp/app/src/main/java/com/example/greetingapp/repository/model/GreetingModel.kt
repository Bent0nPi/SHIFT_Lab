package com.example.greetingapp.repository.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
//App's model
class GreetingModel(context: Context) {
    //database initialization
    val greetingDBHelper = GreetingDBHelper(context)
    var db: SQLiteDatabase? = null

    //database request function for ViewModel
    //function opens database for editing and reading
    fun openDB() {
        db = greetingDBHelper.writableDatabase
    }
    //function inserts all information about user to database
    fun insertToDB(name: String, surname: String, birthDate: String, password: String) {
        val values = ContentValues().apply {
            put(GreetingAppDBNameClass.COLUMN_NAME_NAME, name)
            put(GreetingAppDBNameClass.COLUMN_NAME_SURNAME, surname)
            put(GreetingAppDBNameClass.COLUMN_NAME_BIRTH_DATE, birthDate)
            put(GreetingAppDBNameClass.COLUMN_NAME_PASSWORD, hashPassword(password))
        }
        db?.insert(GreetingAppDBNameClass.TABLE_NAME, null, values)
    }
    //function finds necessary row by id and return it's NAME column
    fun readNameFromDBById(id: Int): String {
        val dataList = ArrayList<String>()
        val selection = "${BaseColumns._ID} = ?"
        val selectArgs = arrayOf(id.toString())
        val cursor = db?.query(
            GreetingAppDBNameClass.TABLE_NAME,
            null,
            selection,
            selectArgs,
            null,
            null,
            null,
        )

        while (cursor?.moveToNext()!!) {
            val dataText =
                cursor.getString(cursor.getColumnIndexOrThrow(GreetingAppDBNameClass.COLUMN_NAME_NAME))
            dataList.add(dataText)
        }
        if(dataList.size == 0) dataList.add("Nobody")

        cursor.close()
        return dataList[0]
    }
    //function returns last Id in table
    fun takeLastId(): Int {
        val order = "${BaseColumns._ID} DESC"
        val cursor = db?.query(
            GreetingAppDBNameClass.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            order,
        )
        var dataId: Int = -1
        while (cursor?.moveToNext()!!) {
            dataId = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            break
        }
        cursor.close()
        return dataId
    }
    //function close database
    fun closeDB() {
        greetingDBHelper.close()
    }

    //function gets password and returns hashed password
    fun hashPassword(rawData: String): Long {
        var hashValue = 0L
        for (i in 0..(rawData.length - 1)) {
            hashValue = (hashValue * 31 + rawData[i].code) % 652627
        }
        return hashValue
    }

    //Functions-validators for registration data
    //function checks user's name and returns one of 3 codes: 0 - ok, 1 - length error, 2 - consist error
    fun checkName(name: String): Int {
        if (name.length == 0) return 0
        if (name.length < 2) return 1
        name.forEach {
            if (!(it.isLetter())) return 2
        }
        return 0
    }
    //function checks user's surname and returns one of 3 codes: 0 - ok, 1 - length error, 2 - consist error
    fun checkSurname(surname: String): Int {
        if (surname.length == 0) return 0
        if (surname.length < 2) return 1
        surname.forEach {
            if (!(it.isLetter())) return 2
        }
        return 0
    }
    //function checks user's birth date and returns one of 6 codes:
    // 0 - ok, 1 - length error, 2 - format error, 3- error in year, 4 - error in month, 5 -error in day
    fun checkDate(date: String): Int {
        if (date.length == 0) return 0
        if (date.length != 10) return 1
        for(i in 0..9) {
            if ((i == 2 || i == 5) && date[i] != '.')
                return 2
            else if ((i != 2 && i != 5) && !date[i].isDigit())
                return 2
        }
        val day = date.substring(0,2).toInt()
        val month = date.substring(3,5).toInt()
        val year = date.substring(6,10).toInt()
        val durations = arrayOf(31, 28,31,30,31,30,31,31,30,31,30,31)
        if(year < 1900 || year > 2022)
            return 3
        if(month > 12|| month < 1)
            return 4
        val maxDay = if(year % 4 == 0 && year % 100 != 0 && month == 2)
            29
        else
            durations[month-1]
        if(day < 1 || day > maxDay) return 5
        return 0
    }
    //function checks user's password and returns one of 6 codes:
    // 0 - ok, 1 - length error, 2 - not enough specific symbols, 3 - not enough uppercase letters,
    //4 - not enough lowercase letters, 5 - not enough digits
    fun checkPassword(password: String): Int {
        if (password.length == 0) return 0
        if (password.length < 8) return 1
        var countSpecificSymbols = 0
        var countUpperCaseLetters = 0
        var countLowerCaseLetters = 0
        var countDigits = 0

        password.forEach {
            if (it.isUpperCase())
                countUpperCaseLetters++
            else if (it.isLowerCase())
                countLowerCaseLetters++
            else if (it.isDigit())
                countDigits++
            else
                countSpecificSymbols++
        }

        if (countSpecificSymbols == 0) return 2
        if (countUpperCaseLetters == 0) return 3
        if (countLowerCaseLetters == 0) return 4
        if (countDigits == 0) return 5
        return 0
    }
    //function checks user's password reply and returns one of 2 codes: 0 - ok, 1 - passwords aren't the same
    fun checkPasswordReply(password: String, passwordReply: String): Int {
        if (passwordReply.length == 0) return 0
        if (password == passwordReply) return 0
        return 1
    }

    //function checks all fields and return true if all right
    fun checkConditionForButton(name: String, surname: String, birthDate: String, password: String, passwordReply: String): Boolean {
        if(name.length == 0 || surname.length == 0 || birthDate.length == 0 ||
            password.length == 0 || passwordReply.length == 0 ) return false
        if (!(checkName(name) == 0)) return false
        if (!(checkSurname(surname) == 0)) return false
        if (!(checkDate(birthDate) == 0)) return false
        if (!(checkPassword(password) == 0)) return false
        if (!(checkPasswordReply(password,passwordReply) == 0)) return false
        return true
    }

}