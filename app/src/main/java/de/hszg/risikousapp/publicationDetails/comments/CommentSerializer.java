package de.hszg.risikousapp.publicationDetails.comments;

import android.util.Xml;
import android.view.View;
import android.widget.EditText;

import org.xmlpull.v1.XmlSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hszg.risikousapp.R;

/**
 * Created by Julian on 21.01.2015.
 */
public class CommentSerializer {
    private View rootView;
    private ByteArrayOutputStream xmlData;
    private String xmlAsString;
    private XmlSerializer serializer;
    private String publicationId;

    /**
     * Constructor, instantiates ByteArrayOutputStream and XmlSerializer
     * Start and finish XML-Document, start all add methods.
     * @throws java.io.IOException
     */
    public CommentSerializer(View rootView, String id) throws IOException {
        this.rootView = rootView;
        this.xmlData = new ByteArrayOutputStream();
        this.serializer = Xml.newSerializer();
        this.publicationId = id;

        serializer.setOutput(xmlData, "UTF-8");
        serializer.startDocument(null, true);
        serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);

        serializer.startTag(null, "comment");

        addPublicationId();
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

    private void addPublicationId()throws IOException{
        makeNode("id", publicationId);
    }

    private void addAuthor() throws IOException{
        EditText author = (EditText) rootView.findViewById(R.id.newCommentAuthor);
        if(checkIfFieldEdited(author)){
           makeNode("author", author.getText().toString());
        }
    }

    private void addText() throws IOException{
        EditText commentText = (EditText) rootView.findViewById(R.id.newComment);
        makeNode("text", commentText.getText().toString());
    }

    /**
     * Checks if user has written something into the referred EditText-field.
     * @param edit
     * @return true if user has written something into the field
     */
    private boolean checkIfFieldEdited(EditText edit){
        if (edit.getText().toString().trim().length() > 0){
            return true;
        }else{
            return false;
        }
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

