package de.hszg.risikousapp.xmlParser;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Julian on 11.12.2014.
 */
public class ReportingAreas {

    private Document reportingAreasDoc;
    private XPath xpath;

    public ReportingAreas(String questionnaireXml) {
        XmlDocumentParser parser = new XmlDocumentParser(questionnaireXml);
        reportingAreasDoc = parser.getXmlDoc();

        xpath = XPathFactory.newInstance().newXPath();
    }

    private NodeList getReportingAreasNodeList(String element) {
        String expression = "/reportingAreas/reportingArea/" + element;

        try {
            return (NodeList) xpath.compile(expression).evaluate(reportingAreasDoc,
                        XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            Log.e("Parser", "Fehler beim Parsen der Namen der reporting areas");
        }

        return null;
    }

    public ArrayList<String> getReportingAreasNames(){
        NodeList areasNodeList = getReportingAreasNodeList("name");

        ArrayList<String> areasNames = new ArrayList<String>();
        for (int i = 0; i < areasNodeList.getLength(); i++) {
            areasNames.add(i, areasNodeList.item(i).getLastChild().getNodeValue());
        }

        return areasNames;
    }

    public ArrayList<String> getReportingAreasShortcuts(){
        NodeList areasNodeList = getReportingAreasNodeList("shortcut");

        ArrayList<String> areasNames = new ArrayList<String>();
        for (int i = 0; i < areasNodeList.getLength(); i++) {
            areasNames.add(i, areasNodeList.item(i).getFirstChild().getNodeValue());
        }

        return areasNames;
    }
}