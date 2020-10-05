package com.example.tekkomundip.kpangkasapura2;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.LinearLayoutManager;

import android.support.v7.widget.RecyclerView;



import java.util.ArrayList;



public class LaporanTeknisiActivity extends AppCompatActivity {



    private RecyclerView recyclerView;

    private AdapterTeknisi adapter;

    private ArrayList<PelaporModelUnit> TeknisiArrayList;



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_laporan_teknisi);







        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);



        adapter = new AdapterTeknisi(TeknisiArrayList);



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(LaporanTeknisiActivity.this);



        recyclerView.setLayoutManager(layoutManager);



        recyclerView.setAdapter(adapter);

    }








}