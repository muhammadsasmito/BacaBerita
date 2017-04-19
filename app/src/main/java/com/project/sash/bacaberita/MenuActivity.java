package com.project.sash.bacaberita;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    ArrayList<KategoriData> kategoriList;
    KategoriAdapter kategoriAdapter;

    ListView lvKategori;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        lvKategori = (ListView) findViewById(R.id.lvKategori);

        lvKategori.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                KategoriData kategoriData = (KategoriData) lvKategori.getItemAtPosition(position);
                Intent i = new Intent(MenuActivity.this,BeritaActivity.class);
                i.putExtra("katid",kategoriData.idKategori+"");
                i.putExtra("judulkat",kategoriData.judulKategori+"");
                startActivity(i);
            }
        });

        dummyData();
    }

    public void dummyData(){
        kategoriList = new ArrayList<>();

        KategoriData utama = new KategoriData();
        utama.judulKategori = "Berita Utama";
        utama.gambarKategori = null;
        utama.idKategori = 1;
        kategoriList.add(utama);

        KategoriData fin = new KategoriData();
        fin.judulKategori = "Finansial";
        fin.gambarKategori = null;
        fin.idKategori = 2;
        kategoriList.add(fin);

        KategoriData sport = new KategoriData();
        sport.judulKategori = "Olahraga";
        sport.gambarKategori = null;
        sport.idKategori = 3;
        kategoriList.add(sport);

        KategoriData seleb = new KategoriData();
        seleb.judulKategori = "Selebritis";
        seleb.gambarKategori = null;
        seleb.idKategori = 4;
        kategoriList.add(seleb);


        KategoriData kes = new KategoriData();
        kes.judulKategori = "Kesehatan";
        kes.gambarKategori = null;
        kes.idKategori = 5;
        kategoriList.add(kes);

        KategoriData tech = new KategoriData();
        tech.judulKategori = "Teknologi";
        tech.gambarKategori = null;
        tech.idKategori = 6;
        kategoriList.add(tech);

        KategoriData oto = new KategoriData();
        oto.judulKategori = "Otomotif";
        oto.gambarKategori = null;
        oto.idKategori = 7;
        kategoriList.add(oto);

        kategoriAdapter = new KategoriAdapter(this,kategoriList);
        lvKategori.setAdapter(kategoriAdapter);
    }

}
