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

import com.tqtvd.util.tcp.TcpChannel;

public class TcpLogger extends Logger {

	private TcpChannel channel = null;

	private TcpLogger(Class clazz, int mode) {
		super(clazz, mode);

	}

	public TcpLogger(Class clazz, int mode, TcpChannel channel) {
		super(clazz, mode);
		this.channel = channel;
	}

	protected void out(String messageType) {
		baseOut(messageType);
		sendln();
	}

	protected void out(String messageType, Object out1) {
		baseOut(messageType);
		sendln((out1 == null ? "null" : out1.toString()));
	}

	protected void out(String mode, Object out1, Object out2) {
		baseOut(mode);
		send((out1 == null ? "null" : out1.toString()));
		send(",");
		sendln((out2 == null ? "null" : out2.toString()));
	}

	protected void out(String mode, Object out1, Object out2, Object out3) {
		baseOut(mode);
		send((out1 == null ? "null" : out1.toString()));
		send(",");
		sendln((out2 == null ? "null" : out2.toString()));
		send(",");
		sendln((out3 == null ? "null" : out3.toString()));
	}

	protected void baseOut(String messageType) {
		long ctms = System.currentTimeMillis();
		Long l = new Long(ctms);
		send("[");
		send(l.toString());
		send("] - ");
		send(Thread.currentThread().getName());
		send(" - ");
		send(messageType);
		send(" - ");
		send(className);
		send(": ");
	}

	private void send(String toSend) {
		if (toSend == null)
			toSend = "null";

		if (channel.isAlive()) {
			channel.getOut().print(toSend);
		} else {
			System.out.println("WARN! TcpLogger:(NOT ALIVE): " + toSend);
		}
	}

	private void sendln(String toSend) {
		if (toSend == null)
			toSend = "null";

		if (channel.isAlive()) {
			channel.getOut().println(toSend);
		} else {
			System.out.println("WARN! TcpLogger:(NOT ALIVE): " + toSend);
		}
	}

	private void sendln() {
		if (channel.isAlive()) {
			channel.getOut().println();
		} else {
			System.out.println("WARN! TcpLogger:(NOT ALIVE): ");
		}
	}

}
