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

public abstract class Logger {

	public static final int LOGGER_MODE_DEBUG = 5;
	public static final int LOGGER_MODE_INFO = 4;
	public static final int LOGGER_MODE_WARNING = 3;
	public static final int LOGGER_MODE_ERROR = 2;
	public static final int LOGGER_MODE_DISABLED = 1;
	public static final int LOGGER_MODE_DEV = 0;

	private int mode;
	protected String className;

	public Logger(Class clazz, int mode) {
		this.mode = mode;

		this.className = clazz.getName();

	}

	public void debug() {
		if (mode >= LOGGER_MODE_DEBUG)
			out("DEBUG");
	}

	public void debug(Object out) {
		if (mode >= LOGGER_MODE_DEBUG)
			out("DEBUG", out);
	}

	public void debug(Object out1, Object out2) {
		if (mode >= LOGGER_MODE_DEBUG)
			out("DEBUG", out1, out2);
	}

	public void debug(Object out1, Object out2, Object out3) {
		if (mode >= LOGGER_MODE_DEBUG)
			out("DEBUG", out1, out2, out3);
	}

	public void info() {
		if (mode >= LOGGER_MODE_INFO)
			out("INFO");
	}

	public void info(Object out) {
		if (mode >= LOGGER_MODE_INFO)
			out("INFO", out);
	}

	public void info(Object out1, Object out2) {
		if (mode >= LOGGER_MODE_INFO)
			out("INFO", out1, out2);
	}

	public void info(Object out1, Object out2, Object out3) {
		if (mode >= LOGGER_MODE_INFO)
			out("INFO", out1, out2, out3);
	}

	public void warn() {
		if (mode >= LOGGER_MODE_WARNING)
			out("WARNING");
	}

	public void warn(Object out) {
		if (mode >= LOGGER_MODE_WARNING)
			out("WARNING", out);
	}

	public void warn(Object out1, Object out2) {
		if (mode >= LOGGER_MODE_WARNING)
			out("WARNING", out1, out2);
	}

	public void warn(Object out1, Object out2, Object out3) {
		if (mode >= LOGGER_MODE_WARNING)
			out("WARNING", out1, out2, out3);
	}

	public void error() {
		if (mode >= LOGGER_MODE_ERROR)
			out("ERROR");
	}

	public void error(Object out) {
		if (mode >= LOGGER_MODE_ERROR)
			out("ERROR", out);
	}

	public void error(Object out1, Object out2) {
		if (mode >= LOGGER_MODE_ERROR)
			out("ERROR", out1, out2);
	}

	public void error(Object out1, Object out2, Object out3) {
		if (mode >= LOGGER_MODE_ERROR)
			out("ERROR", out1, out2, out3);
	}

	public void dev() {
		if (mode == LOGGER_MODE_DEV)
			out("DEV");
	}

	public void dev(Object out) {
		if (mode == LOGGER_MODE_DEV)
			out("DEV", out);
	}

	public void dev(Object out1, Object out2) {
		if (mode == LOGGER_MODE_DEV)
			out("DEV", out1, out2);
	}

	public void dev(Object out1, Object out2, Object out3) {
		if (mode == LOGGER_MODE_DEV)
			out("DEV", out1, out2, out3);
	}

	protected abstract void out(String messageType);

	protected abstract void out(String messageType, Object out1);
	
	protected abstract void out(String mode, Object out1, Object out2);

	protected abstract void out(String mode, Object out1, Object out2, Object out3);

	protected abstract void baseOut(String messageType);
}