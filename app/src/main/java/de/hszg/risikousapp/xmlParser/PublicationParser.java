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

import de.hszg.risikousapp.models.SearchingResults;

/**
 * Created by besitzer on 17.12.14.
 */
public class PublicationParser {

    private Document publicationAreasDoc;
    private XPath xpath;

    protected  int i =0;

    public  PublicationParser(String questionnaireXml) {
        XmlDocumentParser parser = new XmlDocumentParser(questionnaireXml);
        publicationAreasDoc = parser.getXmlDoc();

        xpath = XPathFactory.newInstance().newXPath();
    }

    private NodeList getReportingAreasNodeList(String element) {
        String expression = "/publications/publication/" + element;

        try {
            return (NodeList) xpath.compile(expression).evaluate(publicationAreasDoc,
                    XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            Log.e("Parser", "Fehler beim Parsen der Namen der reporting areas");
        }

        return null;
    }



    public ArrayList<SearchingResults> getData(){
        NodeList areasNodeListid = getReportingAreasNodeList("id");
        NodeList areasNodeListReports = getReportingAreasNodeList("numberOfReports");
        NodeList areasNodeListStatus = getReportingAreasNodeList("status");
        NodeList areasNodeListComments = getReportingAreasNodeList("numberOfComments");
        NodeList areasNodeListentryDate = getReportingAreasNodeList("entryDate");
        //  NodeList areasNodeList = getReportingAreasNodeList("id");


        ArrayList<SearchingResults> SearchingResults = new ArrayList<SearchingResults>();

        for (int i = 0; i < areasNodeListid.getLength(); i++) {
            //ids.add(i, areasNodeList.item(i).getLastChild().getNodeValue());

            SearchingResults.add(new SearchingResults(areasNodeListid.item(i).getLastChild().getNodeValue(),
                    areasNodeListReports.item(i).getLastChild().getNodeValue(),areasNodeListStatus.item(i).getLastChild().getNodeValue(),
                    areasNodeListComments.item(i).getLastChild().getNodeValue(),areasNodeListentryDate.item(i).getLastChild().getNodeValue()));




        }

        return SearchingResults;
    }
}
