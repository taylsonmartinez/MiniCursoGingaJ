package interativa;

import com.sun.dtv.lwuit.Button;
import com.sun.dtv.lwuit.Component;
import com.sun.dtv.lwuit.Container;
import com.sun.dtv.lwuit.Form;
import com.sun.dtv.lwuit.Image;
import com.sun.dtv.lwuit.Label;
import com.sun.dtv.lwuit.animations.CommonTransitions;
import com.sun.dtv.lwuit.events.ActionEvent;
import com.sun.dtv.lwuit.events.ActionListener;
import com.sun.dtv.lwuit.geom.Dimension;
import com.sun.dtv.lwuit.layouts.CoordinateLayout;

public class MainForm extends Component {

    public Form principal = null;
    public Dimension dim = null;
    private Container baseContainer = null;
    private Container menuContainer = null;
    private Container icoInicial = null;
    private Container menuPrincipal = null;
    private CoordinateLayout layoutForm = null;
    // Buttons
    private Button btnExit = null;
    private Button btnFight = null;
    private Button btnWins = null;
    private Button btnReturn = null;
    // Labels
    Label propaganda = null;
    Label combate = null;
    Label luta01 = null;
    Label luta02 = null;
    Label luta03 = null;
    
    Label lutaWins01 = null;
    Label lutaWins02 = null;
    Label lutaWins03 = null;
    private Image bg = null;

    public MainForm() {
        dim = new Dimension(1280, 920);
        layoutForm = new CoordinateLayout(dim);
        principal = new Form();
        principal.setLayout(layoutForm);
        // principal.getStyle().setBgColor(Color.black);
        principal.getStyle().setBgTransparency(0);
        principal.setPreferredSize(dim);
        //principal.setVisible(true);

        //-------------------------------- base container ------------------------

        baseContainer = new Container(new CoordinateLayout(new Dimension(1280, 920)));
        baseContainer.setPreferredSize(new Dimension(1280, 920));

        try {
            bg = Image.createImage("interativa/imagens/bg_ufc.png");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //baseContainer.getStyle().setBgImage(bg);
        baseContainer.getStyle().setBgTransparency(0);
        baseContainer.setX(0);
        baseContainer.setY(0);
        baseContainer.setVisible(true);


        //-------------------------- tela inicial -------------------------------

        icoInicial = new Container(new CoordinateLayout(new Dimension(1280, 920)));
        icoInicial.setPreferredSize(new Dimension(1280, 920));
        //  icoInicial.getStyle().setBgColor(Color.red);
        icoInicial.getStyle().setBgTransparency(0);
        icoInicial.setX(30);
        icoInicial.setY(50);
        icoInicial.setVisible(true);



        //-------------------------- icon interatividade -------------------------------
        Image imgInt = null;
        Button iconeInterativo = new Button();
        iconeInterativo.setPreferredSize(new Dimension(100, 100));
        iconeInterativo.setX(60);
        iconeInterativo.setY(60);
        try {
            imgInt = Image.createImage("interativa/imagens/logoUFC.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        iconeInterativo.setRolloverIcon(imgInt);
        iconeInterativo.setIcon(imgInt);
        iconeInterativo.getStyle().setBgTransparency(0);
        iconeInterativo.getStyle().setBorder(null);
        icoInicial.addComponent(iconeInterativo);


        baseContainer.addComponent(icoInicial);
        principal.addComponent(baseContainer);


        //--------------------------------base  menu container------------------------

        menuContainer = new Container(new CoordinateLayout(new Dimension(1280, 920)));
        menuContainer.setPreferredSize(new Dimension(1280, 920));
        menuContainer.getStyle().setBgImage(bg);
        menuContainer.getStyle().setBgTransparency(0);
        menuContainer.setX(0);
        menuContainer.setY(0);
        menuContainer.setVisible(true);


        menuPrincipal = new Container(new CoordinateLayout(new Dimension(400, 650)));
        menuPrincipal.setPreferredSize(new Dimension(400, 650));
        menuPrincipal.setX(0);
        menuPrincipal.getStyle().setBgTransparency(0);
        menuPrincipal.setY(200);
        menuPrincipal.setVisible(true);


        // btnExit

        Image imgBtnExitEnable = null;
        Image imgBtnExit = null;
        try {
            imgBtnExit = Image.createImage("interativa/imagens/bt_exit.png");
            imgBtnExitEnable = Image.createImage("interativa/imagens/bt_exit_enable.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnExit = new Button();
        btnExit.setPreferredSize(new Dimension(180, 80));
        btnExit.setX(100);
        btnExit.setY(550);
        btnExit.setRolloverIcon(imgBtnExitEnable);
        btnExit.setIcon(imgBtnExit);
        btnExit.getStyle().setBgTransparency(0);
        btnExit.getStyle().setBorder(null);


        // BTN FIGHT

        Image imgBtnFightEnable = null;
        Image imgBtnFight = null;
        try {
            imgBtnFight = Image.createImage("interativa/imagens/bt_fight.png");
            imgBtnFightEnable = Image.createImage("interativa/imagens/bt_fight_enable.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnFight = new Button();
        btnFight.setPreferredSize(new Dimension(180, 80));
        btnFight.setX(100);
        btnFight.setY(10);
        btnFight.setRolloverIcon(imgBtnFightEnable);
        btnFight.setIcon(imgBtnFight);
        btnFight.getStyle().setBgTransparency(0);
        btnFight.getStyle().setBorder(null);

        // BTN Wins

        Image imgBtnResultEnable = null;
        Image imgBtnResult = null;
        try {
            imgBtnResult = Image.createImage("interativa/imagens/bt_wins.png");
            imgBtnResultEnable = Image.createImage("interativa/imagens/bt_wins_enable.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnWins = new Button();
        btnWins.setPreferredSize(new Dimension(180, 80));
        btnWins.setX(100);
        btnWins.setY(110);
        btnWins.setRolloverIcon(imgBtnResultEnable);
        btnWins.setIcon(imgBtnResult);
        btnWins.getStyle().setBgTransparency(0);
        btnWins.getStyle().setBorder(null);


        // BTN RETURN

        Image imgBtnReturnEnable = null;
        Image imgBtnReturn = null;
        try {
            imgBtnReturn = Image.createImage("interativa/imagens/bt_return.png");
            imgBtnReturnEnable = Image.createImage("interativa/imagens/bt_return_enable.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
        btnReturn = new Button();
        btnReturn.setPreferredSize(new Dimension(180, 80));
        btnReturn.setX(100);
        btnReturn.setY(550);
        btnReturn.setRolloverIcon(imgBtnReturnEnable);
        btnReturn.setIcon(imgBtnReturn);
        btnReturn.getStyle().setBgTransparency(0);
        btnReturn.getStyle().setBorder(null);
        btnReturn.setVisible(false);


        // Marketing 
        Image imgPropaganda = null;
        try {
            imgPropaganda = Image.createImage("interativa/imagens/1997_es.jpg");

        } catch (Exception e) {
            e.printStackTrace();
        }

        propaganda = new Label();
        propaganda.setPreferredSize(new Dimension(250, 280));
        propaganda.setIcon(imgPropaganda);
        propaganda.getStyle().setBgTransparency(0);
        propaganda.setX(50);
        propaganda.setY(200);;
        propaganda.setVisible(true);



        // Canal Combate 
        Image imgCombate = null;
        try {
            imgCombate = Image.createImage("interativa/imagens/combate.png");

        } catch (Exception e) {
            e.printStackTrace();
        }

        combate = new Label();
        combate.setPreferredSize(new Dimension(250, 100));
        combate.setIcon(imgCombate);
        combate.getStyle().setBgTransparency(0);
        combate.setX(50);
        combate.setY(400);
        //label.getStyle().setBgImage(imgPropaganda);
        combate.setVisible(true);


        // Labels para Fight
        Image imgLuta01 = null;
        try {
            imgLuta01 = Image.createImage("interativa/imagens/luta01.jpg");

        } catch (Exception e) {
            e.printStackTrace();
        }

        Image imgLuta02 = null;
        try {
            imgLuta02 = Image.createImage("interativa/imagens/luta02.jpg");

        } catch (Exception e) {
            e.printStackTrace();
        }

        Image imgLuta03 = null;
        try {
            imgLuta03 = Image.createImage("interativa/imagens/luta03.jpg");

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        Image imgWinsLuta01 = null;
        try {
            imgWinsLuta01 = Image.createImage("interativa/imagens/luta01_a.png");

        } catch (Exception e) {
            e.printStackTrace();
        }

        Image imgWinsLuta02 = null;
        try {
            imgWinsLuta02 = Image.createImage("interativa/imagens/luta02_b.png");

        } catch (Exception e) {
            e.printStackTrace();
        }
        


        luta01 = new Label();
        luta01.setPreferredSize(new Dimension(250, 150));
        luta01.setIcon(imgLuta01);
        luta01.getStyle().setBgTransparency(0);
        luta01.setX(80);
        luta01.setY(80);
        luta01.setVisible(false);

        luta02 = new Label();
        luta02.setPreferredSize(new Dimension(250, 150));
        luta02.setIcon(imgLuta02);
        luta02.getStyle().setBgTransparency(0);
        luta02.setX(80);
        luta02.setY(235);
        luta02.setVisible(false);

        luta03 = new Label();
        luta03.setPreferredSize(new Dimension(250, 150));
        luta03.setIcon(imgLuta03);
        luta03.getStyle().setBgTransparency(0);
        luta03.setX(80);
        luta03.setY(390);
        luta03.setVisible(false);
        
        
        lutaWins01 = new Label();
        lutaWins01.setPreferredSize(new Dimension(250, 150));
        lutaWins01.getStyle().setBgTransparency(0);
        lutaWins01.setIcon(imgWinsLuta01);
        lutaWins01.setX(80);
        lutaWins01.setY(80);
        lutaWins01.setVisible(false);

        lutaWins02 = new Label();
        lutaWins02.setPreferredSize(new Dimension(250, 150));
        lutaWins02.setIcon(imgWinsLuta02);
        lutaWins02.getStyle().setBgTransparency(0);
        lutaWins02.setX(80);
        lutaWins02.setY(235);
        lutaWins02.setVisible(false);

        lutaWins03 = new Label();
        lutaWins03.setPreferredSize(new Dimension(250, 150));
        lutaWins03.setIcon(imgLuta03);
        lutaWins03.getStyle().setBgTransparency(0);
        lutaWins03.setX(80);
        lutaWins03.setY(390);
        lutaWins03.setVisible(false);


        menuPrincipal.addComponent(propaganda);
        menuPrincipal.addComponent(combate);
        menuPrincipal.addComponent(btnFight);
        menuPrincipal.addComponent(btnExit);
        menuPrincipal.addComponent(btnWins);
        menuPrincipal.addComponent(btnReturn);
        // lutas 
        menuPrincipal.addComponent(luta01);
        menuPrincipal.addComponent(lutaWins01);
       menuPrincipal.addComponent(lutaWins02);
        menuPrincipal.addComponent(luta02);
        menuPrincipal.addComponent(luta03);
        menuContainer.addComponent(menuPrincipal);


        //------------------- ActionListener -------------------------

        //----------------- icone interativo -----------------

        iconeInterativo.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                icoInicial.setVisible(false);

                baseContainer.replace(icoInicial,
                        menuContainer,
                        CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL,
                        true, 2000));
                //baseContainer.repaint();

                principal.repaint();
            }
        });


        btnFight.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                btnWins.setVisible(false);
                lutasAtivas();
                stateWinsAndFight();
            }
        });


        btnWins.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                lutasAtivasWins();
                btnFight.setVisible(false);
                btnWins.setX(100);
                btnWins.setY(10);
                stateWinsAndFight();
            }
        });




        btnReturn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                voltarMenu();
            }
        });

        //----------------- botao Sair -----------------
        btnExit.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                menuContainer.setVisible(false);
                //icoInicial.repaint();
                icoInicial.setVisible(true);
                //loginContainer.repaint();
                baseContainer.replace(menuContainer,
                        icoInicial,
                        CommonTransitions.createSlide(CommonTransitions.ALTERNATING,
                        true, 2000));
                baseContainer.repaint();


            }
        });
        principal.show();

    }

    private void stateWinsAndFight() {
        btnExit.setVisible(false);
        propaganda.setVisible(false);
        combate.setVisible(false);
        btnReturn.setVisible(true);
        menuContainer.addComponent(menuPrincipal);
        principal.repaint();
    }

    private void lutasAtivas() {
        luta01.setVisible(true);
        luta02.setVisible(true);
        luta03.setVisible(true);
    }

    private void lutasAtivasWins() {
        lutaWins01.setVisible(true);
        lutaWins02.setVisible(true);
        luta03.setVisible(true);


        
        //lutasAtivas();

    }

    private void voltarMenu() {
        luta01.setVisible(false);
        luta02.setVisible(false);
        luta03.setVisible(false);
        lutaWins01.setVisible(false);
        lutaWins02.setVisible(false);
        btnExit.setVisible(true);
        btnWins.setVisible(true);
        propaganda.setVisible(true);
        combate.setVisible(true);
        btnReturn.setVisible(false);
        btnFight.setVisible(true);
        btnWins.setX(100);
        btnWins.setY(90);
        menuContainer.addComponent(menuPrincipal);
        principal.repaint();

    }
}
