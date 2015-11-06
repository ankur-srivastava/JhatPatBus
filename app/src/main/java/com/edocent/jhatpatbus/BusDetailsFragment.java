package com.edocent.jhatpatbus;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.edocent.jhatpatbus.models.Bus;


public class BusDetailsFragment extends Fragment implements View.OnClickListener{

    Bus busDetails;
    BusDetailsListener mBusDetailsListener;

    TextView busNameId;
    TextView busDateId;
    TextView busTimeId;
    Button bookId;


    public BusDetailsFragment() {

    }

    static interface BusDetailsListener{
        void bookBus(Bus bus);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus_details, container, false);

        busNameId = (TextView) view.findViewById(R.id.busNameTextId);
        busDateId = (TextView) view.findViewById(R.id.busDateTextId);
        busTimeId = (TextView) view.findViewById(R.id.busTimeTextId);
        bookId = (Button) view.findViewById(R.id.bookBusId);

        busNameId.setText(busDetails.getBusName());
        busDateId.setText(busDetails.getBusDate());
        busTimeId.setText(busDetails.getBusTime());

        bookId.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.mBusDetailsListener = (BusDetailsListener) activity;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bookBusId){
            if(this.mBusDetailsListener != null){
                this.mBusDetailsListener.bookBus(busDetails);
            }
        }
    }

    public Bus getBusDetails() {
        return busDetails;
    }

    public void setBusDetails(Bus busDetails) {
        this.busDetails = busDetails;
    }
}
