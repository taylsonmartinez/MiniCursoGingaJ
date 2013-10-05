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
import java.awt.Point;
import java.util.StringTokenizer;

import ui.Sprite;
import ui.TiledMapAWT;
import util.Constants;
import util.FileProperties;

public class MapAI implements IMapAI {
	
	
    private int mapGatewayX;
    private int mapGatewayY;
    
    private int coinsOnMap;     
    private Point []specialItensOnMap = new Point[7];
    private TiledMapAWT tiledMap;
    private TiledMapAWT tiledItens;

    private int iaMap[][] = new int[10][17];
    
    private int itensMap[][] = new int[10][17];

	private Sprite spriteMap;
	private int tileWidth;
	private int tileHeight;
//	private int width;
//	private int height;
	private Sprite specialItens;

    public MapAI(String mapFile, Sprite spriteMap, Sprite specialItens){
    	this.spriteMap = spriteMap;
    	this.specialItens = specialItens;
		this.tileWidth = spriteMap.getFrameWidth();
		this.tileHeight = spriteMap.getFrameHeight();
//		this.width = iaMap.length * tileWidth;
//		this.height = iaMap[0].length * tileHeight;    	
    }
 
    public void buildItens(String itensBuilder) {
    	
    	StringTokenizer lineTokens = new StringTokenizer(itensBuilder, ";");
    	StringTokenizer columTokens;
    	
    	int line = 0, count = 0;
    	
    	while(lineTokens.hasMoreTokens()){
    		String _line = lineTokens.nextToken();
    		columTokens = new StringTokenizer(_line, ",");
    		int column = 0;
    		while(columTokens.hasMoreTokens()){
    			itensMap[line][column] = Integer.parseInt(columTokens.nextToken().trim());
    			if(itensMap[line][column] == Constants.COIN_MAPID){
    				++coinsOnMap;
    			}else if(itensMap[line][column] == Constants.SPECIAL_ITEM_MAPID){
    				specialItensOnMap[count] = new Point(column, line);
    				count++;
    			}
    			++column;	
    		}
    		++line;    		
    	}	
    	tiledItens = TiledMapAWT.createFromArray(specialItens, itensMap);
   	
	}
    
	public void buildMap(String mapBuilder){    	
		
    	StringTokenizer lineTokens = new StringTokenizer(mapBuilder, ";");
    	StringTokenizer columTokens;
    	
    	int line = 0;
    	
    	while(lineTokens.hasMoreTokens()){
    		
    		String _line = lineTokens.nextToken();
    		columTokens = new StringTokenizer(_line, ",");
    		int column = 0;
    		while(columTokens.hasMoreTokens()){
    			iaMap[line][column] = Integer.parseInt(columTokens.nextToken().trim());
    			++column;
    		}
    		++line;    		
    	}
    	
    	adjustMapVector(iaMap);
    	
    	for(int i=0;i<MAXLINE;i++){
    		for(int j=0;j<MAXCOLUMN;j++){
    			if(iaMap[i][j] == Constants.GATEWAY_MAPID){
    				this.mapGatewayX = j*60;
    				this.mapGatewayY = i*60;
    			}
    		}    		
    	}
		tiledMap = TiledMapAWT.createFromArray(spriteMap, iaMap);
    	
    }

	private void adjustMapVector(int[][] verctorMap) {
		
		int numberOfLines = verctorMap.length;
		int numberOfColumns = verctorMap[0].length;
		for (int column = 0; column < numberOfColumns; column++) {
			for (int line = 0; line < numberOfLines; line++) {
				// -1 porque o mappy gera arrays iniciados em 1 e os sprites comecam em 0.
				// para facilitar para o pessoal de midia, ajustamos o mapa aqui.
				verctorMap[line][column] = verctorMap[line][column] - 1;
			}
		}
		
	}

	public void load(String mapFile) {
    	coinsOnMap = 0;
		String mapBuilder = FileProperties.getProperty("map", mapFile);
    	
    	if(mapBuilder == null){
    		System.out.println("Cannot build map. Exiting ...");
    	}
    	
    	buildMap(mapBuilder);    	
    	
    	String itensBuilder = FileProperties.getProperty("itens", mapFile);
    	
    	if(itensBuilder == null){
    		System.out.println("Cannot build map. Exiting ...");
    	}
    	
    	buildItens(itensBuilder); 
		
	}

	public void unload() {
		
	}

	public void setRandomSpecialItemPosition(int rand, int id){
		int lastX = specialItensOnMap[0].x, lastY = specialItensOnMap[0].y;
		for(int i=0; i<=MAXLINE;i++){
			for(int j=0; j<=MAXCOLUMN;j++){
				if(itensMap[i][j] == Constants.SPECIAL_ITEM_MAPID && specialItensOnMap[rand].x == j && specialItensOnMap[rand].y == i){
					setItemOnMap(j, i, id);
					return;
				}else if(itensMap[i][j] == Constants.SPECIAL_ITEM_MAPID){
					lastX = j;
					lastY = i;
				}
			}
		}
		setItemOnMap(lastX, lastY, id);
		
	}

	public void removeRandomSpecialItem() {
		for(int i=0; i<=MAXLINE;i++){
			for(int j=0; j<=MAXCOLUMN;j++){
				if(itensMap[i][j] == Constants.JEWELRY_MAPID || itensMap[i][j] == Constants.MONEY_BAG_MAPID || itensMap[i][j] == Constants.GOLD_MAPID){
					itensMap[i][j] = Constants.SPECIAL_ITEM_MAPID;
					tiledItens.setMapValue(i, j, Constants.SPECIAL_ITEM_MAPID);
					return;
				}				
			}
		}	
		
	}	
	
	public int getMapGatewayX() {
		return mapGatewayX;
	}

	
	public void setMapGatewayX(int mapGatewayX) {
		this.mapGatewayX = mapGatewayX;
	}

	
	public int getMapGatewayY() {
		return mapGatewayY;
	}

	
	public void setMapGatewayY(int mapGatewayY) {
		this.mapGatewayY = mapGatewayY;
	}

    public int getPointOnMap(int iX, int iY){    	
        return iaMap[iY][iX];
    }
    
    public int[][] getCompleteMap(){
    	return this.iaMap;
    }
    
    public int getItemOnMap(int iX, int iY){
    	return itensMap[iY][iX];
    }


	public void setItemOnMap(int iX, int iY, int id) {
		itensMap[iY][iX] = id;
		tiledItens.setMapValue(iY, iX, id);
	}
	
	public int getCoinsOnMap(){
		return coinsOnMap;
	}
	
	public void paintFullMap(Graphics g, int x, int y){
		g.setClip(x, y, 900, 540);
		tiledMap.draw(g, x, y);	
		tiledItens.draw(g, x, y);
	}

	public void paintFrame(Graphics g, int x, int y, int line, int column) {
		tiledMap.drawFrame(g, x, y, line, column);
		tiledItens.drawFrame(g, x, y, line, column);
	}

	public void paintDebugFrame(Graphics g, int x, int y, int line, int column) {
		tiledMap.drawDebugFrame(g, x, y, line, column);
	}

	public boolean canGoUp(int x, int y) {
		//System.out.println("TILE LINE: " +getTileLine(y));
		return ((getMaskFor(x, y)) & MASK____UP) == MASK____UP && getTileLine(y) >= 0;
	}

	public boolean canGoDown(int x, int y) {
		return ((getMaskFor(x, y)) & MASK__DOWN) == MASK__DOWN && getTileLine(y) < (this.iaMap.length - 1);
	}

	public boolean canGoLeft(int x, int y) {
		return (((getMaskFor(x, y)) & MASK__LEFT) == MASK__LEFT) && getTileColumn(x) >= 0;
	}

	public boolean canGoRight(int x, int y) {
		return ((getMaskFor(x, y)) & MASK_RIGHT) == MASK_RIGHT && getTileColumn(x) <= (this.iaMap[0].length - 1);
	}

	private int getMaskFor(int x, int y) {		
//		System.out.println("MAPX: "+x+" MAPY: "+y+"MAPOBJ: "+this.iaMap[y][x]+" LENGHT: "+MASK.length+" "+MASK[this.iaMap[y][x]]);
		if(x <= MAXCOLUMN && y <= MAXLINE && x >= MINCOLUMN && y >= MINLINE && this.iaMap[y][x] <= MASK.length-1){
			return MASK[this.iaMap[y][x]];
		}else{
			return MASK__NONE;
		}
	}
	
	public int getTileLine(int y) {
		
		y = (y - 60) -1;
		int linha = y / this.tileHeight;
		if (y % this.tileHeight > 0 || y < 0) linha++;
		return linha;
	}

	public int getTileColumn(int x) {
		
		int coluna = x / this.tileWidth;
		if (x % this.tileWidth > 0) coluna++;
		return coluna;
		
	}

	public int getTileWidth() {
		return 0;
	}

	public int getTileHeight() {
		return 0;
	}

	public int getYLine(int line) {
		return 0;
	}

	public int getXColumn(int column) {
		return 0;
	}

    
}
