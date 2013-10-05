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

import java.util.ArrayList;

import ui.Sprite;
import util.Constants;

public class SmartAnimatedGameObject extends AnimatedGameObject {

	private IAnimatedAnimatedObject target;
	private int deepth;
	private int 	  currentStepPathToTarget;
	private ArrayList pathToTarget = new ArrayList();
	private boolean updatePathFinder;

	public SmartAnimatedGameObject(String name, int MAP_ID,
			boolean destructible, int scoringValue, int x, int y,
			Sprite objectSprite,
			IAnimatedAnimatedObject target, int deepth) {

		super(name, MAP_ID, destructible, scoringValue, x, y,
				objectSprite);

		this.target = target;
		this.deepth = deepth;
		this.updatePathFinder = false;

	}

	public int getDeepth() {
		return deepth;
	}

	public void setDeepth(int deepth) {
		this.deepth = deepth;
	}

	public void setTarget(IAnimatedAnimatedObject target) {
		this.target = target;
	}

	public IAnimatedAnimatedObject getTarget() {
		return target;
	}
	
	
	public int getCurrentStepPathToTarget() {
		return currentStepPathToTarget;
	}

	public void setCurrentStepPathToTarget(int currentStepPathToTarget) {
		
		if(currentStepPathToTarget < pathToTarget.size()-1){
			this.currentStepPathToTarget = currentStepPathToTarget;
		}else{
			setUpdatePathFinder(true);
			this.currentStepPathToTarget = 0;
		}
	}

	public ArrayList getPathToTarget() {
		return pathToTarget;
	}

	public void setPathToTarget(ArrayList pathToTarget) {
		this.pathToTarget = pathToTarget;
		currentStepPathToTarget = 0;
	}

	public boolean isUpdatePathFinder() {
		return updatePathFinder;
	}

	public PathFinder pathFinder(int startLin, int startCol, int currentLin,
			int currentCol, int targetLin, int targetCol, int map[][],
			PathFinder pathFinder, int depth, boolean follow) {
//		for(int j=0;j<pathFinder.path.size();j++){
//			System.out.print("["+pathFinder.path.get(j)+"]");
//		}

		// Força bruta.
		if (depth >= 10
				|| pathFinder.parents == null
				|| (pathFinder.parents != null && pathFinder.parents.size() > 0 && pathFinder.parents
						.get(pathFinder.parents.size() - 1).toString()
						.equals(targetLin + "," + targetCol))) {
			 //System.out.println("LIMITE PROFUNDIDADE!!");
			return pathFinder;
		}

		if (Math.abs(startLin - currentLin) > getDeepth()
				|| Math.abs(startCol - currentCol) > getDeepth()) {
			// Força bruta. Faz com que o algoritimo para a busca.
			pathFinder.parents.add(targetLin + "," + targetCol);
			// System.out.println("DEEPTH");
			return pathFinder;
		}

		if ((currentLin == targetLin) && (currentCol == targetCol)) {
			pathFinder.parents.add(currentLin + "," + currentCol);
//			System.out.println("1+" + currentLin + "," + currentCol + "+");
//			System.out.println("2+"+ pathFinder.parents.get(pathFinder.parents.size() - 1).toString());
			return pathFinder;
		}

		pathFinder.parents.add(currentLin + "," + currentCol);

		if (!isInsideLimits(currentLin, currentCol)
				|| isWall(currentLin, currentCol)) {
			pathFinder.parents.remove(pathFinder.parents.size() - 1);

			if (pathFinder.path.size() > 0)
				pathFinder.path.remove(pathFinder.path.size() - 1);

//			 System.out.println("LIMITE!!");
			return pathFinder;
		}

		if (isParent(currentLin, currentCol, pathFinder.parents)) {
			pathFinder.parents.remove(pathFinder.parents.size() - 1);
			if (pathFinder.path.size() > 0)
				pathFinder.path.remove(pathFinder.path.size() - 1);
//			 System.out.println("PARENTE!!");
			return pathFinder;
		}

		if (follow) {
			if (targetLin < currentLin) {
				if (targetCol <= currentCol) {
				
					if (pathFinder.parents != null 	&& level.getLevelMap().canGoUp(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_UP);
						pathFinder = pathFinder(startLin, startCol, currentLin - 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, true);
					}
					if (pathFinder.parents != null && level.getLevelMap().canGoLeft(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_LEFT);
						pathFinder = pathFinder(startLin, startCol, currentLin, currentCol - 1, targetLin, targetCol, map, pathFinder, depth + 1, true);
					}
					if (pathFinder.parents != null && level.getLevelMap().canGoRight(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
							pathFinder.path.add("" + Constants.DIRECTION_RIGHT);
						pathFinder = pathFinder(startLin, startCol, currentLin, currentCol + 1, targetLin, targetCol, map, pathFinder, depth + 1, true);
					}
					if (pathFinder.parents != null && level.getLevelMap().canGoDown(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_DOWN);
						pathFinder = pathFinder(startLin, startCol, currentLin + 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, true);
					}
				} else if (targetCol >= currentCol) {
					if (pathFinder.parents != null && level.getLevelMap().canGoRight(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_RIGHT);
						pathFinder = pathFinder(startLin, startCol, currentLin, currentCol + 1, targetLin, targetCol, map, pathFinder, depth + 1, true);
					}

					if (pathFinder.parents != null && level.getLevelMap().canGoUp(currentCol, currentLin) && !pathFinder.parents .get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_UP);
						pathFinder = pathFinder(startLin, startCol, currentLin - 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, true);
					}

					if (pathFinder.parents != null && level.getLevelMap().canGoLeft(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_LEFT);
						pathFinder = pathFinder(startLin, startCol, currentLin, currentCol - 1, targetLin, targetCol, map, pathFinder, depth + 1, true);
					}

					if (pathFinder.parents != null && level.getLevelMap().canGoDown(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_DOWN);
						pathFinder = pathFinder(startLin, startCol, currentLin + 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, true);
					}

				}
			}else{
	    		if(targetCol >= currentCol){
	    			
	    	    	if(pathFinder.parents != null && level.getLevelMap().canGoDown(currentCol, currentLin)&& !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	    	    		pathFinder.path.add("" + Constants.DIRECTION_DOWN);
	    	    		pathFinder = pathFinder(startLin, startCol, currentLin + 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, true);
	    	    	}  
	    	    	
	       	    	if(pathFinder.parents != null && level.getLevelMap().canGoRight(currentCol, currentLin)&& !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	       	    		pathFinder.path.add("" + Constants.DIRECTION_RIGHT);
	    	    		pathFinder = pathFinder(startLin, startCol, currentLin, currentCol + 1, targetLin, targetCol, map, pathFinder, depth + 1, true);
	    	    	} 
	    	    	
	    	    	if (pathFinder.parents != null && level.getLevelMap().canGoLeft(currentCol, currentLin)&& !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	    	    		pathFinder.path.add("" + Constants.DIRECTION_LEFT);
	    	    		pathFinder = pathFinder(startLin, startCol, currentLin, currentCol - 1, targetLin, targetCol, map, pathFinder, depth + 1, true);
	    	    	}
	    	    	
	    	     	if(pathFinder.parents != null && level.getLevelMap().canGoUp(currentCol, currentLin)&& !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	    	     		pathFinder.path.add("" + Constants.DIRECTION_UP);
	    	     		pathFinder = pathFinder(startLin, startCol, currentLin - 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, true);
	    	    	}
	   	    	
	    		} else if(targetCol <= currentCol){
	    	    	if (pathFinder.parents != null && level.getLevelMap().canGoLeft(currentCol, currentLin)&& !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	    	    		pathFinder.path.add("" + Constants.DIRECTION_LEFT);
	    	    		pathFinder = pathFinder(startLin, startCol, currentLin, currentCol - 1, targetLin, targetCol, map, pathFinder, depth + 1, true);
	    	    	}
	    	    	if(pathFinder.parents != null && level.getLevelMap().canGoDown(currentCol, currentLin)&& !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	    	    		pathFinder.path.add("" + Constants.DIRECTION_DOWN);
	    	    		pathFinder = pathFinder(startLin, startCol, currentLin + 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, true);
	    	    	}     	    	
	    	    	if(pathFinder.parents != null && level.getLevelMap().canGoRight(currentCol, currentLin)&& !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	    	    		pathFinder.path.add("" + Constants.DIRECTION_RIGHT);
	    	    		pathFinder = pathFinder(startLin, startCol, currentLin, currentCol + 1, targetLin, targetCol, map, pathFinder, depth + 1, true);
	    	    	}
	    	     	if(pathFinder.parents != null && level.getLevelMap().canGoUp(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	    	     		pathFinder.path.add("" + Constants.DIRECTION_UP);
	    	     		pathFinder = pathFinder(startLin, startCol, currentLin - 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, true);
	    	    	}    	    	
	   			
	    		}
	    	} 
		} else {
			if (targetLin < currentLin) {
				if (targetCol <= currentCol) {

					if (pathFinder.parents != null && level.getLevelMap().canGoDown(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_DOWN);
						pathFinder = pathFinder(startLin, startCol,currentLin + 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, false);
					}

					if (pathFinder.parents != null && level.getLevelMap().canGoRight(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_RIGHT);
						pathFinder = pathFinder(startLin, startCol, currentLin, currentCol + 1, targetLin, targetCol, map, pathFinder, depth + 1, false);
					}

					if (pathFinder.parents != null && level.getLevelMap().canGoUp(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_UP);
						pathFinder = pathFinder(startLin, startCol, currentLin - 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, false);
					}
					if (pathFinder.parents != null && level.getLevelMap().canGoLeft(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_LEFT);
						pathFinder = pathFinder(startLin, startCol, currentLin, currentCol - 1, targetLin, targetCol, map, pathFinder, depth + 1, false);
					}

				} else if (targetCol >= currentCol) {
					if (pathFinder.parents != null && level.getLevelMap().canGoLeft(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_LEFT);
						pathFinder = pathFinder(startLin, startCol, currentLin, currentCol - 1, targetLin, targetCol, map, pathFinder, depth + 1, false);
					}

					if (pathFinder.parents != null && level.getLevelMap().canGoDown(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_DOWN);
						pathFinder = pathFinder(startLin, startCol, currentLin + 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, false);
					}

					if (pathFinder.parents != null && level.getLevelMap().canGoRight(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_RIGHT);
						pathFinder = pathFinder(startLin, startCol, currentLin, currentCol + 1, targetLin, targetCol, map, pathFinder, depth + 1, false);
					}

					if (pathFinder.parents != null && level.getLevelMap().canGoUp(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size() - 1).toString().equals(targetLin + "," + targetCol)) {
						pathFinder.path.add("" + Constants.DIRECTION_UP);
						pathFinder = pathFinder(startLin, startCol, currentLin - 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, false);
					}
				}
			}else{
	    		if(targetCol >= currentCol){
	    	     	if(pathFinder.parents != null && level.getLevelMap().canGoUp(currentCol, currentLin)&& !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	    	     		pathFinder.path.add("" + Constants.DIRECTION_UP);
	    	     		pathFinder = pathFinder(startLin, startCol, currentLin - 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, false);
	    	    	}
	    	     	
	    	    	if (pathFinder.parents != null && level.getLevelMap().canGoLeft(currentCol, currentLin)&& !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	    	    		pathFinder.path.add("" + Constants.DIRECTION_LEFT);
	    	    		pathFinder = pathFinder(startLin, startCol, currentLin, currentCol - 1, targetLin, targetCol, map, pathFinder, depth + 1, false);
	    	    	}
	    	    	
	    	    	if(pathFinder.parents != null && level.getLevelMap().canGoDown(currentCol, currentLin)&& !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	    	    		pathFinder.path.add("" + Constants.DIRECTION_DOWN);
	    	    		pathFinder = pathFinder(startLin, startCol, currentLin + 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, false);
	    	    	}  
	    	    	
	       	    	if(pathFinder.parents != null && level.getLevelMap().canGoRight(currentCol, currentLin)&& !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	       	    		pathFinder.path.add("" + Constants.DIRECTION_RIGHT);
	    	    		pathFinder = pathFinder(startLin, startCol, currentLin, currentCol + 1, targetLin, targetCol, map, pathFinder, depth + 1, false);
	    	    	} 
	   	    	
	    		} else if(targetCol <= currentCol){
	    	    	if(pathFinder.parents != null && level.getLevelMap().canGoRight(currentCol, currentLin)&& !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	    	    		pathFinder.path.add("" + Constants.DIRECTION_RIGHT);
	    	    		pathFinder = pathFinder(startLin, startCol, currentLin, currentCol + 1, targetLin, targetCol, map, pathFinder, depth + 1, false);
	    	    	}
	    	     	if(pathFinder.parents != null && level.getLevelMap().canGoUp(currentCol, currentLin) && !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	    	     		pathFinder.path.add("" + Constants.DIRECTION_UP);
	    	     		pathFinder = pathFinder(startLin, startCol, currentLin - 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, false);
	    	    	}  
	    	     	
	    	    	if (pathFinder.parents != null && level.getLevelMap().canGoLeft(currentCol, currentLin)&& !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	    	    		pathFinder.path.add("" + Constants.DIRECTION_LEFT);
	    	    		pathFinder = pathFinder(startLin, startCol, currentLin, currentCol - 1, targetLin, targetCol, map, pathFinder, depth + 1, false);
	    	    	}
	    	    	if(pathFinder.parents != null && level.getLevelMap().canGoDown(currentCol, currentLin)&& !pathFinder.parents.get(pathFinder.parents.size()-1).toString().equals(targetLin + "," + targetCol)){
	    	    		pathFinder.path.add("" + Constants.DIRECTION_DOWN);
	    	    		pathFinder = pathFinder(startLin, startCol, currentLin + 1, currentCol, targetLin, targetCol, map, pathFinder, depth + 1, false);
	    	    	}     	    	
	   			
	    		}
	    	}    	
		}

		if (pathFinder.parents != null
				&& pathFinder.parents.size() > 0
				&& !pathFinder.parents.get(pathFinder.parents.size() - 1)
						.toString().equals(targetLin + "," + targetCol)) {
			pathFinder.parents.remove(pathFinder.parents.size() - 1);
			if (pathFinder.path.size() > 0)
				pathFinder.path.remove(pathFinder.path.size() - 1);
		}

		return pathFinder;
	}

	public boolean updatePathFinder() {
		return updatePathFinder;
	}

	public void setUpdatePathFinder(boolean updatePathFinder) {
		this.updatePathFinder = updatePathFinder;
	}

    private boolean isParent(int y, int x, ArrayList parents){
    	boolean ret = false;
    	
    	for(int i=0; parents.size() > 0 && i < parents.size() - 1; i++){
    		if (parents.get(i).toString().equals(y + "," + x)){
    			ret = true;
    		}
    	}
    	
    	return ret;
    } 
    
	
    class PathFinder{
		ArrayList parents = new ArrayList();
		ArrayList path = new ArrayList();
	}    

}
