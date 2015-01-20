package de.hszg.risikousapp.questionnaire;

import android.util.Log;

import org.w3c.dom.Document;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import de.hszg.risikousapp.xmlParser.XmlDocumentParser;

/**
 * Get elements of the questionnaire skeleton, that is provided by the risikous server,
 * using XPath.
 */
public class QuestionnaireSkeletonParser {

    private Document questionnaireDoc;
    private XPath xpath;

    /**
     * Constructor, instantiate XmlDocumentParser and XPath.
     * @param questionnaireXml
     */
    public QuestionnaireSkeletonParser(String questionnaireXml) {
        XmlDocumentParser parser = new XmlDocumentParser(questionnaireXml);
        questionnaireDoc = parser.getXmlDoc();

        xpath = XPathFactory.newInstance().newXPath();
    }

    /**
     * Get the caption text for the referred questionnaire element.
     * @param questionnaireElementName
     * @return String caption
     */
    public String getQuestionCaption(String questionnaireElementName){
        try {
            return (String) xpath.evaluate("string(//" + questionnaireElementName + "/@text)", questionnaireDoc,
                    XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            Log.e("XPath Error", "Fehler bei Ausführung des XPath Ausdrucks - Skeleton Parser, caption");
        }
        return "";
    }

    /**
     * Get the maximum allowed characters for the referred questionnaire element.
     * @param questionnaireElement
     * @return int max chars
     */
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

    /**
     * Get the status if the element is required.
     * @param questionnaireElement
     * @return boolean required status
     */
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
