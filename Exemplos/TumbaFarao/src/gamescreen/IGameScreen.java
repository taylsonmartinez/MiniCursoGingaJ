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
import java.awt.Rectangle;

public interface IGameScreen
{

	/**
	 * 
	 * @param awtContainer
	 */
	public void paint(Container awtContainer);
	public void repaintBackgroundRect(Graphics g, Rectangle rect);
	public void keyPressed(int keyCode);
	public void show();
}
