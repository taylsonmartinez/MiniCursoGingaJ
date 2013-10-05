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

public class GameObject {
    private String name;
    private int mapId;
    private boolean destructible;
    private int scoringValue;
    
    
    GameObject(String name, int mapId, boolean destructible, int scoringValue){
        this.name = name;
        this.mapId = mapId;
        this.destructible = destructible;
        this.scoringValue = scoringValue;
    }


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getMapID() {
		return mapId;
	}


	public void setMapId(int mapId) {
		this.mapId = mapId;
	}


	public boolean isDestructible() {
		return destructible;
	}


	public void setDestructible(boolean destructible) {
		this.destructible = destructible;
	}


	public int getScoringValue() {
		return scoringValue;
	}


	public void setScoringValue(int scoringValue) {
		this.scoringValue = scoringValue;
	}
   
}
