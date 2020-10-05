package com.example.tekkomundip.kpangkasapura2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LihatBarangSummary extends AppCompatActivity {

    Spinner spinnerArea;
    Button btnBack;
    ArrayList<Barang> barangList;
    DatabaseReference databaseBarang;
    ListView listView;
    int notificationID = 1;
    String getTerminal;

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_barang_summary);
        initView();
        spinner_area();
        btnback();

    }
    private void spinner_area(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Area_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerArea.setAdapter(adapter);
        spinnerArea.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                        //showToast("Spinner2: position=" + position + " id=" + id);
                        if (position == 0){
                            getTerminal=spinnerArea.getSelectedItem().toString().trim();
                            showToast("Terminal 1");
                            databaseValueEventListener();
                            databaseAddChildEventListener();
                        }
                        else if (position == 1){
                            getTerminal=spinnerArea.getSelectedItem().toString().trim();
                            showToast("Terminal 2");
                            databaseValueEventListener();
                            databaseAddChildEventListener();
                        }
                        else if (position == 2){
                            getTerminal=spinnerArea.getSelectedItem().toString().trim();
                            showToast("Terminal 3");
                            databaseValueEventListener();
                            databaseAddChildEventListener();
                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                        showToast("Spinner2: unselected");
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        spinnerArea =  findViewById(R.id.cb_Area);
        btnBack = findViewById(R.id.btnkembalilihatbarang);
        listView = (ListView) findViewById(R.id.ListBarang);
        barangList = new ArrayList<>();
        getTerminal=spinnerArea.getSelectedItem().toString().trim();
    }

    public void onBackPressed() {
        Intent reg = new Intent(this, MainActivity2.class);
        startActivity(reg);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
        finish();
    }

    private void addNotification() {
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("notificationID", notificationID);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notifBuilder;
        notifBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Terdapat barang rusak")
                .setContentText("Mohon segera diperbaiki")
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000})
                .setLights(Color.WHITE, 3000, 3000)
                .addAction(R.mipmap.ic_launcher,"Read More",pendingIntent);

        nm.notify(notificationID, notifBuilder.build());
    }

    private void databaseValueEventListener(){
        databaseBarang = FirebaseDatabase.getInstance().getReference("Barang").child(getTerminal).child("DigitalBanner");
        databaseBarang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                barangList.clear();

                for(DataSnapshot barangSnapshot : dataSnapshot.getChildren()){
                    Barang fasilitas = barangSnapshot.getValue(Barang.class);

                    barangList.add(fasilitas);

                }
                BarangList adapter = new BarangList(LihatBarangSummary.this, barangList);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        }) ;
    }

    private void databaseAddChildEventListener(){
        databaseBarang.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addNotification();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void btnback(){
        btnBack.setOnClickListener(V -> {
            Intent reg = new Intent(this, MainActivity2.class);
            startActivity(reg);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
            finish();}
        );
    }

}