package com.example.tekkomundip.kpangkasapura2;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LaporActivity extends AppCompatActivity {

    private Spinner spinnerBarang, spinnerTujuan, spinnerStatus,spinnerLogin1;
    private Button btnBack, btnLapor, btnPilihGambar;
    private ImageView img_BeforeMT;
    private EditText textpesan;
    private TextView tv_namaPelapor,tv_lokasiSpesifik, tv_before;
    private ProgressBar uploadPB;
    private Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
    private String stringtanggal, stringjam;
    String namaPelapor = Login3Activity.getNama();
    String namaFasilitas;

    Handler handler;
    Runnable runnable;
    DatabaseReference databasePelapor, databaseView, databaseBarang,databaseLoginTerm1,databaseLoginTerm2,databaseLoginTerm3;
    String terminal,Status,LokasiSpesifik;
    int notificationID = 1;
    TextView ngamukbanget;
    DatabaseReference dataStatus;
    String terminal1 = Login3Activity.getTempat();
    String terminalngamuk;
    private StorageReference ngamuk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapor);
        initView();
        initDatabase();
        spinnerbarang();
        btnback();
        spinnertujuan();
        spinnerstatuslapor();
        aksiLapor();
        getStatus();
        FirebaseApp.initializeApp(this);
        mStorageRef = FirebaseStorage.getInstance().getReference("Dokumentasi");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Dokumentasi");


        btnPilihGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
    }

    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(img_BeforeMT);
        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile1() {

        final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                + "." + getFileExtension(mImageUri));
        mUploadTask = fileReference.putFile(mImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    private static final String TAG = "MainActivity";

                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                uploadPB.setProgress(0);
                            }
                        }, 500);

                        Toast.makeText(LaporActivity.this, "Upload Berhasil", Toast.LENGTH_LONG).show();
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful()) ;
                        Uri downloadUrl = urlTask.getResult();
                        Log.d(TAG, "onSuccess: firebase download url: " + downloadUrl.toString());

                        Upload upload = new Upload(tv_namaPelapor.getText().toString(), downloadUrl.toString(), tv_before.getText().toString(),
                                spinnerBarang.getSelectedItem().toString().trim(), stringtanggal, stringjam);
                        String uploadId1 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child("before").child(terminal1).child(uploadId1).setValue(upload);

                        Date date = new Date();
                        Date newDate = new Date(date.getTime());
                        SimpleDateFormat tanggal = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat jam = new SimpleDateFormat("HH:mm:ss");
                        String stringjam = jam.format(newDate);
                        stringtanggal = tanggal.format(newDate);
                        mDatabaseRef.child("before").child(terminal1).child(uploadId1).child("tanggal").setValue(stringtanggal);
                        mDatabaseRef.child("before").child(terminal1).child(uploadId1).child("jam").setValue(stringjam);

                       /* mDatabaseRef.child(mSpinnerTujuan.getSelectedItem().toString()).child(uploadId1).child("Namabarang").setValue(mSpinnerBarang.getSelectedItem().toString());
                        mDatabaseRef.child(mSpinnerTujuan.getSelectedItem().toString()).child(uploadId1).child("Tujuan").setValue(mSpinnerTujuan.getSelectedItem().toString());
                        mDatabaseRef.child(mSpinnerTujuan.getSelectedItem().toString()).child(uploadId1).child("Tanggal").setValue(stringtanggal);
                        mDatabaseRef.child(mSpinnerTujuan.getSelectedItem().toString()).child(uploadId1).child("Keterangan").setValue(mTvSebelum.getText().toString()); */
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LaporActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        uploadPB.setProgress((int) progress);
                    }
                });
    }

    private void initView(){
        spinnerBarang = findViewById(R.id.cb_namabarang);
        btnBack = findViewById(R.id.btnkembalipelaporan);
        spinnerStatus = findViewById(R.id.cb_status_lapor);
        spinnerTujuan = findViewById(R.id.cb_Tujuan);
        textpesan = findViewById(R.id.et_pesan_isi);
        btnLapor = findViewById(R.id.btnlaporpelaporan);

        tv_namaPelapor = findViewById(R.id.tv_isiNamaPelapor);
        tv_lokasiSpesifik = findViewById(R.id.tv_lokasi_isi);
        tv_before = findViewById(R.id.tv_before);
        btnPilihGambar = findViewById(R.id.btnPilihGambar);

        img_BeforeMT = findViewById(R.id.img_BeforeMT);
        uploadPB = findViewById(R.id.uploadPB);
    }
    private void initDatabase() {
        databasePelapor = FirebaseDatabase.getInstance().getReference("Pelaporan");
        databaseBarang =  FirebaseDatabase.getInstance().getReference("Barang");
        databaseView = FirebaseDatabase.getInstance().getReference("View");
    }
    public void onBackPressed() {
        Intent reg = new Intent(this, MainActivity.class);
        startActivity(reg);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
        finish();
    }

    private void spinnerbarang(){

        if(terminal1.equals("Terminal1")) {
            terminalngamuk = "Terminal1";
            databaseLoginTerm1 = FirebaseDatabase.getInstance().getReference("Terminal1");
            databaseLoginTerm1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final List<String> barang = new ArrayList<String>();

                    for (DataSnapshot barangSnapshot : dataSnapshot.getChildren()) {
                        String barangName = barangSnapshot.child("nama").getValue(String.class);
                        barang.add(barangName);
                    }
                    ArrayAdapter<String> barangAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, barang);
                    barangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerBarang.setAdapter(barangAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(terminal1.equals("Terminal2")){
            terminalngamuk = "Terminal2";
            databaseLoginTerm2 = FirebaseDatabase.getInstance().getReference("Terminal2");
            databaseLoginTerm2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final List<String> barang = new ArrayList<String>();

                    for (DataSnapshot barangSnapshot: dataSnapshot.getChildren()) {
                        String barangName = barangSnapshot.child("nama").getValue(String.class);
                        barang.add(barangName);
                    }
                    ArrayAdapter<String> barangAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, barang);
                    barangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerBarang.setAdapter(barangAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(terminal1.equals("Terminal3")){
            terminalngamuk = "Terminal3";
            databaseLoginTerm3 = FirebaseDatabase.getInstance().getReference("Terminal3");
            databaseLoginTerm3.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final List<String> barang = new ArrayList<String>();

                    for (DataSnapshot barangSnapshot: dataSnapshot.getChildren()) {
                        String barangName = barangSnapshot.child("nama").getValue(String.class);
                        barang.add(barangName);
                    }
                    ArrayAdapter<String> barangAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, barang);
                    barangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerBarang.setAdapter(barangAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        spinnerBarang.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                        //showToast("Spinner2: position=" + position + " id=" + id);
                        getStatus();
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
    }
    private void spinnerstatuslapor(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Status_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(adapter);
        spinnerStatus.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);

                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

    }
    private void spinnertujuan(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Tujuan_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTujuan.setAdapter(adapter);
        spinnerTujuan.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(
                            AdapterView<?> parent, View view, int position, long id) {
                        ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                        //showToast("Spinner2: position=" + position + " id=" + id);
                        if (position == 0){

                        }
                        else if (position == 1){

                        }
                        else {

                        }
                    }

                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
    }

    private void btnback(){
        btnBack.setOnClickListener(V -> {
            Intent reg = new Intent(this, MainActivity.class);
            startActivity(reg);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
            finish();}
        );
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

    private void addlaporan() {
        //String terminal = spinnerLogin1.getSelectedItem().toString();
        String terminal = Login3Activity.getTempat();
        String fasilitas = spinnerBarang.getSelectedItem().toString();
        String namafasilitas = BarangList.getData6();
        String namapelapor = tv_namaPelapor.getText().toString();
        String pesan = textpesan.getText().toString();
        String tujuan = spinnerTujuan.getSelectedItem().toString();
        String statuslapor = spinnerStatus.getSelectedItem().toString();

        if (!TextUtils.isEmpty(pesan)) {
            final String id = databasePelapor.child(terminal).child(tujuan).push().getKey();
            final String id2 = databaseView.push().getKey();
            //public Pelapor(String pelaporId,String pesan, String namapelapor, String barang, String area, String status
            Pelapor pelapor = new Pelapor(id, pesan, namapelapor, fasilitas, terminal, statuslapor);
            Pelapor pelapor2 = new Pelapor(id, pesan, namapelapor, fasilitas, terminal, statuslapor);
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Date date = new Date();
                        Date newDate = new Date(date.getTime());
                        SimpleDateFormat tahun = new SimpleDateFormat("yyyy");
                        SimpleDateFormat bulan = new SimpleDateFormat("MM", Locale.ENGLISH);
                        SimpleDateFormat bulantahun = new SimpleDateFormat("MMM yyyy", Locale.ENGLISH);
                        SimpleDateFormat haribulantahun = new SimpleDateFormat("d MMM yyyy", Locale.ENGLISH);
                        SimpleDateFormat tanggal = new SimpleDateFormat("dd");
                        SimpleDateFormat jam = new SimpleDateFormat("HH:mm:ss");
                        SimpleDateFormat jamshift = new SimpleDateFormat("HH");
                        String stringtahun = tahun.format(newDate);
                        String stringbulan = bulan.format(newDate);
                        String stringbulantahun = bulantahun.format(newDate);
                        String stringharibulantahun = haribulantahun.format(newDate);
                        String stringtanggal = tanggal.format(newDate);
                        String stringjam = jam.format(newDate);

                        String stringjamshift = jamshift.format(newDate);
                        Integer intjamshift = Integer.parseInt(stringjamshift);

                        if(intjamshift <= 19 && intjamshift >=7){
                            String Shift = "Pagi (07.00 - 19.00)";
                            databasePelapor.child(terminal).child(tujuan).child(id).child("Shift").setValue(Shift);
                        }
                        else{
                            String Shift = "Malam (19.00 - 07.00)";
                            databasePelapor.child(terminal).child(tujuan).child(id).child("Shift").setValue(Shift);
                        }

                        DatabaseReference databasePelaporUnit1 = FirebaseDatabase.getInstance().getReference("Pelaporan").child(terminal).child(tujuan).child(id);
                        databasePelaporUnit1.child("Tahun").setValue(stringtahun);
                        databasePelaporUnit1.child("Bulan").setValue(stringbulan).toString();
                        databasePelaporUnit1.child("BulanTahun").setValue(stringbulantahun);
                        databasePelaporUnit1.child("Tanggal").setValue(stringtanggal).toString();
                        databasePelaporUnit1.child("Jam").setValue(stringjam).toString();
                        databasePelaporUnit1.child("HariBulanTahun").setValue(stringharibulantahun);
                        databasePelaporUnit1.child("Status").setValue(statuslapor);

                        DatabaseReference view = FirebaseDatabase.getInstance().getReference("View").child(id2);
                        view.child("Tahun").setValue(stringtahun);
                        view.child("Bulan").setValue(stringbulan).toString();
                        view.child("BulanTahun").setValue(stringbulantahun);
                        view.child("Tanggal").setValue(stringtanggal).toString();
                        view.child("Jam").setValue(stringjam).toString();
                        view.child("HariBulanTahun").setValue(stringharibulantahun);
                        view.child("Status").setValue(statuslapor);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            };
            handler.postDelayed(runnable, 1 * 1000);

            databasePelapor.child(terminal).child(tujuan).child(id).setValue(pelapor);
            databaseView.child(id2).setValue(pelapor2);


            Toast.makeText(this, "Laporan berhasil ditambahkan", Toast.LENGTH_LONG).show();
        }
        else if (textpesan.equals("")){
            Toast.makeText(this, "Jangan Kosong", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "Jangan Kosong", Toast.LENGTH_LONG).show();
        }
    }
    private void update(){
        try {
            String barang = spinnerBarang.getSelectedItem().toString();
            String terminal = Login3Activity.getTempat();
            String statuslapor = spinnerStatus.getSelectedItem().toString();
            databaseBarang.child(terminal).child("DigitalBanner").child(barang).child("Status").setValue(statuslapor);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void aksiLapor(){
        btnLapor.setOnClickListener(V -> {

            if (textpesan.getText().toString().equals("") )
            {
                Toast.makeText(LaporActivity.this, "Pesan Jangan Kosong..", Toast.LENGTH_SHORT).show();
            }
            else if (mImageUri != null){
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(LaporActivity.this, "Sedang Mengupload", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile1();
                    addlaporan();
                    update();
                    Intent reg = new Intent(this, MainActivity.class);
                    startActivity(reg);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
            }
            else {
                Toast.makeText(LaporActivity.this, "Pilih Gambar Sebelum Perbaikan", Toast.LENGTH_SHORT).show();
            }
            }
        );
    }
    private void getStatus() {
        FirebaseApp.initializeApp(this);
        dataStatus = FirebaseDatabase.getInstance().getReference("Barang");
        //String fasilitas2 = spinnerBarang.getSelectedItem().toString();
        dataStatus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Status = (String) dataSnapshot
                        .child(terminalngamuk)
                        .child("DigitalBanner")
                        .child(spinnerBarang.getSelectedItem().toString())
                        .child("Status").getValue().toString();
                LokasiSpesifik = (String) dataSnapshot
                        .child(terminalngamuk)
                        .child("DigitalBanner")
                        .child(spinnerBarang.getSelectedItem().toString())
                        .child("LokasiSpesifik").getValue().toString();

                tv_namaPelapor.setText(namaPelapor);
                tv_lokasiSpesifik.setText(LokasiSpesifik);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}

