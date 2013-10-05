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

import java.awt.Container;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ImageUtil {

	/**
	 * Threshold como sendo o maximo de memoria de video
	 * que uma aplicacao pode gastar. (15 megabytes)
	 */
	private static final long TOTAL_VIDEO_MEMORY = 15 * 1024 * 1024;

	private Container awtContainer;
	private MediaTracker tracker;
	private long bytesMemoriaDeVideoGastos;
	private HashMap cacheImages = new HashMap();	

	private static ImageUtil instance = null;

	public static ImageUtil createInstance(Container awtContainer) {
		if (instance == null) {
			instance = new ImageUtil(awtContainer);
		}
		return instance;
	}

	public static ImageUtil getInstance() {
		if (instance == null)
			throw new IllegalStateException("ImageUtil nao inicializado. Chame createInstace primeiro.");

		return instance;
	}

	private ImageUtil(Container awtContainer) {
		this.awtContainer = awtContainer;
		this.tracker = new MediaTracker(awtContainer);
		this.bytesMemoriaDeVideoGastos = 0;
	}

	/**
	 * Cria uma imagem de <tt>width</tt> por <tt>height</tt> pixels.
	 * 
	 * @param width
	 *            Largura da imagem em pixels.
	 * @param height
	 *            Altura da imagem em pixels.
	 * @return Um objeto do tipo <tt>Image</tt> com a imagem criada vazia.
	 */
	public Image create(int width, int height) {
		if (this.awtContainer == null)
			throw new IllegalStateException("ImageUtil nao inicializado. Chame createInstace primeiro.");

		Image retorno = awtContainer.createImage(width, height);
		somaPixelsGastos(retorno);
		printPixelsGastos();
		return retorno;
	}

	/**
	 * Carrega uma imagem acessível pelo arquivo definido por filename. Esta
	 * função utiliza um MediaTracker internamente para garantir a carga da
	 * imagem de forma sincrona no AWT. Nao faz cache da imagem em memória (se
	 * getImage for chamado 2 vezes com o mesmo arquivo ele será carregado 2
	 * vezes para memória). <br>
	 * <br>
	 * <b>ATENÇÃO:</b> Imagens carregadas com <code>getImage</code> devem ser
	 * descarregadas com <code>removeImage</code>, para que a limpeza do buffer
	 * seja feita corretamente.
	 * 
	 * @param filename
	 *            Nome do arquivo da image, por exemplo
	 *            <code>"gfx/imagem1.png"</code>.
	 * @return <code>null</code> se nao foi possivel carregar a imagem ou um
	 *         objeto do tipo Image ja carregado em memoria.
	 * 
	 */
	public Image getImage(String filename) {
		if (this.awtContainer == null)
			throw new IllegalStateException("ImageUtil nao inicializado. Chame createInstace primeiro.");

		Image retorno = null;

		if (filename != null)
			if (cacheImages.containsKey(filename))				
				return (Image) cacheImages.get(filename);
			
		try {
			retorno = Toolkit.getDefaultToolkit().createImage(filename);
			tracker.addImage(retorno, 1);
			tracker.waitForAll();
			tracker.removeImage(retorno);

			if (retorno.getWidth(null) <= 0) {
				System.out.println("ImageUtil.getImage(\"" + (filename == null ? "null" : filename) + "\") -> Carga sincrona falhou, width <= 0.");
			}
			if (retorno.getHeight(null) <= 0) {
				System.out.println("ImageUtil.getImage(\"" + (filename == null ? "null" : filename) + "\") -> Carga sincrona falhou, height <= 0.");
			}

			somaPixelsGastos(retorno);
			printPixelsGastos();

		} catch (Exception e) {
			System.out.println("ImageUtil.getImage(\"" + (filename == null ? "null" : filename) + "\") -> Falha ao carregar a imagem.");
			e.printStackTrace();
			retorno = null;
		}
				
		cacheImages.put(filename, retorno);		
		
		return retorno;
	}

	/**
	 * Remove uma imagem da memória, limpando seu buffer interno e liberando os
	 * recursos utilizados pelo AWT. Não chama <code>System.gc()</code>.
	 * 
	 * @param imagem
	 *            Imagem a ser removida da mem�ria.
	 */
	public void removeImage(Image imagem) {		
		try {
			
			// Remover imagem do cache, se ela estiver la.
			if (cacheImages.containsValue(imagem)){
				Set setKeys = cacheImages.keySet();
				Iterator key = setKeys.iterator();
				String chave = null;
				while (key.hasNext())
				{
					String chaveTmp = (String)key.next();
					Image valor = (Image)cacheImages.get(chaveTmp);
					if (valor.equals(imagem))
					{
						chave = chaveTmp;
						break;
					}
				}
				if (chave != null)
				{
					cacheImages.remove(chave);
				}
			}
			reduzPixelsGastos(imagem);
			imagem.flush();
			imagem = null;
		} catch (Exception e) {
			System.out.println("ImageUtil.removeImage(\"" + (imagem == null ? "null" : imagem.toString()) + "\") -> Falha ao remover a imagem.");
			e.printStackTrace();
		}
		printPixelsGastos();
	}

	private void somaPixelsGastos(Image image) {
		bytesMemoriaDeVideoGastos = bytesMemoriaDeVideoGastos + (image.getWidth(null) * image.getHeight(null) * 4);
	}

	private void reduzPixelsGastos(Image image) {
		bytesMemoriaDeVideoGastos = bytesMemoriaDeVideoGastos - (image.getWidth(null) * image.getHeight(null) * 4);
	}

	private void printPixelsGastos() {
		// TOTAL_VIDEO_MEMORY
		int percentual = (int) (100 * ((bytesMemoriaDeVideoGastos * 1.0F) / (TOTAL_VIDEO_MEMORY * 1.0F)));
		System.out.println("ImageUtil:printPixelsGastos() -> " + bytesMemoriaDeVideoGastos + " bytes gastos (" + percentual + "% de " + TOTAL_VIDEO_MEMORY
				+ ").");
	}

}
