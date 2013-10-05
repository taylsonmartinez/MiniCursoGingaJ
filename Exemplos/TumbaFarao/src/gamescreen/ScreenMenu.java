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

public class ScreenMenu implements IGameScreen{

	private Image	imgBgMenu;
	private Image	imgBg;
	private int x = 0;
	private int y = 0;
	private int index = 0;
	private boolean	hasBackground = false;
	private Sprite door;
	private Image exit;
	
	private Sprite[] bts = new Sprite[2];

	
	public ScreenMenu(Container awtContainer, int x, int y){		
		door = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/mumia_porta.png"), 1, 3);
		exit = ImageUtil.getInstance().getImage("gfx/mumia_bt_sair.png");
		this.imgBgMenu = ImageUtil.getInstance().getImage("gfx/mumia_tela_menu_inicial.png");
		this.imgBg = ImageUtil.getInstance().getImage("gfx/fundo_mumia.jpg");
		bts[0] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_1_iniciar_partida.png"), 1, 2);
		bts[1] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_1_como_jogar.png"), 1, 2);
		
		this.x = x;
		this.y = y;
	}

	public void paint(Container awtContainer) {		
		
		Graphics g = awtContainer.getGraphics();
		
		if(!hasBackground){
			g.setClip(0, 0, 1280, 720);			
			g.drawImage(this.imgBg, 0, 0, null);
			
			door.drawFrame(awtContainer, 560, 49, 0, 0);			
		
			g.drawImage(this.imgBgMenu, x, y, null);
		
			int _h = 320;
			for(int i=0; i<2; i++){
	
				if(i == index){
					bts[index].drawFrame(awtContainer, 686, _h, 0, 1);
				}else{			
					bts[i].drawFrame(awtContainer, 686, _h, 0, 0);
				}
				_h+=43;
			}
			g.drawImage(exit, 980, 669, null);
			hasBackground = true;
		}
		
	}

	public void repaintBackgroundRect(Graphics g, Rectangle rect) {
		// TODO Auto-generated method stub
		
	}
	
	public void keyPressed(int keyCode){
		
		if(KeyboardHelper.isDown(keyCode)){
			if(index == 0){
				index = 1;
			}else{
				index = 0;
			}
			hasBackground = false;
		}else if(KeyboardHelper.isUp(keyCode)){
			if(index == 0){
				index = 1;
			}else{
				index = 0;
			}
			hasBackground = false;
		}else if(KeyboardHelper.isOK(keyCode)){
			if(index == 0){				
				Controller.startGame();
				index = 0;
			}
			else if(index == 1){
				index = 0;
				Controller.startAjuda();
			}
		}
	}

	public void show() {
		this.hasBackground = false;
		
	}

	public String toString() {
		return "[ScreenMenu " + super.toString() + "]";
	}
	
}
