package de.hszg.risikousapp.xmlParser;

import android.util.Log;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Class to parse XML-data with use of the {@link javax.xml.parsers.DocumentBuilder}
 */
public class XmlDocumentParser {

    private Document xmlDoc;

    /**
     * Parse given Xml-Data as String with parse()-method of {@link javax.xml.parsers.DocumentBuilder} .
     * xml-input must have utf-8 encoding
     * @param xmlData
     */
    public XmlDocumentParser(String xmlData){
                ByteArrayInputStream xmlStream = null;

                try {
                    xmlStream = new ByteArrayInputStream(xmlData.getBytes("UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("Codierung", "Die Codierung wird nicht unterstützt");
                }

                InputSource source = new InputSource(xmlStream);

                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = null;
                try {
                    builder = factory.newDocumentBuilder();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
                try {
                    xmlDoc = builder.parse(source);
                } catch (SAXException e) {
                    Log.e("Parsing", "Builder failed");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
        }
    }

    /**
     * Get parsed document.
     * @return Document with parsed XML
     */
    public Document getXmlDoc(){
        return xmlDoc;
    }
}
