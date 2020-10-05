package com.example.tekkomundip.kpangkasapura2;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
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


public class LihatBarangActivity extends AppCompatActivity {

    Spinner spinnerArea;
    Button btnDetail;
    TextView tvTerminal,tvnamabarang;
    Button btnBack;
    ArrayList<Barang> barangList;
    DatabaseReference databaseBarang, databaseBarang1, databaseBarang2;
    ListView listView;
    int notificationID = 1;
    String terminal1 = Login3Activity.getTempat();
    private static final String CHANNEL_ID = "";
    Dialog myDialog, myDialog2;
    View v;
    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_barang);
        initView();
        btnback();
        String namabarang = BarangList.getData6();
        //tvnamabarang.setText(namabarang);
        listView = (ListView) findViewById(R.id.ListBarang);
        barangList = new ArrayList<>();

        databaseBarang = FirebaseDatabase.getInstance().getReference("Barang").child(terminal1).child("DigitalBanner");



    }

    /*ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot barangSnapshot : dataSnapshot.getChildren()){
                Barang barang = barangSnapshot.getValue(Barang.class);

                barangList.add(barang);

            }

            BarangList adapter = new BarangList(LihatBarangActivity.this, barangList);
            listView.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };*/

    @Override
    protected void onStart() {
        super.onStart();
        databaseBarang.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                barangList.clear();

                for(DataSnapshot barangSnapshot : dataSnapshot.getChildren()){
                    Barang barang = barangSnapshot.getValue(Barang.class);
                    barangList.add(barang );
                }
                BarangList adapter = new BarangList(LihatBarangActivity.this, barangList);
                listView.setAdapter(adapter );
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int p, long id) {
                    if(p == 1){
                        Toast.makeText(getApplicationContext(),"ngamukk",Toast.LENGTH_LONG).show();
                    }
                    }
                });

                /*listView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),"ngamukk",Toast.LENGTH_LONG).show();
                    }
                });*/

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        }) ;

    }

    private void initView() {
        tvTerminal = findViewById(R.id.textViewTerminal);
        btnBack = findViewById(R.id.btnkembalilihatbarang);
        tvTerminal.setText(terminal1);

        }

    public void onBackPressed() {
        Intent reg = new Intent(this, MainActivity.class);
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
        notifBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Terdapat barang rusak")
                .setContentText("Mohon segera diperbaiki")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000})
                .setLights(Color.WHITE, 3000, 3000)
                .addAction(R.mipmap.ic_launcher,"Read More",pendingIntent);

        nm.notify(notificationID, notifBuilder.build());
    }

    private void btnback(){
        btnBack.setOnClickListener(V -> {
            Intent reg = new Intent(this, LaporActivity.class);
            startActivity(reg);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
            finish();}
        );
    }



}
