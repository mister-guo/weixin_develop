package com.guo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/*
 * ΢������Ч�鹤����
 */
public class ValidationUtil {

	//������Ϣ
	private static String token="xiaoguo";
	//��֤ǩ��
	public static boolean checkSignature(String signature,String timestamp,String nonce){
		//������һ���ַ�������token,timestamp,nonce���������������ֵ�����
		String[] str=new String[]{token,timestamp,nonce};
		Arrays.sort(str);//����
		//�����������ַ���ƴ�ӳ�һ���ַ�������sha1����
		StringBuilder  buff=new StringBuilder();
		for(int i=0;i<str.length;i++){
			buff.append(str[i]);
		}
		MessageDigest md=null;
		String result="";
		try {
			//����sha1����
			md=MessageDigest.getInstance("SHA-1");
			//���ص����ֽ�����
			byte[] digest=md.digest(buff.toString().getBytes());
			//ת���ɼ��ܺ���ַ���
			result=byteToStr(digest);
			System.out.println("result���ܺ���ַ�����"+result);
		} catch (NoSuchAlgorithmException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		System.out.println("sinature.toUpperCase():"+signature.toUpperCase());
		return result!=null?(result.equals(signature.toUpperCase())):false;
	}
	//���ֽ�����ת��Ϊʮ�������ַ���
	private static String byteToStr(byte[] byteArray){
	
		String strDigest="";
		for(int i=0;i<byteArray.length;i++){
			strDigest+=byteToHexStr(byteArray[i]);
		}
		return strDigest;
	}
	//��һ���ַ�ת����ʮ�����Ƶ��ַ���
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

