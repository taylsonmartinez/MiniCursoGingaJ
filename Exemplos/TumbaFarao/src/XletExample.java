import java.awt.Color;
import java.awt.Container;

import javax.microedition.xlet.UnavailableContainerException;
import javax.microedition.xlet.Xlet;
import javax.microedition.xlet.XletContext;
import javax.microedition.xlet.XletStateChangeException;

import ui.ImageUtil;

import com.sun.dtv.ui.event.KeyEvent;
import com.sun.dtv.ui.event.RemoteControlEvent;
import com.sun.dtv.ui.event.UserInputEvent;
import com.sun.dtv.ui.event.UserInputEventListener;
import com.sun.dtv.ui.event.UserInputEventManager;

import controller.Controller;

public class XletExample implements Xlet, UserInputEventListener {

	private XletContext context;
	private Container container;	

	/**
	 * Interface <code>Xlet</code>.
	 */
	public void pauseXlet() {
		System.out.println("pauseXlet begin");
		// do nothing
		System.out.println("pauseXlet end");
	}

	/**
	 * Interface <code>Xlet</code>.
	 */
	public void destroyXlet(boolean unconditional) throws XletStateChangeException {
		System.out.println("destroyXlet begin");
		this.context.notifyDestroyed();
		System.out.println("destroyXlet end");
	}

	/**
	 * Interface <code>Xlet</code>.
	 */
	public void initXlet(XletContext context) throws XletStateChangeException {
		System.out.println("initXlet begin");		
		this.context = context;
		this.container = null;
		try {
			this.container = context.getContainer();
		} catch (UnavailableContainerException e) {
			this.container = null;
			e.printStackTrace();
		}
		ImageUtil.createInstance(container);
		Controller.staticInitialization(container);
		registerUserInputEventListener();

		System.out.println("initXlet end");
	}

	/**
	 * Interface <code>Xlet</code>.
	 */
	public void startXlet() throws XletStateChangeException {
		System.out.println("startXlet begin");			
		
		if (this.container == null)
		{
			System.out.println("Nao ha um awtContainer disponivel para rodar o jogo. Estamos rodando sem interface grafica ?");
			return;
		}
		container.setBackground(Color.BLUE);
		container.setSize(1280, 720);
		container.setLocation(0, 0);
		container.setVisible(true);

		Controller.startApp();

		System.out.println("startXlet end");
	}

	/**
	 * Interface <code>UserInputEventListener</code>.
	 */
	public void userInputEventReceived(UserInputEvent event) {
		if (event instanceof KeyEvent) {
			KeyEvent key = (KeyEvent) event;

			// Tecla vermelha tem ação global, em qualquer ponto do jogo.
			if (key.getKeyCode() == RemoteControlEvent.VK_COLORED_KEY_0)
			{
				System.out.println("GameXlet:userInputEventReceived() -> RED PRESSED! Finalizando aplicacao.");
				Controller.stopApp();
				return;
			}
			
			
			// Repasse da tecla para a tela corrente, sendo que a GameScreen e um caso especial.
			if (Controller.getCurrentScreen() == Controller.GAME_SCREEN_MOVEMENT)
				Controller.getGame().keyPressed(key.getKeyCode());
			else
				Controller.getCurrentGameScreen().keyPressed(key.getKeyCode());

		}
	}

	/**
	 * Registra esta classe como listener de teclas.
	 */
	private void registerUserInputEventListener() {
		UserInputEventManager inputManager = UserInputEventManager.getUserInputEventManager(com.sun.dtv.ui.Screen.getCurrentScreen());

		KeyEvent event__left = new KeyEvent(null, KeyEvent.KEY_PRESSED, 0, 0, RemoteControlEvent.VK_BALANCE_LEFT, 'l');
		KeyEvent event_right = new KeyEvent(null, KeyEvent.KEY_PRESSED, 0, 0, RemoteControlEvent.VK_BALANCE_RIGHT, 'r');
		KeyEvent event____up = new KeyEvent(null, KeyEvent.KEY_PRESSED, 0, 0, RemoteControlEvent.VK_BASS_UP, 'u');
		KeyEvent event__down = new KeyEvent(null, KeyEvent.KEY_PRESSED, 0, 0, RemoteControlEvent.VK_BASS_DOWN, 'd');
		KeyEvent event_enter = new KeyEvent(null, KeyEvent.KEY_PRESSED, 0, 0, RemoteControlEvent.VK_CONFIRM, 'e');
		KeyEvent event___red = new KeyEvent(null, KeyEvent.KEY_PRESSED, 0, 0, RemoteControlEvent.VK_COLORED_KEY_0, 'x');
		KeyEvent event_green = new KeyEvent(null, KeyEvent.KEY_PRESSED, 0, 0, RemoteControlEvent.VK_COLORED_KEY_1, 'g');

		KeyEvent event__left1 = new KeyEvent(null, KeyEvent.KEY_PRESSED, 0, 0, 37, 'l');
		KeyEvent event_right1 = new KeyEvent(null, KeyEvent.KEY_PRESSED, 0, 0, 39, 'r');
		KeyEvent event____up1 = new KeyEvent(null, KeyEvent.KEY_PRESSED, 0, 0, 38, 'u');
		KeyEvent event__down1 = new KeyEvent(null, KeyEvent.KEY_PRESSED, 0, 0, 40, 'd');
		KeyEvent event_enter1 = new KeyEvent(null, KeyEvent.KEY_PRESSED, 0, 0, 80, 'e');
		KeyEvent event_green1 = new KeyEvent(null, KeyEvent.KEY_PRESSED, 0, 0, 34, 'g');

		inputManager.addUserInputEventListener(this, (UserInputEvent) event__left);
		inputManager.addUserInputEventListener(this, (UserInputEvent) event_right);
		inputManager.addUserInputEventListener(this, (UserInputEvent) event____up);
		inputManager.addUserInputEventListener(this, (UserInputEvent) event__down);
		inputManager.addUserInputEventListener(this, (UserInputEvent) event_enter);
		inputManager.addUserInputEventListener(this, (UserInputEvent) event___red);
		inputManager.addUserInputEventListener(this, (UserInputEvent) event_green);

		inputManager.addUserInputEventListener(this, (UserInputEvent) event__left1);
		inputManager.addUserInputEventListener(this, (UserInputEvent) event_right1);
		inputManager.addUserInputEventListener(this, (UserInputEvent) event____up1);
		inputManager.addUserInputEventListener(this, (UserInputEvent) event__down1);
		inputManager.addUserInputEventListener(this, (UserInputEvent) event_enter1);
		inputManager.addUserInputEventListener(this, (UserInputEvent) event_green1);
	}
}
