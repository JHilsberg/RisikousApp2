package de.hszg.risikousapp.publicationDetails.comments;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import de.hszg.risikousapp.publicationDetails.comments.Comment;
import de.hszg.risikousapp.xmlParser.XmlDocumentParser;

/**
 * Parsing xml file from server to get all comments for a specific publication.
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
        String s = "listOfAnswers/comment";
        NodeList commentNodeListAuthor = getCommentNodeList(s+"author");
        NodeList commentNodeListText = getCommentNodeList(s+"text");
        NodeList commentNodeListTimeStamp = getCommentNodeList(s+"timeStamp");

        ArrayList<Comment> listOfAnswers = new ArrayList<>();

        for (int i = 0; i < commentNodeListText.getLength(); i++) {
            listOfAnswers.add(
                    new Comment(
                            commentNodeListAuthor.item(i).getLastChild().getNodeValue(),
                            commentNodeListTimeStamp.item(i).getLastChild().getNodeValue(),
                            commentNodeListText.item(i).getLastChild().getNodeValue(),
                            new ArrayList<Comment>()
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
            commentList.add(
                    new Comment(
                            commentNodeListAuthor.item(i).getLastChild().getNodeValue(),
                            commentNodeListTimeStamp.item(i).getLastChild().getNodeValue(),
                            commentNodeListText.item(i).getLastChild().getNodeValue(),
                            getListOfAnswers()
                    )
            );
        }
        return commentList;
    }
}
