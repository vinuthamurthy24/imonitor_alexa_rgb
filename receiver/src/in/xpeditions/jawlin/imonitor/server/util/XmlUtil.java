/* Copyright © 2012 iMonitor Solutions India Private Limited */
/**
 * 
 */
package in.xpeditions.jawlin.imonitor.server.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author Coladi
 * 
 */
public class XmlUtil {
	
	private static DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	public static Document getDocument(File file)
			throws ParserConfigurationException, SAXException, IOException {
		//DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(file);
		doc.getDocumentElement().normalize();
		return doc;
	}

	public static Document getDocument(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
	//	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(inputStream);
		doc.getDocumentElement().normalize();
		return doc;
	}
	
	public static Document getDocument(String encryptedMessage)
			throws ParserConfigurationException, SAXException, IOException {

		//DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db;
		Document doc = null;
		db = dbf.newDocumentBuilder();
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(encryptedMessage));
		doc = db.parse(is);
		return doc;
	}

	public static String getCharacterDataFromElement(Element e) {
		Node child = e.getFirstChild();
		if (child instanceof CharacterData) {
			CharacterData cd = (CharacterData) child;
			return cd.getData();
		}
		return "?";
	}

	public static Node getChildElementByTagName(Node node, String nodeName) {
		List<Node> childElements = XmlUtil.getChildElementsByTagName(node,
				nodeName);
		if (childElements.size() < 1) {
			return null;
		}
		Node childElement = childElements.get(0);
		return childElement;
	}

	public static List<Node> getChildElementsByTagName(Node node,
			String nodeName) {
		Element element = (Element) node;
		NodeList elementsByTagName = element.getElementsByTagName(nodeName);
		List<Node> nodes = new ArrayList<Node>();
		for (int i = 0; i < elementsByTagName.getLength(); i++) {
			Node item = elementsByTagName.item(i);
			if (item.getParentNode().equals(node)) {
				nodes.add(item);
			}
		}
		return nodes;
	}
}
