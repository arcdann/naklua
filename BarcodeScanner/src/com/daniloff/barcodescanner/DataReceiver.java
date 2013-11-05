package com.daniloff.barcodescanner;

import java.util.LinkedList;
import java.util.List;

public class DataReceiver {
	
	private static String pincode;
	private static String scanDate;
	private static  List<String> scannedCodes= new LinkedList<String>();
	
	public static void setObtainedCode(String obtainedCode) {
		if(obtainedCode!=null&&!obtainedCode.equals("")){
			scannedCodes.add(obtainedCode);
		}
	}
	
//	public static void 
	
	
	public  static String getPincode() {
		return pincode;
	}
	public static  void setPincode(String code) {
		pincode = code;
		System.out.println(pincode);
	}
	public  static String getScanDate() {
		return scanDate;
	}
	public static void setScanDate(String date) {
		scanDate = date;
		System.out.println(scanDate);
	}
	public  static List<String> getScannedCodes() {
		return scannedCodes;
	}
		
	
	

}
