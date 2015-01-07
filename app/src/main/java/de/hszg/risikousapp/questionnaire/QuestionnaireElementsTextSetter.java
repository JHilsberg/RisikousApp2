package de.hszg.risikousapp.questionnaire;

import android.app.Activity;
import android.text.InputFilter;
import android.widget.EditText;
import android.widget.TextView;

import de.hszg.risikousapp.R;

/**
 * Created by Julian on 17.12.2014.
 */
public class QuestionnaireElementsTextSetter {

    private Activity appContext;
    private String maxCharsLabel;
    private QuestionnaireSkeletonParser questionnaire;

    public QuestionnaireElementsTextSetter(QuestionnaireSkeletonParser questionnaire, Activity context){
        this.appContext = context;
        this.questionnaire = questionnaire;
        maxCharsLabel = appContext.getString(R.string.maximumChars);

        setReportingAreaText();
        setIncidentDescription();
        setRiskEstimitation();
        setPointOfTime();
        setLocation();
        setImmediateMeasure();
        setConsequences();
        setOpinionOfReporter();
        setUploadFileText();
        setContactInformation();
    }

    private void setReportingAreaText() {
        TextView reportingArea = (TextView) appContext.findViewById(R.id.reportingArea);

        reportingArea.setText(questionnaire.getQuestionCaption(appContext.getString(R.string.reportingArea)));
        addRequiredStatus(reportingArea);
    }

    private void setIncidentDescription() {
        String incidentDescriptionString = appContext.getString(R.string.incidentDescription);
        TextView incidentDescription = (TextView) appContext.findViewById(R.id.incidentDescription);
        EditText incidentDescriptionEdit = (EditText) appContext.findViewById(R.id.incidentDescriptionEdit);

        incidentDescription.setText(questionnaire.getQuestionCaption(incidentDescriptionString));
        addRequiredStatus(incidentDescription);
        incidentDescriptionEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(incidentDescriptionString));
        incidentDescriptionEdit.setFilters(getMaxCharsFilter(incidentDescriptionString));
    }

    private void setRiskEstimitation(){
        TextView riskEstimation = (TextView) appContext.findViewById(R.id.riskEstimation);
        TextView occuranceRating = (TextView) appContext.findViewById(R.id.occurrenceRating);
        TextView detectionRating = (TextView) appContext.findViewById(R.id.detectionRating);
        TextView significance = (TextView) appContext.findViewById(R.id.significance);

        riskEstimation.setText(questionnaire.getQuestionCaption(appContext.getString(R.string.riskEstimation)));
        addRequiredStatus(riskEstimation);
        occuranceRating.setText(questionnaire.getQuestionCaption(appContext.getString(R.string.occurrenceRating)));
        detectionRating.setText(questionnaire.getQuestionCaption(appContext.getString(R.string.detectionRating)));
        significance.setText(questionnaire.getQuestionCaption(appContext.getString(R.string.significance)));
    }

    private void setPointOfTime(){
        TextView pointOfTime = (TextView) appContext.findViewById(R.id.pointOfTime);

        pointOfTime.setText(questionnaire.getQuestionCaption(appContext.getString(R.string.pointOfTime)));
    }

    private void setLocation(){
        String locationString = appContext.getString(R.string.location);
        TextView location = (TextView) appContext.findViewById(R.id.location);
        EditText locationEdit = (EditText) appContext.findViewById(R.id.locationEdit);

        location.setText(questionnaire.getQuestionCaption(locationString));
        locationEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(locationString));
        locationEdit.setFilters(getMaxCharsFilter(locationString));
    }

    private void setImmediateMeasure(){
        String immediateMeasureString = appContext.getString(R.string.immediateMeasure);
        TextView immediateMeasure = (TextView) appContext.findViewById(R.id.immediateMeasure);
        EditText immediateMeasureEdit = (EditText) appContext.findViewById(R.id.immediateMeasureEdit);


        immediateMeasure.setText(questionnaire.getQuestionCaption(immediateMeasureString));
        immediateMeasureEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(immediateMeasureString));
        immediateMeasureEdit.setFilters(getMaxCharsFilter(immediateMeasureString));
    }

    private void setConsequences (){
        String consequencesString = appContext.getString(R.string.consequences);
        TextView consequences = (TextView) appContext.findViewById(R.id.consequences);
        EditText consequencesEdit = (EditText) appContext.findViewById(R.id.consequencesEdit);

        consequences.setText(questionnaire.getQuestionCaption(consequencesString));
        consequencesEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(consequencesString));
        consequencesEdit.setFilters(getMaxCharsFilter(consequencesString));
    }

    private void setOpinionOfReporter (){
        TextView opinionOfReporter = (TextView) appContext.findViewById(R.id.opinionOfReporter);

        String personalFactorsString = appContext.getString(R.string.personalFactors);
        TextView personalFactors = (TextView) appContext.findViewById(R.id.personalFactors);
        EditText personalFactorsEdit = (EditText) appContext.findViewById(R.id.personalFactorsEdit);

        String organisationalFactorsString = appContext.getString(R.string.organisationalFactors);
        TextView organisationalFactors = (TextView) appContext.findViewById(R.id.organisationalFactors);
        EditText organisationalFactorsEdit = (EditText) appContext.findViewById(R.id.organisationalFactorsEdit);

        String additionalNotesString = appContext.getString(R.string.additionalNotes);
        TextView additionalNotes = (TextView) appContext.findViewById(R.id.additionalNotes);
        EditText additionalNotesEdit = (EditText) appContext.findViewById(R.id.additionalNotesEdit);

        opinionOfReporter.setText(questionnaire.getQuestionCaption(appContext.getString(R.string.opinionOfReporter)));

        personalFactors.setText(questionnaire.getQuestionCaption(personalFactorsString));
        personalFactorsEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(personalFactorsString));
        personalFactorsEdit.setFilters(getMaxCharsFilter(personalFactorsString));

        organisationalFactors.setText(questionnaire.getQuestionCaption(organisationalFactorsString));
        organisationalFactorsEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(organisationalFactorsString));
        organisationalFactorsEdit.setFilters(getMaxCharsFilter(organisationalFactorsString));

        additionalNotes.setText(questionnaire.getQuestionCaption(additionalNotesString));
        additionalNotesEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(additionalNotesString));
        additionalNotes.setFilters(getMaxCharsFilter(additionalNotesString));
    }

    private void setUploadFileText (){
        TextView uploadFileCaption = (TextView) appContext.findViewById(R.id.filesCaption);

        uploadFileCaption.setText(questionnaire.getQuestionCaption(appContext.getString(R.string.files)));
    }

    private void setContactInformation (){
        String contactInformationString = appContext.getString(R.string.contactInformation);
        TextView contactInformation = (TextView) appContext.findViewById(R.id.contactInformation);
        EditText contactInformationEdit = (EditText) appContext.findViewById(R.id.contactInformationEdit);

        contactInformation.setText(questionnaire.getQuestionCaption(contactInformationString));
        contactInformationEdit.setHint(maxCharsLabel + questionnaire.getAnswerMaxChars(contactInformationString));
        contactInformationEdit.setFilters(getMaxCharsFilter(contactInformationString));
    }

    private void addRequiredStatus(TextView textView) {
        if(questionnaire.getRequiredStatus(appContext.getString(R.string.reportingArea))){
            textView.append("*");
        }
    }

    private InputFilter[] getMaxCharsFilter(String element){
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(questionnaire.getAnswerMaxChars(element));
        return filterArray;
    }
}
