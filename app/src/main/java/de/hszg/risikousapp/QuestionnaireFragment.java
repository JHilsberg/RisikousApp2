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
import de.hszg.risikousapp.xmlParser.QuestionnaireSkeleton;
import de.hszg.risikousapp.xmlParser.ReportingAreas;

/**
 * Created by Julian on 08.12.2014.
 */
public class QuestionnaireFragment extends Fragment {

    public final static String TAG = QuestionnaireFragment.class.getSimpleName();

    private String maxCharsLabel;
    private View questionnaireContentView;
    private View loadingView;

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

        questionnaireContentView = getView().findViewById(R.id.questionnaireContent);
        loadingView = getView().findViewById(R.id.loading_spinner);

        questionnaireContentView.setVisibility(View.GONE);

        new GetXmlFromRisikous(getActivity()) {
            @Override
            public void onPostExecute(String result) {
                QuestionnaireSkeleton parser = new QuestionnaireSkeleton(result);
                setTextToAllQuestionnaireElements(parser);

                loadingView.setVisibility(View.GONE);
                questionnaireContentView.setVisibility(View.VISIBLE);
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
        setPointOfTime(questionnaire);
        setLocation(questionnaire);
        setImmediateMeasure(questionnaire);
        setConsequences(questionnaire);
        setOpinionOfReporter(questionnaire);
        setUploadFileText(questionnaire);
        setContactInformation(questionnaire);
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
        TextView pointOfTime = (TextView) getActivity().findViewById(R.id.pointOfTime);

        TextView date = (TextView) getActivity().findViewById(R.id.date);
        TextView time = (TextView) getActivity().findViewById(R.id.time);

        pointOfTime.setText(questionnaire.getQuestionCaption(getString(R.string.pointOfTime)));
        date.setText(questionnaire.getQuestionCaption(getString(R.string.date)));
        time.setText(questionnaire.getQuestionCaption(getString(R.string.time)));
    }

    private void setLocation(QuestionnaireSkeleton questionnaire){
        TextView location = (TextView) getActivity().findViewById(R.id.location);
        EditText locationEdit = (EditText) getActivity().findViewById(R.id.locationEdit);

        location.setText(questionnaire.getQuestionCaption(getString(R.string.location)));
        locationEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(getString(R.string.location)));
    }

    private void setImmediateMeasure(QuestionnaireSkeleton questionnaire){
        TextView immediateMeasure = (TextView) getActivity().findViewById(R.id.immediateMeasure);
        EditText immediateMeasureEdit = (EditText) getActivity().findViewById(R.id.immediateMeasureEdit);

        immediateMeasure.setText(questionnaire.getQuestionCaption(getString(R.string.immediateMeasure)));
        immediateMeasureEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(getString(R.string.immediateMeasure)));
    }

    private void setConsequences (QuestionnaireSkeleton questionnaire){
        TextView consequences = (TextView) getActivity().findViewById(R.id.consequences);
        EditText consequencesEdit = (EditText) getActivity().findViewById(R.id.consequencesEdit);

        consequences.setText(questionnaire.getQuestionCaption(getString(R.string.consequences)));
        consequencesEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(getString(R.string.consequences)));
    }

    private void setOpinionOfReporter (QuestionnaireSkeleton questionnaire){
        TextView opinionOfReporter = (TextView) getActivity().findViewById(R.id.opinionOfReporter);

        TextView personalFactors = (TextView) getActivity().findViewById(R.id.personalFactors);
        EditText personalFactorsEdit = (EditText) getActivity().findViewById(R.id.personalFactorsEdit);

        TextView organisationalFactors = (TextView) getActivity().findViewById(R.id.organisationalFactors);
        EditText organisationalFactorsEdit = (EditText) getActivity().findViewById(R.id.personalFactorsEdit);

        TextView additionalNotes = (TextView) getActivity().findViewById(R.id.additionalNotes);
        EditText additionalNotesEdit = (EditText) getActivity().findViewById(R.id.additionalNotesEdit);

        opinionOfReporter.setText(questionnaire.getQuestionCaption(getString(R.string.opinionOfReporter)));

        personalFactors.setText(questionnaire.getQuestionCaption(getString(R.string.personalFactors)));
        personalFactorsEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(getString(R.string.personalFactors)));

        organisationalFactors.setText(questionnaire.getQuestionCaption(getString(R.string.organisationalFactors)));
        organisationalFactorsEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(getString(R.string.organisationalFactors)));

        additionalNotes.setText(questionnaire.getQuestionCaption(getString(R.string.additionalNotes)));
        additionalNotesEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(getString(R.string.additionalNotes)));
    }

    private void setUploadFileText (QuestionnaireSkeleton questionnaire){
        TextView uploadFileCaption = (TextView) getActivity().findViewById(R.id.fileUploadCaption);

        uploadFileCaption.setText(questionnaire.getQuestionCaption(getString(R.string.files)));
    }

    private void setContactInformation (QuestionnaireSkeleton questionnaire){
        TextView contactInformation = (TextView) getActivity().findViewById(R.id.contactInformation);
        EditText contactInformationEdit = (EditText) getActivity().findViewById(R.id.contactInformationEdit);

        contactInformation.setText(questionnaire.getQuestionCaption(getString(R.string.contactInformation)));
        contactInformationEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(getString(R.string.contactInformation)));
    }
}