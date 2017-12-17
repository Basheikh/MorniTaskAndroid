package com.example.majid_fit5.mornitask.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.example.majid_fit5.mornitask.R;

public class ProgressViewHolder extends RecyclerView.ViewHolder {
    private ProgressBar mPrgBar;
    public ProgressViewHolder(View itemView) {
        super(itemView);
        mPrgBar = itemView.findViewById(R.id.progressBar);
    }
}
