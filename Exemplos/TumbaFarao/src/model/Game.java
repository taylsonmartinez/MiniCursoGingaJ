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
import java.util.ArrayList;

import ui.ImageUtil;
import ui.Sprite;
import util.Constants;
import util.KeyboardHelper;
import controller.Controller;

public class Game implements IGame, IGameEventListener {

	private static final int NUM_LEVELS = 4;
	private IAnimatedAnimatedObject archaeologist;
	private IAnimatedAnimatedObject[] mummy;
	private GameObject[] specialItens;

	private boolean gameOver;
	private boolean gameStarted;
	private boolean bankDoorOpened = false;
	private boolean bankDoorOpening = false;
	private boolean bankDoorClosed = false;
	private boolean bankDoorClosing;
	private boolean startingGame = false;
	private boolean[] specialItemAvaiable = new boolean[5];
	private boolean gamePaused;
	private boolean gameEnd;

	private int mummyCount = -1;
	private int gameScore;
	private int currentLevel = 0;
	private int specialItem = 0;

	private Level[] level;
	private long lastTimeRan;
	private long lastTimeReleasedPolice;
	private long lastTimeShowSpecialItensAvaiable;
	private int doorState;

	public Game(Container awtContainer) {

		gameScore = 0;
		gameOver = false;
		gameStarted = false;
		specialItens = new GameObject[5];
		gamePaused = false;
		gameEnd = false;
		level = new Level[5];

		Sprite special = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/sprite_itens_especiais.png"), 6, 1);

		specialItens[0] = new GameObject("Coin", Constants.COIN_MAPID, true, 10);
		specialItens[1] = new GameObject("Gold Bar", Constants.GOLD_MAPID, true, 500);
		specialItens[2] = new GameObject("Jewelry", Constants.JEWELRY_MAPID, true, 200);
		specialItens[3] = new GameObject("Money Bag", Constants.MONEY_BAG_MAPID, true, 100);
		specialItens[4] = new GameObject("Smoke Bomb", Constants.SMOKEBOMB_MAPID, true, 0);

		Sprite spriteMap = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/sprite_mumia_cenario.png"), 4, 7);

		level[0] = new Level(1, 80, 5000, "config/map1.txt", spriteMap, special);
		level[1] = new Level(2, 60, 5000, "config/map2.txt", spriteMap, special);
		level[2] = new Level(3, 40, 5000, "config/map3.txt", spriteMap, special);
		level[3] = new Level(4, 20, 5000, "config/map4.txt", spriteMap, special);
		level[4] = new Level(5, 15, 5000, "config/map5.txt", spriteMap, special);

		specialItemAvaiable[0] = false;
		specialItemAvaiable[1] = false;
		specialItemAvaiable[2] = false;
		specialItemAvaiable[3] = false;

		mummy = new Mummy[4];
		archaeologist = new Archaeologist(new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/sprite_cacador.png"), 2, 3), this, (IDirectionListener[]) mummy, 3);

		Sprite mummySprite = new Sprite(awtContainer, ImageUtil.getInstance().getImage("gfx/sprite_mumia.png"), 4, 5);

		mummy[0] = new Mummy("Mummy1", Constants.MUMMY_MAPID, false, 1000, 0, 0, mummySprite, archaeologist, 6, 3000);
		mummy[1] = new Mummy("Mummy2", Constants.MUMMY_MAPID, false, 1000, 0, 0, mummySprite, archaeologist, 9, 3000);
		mummy[2] = new Mummy("Mummy3", Constants.MUMMY_MAPID, false, 1000, 0, 0, mummySprite, archaeologist, 12, 3000);
		mummy[3] = new Mummy("Mummy4", Constants.MUMMY_MAPID, false, 1000, 0, 0, mummySprite, archaeologist, 14, 3000);

	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	public boolean isStartingGame() {
		return startingGame;
	}

	public void setStartingGame(boolean startingGame) {
		this.startingGame = startingGame;
	}

	public boolean isBankDoorClosing() {
		return bankDoorClosing;
	}

	public void setBankDoorClosing(boolean bankDoorClosing) {
		this.bankDoorClosing = bankDoorClosing;
	}

	public boolean isBankDoorOpened() {
		return bankDoorOpened;
	}

	public void setBankDoorOpened(boolean bankDoorOpened) {
		this.bankDoorOpened = bankDoorOpened;
	}

	public boolean isBankDoorOpening() {
		return bankDoorOpening;
	}

	public void setBankDoorOpening(boolean bankDoorOpening) {
		this.bankDoorOpening = bankDoorOpening;
	}

	public boolean isBankDoorClosed() {
		return bankDoorClosed;
	}

	public void setBankDoorClosed(boolean bankDoorClosed) {
		this.bankDoorClosed = bankDoorClosed;
	}

	public Level getLevel() {
		return level[currentLevel];
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public int getMummyCount() {
		return mummyCount;
	}

	public void setPoliceCount(int policeCount) {
		this.mummyCount = policeCount;
	}

	public IAnimatedAnimatedObject getMummy(int index) {
		return mummy[index];
	}

	public MapAI getGameMap() {
		return level[currentLevel].getLevelMap();
	}

	public IAnimatedAnimatedObject getArchaeologist() {
		return archaeologist;
	}

	public void setArchaeologist(Archaeologist archaeologist) {
		this.archaeologist = archaeologist;
	}

	public int getGameScore() {
		return gameScore;
	}

	public void setGameScore(int gameScore) {
		this.gameScore = gameScore;
	}

	private void moveObjects() {

		if (archaeologist.getCurrentState() == Constants.ARCHAEOLOGIST_NORMAL) {

			archaeologist.move();
			moveMummys();

			for (int i = 0; i <= mummyCount; i++) {
				if (archaeologist.getMapIndexX() == mummy[i].getMapIndexX() && archaeologist.getMapIndexY() == mummy[i].getMapIndexY()) {
					if (mummy[i].getCurrentState() == Constants.MUMMY_STATE_SCARED && archaeologist.getCurrentState() == Constants.ARCHAEOLOGIST_NORMAL) {
						updateGameScore(((GameObject) mummy[i]).getScoringValue());
						mummy[i].start(System.currentTimeMillis());
						mummy[i].changeState(Constants.MUMMY_STATE_BOMB);
					} else if (archaeologist.getCurrentState() == Constants.ARCHAEOLOGIST_NORMAL && mummy[i].getCurrentState() != Constants.MUMMY_STATE_BOMB
							&& mummy[i].getCurrentState() != Constants.MUMMY_STATE_SCARED
							&& mummy[i].getCurrentState() != Constants.MUMMY_STATE_DIZZY) {
						if (((Archaeologist) archaeologist).getLifes() > 0) {
							for (int j = 0; j < mummyCount; j++) {
								((SmartAnimatedGameObject) mummy[i]).setPathToTarget(new ArrayList());
							}

							((Archaeologist) archaeologist).setLifes(((Archaeologist) archaeologist).getLifes() - 1);
							archaeologist.start(0);
							archaeologist.changeState(Constants.ARCHAEOLOGIST_DIZZY);
						} else {
							gameOver = true;
							archaeologist.changeState(Constants.ARCHAEOLOGIST_NORMAL);
							System.out.println("Game Over");
						}
					}
				}
			}
		}
	}

	private void moveMummys() {

		for (int i = 0; i <= mummyCount; i++) {
			if (mummy[i].update(System.currentTimeMillis()) && mummy[i].getCurrentState() != Constants.MUMMY_STATE_DIZZY && mummy[i].getCurrentState() != Constants.MUMMY_STATE_BOMB) {
				mummy[i].move();
			}
		}

	}

	private void setUpObjects() {

		archaeologist.setInitialX(level[currentLevel].getLevelMap().getMapGatewayX());
		archaeologist.setInitialY(level[currentLevel].getLevelMap().getMapGatewayY());

		for (int i = 0; i < mummy.length; i++) {

			mummy[i].setInitialX(level[currentLevel].getLevelMap().getMapGatewayX());
			mummy[i].setInitialY(level[currentLevel].getLevelMap().getMapGatewayY());

		}
		archaeologist.start(0);

	}

	public void keyPressed(int keyCode) {

		if (getLevel().isLevelClear()) {
			Controller.getScreenMovement().keyPressed(keyCode);
		} else if (!isGamePaused() && !isGameOver()) {
			if (KeyboardHelper.isLeft(keyCode) && archaeologist.getCurrentState() == Constants.ARCHAEOLOGIST_NORMAL) {
				if (archaeologist.getX() % 60 == 0 && archaeologist.getY() % 60 == 0) {
					archaeologist.changeDirection(Constants.DIRECTION_LEFT);
				} else {
					archaeologist.scheduleMovement(Constants.DIRECTION_LEFT);
				}

			} else if (KeyboardHelper.isRight(keyCode) && archaeologist.getCurrentState() == Constants.ARCHAEOLOGIST_NORMAL) {
				if (archaeologist.getX() % 60 == 0 && archaeologist.getY() % 60 == 0) {
					archaeologist.changeDirection(Constants.DIRECTION_RIGHT);
				} else {
					archaeologist.scheduleMovement(Constants.DIRECTION_RIGHT);
				}
			} else if (KeyboardHelper.isUp(keyCode) && archaeologist.getCurrentState() == Constants.ARCHAEOLOGIST_NORMAL) {
				if (archaeologist.getX() % 60 == 0 && archaeologist.getY() % 60 == 0) {
					archaeologist.changeDirection(Constants.DIRECTION_UP);
				} else {
					archaeologist.scheduleMovement(Constants.DIRECTION_UP);
				}

			} else if (KeyboardHelper.isDown(keyCode) && archaeologist.getCurrentState() == Constants.ARCHAEOLOGIST_NORMAL) {
				if (archaeologist.getX() % 60 == 0 && archaeologist.getY() % 60 == 0) {
					archaeologist.changeDirection(Constants.DIRECTION_DOWN);
				} else {
					archaeologist.scheduleMovement(Constants.DIRECTION_DOWN);
				}
			} else if (KeyboardHelper.isOK(keyCode)) {
				if (level[currentLevel].isLevelClear()) {
					reset();
				} else {
					gamePaused = true;
				}
			}
		} else {
			Controller.getScreenMovement().keyPressed(keyCode);
		}
	}

	public void resetAll() {
		currentLevel = 0;
		gameScore = 0;
		((Archaeologist) archaeologist).setLifes(3);
		reset();

		setUpObjects();
	}

	public void reset() {

		setUpObjects();

		lastTimeRan = lastTimeReleasedPolice = lastTimeShowSpecialItensAvaiable = System.currentTimeMillis();

		gameOver = false;
		bankDoorOpened = false;
		bankDoorClosed = false;
		bankDoorClosing = false;
		bankDoorOpening = true;
		gamePaused = false;
		gameEnd = false;
		gameStarted = false;
		startingGame = false;

		specialItem = 0;
		doorState = 0;
		lastTimeRan = lastTimeReleasedPolice = System.currentTimeMillis();
		mummyCount = -1;

		getLevel().reset();
		archaeologist.reset();
		archaeologist.changeState(0);
		archaeologist.levelChanged(getLevel());

		archaeologist.reset();
		for (int i = 0; i < mummy.length; i++) {
			mummy[i].reset();
			mummy[i].levelChanged(getLevel());
		}

		for (int i = 0; i < specialItemAvaiable.length; i++) {
			specialItemAvaiable[i] = false;
		}
	}

	public boolean update(long nowMiliSeconds) {

		if (!isGameStarted()) {
			// Caso a plataforma demorasse mais de 2 segundos para chamar o
			// update isso causaria o travamento no "vai", por isso foi colocado
			// o !startingGame e !gameStarted para se certificar de que ele VAI
			// entrar aqui.
			if ((nowMiliSeconds - lastTimeRan > 2000) && (!startingGame) && (!gameStarted)) { // marreta
																								// biooooonica!
				startingGame = true;
				lastTimeRan = nowMiliSeconds;
				lastTimeReleasedPolice = nowMiliSeconds;
			} else if (isStartingGame()) {
				startingGame = false;
				gameStarted = true;
				gameOver = false;
			}

		} else if (!isGameOver()) {
			if (nowMiliSeconds - lastTimeReleasedPolice > 5000 && !isGamePaused() && isBankDoorOpened()) {
				if (archaeologist.getCurrentState() == Constants.ARCHAEOLOGIST_DIZZY) {
					lastTimeReleasedPolice = 0;
				} else {
					if (mummyCount < 3) {
						lastTimeReleasedPolice = nowMiliSeconds;
						mummy[mummyCount + 1].start(nowMiliSeconds);
						mummyCount++;
					} else if (mummyCount == 3 && !isBankDoorClosed() && !isBankDoorClosing()) {
						System.out.println("Closing the door.");
						setBankDoorClosing(true);
						lastTimeRan = nowMiliSeconds;
					}
				}

			}

			if (specialItemAvaiable[specialItem]) {
				if (nowMiliSeconds - lastTimeShowSpecialItensAvaiable > 15000) {
					getLevel().removeSpecialItem();
					Controller.getScreenMovement().show();
					if (specialItem < specialItemAvaiable.length - 1) {
						specialItem++;
					} else {
						specialItem = 0;
						specialItemAvaiable[0] = false;
						specialItemAvaiable[1] = false;
						specialItemAvaiable[2] = false;
						specialItemAvaiable[3] = false;
					}
				}
			}

			if (!isGamePaused() && !isGameOver() && archaeologist.update(nowMiliSeconds) && !getLevel().isLevelClear()) {
				moveObjects();
			}

			if (isBankDoorOpening() && !isBankDoorOpened() && nowMiliSeconds - lastTimeRan > 100) {
				if (doorState < 2) {
					lastTimeRan = nowMiliSeconds;
					++doorState;
				} else {
					++doorState;
					setBankDoorOpening(false);
					setBankDoorOpened(true);
				}
			} else if (isBankDoorClosing() && nowMiliSeconds - lastTimeRan > 100) {
				if (doorState > 0) {
					lastTimeRan = nowMiliSeconds;
					--doorState;
				} else {
					setBankDoorClosing(false);
					setBankDoorClosed(true);
					setBankDoorOpened(false);
				}
			}
		}
		return true;
	}

	public int getDoorState() {
		return doorState;
	}

	public void start() {

		resetAll();

	}

	public void updateGameScore(int value) {
		level[currentLevel].updateLevelScore(value);
		gameScore += value;

		if (getLevel().getCoinsLeft() == 0 && !bankDoorOpening) {
			lastTimeRan = System.currentTimeMillis();
			setBankDoorOpening(true);
			getLevel().setLevelEnd(true);
		}

		if (getLevel().getCoinsLeft() <= getLevel().getLevelMap().getCoinsOnMap() - (int) (getLevel().getLevelMap().getCoinsOnMap() * 0.25)
				&& !specialItemAvaiable[0]) {
			specialItemAvaiable[0] = true;
			getLevel().addSpecialItem(Constants.MONEY_BAG_MAPID);
			lastTimeShowSpecialItensAvaiable = System.currentTimeMillis();

		} else if (getLevel().getCoinsLeft() <= getLevel().getLevelMap().getCoinsOnMap() - (int) (getLevel().getLevelMap().getCoinsOnMap() * 0.5)
				&& !specialItemAvaiable[1]) {
			specialItemAvaiable[1] = true;
			getLevel().addSpecialItem(Constants.JEWELRY_MAPID);
			lastTimeShowSpecialItensAvaiable = System.currentTimeMillis();
		} else if (getLevel().getCoinsLeft() <= getLevel().getLevelMap().getCoinsOnMap() - (int) (getLevel().getLevelMap().getCoinsOnMap() * 0.75)
				&& !specialItemAvaiable[2]) {
			specialItemAvaiable[2] = true;
			getLevel().addSpecialItem(Constants.GOLD_MAPID);
			lastTimeShowSpecialItensAvaiable = System.currentTimeMillis();
		}

	}

	public boolean isGamePaused() {
		return gamePaused;
	}

	public void setGamePaused(boolean gamePaused) {
		this.gamePaused = gamePaused;

	}

	public void levelUp() {
		if (currentLevel < NUM_LEVELS) {
			currentLevel++;
			this.level[currentLevel].reset();
		}
	}

	public void setGameEnd(boolean gameEnd) {
		this.gameEnd = gameEnd;
	}

	public boolean isGameEnd() {
		return gameEnd;
	}

	public void notifyEvent(GameObject sender, int eventId) {

		if (eventId == Constants.ARCHEOLOGIST_DEAD) {
			for (int i = 0; i < mummyCount; i++) {
				mummy[i].reset();
			}
			mummyCount = -1;
			setBankDoorOpened(true);
			setBankDoorClosed(false);
			setUpObjects();
			Controller.getScreenMovement().show();
			lastTimeReleasedPolice = System.currentTimeMillis();
			doorState = 3;
		} else if (eventId == Game.LEVEL_END) {
			if (currentLevel == NUM_LEVELS) {
				setGameEnd(true);
			}
			getLevel().setLevelClear(true);
		}

	}

	public void notifyItemComsumed(GameObject sender, int eventId, int itemId) {

		if (itemId == Constants.COIN_MAPID) {
			updateGameScore(specialItens[Constants.COIN_MAPID].getScoringValue());
		} else if (itemId == Constants.SMOKEBOMB_MAPID) {
			updateGameScore(specialItens[Constants.SMOKEBOMB_MAPID].getScoringValue());
			long now = System.currentTimeMillis();

			for (int i = 0; i < mummy.length; i++) {
				mummy[i].changeState(Constants.MUMMY_STATE_SCARED);
				mummy[i].start(now);
			}
			System.out.println("Oh no !!! Smith has a super power now ... HEEEELLLPPPPP !");
		} else if (itemId == Constants.GOLD_MAPID) {
			updateGameScore(specialItens[Constants.GOLD_MAPID].getScoringValue());
		} else if (itemId == Constants.MONEY_BAG_MAPID) {
			updateGameScore(specialItens[Constants.MONEY_BAG_MAPID].getScoringValue());
		} else if (itemId == Constants.JEWELRY_MAPID) {
			updateGameScore(specialItens[Constants.JEWELRY_MAPID].getScoringValue());
		}

	}

}
