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
package ui;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;

public class Sprite {
	private int frameW;
	private int frameH;
	private Image img;

	private int totalFrames;
	private int framesX;

	// private int framesY;

	public Sprite(Container awtContainer, Image img, int numFramesX, int numFramesY) {
		this.frameW = img.getWidth(awtContainer) / numFramesX;
		this.frameH = img.getHeight(awtContainer) / numFramesY;
		this.img = img;
		this.totalFrames = numFramesX * numFramesY;
		this.framesX = numFramesX;
		// this.framesY = numFramesY;
	}

	public Sprite(Container awtContainer, Image img) {
		this(awtContainer, img, 0, 0);
	}

	public int getTotalFrames() {
		return this.totalFrames;
	}

	public int getFrameWidth() {
		return this.frameW;
	}

	public int getFrameHeight() {
		return this.frameH;
	}

	public void drawFrame(Container awtContainer, int x, int y, int frameIndexX, int frameIndexY) {
		Graphics g = awtContainer.getGraphics();
		g.setClip(x, y, frameW, frameH);
		// g.drawImage(img, x - (frameIndexX * frameW), y - (frameIndexY * frameH), awtContainer);
		g.drawImage(img, x - (frameIndexX * frameW), y - (frameIndexY * frameH), null);
	}

	public void drawFrame(Container awtContainer, int x, int y, int spriteX, int spriteWidth, int spriteHeight) {
		Graphics g = awtContainer.getGraphics();
		g.setClip(x, y, spriteWidth, spriteHeight);
		// g.drawImage(img, x-spriteX, y, awtContainer);
		g.drawImage(img, x - spriteX, y, null);

	}

	public void drawFrame(Graphics g, int x, int y, int frameIndex) {
		g.setClip(x, y, frameW, frameH);

		int coluna = frameIndex % this.framesX;
		int linha = frameIndex / this.framesX;

		g.drawImage(img, x - (coluna * frameW), y - (linha * frameH), null);
	}

}
