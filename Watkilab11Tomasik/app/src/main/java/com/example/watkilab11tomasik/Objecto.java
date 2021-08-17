package com.example.watkilab11tomasik;

public class Objecto {

    String dana = "dana";
    int progress = 0;
    Thread thr;
    int x = 0;
    MainActivity.MyAsyncTask mat;
    int dzielnik = 0;

    public Objecto(String dana) {
        this.dana = dana;
    }

    public Objecto(String dana, int progress) {
        this.dana = dana;
        this.progress = progress;
    }
}
