package com.essbidali.pantrywatcher.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.businesslogic.ProfileDetail;

import java.util.List;

public class ProfileDetailAdapter extends RecyclerView.Adapter<ProfileDetailAdapter.ViewHolder> implements EditDialogFragment.DialogListener {
    List<ProfileDetail> details;
    Context context;

    public ProfileDetailAdapter(List<ProfileDetail> details, Context context){
        this.details = details;
        this.context = context;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView detailTitleTextView, detailTextView;
        Button editButton;
        ImageView profileDetailImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            detailTitleTextView = itemView.findViewById(R.id.detailTitleTextView);
            detailTextView = itemView.findViewById(R.id.detailTextView);
            editButton = itemView.findViewById(R.id.editButton);
            profileDetailImageView = itemView.findViewById(R.id.profileDetailImageView);
        }
    }

    @NonNull
    @Override
    public ProfileDetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.profile_detail_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProfileDetailAdapter.ViewHolder holder, final int position) {
        final ProfileDetail detail = details.get(position);

        setDecorativeIcon(detail, holder);
        holder.detailTitleTextView.setText(detail.getTitle());
        holder.detailTextView.setText(detail.getValue());
        holder.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditDialogFragment editDialog = EditDialogFragment.newInstance (detail.getTitle(), detail.getValue(), Integer.toString(position));
                editDialog.setMyListener(ProfileDetailAdapter.this);
                FragmentManager fManager = ((AppCompatActivity)context).getSupportFragmentManager();
                editDialog.show(fManager, "EditDialogFragment");
            }
        });
    }

    private void setDecorativeIcon(ProfileDetail detail, ViewHolder holder) {
        if(detail.getType().equals("user")){
            holder.profileDetailImageView.setImageResource(R.drawable.profile_icon);
        }
    }


    @Override
    public int getItemCount() {
        return details.size();
    }

    @Override
    public void onDialogPositiveClick(EditDialogFragment dialog) {
        int position = dialog.getPosition();
        details.get(position).setValue(dialog.getValue());
        this.notifyItemChanged(position);
    }
}
