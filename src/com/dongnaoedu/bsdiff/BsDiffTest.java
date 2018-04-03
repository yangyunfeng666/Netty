package com.dongnaoedu.bsdiff;


public class BsDiffTest {

	public static void main(String[] args) {
		String os = System.getProperty("os.name");
		//windows操作系统
		if(os.toLowerCase().startsWith("win")){
			//得到差分包
			BsDiff.diff(ConstantsWin.OLD_APK_PATH, ConstantsWin.NEW_APK_PATH, ConstantsWin.PATCH_PATH);		
		}else{
			BsDiff.diff(ConstantsLinux.OLD_APK_PATH, ConstantsLinux.NEW_APK_PATH, ConstantsLinux.PATCH_PATH);
		}
	
	}
	
}
