package de.hszg.risikousapp.publicationDetails.comments;

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
 * Get all comments for a specific publication using Xpath.
 */
public class CommentsParser {
    private Document commentDoc;
    private XPath xpath;

    public CommentsParser(String commentsXml) {
        XmlDocumentParser parser = new XmlDocumentParser(commentsXml);
        commentDoc = parser.getXmlDoc();

        xpath = XPathFactory.newInstance().newXPath();
    }

    /**
     * Executes xpath expression to get the specified element as a xml node list.
     * @param element xml element name
     * @return node list
     */
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

    private ArrayList<Comment> getListOfAnswers() {
        String path = "listOfAnswers/comment/";
        NodeList commentNodeListAuthor = getCommentNodeList(path+"author");
        NodeList commentNodeListText = getCommentNodeList(path+"text");
        NodeList commentNodeListTimeStamp = getCommentNodeList(path+"timeStamp");

        ArrayList<Comment> listOfAnswers = new ArrayList<>();

        for (int i = 0; i < commentNodeListText.getLength(); i++) {
            listOfAnswers.add(
                    new Comment(
                            commentNodeListAuthor.item(i).getLastChild().getNodeValue(),
                            commentNodeListTimeStamp.item(i).getLastChild().getNodeValue(),
                            commentNodeListText.item(i).getLastChild().getNodeValue()
                            )
            );
        }
        return listOfAnswers;
    }

    /**
     * Returns a list with all comments of a publication.
     * @return arrayList with comment objects
     */
    public ArrayList<Comment> getCommentList() {
        NodeList commentNodeListAuthor = getCommentNodeList("author");
        NodeList commentNodeListText = getCommentNodeList("text");
        NodeList commentNodeListTimeStamp = getCommentNodeList("timeStamp");

        ArrayList<Comment> commentList = new ArrayList<>();

        for (int i = 0; i < commentNodeListText.getLength(); i++) {
            Comment comment = new Comment(
                            commentNodeListAuthor.item(i).getLastChild().getNodeValue(),
                            commentNodeListTimeStamp.item(i).getLastChild().getNodeValue(),
                            commentNodeListText.item(i).getLastChild().getNodeValue()
                    );
            comment.setListOfAnswers(getListOfAnswers());
            commentList.add(comment);
        }
        return commentList;
    }
}