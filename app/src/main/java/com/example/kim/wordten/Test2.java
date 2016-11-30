package com.example.kim.wordten;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Random;

/* Activity to test words */
public class Test2 extends AppCompatActivity {
    List<Word> list;
    int counter;
    int truth;
    int score;
    int current;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        Log.d("im in", "im on test2");
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        list = new Gson().fromJson(message,  new TypeToken<List<Word>>() {
        }.getType());

        counter = list.size();
        score = 0;

        if (counter>0) {
            displayQuestion(0);
            counter --;
        }
        current = 1;


      /*  TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(list.get(0).getSpell());

        ViewGroup layout = (ViewGroup) findViewById(R.id.activity_test);
        layout.addView(textView);*/


        // tvSpell tvPron tvMeaning
    }

    public void displayQuestion(int xcurrent){

            TextView tvExam = (TextView) findViewById(R.id.tvExam);
            tvExam.setText(list.get(xcurrent).getExam());
            TextView tvSpell = (TextView) findViewById(R.id.tvSpell);
            tvSpell.setText(list.get(xcurrent).getSpell());
            TextView tvPron = (TextView) findViewById(R.id.tvPron);
            tvPron.setText(list.get(xcurrent).getPron());
            TextView tvMeaning = (TextView) findViewById(R.id.tvMeaning);


            Random r = new Random();
            int randomMeaning = r.nextInt(2);
            if (randomMeaning ==0){
                tvMeaning.setText(list.get(xcurrent).getCorrect());
                truth = 1;
            } else {
                tvMeaning.setText(list.get(xcurrent).getWrong());
                truth = 0;
            }
            current++;
            counter--;

        /*}else{
            TextView tvError = (TextView) findViewById(R.id.tvInstr);
            tvError.setText("데이터베이스 에러: 관리자에게 문의하세요.");
        }*/

    }



    public void checkCorrect(View v) {
        TextView tv2 = (TextView) findViewById(R.id.textView11);
        if (truth ==1) {
            score++;

            tv2.setText("[ 정답! ]");
        } else {
            tv2.setText("[ 틀렸습니다. ]");
        }
        TextView tv = (TextView) findViewById(R.id.tvInstr);
        Button button = (Button) findViewById(R.id.buttonCorrect);
        Button button2 = (Button) findViewById(R.id.buttonWrong);
        if (counter==0) {
            tv.setText("수고하셨습니다. 점수는 " + score + "점 입니다.");
            button.setVisibility(View.GONE);
            button2.setVisibility(View.GONE);
        } else displayQuestion(current);
    }

    public void checkWrong(View v) {
        TextView tv2 = (TextView) findViewById(R.id.textView11);
        if (truth ==0) {
            score++;

            tv2.setText("[ 정답! ]");
        } else {
            tv2.setText("[ 틀렸습니다. ]");
        }
        TextView tv = (TextView) findViewById(R.id.tvInstr);
        Button button = (Button) findViewById(R.id.buttonCorrect);
        Button button2 = (Button) findViewById(R.id.buttonWrong);
        if (counter==0) {
            tv.setText("수고하셨습니다. 점수는 " + score + "점 입니다.");
            button.setVisibility(View.GONE);
            button2.setVisibility(View.GONE);
        } else displayQuestion(current);

    }

    }
