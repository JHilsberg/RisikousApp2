package de.hszg.risikousapp.xmlParser;

import android.util.Log;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

/**
 * Created by Julian on 08.12.2014.
 */
public class QuestionnaireSkeletonParser {

    private Document questionnaireDoc;
    private XPath xpath;

    public QuestionnaireSkeletonParser(String questionnaireXml) {
        XmlDocumentParser parser = new XmlDocumentParser(questionnaireXml);
        questionnaireDoc = parser.getXmlDoc();

        xpath = XPathFactory.newInstance().newXPath();
    }

    public String getQuestionCaption(String questionName){
        try {
            return (String) xpath.evaluate("string(//" + questionName + "/@text)", questionnaireDoc,
                    XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            Log.e("XPath Error", "Fehler bei Ausführung des XPath Ausdrucks - Skeleton Parser Caption");
        }
        return "";
    }

    public String getAnswerMaxChars(String question) {
        try {
            return (String) xpath.evaluate("string(//"+ question +"/@maximumOfCharacters)", questionnaireDoc,
                    XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            Log.e("XPath Error", "Fehler bei Ausführung des XPath Ausdrucks - Skeleton Parser max Chars");
        }
        return "";
    }
}
