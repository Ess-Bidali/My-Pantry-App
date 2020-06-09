package com.essbidali.pantrywatcher.ui.homepagefragments.notifications;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.businesslogic.Notification;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.ViewHolder> {

    List<Notification> notifications;

    public NotificationsAdapter(List<Notification> notifs){
        notifications = notifs;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView notificationTitleTextView;
        CardView summaryCardView;
        ImageView notificationImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            notificationTitleTextView = itemView.findViewById(R.id.notificationTitleTextView);
            summaryCardView = itemView.findViewById(R.id.summaryCardView);
            notificationImageView = itemView.findViewById(R.id.notificationImageView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View notificationView = inflater.inflate(R.layout.notification_item_layout, parent, false);
        return new NotificationsAdapter.ViewHolder(notificationView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notification notification = notifications.get(position);

        holder.notificationTitleTextView.setText(notification.getText());
//        if (!notification.viewed){
//            //Color actually shows as grey
//            holder.summaryCardView.setCardBackgroundColor(R.color.colorAccent);
//        }
        setImage(notification, holder);
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    public void setImage(Notification notific, ViewHolder holder){
        if(notific.viewed){
            holder.notificationImageView.setImageAlpha(200);
        }
        int category = notific.getCategory();
        int drawableId;
        switch (category){
            case R.string.category_shopping:
                drawableId = R.drawable.shopping_list_tab_icon;
                break;
            case R.string.category_household:
                drawableId = R.drawable.households_icon;
                break;
            case R.string.category_profile:
                drawableId = R.drawable.profile_icon;
                break;
            default:
                drawableId = R.drawable.inventory_tab_icon;
                break;
        }
        holder.notificationImageView.setImageResource(drawableId);
    }

}
