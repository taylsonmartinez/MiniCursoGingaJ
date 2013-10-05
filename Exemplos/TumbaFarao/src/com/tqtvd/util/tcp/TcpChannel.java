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
package com.tqtvd.util.tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.tqtvd.util.logger.LoggerFactory;

public class TcpChannel {

	private boolean isAlive;
	private Socket socket;
	private PrintWriter out;
	private BufferedReader in;
	private static TcpChannel instance = null;

	public static TcpChannel getInstance() {
		TcpChannel retorno = null;
		if (instance == null) {
			try {
				retorno = new TcpChannel(LoggerFactory.getInstance().getIp(),
						LoggerFactory.getInstance().getPort());
			} catch (IOException e) {
				e.printStackTrace();
			}
			instance = retorno;
		} else {
			retorno = instance;
		}
		return retorno;
	}

	private TcpChannel(String server, String port) throws IOException {
		isAlive = false;
		socket = null;
		out = null;
		in = null;

		int portNumber;
		try {
			portNumber = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			portNumber = 4444;
		}

		try {
			socket = new Socket(server, portNumber);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			isAlive = true;
		} catch (UnknownHostException e) {
			System.out
					.println("TcpLogger UnknownHostException trying to open ["
							+ server + ":" + port + "]");
			e.printStackTrace();
			socket = null;
			throw e;
		} catch (IOException e) {
			System.out.println("TcpLogger IOException trying to open ["
					+ server + ":" + port + "]");
			e.printStackTrace();
			socket = null;
			throw e;
		}
	}

	public void close() {
		try {
			out.close();
		} catch (Exception e) {
		}// glup

		try {
			in.close();
		} catch (Exception e) {
		}// glup

		try {
			socket.close();
		} catch (Exception e) {
		}// glup

		isAlive = false;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public PrintWriter getOut() {
		return out;
	}

}
