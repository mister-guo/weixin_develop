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

	//��xmlת����map
	public static Map<String,String> toXmlMap(HttpServletRequest req) throws IOException, DocumentException{
		Map<String,String> map=new HashMap<String,String>();
		SAXReader saxReader=new SAXReader();
		//��ȡ�û�������ļ���
		ServletInputStream input=req.getInputStream();
		//���ļ���ת����map
		Document doc=saxReader.read(input);
		Element root=doc.getRootElement();
		//��ȡ�ӽڵ�
		List<Element> element=root.elements();
		//����ȡ��
		for(Element e:element){
			map.put(e.getName(), e.getText());
		}
		return map;
	}
	//�����ǵ�ʵ��ת����xml
	public static String testMessageToXml(MsgEntity msgEntity){
		XStream xStream=new XStream();
		//������ͷ�滻
		xStream.alias("xml", msgEntity.getClass());
		return xStream.toXML(msgEntity);
	}
}
