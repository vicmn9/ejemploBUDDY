package com.example.easyway;

import java.util.ArrayList;
import java.util.Comparator;

public class ListRutaSyngleton {
    private static ListRutaSyngleton instance;
    private ArrayList<Ruta> rutas;

    public static ListRutaSyngleton getInstance(){
        if(instance==null){
            instance=new ListRutaSyngleton();
        }
        return instance;
    }

    public ArrayList<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(ArrayList<Ruta> rutas) {
        this.rutas = rutas;
    }

    public void onReset(){
        rutas=new ArrayList<Ruta>();
    }

    public void addElement(Ruta ruta){
        rutas.add(ruta);
    }

    public void onSort(){
        rutas.sort(new Comparator<Ruta>() {
            @Override
            public int compare(Ruta o1, Ruta o2) {
                double kmO1=Double.parseDouble(o1.getDistancia().split(" ")[0]);
                double kmO2=Double.parseDouble(o2.getDistancia().split(" ")[0]);
                if(o1.getDuracionSegunto()<o2.getDuracionSegunto()){
                    return -1;
                }else if(o1.getDuracionSegunto()==o2.getDuracionSegunto() && kmO1<kmO2){
                    return -1;
                }else if(o1.getDuracionSegunto()==o2.getDuracionSegunto() && kmO1==kmO2){
                    return 0;
                }else if(o1.getDuracionSegunto()==o2.getDuracionSegunto() && kmO1>kmO2){
                    return 1;
                }else{
                    return 1;
                }
            }
        });
    }
}
