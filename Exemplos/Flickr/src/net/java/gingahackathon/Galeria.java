package net.java.gingahackathon;

import com.sun.dtv.lwuit.Container;
import com.sun.dtv.lwuit.Font;
import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.Label;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.layouts.GridLayout;
import com.sun.dtv.lwuit.plaf.Style;

public class Galeria extends Container {
	public Galeria(int linha, int coluna) {
		super(new GridLayout(linha, coluna));
	}
	
	public void adicionarImage(Image img) {
		Label l = new Label();
		if(img!=null) {
			l.setIcon(img);
		} else {
			l.setText("Sem imagem");
		}
		
		l.setPreferredSize(new Dimension(240,160));
		
		Style labelStyle = l.getStyle();
		labelStyle.setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM));
		labelStyle.setBgTransparency(0);
		this.addComponent(l);
	}
	
}
