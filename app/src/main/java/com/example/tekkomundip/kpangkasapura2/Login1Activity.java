package com.example.tekkomundip.kpangkasapura2;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login1Activity extends AppCompatActivity {

    private EditText etUsernameLogin, etPasswdLogin;
    private Button btnLogin1, btnLogin2;
    public TextView tvButtonTeknisi;
    public String username, password;
    DatabaseReference databaseLogin;
    Spinner spinnerlogin;
    public static String HakAkses;
    public static String UsernameLogin;

    boolean flag = true;

    String user, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);
        initView();
        spinnerLogin();
        btnlogin1();
        getStringpassword();
        getStringusername();
        textteknisi();



    }
    private void  initView(){
        etUsernameLogin = findViewById(R.id.etUsernameLogin);
        etPasswdLogin = findViewById(R.id.etPasswdLogin);
        btnLogin1 = findViewById(R.id.btnLogin1);
        spinnerlogin = (Spinner) findViewById(R.id.spinner1);
        tvButtonTeknisi = findViewById(R.id.tv_loginteknisi);
        tvButtonTeknisi.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
    }

    private void spinnerLogin(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.HakAkses_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerlogin.setAdapter(adapter);
        spinnerlogin.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                        //showToast("Spinner2: position=" + position + " id=" + id);
                        if (position == 0){
                            HakAkses = spinnerlogin.getSelectedItem().toString();
                            //username = etUsernameLogin.getText().toString();
                            //password = etPasswdLogin.getText().toString();
                        }
                        else if (position == 1){
                            HakAkses = spinnerlogin.getSelectedItem().toString();

                        }
                        else if (position == 2){
                            HakAkses = spinnerlogin.getSelectedItem().toString();

                        }
                        else {
                            HakAkses = spinnerlogin.getSelectedItem().toString();

                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
    }
    private void getStringusername(){
        etUsernameLogin.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                username = mEdit.toString();
                UsernameLogin = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }
    private void getStringpassword(){
        etPasswdLogin.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                password = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }
    private void validate(String username, String password){
        if((username.equals(user)) && (password.equals(pass))){
            Intent intent1= new Intent(Login1Activity.this, MainActivity.class);
            startActivity(intent1);
        }
        else{
            Toast.makeText(this, "Anda bukan petugas " +HakAkses, Toast.LENGTH_LONG).show();
        }
    }
    private void validatesummary(String username, String password){
        if((username.equals(user)) && (password.equals(pass))){
            Intent intent1= new Intent(Login1Activity.this, MainActivity2.class);
            startActivity(intent1);
        }
        else{
            Toast.makeText(this, "Anda bukan petugas " +HakAkses, Toast.LENGTH_LONG).show();
        }
    }
    private void login(){
        FirebaseApp.initializeApp(this);
        databaseLogin = FirebaseDatabase.getInstance().getReference("Login");

        databaseLogin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getStringpassword();
                getStringusername();
                //terminal1
                try {
                    user = (String) dataSnapshot.child(HakAkses).child(username).child("u").getValue().toString();
                    pass = (String) dataSnapshot.child(HakAkses).child(username).child("p").getValue().toString();


                    if (HakAkses.equals("Terminal1") || HakAkses.equals("Terminal2") || HakAkses.equals("Terminal3"))
                        validate(username, password);
                    else if (HakAkses.equals("Summary")) {
                        validatesummary(username, password);
                    }
                }
                catch (Exception E){

                    flag = false;
                    if(!flag){
                        Toast.makeText(getApplicationContext(),"Anda tidak tedaftar" , Toast.LENGTH_SHORT).show();
                    }
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }
    private void btnlogin1(){
        btnLogin1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                login();
            }
        });
    }
    private void textteknisi() {
        tvButtonTeknisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login2();
            }
        });
    }
    private void login2(){
        Intent intent5= new Intent(Login1Activity.this, Login2Activity.class);
        startActivity(intent5);
    }
    public void onBackPressed() {
        exit();
    }
    private void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Yakin mau keluar?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })

                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();

    }
    public static String getData1(){
        return HakAkses;
    }
    public static String getData4(){
        return UsernameLogin;
    }
}

