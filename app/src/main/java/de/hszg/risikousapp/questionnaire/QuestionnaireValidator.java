package de.hszg.risikousapp.questionnaire;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import de.hszg.risikousapp.R;
import de.hszg.risikousapp.httpHelper.PostXmlToRisikous;

/**
 * Class to validate the required fields in the questionnaire.
 * If validation is successful report is send to risikous server.
 */
public class QuestionnaireValidator {

    private FragmentActivity appContext;
    private String file;
    private String fileName;
    private QuestionnaireXmlSerializer serializer;

    /**
     * Constructor, starts the validation.
     * @param appContext
     * @param file
     * @param fileName
     */
    public QuestionnaireValidator(FragmentActivity appContext, String file, String fileName) {
        this.appContext = appContext;
        this.file = file;
        this.fileName = fileName;
        checkRequiredFields();
    }

    /**
     * Checks if all required fields have some input
     * In success case method starts XML-serializer and send report to server.
     */
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
                serializer = new QuestionnaireXmlSerializer(appContext, file, fileName);
                questionnaireXml = serializer.getXmlAsString();
            } catch (IOException e) {
                Log.e("serializer", "Fehler bei der Serialisierung");
            }

            new PostXmlToRisikous(){
                @Override
                protected void onPreExecute(){
                    appContext.setProgressBarIndeterminateVisibility(true);
                }

                @Override
                protected void onPostExecute(String result) {
                    Log.i("status", "" + result);
                    appContext.setProgressBarIndeterminateVisibility(false);
                    setSendView(result);
                }
            }.execute("questionnaire/addQuestionnaire", questionnaireXml);
        }else {
            validationError.show();
            questionnaireScroll.fullScroll(ScrollView.FOCUS_UP);
            Log.e("Validation failed", ""+ check1 + check2 + check3);
        }
    }

    /**
     * Method check if a reporting area is selected.
     * @return true if a area is selected, else false
     */
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

    /**
     * Method check if a incident description is given by the user.
     * @return true if a description is given by the user, else false
     */
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

    /**
     * Method check if all required risk estimation selections are selected by the user.
     * @return true if a selection in each selection-group is given by the user, else false
     */
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

    /**
     * Change the questionnaire view to a send confirmation view.
     * @param statusCode
     */
    private void setSendView(String statusCode){
        if (statusCode.equals("200")){
            QuestionnaireFragment questionnaireFragment = (QuestionnaireFragment) appContext.getSupportFragmentManager().findFragmentById(R.id.content_frame);
            questionnaireFragment.setSendView();
        }
    }

}
