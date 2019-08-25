package com.sonali.styletransfer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView ivStyleImage;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        ivStyleImage= (ImageView) itemView.findViewById(R.id.ivStyleImage);
    }

    public ImageView getIvStyleImage() {
        return ivStyleImage;
    }
}
