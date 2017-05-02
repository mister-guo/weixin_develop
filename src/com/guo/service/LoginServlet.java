package com.guo.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.guo.entity.MsgEntity;
import com.guo.util.MsgAgeUtils;
import com.guo.util.ValidationUtil;

/*
 * ��֤����΢�ŷ�����������
 */
public class LoginServlet extends HttpServlet{
	/*
	 * ΢�ŷ���������֤��Ϣת�������й���IP��ַ�ķ�����
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out=resp.getWriter();
		//���ܵ�ǩ���ַ���
		String signature=req.getParameter("signature");
		//ʱ���
		String timestamp=req.getParameter("timestamp");
		//�����
		String nonce=req.getParameter("nonce");
		//����ַ���
		String echostr=req.getParameter("echostr");
		System.out.println("���ܵ�ǩ���ַ���"+signature);
		System.out.println("ʱ���"+timestamp);
		System.out.println("�����"+nonce);
		System.out.println("����ַ���"+echostr);
		//��֤�ɹ�
		if(ValidationUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);//��֤�ɹ���ֱ�ӷ���echostr�ַ���
		}
		out.close();
		out=null;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		//�����û����͵���Ϣ��
		//1���û��������ݣ�΢�Ż����XML��Ϣ����XML��Ϣת����MAP
		PrintWriter writer=resp.getWriter();
		try {
			Map<String,String> xmlMap=MsgAgeUtils.toXmlMap(req);
			System.out.println(xmlMap);
			String msgType=xmlMap.get("MsgType");
			String result=null;
			if(msgType.equals("text")){
				MsgEntity msgEntity=new MsgEntity();
				msgEntity.setToUserName(xmlMap.get("FromUserName"));
				msgEntity.setFromUserName(xmlMap.get("ToUserName"));
				msgEntity.setCreateTime(System.currentTimeMillis()+"");
				msgEntity.setMsgType("text");
				
				String userContent=xmlMap.get("Content");
				String content=null;
				
				if(userContent.equals("�Ϻ�����")){
					content="�Ϻ�����Ϊ����ת��";
				}else if(userContent.equals("����")){
					content="����ã�����ã��Ժ�ÿ�춼�ܺã�";
				}else{
					content="�����Ϣ�����Ѿ��յ���лл����";
				}
				msgEntity.setContent(content);
				result=MsgAgeUtils.testMessageToXml(msgEntity);
			}
			System.out.println("result"+result);
			writer.write(result);
		} catch (DocumentException e) {
			// TODO �Զ����ɵ� catch ��
			System.out.println(e.getMessage());
		}finally{
			writer.close();
		}
	}
}
