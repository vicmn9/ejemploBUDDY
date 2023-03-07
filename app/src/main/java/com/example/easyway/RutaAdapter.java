package com.example.easyway;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class RutaAdapter extends RecyclerView.Adapter<RutaAdapter.ViewHolder> {

    private List<Ruta> rutas;
    private Context context;
    private LayoutInflater myInflater;
    public RutaAdapter (ArrayList<Ruta> rutas, Context context) {
        this.context=context;
        this.myInflater=LayoutInflater.from(context);
        this.rutas = rutas;
    }

    @NonNull
    @Override
    public RutaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = myInflater.inflate(R.layout.item_ruta_info,null);
        return new RutaAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RutaAdapter.ViewHolder holder, int position) {
        holder.bindData(rutas.get(position));
    }

    @Override
    public int getItemCount() {
        return this.rutas.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView infoOrigenDestinoR;
        AppCompatTextView infoTiempoRuta;
        AppCompatTextView infoDistanciaRuta;


        ViewHolder(View itemView) {
            super(itemView);
            infoOrigenDestinoR = itemView.findViewById(R.id.origenDestinoRuta);
            infoTiempoRuta = itemView.findViewById(R.id.tiempoRuta);
            infoDistanciaRuta = itemView.findViewById(R.id.distanciaRuta);
        }

        void bindData(final Ruta item){
            infoOrigenDestinoR.setText(item.getInfoOrigenDest());
            infoTiempoRuta.setText(item.getInfoTiempo());
            infoDistanciaRuta.setText(item.getDistancia());
        }

    }
}
