package com.example.tekkomundip.kpangkasapura2;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class TahunanActivity extends AppCompatActivity {
    private Button btnkembali;
    ListView listViewLaporTahunan;
    List<PelaporModel> pelaporList;
    DatabaseReference databasePelapor;
    Button b;
    EditText mSearchFieldtahunan;
    PelaporList adapter;
    static Dialog d ;
    int year = Calendar.getInstance().get(Calendar.YEAR);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tahunan);
        initView();
        listViewLaporTahunan = (ListView) findViewById(R.id.listViewLaporTahunan);
        pelaporList = new ArrayList<>();
        databasePelapor = FirebaseDatabase.getInstance().getReference("View");
        /*Query query = FirebaseDatabase.getInstance().getReference("Pelaporan").child("Terminal1").child("Unit1")
                .orderByChild("Tahun")
                .equalTo("2019");
        query.addValueEventListener(valueEventListener);
*/
        b = (Button) findViewById(R.id.year);
        b.setText(""+year);
        Query query2 = FirebaseDatabase.getInstance().getReference("View")
                .orderByChild("Tahun")
                .equalTo(String.valueOf(b.getText()));
        query2.addValueEventListener(valueEventListener);
        b.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                showYearDialog();

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

                adapter = new PelaporList(TahunanActivity.this, pelaporList);
                listViewLaporTahunan.setAdapter(adapter);
                mSearchFieldtahunan = findViewById(R.id.mSearchFieldtahunan);
                mSearchFieldtahunan.addTextChangedListener(new TextWatcher() {
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
                        adapter = new PelaporList(TahunanActivity.this, tesList);
                        listViewLaporTahunan.setAdapter(adapter);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });


            }else{
                PelaporList adapter = new PelaporList(TahunanActivity.this, pelaporList);
                listViewLaporTahunan.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), "Belum ada Laporan", Toast.LENGTH_LONG).show();
            }
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    private void showYearDialog()
    {

        final Dialog d = new Dialog(TahunanActivity.this);
        d.setTitle("Year Picker");
        d.setContentView(R.layout.yeardialog);
        Button set = (Button) d.findViewById(R.id.button1);
        Button cancel = (Button) d.findViewById(R.id.button2);
        TextView year_text=(TextView)d.findViewById(R.id.year_text);
        year_text.setText(" "+year);
        final NumberPicker nopicker = (NumberPicker) d.findViewById(R.id.numberPicker1);

        nopicker.setMaxValue(year+50);
        nopicker.setMinValue(year-50);
        nopicker.setWrapSelectorWheel(false);
        nopicker.setValue(year);
        nopicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        databasePelapor = FirebaseDatabase.getInstance().getReference("View");

        set.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                b.setText(String.valueOf(nopicker.getValue()));
                d.dismiss();

                String tahun = b.getText().toString();
                Query query1 = FirebaseDatabase.getInstance().getReference("View")
                        .orderByChild("Tahun")
                        .equalTo(String.valueOf(tahun));
                query1.addValueEventListener(valueEventListener);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.show();
    }
}
