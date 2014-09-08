import java.io.IOException;

import javax.microedition.xlet.Xlet;
import javax.microedition.xlet.XletContext;
import javax.microedition.xlet.XletStateChangeException;

import net.java.gingahackathon.Galeria;
import net.java.gingahackathon.URLFetcher;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.dtv.lwuit.Button;
import com.sun.dtv.lwuit.Font;
import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.events.ActionEvent;
import com.sun.dtv.lwuit.events.ActionListener;
import com.sun.dtv.lwuit.layouts.BoxLayout;
import com.sun.dtv.lwuit.plaf.Style;

public class MainXlet implements Xlet {

	public void destroyXlet(boolean arg0) throws XletStateChangeException {
	}

	public void initXlet(XletContext arg0) throws XletStateChangeException {
	}

	public void pauseXlet() {
	}

	public void startXlet() throws XletStateChangeException {
		final Form f = new Form();
		BoxLayout layout = new BoxLayout(BoxLayout.Y_AXIS);
		f.setLayout(layout);
		Style formStyle = f.getStyle();
		// formStyle.setBgColor(Color.black);
		formStyle.setBgTransparency(0);

		final Button btn = new Button("Recuperar imagens do Flickr");
		Style btnStyle = btn.getStyle();

		btnStyle.setFont(Font.createSystemFont(Font.FACE_SYSTEM,
				Font.STYLE_PLAIN, Font.SIZE_LARGE));

		final Galeria galeria = new Galeria(4, 5);

		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				btn.setText("Carregando...");
				new Thread(new Runnable() {
					
					public void run() {
						getFlickrImages(galeria);
						btn.setText("Flickr busca por taylsonmartinez");
						f.repaint();
					}
				}).start();
				
				

			}

		});
		f.addComponent(btn);
		f.addComponent(galeria);

		f.show();
	}

	private void getFlickrImages(final Galeria galeria) {
		try {
			String content = URLFetcher
					.getURLContent("http://api.flickr.com/services/feeds/photos_public.gne?jsoncallback=callback&format=json&tagmode=any&lang=pt-br&tags=taylsonmartinez");
			content = content.substring(content.indexOf("{"),
					content.length() - 2);

			JSONObject json = new JSONObject(content);
			JSONArray items = json.getJSONArray("items");
			for (int i = 0; i < items.length(); i++) {
				System.out.println(i);
				JSONObject item = items.getJSONObject(i);
				JSONObject media = item.getJSONObject("media");
				String img = media.getString("m");
				galeria.adicionarImage(URLFetcher.getImageFromURL(img));
				System.out.println(img);
			}

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
