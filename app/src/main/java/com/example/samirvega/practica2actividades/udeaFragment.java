package com.example.samirvega.practica2actividades;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.samirvega.practica2actividades.modelos.adapterudea;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class udeaFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapterudea;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList udealist;

    public udeaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View itemView = inflater.inflate(R.layout.fragment_udea,container,false);

        recyclerView = itemView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        udealist = new ArrayList<>();

        adapterudea = new adapterudea(udealist,R.layout.cardview_1,getActivity());

        recyclerView.setAdapter(adapterudea);

        return inflater.inflate(R.layout.fragment_udea, container, false);
    }

}
