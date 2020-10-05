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

import static com.example.tekkomundip.kpangkasapura2.BulananActivity.dialog;


public class HarianActivity extends AppCompatActivity {
    ListView lvLaporHarian;
    List<PelaporModel> pelaporList;
    DatabaseReference databasePelapor;
    Button btnDate,btnSearch;
    EditText mSearchField;
    PelaporList adapter;
    Query queryText;
    static Dialog dateDialog ;
    int calYear = Calendar.getInstance().get(Calendar.YEAR);
    int calMonth = Calendar.getInstance().get(Calendar.MONTH)+1;
    int calDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    Date date = new Date();
    Date newDate = new Date(date.getTime());
    SimpleDateFormat bulanAwal = new SimpleDateFormat("MMM", Locale.ENGLISH);

    String awalBulan = bulanAwal.format(newDate);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harian);
        lvLaporHarian = (ListView) findViewById(R.id.lvLaporHarian);
        mSearchField = (EditText) findViewById(R.id.mSearchField);
        pelaporList = new ArrayList<>();
        btnDate = (Button) findViewById(R.id.btnDate);



        btnDate.setText(calDay+" "+awalBulan+" "+calYear);


        databasePelapor = FirebaseDatabase.getInstance().getReference("View");
        Query query2 = FirebaseDatabase.getInstance().getReference("View")
                .orderByChild("HariBulanTahun")
                .equalTo(String.valueOf(btnDate.getText()));
        query2.addValueEventListener(valueEventListener);




        databasePelapor = FirebaseDatabase.getInstance().getReference("View");
        btnDate.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                showDateDialog();

            }
        });
    }

    public void onBackPressed() {
        Intent reg = new Intent(this, MainActivity2.class);
        startActivity(reg);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
        finish();
    }

    private void firebaseSearch(String searchText){
        databasePelapor = FirebaseDatabase.getInstance().getReference("View");
        Query search= FirebaseDatabase.getInstance().getReference("View")
                .orderByChild("barang")
                .startAt(searchText)
                .endAt(searchText+"\uf8ff");
        search.addValueEventListener(valueEventListener);
    }

    private void showDateDialog()
    {

        final Dialog dateDialog = new Dialog(HarianActivity.this);
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
        int pos = monthPicker.getValue()-1;
        //get string from number picker and position
        String selectPicker = arrayString[pos];
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
                databasePelapor = FirebaseDatabase.getInstance().getReference("View");
                //Query query1 = FirebaseDatabase.getInstance().getReference("Pelaporan").child("Terminal1").child("Unit1")
                Query query1 = FirebaseDatabase.getInstance().getReference("View")
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
            pelaporList.clear();

            for (DataSnapshot pelaporSnapshot : dataSnapshot.getChildren()){
                PelaporModel pelapor = pelaporSnapshot.getValue(PelaporModel.class);
                pelaporList.add(pelapor);

            }
            if (dataSnapshot.getValue() != null){

                adapter = new PelaporList(HarianActivity.this, pelaporList);
                lvLaporHarian.setAdapter(adapter);
                mSearchField.addTextChangedListener(new TextWatcher() {
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
                        adapter = new PelaporList(HarianActivity.this, tesList);
                        lvLaporHarian.setAdapter(adapter);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });

            }else{
                PelaporList adapter = new PelaporList(HarianActivity.this, pelaporList);
                lvLaporHarian.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), "Belum ada Laporan", Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

}
