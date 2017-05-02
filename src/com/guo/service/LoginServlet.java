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
 * 验证来自微信服务器的请求
 */
public class LoginServlet extends HttpServlet{
	/*
	 * 微信服务器将验证信息转发到自有公网IP地址的服务器
	 * 
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		PrintWriter out=resp.getWriter();
		//加密的签名字符串
		String signature=req.getParameter("signature");
		//时间戳
		String timestamp=req.getParameter("timestamp");
		//随机数
		String nonce=req.getParameter("nonce");
		//随机字符串
		String echostr=req.getParameter("echostr");
		System.out.println("加密的签名字符串"+signature);
		System.out.println("时间戳"+timestamp);
		System.out.println("随机数"+nonce);
		System.out.println("随机字符串"+echostr);
		//验证成功
		if(ValidationUtil.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);//验证成功后直接返回echostr字符串
		}
		out.close();
		out=null;
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		//接收用户发送的消息：
		//1、用户发送内容，微信会接收XML消息，将XML消息转换成MAP
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
				
				if(userContent.equals("上海天气")){
					content="上海天气为多云转阴";
				}else if(userContent.equals("您好")){
					content="今天好，明天好，以后每天都很好！";
				}else{
					content="你的消息我们已经收到，谢谢您！";
				}
				msgEntity.setContent(content);
				result=MsgAgeUtils.testMessageToXml(msgEntity);
			}
			System.out.println("result"+result);
			writer.write(result);
		} catch (DocumentException e) {
			// TODO 自动生成的 catch 块
			System.out.println(e.getMessage());
		}finally{
			writer.close();
		}
	}
}
