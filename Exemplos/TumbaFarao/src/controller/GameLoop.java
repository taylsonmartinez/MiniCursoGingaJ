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

import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.PaintEvent;

/**
 * Faz o controle do gameloop, chamando o paint da tela corrente adequadamente. S� deve ser usado dentro do pacote <code>controller</code>.
 * 
 * @author jmachado
 */
public class GameLoop extends Component {

	private static final long serialVersionUID = -2791712318295230911L; // remocao de warnings no eclipse.

	private Object loopMutex;
	private boolean runLoop;
	private IGameScreen gameScreen;
	private boolean mustPaint = true;
	private long lastTime = 0;

	private PaintEvent p = null;

	private Container container = null;

	protected GameLoop(Container container) {
		System.out.println("AnimationAwtComponent begin");

		this.loopMutex = new Object();
		this.container = container;
		this.runLoop = true;
		super.setSize(1280, 720);
		super.setLocation(0, 0);
		super.setEnabled(true);
		super.setVisible(true);
		super.setName("AnimationAwtComponent");
		p = new PaintEvent(this, PaintEvent.PAINT, new Rectangle(0, 0, 1280, 720));
		System.out.println("AnimationAwtComponent end");
	}

	/**
	 * Inicia o loop do jogo. Thread-safe.
	 */
	protected void startLoop() {
		synchronized (loopMutex) {
			runLoop = true;
			dispatchPaintEvent("startLoop");
		}
	}

	/**
	 * Finaliza o loop do jogo. Thread-safe.
	 */
	protected void stopLoop() {
		synchronized (loopMutex) {
			runLoop = false;
		}
	}

	public void paint(final Graphics g) {
		// System.out.println("paint " + this.gameScreen);
		this.gameScreen = Controller.getCurrentGameScreen();

		if (Controller.getCurrentScreen() == Controller.GAME_SCREEN_MOVEMENT) {

			mustPaint = mustPaint || Controller.getGame().update(System.currentTimeMillis());

			long now = System.currentTimeMillis();
			if (now - lastTime >= Controller.getGame().getLevel().getLevelSpeed() && mustPaint) {
				Controller.getScreenMovement().paint(container);
				lastTime = System.currentTimeMillis();
				mustPaint = false;
			}

		}

		this.gameScreen.paint(container);

		synchronized (loopMutex) {
			if (runLoop) {
				dispatchPaintEvent("paint");
			} else {
				System.out.println("GameLoop/paint() -> loop parado.");
			}
		}
		// System.out.println("/paint");
	}

	/**
	 * Dispara um novo evento de paint, para continuar o loop.
	 */
	private void dispatchPaintEvent(String from) {
		// System.out.println("GameLoop::dispatchPaintEvent(" + (from == null ? "null" : "\"" + from + "\"") + ")");
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(p);
	}

}
