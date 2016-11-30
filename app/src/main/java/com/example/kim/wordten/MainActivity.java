package com.example.kim.wordten;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.kim.wordten";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //set difficulty spinner
        Spinner spinner = (Spinner) findViewById(R.id.spinner_diff);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.diff_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    public void startTest(View v){
        TextView tv = (TextView)findViewById(R.id.startStatusText);
        tv.setText("시작");




        Intent intent = new Intent(this, Test2.class);

        Spinner spinner = (Spinner) findViewById(R.id.spinner_diff);
        String message = spinner.getSelectedItem().toString();
        int diff=0;

        if (message.equalsIgnoreCase("중1")) {
            diff = 7;
        } else if(message.equalsIgnoreCase("중2")) {
            diff = 6;
        } else if(message.equalsIgnoreCase("중3")) {
            diff = 5;
        } else if(message.equalsIgnoreCase("고1")) {
            diff = 4;
        } else if(message.equalsIgnoreCase("고2")) {
            diff = 3;
        } else if(message.equalsIgnoreCase("고3 기본")) {
            diff = 2;
        } else if(message.equalsIgnoreCase("고3 심화")) {
            diff = 1;
        }






            // String message = tv.getText().toString();

        //string message


        //getting word from the database
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();

       SharedPreferences sp = getSharedPreferences("DIFFICULTY_BOOKMARK", MODE_PRIVATE);

       /*  SharedPreferences.Editor editor = sp.edit();
        editor.putString("1", "hrere");
        editor.commit();*/
        if (!sp.contains(Integer.toString(diff))){
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Integer.toString(diff), "0");
            editor.commit();
        }else{
            int pref = Integer.parseInt(sp.getString(Integer.toString(diff),"")) + 10;
            if (pref>190) pref =10;
            SharedPreferences.Editor editor = sp.edit();
            editor.putString(Integer.toString(diff),Integer.toString(pref));
            editor.commit();
            Log.d("logKey3","sharedPreference 현재 pref: "+ sp.getString(Integer.toString(diff),""));
        }
        List<Word> list = databaseAccess.getTenWords(diff,Integer.parseInt(sp.getString(Integer.toString(diff),"")));
      //  databaseAccess.getTenWords(diff,Integer.parseInt(sp.getString(Integer.toString(diff),"")));
        //List<String> wordList = databaseAccess.getQuotes();
        if(list.size()> 9) {
            Log.d("fetch1", list.get(0).toString());
            Log.d("fetch10", list.get(9).toString());
        }
        databaseAccess.close();

        /* Gson */
         String gson = new Gson().toJson(list);


        intent.putExtra(EXTRA_MESSAGE, gson);
        startActivity(intent);
    }

}
