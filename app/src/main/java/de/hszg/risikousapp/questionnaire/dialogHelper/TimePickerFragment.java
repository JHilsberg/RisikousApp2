package de.hszg.risikousapp.questionnaire.dialogHelper;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

import de.hszg.risikousapp.R;

/**
 * Class for time picker dialog
 * additional fragment is shown in questionnaire fragment
 */
public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    /**
     * Create a new instance of TimePickerDialog and return it
     * Use the current time as the default values for the picker
     * @param savedInstanceState
     * @return time picker fragment
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        //
        return new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    /**
     * When user has selected a time, this is shown as text in the selection button in the questionnaire view.
     * @param view
     * @param hourOfDay
     * @param minute
     */
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Button time = (Button) getActivity().findViewById(R.id.timeChoose);

        time.setText(new StringBuilder().append(timeString(hourOfDay)).append(":").append(timeString(minute)));
    }

    /**
     * add a 0 to times where are less then 10 minutes
     * to make the return value matching following format: HH:MM
     * @param timeValue
     * @return time as String
     */
    private static String timeString(int timeValue) {
        if (timeValue >= 10) {
            return String.valueOf(timeValue);
        }else {
            return "0" + String.valueOf(timeValue);
        }
    }

}
