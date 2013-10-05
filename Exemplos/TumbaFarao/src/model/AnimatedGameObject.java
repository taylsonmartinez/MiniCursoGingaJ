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

import java.awt.Rectangle;

import ui.Sprite;
import util.Constants;

public class AnimatedGameObject extends GameObject {

	private int nextDirection;
	private int prevDirection;
	private int x;
	private int y;
	private int	lastX;
	private int	lastY;
	private int initialX;
	private int initialY;
	private int	mapIndexX = 0;
	private int	mapIndexY = 0;
	private int lastMapIndexX = 0;
	private int lastMapIndexY = 0;
	private int scheduledMoving = Constants.DIRECTION_NONE;
	private Rectangle lastRect;
	private Sprite	objectSprite;
	protected Level level;
	
	
	public AnimatedGameObject(String name, int MAP_ID, boolean destructible, int scoringValue, int x, int y, Sprite objectSprite ) {
		
		super(name, MAP_ID, destructible, scoringValue);		
		nextDirection = Constants.DIRECTION_RIGHT;
	
        this.x = x;
        this.y = y;
        this.initialX = x;
        this.initialY = y;
		this.objectSprite = objectSprite;
		this.lastX = x;
		this.lastY = y;

		
		this.lastRect = new Rectangle();
		this.lastRect.setLocation(getX(), getY());
		this.lastRect.setSize(70, 70);
		
		//long seed = System.currentTimeMillis() + (possibleDirections[0]*100) + Runtime.getRuntime().freeMemory();
	}
	
    public int getInitialX() {
		return initialX;
	}

	public void setInitialX(int initialX) {
		this.initialX = initialX;
		setX(initialX);
	}

	public int getInitialY() {
		return initialY;
	}

	public void setInitialY(int initialY) {
		this.initialY = initialY;
		setY(initialY);
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		if(x%60 == 0){
			lastMapIndexX = mapIndexX;			
			mapIndexX = x/60;
		}
		lastX = this.x;
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		if(y%60 == 0){
			lastMapIndexY = mapIndexY;
			mapIndexY = y/60;
		}
		lastY = this.y;
		this.y = y;
	}	
	
	public int getScheduledMoving() {
		return scheduledMoving;
	}

	public void setScheduledMoving(int scheduledMoving) {
		this.scheduledMoving = scheduledMoving;
	}

	public int getMapIndexX() {
		return mapIndexX;
	}

	public int getMapIndexY() {
		return mapIndexY;
	}
	
	public int getLastMapIndexX() {
		return lastMapIndexX;
	}

	public int getLastMapIndexY() {
		return lastMapIndexY;
	}

	public int getNextDirection(){
        return nextDirection;
    }

    public int getPrevDirection(){      
    	return prevDirection;
    }

	public boolean setPrevDirection(int dir){
		prevDirection = dir;
        return true;
	}

    public void setNextDirection(int dir){
        nextDirection = dir;
    }
    
    public Sprite getObjectSprite(){
    	return objectSprite;
    }
    
    public void setObjectSprite(Sprite objectSprite){
    	this.objectSprite = objectSprite;
    }

	
    protected void moveRight(int dx, int dy) {
		//if(getX()+dx >= MapAI.MINCOLUMN*objectSprite.getFrameWidth() && getX()+dx <= MapAI.MAXCOLUMN*objectSprite.getFrameWidth()){
			setLastRectXY(getX(), getY());
	    	setX(getX()+dx);
	    	setY(getY()+dy);
		//}
	}

	protected void moveUp(int dx, int dy) {
		//if(getY()+dy >= MapAI.MINLINE*objectSprite.getFrameHeight() && getY()+dy <= MapAI.MAXLINE*objectSprite.getFrameHeight()){
			setLastRectXY(getX(), getY());
	    	setX(getX()+dx);
	    	setY(getY()+dy);
		//}
	}

	protected void moveLeft(int dx, int dy) {
		//if(getX()+dx >= MapAI.MINCOLUMN*objectSprite.getFrameWidth() && getX()+dx <= MapAI.MAXCOLUMN*objectSprite.getFrameWidth()){
			setLastRectXY(getX(), getY());
	    	setX(getX()+dx);
	    	setY(getY()+dy);
		//}
	}

	protected void moveDown(int dx, int dy) {	
		//if(getY()+dy >= MapAI.MINLINE*objectSprite.getFrameHeight() && getY()+dy <= MapAI.MAXLINE*objectSprite.getFrameHeight()){
			setLastRectXY(getX(), getY());
	    	setX(getX()+dx);
	    	setY(getY()+dy);
		//}
	}

	public void levelChanged(Level level){
		this.level = level;
	}
/*    public boolean setRandomDirection()
    {
    	// Original
        iRand = rand.nextInt(4);
        this.setNextDirection(iRand);
        return true;
    	
    }*/
       
    public boolean isWall(int lin, int col){
    	boolean ret = false;
    	
//    	System.out.println("isWall - 1: " + col + "," + lin);
//		System.out.println("isWall - 2: " + map[lin][col]);
    	
    	try{
        	if (level.getLevelMap().getCompleteMap()[lin][col] == 26 ){
        		ret = true;
            	System.out.println("isWall - 1: " + col + "," + lin);
        		System.out.println("isWall - 2: " + level.getLevelMap().getCompleteMap()[lin][col]);        		
        	}
    	}catch(Exception e){
    		ret = false;
    	}
    	
    	return ret;
    }
    
    public boolean isInsideLimits(int y, int x){
    	boolean ret = true;
    	
    	try{
        	if ((y >= MapAI.MINLINE && y <= MapAI.MAXLINE) && (x >= MapAI.MINCOLUMN || x <= MapAI.MAXCOLUMN))
        		ret = true;
        	
    	}catch(Exception e){
    		ret = false;
    	}
    	
    	return ret;
    }
    
    public int getLastX() {
		return lastX;
	}

	public void setLastX(int lastX) {
		this.lastX = lastX;
	}

	public int getLastY() {
		return lastY;
	}

	public void setLastY(int lastY) {
		this.lastY = lastY;
	}

	public Rectangle getLastRect() {
		return lastRect;
	}

	public void setLastRect(Rectangle lastRect) {
		this.lastRect = lastRect;
	}
	
	public void setLastRectXY(int x, int y){
		lastRect.setLocation(x, y);
	}	
	
	/**
     * Resets the Object to initial state.
     */
    public void reset(){
    	System.out.println("------------------------------------------- ");
    	System.out.println("------------------------------------------- ");
    	System.out.println("------------------------------------------- ");
    	System.out.println("----------------RESET---------------------- ");
    	System.out.println("------------------------------------------- ");
    	System.out.println("------------------------------------------- ");
    	System.out.println("------------------------------------------- ");
    	x = initialX;
    	y = initialY;
    	mapIndexX = initialX / 60;
    	mapIndexY = initialY / 60;
    	lastRect.x = initialX;
    	lastRect.y = initialY;
        prevDirection = Constants.DIRECTION_NONE;
        nextDirection = Constants.DIRECTION_NONE;
    	scheduledMoving = Constants.DIRECTION_NONE;
    	System.out.println(getName()+": "+lastRect.x+" "+lastRect.y);
    }
}
