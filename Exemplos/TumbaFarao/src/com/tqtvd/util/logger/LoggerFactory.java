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

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.tqtvd.util.tcp.TcpChannel;

/**
 * Classe responsavel por criar uma instancia de Logger utilizando o arquivo de
 * configuracao (logger.properties) como parametro para a criacao das classes de
 * Logger.
 * 
 * Existem dois (2) tipos possiveis de logger: SYSOUT ou TCP
 * 
 * SYSOUT: envia o log para a saida padrão do sistema. (DEFAULT) TCP: envia o
 * log para um cliente tcp.
 * 
 * Existem cinto (5) modos de atuacao do logger: DEBUG, INFO, WARN, ERROR, OFF e
 * DEV
 * 
 * DEBUG: mostra todos os logs da aplicacao. INFO: mostra os logs de
 * information, warning e error. WARN: mostra os logs de warning e error da
 * aplicacao. ERROR: mostra somente os logs de error da aplicacao. OFF: desliga
 * todos os logs. (DEFAULT) DEV: modo destinado a debug de pontos expecificos
 * durante o desenvolvimento.
 * 
 * Em caso de erro de alguma das configuracoes acima o tipo e modo default serao
 * usados: TYPE = SYSOUT, MODE = OFF
 * 
 * PS: Cuidado com espacos em branco apos o valor podem gerar erro.
 * 
 * @author fsedrez
 * 
 */
public class LoggerFactory {
	/**
	 * Variavel estatica para definir o modo de log como DEBUG;
	 */
	public static final int LOGGER_MODE_DEBUG = Logger.LOGGER_MODE_DEBUG;
	/**
	 * Variavel estatica para definir o modo de log como INFO;
	 */
	public static final int LOGGER_MODE_INFO = Logger.LOGGER_MODE_INFO;
	/**
	 * Variavel estatica para definir o modo de log como WARN;
	 */
	public static final int LOGGER_MODE_WARNING = Logger.LOGGER_MODE_WARNING;
	/**
	 * Variavel estatica para definir o modo de log como ERROR;
	 */
	public static final int LOGGER_MODE_ERROR = Logger.LOGGER_MODE_ERROR;
	/**
	 * Variavel estatica para definir o modo de log como OFF;
	 */
	public static final int LOGGER_MODE_DISABLED = Logger.LOGGER_MODE_DISABLED;
	/**
	 * Variavel estatica para definir o modo de log como DEV;
	 */
	public static final int LOGGER_MODE_DEV = Logger.LOGGER_MODE_DEV;

	/**
	 * Variavel estatica para definir o tipo de log como TCP;
	 */
	public static final int LOGGER_TYPE_TCP = 1;
	/**
	 * Variavel estatica para definir o tipo de log como SYSOUT;
	 */
	public static final int LOGGER_TYPE_SYSOUT = 0;

	private int currentLoggingMode = LOGGER_MODE_DISABLED;
	private static LoggerFactory instance = null;
	private int currentLoggingType = LOGGER_TYPE_SYSOUT;
	private String ip = "";
	private String port = "";

	/**
	 * Cria a instancia que vai cuidar da criacao das classes de logger,
	 * seguindo os parametros do logger.properties.
	 * 
	 * @return LoggerFactory
	 */
	public static LoggerFactory getInstance() {
		LoggerFactory retorno = null;
		if (instance == null) {
			retorno = new LoggerFactory();
			instance = retorno;
		} else {
			retorno = instance;
		}
		return retorno;
	}

	private LoggerFactory() {
		Properties loggerProperties = new Properties();
		String propertiesType = null;
		String propertiesMode = null;
		String propertiesIp = null;
		String propertiesPort = null;

		try {

			loggerProperties.load(new FileInputStream("logger.properties"));
			propertiesType = loggerProperties.getProperty("TYPE");
			propertiesMode = loggerProperties.getProperty("MODE");
			propertiesIp = loggerProperties.getProperty("IP");
			propertiesPort = loggerProperties.getProperty("PORT");

		} catch (IOException e) {

			propertiesType = null;
			propertiesMode = null;
			propertiesIp = null;
			propertiesPort = null;
			System.out
					.println("WARN! Não foi possível carregar o arquivo de propriedades.  Usando o TYPE SYSOUT e MODE OFF por default");
		}

		if (propertiesMode != null) {

			if (propertiesMode.equalsIgnoreCase("DEBUG")) {
				currentLoggingMode = LOGGER_MODE_DEBUG;
			} else if (propertiesMode.equalsIgnoreCase("INFO")) {
				currentLoggingMode = LOGGER_MODE_INFO;
			} else if (propertiesMode.equalsIgnoreCase("WARN")) {
				currentLoggingMode = LOGGER_MODE_WARNING;
			} else if (propertiesMode.equalsIgnoreCase("ERROR")) {
				currentLoggingMode = LOGGER_MODE_ERROR;
			} else if (propertiesMode.equalsIgnoreCase("OFF")) {
				currentLoggingMode = LOGGER_MODE_DISABLED;
			} else if (propertiesMode.equalsIgnoreCase("DEV")) {
				currentLoggingMode = LOGGER_MODE_DEV;
			} else {
				currentLoggingMode = LOGGER_MODE_DISABLED;
				System.out
						.println("WARN! Não há definição valida para o MODE. Usando o MODE OFF por default");
			}
		} else {
			currentLoggingMode = LOGGER_MODE_DISABLED;
			System.out
					.println("WARN! Não há definição para o MODE. Usando o MODE OFF por default.");

		}

		if (propertiesType != null) {
			if (propertiesType.equalsIgnoreCase("SYSOUT")) {
				currentLoggingType = LOGGER_TYPE_SYSOUT;
			} else if (propertiesType.equalsIgnoreCase("TCP")) {
				currentLoggingType = LOGGER_TYPE_TCP;
				if (propertiesIp != null) {
					ip = propertiesIp;
				} else {
					currentLoggingType = LOGGER_TYPE_SYSOUT;
					currentLoggingMode = LOGGER_MODE_DISABLED;
					System.out
							.println("WARN! Não há definição para o IP. Usando o TYPE SYSOUT e MODE OFF por default");
				}
				if (propertiesPort != null) {
					port = propertiesPort;
				} else {
					currentLoggingType = LOGGER_TYPE_SYSOUT;
					currentLoggingMode = LOGGER_MODE_DISABLED;
					System.out
							.println("WARN! Não há definição para o PORT. Usando o TYPE SYSOUT e MODE OFF por default");
				}
			} else {
				currentLoggingType = LOGGER_TYPE_SYSOUT;
				currentLoggingMode = LOGGER_MODE_DISABLED;
				System.out
						.println("WARN! Não há definição valida para o TYPE.  Usando o TYPE SYSOUT por default");
			}
		} else {
			currentLoggingType = LOGGER_TYPE_SYSOUT;
			currentLoggingMode = LOGGER_MODE_DISABLED;
			System.out
					.println("WARN! Não há definição para o TYPE.  Usando o TYPE SYSOUT por default");
		}
	}

	/**
	 * Cria uma instancia de logger para a classe solicitada.
	 * 
	 * @param clazz
	 *            classe que se deveja receber log.
	 * @return logger da classe.
	 */
	public Logger getLogger(Class clazz) {
		Logger retorno = null;

		if (clazz == null) {
			if (currentLoggingType == LOGGER_TYPE_SYSOUT) {
				retorno = new SysOutLogger(LoggerFactory.class,
						currentLoggingMode);
			} else if (currentLoggingType == LOGGER_TYPE_TCP) {
				retorno = new TcpLogger(LoggerFactory.class,
						currentLoggingMode, TcpChannel.getInstance());
			}
		} else {
			if (currentLoggingType == LOGGER_TYPE_SYSOUT) {
				retorno = new SysOutLogger(clazz, currentLoggingMode);
			} else if (currentLoggingType == LOGGER_TYPE_TCP) {
				retorno = new TcpLogger(clazz, currentLoggingMode,
						TcpChannel.getInstance());
			}
		}

		return retorno;

	}

	/**
	 * Fecha a conexao criada no log do tipo TCP;
	 */
	public void close() {
		if (currentLoggingType == LOGGER_TYPE_TCP) {
			TcpChannel.getInstance().close();
		}
	}

	/**
	 * Retorna o modo de log corrente.
	 * 
	 * @return modo de log
	 */
	public int getCurrentLoggingMode() {
		return currentLoggingMode;
	}

	/**
	 * Retorna a tipo de log corrente.
	 * 
	 * @return tipo de log
	 */
	public int getCurrentLoggingType() {
		return currentLoggingType;
	}

	/**
	 * Retorna o ip usado para fazer a conexao TCP;
	 * 
	 * @return ip usado para a conexao.
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * Retorna a porta usada para fazer a conexao TCP;
	 * 
	 * @return porta usada para a conexao.
	 */
	public String getPort() {
		return port;
	}

}