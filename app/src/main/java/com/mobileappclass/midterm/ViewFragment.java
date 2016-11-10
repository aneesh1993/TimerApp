package com.mobileappclass.midterm;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFragment extends Fragment {

    TextView viewTaskName, viewTaskTime;

    public ViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view, container, false);

        viewTaskName = (TextView)view.findViewById(R.id.viewTaskName);
        viewTaskTime = (TextView)view.findViewById(R.id.viewTaskTime);

        Bundle args = getArguments();
        if(args != null){
            viewTaskName.setText(args.getString("name"));
            viewTaskTime.setText(args.getString("time"));
        }



        return view;
    }

}
