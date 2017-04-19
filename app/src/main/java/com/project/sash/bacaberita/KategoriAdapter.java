package com.project.sash.bacaberita;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by User on 12/04/2017.
 */

public class KategoriAdapter extends ArrayAdapter<KategoriData> {

    Context context;
    ArrayList<KategoriData> values;

    public KategoriAdapter(@NonNull Context context, ArrayList<KategoriData> values) {
        super(context,R.layout.list_kategori,values);
        this.context = context;
        this.values = values;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view  = inflater.inflate(R.layout.list_kategori,parent,false);
        TextView tvJudul = (TextView) view.findViewById(R.id.tvJudulKategori);
        ImageView ivGambar  = (ImageView) view.findViewById(R.id.ivGambarKategori);

        tvJudul.setText(values.get(position).judulKategori);
        switch (position){
            case 0:
                ivGambar.setImageResource(R.drawable.utama);
                break;
            case 1:
                ivGambar.setImageResource(R.drawable.finance);
                break;
            case 2:
                ivGambar.setImageResource(R.drawable.sport);
                break;
            case 3:
                ivGambar.setImageResource(R.drawable.seleb);
                break;
            case 4:
                ivGambar.setImageResource(R.drawable.kes);
                break;
            case 5:
                ivGambar.setImageResource(R.drawable.tekno);
                break;
            case 6:
                ivGambar.setImageResource(R.drawable.oto);
                break;
            default:
                break;
        }

        return view;
    }
}
