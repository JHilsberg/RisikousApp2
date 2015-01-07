package de.hszg.risikousapp.publicationDetails;

import android.util.Log;

import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import de.hszg.risikousapp.publicationDetails.PublicationForDetails;
import de.hszg.risikousapp.xmlParser.XmlDocumentParser;

/**
 * Created by Hannes on 18.12.2014.
 */
public class PublicationDetailsParser {
    private Document publicationDoc;
    private XPath xpath;

    private PublicationForDetails publication;

    public PublicationDetailsParser(String publicationXml){
        XmlDocumentParser parser = new XmlDocumentParser(publicationXml);
        publicationDoc = parser.getXmlDoc();

        xpath = XPathFactory.newInstance().newXPath();

        setDetails();
    }

    private void setDetails() {
        publication = new PublicationForDetails();

        publication.setTitle(getNodetext("title"));
        publication.setIncidentReport(getNodetext("incidentReport"));
        publication.setMinRPZofReporter(getNodetext("minRPZofReporter"));
        publication.setAvgRPZofReporter(getNodetext("avgRPZofReporter"));
        publication.setMaxRPZofReporter(getNodetext("maxRPZofReporter"));
        publication.setMinRPZofQBM(getNodetext("minRPZofQMB"));
        publication.setAvgRPZofQBM(getNodetext("avgRPZofQMB"));
        publication.setMaxRPZofQBM(getNodetext("maxRPZofQMB"));
        publication.setDifferenceStatement(getNodetext("differenceStatement"));
        publication.setCategory(getNodetext("category"));
        publication.setAction(getNodetext("action"));
        publication.setAssignedReports(getNodetext("assignedReports"));
    }

    private String getNodetext(String tagName){
        try {
            return (String) xpath.evaluate("/publication/" + tagName + "/text()", publicationDoc,
                    XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            Log.e("XPath Error", "Fehler bei Ausführung des XPath Ausdrucks - Publication, nodetext");
        }
        return "";
    }

    public PublicationForDetails getPublication() {
        return publication;
    }
}

