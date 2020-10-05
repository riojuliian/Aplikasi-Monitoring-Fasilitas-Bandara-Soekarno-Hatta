package com.example.tekkomundip.kpangkasapura2;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class PelaporListUnit extends ArrayAdapter<PelaporModelUnit> {
    private Activity context;
    private List<PelaporModelUnit> pelaporListUnit;

    public PelaporListUnit(Activity context, List<PelaporModelUnit> pelaporListUnit){
        super(context, R.layout.list_layout_unit, pelaporListUnit);
        this.context = context;
        this.pelaporListUnit = pelaporListUnit;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout_unit, null, true);

        TextView textViewNamaPelapor = (TextView) listViewItem.findViewById(R.id.textViewNamaPelapor);
        TextView textViewBarang = (TextView) listViewItem.findViewById(R.id.textViewBarang);
        TextView textViewArea = (TextView) listViewItem.findViewById(R.id.textViewArea);
        TextView textViewJam = (TextView) listViewItem.findViewById(R.id.textViewJam);
        TextView textViewTanggal = (TextView) listViewItem.findViewById(R.id.textViewTanggal);
        TextView textViewStatus = (TextView) listViewItem.findViewById(R.id.textViewStatusLapor);
        TextView textViewPesan = (TextView) listViewItem.findViewById(R.id.textViewPesan);

        PelaporModelUnit pelaporunit= pelaporListUnit.get(position);

        textViewNamaPelapor.setText(pelaporunit.getNamapelapor());
        textViewBarang.setText(pelaporunit.getBarang());
        textViewArea.setText(pelaporunit.getArea());
        textViewJam.setText(pelaporunit.getJam());
        textViewTanggal.setText(pelaporunit.getHariBulanTahun());
        textViewStatus.setText(pelaporunit.getStatus());
        textViewPesan.setText(pelaporunit.getPesan());

        return listViewItem;
    }
}
