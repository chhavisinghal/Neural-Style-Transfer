package com.sonali.styletransfer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<RecyclerViewModel> arrayList;
    private ButtonInterface buttonInterface;

    public RecyclerViewAdapter(Context context, ArrayList<RecyclerViewModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int i) {
        final ViewHolder viewHolder1 = (ViewHolder) holder;
        int style_image_id=arrayList.get(i).getImgId();
        Picasso.with(context).load(style_image_id).into(viewHolder1.getIvStyleImage());
        viewHolder1.getIvStyleImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(buttonInterface!=null) {
                    Log.i("kkk","there1");

                    buttonInterface.onclick(v,arrayList.get(i));
                    viewHolder1.getIvStyleImage().setImageAlpha(60);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void buttonSetOnclick(ButtonInterface buttonInterface){
        Log.i("kkk","there2");
        this.buttonInterface=buttonInterface;
    }

    public interface ButtonInterface{
        public void onclick( View view,RecyclerViewModel model);

    }
}
