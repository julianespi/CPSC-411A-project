package com.example.myapplication

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    val selectInfo: String = "SELECT * FROM dish_info"
    val selectIngredients: String = "SELECT * FROM dish_ingredients"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS dish_info(" +
                    "dish_id TEXT PRIMARY KEY" +
                    ",dish_name TEXT" +
                    ",description TEXT" +
                    ",image_id TEXT" +
                    ",dish_type TEXT" +
                ")"
        )
        db?.execSQL(
            "CREATE TABLE IF NOT EXISTS dish_ingredients(" +
                    "dish_id TEXT" +
                    ",ingredient TEXT" +
                    ",ingredient_qty INTEGER" +
                ")"
        )
    }

    fun selectStrFromDb(dbName: String, id: Any?, column: String?): String?{
        if (dbName !in listOf("dish_info", "dish_ingredients") || column !in listOf("dish_id", "dish_name", "description", "image_id", "dish_type"))
            return null

        val db = this.readableDatabase

        val cursor : Cursor = db.rawQuery(
            "SELECT $column FROM $dbName WHERE dish_id = ?"
            ,arrayOf(id.toString())
        )

        val result = if (cursor.moveToFirst())
            cursor.getString(cursor.getColumnIndexOrThrow(column))
        else
            null

        cursor.close()
        return result
    }

    fun selectIngredientQty(ingredientName: String, id: Any?): Int?{
        val db = this.readableDatabase

        val cursor : Cursor = db.rawQuery(
            "SELECT ingredient_qty FROM dish_ingredients WHERE ingredient = ? AND dish_id = ?"
            ,arrayOf(ingredientName, id.toString())
        )

        val result = if (cursor.moveToFirst())
            cursor.getInt(cursor.getColumnIndexOrThrow("ingredient_qty"))
        else
            null

        cursor.close()
        return result
    }

    fun getSelectResults(inputQuery: String): List<Map<String, Any?>>{
        val result = mutableListOf<Map<String, Any?>>()
        val cursor = this.readableDatabase.rawQuery(inputQuery, null)

        cursor.use{if(cursor.moveToFirst()){
            do{
                val tuple = mutableMapOf<String, Any?>()

                for (columnIndex in 0 until cursor.columnCount)
                    tuple[cursor.getColumnName(columnIndex)] = cursor.getString(columnIndex)

                result.add(tuple)
            } while (cursor.moveToNext())
        }}

        cursor.close()
        return result
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


    companion object {
        const val DATABASE_NAME = "user_data.db"
        const val DATABASE_VERSION = 1
    }
}