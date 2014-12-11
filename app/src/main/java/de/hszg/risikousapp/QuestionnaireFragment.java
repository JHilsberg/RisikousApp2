package de.hszg.risikousapp;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import de.hszg.risikousapp.httpHelper.GetXmlFromRisikous;
import de.hszg.risikousapp.xmlParser.QuestionnaireSkeletonParser;

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
    }

    public void setTextToAllElements(QuestionnaireSkeletonParser questionnaire) {
        setReportingArea(questionnaire);
        setIncidentDescription(questionnaire);
    }

    public void setReportingArea(QuestionnaireSkeletonParser questionnaire) {
        TextView reportingText = (TextView) getActivity().findViewById(R.id.reportingArea);

        reportingText.setText(questionnaire.getQuestionCaption(getResources().getString(R.string.reportingArea)));
    }

    public void setIncidentDescription(QuestionnaireSkeletonParser questionnaire) {
        String incidenDescription = getResources().getString(R.string.incidentDescription);
        TextView incidentDescriptionCaption = (TextView) getActivity().findViewById(R.id.incidentDescription);
        EditText incidentDescriptionEdit = (EditText) getActivity().findViewById(R.id.incidentDescriptionEdit);

        incidentDescriptionCaption.setText(questionnaire.getQuestionCaption(incidenDescription));
        incidentDescriptionEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(incidenDescription));
    }

}