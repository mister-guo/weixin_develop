package com.guo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/*
 * 微信请求效验工具类
 */
public class ValidationUtil {

	//令牌信息
	private static String token="xiaoguo";
	//验证签名
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		//构建成一个字符串，将token,timestamp,nonce这三个参数进行字典排序
		String[] str=new String[]{token,timestamp,nonce};
		Arrays.sort(str);//排序
		//将三个参数字符串拼接成一个字符串进行sha1加密
		StringBuilder  buff=new StringBuilder();
		for(int i=0;i<str.length;i++){
			buff.append(str[i]);
		}
		MessageDigest md=null;
		String result="";
		try {
			//进行sha1加密
			md=MessageDigest.getInstance("SHA-1");
			//返回的是字节数组
			byte[] digest=md.digest(buff.toString().getBytes());
			//转换成加密后的字符串
			result=byteToStr(digest);
			System.out.println("result加密后的字符串："+result);
		} catch (NoSuchAlgorithmException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		System.out.println("sinature.toUpperCase():"+signature.toUpperCase());
		return result!=null?(result.equals(signature.toUpperCase())):false;
	}
	//将字节数组转换为十六进制字符串
	private static String byteToStr(byte[] byteArray){
	
		String strDigest="";
		for(int i=0;i<byteArray.length;i++){
			strDigest+=byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}
	//将一个字符转换成十六进制的字符串
	private static String byteToHexStr(byte mByte){
		char[] Digit={'0','1','2','3','4','5','6','7','8','9',
				'A','B','C','D','E','F'};
		char[] temp=new char[2];
		temp[0]=Digit[ (mByte >>> 4) & 0X0F];
		temp[1]=Digit[mByte & 0X0F];
		String s=new String(temp);
		return s;
		}
	}

