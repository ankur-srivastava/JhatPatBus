package com.edocent.jhatpatbus;


import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    Button travelDateIdText;

    public DatePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public DatePickerDialog onCreateDialog(Bundle bundle){
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_WEEK);

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth+"/"+monthOfYear+"/"+year;
        getTravelDateIdText().setText(date);
    }

    public Button getTravelDateIdText() {
        return travelDateIdText;
    }

    public void setTravelDateIdText(Button travelDateIdText) {
        this.travelDateIdText = travelDateIdText;
    }
}
