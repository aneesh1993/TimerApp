package com.mobileappclass.midterm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddFragment extends Fragment {

    EditText taskName, taskTime;
    Button confirmButton;


    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add, container, false);

        taskName = (EditText)view.findViewById(R.id.enterTaskName);
        taskTime = (EditText)view.findViewById(R.id.enterTaskTime);
        confirmButton = (Button)view.findViewById(R.id.confirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmClicked();
            }
        });

        return view;
    }

    public void confirmClicked(){
        String[] toPass = new String[2];
        String name = taskName.getText().toString();
        String time = taskTime.getText().toString();

        toPass[0] = name;
        toPass[1] = time;

        ((MainActivity)getActivity()).setListFromFrag(toPass);
        ((MainActivity)getActivity()).startTimer(toPass[1] + "", toPass[0]);
        taskName.setText("");
        taskTime.setText("");
    }

}
