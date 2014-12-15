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

import org.w3c.dom.Text;

import java.util.ArrayList;
import de.hszg.risikousapp.httpcommandhelper.GetXmlFromRisikous;
import de.hszg.risikousapp.xmlParser.QuestionnaireSkeleton;
import de.hszg.risikousapp.xmlParser.ReportingAreas;

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

        maxCharsLabel = getString(R.string.maximumChars);

        new GetXmlFromRisikous(getActivity()) {
            @Override
            public void onPostExecute(String result) {
                QuestionnaireSkeleton parser = new QuestionnaireSkeleton(result);
                setTextToAllQuestionnaireElements(parser);
            }
        }.execute("questionnaire");

        new GetXmlFromRisikous(getActivity()) {
            @Override
            public void onPostExecute(String result) {
                ReportingAreas parser = new ReportingAreas(result);
                setReportingAreaSpinner(parser);
            }
        }.execute("reportingareas");
    }

    private void setTextToAllQuestionnaireElements(QuestionnaireSkeleton questionnaire) {
        setReportingAreaText(questionnaire);
        setIncidentDescription(questionnaire);
        setRiskEstimitation(questionnaire);
    }

    private void setReportingAreaSpinner(ReportingAreas areas) {
        Spinner reportingAreaSpinner = (Spinner) getActivity().findViewById(R.id.reportingAreaSelection);
        ArrayList<String> reportingAreasNames = areas.getReportingAreasNames();

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                reportingAreasNames);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        reportingAreaSpinner.setAdapter(spinnerArrayAdapter);
    }


    private void setReportingAreaText(QuestionnaireSkeleton questionnaire) {
        TextView reportingAreaText = (TextView) getActivity().findViewById(R.id.reportingArea);

        reportingAreaText.setText(questionnaire.getQuestionCaption(getString(R.string.reportingArea)));
    }

    private void setIncidentDescription(QuestionnaireSkeleton questionnaire) {
        String incidentDescription = getString(R.string.incidentDescription);
        TextView incidentDescriptionCaption = (TextView) getActivity().findViewById(R.id.incidentDescription);
        EditText incidentDescriptionEdit = (EditText) getActivity().findViewById(R.id.incidentDescriptionEdit);

        incidentDescriptionCaption.setText(questionnaire.getQuestionCaption(incidentDescription));
        incidentDescriptionEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(incidentDescription));
    }

    private void setRiskEstimitation(QuestionnaireSkeleton questionnaire){
        TextView riskEstimation = (TextView) getActivity().findViewById(R.id.riskEstimation);
        TextView occuranceRating = (TextView) getActivity().findViewById(R.id.occurenceRating);
        TextView detectionRating = (TextView) getActivity().findViewById(R.id.detectionRating);
        TextView significance = (TextView) getActivity().findViewById(R.id.significance);

        riskEstimation.setText(questionnaire.getQuestionCaption(getString(R.string.riskEstimation)));
        occuranceRating.setText(questionnaire.getQuestionCaption(getString(R.string.occurrenceRating)));
        detectionRating.setText(questionnaire.getQuestionCaption(getString(R.string.detectionRating)));
        significance.setText(questionnaire.getQuestionCaption(getString(R.string.significance)));
    }

    private void setPointOfTime(QuestionnaireSkeleton questionnaire){
        //TODO
        TextView date = (TextView) getActivity().findViewById(R.id.dateEdit);
        TextView time = (TextView) getActivity().findViewById(R.id.timeEdit);

    }

    private void setLocation(QuestionnaireSkeleton questionnaire){
        //TODO
    }

    private void setImmediateMeasure(QuestionnaireSkeleton questionnaire){
        //TODO
    }

    private void setConsequences (QuestionnaireSkeleton questionnaire){
        //TODO
    }

    private void setOpinionOfReporter (QuestionnaireSkeleton questionnaire){
        //TODO
    }

    private void setUploadFileText (QuestionnaireSkeleton questionnaire){
        //TODO
    }

    private void setContactInformation (QuestionnaireSkeleton questionnaire){
        //TODO
    }


}