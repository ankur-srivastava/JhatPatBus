package com.edocent.jhatpatbus;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.edocent.jhatpatbus.models.Bus;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.busroutetimings.BusroutetimingsService;
import com.liferay.mobile.screens.context.SessionContext;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BusListFragment extends Fragment implements AdapterView.OnItemClickListener{

    ListView busListView;

    static String TAG = DisplayBusesFragment.class.getSimpleName();
    Session session = SessionContext.createSessionFromCurrentSession();
    List<Bus> busList;

    private OnFragmentInteractionListener mListener;

    String fromLocation;
    String toLocation;
    String date;

    public BusListFragment() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            Bus tempBus = busList.get(position);
            Log.v(TAG, "Following Bus object was clicked "+tempBus.getBusId());
            mListener.onFragmentInteraction(tempBus);
        }
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Bus bus);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bus_list, container, false);
        busListView = (ListView) view.findViewById(R.id.fragmentBusListId);
        busListView.setOnItemClickListener(this);
        new GetBusesAsyncTask().execute(this.date, this.fromLocation, this.toLocation);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnFragmentInteractionListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class GetBusesAsyncTask extends AsyncTask<String, Void, List> {

        @Override
        protected List<Bus> doInBackground(String[] params) {
            BusroutetimingsService busroutetimingsService = null;
            JSONArray jsonArray = null;
            List<Bus> tempBusList = null;

            if(session != null){
                Log.v(TAG, "Got Session");
                busroutetimingsService = new BusroutetimingsService(session);
                try {
                    jsonArray = busroutetimingsService.getBusBasedOnRouteAndDate(params[0], params[1], params[2]);
                }catch (Exception e){
                    Log.v(TAG, "Error "+e.getMessage());
                }
            }

            if(jsonArray != null){
                tempBusList = new ArrayList<Bus>();
                for(int i=0; i<jsonArray.length(); i++){
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Log.v(TAG, "From JSON - " + jsonObject.getString("price"));

                        int id = i+1;
                        Bus tempBus = new Bus(id, jsonObject.getString("busDetails"), jsonObject.getInt("total"),
                                jsonObject.getInt("available"), jsonObject.getDouble("price"), jsonObject.getString("travelTime"), jsonObject.getString("travelDate"));

                        if(tempBus != null){
                            tempBusList.add(tempBus);
                        }
                    }catch (Exception e){
                        Log.v(TAG, "Error "+e.getMessage());
                    }
                }
            }

            return tempBusList;
        }

        @Override
        protected void onPostExecute(List list) {
            super.onPostExecute(list);
            busList = list;
            if(busList != null){
                BusListAdapter adapter = new BusListAdapter(getActivity(), R.layout.bus_listview_row, busList);
                busListView.setAdapter(adapter);
            }
        }
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
