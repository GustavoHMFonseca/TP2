package com.gustavofonseca.tp2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

public class RecyclerViewAdapter extends  RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{


    private ArrayList<No> mNo;
    private Context mContext;
    private ChamaPaginas call;


    public RecyclerViewAdapter(Context mContext, ArrayList<No> mNo, ChamaPaginas call) {
        this.mNo = mNo;
        this.mContext = mContext;
        this.call = call;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder  holder = new ViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.cellName.setText(mNo.get(position).getNome());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,mNo.get(position).getNome(), Toast.LENGTH_SHORT).show();
                if(mNo.get(position).getIsDocument() == false) {
                    Log.d("Recicler", "CHAMOU BB");
                    call.ChamaPaginas(mNo.get(position), position);
                }
                else
                {
                    call.ExibeDado(mNo.get(position));
                }


            }
        });

        holder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("Recicler", "Apagou");
                call.removeNo(position);
                notifyDataSetChanged();
                return false;
            }
        });
    }




    @Override
    public int getItemCount() {
        return mNo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView cellName;
        RelativeLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cellName = itemView.findViewById(R.id.cell_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
