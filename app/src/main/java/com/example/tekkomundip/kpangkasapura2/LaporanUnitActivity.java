package com.example.tekkomundip.kpangkasapura2;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static com.example.tekkomundip.kpangkasapura2.BulananActivity.dialog;


public class LaporanUnitActivity extends AppCompatActivity {
    ListView lvLaporUnit;
    List<PelaporModelUnit> pelaporListUnit;
    DatabaseReference databasePelapor;
    Button btnDate,btnSearch;
    EditText mSearchField;
    PelaporListUnit adapter;
    Query queryText;
    static Dialog dateDialog ;
    int calYear = Calendar.getInstance().get(Calendar.YEAR);
    int calMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
    int calDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    Date date = new Date();
    Date newDate = new Date(date.getTime());
    SimpleDateFormat bulanAwal = new SimpleDateFormat("MMM", Locale.ENGLISH);
    String awalBulan = bulanAwal.format(newDate);
    private static final String CHANNEL_ID = "";
    int notificationID = 1;

    String unitlogin = Login3Activity.getBagian();
    String terminallogin = Login3Activity.getTempat();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_unit);

        lvLaporUnit = (ListView) findViewById(R.id.lvLaporUnit);
        mSearchField = (EditText) findViewById(R.id.mSearchField);
        pelaporListUnit = new ArrayList<>();
        btnDate = (Button) findViewById(R.id.btnDate);

        btnDate.setText(calDay+" "+awalBulan+" "+calYear);

        databasePelapor = FirebaseDatabase.getInstance().getReference("Pelaporan");
        Query query2 = FirebaseDatabase.getInstance().getReference("Pelaporan").child(terminallogin).child(unitlogin)
                .orderByChild("HariBulanTahun")
                .equalTo(String.valueOf(btnDate.getText()));
        query2.addValueEventListener(valueEventListener);

        //btnDate.setText("     Pilih Tanggal");
        databasePelapor = FirebaseDatabase.getInstance().getReference("Pelaporan");
        /*Query query = FirebaseDatabase.getInstance().getReference("Pelaporan").child("Terminal1").child("Unit1")
                .orderByChild("Bulan")
                .equalTo("01");
        query.addValueEventListener(valueEventListener);*/
        btnDate.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                showDateDialog();

            }
        });
/*
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = mSearchField.getText().toString();

                firebaseSearch(searchText);
            }
        });
*/

    }

    public class tahun{
        String testahun;

        public tahun(String testahun) {
            this.testahun = testahun;
        }

        public String getTestahun() {
            return testahun;
        }

        public tahun(){

        }
    }

    public void onBackPressed() {
        Intent reg = new Intent(this, MainActivity3.class);
        startActivity(reg);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
        finish();
    }

    private void firebaseSearch(String searchText){
        databasePelapor = FirebaseDatabase.getInstance().getReference("Pelaporan");
        Query search= FirebaseDatabase.getInstance().getReference("Pelaporan").child("Terminal2").child("Unit1")
                .orderByChild("barang")
                .startAt(searchText)
                .endAt(searchText+"\uf8ff");
        search.addValueEventListener(valueEventListener);
    }

    private void showDateDialog()
    {

        final Dialog dateDialog = new Dialog(LaporanUnitActivity.this);
        dateDialog.setTitle("Date Picker");
        dateDialog.setContentView(R.layout.datedialog);
        Button set1 = (Button) dateDialog.findViewById(R.id.btnSet);
        Button cancel1 = (Button) dateDialog.findViewById(R.id.btnCancel);
        TextView date =(TextView)dateDialog.findViewById(R.id.date);

        final NumberPicker dayPicker = (NumberPicker) dateDialog.findViewById(R.id.dayPicker);
        final NumberPicker monthPicker = (NumberPicker) dateDialog.findViewById(R.id.pickerMonth);
        final NumberPicker yearPicker = (NumberPicker) dateDialog.findViewById(R.id.yearPicker);

        yearPicker.setMaxValue(calYear+50);
        yearPicker.setMinValue(calYear-50);
        yearPicker.setWrapSelectorWheel(false);
        yearPicker.setValue(calYear);
        yearPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        monthPicker.setWrapSelectorWheel(false);
        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);
        monthPicker.setValue(calMonth);
        String[] arrayString= new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul",
                "Aug","Sep","Oct","Nov","Dec"};
        monthPicker.setDisplayedValues(arrayString);
        monthPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        dayPicker.setMaxValue(31);
        dayPicker.setMinValue(01);
        dayPicker.setValue(calDay);
        dayPicker.setWrapSelectorWheel(false);
        dayPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        date.setText(calDay+" "+awalBulan+" "+calYear);

        dayPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newVal = dayPicker.getValue();
                oldVal = monthPicker.getValue()-1;
                String selectPicker = arrayString[oldVal];
                int selectPicker1 = yearPicker.getValue();
                date.setText(newVal+" "+selectPicker+" "+selectPicker1);
            }
        });

        monthPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newVal = monthPicker.getValue()-1;
                String selectPicker = arrayString[newVal];
                oldVal = dayPicker.getValue();
                date.setText(oldVal+" "+selectPicker+" "+ yearPicker.getValue());

            }
        });

        yearPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                oldVal = monthPicker.getValue()-1;
                String selectPicker1 = arrayString[oldVal];
                newVal = yearPicker.getValue();
                int selectPicker = dayPicker.getValue();
                date.setText(selectPicker+" "+selectPicker1+" "+newVal);
            }
        });

        set1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                btnDate.setText(String.valueOf(date.getText()));
                String tahun = btnDate.getText().toString();
                databasePelapor = FirebaseDatabase.getInstance().getReference("Pelaporan");
                Query query1 = FirebaseDatabase.getInstance().getReference("Pelaporan").child(terminallogin).child(unitlogin)
                        .orderByChild("HariBulanTahun")
                        .equalTo(String.valueOf(tahun));
                query1.addValueEventListener(valueEventListener);
                dateDialog.dismiss();

            }
        });
        cancel1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dateDialog.dismiss();
            }
        });
        dateDialog.show();
    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            pelaporListUnit.clear();

            for (DataSnapshot pelaporSnapshot : dataSnapshot.getChildren()){
                PelaporModelUnit pelaporUnit = pelaporSnapshot.getValue(PelaporModelUnit.class);
                pelaporListUnit.add(pelaporUnit);

            }
            if (dataSnapshot.getValue() != null){

                adapter = new PelaporListUnit(LaporanUnitActivity.this, pelaporListUnit);
                lvLaporUnit.setAdapter(adapter);
                mSearchField.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence cs, int start, int before, int count) {
                        int textlength = cs.length();
                        ArrayList<PelaporModelUnit> tesList = new ArrayList<PelaporModelUnit>();
                        for(PelaporModelUnit c: pelaporListUnit){
                            if (textlength <= c.getBarang().length()) {
                                if (c.getBarang().toLowerCase().contains(cs.toString().toLowerCase())) {
                                    tesList.add(c);
                                }
                            }
                        }
                        adapter = new PelaporListUnit(LaporanUnitActivity.this, tesList);
                        lvLaporUnit.setAdapter(adapter);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

                databasePelapor.child(terminallogin).child(unitlogin).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        addNotification();
                        createNotificationChannel();
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

            }else{
                PelaporListUnit adapter = new PelaporListUnit(LaporanUnitActivity.this, pelaporListUnit);
                lvLaporUnit.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), "Belum ada Laporan", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void addNotification() {
        Intent i = new Intent(this, MainActivity3.class);
        i.putExtra("notificationID", notificationID);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, i, 0);
        NotificationManager nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder notifBuilder;
        notifBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle("Terdapat perubahan status barang")
                .setContentText("Mohon segera dicek")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000, 2000})
                .setLights(Color.WHITE, 3000, 3000)
                .addAction(R.mipmap.ic_launcher,"Read More",pendingIntent);

        nm.notify(notificationID, notifBuilder.build());
    }
    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
