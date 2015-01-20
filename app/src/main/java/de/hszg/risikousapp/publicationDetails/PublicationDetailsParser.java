package de.hszg.risikousapp.publicationDetails;

import android.util.Log;

import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import de.hszg.risikousapp.xmlParser.XmlDocumentParser;

/**
 * Xml Parser to get the publication details.
 */
public class PublicationDetailsParser {
    private Document publicationDoc;
    private XPath xpath;

    private PublicationForDetails publication;

    public PublicationDetailsParser(String publicationXml){
            XmlDocumentParser parser = new XmlDocumentParser(publicationXml);
            publicationDoc = parser.getXmlDoc();

            xpath = XPathFactory.newInstance().newXPath();

            getAllPublicationElements();
    }

    /**
     * Gets all elements for publication object from xml and set them to local the variable.
     */
    private void getAllPublicationElements() {
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

    /**
     * Returns the node text of the specified tag.
     * @param tagName name of the tag where you want the text
     * @return text of given node name
     */
    private String getNodetext(String tagName){
        try {
            return (String) xpath.evaluate("/publication/" + tagName + "/text()", publicationDoc,
                    XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            Log.e("XPath Error", "Fehler bei Ausführung des XPath Ausdrucks - Publication, nodetext");
        }
        return "";
    }

    /**
     * @return publication object for detail view
     */
    public PublicationForDetails getPublication() {
        return publication;
    }
}

