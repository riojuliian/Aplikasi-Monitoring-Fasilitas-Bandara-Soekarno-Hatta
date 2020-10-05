package com.example.tekkomundip.kpangkasapura2;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentProvider;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class BarangList extends ArrayAdapter<Barang> {
    private Activity context;
    private List<Barang> barangList;
    public static String namabarang,gas;
    boolean status;
    String coba,coba1,namabrg;
    TextView textViewStatus;
    TextView textViewNamaBrg,textnamaaaa ;
    private Context xyz;

    public BarangList(Activity context, List<Barang> barangList){
        super(context, R.layout.list_layout_barang, barangList);
        this.context = context;
        this.barangList = barangList;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout_barang, null, true);

        TextView textViewLokasi= (TextView) listViewItem.findViewById(R.id.textViewLokasi);
        TextView textViewLokasiSpesifik = (TextView) listViewItem.findViewById(R.id.textViewLocSpesifik);
        textViewNamaBrg = (TextView) listViewItem.findViewById(R.id.textViewNamaBrg);;
        textViewStatus = (TextView) listViewItem.findViewById(R.id.textViewStatusBrg);
        Barang barang = barangList.get(position);

        textViewLokasi.setText(barang.getLokasi());
        textViewLokasiSpesifik.setText(barang.getLokasiSpesifik());
        textViewNamaBrg.setText(barang.getNama());
        textViewStatus.setText(barang.getStatus());
        namabrg = textViewNamaBrg.getText().toString();
        coba = textViewStatus.getText().toString();

        return listViewItem;
    }
    public BarangList (Context context, int resource){
        super(context,resource);
        this.xyz = context;
    }

    private void exit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(xyz.getApplicationContext());
        builder.setMessage("Yakin mau keluar? ")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })

                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).show();

    }
    public static String getData6(){
        return namabarang;
    }
    public static String getData7(){
        return gas;
    }
}
