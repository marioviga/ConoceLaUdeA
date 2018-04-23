package com.example.samirvega.practica2actividades.modelos;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.samirvega.practica2actividades.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by SAMIR VEGA on 23/04/2018.
 */

public class adapterudea extends RecyclerView.Adapter<adapterudea.udeaViewHolder>{

    private ArrayList<udeaActivity> udealist;
    private int resource;
    private Activity activity;

    public adapterudea(ArrayList<udeaActivity> udealist,int resource,Activity activity){
        this.udealist = udealist;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public udeaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(resource,parent,false);

        return new udeaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(udeaViewHolder holder, int position) {
        udeaActivity udeaActivity= udealist.get(position);
        holder.bindUdea(udeaActivity,activity);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class udeaViewHolder extends RecyclerView.ViewHolder {

        private TextView tnombre,tvalor;
        private ImageView iFoto;

        public udeaViewHolder(View itemView) {
            super(itemView);
            tnombre = itemView.findViewById(R.id.tnombre);
            tvalor = itemView.findViewById(R.id.tvalor);
            iFoto = itemView.findViewById(R.id.iFoto);
        }

        public void bindUdea(udeaActivity udeaActivity, Activity activity) {
            tnombre.setText(udeaActivity.getNombre());
            tvalor.setText(udeaActivity.getValor());
            Picasso.get().load(udeaActivity.getFoto()).into(iFoto);

        }
    }

}
