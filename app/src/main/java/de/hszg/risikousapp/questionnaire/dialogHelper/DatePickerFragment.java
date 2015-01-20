package de.hszg.risikousapp.questionnaire.dialogHelper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import de.hszg.risikousapp.R;

/**
 * Class for date picker dialog
 * additional fragment is shown in questionnaire fragment
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    /**
     * initialize fragment
     * use the current date as the default date in the picker
     * @param savedInstanceState
     * @return date picker dialog
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        return datePickerDialog;
    }

    /**
     * When user has selected a date, this is shown as text in the selection button in the questionnaire view.
     * @param view
     * @param year
     * @param month
     * @param day
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

        Button date = (Button) getActivity().findViewById(R.id.dateChoose);

        date.setText(sdf.format(c.getTime()));
    }

}
