/**
 * 
 * TQTVD CONFIDENTIAL
 * __________________
 * 
 *  [2012] - [2013] TQTVD SOFTWARE LTDA 
 *  All Rights Reserved.
 * 
 * NOTICE:  All information contained herein is, and remains
 * the property of TQTVD SOFTWARE LTDA and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to TQTVD SOFTWARE LTDA
 * and its suppliers and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from TQTVD SOFTWARE LTDA.
*/
package util;

import com.sun.dtv.ui.event.RemoteControlEvent;

public class KeyboardHelper {
	
	public static boolean isOK(int keyCode) {
		return keyCode == RemoteControlEvent.VK_CONFIRM || keyCode == RemoteControlEvent.VK_ENTER || keyCode == 80;
	}

	public static boolean isAppStarter(int keyCode) {
		return keyCode == RemoteControlEvent.VK_CONFIRM || keyCode == RemoteControlEvent.VK_ENTER;
	}
	
	public static boolean isExit(int keyCode){
        return keyCode == 462 || RemoteControlEvent.VK_ESCAPE == keyCode;
	}

	public static boolean isBack(int keyCode) {
        return keyCode == RemoteControlEvent.VK_BACK || keyCode == 8;
	}

	public static boolean isLeft(int keyCode) {
		return keyCode == RemoteControlEvent.VK_LEFT || keyCode == 37;
	}
	
	public static boolean isRight(int keyCode) {
		return keyCode == RemoteControlEvent.VK_RIGHT || keyCode == 39;
	}
	
	public static boolean isUp(int keyCode) {
		return keyCode == RemoteControlEvent.VK_UP|| keyCode == 38;
	}
	
	public static boolean isDown(int keyCode) {
		return keyCode == RemoteControlEvent.VK_DOWN|| keyCode == 40;
	}
	
	public static boolean isDirectional(int keyCode) {
		return isUp(keyCode) || isDown(keyCode) || isLeft(keyCode) || isRight(keyCode);
	}
	
	public static boolean isRed(int keyCode) {
		return keyCode == RemoteControlEvent.VK_COLORED_KEY_0;
	}
	
	public static boolean isGreen(int keyCode) {
		return keyCode == RemoteControlEvent.VK_COLORED_KEY_1;
	}
	
	public static boolean isYellow(int keyCode) {
		return keyCode == RemoteControlEvent.VK_COLORED_KEY_2;
	}

	public static boolean isBlue(int keyCode) {
		return keyCode == RemoteControlEvent.VK_COLORED_KEY_3;
	}
	
}
