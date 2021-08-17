package com.example.watkilab11tomasik;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv,lv2;
    ArrayList<Objecto> lista = new ArrayList<>();
    ArrayList<Objecto> lista2 = new ArrayList<>();
    BaseAdapter adb,adb2;
    EditText ed;
    ProgressBar pr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.lv);
        lv2 = findViewById(R.id.lv2);
        ed = findViewById(R.id.editTextTextPersonName);
        pr = findViewById(R.id.progressBar2);
        adb =  new BaseAdapter() {
            @Override
            public int getCount() {
                return lista.size();
            }

            @Override
            public Object getItem(int i) {
                return lista.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View convertView, ViewGroup viewGroup) {
                if(convertView == null) {

                    convertView = getLayoutInflater().inflate(R.layout.adapterlay, null);



                    TextView tt = convertView.findViewById(R.id.textView);
                    tt.setText(lista.get(i).dana);
                    ProgressBar bar = convertView.findViewById(R.id.progressBar);
                    bar.setProgress(lista.get(i).progress);
                }
                return convertView;
            }
        };
        adb2 =  new BaseAdapter() {
            @Override
            public int getCount() {
                return lista2.size();
            }

            @Override
            public Object getItem(int i) {
                return lista2.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View convertView, ViewGroup viewGroup) {
                if(convertView == null) {

                    convertView = getLayoutInflater().inflate(R.layout.adapterlay, null);



                    TextView tt = convertView.findViewById(R.id.textView);
                    tt.setText(lista2.get(i).dana);
                    ProgressBar bar = convertView.findViewById(R.id.progressBar);
                    bar.setProgress(lista2.get(i).progress);
                }
                return convertView;
            }
        };
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (lista.get(i).thr != null && lista.get(i).thr.isAlive())
                    lista.get(i).thr.interrupt();
            }
        });
        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                    if (lista2.get(i).mat != null)
                        lista2.get(i).mat.cancel(true);

            }
        });

    }

    int p = 0;
    public void zmiana(View view)
    {
        if(ed.getText()!=null&&Integer.parseInt(ed.getText().toString())!=0)
        {
        p++;
        Objecto bob = new Objecto(Integer.toString(p));
        lista2.add(bob);
        lv2.setAdapter(adb2);
        bob.dzielnik = Integer.parseInt(ed.getText().toString());
        bob.mat = new MyAsyncTask();
        bob.mat.execute(bob);
        }
    }
    int i = 0;
    public void dodajelem(View view)
    {
        if(ed.getText()!=null &&Integer.parseInt(ed.getText().toString())!=0){
        i++;
        Objecto ob = new Objecto(Integer.toString(i));
        lista.add(ob);
        ob.thr = new Thread(new Runnable() {
            @Override
            public void run() {
                long licznik = 0;
                long n = 100000000;
                int xx = Integer.parseInt(ed.getText().toString());
                Editable ddd = ed.getText();
                while (licznik < n && !ob.thr.isInterrupted()) {
                    licznik++;
                    if(licznik%xx==0 && ddd!=null)
                        ob.x++;
                    if(licznik%1000000==0)
                    {
                    ob.progress++;
                    ob.dana=Integer.toString(ob.x);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lv.setAdapter(adb);
                        }
                    });}

                }
            }
        });
        ob.thr.start();
    }}
    Thread thr;
    public void sync(View view)
    {

        thr = new Thread(new Runnable() {
            @Override
            public void run() {
                dod();
            }
        });
        thr.start();

    }

    public synchronized void dod()
    {
        int licznik = 0;
        int n = 100;
        while (licznik < n && !thr.isInterrupted()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
            licznik++;
            pr.setProgress(100 * licznik / n);

        }
    }


    class MyAsyncTask extends AsyncTask<Objecto, Objecto, List<String>> {

        @Override
        protected List<String> doInBackground(Objecto... objectos) {
            Objecto bob = objectos[0];
            long licznik = 0;
            while (licznik < 100000000) {
                licznik++;
                if (licznik % bob.dzielnik==0 && bob.dzielnik!=0)
                    bob.x++;
                if (licznik % 1000000 == 0) {
                    bob.progress++;
                    bob.dana = Integer.toString(bob.x);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            lv2.setAdapter(adb2);
                        }
                    });

                }

                if (this.isCancelled())
                    break;
            }
            return null;
        }

    }





}