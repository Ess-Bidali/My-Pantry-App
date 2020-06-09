package com.essbidali.pantrywatcher.ui.homepagefragments.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.businesslogic.SummaryItem;

import java.util.List;

public class SummaryAdapter extends RecyclerView.Adapter<SummaryAdapter.ViewHolder> {

    private List<SummaryItem> summaryItems;

    //Constructor
    SummaryAdapter(List<SummaryItem> summaryItems){
        this.summaryItems = summaryItems;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView titleTextView, numberTextView, learnMoreTextView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            //Hold reference to the views to be customized to each item
            cardView = itemView.findViewById(R.id.summaryCardView);
            titleTextView = itemView.findViewById(R.id.summaryTitleTextView);
            numberTextView = itemView.findViewById(R.id.summaryNumberTextView);
            learnMoreTextView = itemView.findViewById(R.id.learnMoreTextView);
        }
    }

    @NonNull
    @Override
    public SummaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View summaryView = inflater.inflate(R.layout.summary_item_layout, parent, false);
        return new ViewHolder(summaryView);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryAdapter.ViewHolder holder, int position) {
        SummaryItem item = summaryItems.get(position);

        //Customize the general layout views to each item
        holder.titleTextView.setText(item.getTitle());
        String quantity = "(" + item.getQuantity() + ")";
        holder.numberTextView.setText(quantity);
        holder.learnMoreTextView.setText(R.string.learn_more);
    }

    @Override
    public int getItemCount() {
        return summaryItems.size();
    }

}
