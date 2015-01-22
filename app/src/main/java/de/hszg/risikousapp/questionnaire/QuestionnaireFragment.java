package de.hszg.risikousapp.questionnaire;

import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.ipaulpro.afilechooser.FileChooserActivity;
import com.ipaulpro.afilechooser.utils.FileUtils;

import java.util.ArrayList;

import de.hszg.risikousapp.R;
import de.hszg.risikousapp.httpHelper.GetXmlFromRisikous;
import de.hszg.risikousapp.questionnaire.dialogHelper.DatePickerFragment;
import de.hszg.risikousapp.questionnaire.dialogHelper.FileDecoder;
import de.hszg.risikousapp.questionnaire.dialogHelper.TimePickerFragment;
import de.hszg.risikousapp.questionnaire.reportingArea.ReportingArea;
import de.hszg.risikousapp.questionnaire.reportingArea.ReportingAreasParser;
import de.hszg.risikousapp.questionnaire.reportingArea.ReportingAreasSpinnerAdapter;

/**
 * Fragment that shows the questionnaire.
 */
public class QuestionnaireFragment extends Fragment implements View.OnClickListener {

    public final static String TAG = QuestionnaireFragment.class.getSimpleName();
    public final static int REQUEST_CODE = 6384;
    public String base64File= "";
    private String fileName = "";

    private static final String TAG_FC = "FileChooserActivity";

    private String questionnaireSkeleton = "";
    private String reportingAreas = "";

    private boolean viewWasAlreadyCreated = false;

    /**
     * Returns a new Instance of a Questionnaire Fragment.
     * @return QuestionnaireFragment
     */
    public static QuestionnaireFragment newInstance() {
        return new QuestionnaireFragment();
    }

    /**
     * Starts the async tasks, download questionnaire skeleton, load Text in View the first Time the Fragment is started.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        downloadQuestionnaireSkeletonAndReportingAreas();
        setRetainInstance(true);
    }

    /**
     * Set the root view and restores elements.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return root view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_questionnaire, container, false);

        if(savedInstanceState != null) {
            Button dateButton = (Button) rootView.findViewById(R.id.dateChoose);
            Button timeButton = (Button) rootView.findViewById(R.id.timeChoose);
            Spinner reportingAreas = (Spinner) rootView.findViewById(R.id.reportingAreaSelection);
            Button fileButton = (Button) rootView.findViewById(R.id.fileUpload);

            dateButton.setText(savedInstanceState.getCharSequence("date"));
            timeButton.setText(savedInstanceState.getCharSequence("time"));
            reportingAreas.setSelection(savedInstanceState.getInt("selectedArea"));
            fileButton.setText(savedInstanceState.getCharSequence("fileName"));
        }

        return rootView;
    }

    /**
     * Sets the captions of all questionnaire elements, when view is restored.
     * For example after changing the orientation.
     * @param onSavedInstance
     */
    @Override
    public void onActivityCreated(Bundle onSavedInstance) {
        super.onActivityCreated(onSavedInstance);

        if(viewWasAlreadyCreated){
            setAllTextAndReportingAreas();
        }
        viewWasAlreadyCreated = true;

        setListeners(getView());
    }

    /**
     * Identify and execute an action, if a button is clicked.
     * @param v
     */
    @Override
        public void onClick(View v) {
        if (v.getId() == R.id.sendQuestionnaire) {
            new QuestionnaireValidator(getActivity(), base64File, fileName);
        } else if (v.getId() == R.id.dateChoose) {
            DialogFragment newFragment = new DatePickerFragment();
            newFragment.show(getActivity().getFragmentManager(), "datePicker");
        } else if (v.getId() == R.id.timeChoose) {
            DialogFragment newFragment = new TimePickerFragment();
            newFragment.show(getActivity().getFragmentManager(), "timePicker");
        } else if (v.getId() == R.id.fileUpload) {
            startFileChooser();
        } else if(v.getId() == R.id.newQuestionnaire){
            getActivity().getSupportFragmentManager()
                    .beginTransaction().replace(R.id.content_frame,
                    QuestionnaireFragment.newInstance(),
                    QuestionnaireFragment.TAG).commit();
        } else if (v.getId() == R.id.returnToQuestionnaire){
            goBackToQuestionnaire();
        }
    }

    /**
     * Save the selected date, time and reporting area, when destroy view to restore the selections.
     * @param outState
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Button dateButton = (Button) getActivity().findViewById(R.id.dateChoose);
        Button timeButton = (Button) getActivity().findViewById(R.id.timeChoose);
        Spinner reportingAreas = (Spinner) getActivity().findViewById(R.id.reportingAreaSelection);
        Button fileButton = (Button) getActivity().findViewById(R.id.fileUpload);

        outState.putCharSequence("date", dateButton.getText());
        outState.putCharSequence("time", timeButton.getText());
        outState.putInt("selectedArea", reportingAreas.getSelectedItemPosition());
        outState.putCharSequence("fileName", fileButton.getText());
    }

    /**
     * Get the selected file from the file chooser activity and encode it in Base64.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == getActivity().RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        Log.i(TAG, "Uri = " + uri.toString());
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(getActivity().getApplicationContext(), uri);
                            fileName = path.substring(path.lastIndexOf("/") + 1);

                            Button file = (Button) getActivity().findViewById(R.id.fileUpload);
                            file.setText(fileName);

                            FileDecoder fileDecoder = new FileDecoder(path);
                            base64File = fileDecoder.getFile();
                        } catch (Exception e) {
                            Log.e("FileChooser", "File select error", e);
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Start the async tasks to get the captions and reporting area list from the server.
     * Shows progress spinner while downloading. Set text to all elements after successful download.
     */
    private void downloadQuestionnaireSkeletonAndReportingAreas() {
        new GetXmlFromRisikous(){
            @Override
            protected void onPreExecute(){
                getActivity().setProgressBarIndeterminateVisibility(true);
            }

            @Override
            protected void onPostExecute(String result) {
                if (result.equals("error")){
                    showErrorMessage();
                }else{
                    questionnaireSkeleton = result;
                    QuestionnaireSkeletonParser questionnaireParser = new QuestionnaireSkeletonParser(questionnaireSkeleton);
                    new QuestionnaireElementsTextSetter(questionnaireParser, getActivity());
                }

            }
        }.execute("questionnaire");

        new GetXmlFromRisikous(){
            @Override
            protected void onPostExecute(String result) {
                if (result.equals("error")){
                    showErrorMessage();
                }else {
                    reportingAreas = result;
                    ReportingAreasParser reportingAreasParserParser = new ReportingAreasParser(reportingAreas);
                    setReportingAreaSpinner(reportingAreasParserParser);
                }
                getActivity().setProgressBarIndeterminateVisibility(false);
            }
        }.execute("reportingareas");
    }

    /**
     * Sets the captions of the questionnaire and add the reporting area list to the spinner.
     */
    private void setAllTextAndReportingAreas() {
        QuestionnaireSkeletonParser questionnaireParser = new QuestionnaireSkeletonParser(questionnaireSkeleton);
        ReportingAreasParser reportingAreasParserParser = new ReportingAreasParser(reportingAreas);

        new QuestionnaireElementsTextSetter(questionnaireParser, getActivity());
        setReportingAreaSpinner(reportingAreasParserParser);
    }

    /**
     * Sets onclick listeners to all buttons.
     * @param view
     */
    private void setListeners(View view) {
        Button sendQuestionnaire = (Button) view.findViewById(R.id.sendQuestionnaire);
        sendQuestionnaire.setOnClickListener(this);
        Button dateChoose = (Button) view.findViewById(R.id.dateChoose);
        dateChoose.setOnClickListener(this);
        Button timeChoose = (Button) view.findViewById(R.id.timeChoose);
        timeChoose.setOnClickListener(this);
        Button fileUpload = (Button) view.findViewById(R.id.fileUpload);
        fileUpload.setOnClickListener(this);
        Button newQuestionnaire = (Button) view.findViewById(R.id.newQuestionnaire);
        newQuestionnaire.setOnClickListener(this);
        Button tryAgain = (Button) view.findViewById(R.id.returnToQuestionnaire);
        tryAgain.setOnClickListener(this);
    }

    /**
     * Add the reporting area list to the spinner, set onItemListener.
     * @param areas parser for reporting areas
     */
    private void setReportingAreaSpinner(ReportingAreasParser areas) {
        Spinner reportingAreaSpinner = (Spinner) getActivity().findViewById(R.id.reportingAreaSelection);
        final ArrayList<ReportingArea> reportingAreas = areas.getReportingAreas();

        final ArrayAdapter spinnerArrayAdapter = new ReportingAreasSpinnerAdapter(getActivity(),
                android.R.layout.simple_spinner_item, reportingAreas);
        reportingAreaSpinner.setAdapter(spinnerArrayAdapter);

        reportingAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                ReportingArea reportingArea = (ReportingArea) spinnerArrayAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * Starts the file chooser activity.
     */
    private void startFileChooser() {
        FileChooserActivity fileChooserActivity;
        Intent target = FileUtils.createGetContentIntent();

        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            Log.e("Chooser", "FileChooser activity not found");
        }
    }

    /**
     * Change view to a confirmation page, after sending the questionnaire.
     */
    public void setSendView() {
        View questionnaireContentView = getActivity().findViewById(R.id.questionnaireContent);
        questionnaireContentView.setVisibility(View.GONE);

        View sentView = getActivity().findViewById(R.id.sentView);
        sentView.setVisibility(View.VISIBLE);
    }

    /**
     * Shows error message when connection to risikous server timed out.
     */
    public void showErrorMessage(){
        View questionnaireContentView = getActivity().findViewById(R.id.questionnaireContent);
        questionnaireContentView.setVisibility(View.GONE);

        View errorView = getActivity().findViewById(R.id.connectionErrorView);
        errorView.setVisibility(View.VISIBLE);
    }

    /**
     * Reload the questionnaire, if there was a connection error.
     */
    public void goBackToQuestionnaire(){
        downloadQuestionnaireSkeletonAndReportingAreas();

        View questionnaireContentView = getActivity().findViewById(R.id.questionnaireContent);
        questionnaireContentView.setVisibility(View.VISIBLE);

        View errorView = getActivity().findViewById(R.id.connectionErrorView);
        errorView.setVisibility(View.GONE);
    }
}