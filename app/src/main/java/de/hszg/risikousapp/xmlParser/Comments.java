package de.hszg.risikousapp.xmlParser;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import de.hszg.risikousapp.models.Comment;
import de.hszg.risikousapp.models.PublicationForList;

/**
 * Created by Hannes on 18.12.2014.
 */
public class Comments {
    private Document commentDoc;
    private XPath xpath;

    public Comments(String commentsXml) {
        XmlDocumentParser parser = new XmlDocumentParser(commentsXml);
        commentDoc = parser.getXmlDoc();

        xpath = XPathFactory.newInstance().newXPath();
    }

    private NodeList getCommentNodeList(String element) {
        String expression = "/comments/comment/" + element;

        try {
            return (NodeList) xpath.compile(expression).evaluate(commentDoc,
                    XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            Log.e("Parser", "Fehler beim Parsen der Kommentare");
        }
        return null;
    }

    public ArrayList<Comment> getData() {
        NodeList commentNodeListAuthor = getCommentNodeList("author");
        NodeList commentNodeListText = getCommentNodeList("text");
        NodeList commentNodeListTimeStamp = getCommentNodeList("timeStamp");

        ArrayList<Comment> commentList = new ArrayList<Comment>();

        for (int i = 0; i < commentNodeListText.getLength(); i++) {
            commentList.add(
                    new Comment(
                            commentNodeListAuthor.item(i).getLastChild().getNodeValue(),
                            commentNodeListTimeStamp.item(i).getLastChild().getNodeValue(),
                            commentNodeListText.item(i).getLastChild().getNodeValue()
                    )
            );
        }
        return commentList;
    }
}
