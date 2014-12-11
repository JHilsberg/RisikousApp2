package de.hszg.risikousapp;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import de.hszg.risikousapp.httpcommandhelper.GetXmlFromRisikous;
import de.hszg.risikousapp.xmlParser.QuestionnaireSkeletonParser;
import de.hszg.risikousapp.xmlParser.ReportingAreasParser;

/**
 * Created by Julian on 08.12.2014.
 */
public class QuestionnaireFragment extends Fragment {

    public final static String TAG = QuestionnaireFragment.class.getSimpleName();

    private String maxCharsLabel;

    public QuestionnaireFragment() {
    }

    public static QuestionnaireFragment newInstance() {
        return new QuestionnaireFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_questionnaire, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle onSavedInstance) {
        super.onActivityCreated(onSavedInstance);

        maxCharsLabel = getActivity().getResources().getString(R.string.maximumChars);

        new GetXmlFromRisikous(getActivity()) {
            @Override
            public void onPostExecute(String result) {
                QuestionnaireSkeletonParser parser = new QuestionnaireSkeletonParser(result);
                setTextToAllElements(parser);
            }
        }.execute("questionnaire");

        new GetXmlFromRisikous(getActivity()) {
            @Override
            public void onPostExecute(String result) {
                ReportingAreasParser parser = new ReportingAreasParser(result);
                setReportingAreaSpinner(parser);
            }
        }.execute("reportingareas");
    }

    private void setReportingAreaSpinner(ReportingAreasParser areas) {
        Spinner reportingAreaSpinner = (Spinner) getActivity().findViewById(R.id.reportingAreaSelection);
        ArrayList<String> reportingAreasNames = areas.getReportingAreasNames();

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                reportingAreasNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reportingAreaSpinner.setAdapter(spinnerArrayAdapter);
    }

    private void setTextToAllElements(QuestionnaireSkeletonParser questionnaire) {
        setReportingAreaText(questionnaire);
        setIncidentDescription(questionnaire);
    }

    private void setReportingAreaText(QuestionnaireSkeletonParser questionnaire) {
        TextView reportingAreaText = (TextView) getActivity().findViewById(R.id.reportingArea);

        reportingAreaText.setText(questionnaire.getQuestionCaption(getResources().getString(R.string.reportingArea)));
    }

    private void setIncidentDescription(QuestionnaireSkeletonParser questionnaire) {
        String incidenDescription = getResources().getString(R.string.incidentDescription);
        TextView incidentDescriptionCaption = (TextView) getActivity().findViewById(R.id.incidentDescription);
        EditText incidentDescriptionEdit = (EditText) getActivity().findViewById(R.id.incidentDescriptionEdit);

        incidentDescriptionCaption.setText(questionnaire.getQuestionCaption(incidenDescription));
        incidentDescriptionEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(incidenDescription));
    }

}