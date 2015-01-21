package de.hszg.risikousapp.publicationDetails.comments;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

    /**
     * Initialize the XmlDocumnetParser and Xpath object.
     * @param commentsXml
     */
    public CommentsParser(String commentsXml) {
        XmlDocumentParser parser = new XmlDocumentParser(commentsXml);
        commentDoc = parser.getXmlDoc();

        xpath = XPathFactory.newInstance().newXPath();
    }

    /**
     * Get a list with all comments, including the answers
     * @return ArrayList all comments
     */
    public ArrayList<Comment> getComments(){
        NodeList commentNodeList = getCommentNodeList();
        ArrayList<Comment> commentList = new ArrayList<>();
        String id = "";
        String author = "";
        String timeStamp = "";
        String text = "";
        ArrayList<Comment> answers = null;

        for (int i = 0; i < commentNodeList.getLength(); i++){
            NodeList commentChildren = commentNodeList.item(i).getChildNodes();
            for (int j = 0; j < commentChildren.getLength(); j++){
                switch (commentChildren.item(j).getNodeName()) {
                    case "id" :
                        id = commentChildren.item(j).getLastChild().getNodeValue();
                        break;
                    case "author" :
                        author = commentChildren.item(j).getLastChild().getNodeValue();
                        break;
                    case "timeStamp" :
                        timeStamp = commentChildren.item(j).getLastChild().getNodeValue();
                        break;
                    case "text" :
                        text = commentChildren.item(j).getLastChild().getNodeValue();
                        break;
                    case "listOfAnswers" :
                        answers = getAnswers(commentChildren.item(j).getChildNodes());
                        break;
                }
            }
            Comment newComment = new Comment(id, author, timeStamp , text);
            if(answers != null){
                newComment.setListOfAnswers(answers);
            }else{
                newComment.setListOfAnswers(noAnswers());
            }

            commentList.add(newComment);
        }

        return commentList;
    }

    /**
     * Get all comment nodes
     * @return node list of comments
     */
    private NodeList getCommentNodeList(){
        String expression = "/comments/comment";

        try {
            return (NodeList) xpath.compile(expression).evaluate(commentDoc, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            Log.e("Parser", "Fehler beim Parsen der Kommentare");
        }

        return null;
    }

    /**
     * Get a list of all answers
     * @param answers
     * @return ArrayList of answers (comments)
     */
    private ArrayList<Comment> getAnswers(NodeList answers) {
        ArrayList<Comment> answerComments = new ArrayList<>();
        String id = "";
        String author = "";
        String timeStamp = "";
        String text = "";

        for (int i = 0; i < answers.getLength(); i++) {
            NodeList answerChildren = answers.item(i).getChildNodes();
            for (int j = 0; j < answerChildren.getLength(); j++) {
                switch (answerChildren.item(j).getNodeName()) {
                    case "id":
                        id = answerChildren.item(j).getLastChild().getNodeValue();
                        break;
                    case "author":
                        author = answerChildren.item(j).getLastChild().getNodeValue();
                        break;
                    case "timeStamp":
                        timeStamp = answerChildren.item(j).getLastChild().getNodeValue();
                        break;
                    case "text":
                        text = answerChildren.item(j).getLastChild().getNodeValue();
                        break;
                }
            }
            Comment answerComment = new Comment(id, author, timeStamp , text);
            answerComments.add(answerComment);
        }

        return answerComments;
    }

    /**
     * Get a ArrayList with one element, if no answer was found for a comment
     * @return ArrayList with one comment
     */
    private ArrayList<Comment> noAnswers() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();

        Comment noAnswerComment = new Comment("00", "admin", dateFormat.format(date), "Aktuell sind keine Antworten für diesen Kommentar vorhanden.");
        ArrayList<Comment> answerList = new ArrayList<>();
        answerList.add(noAnswerComment);
        return answerList;
    }
}