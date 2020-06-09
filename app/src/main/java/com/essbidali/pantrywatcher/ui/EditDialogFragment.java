package com.essbidali.pantrywatcher.ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.essbidali.pantrywatcher.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;


public class EditDialogFragment extends DialogFragment {

    private static final String TITLE = "title";
    private static final String VALUE = "value";
    private static final String POSITION = "position";

    private String title;
    private String value;
    private String position;
    private String input;
    DialogListener listener;
    TextView dialogTitle;
    TextInputLayout dialogFieldHint;
    TextInputEditText fieldTextInputEditText;
    Button positiveButton, negativeButton;

    public EditDialogFragment() {
        // Required empty public constructor
    }

    public interface DialogListener {
        void onDialogPositiveClick(EditDialogFragment dialog);
    }

    public void setMyListener(DialogListener listener){
        this.listener = listener;
    }

    public String getValue() {
        return value;
    }

    public int getPosition() {
        return Integer.parseInt(position);
    }

    public static EditDialogFragment newInstance(String title, String value, String position) {
        EditDialogFragment fragment = new EditDialogFragment();
        Bundle args = new Bundle();
        args.putString(TITLE, title);
        args.putString(VALUE, value);
        args.putString(POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(TITLE);
            value = getArguments().getString(VALUE);
            position = getArguments().getString(POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_field_dialog, container, false);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        View view  = inflater.inflate(R.layout.fragment_edit_field_dialog, null);

        //Hooks and modifications
        dialogTitle = view.findViewById(R.id.dialogTitleTextView);
        dialogFieldHint = view.findViewById(R.id.dialogFieldHint);
        fieldTextInputEditText = view.findViewById(R.id.fieldTextInputEditText);
        positiveButton = view.findViewById(R.id.positiveButton);
        negativeButton = view.findViewById(R.id.negativeButton);

        fieldTextInputEditText.setText(value);
        String headerText = "Edit " + title;
        dialogTitle.setText(headerText);
        dialogFieldHint.setHint(title);

        enablePositiveButton(false);

        //Listeners
        //Check for change in input and activate positive button
        fieldTextInputEditText.addTextChangedListener(checkInput());

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeValue();
                listener.onDialogPositiveClick(EditDialogFragment.this);
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

    TextWatcher checkInput(){
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void afterTextChanged(Editable s) {
                input = Objects.requireNonNull(fieldTextInputEditText.getText()).toString().trim();
                if(input.length() > 0 && !input.equals(value)){
                    if(!positiveButton.isEnabled()){
                        enablePositiveButton(true);
                    }
                }
                else {
                    enablePositiveButton(false);
                }
            }
        };
    }

    @SuppressLint("ResourceAsColor")
    void enablePositiveButton(Boolean bool){
        positiveButton.setEnabled(bool);

        if(bool){
            positiveButton.setTextColor( getResources().getColor(R.color.colorText));
        } else{
            positiveButton.setTextColor(R.color.material_on_background_disabled);

        }
    }

    public void changeValue(){
        input = Objects.requireNonNull(fieldTextInputEditText.getText()).toString().trim();
        this.value = input;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if(positiveButton.isEnabled()){
            enablePositiveButton(false);
        }
    }
}