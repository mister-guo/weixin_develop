package com.guo.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.guo.entity.MsgEntity;
import com.thoughtworks.xstream.XStream;

public class MsgAgeUtils {

	//将xml转换成map
	public static Map<String,String> toXmlMap(HttpServletRequest req) throws IOException, DocumentException{
		Map<String,String> map=new HashMap<String,String>();
		SAXReader saxReader=new SAXReader();
		//获取用户输入的文件流
		ServletInputStream input=req.getInputStream();
		//将文件流转换成map
		Document doc=saxReader.read(input);
		Element root=doc.getRootElement();
		//获取子节点
		List<Element> element=root.elements();
		//遍历取出
		for(Element e:element){
			map.put(e.getName(), e.getText());
		}
		return map;
	}
	//将我们的实体转换成xml
	public static String testMessageToXml(MsgEntity msgEntity){
		XStream xStream=new XStream();
		//将请求头替换
		xStream.alias("xml", msgEntity.getClass());
		return xStream.toXML(msgEntity);
	}
}
