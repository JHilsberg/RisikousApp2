package de.hszg.risikousapp.xmlParser;


import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.lang.String;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import de.hszg.risikousapp.models.PublicationForList;

/**
 * Created by besitzer on 17.12.14.
 */
public class PublicationList {

    private Document publicationListDoc;
    private XPath xpath;

    public PublicationList(String questionnaireXml) {
        XmlDocumentParser parser = new XmlDocumentParser(questionnaireXml);
        publicationListDoc = parser.getXmlDoc();

        xpath = XPathFactory.newInstance().newXPath();
    }

    private NodeList getPublicationNodeList(String element) {
        String expression = "/publications/publication/" + element;

        try {
            return (NodeList) xpath.compile(expression).evaluate(publicationListDoc,
                    XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            Log.e("Parser", "Fehler beim Parsen der Namen Veröffentlichungen");
        }

        return null;
    }

    public ArrayList<PublicationForList> getData() {
        NodeList areasNodeListid = getPublicationNodeList("id");
        NodeList areasNodeListTitle = getPublicationNodeList("title");
        NodeList areasNodeListReports = getPublicationNodeList("numberOfReports");
        NodeList areasNodeListStatus = getPublicationNodeList("status");
        NodeList areasNodeListComments = getPublicationNodeList("numberOfComments");
        NodeList areasNodeListentryDate = getPublicationNodeList("entryDate");

        ArrayList<PublicationForList> PublicationForList = new ArrayList<PublicationForList>();

        for (int i = 0; i < areasNodeListid.getLength(); i++) {

            PublicationForList.add(
                    new PublicationForList(
                            areasNodeListid.item(i).getLastChild().getNodeValue(),
                            areasNodeListTitle.item(i).getLastChild().getNodeValue(),
                            areasNodeListReports.item(i).getLastChild().getNodeValue(),
                            areasNodeListStatus.item(i).getLastChild().getNodeValue(),
                            areasNodeListComments.item(i).getLastChild().getNodeValue(),
                            areasNodeListentryDate.item(i).getLastChild().getNodeValue()
                    )
            );
        }
        return PublicationForList;
    }
}
