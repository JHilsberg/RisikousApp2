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

    public String getAnswerMaxChars(String questionnaireElement) {
        try {
            return (String) xpath.evaluate("string(//"+ questionnaireElement +"/@maximumOfCharacters)", questionnaireDoc,
                    XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            Log.e("XPath Error", "Fehler bei Ausführung des XPath Ausdrucks - Skeleton Parser, max Chars");
        }
        return "";
    }
}
