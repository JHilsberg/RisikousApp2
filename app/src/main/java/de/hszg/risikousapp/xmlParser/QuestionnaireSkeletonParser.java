package de.hszg.risikousapp.xmlParser;

import android.util.Log;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Julian on 08.12.2014.
 */
public class QuestionnaireSkeletonParser {

    private Document questionnaireDoc;
    private XPath xpath;

    public QuestionnaireSkeletonParser(String questionnaireXml) {
        InputSource source = new InputSource(new StringReader(questionnaireXml));

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        try {
            questionnaireDoc = builder.parse(source);
        } catch (SAXException e) {
            Log.e("Parsing", "Builder failed");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        xpath = XPathFactory.newInstance().newXPath();
    }

    public String getReportingAreaCaption(){
        try {
            return (String) xpath.evaluate("string(/questionnaire/reportingArea/@text)", questionnaireDoc,
                      XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            Log.e("XPath Error", "Fehler bei Ausführung des XPath Ausdrucks");
        }
        return "";
    }

    public String getIncidentDescriptionCaption() {
        try {
            return (String) xpath.evaluate("string(/questionnaire/incidentDescription/@text)", questionnaireDoc,
                    XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            Log.e("XPath Error", "Fehler bei Ausführung des XPath Ausdrucks");
        }
        return "";
    }

    public String getIncidentDescriptionHint() {
        try {
            return (String) xpath.evaluate("string(/questionnaire/incidentDescription/@maximumOfCharacters)", questionnaireDoc,
                    XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            Log.e("XPath Error", "Fehler bei Ausführung des XPath Ausdrucks");
        }
        return "";
    }
}
