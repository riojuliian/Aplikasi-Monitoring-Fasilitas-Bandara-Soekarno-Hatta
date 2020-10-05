package com.example.tekkomundip.kpangkasapura2;

import android.graphics.drawable.Drawable;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload> mUploads;



    public ImageAdapter(Context context, List<Upload> uploads) {
        mContext = context;
        mUploads = uploads;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(getItemCount()-(position + 1));
        holder.textViewName.setText(" Nama Teknisi : " + uploadCurrent.getNamaTeknisi());
        holder.textViewName2.setText(" " + uploadCurrent.getKeterangan());
        holder.textViewName3.setText(" " + uploadCurrent.getBarang());
        holder.textViewName4.setText(" " + uploadCurrent.getTanggal());

        Picasso.with(mContext)
                .load(uploadCurrent.getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .fit()
                .centerInside()
                .placeholder(R.drawable.logo)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName, textViewName2, textViewName3, textViewName4;
        public ImageView imageView;

        public ImageViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewName2 = itemView.findViewById(R.id.text_view_name2);
            textViewName3 = itemView.findViewById(R.id.text_view_name3);
            textViewName4 =itemView.findViewById(R.id.text_view_name4);
            imageView = itemView.findViewById(R.id.image_view_upload);
        }
    }
}