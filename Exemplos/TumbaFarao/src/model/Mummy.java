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
import controller.Controller;

public class Mummy extends SmartAnimatedGameObject implements IAnimatedAnimatedObject, IDirectionListener{
	


	private int		  currentState = 0;
	private long 	  lastTimeRan = 0;
	private int       currentAnimationStep = 0;
	private int		  policeScaredRunningStep = 0;
	private int		  policeScaredBombStep = 0;
	private long	  nextTimeToMove = 0;
	private long 	  timeToUpdatePathFinder;
	private long 	  lastUpdatePathFinder;



	public Mummy(String name, int MAP_ID, boolean destructible, int scoringValue, int x, int y,
			Sprite objectSprite, IAnimatedAnimatedObject target, int deepth, long timeToUpdatePathFinder) {
		
		super(name, MAP_ID, destructible, scoringValue, x, y,
				objectSprite, target, deepth);
		this.timeToUpdatePathFinder = timeToUpdatePathFinder;
	}
	
	public void paint(Container awtContainer, int x, int y) {
		
		paintPoliceState(awtContainer, x, y);

	}

	
	private void paintPoliceState(Container awtContainer, int x, int y) {
		

		if(currentState == Constants.MUMMY_STATE_NORMAL){
			if(getNextDirection() == Constants.DIRECTION_LEFT || getNextDirection() == Constants.DIRECTION_UP){
				getObjectSprite().drawFrame(awtContainer, x+getX(), y+getY(), currentAnimationStep, currentState);
			}else if(getNextDirection() == Constants.DIRECTION_RIGHT || getNextDirection() == Constants.DIRECTION_DOWN){
				getObjectSprite().drawFrame(awtContainer, x+getX(), y+getY(), currentAnimationStep+2, 0);			
			}else{
				getObjectSprite().drawFrame(awtContainer, x+getX(), y+getY(), currentAnimationStep, 0);
			}
		}else if(currentState == Constants.MUMMY_STATE_SCARED){
			
			if(getNextDirection() == Constants.DIRECTION_LEFT || getNextDirection() == Constants.DIRECTION_UP){
				getObjectSprite().drawFrame(awtContainer, x+getX(), y+getY(), policeScaredRunningStep, 1);
			}else if(getNextDirection() == Constants.DIRECTION_RIGHT || getNextDirection() == Constants.DIRECTION_DOWN){
				getObjectSprite().drawFrame(awtContainer, x+getX(), y+getY(), policeScaredRunningStep, 2);			
			}
			
		}else if(currentState == Constants.MUMMY_STATE_BOMB){
			getObjectSprite().drawFrame(awtContainer, x+getX(), y+getY(), policeScaredBombStep, 3);
		}else if(currentState == Constants.MUMMY_STATE_DIZZY){
			getObjectSprite().drawFrame(awtContainer, x+getX(), y+getY(), policeScaredBombStep, 4);
		}		

		
	}

	public int getCurrentState(){
		return currentState;
	}
	
	public int getState(){
		return currentState;
	}
	
	public void changeState(int stateId) {
		if(stateId == Constants.MUMMY_STATE_NORMAL){
			if(currentState != Constants.MUMMY_STATE_BOMB && currentState != Constants.MUMMY_STATE_DIZZY){
				currentState = stateId;
				currentAnimationStep = 0;
			}
		}else{
			currentState = stateId;
			currentAnimationStep = 0;
			nextTimeToMove = System.currentTimeMillis()+200;
		}
	}

	public boolean update(long nowMiliSeconds) {
				
		if(currentState == Constants.MUMMY_STATE_NORMAL){
			if(currentAnimationStep == 1){
				currentAnimationStep = 0;
			}else{
				currentAnimationStep = 1;
			}			
			
			if(nowMiliSeconds - lastUpdatePathFinder > timeToUpdatePathFinder){
				setUpdatePathFinder(true);
				lastUpdatePathFinder = nowMiliSeconds;
			}

			return true;
		}else if(currentState == Constants.MUMMY_STATE_SCARED){
			
			if(nowMiliSeconds - lastTimeRan > level.getPowerDurationTime()){
				currentState = Constants.MUMMY_STATE_NORMAL;
				lastTimeRan = 0;
				return true;
			}else{
				if(nowMiliSeconds >= nextTimeToMove){
					
					if(policeScaredRunningStep < 1){
						policeScaredRunningStep++;
					}else{
						policeScaredRunningStep=0;
					}
					nextTimeToMove = nowMiliSeconds + 200;
					return true;
				}else{
					return false;
				}
			}
		}else if(currentState == Constants.MUMMY_STATE_BOMB){
			
			if(nowMiliSeconds - lastTimeRan > 500){				
				if(policeScaredBombStep < 2){
					policeScaredBombStep++;
					lastTimeRan = System.currentTimeMillis();
				}else{
					currentState = Constants.MUMMY_STATE_DIZZY;
					policeScaredBombStep = 0;
					lastTimeRan = System.currentTimeMillis();
				}				
			}
			return false;
		}else if(currentState == Constants.MUMMY_STATE_DIZZY){
			if(nowMiliSeconds - lastTimeRan > 1000){				
				if(policeScaredBombStep < 2){
					policeScaredBombStep++;
					lastTimeRan = System.currentTimeMillis();
				}else{
					currentState = Constants.MUMMY_WAITING;
				}
				
			}
			return false;
		}else if(currentState == Constants.MUMMY_WAITING){
			reset();
			setY(getY()+60);
			Controller.getScreenMovement().show();
		}
		return false;
		
	}

	public void reset(){
		
		super.reset();
		getPathToTarget().clear();
		lastUpdatePathFinder = System.currentTimeMillis();
		policeScaredRunningStep = 0;
		currentAnimationStep = 0;
		lastTimeRan = 0;
		currentState = Constants.MUMMY_STATE_NORMAL;
		
	}
	public void start(long nowMiliSeconds) {
		lastTimeRan = nowMiliSeconds;
		policeScaredRunningStep = 0;
		policeScaredBombStep = 0;
		setUpdatePathFinder(true);
	}
	public void changeDirection(int direction) {
		setNextDirection(direction);		
	}
	public void scheduleMovement(int movement) {
		// TODO Auto-generated method stub
		
	}
	
	public boolean move() {
		
		if (getTarget() != null && getX()%60 == 0 && getY()%60 == 0 && getPathToTarget().size()>0){				
			setCurrentStepPathToTarget(getCurrentStepPathToTarget()+1);
		}			
		
		boolean move = false;
		if (getTarget() != null && getX()%60 == 0 && getY()%60 == 0 && updatePathFinder()){
			PathFinder result = new PathFinder();
			if (!isWall(getMapIndexY(), getMapIndexX())){
				if(getCurrentState() != Constants.MUMMY_STATE_SCARED && getTarget().getCurrentState() == Constants.ARCHAEOLOGIST_NORMAL){
					result = pathFinder(getMapIndexY(), getMapIndexX(), getMapIndexY(), getMapIndexX(), getTarget().getMapIndexY(), getTarget().getMapIndexX(), level.getLevelMap().getCompleteMap(), result, 0, true);
				}else{
					result = pathFinder(getMapIndexY(), getMapIndexX(), getMapIndexY(), getMapIndexX(), getTarget().getMapIndexY(), getTarget().getMapIndexX(), level.getLevelMap().getCompleteMap(), result, 0, false);
				}
		
				if (result.path != null &&  result.path.size() > 0){
					setPathToTarget(result.path);
					setUpdatePathFinder(false);
//					System.out.println(getName()+" - " +"FIND PATH:");
//					for(int j=0;j<result.path.size();j++){
//						System.out.print("["+result.path.get(j)+"]");
//					}
//					System.out.println();
				}else{
					System.out.println("Caminho encontrado (Nenhum caminho encontrado).");
				}
			}					
		}
		Integer nextMovement = null;
		
		if(getPathToTarget().size() > 0 && getTarget() != null){
			nextMovement = new Integer(getPathToTarget().get(getCurrentStepPathToTarget()).toString().trim());
			setNextDirection(nextMovement.intValue());
		}else{
			nextMovement = new Integer(getNextDirection());
			setNextDirection(nextMovement.intValue());
		}
	
		switch (getNextDirection()) {
		case Constants.DIRECTION_UP:
			if(level.getLevelMap().canGoUp(getMapIndexX(), getMapIndexY())){
				moveDown(0, -10);
			}
			break;
		case Constants.DIRECTION_DOWN:
			if(level.getLevelMap().canGoDown(getMapIndexX(), getMapIndexY())){
				moveDown(0, 10);
			}			
			break;
		case Constants.DIRECTION_LEFT:
			if(level.getLevelMap().canGoLeft(getMapIndexX(), getMapIndexY())){
				moveDown(-10, 0);
			}			
			break;
		case Constants.DIRECTION_RIGHT:
			if(level.getLevelMap().canGoRight(getMapIndexX(), getMapIndexY())){
				moveDown(10, 0);
			}			
			break;
		case Constants.DIRECTION_NONE: // no direction to move, wait for input
		default:
			break;
		}		
		
		return move;
	}
	public void notifyDirectionChanged(Object sender) {
		setUpdatePathFinder(true);
	}

}
