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
package util;

import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

public class ComposedImage {

	private BufferedImage bufferedImage;
	private Container awtContainer;
	private Image image;
	
	public ComposedImage(Container awtContainer){
		this(awtContainer,0, 0);
	}
	
	public ComposedImage(Container awtContainer, int width, int height){
		this.awtContainer = awtContainer;
		Graphics g = this.awtContainer.getGraphics(); 
		bufferedImage = ((Graphics2D)g).getDeviceConfiguration().createCompatibleImage(width, height);	

	}
	
	public void drawImage(Image img, int x, int y){
		Graphics g = bufferedImage.getGraphics();		
		g.drawImage(img , x, y, awtContainer);
	}
	
	public void drawString(String str, int x, int y){
		Graphics g = bufferedImage.getGraphics();		
		g.drawString(str , x, y);
	}	
	
	public Image getImage(){
		return Toolkit.getDefaultToolkit().createImage(image.getSource()); 
	}
}
