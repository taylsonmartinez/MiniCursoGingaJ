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
package model;

import java.awt.Container;
import ui.Sprite;
import util.Constants;

public class Archaeologist extends AnimatedGameObject implements IAnimatedAnimatedObject{

	private int 					currentState = 0;
	private long    				animation = 20;
	private long 					lastTimeRun = 0;
	private int     				currentAnimationStep = 0;
	private int     				lastTimeRanArchaeologistDown = 100;
	private IGameEventListener		gameEventListener;
	private IDirectionListener[]	directionListener;
	private int 					lifes;

	public Archaeologist(Sprite objectSprite, IGameEventListener gameEventListener, IDirectionListener[]	directionListener, int lifes){
		
		super("Archaeologist", Constants.ARCHAEOLOGIST_MAPID, false, 0, 0, 0, 
				objectSprite);
		this.lifes = lifes;
		this.gameEventListener = gameEventListener;
		this.directionListener = directionListener;
	}

	
	public int getLifes() {
		return lifes;
	}


	public void setLifes(int lifes) {
		this.lifes = lifes;
	}


	public void paint(Container awtContainer, int x, int y) {

		if(currentState == Constants.ARCHAEOLOGIST_NORMAL){
			if(getNextDirection() == Constants.DIRECTION_LEFT || getNextDirection() == Constants.DIRECTION_UP){
				getObjectSprite().drawFrame(awtContainer, x+getX(), y+getY(), currentAnimationStep, 0);
				
			}else if(getNextDirection() == Constants.DIRECTION_RIGHT || getNextDirection() == Constants.DIRECTION_DOWN){
				getObjectSprite().drawFrame(awtContainer, x+getX(), y+getY(), currentAnimationStep, 1);
			}else{
				getObjectSprite().drawFrame(awtContainer, x+getX(), y+getY(), currentAnimationStep, 1);
			}
		}else if(currentState == Constants.ARCHAEOLOGIST_DIZZY){			
			getObjectSprite().drawFrame(awtContainer, x+getX(), y+getY(), currentAnimationStep, 2);

		}
	}
	
	public boolean move() {		

		boolean move = false;
		
		if(getX() < 0 || getY() < 0 || getX() > MapAI.MAXCOLUMN*60 || getY() > MapAI.MAXLINE*60){
			move = false;
		}else{
			if(getScheduledMoving() != Constants.DIRECTION_NONE && getX()%60 == 0 && getY()%60 == 0){
				if(canMove(getScheduledMoving())){
					setPrevDirection(getScheduledMoving());
					setNextDirection(getScheduledMoving());
					setScheduledMoving(Constants.DIRECTION_NONE);
					move = true;
				}else if(canMove(getNextDirection())){
					setPrevDirection(getNextDirection());
					move = true;
				}
			}else{
				if(canMove(getNextDirection())){
					setPrevDirection(getNextDirection());
					move = true;
				}else if(canMove(getPrevDirection())){
					setPrevDirection(getPrevDirection());
					move = true;
				}
			}

			int mapObj = level.getLevelMap().getPointOnMap(getMapIndexX(), getMapIndexY());

			if(mapObj == Constants.GATEWAY_MAPID && level.isLevelEnd()){
				System.out.println("ENDED");
				gameEventListener.notifyEvent(this, Game.LEVEL_END);
			}			
		}
		
		if(move) consumeItem();
		return move;
	}

	private void consumeItem(){
		
		int itemId = level.getLevelMap().getItemOnMap(getMapIndexX(), getMapIndexY());
		switch (itemId) {
			case Constants.COIN_MAPID:
				level.getLevelMap().setItemOnMap(getMapIndexX(), getMapIndexY(),Constants.EMPTY_MONEY_MAPID);
				level.setCoinsLeft(level.getCoinsLeft() - 1);
				gameEventListener.notifyItemComsumed(this, Game.ITEM_CONSUMED, Constants.COIN_MAPID);
				break;
			case Constants.GOLD_MAPID:
				level.removeSpecialItem();
				gameEventListener.notifyItemComsumed(this, Game.ITEM_CONSUMED, Constants.GOLD_MAPID);
				break;
			case Constants.JEWELRY_MAPID:
				level.removeSpecialItem();
				gameEventListener.notifyItemComsumed(this, Game.ITEM_CONSUMED, Constants.JEWELRY_MAPID);
				break;
			case Constants.MONEY_BAG_MAPID:
				gameEventListener.notifyItemComsumed(this, Game.ITEM_CONSUMED, Constants.MONEY_BAG_MAPID);
				level.removeSpecialItem();
				break;
			case Constants.SMOKEBOMB_MAPID:
				gameEventListener.notifyItemComsumed(this, Game.ITEM_CONSUMED, Constants.SMOKEBOMB_MAPID);
				level.getLevelMap().setItemOnMap(getMapIndexX(), getMapIndexY(),Constants.EMPTY_MONEY_MAPID);
				break;
	
			default:
				// System.out.println("Unknow item! Discarding ...");
				break;
		}		
	}
	private boolean canMove(int nextDirection) {
		
		boolean move = false;

		switch (nextDirection) {
		case Constants.DIRECTION_DOWN:
			
			if(level.getLevelMap().canGoDown(getMapIndexX(), getMapIndexY())){
				moveDown(0, 10);
				move = true;
			}
			break;
		case Constants.DIRECTION_UP:
			if(level.getLevelMap().canGoUp(getMapIndexX(), getMapIndexY())){
		
				if(level.getLevelMap().getPointOnMap(getMapIndexX(), getMapIndexY()-1) != Constants.GATEWAY_MAPID){
					moveUp(0, -10);
					move = true;
				}else{
					if(level.isLevelEnd()){
						moveUp(0, -10);
						move = true;
					}else{
						move=false;
					}
				}				
			}			
			break;
		case Constants.DIRECTION_LEFT:
			if(level.getLevelMap().canGoLeft(getMapIndexX(), getMapIndexY())){
				//Caso especial. Esse item � uma porta especial do map. De acordo com a regra do jogo,
				//o arqueologo deve sair na porta especial a direita.

				if(level.getLevelMap().getPointOnMap(getMapIndexX()-1, getMapIndexY()) == Constants.SPECIAL_DOOR_LEFT_MAPID){								
					setX((MapAI.MAXCOLUMN-1)*60);
				}else{				
					moveLeft(-10, 0);
				}
				move = true;
			}			
			break;
		case Constants.DIRECTION_RIGHT:
			if(level.getLevelMap().canGoRight(getMapIndexX(), getMapIndexY())){
				//Caso especial. Esse item � uma porta especial do map. De acordo com a regra do jogo,
				//o arqueologo deve sair na porta especial a direita.
				if(level.getLevelMap().getPointOnMap(getMapIndexX()+1, getMapIndexY()) == Constants.SPECIAL_DOOR_RIGHT_MAPID){								
					setX((MapAI.MINCOLUMN+1)*60);
				}else{
					moveRight(10, 0);
				}
				move = true;
			}			
			break;			
		default:
			break;
		}		
		
		return move;
	}


	public void changeState(int stateId) {
		currentState = stateId;		
	}

	public boolean update(long nowMiliSeconds) {
		if(currentState == Constants.ARCHAEOLOGIST_NORMAL){
			if(nowMiliSeconds - lastTimeRun > animation){
				if(currentAnimationStep == 1){
					currentAnimationStep = 0;
				}else{
					currentAnimationStep = 1;
				}
			}
			return true;
		}else if(currentState == Constants.ARCHAEOLOGIST_DIZZY){
			
			if(nowMiliSeconds - lastTimeRun > 3000){
				reset();
				gameEventListener.notifyEvent(this, Constants.ARCHEOLOGIST_DEAD);
				currentState = 0;
			}else{
				if(nowMiliSeconds - lastTimeRanArchaeologistDown > 100){
					if(currentAnimationStep == 1){
						currentAnimationStep = 0;
					}else{
						currentAnimationStep = 1;
					}
				}
			}
			return true;
		}		
		return false;	
	}

	public void start(long nowMiliseconds) {
		lastTimeRun = System.currentTimeMillis();
		currentAnimationStep = 0;
		lastTimeRanArchaeologistDown = 0;
	}

	public int getCurrentState() {
		return currentState;
	}


	public void changeDirection(int direction) {
		if((getPrevDirection() == Constants.DIRECTION_DOWN || getPrevDirection() == Constants.DIRECTION_UP) && 
		   (direction == Constants.DIRECTION_LEFT || direction == Constants.DIRECTION_RIGHT)){
			for(int i=0; i<directionListener.length;i++){
				directionListener[i].notifyDirectionChanged(this);
			}
		}
		setNextDirection(direction);		
	}


	public void scheduleMovement(int movement) {
		setScheduledMoving(movement);
	}	
	
}
