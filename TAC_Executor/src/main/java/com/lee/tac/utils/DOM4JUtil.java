package com.lee.tac.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * program: tac-root
 * description: ${description}
 * author: zhao lee
 * create: 2018-04-23 19:57
 **/
public class DOM4JUtil {

    public static Document xml2Dom4j(String filePath){
        SAXReader reader = new SAXReader();
        Document document = null;
        try {
            // 通过reader对象的read方法加载xml文件,获取docuemnt对象。
            document = reader.read(new File(filePath));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static String getTestNGXmlSuiteName(Document document){
        SAXReader reader = new SAXReader();
        String projectName = "";

        // 通过document对象获取根节点
        Element suiteRoot = document.getRootElement();
        projectName = suiteRoot.attribute("name").getValue();
        return projectName;
    }

    public static void addLogIdParameter(Document document, String LogId){

        // 通过document对象获取根节点
        Element suiteRoot = document.getRootElement();
        Element parameterNode=suiteRoot.addElement("parameter");
        parameterNode.addAttribute("name","logId");
        parameterNode.addAttribute("value",LogId);

    }

    public static void setTestNGXmlSuiteName(Document document, String value){

        // 通过document对象获取根节点
        Element suiteRoot = document.getRootElement();
        suiteRoot.attribute("name").setValue(value);
    }

    public static String getTestNGXmlTestName(Document document){
        SAXReader reader = new SAXReader();
        String testName = "";
        Element suiteRoot = document.getRootElement();
        // 通过element对象的elementIterator方法获取迭代器
        Element testNode = (Element) suiteRoot.elements().get(0);
        testName = testNode.attribute("name").getValue();
        return testName;
    }

    public static void setTestNGXmlTestName(Document document, String value){
        Element suiteRoot = document.getRootElement();
        // 通过element对象的elementIterator方法获取迭代器
        Element testNode = (Element) suiteRoot.elements().get(0);
        testNode.attribute("name").setValue(value);
    }

    /**
     * 通过document对象将内存中的dom树保存到新的xml文档。
     *
     * @param document
     * @throws Exception
     */
    public static void writeToNewXMLDocument(String filePath, Document document){

        XMLWriter writer = null;
        try {
            writer = new XMLWriter(new FileWriter(filePath));
            writer.write(document);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Document document = DOM4JUtil.xml2Dom4j("/Users/zhaoli/Downloads/files/1524559177944.xml");
        DOM4JUtil.setTestNGXmlSuiteName(document, "111");
        DOM4JUtil.setTestNGXmlTestName(document, "222");
        writeToNewXMLDocument("/Users/zhaoli/Downloads/files/1524559177944.xml",document);

    }
}
