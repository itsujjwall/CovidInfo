package com.example.covidinfo.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidinfo.Models.StateWiseModel;
import com.example.covidinfo.R;

import java.text.NumberFormat;
import java.util.ArrayList;

public class StateWiseAdapter extends RecyclerView.Adapter<StateWiseAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<StateWiseModel> stateWiseModelArrayList;

    public StateWiseAdapter(Context mContext, ArrayList<StateWiseModel> stateWiseModelArrayList) {
        this.mContext = mContext;
        this.stateWiseModelArrayList = stateWiseModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_state_wise, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StateWiseModel currentItem = stateWiseModelArrayList.get(position);
        String stateName = currentItem.getState();
        String stateTotal = currentItem.getConfirmed();
        holder.tv_stateTotalCases.setText(NumberFormat.getInstance().format(Long.parseLong(stateTotal)));
        holder.tv_stateName.setText(stateName);
    }

    public void filterList(ArrayList<StateWiseModel> filteredList, String text) {
        stateWiseModelArrayList = filteredList;
        //this.searchText = text;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return stateWiseModelArrayList ==null ? 0 : stateWiseModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_stateName, tv_stateTotalCases;
        LinearLayout lin_state_wise;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_stateName = itemView.findViewById(R.id.statewise_layout_name_textview);
            tv_stateTotalCases = itemView.findViewById(R.id.statewise_layout_confirmed_textview);
            lin_state_wise = itemView.findViewById(R.id.layout_state_wise_lin);
        }
    }
}
