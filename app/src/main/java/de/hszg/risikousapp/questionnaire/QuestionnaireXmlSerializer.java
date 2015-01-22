package de.hszg.risikousapp.questionnaire;

import android.app.Activity;
import android.util.Log;
import android.util.Xml;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hszg.risikousapp.R;
import de.hszg.risikousapp.questionnaire.reportingArea.ReportingArea;


/**
 * Creates an XML-Message with all elements the user put in the questionnaire.
 */
public class QuestionnaireXmlSerializer {

    private Activity appContext;
    private ByteArrayOutputStream xmlData;
    private XmlSerializer serializer;
    private String file;
    private String fileName;

    /**
     * Constructor, instantiates ByteArrayOutputStream and XmlSerializer
     * Start and finish XML-Document, start all add methods.
     * @param appContext
     * @param file
     * @param fileName
     * @throws IOException
     */
    public QuestionnaireXmlSerializer(Activity appContext, String file, String fileName) throws IOException {
        this.appContext = appContext;
        this.xmlData = new ByteArrayOutputStream();
        this.serializer = Xml.newSerializer();
        this.file = file;
        this.fileName = fileName;

        serializer.setOutput(xmlData, "UTF-8");
        serializer.startDocument(null, true);
        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        serializer.startTag(null, "questionnaire");

        addReportingArea();
        addIncidentDescription();
        addRiskEstimation();
        addPointOfTime();
        addLocation();
        addImmediateMeasure();
        addConsequences();
        addOpinionOfReporter();
        addFile();
        addContactInfo();

        finishDocument();
    }

    /**
     * Get XML data as String
     * @return string with xml message
     */
    public String getXmlAsString(){
        String xmlAsString = xmlData.toString();
        Log.i("Meldung", xmlAsString);
        return xmlAsString;
    }

    /**
     * Adds reporting area to XML message.
     * @throws IOException
     */
    private void addReportingArea() throws IOException {
        Spinner reportingAreasSelection = (Spinner) appContext.findViewById(R.id.reportingAreaSelection);
        ReportingArea reportingArea= (ReportingArea) reportingAreasSelection.getAdapter().getItem(reportingAreasSelection.getSelectedItemPosition());

        makeNode(appContext.getString(R.string.reportingArea), reportingArea.getShortcut());
    }

    /**
     * Adds incident description to XML message.
     * @throws IOException
     */
    private void addIncidentDescription() throws IOException{
        EditText incidentDescriptionEdit = (EditText) appContext.findViewById(R.id.incidentDescriptionEdit);
        makeNode(appContext.getString(R.string.incidentDescription), incidentDescriptionEdit.getText().toString());
    }

    /**
     * Adds risk estimation start- and end-tag to XML message.
     * @throws IOException
     */
    private void addRiskEstimation() throws IOException{
        serializer.startTag(null, appContext.getString(R.string.riskEstimation));
        addOccurrenceRating();
        addDetectionRating();
        addSignificance();
        serializer.endTag(null, appContext.getString(R.string.riskEstimation));
    }

    /**
     * Adds occurrence rating selection to XML message.
     * @throws IOException
     */
    private void addOccurrenceRating() throws IOException{
        RadioGroup occurrenceRatingGroup = (RadioGroup) appContext.findViewById(R.id.occurrenceRatingGroup);

        switch(occurrenceRatingGroup.getCheckedRadioButtonId()){
            case R.id.occurrenceRatingLow:
                makeNode(appContext.getString(R.string.occurrenceRating), "1");
                break;
            case R.id.occurrenceRatingMid:
                makeNode(appContext.getString(R.string.occurrenceRating), "2");
                break;
            case R.id.occurrenceRatingHigh:
                makeNode(appContext.getString(R.string.occurrenceRating), "3");
                break;
        }
    }

    /**
     * Adds detection rating selection to XML message.
     * @throws IOException
     */
    private void addDetectionRating() throws IOException{
        RadioGroup detectionRatingGroup = (RadioGroup) appContext.findViewById(R.id.detectionRatingGroup);

        switch(detectionRatingGroup.getCheckedRadioButtonId()){
            case R.id.detectionRatingLow:
                makeNode(appContext.getString(R.string.detectionRating), "1");
                break;
            case R.id.detectionRatingMid:
                makeNode(appContext.getString(R.string.detectionRating), "2");
                break;
            case R.id.detectionRatingHigh:
                makeNode(appContext.getString(R.string.detectionRating), "3");
                break;
        }
    }
    /**
     * Adds significance selection to XML message.
     * @throws IOException
     */
    private void addSignificance() throws IOException{
        RadioGroup significanceGroup = (RadioGroup) appContext.findViewById(R.id.significanceGroup);

        switch(significanceGroup.getCheckedRadioButtonId()){
            case R.id.significanceLow:
                makeNode(appContext.getString(R.string.significance), "1");
                break;
            case R.id.significanceMid:
                makeNode(appContext.getString(R.string.significance), "2");
                break;
            case R.id.significanceHigh:
                makeNode(appContext.getString(R.string.significance), "3");
                break;
        }
    }

    /**
     * Adds point of time (date and time) to XML message, if selected by the user.
     * @throws IOException
     */
    private void addPointOfTime() throws IOException{
        String pointOfTime = appContext.getString(R.string.pointOfTime);

        Button date = (Button) appContext.findViewById(R.id.dateChoose);
        Button time = (Button) appContext.findViewById(R.id.timeChoose);

        if(checkIfDateIsSet(date) && checkIfTimeIsSet(time)) {
            serializer.startTag(null, pointOfTime);
            makeNode(appContext.getString(R.string.date), date.getText().toString());
            makeNode(appContext.getString(R.string.time), time.getText().toString());
            serializer.endTag(null, pointOfTime);
        }else if(checkIfDateIsSet(date) && !checkIfTimeIsSet(time)){
            serializer.startTag(null, pointOfTime);
            makeNode(appContext.getString(R.string.date), date.getText().toString());
            serializer.endTag(null, pointOfTime);
        }else if(!checkIfDateIsSet(date) && checkIfTimeIsSet(time)){
            serializer.startTag(null, pointOfTime);
            makeNode(appContext.getString(R.string.time), time.getText().toString());
            serializer.endTag(null, pointOfTime);
        }

    }

    /**
     * Adds location to XML message, if given by the user.
     * @throws IOException
     */
    private void addLocation() throws IOException{
        EditText location = (EditText) appContext.findViewById(R.id.locationEdit);

        if (checkIfFieldEdited(location)){
            makeNode(appContext.getString(R.string.location), location.getText().toString());
        }
    }

    /**
     * Adds immediate measure to XML message, if given by the user.
     * @throws IOException
     */
    private void addImmediateMeasure() throws IOException{
        EditText immediateMeasure = (EditText) appContext.findViewById(R.id.immediateMeasureEdit);

        if(checkIfFieldEdited(immediateMeasure)) {
            makeNode(appContext.getString(R.string.immediateMeasure), immediateMeasure.getText().toString());
        }
    }

    /**
     * Adds consequences to XML message, if given by the user.
     * @throws IOException
     */
    private void addConsequences() throws IOException{
        EditText consequences = (EditText) appContext.findViewById(R.id.consequencesEdit);

        if(checkIfFieldEdited(consequences)){
            makeNode(appContext.getString(R.string.consequences), consequences.getText().toString());
        }
    }

    /**
     * Adds opinion of reporter (personal factors, organizational factors, additional notes) to XML message, if given by the user.
     * @throws IOException
     */
    private void addOpinionOfReporter() throws IOException{
        String opinion = appContext.getString(R.string.opinionOfReporter);

        EditText personalFactors = (EditText) appContext.findViewById(R.id.personalFactorsEdit);
        EditText organisationalFactors = (EditText) appContext.findViewById(R.id.organisationalFactorsEdit);
        EditText additionalNotes = (EditText) appContext.findViewById(R.id.additionalNotesEdit);

        serializer.startTag(null, opinion);
        makeNode(appContext.getString(R.string.personalFactors), personalFactors.getText().toString());
        makeNode(appContext.getString(R.string.organisationalFactors), organisationalFactors.getText().toString());
        makeNode(appContext.getString(R.string.additionalNotes), additionalNotes.getText().toString());
        serializer.endTag(null, opinion);
    }

    /**
     * Adds file in Base64-encoding to XML message, if one is selected by the user.
     * Attributes are the encoding and the file name.
     * @throws IOException
     */
    private void addFile() throws IOException{
        if (!file.equals("")){
            serializer.startTag(null, appContext.getString(R.string.files));
            serializer.startTag(null, "file").attribute(null, "name", fileName).attribute(null, "encoding", "Base64");
            serializer.text(file);
            serializer.endTag(null, "file");
            serializer.endTag(null, appContext.getString(R.string.files));
        }
    }

    /**
     * Adds contact info to XML message, if given by the user.
     * @throws IOException
     */
    private void addContactInfo() throws IOException{
        EditText contactInfo = (EditText) appContext.findViewById(R.id.contactInformationEdit);

        if (checkIfFieldEdited(contactInfo)){
            makeNode(appContext.getString(R.string.contactInformation), contactInfo.getText().toString());
        }
    }

    /**
     * Checks if user has written something into the referred EditText-field.
     * @param edit
     * @return true if user has written something into the field
     */
    private boolean checkIfFieldEdited(EditText edit){
        if (edit.getText().toString().trim().length() > 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Checks if user has selected a date.
     * @param date
     * @return true if user has selected a date
     */
    private boolean checkIfDateIsSet(Button date){
        if (date.getText().equals(appContext.getString(R.string.button_dateChoose))) {
            return false;
        }else{
            return true;
        }
    }

    /**
     * Checks if user has selected a time.
     * @param time
     * @return true if user has selected a time
     */
    private boolean checkIfTimeIsSet(Button time){
        if (time.getText().equals(appContext.getString(R.string.button_timeChoose))){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Makes a XML-node with the referred tag name and text.
     * @param tagName
     * @param text
     * @throws IOException
     */
    private void makeNode(String tagName, String text) throws IOException{
        serializer.startTag(null, tagName);
        serializer.text(text);
        serializer.endTag(null, tagName);
    }

    /**
     * Finishes the XML-message and closes the ByteOutputStream.
     * @throws IOException
     */
    private void finishDocument() throws IOException{
        serializer.endDocument();
        serializer.flush();
        xmlData.close();
    }
}
