package de.hszg.risikousapp.questionnaire.reportingArea;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import de.hszg.risikousapp.xmlParser.XmlDocumentParser;

/**
 * XML-Parser to parse the publication areas with XPath.
 */
public class ReportingAreasParser {

    private Document reportingAreasDoc;
    private XPath xpath;

    /**
     * Create new instances of XmlDocumentParser an XPath
     * @param questionnaireXml
     */
    public ReportingAreasParser(String questionnaireXml) {
        XmlDocumentParser parser = new XmlDocumentParser(questionnaireXml);
        reportingAreasDoc = parser.getXmlDoc();

        xpath = XPathFactory.newInstance().newXPath();
    }

    /**
     * Get node list of selected element (name, shortcut) of all reporting areas.
     * @param element
     * @return node list of one element of all reporting areas
     */
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

    /**
     * Read name and shortcut out of node list.
     * Create reportingArea object for each area in XML-message and save them in one ArrayList.
     * @return list of all reporting ares
     */
    public ArrayList<ReportingArea> getReportingAreas(){
        NodeList areasNodeListNames = getReportingAreasNodeList("name");
        NodeList areasNodeListShortcuts = getReportingAreasNodeList("shortcut");

        ArrayList<ReportingArea> reportingAreas = new ArrayList<>();

        for (int i = 0; i < areasNodeListNames.getLength(); i++) {
            reportingAreas.add(new ReportingArea(areasNodeListNames.item(i).getLastChild().getNodeValue(),
                    areasNodeListShortcuts.item(i).getLastChild().getNodeValue()));
        }

        return reportingAreas;
    }
}
