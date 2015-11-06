package com.edocent.jhatpatbus;


import android.app.Activity;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.edocent.jhatpatbus.models.Bus;
import com.edocent.jhatpatbus.models.Locations;
import com.liferay.mobile.android.auth.basic.BasicAuthentication;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.service.SessionImpl;
import com.liferay.mobile.android.v62.busroutetimings.BusroutetimingsService;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.User;
import com.liferay.mobile.screens.context.storage.CredentialsStoreBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements View.OnClickListener{

    static String TAG = HomeFragment.class.getSimpleName();

    List<Bus> busList;
    User _user;
    HomeListener mHomeListener;

    TextView welcomeHomeId;
    Button findBusesButton;
    Button travelDate;
    Spinner fromLocation;
    Spinner toLocation;

    int fromLocationId;
    int toLocationId;

    Session session = SessionContext.createSessionFromCurrentSession();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.v(TAG, "Going to store session");
        SessionContext.storeSession(CredentialsStoreBuilder.StorageType.SHARED_PREFERENCES);
        Log.v(TAG, "session stored");
        _user = SessionContext.getLoggedUser();

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        welcomeHomeId = (TextView) view.findViewById(R.id.welcomeHomeId);
        travelDate = (Button) view.findViewById(R.id.travelDateId);
        fromLocation = (Spinner) view.findViewById(R.id.fromLocationId);
        toLocation = (Spinner) view.findViewById(R.id.toLocationId);
        findBusesButton = (Button) view.findViewById(R.id.findBusId);
        findBusesButton.setOnClickListener(this);
        travelDate.setOnClickListener(this);

        if(_user != null){
            Log.v(TAG, "In Home user is "+_user.getEmail());
            welcomeHomeId.setText("Welcome "+_user.getFirstName());
        }

        ArrayAdapter<Locations> fromLocationsAdapter = new ArrayAdapter<Locations>(getActivity(), android.R.layout.simple_spinner_item, Locations.fromLocationsArray);
        fromLocationsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromLocation.setAdapter(fromLocationsAdapter);
        fromLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v(TAG, "From Drop down clicked ");
                Locations location = (Locations)parent.getItemAtPosition(position);
                Log.v(TAG, "Got location ");
                fromLocationId = location.getLocationId();
                Log.v(TAG, "Selected from location id is "+fromLocationId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<Locations> toLocationsAdapter = new ArrayAdapter<Locations>(getActivity(), android.R.layout.simple_spinner_item, Locations.toLocationsArray);
        toLocationsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toLocation.setAdapter(toLocationsAdapter);
        toLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Locations location = (Locations)parent.getItemAtPosition(position);
                toLocationId = location.getLocationId();
                Log.v(TAG, "Selected to location id is "+toLocationId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Log.v(TAG, "Clicked "+id);
        if(id == R.id.findBusId){
            if(this.fromLocationId == 0){
                Toast.makeText(v.getContext(), "Please choose a starting point", Toast.LENGTH_SHORT).show();
            }else if(this.toLocationId == 0){
                Toast.makeText(v.getContext(), "Please choose your final stop", Toast.LENGTH_SHORT).show();
            }else if(travelDate == null){
                Toast.makeText(v.getContext(), "When do you want to travel ?", Toast.LENGTH_SHORT).show();
            }else{
                if(this.mHomeListener != null){
                    Log.v(TAG, "Data being passed is "+this.fromLocationId+" and "+this.toLocationId);
                    this.mHomeListener.displayBuses(String.valueOf(this.fromLocationId), String.valueOf(this.toLocationId), travelDate.getText().toString());
                }
            }
        }else if(id == R.id.travelDateId){
            Log.v(TAG, "In Date Picker");
            DatePickerFragment dialogFragment = new DatePickerFragment();
            dialogFragment.setTravelDateIdText(travelDate);
            dialogFragment.show(getFragmentManager(), "datePicker");
        }
    }

    static interface HomeListener{
        void displayBuses(String fromLocation, String toLocation, String date);
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        mHomeListener = (HomeListener) activity;
    }
}
