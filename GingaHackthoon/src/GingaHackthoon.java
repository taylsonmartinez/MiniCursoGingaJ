import interativa.MainForm;
import javax.microedition.xlet.Xlet;
import javax.microedition.xlet.XletContext;
import javax.microedition.xlet.XletStateChangeException;
import com.sun.dtv.ui.DTVContainer;
import com.sun.dtv.ui.DTVContainerPattern;

/** Classe Principal que implementa o Xlet
 * 
 * <p></p>
 *
 *
 * @version 1.1
 */
public class GingaHackthoon implements Xlet{
	
	public XletContext mycontext;
	public DTVContainer dtvContainer;
	private MainForm index;
	
        
      /**
       *  Destru??do (Destroyed)
       * 
     */
	public void destroyXlet(boolean arg0) throws XletStateChangeException {
		dtvContainer.dispose();
		this.mycontext.notifyDestroyed();
		
	}

	/**
       *  initXlet 
       * 
     */
	public void initXlet(XletContext context) throws XletStateChangeException {
		this.mycontext = context;
		dtvContainer = DTVContainer.getBestDTVContainer(new DTVContainerPattern());
		
	}

	/**
       *  Xlet entra no estado pausado (Paused)
       * 
     */
	public void pauseXlet() {
		dtvContainer.setVisibility(false);
		
	}

	/**
       *  pronta para executar (start)
       * 
     */
	public void startXlet() throws XletStateChangeException {
		dtvContainer.setVisibility(true);
		System.out.println("Start");
		index = new MainForm();
		dtvContainer.addComponent(index);
//		dtvContainer.setBackgroundMode(DTVContainer.BACKGROUND_FILL);
		
	}

}
