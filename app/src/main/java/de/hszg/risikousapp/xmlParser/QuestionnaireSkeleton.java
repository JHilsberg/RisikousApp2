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
public class QuestionnaireSkeleton {

    private Document questionnaireDoc;
    private XPath xpath;

    public QuestionnaireSkeleton(String questionnaireXml) {
        XmlDocumentParser parser = new XmlDocumentParser(questionnaireXml);
        questionnaireDoc = parser.getXmlDoc();

        xpath = XPathFactory.newInstance().newXPath();
    }

    public String getQuestionCaption(String questionnaireElementName){
        try {
            return (String) xpath.evaluate("string(//" + questionnaireElementName + "/@text)", questionnaireDoc,
                    XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            Log.e("XPath Error", "Fehler bei Ausführung des XPath Ausdrucks - Skeleton Parser, caption");
        }
        return "";
    }

    public int getAnswerMaxChars(String questionnaireElement) {
        try {
            return Integer.parseInt( (String) xpath.evaluate("string(//"+ questionnaireElement +"/@maximumOfCharacters)", questionnaireDoc,
                    XPathConstants.STRING));
        } catch (XPathExpressionException e) {
            Log.e("XPath Error", "Fehler bei Ausführung des XPath Ausdrucks - Skeleton Parser, max Chars");
        } catch (ClassCastException e){
            Log.e("Cast Error", "Fehler bei Ausführung des Casts in Integer");
        }
        return 0;
    }

    public Boolean getRequiredStatus(String questionnaireElement) {
        try {
            return (Boolean) xpath.evaluate("string(//"+ questionnaireElement +"/@required)", questionnaireDoc,
                    XPathConstants.BOOLEAN);
        } catch (XPathExpressionException e) {
            Log.e("XPath Error", "Fehler bei Ausführung des XPath Ausdrucks - Skeleton Parser, required status");
        }
        return false;
    }
}
