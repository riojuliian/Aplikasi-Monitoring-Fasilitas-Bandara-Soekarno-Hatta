package com.example.tekkomundip.kpangkasapura2;

import android.content.Intent;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login2Activity extends AppCompatActivity {

    private EditText etUsernameLogin2, etPasswdLogin2;
    private Button btnLogin3;
    String username2, password2;
    DatabaseReference databaseLogin;
    public static String usernameLogin2;

    //spinner2
    Spinner spinner2;
    String login2 [] = { "Unit1", "Unit2", "Unit3"};
    ArrayAdapter<String> adapter2;
    public static String getUnit;

    //spinner3
    Spinner spinner3;
    String login3 [] = {"Terminal1", "Terminal2", "Terminal3"};
    ArrayAdapter<String> adapter3;
    public static String getTerminal;

    //firebase child
    String user, pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        initView2();
        btnlogin3();
        spinner2();
        spinner3();
        getStringpassword();
        getStringusername();
    }


    //METHODS
    private void spinner2(){

        adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, login2);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        getUnit = spinner2.getSelectedItem().toString();
                        break;
                    case 1 :
                        getUnit = spinner2.getSelectedItem().toString();
                        break;
                    case 2 :
                        getUnit = spinner2.getSelectedItem().toString();
                        break;

                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void spinner3(){
        adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, login3);
        spinner3.setAdapter(adapter3);

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0 :
                        getTerminal = spinner3.getSelectedItem().toString();
                        break;
                    case 1 :
                        getTerminal = spinner3.getSelectedItem().toString();
                        break;
                    case 2 :
                        getTerminal = spinner3.getSelectedItem().toString();
                        break;
                    default:
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initView2(){
        etUsernameLogin2 = findViewById(R.id.etUsernameLogin2);
        etPasswdLogin2 = findViewById(R.id.etPasswdLogin2);
        btnLogin3 = findViewById(R.id.btnLogin3);
        spinner2 = (Spinner) findViewById(R.id.spinnerDiv);
        spinner3 = (Spinner) findViewById(R.id.spinnerTerm);
    }
    private void getStringusername(){
        etUsernameLogin2.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {
                username2 = mEdit.toString();
                usernameLogin2 = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }
    private void getStringpassword(){
        etPasswdLogin2.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void afterTextChanged(Editable mEdit)
            {

                password2 = mEdit.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
    }
    private void btnlogin3(){
        btnLogin3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Intent intent = new Intent (Login2Activity.this, MainActivity3.class);
               // startActivity(intent);
                login2();
            }
        });
    }

    private void login2(){
        FirebaseApp.initializeApp(this);
        databaseLogin = FirebaseDatabase.getInstance().getReference("Login");

        databaseLogin.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                getStringpassword();
                getStringusername();
                //terminal1
                user = (String) dataSnapshot.child(getTerminal).child("Unit").child(getUnit).child(username2).child("u").getValue().toString();
                pass = (String) dataSnapshot.child(getTerminal).child("Unit").child(getUnit).child(username2).child("p").getValue().toString();

                    validate(username2, password2);


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });}

    private void validate(String username, String password){
        if((username.equals(user)) && (password.equals(pass))){
            Intent intent1= new Intent(Login2Activity.this, MainActivity3.class);
            startActivity(intent1);
        }
        else{
            Toast.makeText(this, "Anda bukan petugas " +getUnit, Toast.LENGTH_LONG).show();
        }
    }
    private void pilih(){
        Toast.makeText(this, "Silakan pilih divisi", Toast.LENGTH_LONG).show();
    }

    //passing string
    public static String getData2(){
        return getUnit;
    }
    public static String getData3(){
        return getTerminal;
    }
    public static String getData5(){
        return usernameLogin2;
    }
}
