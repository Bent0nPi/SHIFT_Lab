package com.example.greetingapp.repository.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GreetingDBHelper(context: Context) : SQLiteOpenHelper(context,
    GreetingAppDBNameClass.DATABASE_NAME,null,
    GreetingAppDBNameClass.DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(GreetingAppDBNameClass.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(GreetingAppDBNameClass.SQL_DELETE_TABLE)
        onCreate(db)
    }

}