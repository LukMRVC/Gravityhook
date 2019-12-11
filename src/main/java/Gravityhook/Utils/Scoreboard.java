package Gravityhook.Utils;

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
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Scoreboard {

    public static class ScoreboardRow {

        public long date;
        public int score;
        public String nickname;

        public ScoreboardRow(int score, String nick) {
            this(score, nick, java.time.Instant.now().getEpochSecond());
        }

        public ScoreboardRow(int score, String nick, long date) {
            this.date = date;
            this.score = score;
            this.nickname = nick;
        }

        public int getScore() {
            return score;
        }

        public String getDate() {
            java.time.LocalDateTime dateTime = java.time.LocalDateTime.ofEpochSecond(date, 0, ZoneOffset.UTC);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", Locale.getDefault());
            return dateTime.format(formatter);
        }

        public String getNickname() {
            return nickname;
        }
    }

    public final String filepath = "scoreboards.xml";

    public void writeScore(ScoreboardRow row) throws ParserConfigurationException, TransformerException {
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
        ePlayer.setTextContent(row.nickname);
        Element value = doc.createElement("Value");
        value.setTextContent(Integer.toString(row.score));
        Element date = doc.createElement("Date");
        date.setTextContent(Long.toString(row.date));
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

    public List<ScoreboardRow> readScores() throws ParserConfigurationException, FileNotFoundException {
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
        ArrayList<ScoreboardRow> scoresList = new ArrayList<>();
        for (int i = 0; i < scores.getLength(); ++i) {
            Node item = scores.item(i);
            String nick = item.getChildNodes().item(0).getTextContent();
            String score = item.getChildNodes().item(1).getTextContent();
            String date = item.getChildNodes().item(2).getTextContent();
            scoresList.add(new ScoreboardRow(Integer.parseInt(score), nick, Long.parseLong(date)));
        }

        return scoresList;

    }
}
