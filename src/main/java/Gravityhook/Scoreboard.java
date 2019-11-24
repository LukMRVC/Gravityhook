package Gravityhook;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;

public class Scoreboard {

    public final String filepath = "scoreboards.xml";

    public void writeScore(String score, String player) throws IOException, ParserConfigurationException {

    }

    public void readScores() throws ParserConfigurationException, FileNotFoundException {
        InputStream fis = new FileInputStream(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        try {
            Document doc = builder.parse(fis);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
