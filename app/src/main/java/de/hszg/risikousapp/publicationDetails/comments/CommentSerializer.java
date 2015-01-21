package de.hszg.risikousapp.publicationDetails.comments;

import android.util.Xml;

import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Create xml message for comments and answers.
 */
public class CommentSerializer {
    private ByteArrayOutputStream xmlData;
    private String xmlAsString;
    private XmlSerializer serializer;
    private String id;
    private String author;
    private String text;

    /**
     * Constructor, instantiates ByteArrayOutputStream and XmlSerializer
     * Start and finish XML-Document, start all add methods.
     * @throws java.io.IOException
     */
    public CommentSerializer(String id, String author, String text) throws IOException {
        this.id = id;
        this.author = author;
        this.text = text;

        this.xmlData = new ByteArrayOutputStream();
        this.serializer = Xml.newSerializer();

        serializer.setOutput(xmlData, "UTF-8");
        serializer.startDocument(null, true);
        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        serializer.startTag(null, "comment");

        addId();
        addAuthor();
        addText();
        finishDocument();
    }

    /**
     * Get XML data as String
     * @return string with xml message
     */
    public String getXmlAsString() {
        xmlAsString = xmlData.toString();
        return xmlAsString;
    }

    private void addId()throws IOException{
        makeNode("id", id);
    }

    private void addAuthor() throws IOException{
           makeNode("author", this.author);
    }

    private void addText() throws IOException{
        makeNode("text", this.text);
    }

    /**
     * Makes a XML-node with the referred tag name and text.
     * @param tagName
     * @param text
     * @throws IOException
     */
    private void makeNode(String tagName, String text) throws IOException{
        serializer.startTag(null, tagName);
        serializer.text(text);
        serializer.endTag(null, tagName);
    }

    /**
     * Finishes the XML-message and closes the ByteOutputStream.
     * @throws IOException
     */
    private void finishDocument() throws IOException{
        serializer.endDocument();
        serializer.flush();
        xmlData.close();
    }

}

