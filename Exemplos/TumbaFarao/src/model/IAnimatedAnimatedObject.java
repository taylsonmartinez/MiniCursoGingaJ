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
import java.awt.Rectangle;

public interface IAnimatedAnimatedObject {
	
	public void reset();
	
	public void changeState(int stateId);
	
	public boolean update(long nowMiliSeconds);
	
	public void start(long nowMiliSeconds);
	
	public boolean move();
	
	public void setX(int x);
	
	public void setY(int y);
	
	public int getX();
	
	public int getY();

	public int getInitialX();
	
	public int getInitialY();
	
	public void setInitialX(int initialX);
	
	public void setInitialY(int initialY);

	public int getMapIndexX();
	
	public int getMapIndexY();
	
	public void paint(Container awtContainer, int x, int y);
	
	public void levelChanged(Level level);
	
	public void changeDirection(int direction);
	
	public void scheduleMovement(int movement);
	
	public int getCurrentState();
	
	public int getPrevDirection();
	
	public Rectangle getLastRect();
	
}
