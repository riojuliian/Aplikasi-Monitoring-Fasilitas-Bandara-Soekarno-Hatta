package com.example.tekkomundip.kpangkasapura2;

import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.TextView;



import java.util.ArrayList;



/**

 * Created by Dimas Maulana on 5/26/17.

 * Email : araymaulana66@gmail.com

 */



public class AdapterTeknisi extends RecyclerView.Adapter<AdapterTeknisi.TeknisiViewHolder> {





    private ArrayList<PelaporModelUnit> dataList;



    public AdapterTeknisi(ArrayList<PelaporModelUnit> dataList) {

        this.dataList = dataList;

    }



    @Override

    public TeknisiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.layout_list_laporanteknisi, parent, false);

        return new TeknisiViewHolder(view);

    }



    @Override

    public void onBindViewHolder(TeknisiViewHolder holder, int position) {

        holder.txtNama.setText(dataList.get(position).getNamapelapor());

        holder.txtJam.setText(dataList.get(position).getJam());

        holder.txtPesan.setText(dataList.get(position).getPesan());

    }



    @Override

    public int getItemCount() {

        return (dataList != null) ? dataList.size() : 0;

    }



    public class TeknisiViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNama, txtJam, txtPesan;



        public TeknisiViewHolder(View itemView) {

            super(itemView);

            txtNama = (TextView) itemView.findViewById(R.id.txt_nama);

            txtJam = (TextView) itemView.findViewById(R.id.txt_jam);

            txtPesan = (TextView) itemView.findViewById(R.id.txt_pesan);

        }

    }

}