package de.hszg.risikousapp.xmlParser;

import android.util.Log;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by Julian on 11.12.2014.
 */
public class XmlDocumentParser {

    private Document xmlDoc;

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

    public Document getXmlDoc(){
        return xmlDoc;
    }
}
