package com.edocent.jhatpatbus;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.edocent.jhatpatbus.models.Bus;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by ankursrivastava on 9/3/15.
 */
public class BusListAdapter extends ArrayAdapter<Bus> {

    Context mContext;
    int resource;
    List<Bus> busObjects;
    ImageView imageId;

    String jobsmartlyGigsURL = "http://www.jobsmartly.com/m/experiment.php";

    public BusListAdapter(Context context, int resource, List<Bus> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.resource = resource;
        this.busObjects = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View row = convertView;

        Bus bus = busObjects.get(position);

        LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
        row = inflater.inflate(resource, parent, false);

        TextView busNameText = (TextView) row.findViewById(R.id.busNameId);
        TextView busTimeText = (TextView) row.findViewById(R.id.busTimeId);
        TextView seatsAvail = (TextView) row.findViewById(R.id.seatsAvail);
        TextView priceIdText = (TextView) row.findViewById(R.id.priceId);
        imageId = (ImageView) row.findViewById(R.id.imageId);

        busNameText.setText(bus.getBusName());
        busTimeText.setText(bus.getBusTime());
        if(bus.getAvailableSeats() != 0) {
            seatsAvail.setText(bus.getAvailableSeats()+" seats left");
        }else{
            seatsAvail.setText("No seats left");
        }
        priceIdText.setText(String.valueOf(bus.getPrice())+"Rs");

        try {
            new DownloadImageTask()
                    .execute("http://www.jobsmartly.com/pics/t/15-1.jpg?1441948357");
        }catch (Exception e){
            e.printStackTrace();
        }



        return row;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

        protected Bitmap doInBackground(String... urls) {

            /*Experiment with okhttp*/
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder().url(jobsmartlyGigsURL).build();
            Call call = okHttpClient.newCall(request);
            try {
                Response response = call.execute();
                if(response.isSuccessful()){
                    JSONArray gigsArray = new JSONArray(response.body().string());
                    if(gigsArray != null){
                        for(int i=0;i<gigsArray.length();i++){
                            JSONObject jsonObject = gigsArray.getJSONObject(i);
                            String title = jsonObject.getString("gtitle");
                            Log.v(BusListAdapter.class.getSimpleName(), "From JSON Object title is "+title);
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                Log.v(BusListAdapter.class.getSimpleName(), "Error "+e.getMessage());
            } catch (JSONException e) {
                Log.v(BusListAdapter.class.getSimpleName(), "JSON Error " + e.getMessage());
            }
            /*Ends*/

            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            imageId.setImageBitmap(result);
        }
    }

}
