package de.hszg.risikousapp;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import de.hszg.risikousapp.httpcommandhelper.PostXmlToRisikousServer;
import de.hszg.risikousapp.xmlSerializer.QuestionnaireXmlSerializer;

/**
 * Created by Julian on 17.12.2014.
 */
public class QuestionnaireValidator {

    private FragmentActivity appContext;
    private QuestionnaireXmlSerializer serializer;

    public QuestionnaireValidator(FragmentActivity appContext) {
        this.appContext = appContext;
        checkRequiredFields();
    }

    private void checkRequiredFields(){
        ScrollView questionnaireScroll = (ScrollView) appContext.findViewById(R.id.questionnaireContent);
        Toast validationError = Toast.makeText(appContext, appContext.getString(R.string.fillRequiredFields), Toast.LENGTH_LONG);

        boolean check1 = checkReportingAreaSelection();
        boolean check2 = checkIncidentDescription();
        boolean check3 = checkRiskEstimation();

        if(check1 && check2 && check3){
            String questionnaireXml = "";
            Log.i("validate", "validation success - data ready to send");

            try {
                serializer = new QuestionnaireXmlSerializer(appContext);
                questionnaireXml = serializer.getXmlAsString();
            } catch (IOException e) {
                e.printStackTrace();
            }

            new PostXmlToRisikousServer(appContext){
                @Override
                public void onPostExecute(String result) {
                    Log.i("status", "" + result);
                    setSendView(result);
                }
            }.execute("questionnaire/addQuestionnaire", questionnaireXml);
        }else {
            validationError.show();
            questionnaireScroll.fullScroll(ScrollView.FOCUS_UP);
            Log.e("Validation failed", ""+ check1 + check2 + check3);
        }
    }

    private boolean checkReportingAreaSelection() {
        TextView reportingArea = (TextView) appContext.findViewById(R.id.reportingArea);
        Spinner reportingAreaSelection = (Spinner) appContext.findViewById(R.id.reportingAreaSelection);

        if (reportingAreaSelection.getSelectedItem() != null){
            reportingArea.setTextColor(appContext.getResources().getColor(android.R.color.white));
            return true;
        }else {
            reportingArea.setTextColor(appContext.getResources().getColor(android.R.color.holo_red_dark));
            return false;
        }
    }

    private boolean checkIncidentDescription(){
        TextView incidentDescription = (TextView) appContext.findViewById(R.id.incidentDescription);
        EditText incidentDescriptionEdit = (EditText) appContext.findViewById(R.id.incidentDescriptionEdit);

        if (incidentDescriptionEdit.getText().toString().trim().length() > 0){
            incidentDescription.setTextColor(appContext.getResources().getColor(android.R.color.white));
            return true;
        }else{
            incidentDescription.setTextColor(appContext.getResources().getColor(android.R.color.holo_red_dark));
            return false;
        }
    }

    private boolean checkRiskEstimation(){
        TextView riskEstimation = (TextView) appContext.findViewById(R.id.riskEstimation);
        RadioGroup occurrenceRatingGroup = (RadioGroup) appContext.findViewById(R.id.occurrenceRatingGroup);
        RadioGroup detectionRatingGroup = (RadioGroup) appContext.findViewById(R.id.detectionRatingGroup);
        RadioGroup significanceGroup = (RadioGroup) appContext.findViewById(R.id.significanceGroup);

        int idOccurrence = occurrenceRatingGroup.getCheckedRadioButtonId();
        int idDetection = detectionRatingGroup.getCheckedRadioButtonId();
        int idSignificance = significanceGroup.getCheckedRadioButtonId();

        if (idOccurrence != -1 && idDetection != -1 && idSignificance != -1){
            riskEstimation.setTextColor(appContext.getResources().getColor(android.R.color.white));
            return true;
        }else{
            riskEstimation.setTextColor(appContext.getResources().getColor(android.R.color.holo_red_dark));
            return false;
        }
    }

    private void setSendView(String statusCode){
        if (statusCode.equals("200")){
            QuestionnaireFragment questionnaireFragment = (QuestionnaireFragment) appContext.getSupportFragmentManager().findFragmentById(R.id.content_frame);
            questionnaireFragment.setSendView();
        }
    }

}
