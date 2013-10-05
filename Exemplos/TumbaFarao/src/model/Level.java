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

import java.util.Random;

import ui.Sprite;

public class Level implements ILevel{
	
	private MapAI levelMap;
	private int levelSpeed;
	private int levelScore;
	private int powerDurationTime;
	private long startPolices;
	private boolean levelClear;
	private int coinsLeft;
	private Random rand;
	private String mapFile;
	private int id;
	private boolean levelEnd;
	
	public Level(int id, int levelSpeed, long startPolices, String mapFile, Sprite spriteMap, Sprite spriteSpecialItens){
		levelMap = new MapAI(mapFile, spriteMap, spriteSpecialItens);
		levelClear = false;
		this.startPolices = startPolices;
		this.levelSpeed = levelSpeed;
		this.powerDurationTime = levelSpeed*100;
		this.coinsLeft = levelMap.getCoinsOnMap();
		rand = new Random();
		this.mapFile = mapFile;
		this.id = id;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public MapAI getLevelMap() {
		return levelMap;
	}
	
	public void setLevelMap(MapAI levelMap) {
		this.levelMap = levelMap;
	}
	
	public int getLevelSpeed() {
		return levelSpeed;
	}
	
	public void setLevelSpeed(int levelSpeed) {
		this.levelSpeed = levelSpeed;
	}
	
	public long getStartPolices() {
		return startPolices;
	}
	
	public void setStartPolices(long startPolices) {
		this.startPolices = startPolices;
	}
	
	public int getLevelScore() {
		return levelScore;
	}
	
	public void setLevelScore(int levelScore) {
		this.levelScore = levelScore;
	}
	
	public boolean isLevelClear() {
		return levelClear;
	}
	
	public void setLevelClear(boolean levelClear) {
		this.levelClear = levelClear;
	}

	public int getPowerDurationTime() {
		return powerDurationTime;
	}

	public void setPowerLeft(int powerDurationTime) {
		this.powerDurationTime = powerDurationTime;
	}	
	
	public int getCoinsLeft() {
		return coinsLeft;
	}

	public void setCoinsLeft(int coinsLeft) {
		if(coinsLeft>=0)
			this.coinsLeft = coinsLeft;
	}

	public void addSpecialItem(int id){		
		levelMap.setRandomSpecialItemPosition(rand.nextInt(4), id);
	}
	
	public void removeSpecialItem(){
		levelMap.removeRandomSpecialItem();
	}

	public void updateLevelScore(int value) {
		this.levelScore += value;		
	}

	public void reset() {

		levelMap.load(mapFile);
		levelClear = false;
		levelEnd = false;
		levelScore = 0;
		setCoinsLeft(levelMap.getCoinsOnMap());		
		
	}

	public void setLevelEnd(boolean levelEnd) {
		this.levelEnd = levelEnd;
		
	}
	
	public boolean isLevelEnd(){
		return levelEnd;
	}
}
