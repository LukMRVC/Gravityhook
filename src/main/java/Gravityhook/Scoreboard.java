package Gravityhook;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class Scoreboard {

    public final String filepath = "scoreboards.xml";

    public void writeScore(String score, String player) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = null;
        Element root = null;
        InputStream fis;
        try {
            fis = new FileInputStream(filepath);
            doc = builder.parse(fis);
        } catch (SAXException | IOException ex ) {
            doc = builder.newDocument();
            root = doc.createElement("Gravityhook");
            root.appendChild(doc.createElement("Scores"));
            doc.appendChild(root);
        }
        root = doc.getDocumentElement();
        Node scores = root.getFirstChild();
        Element eScore = doc.createElement("Score");
        Element ePlayer = doc.createElement("Player");
        ePlayer.setTextContent(player);
        Element value = doc.createElement("Value");
        value.setTextContent(score);
        Element date = doc.createElement("Datetime");
        date.setTextContent(Long.toString(System.currentTimeMillis() / 1000L));
        eScore.appendChild(ePlayer);
        eScore.appendChild(value);
        eScore.appendChild(date);
        scores.appendChild(eScore);
        DOMSource xml = new DOMSource(doc);


        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult("scoreboards.xml");
        transformer.transform(xml, result);
    }

    public NodeList readScores() throws ParserConfigurationException, FileNotFoundException {
        InputStream fis = new FileInputStream(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        NodeList scores = null;
        DocumentBuilder builder = factory.newDocumentBuilder();
        try {
            Document doc = builder.parse(fis);
            Element root = doc.getDocumentElement();
            Node s = root.getFirstChild();
            scores = s.getChildNodes();
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
        return scores;

    }
}
