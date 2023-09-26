package com.example.greetingapp.repository.model

import android.provider.BaseColumns

//object describes table with users data in database
object GreetingAppDBNameClass: BaseColumns {
    const val TABLE_NAME = "UsersDataTable"

    //columns
    const val COLUMN_NAME_NAME = "name"
    const val COLUMN_NAME_SURNAME = "surname"
    const val COLUMN_NAME_BIRTH_DATE = "birth_date"
    const val COLUMN_NAME_PASSWORD = "password"

    //info about database
    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "GreetingAppDB.db"

    //this sgl request creates table
    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_NAME TEXT," +
            "$COLUMN_NAME_SURNAME TEXT, $COLUMN_NAME_BIRTH_DATE TEXT, $COLUMN_NAME_PASSWORD INTEGER )"

    //this sql request deletes table
    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}
