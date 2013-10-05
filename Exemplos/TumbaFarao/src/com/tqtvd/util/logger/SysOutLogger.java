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
package com.tqtvd.util.logger;

public class SysOutLogger extends Logger {

	public SysOutLogger(Class clazz, int mode) {
		super(clazz, mode);
		
	}
	
	protected void out(String messageType) {
		baseOut(messageType);
		System.out.println();
	}

	protected void out(String messageType, Object out1) {
		baseOut(messageType);
		System.out.println((out1 == null ? "null" : out1.toString()));
	}

	protected void out(String mode, Object out1, Object out2) {
		baseOut(mode);
		System.out.print((out1 == null ? "null" : out1.toString()));
		System.out.print(",");
		System.out.println((out2 == null ? "null" : out2.toString()));
	}

	protected void out(String mode, Object out1, Object out2, Object out3) {
		baseOut(mode);
		System.out.print((out1 == null ? "null" : out1.toString()));
		System.out.print(",");
		System.out.println((out2 == null ? "null" : out2.toString()));
		System.out.print(",");
		System.out.println((out3 == null ? "null" : out3.toString()));
	}

	protected void baseOut(String messageType) {
		System.out.print("[");
		System.out.print(System.currentTimeMillis());
		System.out.print("] - ");
		System.out.print(Thread.currentThread().getName());
		System.out.print(" - ");
		System.out.print(messageType);
		System.out.print(" - ");
		System.out.print(className);
		System.out.print(": ");
	}

}
