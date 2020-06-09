package com.essbidali.pantrywatcher.ui.homepagefragments.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.essbidali.pantrywatcher.R;
import com.essbidali.pantrywatcher.businesslogic.Notification;
import com.essbidali.pantrywatcher.businesslogic.SampleDataSupplier;
import com.essbidali.pantrywatcher.ui.homepagefragments.MainSectionFragment;

import java.util.List;

public class NotificationsFragment extends Fragment implements MainSectionFragment {

    List<Notification> notificationsList;
    List<Notification> oldNotificationsList;
    NotificationsAdapter nAdapter;
    NotificationsAdapter oldAdapter;


    public NotificationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        //Hooks
        RecyclerView notificationsRecyclerView = view.findViewById(R.id.notificationsRecyclerView);
        RecyclerView oldNotificationsRecyclerView = view.findViewById(R.id.oldNotificationsRecyclerView);
        TextView earlierNotificationsTextView = view.findViewById(R.id.earlierNotificationsTextView);

        //Fetch new notification items and display in recyclerView
        notificationsList = fetchNewNotifications();
        nAdapter = new NotificationsAdapter(notificationsList);
        notificationsRecyclerView.setAdapter(nAdapter);
        notificationsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        //Fetch new notification items and display in recyclerView
        oldNotificationsList = fetchOldNotifications();
        oldAdapter = new NotificationsAdapter(oldNotificationsList);
        oldNotificationsRecyclerView.setAdapter(oldAdapter);
        oldNotificationsRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        //Only display these when there are viewed notifications
        if(oldNotificationsList.isEmpty()){
            earlierNotificationsTextView.setVisibility(View.GONE);
            oldNotificationsRecyclerView.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public int getTitleId() {
        return R.string.notification_page_title;
    }

    List<Notification> fetchNewNotifications(){
        return new SampleDataSupplier().supplyNewNotifications();
    }

    List<Notification> fetchOldNotifications(){
        return new SampleDataSupplier().supplyOldNotifications();
    }


}
