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

public interface IGame {
	
	public static final int LEVEL_END = 0;
	public static final int GAME_END = 1;
	public static final int ITEM_CONSUMED = 0;
	
	public void keyPressed(int keyCode);
	public boolean update(long nowMiliSeconds);
	public void start();
	public void updateGameScore(int value);
	public void reset();
}
