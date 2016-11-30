package com.example.kim.wordten;

/**
 * Created by kim on 2016-11-02.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;
    private static final String KEY_ID = "id";
    private static final String KEY_SPELL = "spell";
    private static final String KEY_PRON = "pron";
    private static final String KEY_CORRECT = "correct";
    private static final String KEY_WRONG = "wrong";
    private static final String KEY_EXAM = "example";
    private static final String KEY_TRANS = "trans";
    private static final String KEY_DIFF = "diff";
    private static final String KEY_WEIGHT = "weight";

    private static final String[] COLUMNS = {KEY_ID, KEY_SPELL, KEY_PRON, KEY_CORRECT, KEY_WRONG, KEY_EXAM, KEY_TRANS, KEY_DIFF, KEY_WEIGHT};

    /**
     * Private constructor to avoid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<String> getQuotes() {
        List<String> list = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM quotes", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }


    public Word getWord(int diff, int pref){
       // 1. get reference to readable DB
        this.database = openHelper.getReadableDatabase();

        // 2. build query
        Cursor cursor =      database.query("words", // a. table
                COLUMNS, // b. column names
                "diff = ?", // c. selections
                new String[] {String.valueOf(diff) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

    //int id,        String.valueOf(id),         id = ? and
        //database.rawQuery("SELECT * FROM Words", null);



        // 3. if we got results get the first one
        if (cursor != null) {

            Log.d("cursorCount", "cursor size is: "+ cursor.getCount());
            cursor.moveToFirst();
        }
        // 4. build word object
        Word word = new Word();

        word.setId(Integer.parseInt(cursor.getString(0)));
        word.setSpell(cursor.getString(1));
        word.setPron(cursor.getString(2));

        //get correct
        word.setCorrect(cursor.getString(3));
        //get wrong
        word.setWrong(cursor.getString(4));

        //get exam
        word.setExam(cursor.getString(5));


        Log.d("getWord("+diff+")", word.toString());

        // 5. return word
        return word;
    }

    // Get Ten Words
    public List<Word> getTenWords(int diff, int pref) {
        List<Word> words = new LinkedList<Word>();

        // 2. build query
        Cursor cursor =      database.query("words", // a. table
                COLUMNS, // b. column names
                "diff = ?", // c. selections
                new String[] {String.valueOf(diff) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit


        // 2. get reference to writable DB
        this.database = openHelper.getWritableDatabase();

        if (cursor != null) {

            Log.d("cursorCount", "cursor size is: "+ cursor.getCount());

        }

        // 3. go over each row, build word and add it to list
        Word word = null;
        if (cursor.moveToPosition(pref)) {
            for (int i=0; i<10; i++){
                if(cursor.moveToNext()) {
                    word = new Word();
                    word.setId(Integer.parseInt(cursor.getString(0)));
                    word.setSpell(cursor.getString(1));
                    word.setPron(cursor.getString(2));
                    word.setCorrect(cursor.getString(3));
                    word.setWrong(cursor.getString(4));
                    word.setExam(cursor.getString(5));
                    // Add word to words
                    words.add(word);
                }
            }
        }

        Log.d("getTenWords()", words.toString());

        // return words
        return words;
    }

    // Get All Words
    public List<Word> getAllWords() {
        List<Word> words = new LinkedList<Word>();

        // 1. build the query
        String query = "SELECT  * FROM " + "words";

        // 2. get reference to writable DB
        this.database = openHelper.getWritableDatabase();
        Cursor cursor = this.database.rawQuery(query, null);

        // 3. go over each row, build word and add it to list
        Word word = null;
        if (cursor.moveToFirst()) {
            do {
                word = new Word();
                word.setId(Integer.parseInt(cursor.getString(0)));
                word.setSpell(cursor.getString(1));
                word.setPron(cursor.getString(2));

                // Add word to words
                words.add(word);
            } while (cursor.moveToNext());
        }

        Log.d("getAllWords()", words.toString());

        // return words
        return words;
    }


    // Updating single word
    public int updateWord(Word word) {

        // 1. get reference to writable DB
        this.database = openHelper.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put("title", word.getSpell()); // get title
        values.put("author", word.getWeight()); // get author

        // 3. updating row
        int i = this.database.update("words", //table
                values, // column/value
                KEY_ID+" = ?", // selections
                new String[] { String.valueOf(word.getId()) }); //selection args

        // 4. close
        this.database.close();

        return i;

    }


}