package com.example.kim.wordten;

import android.util.Log;

/**
 * Created by kim on 2016-11-02.
 */

public class Word {
    private int id;
    private String spell;
    private String pron;
    private String correct;
    private String wrong;
    private String exam;
    private String trans;
    private int diff;
    private int weight;

    public Word(){}

    public Word(int id, String spell, String pron, String correct,
                String wrong, String exam, String trans, int diff, int weight) {
        super();
        this.id = id;
        this.spell = spell;
        this.pron = pron;
        this.correct = correct;
        this.wrong = wrong;
        this.exam = exam;
        this.trans = trans;
        this.diff = diff;
        this.weight = weight;
    }

    //getters & setters
    // list of getters
    public int getId(){
        return id;
    }

    public String getSpell(){
        return spell;
    }

    public String getPron(){
        return pron;
    }
    public String getCorrect(){
        return correct;
    }

    public String getWrong(){
        return wrong;
    }

    public String getExam(){
        return exam;
    }

    public String getTrans(){
        return trans;
    }

    public int getDiff(){
        return diff;
    }

    public int getWeight(){
        return weight;
    }

    //setter;


    public void upWeight(){
        this.weight++;
    }

    public void downWeight(){
        if (this.weight > 1){
            this.weight--;
        }else{
            Log.d("downWeight", "Weight of " + this.spell + " is already 0");
        }
    }

    @Override
    public String toString() {
        return "Word [id=" + id + ", spell=" + spell + ", pron=" + pron
                +  "예시: "+ exam+"]";
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public void setPron(String pron) {
        this.pron = pron;
    }


    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public void setWrong(String wrong) {
        this.wrong = wrong;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }
}
