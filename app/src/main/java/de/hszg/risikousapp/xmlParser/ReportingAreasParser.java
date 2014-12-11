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
public class ReportingAreasParser {

    private Document reportingAreasDoc;
    private XPath xpath;

    public ReportingAreasParser(String questionnaireXml) {
        XmlDocumentParser parser = new XmlDocumentParser(questionnaireXml);
        reportingAreasDoc = parser.getXmlDoc();

        xpath = XPathFactory.newInstance().newXPath();
    }

    private NodeList getReportingAreasNodeList() throws XPathExpressionException {
        String expression = "/reportingAreas/reportingArea/name";

        return (NodeList) xpath.compile(expression).evaluate(reportingAreasDoc,
                    XPathConstants.NODESET);
    }

    public ArrayList<String> getReportingAreasNames(){
        NodeList areasNodeList = null;
        try {
            areasNodeList = getReportingAreasNodeList();
        } catch (XPathExpressionException e) {
            Log.e("XPath Error", "Fehler bei Ausführung des XPath Ausdrucks");
        }

        ArrayList<String> areasNames = new ArrayList<String>();
        for (int i = 0; i < areasNodeList.getLength(); i++) {
            areasNames.add(i, areasNodeList.item(i).getLastChild().getNodeValue());
        }

        return areasNames;
    }
}
