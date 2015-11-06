package com.edocent.jhatpatbus;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListFragment;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.edocent.jhatpatbus.dummy.DummyContent;
import com.edocent.jhatpatbus.models.Bus;
import com.liferay.mobile.android.service.Session;
import com.liferay.mobile.android.v62.busroutetimings.BusroutetimingsService;
import com.liferay.mobile.screens.context.SessionContext;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayBusesFragment extends ListFragment {

    static String TAG = DisplayBusesFragment.class.getSimpleName();
    Session session = SessionContext.createSessionFromCurrentSession();
    List<Bus> busList;

    private OnFragmentInteractionListener mListener;

    String fromLocation;
    String toLocation;
    String date;

    public DisplayBusesFragment() { }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Bus bus);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GetBusesAsyncTask().execute(this.date, this.fromLocation, this.toLocation);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if (null != mListener) {
            Bus tempBus = this.busList.get(position);
            Log.v(TAG, "Following Bus object was clicked "+tempBus.getBusId());
            mListener.onFragmentInteraction(tempBus);
        }
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
                Log.v(TAG,busList.get(1).getBusName());
                // TODO: Change Adapter to display your content

                setListAdapter(new ArrayAdapter<Bus>(getActivity(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, busList) {
                    @Override
                    public View getView(int pos, View v, ViewGroup vg) {
                        TextView textView = (TextView) super.getView(pos, v, vg);
                        /*
                        SpannableStringBuilder builder = new SpannableStringBuilder();
                        builder.append(textView.getText()+"  ")
                                .append(" ", new ImageSpan(getActivity(), R.drawable.arrow), 0);

                        textView.setText(builder);
                        */
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                        textView.setTextAppearance(getContext(), android.R.style.TextAppearance_Large);
                        textView.setTextColor(getResources().getColor(R.color.westeros_red));
                        return textView;
                    }
                });
            }

        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
