package de.hszg.risikousapp;

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

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import de.hszg.risikousapp.dialogHelper.DatePickerFragment;
import de.hszg.risikousapp.dialogHelper.FileDecoder;
import de.hszg.risikousapp.dialogHelper.TimePickerFragment;
import de.hszg.risikousapp.httpcommandhelper.GetXmlFromRisikous;
import de.hszg.risikousapp.models.ReportingArea;
import de.hszg.risikousapp.xmlParser.QuestionnaireSkeleton;
import de.hszg.risikousapp.xmlParser.ReportingAreas;

/**
 * Created by Julian on 08.12.2014.
 */
public class QuestionnaireFragment extends Fragment implements View.OnClickListener {

    public final static String TAG = QuestionnaireFragment.class.getSimpleName();
    public final static int REQUEST_CODE = 6384;
    public String base64File= "";

    private static final String TAG_FC = "FileChooserActivity";

    private String questionnaireSkeleton = "";
    private String reportingAreas = "";

    private boolean fragmentStartedAlready = false;


    public static QuestionnaireFragment newInstance() {
        return new QuestionnaireFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startAsyncTasks();
        setRetainInstance(true);
    }

    private void startAsyncTasks() {
        new GetXmlFromRisikous(){
            @Override
            protected void onPreExecute(){
                getActivity().setProgressBarIndeterminateVisibility(true);
            }

            @Override
            protected void onPostExecute(String result) {
                questionnaireSkeleton = result;
                QuestionnaireSkeleton questionnaireParser = new QuestionnaireSkeleton(questionnaireSkeleton);
                new QuestionnaireElementsTextSetter(questionnaireParser, getActivity());
            }
        }.execute("questionnaire");

        new GetXmlFromRisikous(){
            @Override
            protected void onPostExecute(String result) {
                reportingAreas = result;
                ReportingAreas reportingAreasParser = new ReportingAreas(reportingAreas);
                setReportingAreaSpinner(reportingAreasParser);
                getActivity().setProgressBarIndeterminateVisibility(false);
            }
        }.execute("reportingareas");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_questionnaire, container, false);

        if(savedInstanceState != null) {
            Button dateButton = (Button) rootView.findViewById(R.id.dateChoose);
            Button timeButton = (Button) rootView.findViewById(R.id.timeChoose);
            Spinner reportingAreas = (Spinner) rootView.findViewById(R.id.reportingAreaSelection);

            dateButton.setText(savedInstanceState.getCharSequence("date"));
            timeButton.setText(savedInstanceState.getCharSequence("time"));
            reportingAreas.setSelection(savedInstanceState.getInt("selectedArea"));
        }

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle onSavedInstance) {
        super.onActivityCreated(onSavedInstance);

        if(fragmentStartedAlready){
            setAllTextAndReportingAreas();
        }
        fragmentStartedAlready = true;
        setListeners(getView());
    }

    private void setAllTextAndReportingAreas() {
        QuestionnaireSkeleton questionnaireParser = new QuestionnaireSkeleton(questionnaireSkeleton);
        ReportingAreas reportingAreasParser = new ReportingAreas(reportingAreas);

        new QuestionnaireElementsTextSetter(questionnaireParser, getActivity());
        setReportingAreaSpinner(reportingAreasParser);
    }

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
    }

    private void setReportingAreaSpinner(ReportingAreas areas) {
        Spinner reportingAreaSpinner = (Spinner) getActivity().findViewById(R.id.reportingAreaSelection);
        final ArrayList<ReportingArea> reportingAreas = areas.getReportingAreas();

        final ArrayAdapter spinnerArrayAdapter = new ReportingAreasSpinAdapter(getActivity(),
                android.R.layout.simple_spinner_item, reportingAreas);
        reportingAreaSpinner.setAdapter(spinnerArrayAdapter);

        reportingAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                ReportingArea reportingArea = (ReportingArea) spinnerArrayAdapter.getItem(position);
                Log.i("Reporting Area", "Ausgewählter Meldekreis: " + reportingArea.getName() +
                        " Shortcut: " + reportingArea.getShortcut());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
        public void onClick(View v) {
        if (v.getId() == R.id.sendQuestionnaire) {
            new QuestionnaireValidator(getActivity());
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
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Button dateButton = (Button) getActivity().findViewById(R.id.dateChoose);
        Button timeButton = (Button) getActivity().findViewById(R.id.timeChoose);
        Spinner reportingAreas = (Spinner) getActivity().findViewById(R.id.reportingAreaSelection);

        outState.putCharSequence("date", dateButton.getText());
        outState.putCharSequence("time", timeButton.getText());
        outState.putInt("selectedArea", reportingAreas.getSelectedItemPosition());
    }

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
                            final String fileName = path.substring(path.lastIndexOf("/") + 1);

                            Button file = (Button) getActivity().findViewById(R.id.fileUpload);
                            file.setText(path);
                            Toast.makeText(getActivity().getApplicationContext(), "Ausgewählte Datei: " + fileName, Toast.LENGTH_LONG).show();
                            FileDecoder fileDecoder = new FileDecoder(fileName);
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

    public void setSendView() {
        View questionnaireContentView = getActivity().findViewById(R.id.questionnaireContent);
        questionnaireContentView.setVisibility(View.GONE);

        View sentView = getActivity().findViewById(R.id.sentView);
        sentView.setVisibility(View.VISIBLE);
    }
}


