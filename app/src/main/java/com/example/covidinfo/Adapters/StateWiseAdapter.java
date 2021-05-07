package com.example.covidinfo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.covidinfo.EachStateDataActivity;
import com.example.covidinfo.Models.StateWiseModel;
import com.example.covidinfo.R;

import java.text.NumberFormat;
import java.util.ArrayList;

import static com.example.covidinfo.Constants.STATE_ACTIVE;
import static com.example.covidinfo.Constants.STATE_CONFIRMED;
import static com.example.covidinfo.Constants.STATE_CONFIRMED_NEW;
import static com.example.covidinfo.Constants.STATE_DEATH;
import static com.example.covidinfo.Constants.STATE_DEATH_NEW;
import static com.example.covidinfo.Constants.STATE_LAST_UPDATE;
import static com.example.covidinfo.Constants.STATE_NAME;
import static com.example.covidinfo.Constants.STATE_RECOVERED;
import static com.example.covidinfo.Constants.STATE_RECOVERED_NEW;

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
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        StateWiseModel currentItem = stateWiseModelArrayList.get(position);
        String stateName = currentItem.getState();
        String stateTotal = currentItem.getConfirmed();
        holder.tv_stateTotalCases.setText(NumberFormat.getInstance().format(Long.parseLong(stateTotal)));
        holder.tv_stateName.setText(stateName);

        holder.lin_state_wise.setOnClickListener(v -> {
            StateWiseModel clickedItem = stateWiseModelArrayList.get(position);
            Intent perStateIntent = new Intent(mContext, EachStateDataActivity.class);

            perStateIntent.putExtra(STATE_NAME, clickedItem.getState());
            perStateIntent.putExtra(STATE_CONFIRMED, clickedItem.getConfirmed());
            perStateIntent.putExtra(STATE_CONFIRMED_NEW, clickedItem.getConfirmed_new());
            perStateIntent.putExtra(STATE_ACTIVE, clickedItem.getActive());
            perStateIntent.putExtra(STATE_DEATH, clickedItem.getDeath());
            perStateIntent.putExtra(STATE_DEATH_NEW, clickedItem.getDeath_new());
            perStateIntent.putExtra(STATE_RECOVERED, clickedItem.getRecovered());
            perStateIntent.putExtra(STATE_RECOVERED_NEW, clickedItem.getRecovered_new());
            perStateIntent.putExtra(STATE_LAST_UPDATE, clickedItem.getLastupdate());

            mContext.startActivity(perStateIntent);

        });
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
