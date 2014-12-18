package de.hszg.risikousapp.xmlSerializer;

import android.app.Activity;
import android.util.Xml;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hszg.risikousapp.R;
import de.hszg.risikousapp.models.ReportingArea;


/**
 * Created by Julian on 17.12.2014.
 */

public class QuestionnaireXmlSerializer {

    private Activity appContext;
    private ByteArrayOutputStream xmlData;
    private String xmlAsString;
    private XmlSerializer serializer;

    public QuestionnaireXmlSerializer(Activity appContext) throws IOException {
        this.appContext = appContext;
        this.xmlData = new ByteArrayOutputStream();
        this.serializer = Xml.newSerializer();

        serializer.setOutput(xmlData, "UTF-8");
        serializer.startDocument(null, true);
        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        serializer.startTag(null, "questionnaire");

        addReportingArea();
        addIncidentDescription();
        addRiskEstimation();

        finishDocument();
    }

    public String getXmlAsString(){
        xmlAsString = xmlData.toString();
        return xmlAsString;
    }

    private void addReportingArea() throws IOException {
        Spinner reportingAreasSelection = (Spinner) appContext.findViewById(R.id.reportingAreaSelection);
        ReportingArea reportingArea= (ReportingArea) reportingAreasSelection.getAdapter().getItem(reportingAreasSelection.getSelectedItemPosition());

        makeTag(appContext.getString(R.string.reportingArea), reportingArea.getShortcut());
    }

    private void addIncidentDescription() throws IOException{
        EditText incidentDescriptionEdit = (EditText) appContext.findViewById(R.id.incidentDescriptionEdit);
        makeTag(appContext.getString(R.string.incidentDescription), incidentDescriptionEdit.getText().toString());
    }

    private void addRiskEstimation() throws IOException{
        serializer.startTag(null, appContext.getString(R.string.riskEstimation));
        addOccurrenceRating();
        addDetectionRating();
        addSignificance();
        serializer.endTag(null, appContext.getString(R.string.riskEstimation));
    }

    private void addOccurrenceRating() throws IOException{
        RadioGroup occurrenceRatingGroup = (RadioGroup) appContext.findViewById(R.id.occurrenceRatingGroup);

        switch(occurrenceRatingGroup.getCheckedRadioButtonId()){
            case R.id.occurrenceRatingLow:
                makeTag(appContext.getString(R.string.occurrenceRating), "1");
                break;
            case R.id.occurrenceRatingMid:
                makeTag(appContext.getString(R.string.occurrenceRating), "2");
                break;
            case R.id.occurrenceRatingHigh:
                makeTag(appContext.getString(R.string.occurrenceRating), "3");
                break;
        }
    }

    private void addDetectionRating() throws IOException{
        RadioGroup detectionRatingGroup = (RadioGroup) appContext.findViewById(R.id.detectionRatingGroup);

        switch(detectionRatingGroup.getCheckedRadioButtonId()){
            case R.id.detectionRatingLow:
                makeTag(appContext.getString(R.string.detectionRating), "1");
                break;
            case R.id.detectionRatingMid:
                makeTag(appContext.getString(R.string.detectionRating), "2");
                break;
            case R.id.detectionRatingHigh:
                makeTag(appContext.getString(R.string.detectionRating), "3");
                break;
        }
    }

    private void addSignificance() throws IOException{
        RadioGroup significanceGroup = (RadioGroup) appContext.findViewById(R.id.significanceGroup);

        switch(significanceGroup.getCheckedRadioButtonId()){
            case R.id.significanceLow:
                makeTag(appContext.getString(R.string.significance), "1");
                break;
            case R.id.significanceMid:
                makeTag(appContext.getString(R.string.significance), "2");
                break;
            case R.id.significanceHigh:
                makeTag(appContext.getString(R.string.significance), "3");
                break;
        }
    }

    private void makeTag(String tagName, String text) throws IOException{
        serializer.startTag(null, tagName);
        serializer.text(text);
        serializer.endTag(null, tagName);
    }

    private void finishDocument() throws IOException{
        serializer.endDocument();
        serializer.flush();
        xmlData.close();
    }
}
