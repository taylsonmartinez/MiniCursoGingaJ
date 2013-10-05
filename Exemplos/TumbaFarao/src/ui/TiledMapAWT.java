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
package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;


public class TiledMapAWT
{
	private Sprite sprite;
	
	private int numTilesWide, numTilesTall;
	private int[][] vectorMap;
	
	private Font debugFont;
	
	/**
	 * Construa seu mapa atraves deste metodo.
	 * @param spriteMap sprite contendo os frames usados pelo mapa.
	 * @param mapArray vetor de linhas x colunas contendo os indices dos frames do sprite usados para desenhar o mapa.
	 * @return TiledMap pronto para uso. 
	 */
	public static TiledMapAWT createFromArray(Sprite spriteMap, int[][] mapArray)
	{
		int numeroDeLinhas = mapArray.length;
		int numeroDeColunas = mapArray[0].length;
		System.out.println("TiledMap::createFromArray(): spriteMap[" + spriteMap.toString() + "], LxC=(" + numeroDeLinhas + "," + numeroDeColunas + ")");
		
		TiledMapAWT retorno = new TiledMapAWT(spriteMap, numeroDeLinhas, numeroDeColunas);
		
		for (int column = 0; column < numeroDeColunas; column++)
		{
			for (int line = 0; line < numeroDeLinhas; line++)
			{
				//System.out.println("linha, coluna=" + linha + ", " + coluna);
				retorno.setMapValue(line, column, mapArray[line][column]);
			}
		}
		
		return retorno;
	}
	
	
	
	private TiledMapAWT(Sprite spriteMap, int numTilesTall, int numTilesWide)
	{
		this.sprite = spriteMap;
		this.vectorMap = new int[numTilesTall][numTilesWide]; // colunas x linhas
		this.numTilesWide = numTilesWide; 
		this.numTilesTall = numTilesTall;
		
		this.debugFont = new Font("Tiresias", Font.PLAIN , 14);
		
		for (int coluna = 0; coluna < numTilesWide; coluna++)
		{
			for (int linha = 0; linha < numTilesTall; linha++)
			{
				//System.out.println("@linha, coluna=" + linha + ", " + coluna);
				vectorMap[linha][coluna] = 0;
			}
		}
	}
	
	public void setMapValue(int line, int column, int spriteIndex)
	{
		if (column < numTilesWide && column >= 0 && line < numTilesTall && line >= 0)
		{
			if (spriteIndex < sprite.getTotalFrames())
			{
				vectorMap[line][column] = spriteIndex;
			}
			else
			{
				System.out.println("TiledMap::setMapValue() -> Valor " + spriteIndex + " nao consta no sprite. Ignorado.");
				vectorMap[line][column] = -1;
			}
		}
		else
		{
			System.out.println("TiledMap::setMapValue() -> Posicao linha,coluna (" + line + ", " + column + ") fora da area do mapa. (0, 0) a (" + (numTilesTall-1) + ", " + (numTilesWide-1) + ").");
		}
	}
	
	public int getMapValue(int linha, int coluna)
	{
		if (linha < numTilesTall && linha >= 0 && coluna < numTilesWide && coluna >= 0)
		{
			return vectorMap[linha][coluna];
		}
		System.out.println("TiledMap::setMapValue() -> Posicao linha,coluna (" + linha + ", " + coluna + ") fora da area do mapa. (0, 0) a (" + (numTilesTall-1) + ", " + (numTilesWide-1) + ").");
		return Integer.MIN_VALUE;
	}
	
	
	public void draw(Graphics g, int x, int y)
	{
		for (int line = 0; line < numTilesTall; line++)
		{
			int dy = y + (line * sprite.getFrameHeight());
			for (int column = 0; column < numTilesWide; column++)
			{
				int dx = x + (column * sprite.getFrameWidth());
				int index = vectorMap[line][column];
				if(index <= sprite.getTotalFrames() - 1 && index >= 0){
					sprite.drawFrame(g, dx, dy, vectorMap[line][column] );
				}
			}
		}
	}
	
	public void drawFrame(Graphics g, int x, int y, int line, int column)
	{
		if (line < 0 || column < 0 || vectorMap[line][column] < 0 || vectorMap[line][column] > sprite.getTotalFrames())
		{
//			System.out.println("drawFrame() -> (" + line + "," + column + "): valores negativos sao ignorados.");
			return;
		}
		
		int w = sprite.getFrameWidth();
		int h = sprite.getFrameHeight();
		g.setClip(x, y, w, h);
		sprite.drawFrame(g, x, y, vectorMap[line][column] );
	}
	
	
	public void drawDebugFrame(Graphics g, int x, int y, int line, int column)
	{
		int w = sprite.getFrameWidth();
		int h = sprite.getFrameHeight();		
		g.setClip(x, y, w, h);
		sprite.drawFrame(g, x, y, vectorMap[line][column] );
		g.setColor(Color.RED);
		g.drawRect(x, y, w, h);
		
		g.setColor(Color.BLACK);
		g.fillRect(x+1, y+1, w-2, 11);
		
		g.setColor(Color.WHITE);
		g.setFont(debugFont);
		g.drawString(line + "," + column, x+2, y + 10);
		//System.out.println(linha + "," + coluna + "dx=" + dx + " dy=" + dy);
		
	}
	
	
	
	
}
