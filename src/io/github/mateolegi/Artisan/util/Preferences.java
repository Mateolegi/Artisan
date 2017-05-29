/*
 * The MIT License
 *
 * Copyright 2017 mateo.leal.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package io.github.mateolegi.Artisan.util;

import io.github.mateolegi.Artisan.models.Project;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author mateo
 */
public class Preferences {

    DocumentBuilderFactory docFactory;
    DocumentBuilder docBuilder;
    Document doc;
    Element root;
    public static final String PATH = "config.xml";

    public Preferences() {
        try {
            docFactory = DocumentBuilderFactory.newInstance();
            docBuilder = docFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public void createFile() {
        try {

            doc = docBuilder.newDocument();
            root = doc.createElement("properties");
            doc.appendChild(root);

            Element configurations = doc.createElement("configurations");
            Element projects = doc.createElement("projects");
            root.appendChild(configurations);
            root.appendChild(projects);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(PATH));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
        } catch (DOMException | TransformerException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }

    public void saveProp(String father, String title, String value) {

        try {
            File file = new File(PATH);
            doc = docBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodes = doc.getElementsByTagName(father);
            Node node = nodes.item(0);
            if (node == null) {
                root = doc.getDocumentElement();
                Element elem = doc.createElement(father);
                root.appendChild(elem);
                nodes = doc.getElementsByTagName(father);
                node = nodes.item(0);
            }
            Element prop = doc.createElement(title);
            node.appendChild(prop);
            prop.appendChild(doc.createTextNode(value));

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(PATH));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);

        } catch (SAXException | TransformerException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("File does not exist, will be created. " + ex.getMessage());
            createFile();
            saveProp(father, title, value);
        }
    }

    public String getProp(String nodeName, String title) {

        String value = "";
        try {
            File file = new File(PATH);
            doc = docBuilder.parse(file);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName(nodeName);
            Element element = (Element) nList.item(0);
            value = element.getElementsByTagName(title).item(0).getTextContent();
        } catch (Exception e) {
            return "";
        }
        return value;
    }

    public boolean fileExists() {
        File file = new File(PATH);
        return file.exists() && !file.isDirectory();
    }

    public boolean saveProject(Project project) {
        try {

            File file = new File(PATH);
            doc = docBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList projects = doc.getElementsByTagName("projects");
            Element projectsNode = (Element) projects.item(0);

            Element newProject = doc.createElement("project");
            projectsNode.appendChild(newProject);

            Attr name = doc.createAttribute("name");
            name.setValue(project.getName());
            newProject.setAttribute("name", project.getName());

            Element projectPath = doc.createElement("path");
            projectPath.appendChild(doc.createTextNode(project.getPath()));
            newProject.appendChild(projectPath);

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(PATH));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);
            transformer.transform(source, result);
            return true;
            
        } catch (IOException | TransformerException | DOMException | SAXException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
    
    public LinkedList<Project> getProjects() {
        LinkedList<Project> projects = new LinkedList<>();
        
        try {
            File file = new File(PATH);
            doc = docBuilder.parse(file);
            doc.getDocumentElement().normalize();
            
            NodeList nList = doc.getElementsByTagName("project");
            
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elem = (Element) node;
                    projects.add(new Project(elem.getAttribute("name"), elem.getElementsByTagName("path").item(0).getTextContent()));
                }
            }
            
        } catch (IOException | DOMException | SAXException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        return projects;
    }
}
