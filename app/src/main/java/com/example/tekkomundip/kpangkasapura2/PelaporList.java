package com.example.tekkomundip.kpangkasapura2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class PelaporList extends ArrayAdapter<PelaporModel> {
    private Activity context;
    private List<PelaporModel> pelaporList;

    public PelaporList(Activity context, List<PelaporModel> pelaporList){
        super(context, R.layout.list_layout_laporan, pelaporList);
        this.context = context;
        this.pelaporList = pelaporList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout_laporan, null, true);

        TextView textViewNamaPelapor = (TextView) listViewItem.findViewById(R.id.textViewNamaPelapor);
        TextView textViewBarang = (TextView) listViewItem.findViewById(R.id.textViewBarang);
        TextView textViewArea = (TextView) listViewItem.findViewById(R.id.textViewArea);
        TextView textViewJam = (TextView) listViewItem.findViewById(R.id.textViewJam);
        TextView textViewTanggal = (TextView) listViewItem.findViewById(R.id.textViewTanggal);
        TextView textViewStatus = (TextView) listViewItem.findViewById(R.id.textViewStatusLapor);

        PelaporModel pelapor= pelaporList.get(position);

        textViewNamaPelapor.setText(pelapor.getNamapelapor());
        textViewBarang.setText(pelapor.getBarang());
        textViewArea.setText(pelapor.getArea());
        textViewJam.setText(pelapor.getJam());
        textViewTanggal.setText(pelapor.getHariBulanTahun() );
        textViewStatus.setText(pelapor.getStatus());

        return listViewItem;
    }
}
