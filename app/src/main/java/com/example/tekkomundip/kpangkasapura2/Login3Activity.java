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

public class Login3Activity extends AppCompatActivity {

    private EditText etUsernameLogin, etPasswdLogin;
    private Button btnLogin1;
    public String username, password;
    DatabaseReference databaseLogin;
    public static String UsernameLogin;

    boolean flag = true;

    String user, pass;
    public static String bagian, tempat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);
        initView();
        btnlogin1();
        getStringpassword();
        getStringusername();
    }

    private void  initView(){
        etUsernameLogin = findViewById(R.id.etUsernameLogin);
        etPasswdLogin = findViewById(R.id.etPasswdLogin);
        btnLogin1 = findViewById(R.id.btnLogin1);
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

    //validations
    private void validateSupervisor(String username, String password){
        if((username.equals(user)) && (password.equals(pass))){
            //if((password.equals(pass))){
            Intent intent1= new Intent(Login3Activity.this, MainActivity.class);
            startActivity(intent1);
        }
        else{
            Toast.makeText(this, "Anda bukan petugas", Toast.LENGTH_LONG).show();
        }
    }

    private void validateManager(String username, String password){
        if((username.equals(user)) && (password.equals(pass))){
            //if((password.equals(pass))){
            Intent intent1= new Intent(Login3Activity.this, MainActivity2.class);
            startActivity(intent1);
        }
        else{
            Toast.makeText(this, "Anda bukan petugas", Toast.LENGTH_LONG).show();
        }
    }

    private void validateUnit1(String username, String password){
        if((username.equals(user)) && (password.equals(pass))){
            //if((password.equals(pass))){
            Intent intent1= new Intent(Login3Activity.this, MainActivity3.class);
            startActivity(intent1);
        }
        else{
            Toast.makeText(this, "Anda bukan petugas", Toast.LENGTH_LONG).show();
        }
    }

    private void validateUnit2(String username, String password){
        if((username.equals(user)) && (password.equals(pass))){
            //if((password.equals(pass))){
            Intent intent1= new Intent(Login3Activity.this, MainActivity3.class);
            startActivity(intent1);
        }
        else{
            Toast.makeText(this, "Anda bukan petugas", Toast.LENGTH_LONG).show();
        }
    }

    private void validateUnit3(String username, String password){
        if((username.equals(user)) && (password.equals(pass))){
            //if((password.equals(pass))){
            Intent intent1= new Intent(Login3Activity.this, MainActivity3.class);
            startActivity(intent1);
        }
        else{
            Toast.makeText(this, "Anda bukan petugas", Toast.LENGTH_LONG).show();
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
                    user = (String) dataSnapshot.child(username).child("u").getValue().toString();
                    pass = (String) dataSnapshot.child(username).child("p").getValue().toString();
                    bagian = (String) dataSnapshot.child(username).child("bagian").getValue().toString();
                    tempat = (String) dataSnapshot.child(username).child("tempat").getValue().toString();

                    if(bagian.equals("Supervisor")) {
                        validateSupervisor(username, password);
                    }
                    else if(bagian.equals("Manager")){
                        validateManager(username, password);
                    }
                    else if(bagian.equals("Unit1")){
                        validateUnit1(username, password);
                    }
                    else if(bagian.equals("Unit2")){
                        validateUnit2(username, password);
                    }
                    else if(bagian.equals("Unit3")){
                        validateUnit3(username, password);
                    }
                }
                catch (Exception e){

                    flag = false;
                    if(!flag){
                        Toast.makeText(getApplicationContext(),"catch" , Toast.LENGTH_SHORT).show();
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

    //passing string
    public static String getNama(){
        return UsernameLogin;
    }
    public static String getBagian(){
        return bagian;
    }
    public static String getTempat(){
        return tempat;
    }
}