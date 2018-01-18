package server.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class ReadXMLFile {
	private File fXmlFile;
	private DocumentBuilderFactory dbFactory;
	private DocumentBuilder dBuilder;
	private Document doc;
	
	public ReadXMLFile() {
		try {
			fXmlFile = new File("src/resources/config.xml");
			dbFactory = DocumentBuilderFactory.newInstance();
			dBuilder = dbFactory.newDocumentBuilder();
			doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getServerConf(String element) {
		NodeList nList = doc.getElementsByTagName("server");	
		Node serverNode = nList.item(0);
		Element eElement = (Element) serverNode;
		
		return eElement.getElementsByTagName(element).item(0).getTextContent();
	}
	
	public String getClientConf(String element) {
		NodeList nList = doc.getElementsByTagName("client");	
		Node serverNode = nList.item(0);
		Element eElement = (Element) serverNode;
		
		return eElement.getElementsByTagName(element).item(0).getTextContent();
	}
	
	public String getDatabaseConf(String element) {
		NodeList nList = doc.getElementsByTagName("database");	
		Node serverNode = nList.item(0);
		Element eElement = (Element) serverNode;
		
		return eElement.getElementsByTagName(element).item(0).getTextContent();
	}
}
