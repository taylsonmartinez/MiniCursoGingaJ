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
package gamescreen;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import ui.ImageUtil;
import ui.Sprite;
import util.KeyboardHelper;
import controller.Controller;

public class ScreenAjuda implements IGameScreen {
	private Image imgBgHelp;
	private Image imgBg;
	private Sprite door;
	private int x = 0;
	private int y = 0;
	private boolean hasBackground = false;

	public ScreenAjuda(Container awtContainer, int x, int y) {
		hasBackground = false;
		door = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/mumia_porta.png"), 1, 3);
		this.imgBgHelp = ImageUtil.getInstance().getImage("gfx/mumia_tela_como_jogar.png");
		this.imgBg = ImageUtil.getInstance().getImage("gfx/fundo_mumia.jpg");
		this.x = x;
		this.y = y;

	}

	public void paint(Container awtContainer) {
		Graphics g = awtContainer.getGraphics();

		if (!hasBackground) {
			g.setClip(0, 0, 1280, 720);
			g.drawImage(this.imgBg, 0, 0, null);
			door.drawFrame(awtContainer, 560, 49, 0, 0);
			g.drawImage(this.imgBgHelp, x, y, null);
			hasBackground = true;
		}

	}

	public void repaintBackgroundRect(Graphics g, Rectangle rect) {
		// TODO Auto-generated method stub

	}

	public void keyPressed(int keyCode) {

		if (KeyboardHelper.isOK(keyCode)) {
			Controller.goBack();
		}
	}

	public void show() {
		this.hasBackground = false;

	}

	public String toString() {
		return "[ScreenAjuda " + super.toString() + "]";
	}

}
