package com.example.tekkomundip.kpangkasapura2;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LaporPerbaikanActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_IMAGE_REQUEST2 = 2;

    private Button mButtonChooseImage, mButtonUpload, mButtonChooseImageDua;
    private TextView mTextViewShowUploads, mTvSebelum, mTvSesudah,tv_tujuan, tv_getstatus;
    private EditText mEditTextFileName;
    private ImageView mImageView, mImageViewDua;
    private Spinner mSpinnerBarang, mSpinnerTujuan;
    private ProgressBar mProgressBar;
    private DatePicker mDate;
    String terminal1 = Login3Activity.getTempat();
    String usernamePelapor = Login3Activity.getNama();
    String terminalngamuk;
    String Status;
    DatabaseReference databaseLoginTerm1,databaseLoginTerm2,databaseLoginTerm3, dataStatus;
    private Uri mImageUri, mImageUri2;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    private String stringtanggal, stringtanggal2, stringjam, stringjam2;

    String getUnitLogin = Login3Activity.getBagian();
    String getTerminalLogin = Login3Activity.getTempat();
    String getUsernamePelapor = Login3Activity.getNama();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapor_perbaikan);

       // mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonChooseImageDua = findViewById(R.id.button_choose_imagedua);
        mButtonUpload = findViewById(R.id.button_upload);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
       // mEditTextFileName = findViewById(R.id.edit_text_file_name);
       // mImageView = findViewById(R.id.image_view);
        mImageViewDua = findViewById(R.id.image_viewdua);
        mSpinnerBarang = findViewById(R.id.spinner_barang);
        mProgressBar = findViewById(R.id.progress_bar);
      //  mTvSebelum = findViewById(R.id.tv_gambar_sebelum);
        mTvSesudah = findViewById(R.id.tv_gambar_sesudah);
        tv_tujuan = findViewById(R.id.tv_tujuanperbaikan);
        tv_tujuan.setText(terminal1);
        tv_getstatus = findViewById(R.id.tv_status_perbaikan_isi);
        /*ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Area_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerTujuan.setAdapter(adapter);
        mSpinnerTujuan.setOnItemSelectedListener(this);*/



        mStorageRef = FirebaseStorage.getInstance().getReference("Dokumentasi");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Dokumentasi");

        spinnerBarang();
        getStatus();
     /*   mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        }); */

        mButtonChooseImageDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser2();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(/* mImageView == null ||*/ mImageViewDua == null){
                Toast.makeText(LaporPerbaikanActivity.this, "Silahkan pilih gambar dahulu", Toast.LENGTH_SHORT).show();
            }
            else {
                if (mImageUri2 != null) {

                        if (mUploadTask != null && mUploadTask.isInProgress()) {
                            Toast.makeText(LaporPerbaikanActivity.this, "Sedang Mengupload", Toast.LENGTH_SHORT).show();
                        } else {
                           // uploadFile1();
                            uploadFile2();
                        }
                     /* else {
                        Toast.makeText(LaporPerbaikanActivity.this, "Pilih Gambar Sebelum Perbaikan", Toast.LENGTH_SHORT).show();
                    } */
                } else {
                    Toast.makeText(LaporPerbaikanActivity.this, "Pilih Gambar Setelah Perbaikan", Toast.LENGTH_SHORT).show();
                }
            }
            }
        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagesActivity();
            }
        });
    }

    private void spinnerBarang(){
        if(terminal1.equals("Terminal1")) {
            terminalngamuk = "Terminal1";
            databaseLoginTerm1 = FirebaseDatabase.getInstance().getReference("Terminal1");
            Query status = FirebaseDatabase.getInstance().getReference("Terminal1").child("nama").orderByChild("status").equalTo("DOWN");

            databaseLoginTerm1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final List<String> barang = new ArrayList<String>();

                    for (DataSnapshot barangSnapshot : dataSnapshot.getChildren()) {
                        //String barangName = barangSnapshot.getValue(String.class);
                        String barangName = barangSnapshot.child("nama").getValue(String.class);
                        barang.add(barangName);
                    }
                    ArrayAdapter<String> barangAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, barang);
                    barangAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    mSpinnerBarang.setAdapter(barangAdapter);
                    mSpinnerBarang.setOnItemSelectedListener(
                            new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(
                                        AdapterView<?> parent, View view, int position, long id) {
                                    ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                                  //  mImageView.setImageBitmap(null);
                                    mImageViewDua.setImageBitmap(null);
                                    mUploadTask = null;
                             //       mImageUri = null;
                                    mImageUri2 = null;
                                    getStatus();
                                }

                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
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
                    mSpinnerBarang.setAdapter(barangAdapter);
                    mSpinnerBarang.setOnItemSelectedListener(
                            new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(
                                        AdapterView<?> parent, View view, int position, long id) {
                                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                                  //  mImageView.setImageBitmap(null);
                                    mImageViewDua.setImageBitmap(null);
                                    mUploadTask = null;
                                //    mImageUri = null;
                                    mImageUri2 = null;
                                    getStatus();
                                }

                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
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
                    mSpinnerBarang.setAdapter(barangAdapter);
                    mSpinnerBarang.setOnItemSelectedListener(
                            new AdapterView.OnItemSelectedListener() {
                                public void onItemSelected(
                                        AdapterView<?> parent, View view, int position, long id) {
                                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
                                 //   mImageView.setImageBitmap(null);
                                    mImageViewDua.setImageBitmap(null);
                                    mUploadTask = null;
                               //     mImageUri = null;
                                    mImageUri2 = null;
                                    getStatus();
                                }

                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }


 /*   private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    } */

    private void openFileChooser2() {
        Intent intent2 = new Intent();
        intent2.setType("image/*");
        intent2.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent2, PICK_IMAGE_REQUEST2);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

      /*  if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(mImageView);
        } */
        if (requestCode == PICK_IMAGE_REQUEST2 && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri2 = data.getData();

            Picasso.with(this).load(mImageUri2).into(mImageViewDua);
        }
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

   /* private void uploadFile1() {

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
                                mProgressBar.setProgress(0);
                            }
                        }, 500);

                        Toast.makeText(LaporPerbaikanActivity.this, "Upload Berhasil", Toast.LENGTH_LONG).show();
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful()) ;
                        Uri downloadUrl = urlTask.getResult();
                        Log.d(TAG, "onSuccess: firebase download url: " + downloadUrl.toString());

                        Upload upload = new Upload(usernamePelapor,downloadUrl.toString(), mTvSebelum.getText().toString(),
                                mSpinnerBarang.getSelectedItem().toString().trim(), stringtanggal, stringjam);
                        String uploadId1 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(terminal1).child(uploadId1).setValue(upload);

                        Date date = new Date();
                        Date newDate = new Date(date.getTime());
                        SimpleDateFormat tanggal = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat jam = new SimpleDateFormat("HH:mm:ss");
                        String stringjam = jam.format(newDate);
                        stringtanggal = tanggal.format(newDate);
                        mDatabaseRef.child(terminal1).child(uploadId1).child("tanggal").setValue(stringtanggal);
                        mDatabaseRef.child(terminal1).child(uploadId1).child("tanggal").setValue(stringjam);

                       /* mDatabaseRef.child(mSpinnerTujuan.getSelectedItem().toString()).child(uploadId1).child("Namabarang").setValue(mSpinnerBarang.getSelectedItem().toString());
                        mDatabaseRef.child(mSpinnerTujuan.getSelectedItem().toString()).child(uploadId1).child("Tujuan").setValue(mSpinnerTujuan.getSelectedItem().toString());
                        mDatabaseRef.child(mSpinnerTujuan.getSelectedItem().toString()).child(uploadId1).child("Tanggal").setValue(stringtanggal);
                        mDatabaseRef.child(mSpinnerTujuan.getSelectedItem().toString()).child(uploadId1).child("Keterangan").setValue(mTvSebelum.getText().toString()); */
                /*    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LaporPerbaikanActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        mProgressBar.setProgress((int) progress);
                    }
                });

    } */

    private void uploadFile2() {

        final StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                + "." + getFileExtension(mImageUri2));


        mUploadTask = fileReference.putFile(mImageUri2)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    private static final String TAG ="MainActivity" ;
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(0);
                            }
                        },500);

                        Toast.makeText(LaporPerbaikanActivity.this, "Upload Berhasil", Toast.LENGTH_LONG).show();
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful());
                        Uri downloadUrl = urlTask.getResult();
                        Log.d(TAG, "onSuccess: firebase download url: " + downloadUrl.toString());

                        Upload upload2 = new Upload(usernamePelapor,downloadUrl.toString(), mTvSesudah.getText().toString(),
                                mSpinnerBarang.getSelectedItem().toString().trim(), stringtanggal2, stringjam2);
                        String uploadId2 = mDatabaseRef.push().getKey();
                        mDatabaseRef.child("after").child(terminal1).child(uploadId2).setValue(upload2);

                        Date date2 = new Date();
                        Date newDate2 = new Date(date2.getTime());
                        SimpleDateFormat tanggal2 = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat jam2 = new SimpleDateFormat("HH:mm:ss");
                        String stringjam2 = jam2.format(newDate2);
                        stringtanggal2 = tanggal2.format(newDate2);
                        mDatabaseRef.child("after").child(terminal1).child(uploadId2).child("tanggal").setValue(stringtanggal2);
                        mDatabaseRef.child("after").child(terminal1).child(uploadId2).child("jam").setValue(stringjam2);


                        /*
                            mDatabaseRef.child(mSpinnerTujuan.getSelectedItem().toString()).child(uploadId2).child("Namabarang").setValue(mSpinnerBarang.getSelectedItem().toString());
                            mDatabaseRef.child(mSpinnerTujuan.getSelectedItem().toString()).child(uploadId2).child("Tujuan").setValue(mSpinnerTujuan.getSelectedItem().toString());
                            mDatabaseRef.child(mSpinnerTujuan.getSelectedItem().toString()).child(uploadId2).child("Tanggal").setValue(stringtanggal);
                            mDatabaseRef.child(mSpinnerTujuan.getSelectedItem().toString()).child(uploadId2).child("Keterangan").setValue(mTvSesudah.getText().toString()); */
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(LaporPerbaikanActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        mProgressBar.setProgress((int) progress);
                    }
                });
    }

    private void openImagesActivity() {
        Intent intent = new Intent(this, PerbaikanActivity2.class);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void getStatus() {
        FirebaseApp.initializeApp(this);
        dataStatus = FirebaseDatabase.getInstance().getReference("Barang");
        dataStatus.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Status = (String) dataSnapshot
                        .child(getTerminalLogin)
                        .child("DigitalBanner")
                        .child(mSpinnerBarang.getSelectedItem().toString())
                        .child("Status").getValue().toString();
                tv_getstatus.setText(Status);
                if (tv_getstatus.getText().equals("UP")){
                  //  mButtonChooseImage.setAlpha(.10f);
                 //   mButtonChooseImage.setClickable(false);
                    mButtonChooseImageDua.setAlpha(.10f);
                    mButtonChooseImageDua.setClickable(false);
                    mButtonUpload.setAlpha(.10f);
                    mButtonUpload.setClickable(false);
                }
                else{
                  //  mButtonChooseImage.setClickable(true);
                  //  mButtonChooseImage.setAlpha(1f);
                    mButtonChooseImageDua.setClickable(true);
                    mButtonChooseImageDua.setAlpha(1f);
                    mButtonUpload.setClickable(true);
                    mButtonUpload.setAlpha(1f);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void onBackPressed() {
        Intent reg = new Intent(this, MainActivity3.class);
        startActivity(reg);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_righ);
        finish();
    }
}