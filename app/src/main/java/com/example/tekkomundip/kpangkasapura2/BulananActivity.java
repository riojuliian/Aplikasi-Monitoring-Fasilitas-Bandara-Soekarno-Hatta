package com.example.tekkomundip.kpangkasapura2;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

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


public class BulananActivity extends AppCompatActivity {
    ListView listViewLaporanBulan;
    List<PelaporModel> pelaporList;
    DatabaseReference databasePelapor;
    Button btn;
    static Dialog dialog ;
    EditText mSearchFieldbulanan;
    PelaporList adapter;
    int year1 = Calendar.getInstance().get(Calendar.YEAR);
    int month = Calendar.getInstance().get(Calendar.MONTH)+1;
    Date date = new Date();
    Date newDate = new Date(date.getTime());
    SimpleDateFormat bulanAwal = new SimpleDateFormat("MMM", Locale.ENGLISH);

    String awalBulan = bulanAwal.format(newDate);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bulanan);
        listViewLaporanBulan = (ListView) findViewById(R.id.listViewLaporanBulan);
        btn = (Button) findViewById(R.id.btnPilihBulan);


        pelaporList = new ArrayList<>();

        btn.setText(awalBulan+" "+year1);


        databasePelapor = FirebaseDatabase.getInstance().getReference("View");
        //Query query2 = FirebaseDatabase.getInstance().getReference("Pelaporan").child("Terminal1").child("Unit1")
        Query query2 = FirebaseDatabase.getInstance().getReference("View")
                .orderByChild("BulanTahun")
                .equalTo(String.valueOf(btn.getText()));
        query2.addValueEventListener(valueEventListener);

         /*Query query = FirebaseDatabase.getInstance().getReference("Pelaporan").child("Terminal1").child("Unit1")
                .orderByChild("Bulan")
                .equalTo("01");
        query.addValueEventListener(valueEventListener);*/



        btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {

                showMonthYearDialog();
            }
        });
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
    private void initView(){

    }
    public void onBackPressed() {
        Intent reg = new Intent(this, MainActivity2.class);
        startActivity(reg);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
        finish();
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            pelaporList.clear();

            for (DataSnapshot pelaporSnapshot : dataSnapshot.getChildren()){
                PelaporModel pelapor = pelaporSnapshot.getValue(PelaporModel.class);
                pelaporList.add(pelapor);

            }
            if (dataSnapshot.getValue() != null){

                adapter = new PelaporList(BulananActivity.this, pelaporList);
                listViewLaporanBulan.setAdapter(adapter);
                mSearchFieldbulanan = findViewById(R.id.mSearchFieldbulanan);
                mSearchFieldbulanan.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence cs, int start, int before, int count) {
                        int textlength = cs.length();
                        ArrayList<PelaporModel> tesList = new ArrayList<PelaporModel>();
                        for(PelaporModel c: pelaporList){
                            if (textlength <= c.getBarang().length()) {
                                if (c.getBarang().toLowerCase().contains(cs.toString().toLowerCase())) {
                                    tesList.add(c);
                                }
                            }
                        }
                        adapter = new PelaporList(BulananActivity.this, tesList);
                        listViewLaporanBulan.setAdapter(adapter);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }else{
                PelaporList adapter = new PelaporList(BulananActivity.this, pelaporList);
                listViewLaporanBulan.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), "Belum ada Laporan", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void showMonthYearDialog()
    {

        final Dialog dialog = new Dialog(BulananActivity.this);
        dialog.setTitle("Month Picker");
        dialog.setContentView(R.layout.monthyeardialog);
        Button set1 = (Button) dialog.findViewById(R.id.btnSet1);
        Button cancel1 = (Button) dialog.findViewById(R.id.btnCancel1);
        TextView year_text1 =(TextView)dialog.findViewById(R.id.year_text1);


        final NumberPicker nopicker3 = (NumberPicker) dialog.findViewById(R.id.bulanPicker) ;
        final NumberPicker nopicker2 = (NumberPicker) dialog.findViewById(R.id.numberPicker2);

        nopicker2.setMaxValue(year1+50);
        nopicker2.setMinValue(year1-50);
        nopicker2.setWrapSelectorWheel(false);
        nopicker2.setValue(year1);
        nopicker2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        nopicker3.setWrapSelectorWheel(false);
        nopicker3.setMinValue(1);
        nopicker3.setMaxValue(12);
        nopicker3.setValue(month);
        String[] arrayString= new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul",
                "Aug","Sep","Oct","Nov","Dec"};
        nopicker3.setDisplayedValues(arrayString);
        nopicker3.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        year_text1.setText(awalBulan+" "+year1);


        //get string from number picker and position


        nopicker3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                newVal = nopicker3.getValue()-1;
                String selectPicker = arrayString[newVal];
                year_text1.setText(selectPicker+" "+ nopicker2.getValue());

            }
        });

        nopicker2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                oldVal = nopicker3.getValue()-1;
                String selectPicker1 = arrayString[oldVal];
                newVal = nopicker2.getValue();
                year_text1.setText(selectPicker1+" "+newVal);
            }
        });


        set1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                btn.setText(String.valueOf(year_text1.getText()));
                String bulan = btn.getText().toString();
                databasePelapor = FirebaseDatabase.getInstance().getReference("View");
                Query query1 = FirebaseDatabase.getInstance().getReference("View")
                        .orderByChild("BulanTahun")
                        .equalTo(String.valueOf(bulan));
                query1.addValueEventListener(valueEventListener);
                dialog.dismiss();


            }
        });
        cancel1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


}