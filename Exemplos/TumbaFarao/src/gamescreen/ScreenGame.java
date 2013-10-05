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

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;

import model.Archaeologist;
import model.Game;
import model.IAnimatedAnimatedObject;
import model.MapAI;
import model.Mummy;
import ui.ImageUtil;
import ui.Sprite;
import util.Constants;
import util.KeyboardHelper;
import controller.Controller;


public class ScreenGame implements IGameScreen{
	private boolean	hasBackground;
	private Image	imgBg;
	private Image	pauseBg;
	private Image	scoreBg;
	private Image   text_level;
	private Image   text_go;
	private Image	text_completed_level;
	private Image	text_completed_game;
	
	private int x;
	private int y;
	private Sprite score;
	private Sprite score1;
	private Sprite[] bts = new Sprite[9];
	private int index = 0;
	private int[] spriteW = new int[11];
	private int[] spriteX = new int[11];
	private Sprite door;
	private Sprite archaeologist;
	private Sprite letters;
	private Image pause;
	private boolean confirm = false;
	private boolean paintMap = true;
	private Image exit;

	private final static int EXIT_X = 740;
	private final static int EXIT_Y = 669;
	private final static int PAUSE_X = 870;
	private final static int PAUSE_Y = 669; 
	
	private Color c = new Color(210, 160, 20);
	private Font defaultFont;
	private Game game;
	
	public ScreenGame(Container awtContainer, int x, int y)
	{
		this.defaultFont = new Font("Tiresias", Font.PLAIN , 16);
		
		this.hasBackground = false;
		this.imgBg = ImageUtil.getInstance().getImage("gfx/fundo_mumia.jpg");
		this.pauseBg = ImageUtil.getInstance().getImage("gfx/mumia_fundo_2.png");
		this.scoreBg = ImageUtil.getInstance().getImage("gfx/mumia_fundo_1.png");
		this.text_completed_level = ImageUtil.getInstance().getImage("gfx/mumia_txt_nivelconcluido.png");
		this.text_completed_game = ImageUtil.getInstance().getImage("gfx/mumia_txt_fimdejogo.png");
		this.archaeologist = new Sprite(awtContainer,  ImageUtil.getInstance().getImage("gfx/sprite_cacador.png"), 2, 3);
		this.x = x;
		this.y = y;
		this.pause = ImageUtil.getInstance().getImage("gfx/mumia_bt_pausar_menu.png");
		this.exit = ImageUtil.getInstance().getImage("gfx/mumia_bt_sair.png");
		
		this.text_level = ImageUtil.getInstance().getImage("gfx/mumia_txt_nivel.png");
		this.text_go = ImageUtil.getInstance().getImage("gfx/mumia_txt_vai.png");		
		
		score = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/sprite_placar_numeros_2.png"), 11, 1);
		score1 = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/sprite_placar_numeros.png"), 11, 1);
		
		letters = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/sprite_placar_letras.png"), 11, 3);
		door = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/mumia_porta.png"), 1, 3);
		
		bts[0] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_2_continuar.png"), 1, 2);
		bts[1] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_2_reiniciar.png"), 1, 2);
		bts[2] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_2_como_jogar.png"), 1, 2);
		bts[3] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_2_sair.png"), 1, 2);
		bts[4] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_1_sair_do_jogo.png"), 1, 2);
		bts[5] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_1_jogar_de_novo.png"), 1, 2);
		bts[6] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_1_proximo_nivel.png"), 1, 2);
		bts[7] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_2_sim.png"), 1, 2);
		bts[8] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_2_nao.png"), 1, 2);
		
		spriteW[0] = 28;
		spriteW[1] = 21;
		spriteW[2] = 23;
		spriteW[3] = 25;
		spriteW[4] = 32;
		spriteW[5] = 27;
		spriteW[6] = 27;
		spriteW[7] = 28;
		spriteW[8] = 27;
		spriteW[9] = 27;
		spriteW[10] = 15;

		spriteX[0] = 0;
		spriteX[1] = 28;
		spriteX[2] = 49;
		spriteX[3] = 72;
		spriteX[4] = 97;
		spriteX[5] = 129;
		spriteX[6] = 156;
		spriteX[7] = 183;
		spriteX[8] = 211;
		spriteX[9] = 238;
		spriteX[10] = 265;		
	}

	public ScreenGame(Container awtContainer, int x, int y, Game game)
	{
		this.defaultFont = new Font("Tiresias", Font.PLAIN , 16);
		
		this.hasBackground = false;
		this.imgBg = ImageUtil.getInstance().getImage("gfx/fundo_mumia.jpg");
		this.pauseBg = ImageUtil.getInstance().getImage("gfx/mumia_fundo_2.png");
		this.scoreBg = ImageUtil.getInstance().getImage("gfx/mumia_fundo_1.png");
		this.text_completed_level = ImageUtil.getInstance().getImage("gfx/mumia_txt_nivelconcluido.png");
		this.text_completed_game = ImageUtil.getInstance().getImage("gfx/mumia_txt_fimdejogo.png");
		this.archaeologist = new Sprite(awtContainer,  ImageUtil.getInstance().getImage("gfx/sprite_cacador.png"), 2, 3);
		this.x = x;
		this.y = y;
		this.pause = ImageUtil.getInstance().getImage("gfx/mumia_bt_pausar_menu.png");
		this.exit = ImageUtil.getInstance().getImage("gfx/mumia_bt_sair.png");
		
		this.text_level = ImageUtil.getInstance().getImage("gfx/mumia_txt_nivel.png");
		this.text_go = ImageUtil.getInstance().getImage("gfx/mumia_txt_vai.png");
		this.game = game;
		
		score = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/sprite_placar_numeros_2.png"), 11, 1);
		score1 = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/sprite_placar_numeros.png"), 11, 1);
		
		letters = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/sprite_placar_letras.png"), 11, 3);
		door = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/mumia_porta.png"), 1, 3);
		
		bts[0] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_2_continuar.png"), 1, 2);
		bts[1] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_2_reiniciar.png"), 1, 2);
		bts[2] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_2_como_jogar.png"), 1, 2);
		bts[3] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_2_sair.png"), 1, 2);
		bts[4] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_1_sair_do_jogo.png"), 1, 2);
		bts[5] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_1_jogar_de_novo.png"), 1, 2);
		bts[6] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_1_proximo_nivel.png"), 1, 2);
		bts[7] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_2_sim.png"), 1, 2);
		bts[8] = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/bt_2_nao.png"), 1, 2);
		
		spriteW[0] = 28;
		spriteW[1] = 21;
		spriteW[2] = 23;
		spriteW[3] = 25;
		spriteW[4] = 32;
		spriteW[5] = 27;
		spriteW[6] = 27;
		spriteW[7] = 28;
		spriteW[8] = 27;
		spriteW[9] = 27;
		spriteW[10] = 15;

		spriteX[0] = 0;
		spriteX[1] = 28;
		spriteX[2] = 49;
		spriteX[3] = 72;
		spriteX[4] = 97;
		spriteX[5] = 129;
		spriteX[6] = 156;
		spriteX[7] = 183;
		spriteX[8] = 211;
		spriteX[9] = 238;
		spriteX[10] = 265;		
	}
	public void paint(Container awtContainer)
	{
		Graphics g = awtContainer.getGraphics();	
				
		// Marca d'agua
		g.setColor(c);
		g.setFont(defaultFont);
		g.drawString("revis�o __CONST_DEPLOY_SVN_RELEASE__", 200, 675);
		
		if (!this.hasBackground)
		{
			g.setClip(0, 0, 1280, 720);
			g.drawImage(this.imgBg, 0, 0, null);
			this.hasBackground = true;
		}
		
		if(game.isGameEnd() || game.isGameOver()){
			
			g.setClip(0, 0, 1280, 720);
			
			g.drawImage(this.imgBg, 0, 0, null);					
			game.getLevel().getLevelMap().paintFullMap(awtContainer.getGraphics(), x, y);
			g.drawImage(this.scoreBg, x+265, y+148, null);
			g.drawImage(this.text_completed_game, x+372, y+160, null);			
			
			String _score = ""+game.getGameScore();

			int sx = 0;
			
			if(_score.length() == 1){
				sx = 590;
			}else if(_score.length() == 2){
				sx = 658;
			}else if(_score.length() == 3){
				sx = 599;
			}else if(_score.length() == 4){
				sx = 680;
			}else if(_score.length() == 5){
				sx = 737;
			}else if(_score.length() == 6){
				sx = 748;
			}else if(_score.length() == 7){
				sx = 769;
			}else if(_score.length() == 8){
				sx = 780;
			}
			
			if(_score.length() == 3){
				sx+=80;
			}else if(_score.length() == 4){
				sx+=20;
			}
			int j = 0;
				
			int lastW = 0;
			for(int i=_score.length()-1;i>=0;i--){
				if(j%3==0 && j!=0){
					j=0;
					score1.drawFrame(awtContainer, sx-13, 370, 10, 0);
					sx-=spriteW[10]+3;
				}
				++j;			
				String characther = ""+_score.charAt(i);
				Integer index = Integer.valueOf(characther);
				lastW = spriteW[index.intValue()];
				score1.drawFrame(awtContainer, sx-lastW, 370, index.intValue(), 0);
				sx -= spriteW[i]+13;
				
			}					
				
			if(index == 0){		
				bts[5].drawFrame(awtContainer, 462, 432, 0, 1);
				bts[4].drawFrame(awtContainer, 462, 504, 0, 0);
			}else{
				bts[5].drawFrame(awtContainer, 462, 432, 0, 0);
				bts[4].drawFrame(awtContainer, 462, 504, 0, 1);				
			}
			
			door.drawFrame(awtContainer, 560, 49, 0, 2);
			
		}else if(game.getLevel().isLevelClear()){
			
				g.setClip(0, 0, 1280, 720);
						
				g.drawImage(this.imgBg, 0, 0, null);					
				game.getLevel().getLevelMap().paintFullMap(awtContainer.getGraphics(), x, y);
				g.drawImage(this.scoreBg, 393, 208, null);
				g.drawImage(this.text_completed_level, 363, 194, null);
						
				String _score = ""+game.getGameScore();
				
				int sx = 0;
				
				if(_score.length() == 1){
					sx = 637;
				}else if(_score.length() == 2){
					sx = 664;
				}else if(_score.length() == 3){
					sx = 679;
				}else if(_score.length() == 4){
					sx = 690;
				}else if(_score.length() == 5){
					sx = 697;
				}else if(_score.length() == 6){
					sx = 708;
				}else if(_score.length() == 7){
					sx = 729;
				}else if(_score.length() == 8){
					sx = 740;
				}
				
				int j = 0;
					
				int lastW = 0;
				for(int i=_score.length()-1;i>=0;i--){
					if(j%3==0 && j!=0){
						j=0;
						score1.drawFrame(awtContainer, sx-7, 387, 10, 0);
					}
					++j;			
					String characther = ""+_score.charAt(i);
					Integer index = Integer.valueOf(characther);
					lastW = spriteW[index.intValue()];
					score1.drawFrame(awtContainer, sx-lastW, 387, index.intValue(), 0);
					sx -= spriteW[i]+5;
					
				}					
					
						
				bts[6].drawFrame(awtContainer, 452, 489, 0, 1);
				door.drawFrame(awtContainer, 560, 49, 0, 2);
				this.hasBackground = false;
		}else if(!game.isGameOver() && !game.isGamePaused()){
			
			if(paintMap){
				game.getLevel().getLevelMap().paintFullMap(awtContainer.getGraphics(), x, y);
				paintMap = false;
			}

			door.drawFrame(awtContainer, 560, 49, 0, game.getDoorState()-1 < 0 ? 0:game.getDoorState()-1);
			
			paintSpecialItens(awtContainer.getGraphics());
			if(game.isGameStarted()){
				
				paintScore(awtContainer);
				paintLifes(awtContainer);				
				Archaeologist gn = (Archaeologist) game.getArchaeologist();

				repaintBackgroundRect(awtContainer.getGraphics(), new Rectangle(gn.getLastMapIndexX()*60, gn.getLastMapIndexY()*60, 60, 60));
				repaintBackgroundRect(awtContainer.getGraphics(), new Rectangle(gn.getMapIndexX()*60, gn.getMapIndexY()*60, 60, 60));
				
				switch (gn.getNextDirection()) {
				case Constants.DIRECTION_DOWN:
					if(gn.getMapIndexY()+1 < MapAI.MAXLINE)
						repaintBackgroundRect(awtContainer.getGraphics(), new Rectangle(gn.getMapIndexX()*60, (gn.getMapIndexY()+1)*60, 60, 60));					
					break;
				case Constants.DIRECTION_UP:
					repaintBackgroundRect(awtContainer.getGraphics(), new Rectangle(gn.getMapIndexX()*60, (gn.getMapIndexY()-1)*60, 60, 60));					
					break;
				case Constants.DIRECTION_LEFT:
					if(gn.getMapIndexX()-1 > MapAI.MINCOLUMN)
						repaintBackgroundRect(awtContainer.getGraphics(), new Rectangle((gn.getMapIndexX()-1)*60, gn.getMapIndexY()*60, 60, 60));					
					break;
				case Constants.DIRECTION_RIGHT:					
					if(gn.getMapIndexX()+1 < MapAI.MAXCOLUMN)
						repaintBackgroundRect(awtContainer.getGraphics(), new Rectangle((gn.getMapIndexX()+1)*60, gn.getMapIndexY()*60, 60, 60));					
					break;					
				default:
					break;
				}

				
				if(game.getLevel().getLevelMap().getItemOnMap(gn.getMapIndexX(), gn.getMapIndexY()) == Constants.GATEWAY_MAPID){						
					g.setClip(560, 49, door.getFrameWidth(), door.getFrameHeight());
					door.drawFrame(awtContainer, 560, 49, 0, game.getDoorState()-1);
				}
				
				for(int i=0; i<=game.getMummyCount();i++){
					Mummy mummy = (Mummy) game.getMummy(i);
					
					repaintBackgroundRect(awtContainer.getGraphics(), new Rectangle(mummy.getLastMapIndexX()*60, mummy.getLastMapIndexY()*60, 60, 60));
					repaintBackgroundRect(awtContainer.getGraphics(), new Rectangle(mummy.getMapIndexX()*60, mummy.getMapIndexY()*60, 60, 60));
					
					switch (mummy.getNextDirection()) {
					case Constants.DIRECTION_DOWN:
						if(mummy.getMapIndexY()+1 < MapAI.MAXLINE)
							repaintBackgroundRect(awtContainer.getGraphics(), new Rectangle(mummy.getMapIndexX()*60, (mummy.getMapIndexY()+1)*60, 60, 60));					
						break;
					case Constants.DIRECTION_UP:
						repaintBackgroundRect(awtContainer.getGraphics(), new Rectangle(mummy.getMapIndexX()*60, (mummy.getMapIndexY()-1)*60, 60, 60));					
						break;
					case Constants.DIRECTION_LEFT:
						if(gn.getMapIndexX()-1 > MapAI.MINCOLUMN)
							repaintBackgroundRect(awtContainer.getGraphics(), new Rectangle((mummy.getMapIndexX()-1)*60, mummy.getMapIndexY()*60, 60, 60));					
						break;
					case Constants.DIRECTION_RIGHT:					
						if(gn.getMapIndexX()+1 < MapAI.MAXCOLUMN)
							repaintBackgroundRect(awtContainer.getGraphics(), new Rectangle((mummy.getMapIndexX()+1)*60, mummy.getMapIndexY()*60, 60, 60));					
						break;					
					default:
						break;
					}					
					if(game.getLevel().getLevelMap().getItemOnMap(mummy.getMapIndexX(), mummy.getMapIndexY()) == Constants.GATEWAY_MAPID){						
						g.setClip(560, 49, door.getFrameWidth(), door.getFrameHeight());
						door.drawFrame(awtContainer, 560, 49, 0, game.getDoorState()-1);
					}					
				}
		
				paintMummy(awtContainer);
				paintArchaeologist(awtContainer);
					
			}else{
				
				g.drawImage(pauseBg, 482, 277, null);
				score1.drawFrame(awtContainer, 626, 375, game.getLevel().getId(), 0);
				g.drawImage(text_level, 574, 307, null);
				
				if(game.isStartingGame()){
					g.drawImage(text_go, 589, 442, null);
					this.hasBackground = false;
					paintMap = true;					
				}
			}
			
			g.drawImage(pause, PAUSE_X, PAUSE_Y, null);
			g.drawImage(exit, EXIT_X, EXIT_Y, null);
		}else if(game.isGamePaused()){
			
			if(paintMap){
				game.getLevel().getLevelMap().paintFullMap(awtContainer.getGraphics(), x, y);
				paintMap = false;
			}
			g.setClip(560, 49, door.getFrameWidth(), door.getFrameHeight());
			door.drawFrame(awtContainer, 560, 49, 0, 0);
			
			paintScore(awtContainer);
			paintLifes(awtContainer);

			game.getArchaeologist().paint(awtContainer, x, y);			
			paintMummy(awtContainer);	
			
			if(confirm){
				int _h = 295;
				int width = 0, height = 0;
				
				width = this.pauseBg.getWidth(awtContainer);
				height = this.pauseBg.getHeight(awtContainer);
				g.setClip((1280/2)-(width/2), 278, width, height);
				g.drawImage(this.pauseBg, (1280/2)-(width/2), 278, null);				
					
				if(index == 0){
					bts[7].drawFrame(awtContainer, height, _h, 0, 1);
					bts[8].drawFrame(awtContainer, height, _h, 0, 0);
				}else{
					bts[7].drawFrame(awtContainer, height, _h, 0, 0);
					bts[8].drawFrame(awtContainer, height, _h, 0, 1);
				}
			}else{
				
				int _h = 295;
				int width = 0, height = 0;
				
				width = this.pauseBg.getWidth(awtContainer);
				height = this.pauseBg.getHeight(awtContainer);
				g.setClip((1280/2)-(width/2), 278, width, height);
				g.drawImage(this.pauseBg, (1280/2)-(width/2), 278, null);				
				for(int i=0; i<4; i++){
					if(i == index){
						bts[index].drawFrame(awtContainer, 1280/2-bts[index].getFrameWidth()/2, _h, 0, 1);
					}else{			
						bts[i].drawFrame(awtContainer, 1280/2-bts[index].getFrameWidth()/2, _h, 0, 0);
					}
					_h+=47;
				}				
		}
		
		g.drawImage(exit, EXIT_X, EXIT_Y, null);
		
		}
	}



	private void paintLifes(Container awtContainer){
		Graphics g = awtContainer.getGraphics(); 
		g.setClip(1028, 57, 57, 42);
		g.drawImage(imgBg, 0, 0 , awtContainer);
		archaeologist.drawFrame(awtContainer, 975, 57, 1, 0);
		Archaeologist archaeologist = (Archaeologist) game.getArchaeologist();
		Integer index = Integer.valueOf(""+archaeologist.getLifes());
		letters.drawFrame(awtContainer, 1020, 57, 0, 2);
		score.drawFrame(awtContainer, 1055, 57, spriteX[index.intValue()], spriteW[index.intValue()], 60);	
		
	}

	private void paintScore(Container awtContainer){
		
		Graphics g = awtContainer.getGraphics();
		String _score = ""+game.getGameScore();
		
		int width = (_score.length())*35;
		
		g.setClip(963 - width, 57, width, 50);
		g.drawImage(this.imgBg, 0, 0, awtContainer);
		
		int j = 0;
		int sx = 963;
		int lastW = 0;
		for(int i=_score.length()-1;i>=0;i--){
			if(j%3==0 && j!=0){
				j=0;
				score.drawFrame(awtContainer, sx-13, 57, spriteX[10], spriteW[10], 60);
				sx-=spriteW[10]+3;
			}
			++j;			
			String characther = ""+_score.charAt(i);
			Integer index = Integer.valueOf(characther);
			lastW = spriteW[index.intValue()];
			score.drawFrame(awtContainer, sx-lastW, 57, spriteX[index.intValue()], spriteW[index.intValue()], 60);
			sx -= spriteW[i]+7;
			
		}		
	}
	public void repaintBackgroundRect(Graphics g, Rectangle r)
	{
		
		g.setClip(188+r.x, 60+r.y, r.width, r.height);
		game.getLevel().getLevelMap().paintFrame(g, x+r.x, y+r.y, r.y/r.height, r.x/r.width);
	}
	
	private void paintMummy(Container awtContainer) {
		Graphics g = awtContainer.getGraphics();
		for(int i=0; i<=game.getMummyCount();i++){

			Mummy mummy = (Mummy) game.getMummy(i);
			
			int currentId = game.getGameMap().getItemOnMap(mummy.getMapIndexX(), mummy.getMapIndexY());
			
			if(currentId == Constants.COIN_MAPID ){
				
			}else if(currentId == Constants.MONEY_BAG_MAPID ){
				game.getLevel().getLevelMap().paintFrame(g, x+(mummy.getMapIndexX()*60), y+(mummy.getMapIndexY()*60), mummy.getMapIndexY(), mummy.getMapIndexX());
			}
			
			int lastId = game.getGameMap().getItemOnMap(mummy.getLastMapIndexX(), mummy.getLastMapIndexY());
			if(mummy.getLastMapIndexX()<MapAI.MAXCOLUMN){
				int nextId = game.getGameMap().getItemOnMap(mummy.getLastMapIndexX()+1, mummy.getLastMapIndexY());
				if(lastId == Constants.COIN_MAPID || nextId == Constants.SMOKEBOMB_MAPID){
					game.getLevel().getLevelMap().paintFrame(g, x+(mummy.getMapIndexX()*60), y+(mummy.getMapIndexY()*60), mummy.getMapIndexY(), mummy.getMapIndexX());
				}else if(lastId == Constants.MONEY_BAG_MAPID || nextId == Constants.SMOKEBOMB_MAPID){
					game.getLevel().getLevelMap().paintFrame(g, x+(mummy.getMapIndexX()*60), y+(mummy.getMapIndexY()*60), mummy.getMapIndexY(), mummy.getMapIndexX());
				}
			}else{
				int nextId = game.getGameMap().getItemOnMap(MapAI.MAXCOLUMN, mummy.getLastMapIndexY());
				if(lastId == Constants.COIN_MAPID || nextId == Constants.SMOKEBOMB_MAPID){
					game.getLevel().getLevelMap().paintFrame(g, x+(mummy.getMapIndexX()*60), y+(mummy.getMapIndexY()*60), mummy.getMapIndexY(), mummy.getMapIndexX());
				}else if(lastId == Constants.MONEY_BAG_MAPID || nextId == Constants.SMOKEBOMB_MAPID){
					game.getLevel().getLevelMap().paintFrame(g, x+(mummy.getMapIndexX()*60), y+(mummy.getMapIndexY()*60), mummy.getMapIndexY(), mummy.getMapIndexX());
				}				
			}
			
		}
		
		for(int i=0; i<=game.getMummyCount();i++){
			Mummy mummy = (Mummy) game.getMummy(i);
			mummy.paint(awtContainer, x, y);
		}
		
	}
	
	private void paintArchaeologist(Container awtContainer){	
		IAnimatedAnimatedObject archaeologist = game.getArchaeologist();
		archaeologist.paint(awtContainer, x, y);
	}


	private void paintSpecialItens(Graphics g) {
		
		for (int i=0; i<=MapAI.MAXLINE;i++){
			for (int j=0; j<=MapAI.MAXCOLUMN;j++){
				int idObj = game.getGameMap().getItemOnMap(j, i);				
				if (idObj == Constants.MONEY_BAG_MAPID || idObj == Constants.JEWELRY_MAPID || Constants.GOLD_MAPID == idObj){
					game.getLevel().getLevelMap().paintFrame(g, x+(j*60), y+(i*60), i, j);	
				}
			}
		}
		
	}

	public void keyPressed(int keyCode) {

		if(game.isGameEnd()){
			if(KeyboardHelper.isDown(keyCode) || KeyboardHelper.isUp(keyCode)){
				if(index == 1){
					index = 0;
					this.hasBackground = false;
				}else{
					index = 1;
					this.hasBackground = false;
				}
			}else if(KeyboardHelper.isOK(keyCode)){
				if(index == 0){
					Controller.startGame();
					index = 0;
				}else if(index == 1){
					Controller.goBack();
					show();
					index = 0;
				}
			}	
			
		}else if(game.isGameOver()){
			if(KeyboardHelper.isDown(keyCode) || KeyboardHelper.isUp(keyCode)){
				if(index == 1){
					index = 0;
					this.hasBackground = false;
				}else{
					index = 1;
					this.hasBackground = false;
				}
			}else if(KeyboardHelper.isOK(keyCode)){
				if(index == 0){
					this.hasBackground = false;
					game.resetAll();
					show();
					index = 0;
				}else if(index == 1){
					Controller.goBack();
					index = 0;
				}
			}				
		}else if(game.getLevel().isLevelClear()){
			if(KeyboardHelper.isOK(keyCode)){

				game.levelUp();
				game.reset();
				
				this.hasBackground = false;
				this.paintMap = true;
				index = 0;
			}			
		}else if(confirm){
			
		}else{
			if(KeyboardHelper.isDown(keyCode)){
				if(index < 3){
					++index;
				}else{
					index = 0;
				}
			}else if(KeyboardHelper.isUp(keyCode)){
				if(index > 0){
					--index;
				}else{
					index = 3;
				}
			}else if(KeyboardHelper.isOK(keyCode)){
				if(index == 0){
					this.hasBackground = false;					
					game.setGamePaused(false);
					show();
					index = 0;
				}else if(index == 1){
					game.resetAll();
					game.setGamePaused(false);
					show();
					index = 0;
					//confirm = true;
				}else if(index == 2){
					index = 0;
					this.paintMap = true;
					Controller.startAjuda();					
				}else{					
					game.resetAll();
					Controller.startMenu();
					this.paintMap = true;
					index = 0;
				}
			}		
		}
		
	}

	public void show() {
		this.hasBackground = false;		
		this.paintMap = true;
	}

	public String toString() {
		return "[ScreenGame " + super.toString() + "]";
	}

	
}

