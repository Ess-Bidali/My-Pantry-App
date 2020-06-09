package com.essbidali.pantrywatcher.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.essbidali.pantrywatcher.R;


public class DeleteDialogFragment extends DialogFragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TITLE = "title";
    private static final String POSITION = "position";

    private String title;
    private String position;
    DeleteDialogFragment.DialogListener listener;
    TextView dialogTitleTextView, contentTextView;
    Button positiveButton, negativeButton;


    public DeleteDialogFragment() {
        // Required empty public constructor
    }

    public interface DialogListener {
        void onDialogPositiveClick(DeleteDialogFragment dialog);
    }

    public void setMyListener(DeleteDialogFragment.DialogListener listener){
        this.listener = listener;
    }

    public int getPosition() {
        return Integer.parseInt(position);
    }

    public static DeleteDialogFragment newInstance(String name, String position) {
        DeleteDialogFragment fragment = new DeleteDialogFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, name);
        args.putString(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            position = getArguments().getString(POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_delete_dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        View view  = inflater.inflate(R.layout.fragment_delete_dialog, null);

        //Hooks and modifications
        dialogTitleTextView = view.findViewById(R.id.dialogTitleTextView);
        contentTextView = view.findViewById(R.id.contentTextView);
        positiveButton = view.findViewById(R.id.positiveButton);
        negativeButton = view.findViewById(R.id.negativeButton);

        String headerText = "Delete " + title;
        dialogTitleTextView.setText(headerText);
        String content = "Are you sure you want to delete " + title.toLowerCase() + "?";
        contentTextView.setText(content);

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDialogPositiveClick(DeleteDialogFragment.this);
                dismiss();
            }
        });
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        builder.setView(view);

        return builder.create();
    }

}