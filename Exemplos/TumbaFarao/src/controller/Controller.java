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
package controller;

import gamescreen.IGameScreen;
import gamescreen.ScreenAjuda;
import gamescreen.ScreenGame;
import gamescreen.ScreenMenu;

import java.awt.Container;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import model.Game;

public class Controller {

	public static final int GAME_SCREEN_MENU = 0;
	public static final int GAME_SCREEN_MOVEMENT = 1;
	public static final int GAME_SCREEN_AJUDA = 2;

	private static GameLoop gameLoop;
	// private static Container awtContainer;

	private static Game game;
	private static boolean isInitialized = false;
	private static int currentScreen = 0;
	private static IGameScreen screenMovement;
	private static IGameScreen screenMenu;
	private static IGameScreen screenAjuda;
	private static int lastCurrentScreen;

	// esconder das demais classes.
	private Controller() {
	}

	public synchronized static void staticInitialization(Container container) {
		if (isInitialized)
			return;
		gameLoop = new GameLoop(container);
		game = new Game(container);
		screenMovement = new ScreenGame(container, 128, 60, game);
		screenMenu = new ScreenMenu(container, 155, 120);
		screenAjuda = new ScreenAjuda(container, 155, 120);

		container.add(gameLoop);

		isInitialized = true;
	}

	public static Game getGame() {
		return game;
	}

	public static IGameScreen getScreenMovement() {
		return screenMovement;
	}

	public static int getCurrentScreen() {
		return currentScreen;
	}

	public static IGameScreen getCurrentGameScreen() {
		switch (currentScreen) {
		case GAME_SCREEN_MENU:
			return screenMenu;
		case GAME_SCREEN_MOVEMENT:
			return screenMovement;
		case GAME_SCREEN_AJUDA:
			return screenAjuda;
		}
		return null;
	}

	public static IGameScreen getScreenMenu() {
		return screenMenu;
	}

	public static IGameScreen getScreenHelp() {
		return screenAjuda;
	}

	public static void startGame() {

		game.start();
		screenMovement.show();
		lastCurrentScreen = currentScreen;
		currentScreen = GAME_SCREEN_MOVEMENT;

	}

	public static void startAjuda() {
		lastCurrentScreen = currentScreen;
		screenAjuda.show();
		currentScreen = GAME_SCREEN_AJUDA;
	}

	public static void startMenu() {
		screenMenu.show();
		lastCurrentScreen = currentScreen;
		currentScreen = GAME_SCREEN_MENU;
	}

	public static void goBack() {
		currentScreen = lastCurrentScreen;

		if (currentScreen == GAME_SCREEN_MENU) {
			screenMenu.show();
		} else if (currentScreen == GAME_SCREEN_MOVEMENT) {
			screenMovement.show();
		} else if (currentScreen == GAME_SCREEN_AJUDA) {
			screenAjuda.show();
		}
	}

	/**
	 * Inicia o jogo, fazendo o gameloop comecar a rodar.
	 */
	public static void startApp() {
		gameLoop.startLoop();
	}

	/**
	 * Finaliza a aplicacao.
	 */
	public static void stopApp() {
		gameLoop.stopLoop();
		stickerCenterClose();
	}

	/**
	 * Tudo isso para nao poder acrescentar a lib do sticker center ao projeto, e manter compativel com a versao 1.0 do SC, na visiontec. O comando com a lib do
	 * SC seria <code>StickerContext.getInstance().close();</code>.
	 */
	private static void stickerCenterClose() {
		String classeStickerCenter = "com.tqtvd.stickercenter.impl.StickerContext";
		String metodoGetInstance = "getInstance";
		String metodoClose = "close";

		// StickerContext.
		Class stickerContext = null;
		try {
			stickerContext = Class.forName(classeStickerCenter);
		} catch (ClassNotFoundException ex) {
			System.out.println("stickerCenterClose() -> Classe nao encontrada: [" + classeStickerCenter + "]");
			System.out.println("stickerCenterClose() -> Nao iremos chamar StickerContext.getInstance().close();");
			System.out.println(ex.toString());
			return;
		}
		if (stickerContext == null) {
			System.out.println("stickerCenterClose() -> Nao ha classe para [" + classeStickerCenter + "] disponivel.");
			System.out.println("stickerCenterClose() -> Nao iremos chamar StickerContext.getInstance().close();");
			return;
		}
		// /StickerContext.

		// getInstance()
		Method getInstance = null;
		try {
			getInstance = stickerContext.getMethod(metodoGetInstance, null);
		} catch (NoSuchMethodException ex) {
			System.out.println("stickerCenterClose() -> Metodo nao encontrado: [" + metodoGetInstance + "]");
			System.out.println("stickerCenterClose() -> Nao iremos chamar StickerContext.getInstance().close();");
			System.out.println(ex.toString());
			return;
		} catch (SecurityException ex) {
			System.out.println("stickerCenterClose() -> SecurityException ao pegar o metodo [" + metodoGetInstance + "]");
			System.out.println("stickerCenterClose() -> Nao iremos chamar StickerContext.getInstance().close();");
			System.out.println(ex.toString());
			return;
		}
		if (getInstance == null) {
			System.out.println("stickerCenterClose() -> Nao ha metodo para [" + metodoGetInstance + "] disponivel.");
			System.out.println("stickerCenterClose() -> Nao iremos chamar StickerContext.getInstance().close();");
			return;
		}
		// /getInstance()

		// close()
		Method close = null;
		try {
			close = stickerContext.getMethod(metodoClose, null);
		} catch (NoSuchMethodException ex) {
			System.out.println("stickerCenterClose() -> Metodo nao encontrado: [" + metodoClose + "]");
			System.out.println("stickerCenterClose() -> Nao iremos chamar StickerContext.getInstance().close();");
			System.out.println(ex.toString());
			return;
		} catch (SecurityException ex) {
			System.out.println("stickerCenterClose() -> SecurityException ao pegar o metodo [" + metodoClose + "]");
			System.out.println("stickerCenterClose() -> Nao iremos chamar StickerContext.getInstance().close();");
			System.out.println(ex.toString());
			return;
		}
		if (close == null) {
			System.out.println("stickerCenterClose() -> Nao ha metodo para [" + metodoClose + "] disponivel.");
			System.out.println("stickerCenterClose() -> Nao iremos chamar StickerContext.getInstance().close();");
			return;
		}
		// /close()

		// retorno de getInstance()
		Object instance = null;
		try {
			instance = getInstance.invoke(null, null);
		} catch (IllegalArgumentException ex) {
			System.out.println("stickerCenterClose() -> IllegalArgumentException ao chamar [" + metodoGetInstance + "]");
			System.out.println("stickerCenterClose() -> Nao iremos chamar StickerContext.getInstance().close();");
			System.out.println(ex.toString());
			return;
		} catch (IllegalAccessException ex) {
			System.out.println("stickerCenterClose() -> IllegalAccessException ao chamar [" + metodoGetInstance + "]");
			System.out.println("stickerCenterClose() -> Nao iremos chamar StickerContext.getInstance().close();");
			System.out.println(ex.toString());
			return;
		} catch (InvocationTargetException ex) {
			System.out.println("stickerCenterClose() -> InvocationTargetException ao chamar [" + metodoGetInstance + "]");
			System.out.println("stickerCenterClose() -> Nao iremos chamar StickerContext.getInstance().close();");
			System.out.println(ex.toString());
			return;
		}
		if (instance == null) {
			System.out.println("stickerCenterClose() -> Nao ha instancia do objeto StickerCenterContext disponivel.");
			System.out.println("stickerCenterClose() -> Nao iremos chamar StickerContext.getInstance().close();");
			return;
		}
		// /retorno de getInstance()

		// Invocando: StickerContext.getInstance().close();
		try {
			System.out.println("StickerContext.getInstance().close(); -> Invocando...");
			close.invoke(instance, null);
			System.out.println("StickerContext.getInstance().close(); -> Invocado com sucesso!");
		} catch (IllegalArgumentException ex) {
			System.out.println("stickerCenterClose() -> IllegalArgumentException ao invocar StickerContext.getInstance().close().");
			System.out.println(ex.toString());
		} catch (IllegalAccessException ex) {
			System.out.println("stickerCenterClose() -> IllegalAccessException ao invocar StickerContext.getInstance().close().");
			System.out.println(ex.toString());
		} catch (InvocationTargetException ex) {
			System.out.println("stickerCenterClose() -> InvocationTargetException ao invocar StickerContext.getInstance().close().");
			System.out.println(ex.toString());
		}
		// /Invocando: StickerContext.getInstance().close();
	}

}
