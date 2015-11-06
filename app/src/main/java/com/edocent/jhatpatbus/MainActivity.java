package com.edocent.jhatpatbus;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.edocent.jhatpatbus.models.Bus;
import com.liferay.mobile.screens.context.SessionContext;
import com.liferay.mobile.screens.context.storage.CredentialsStoreBuilder;


public class MainActivity extends Activity implements LoginFragment.LoginListener, HomeFragment.HomeListener,
        BusDetailsFragment.BusDetailsListener, BusListFragment.OnFragmentInteractionListener, BookBusFragment.OnFragmentInteractionListener{

    static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            SessionContext.loadSessionFromStore(CredentialsStoreBuilder.StorageType.SHARED_PREFERENCES);
        }catch (Exception e){
            Log.v(TAG, "Error loading session "+e.getMessage());
        }

        if(SessionContext.hasSession()){
            callHome();
        }else{
            LoginFragment loginFragment = new LoginFragment();
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mainFrameId, loginFragment);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void callSignup() {
        Log.v(TAG, "In Call Signup");
        SignupFragment signupFragment = new SignupFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameId, signupFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void callHome() {
        Log.v(TAG, "In Call Home");
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameId, homeFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    /*
    @Override
    public void displayBuses(String fromLocation, String toLocation, String date) {
        Log.v(TAG, "In Display Buses");

        DisplayBusesFragment displayBusesFragment = new DisplayBusesFragment();
        displayBusesFragment.setFromLocation(fromLocation);
        displayBusesFragment.setToLocation(toLocation);
        displayBusesFragment.setDate(date);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameId, displayBusesFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }*/

    @Override
    public void displayBuses(String fromLocation, String toLocation, String date) {
        Log.v(TAG, "In Display Buses");

        BusListFragment busListFragment = new BusListFragment();
        busListFragment.setFromLocation(fromLocation);
        busListFragment.setToLocation(toLocation);
        busListFragment.setDate(date);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameId, busListFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Bus bus) {
        Log.v(TAG, "Load Bus Details");
        BusDetailsFragment busDetailsFragment = new BusDetailsFragment();
        busDetailsFragment.setBusDetails(bus);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameId, busDetailsFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void bookBus(Bus bus) {
        BookBusFragment bookBusFragment = new BookBusFragment();
        bookBusFragment.setBus(bus);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrameId, bookBusFragment);
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
