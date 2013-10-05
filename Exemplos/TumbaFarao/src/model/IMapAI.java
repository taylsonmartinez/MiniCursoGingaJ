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

import java.awt.Graphics;

public interface IMapAI {

	public static final int MASK__NONE = 0;
	public static final int MASK____UP = 1;
	public static final int MASK__DOWN = 2;
	public static final int MASK__LEFT = 4;
	public static final int MASK_RIGHT = 8;
	
    public int MAXCOLUMN = 16;
    public int MAXLINE = 9;
    public int MINCOLUMN = 0;
    public int MINLINE = 0;    

	// Sincronizado com MAP_TILE_XXX
	public static final int[] MASK = new int[] { MASK__DOWN | MASK_RIGHT, // MAP_TILE_UP____LEFT
			MASK__DOWN | MASK__LEFT, // MAP_TILE_UP___RIGHT
			MASK____UP | MASK_RIGHT, // MAP_TILE_DOWN__LEFT
			MASK____UP | MASK__LEFT, // MAP_TILE_DOWN_RIGHT
			MASK__LEFT | MASK_RIGHT, // MAP_TILE_HORIZONTAL
			MASK____UP | MASK__DOWN, // MAP_TILE___VERTICAL
			MASK__LEFT | MASK__DOWN | MASK_RIGHT, // MAP_TILE_T_____DOWN
			MASK____UP | MASK__DOWN | MASK__LEFT | MASK_RIGHT, // MAP_TILE______CROSS
			MASK__LEFT | MASK____UP | MASK__DOWN, // MAP_TILE_T_____LEFT
			MASK_RIGHT | MASK____UP | MASK__DOWN, // MAP_TILE_T____RIGHT
			MASK____UP | MASK__LEFT | MASK_RIGHT, // MAP_TILE_T_______UP
			MASK__NONE, // MAP_TILE__INVISIBLE
			MASK__NONE, // MAP_TILE__INVISIBLE
			MASK__NONE, // MAP_TILE__INVISIBLE
			MASK__NONE, // MAP_TILE__INVISIBLE
			MASK__NONE, // MAP_TILE__INVISIBLE
			MASK__NONE, // MAP_TILE__INVISIBLE
			MASK__NONE, // MAP_TILE__INVISIBLE
			MASK__NONE, // MAP_TILE__INVISIBLE
			MASK__NONE, // MAP_TILE__INVISIBLE
			MASK__LEFT | MASK_RIGHT, // MAP_TILE_HORIZONTAL
			MASK__LEFT | MASK_RIGHT, // MAP_TILE_HORIZONTAL
			MASK__NONE, // MAP_TILE__INVISIBLE
			MASK__NONE, // MAP_TILE__INVISIBLE
			MASK__NONE, // MAP_TILE__INVISIBLE
			MASK__NONE, // MAP_TILE__INVISIBLE
			MASK__NONE, // MAP_TILE__INVISIBLE
			MASK__NONE, // MAP_TILE__INVISIBLE //27
			MASK__DOWN, // DOOR			
			
	};
	
	public void buildItens(String itensBuilder);
	public void buildMap(String mapBuilder);
	public void load(String mapFile);
	public void unload();
	public abstract void paintFullMap(Graphics g, int x, int y);
	public abstract void paintFrame(Graphics g, int x, int y, int line, int column);	
	public abstract void paintDebugFrame(Graphics g, int x, int y, int line, int column);
	public abstract boolean canGoUp(int x, int y);
	public abstract boolean canGoDown(int x, int y);
	public abstract boolean canGoLeft(int x, int y);
	public abstract boolean canGoRight(int x, int y);
	public abstract int getTileLine(int y);
	public abstract int getTileColumn(int x);	
	public abstract int getTileWidth();
	public abstract int getTileHeight();	
	public abstract int getYLine(int line);	
	public abstract int getXColumn(int column);
}
