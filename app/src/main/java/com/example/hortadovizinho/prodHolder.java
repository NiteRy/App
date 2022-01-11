package com.example.hortadovizinho;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hortadovizinho.clas.Produto;

import java.util.ArrayList;

public class prodHolder extends RecyclerView.Adapter<prodHolder.MyViewHolder>
{
    private final RecycleViewInterface recycleViewInterface;

    Context context;
    ArrayList<Produto> list;
    View v;

    public prodHolder(Context context, ArrayList<Produto> list, RecycleViewInterface recycleViewInterface)
    {
        this.context = context;
        this.list = list;
        this.recycleViewInterface=recycleViewInterface;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        v=LayoutInflater.from(context).inflate(R.layout.line_prod,parent,false);

        return new MyViewHolder(v,recycleViewInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        Produto pro=list.get(position);

        holder.textView.setText("Nome: "+pro.getNome()+"\nDescrição: "+pro.getDescricao()+"\nUnidade: "+pro.getUnidade()+"\nPreço: "+pro.getPreco()+"\nStock: "+pro.getStock());

        Glide.with(v).load(pro.getFoto()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView textView;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView,RecycleViewInterface recycleViewInterface)
        {
            super(itemView);
            textView=itemView.findViewById(R.id.txtP);
            imageView=itemView.findViewById(R.id.imgP);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(recycleViewInterface!=null)
                    {
                        int pos=getAdapterPosition();
                        if(pos!=RecyclerView.NO_POSITION)
                        {
                            recycleViewInterface.onItemClick(pos);
                        }
                    }
                }
            });

        }
    }


}
