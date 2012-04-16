package ihm;


import bibliothequesTiers.ExampleFileFilter;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import java.io.File;
import java.io.IOException;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import noyau.Aeroport;
import noyau.Chariot;
import noyau.CheminChariot;
import noyau.ElementNoyau;
import noyau.Intersection;
import noyau.Rail;
import noyau.TapisRoulant;
import noyau.Toboggan;
import noyau.VoieGarage;
import noyau.Vol;

import oracle.jdeveloper.layout.XYConstraints;
import oracle.jdeveloper.layout.XYLayout;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.SAXException;

import vue.VueChariot;
import vue.VueChoixDirection;
import vue.VueElementsClickable;
import vue.VueMenuChoixDirection;
import vue.VueVoieGarage;


public class FenetrePrincipale extends JFrame {
    
    // Elements Charte graphique :
    static final Color COLOR_BLEUFONCE = new Color(65, 79, 167);
    static final Color COLOR_BLEUPALE = new Color(218, 239, 231);
    static final Color COLOR_BLEUPALE2 = new Color(148, 195, 255);
    static transient final Image ICO_LOGO = new ImageIcon("src\\ihm\\images\\logo_application.png").getImage();
    static final float ESPACE = (float)0.0125; // Pourcentage de pixels destinés à servir d'espace par rapport à la taille de la fenetre.
    static final int LARGEUR_ONGLET = 220;
    static final int HAUTEUR_ONGLET = 490;
    static final int HAUTEUR_INFOSUP = 40;
    
    // Propriétés de simulation
    private int freqActuVue = 10 ; // Nombre d'images par secondes
    private static Double vitesseNoyau = Double.valueOf(1); // Accélération de la simulation par rapport à la vitesse réelle du système
    private static Boolean modeAuto = false;
    private long nbTopSecond = 0;
    private ActionListener taskPerformer = new ActionListener() {

        public void actionPerformed(ActionEvent evt) {
            ActualiserSimulation();
            nbTopSecond = ActualiserDuree(nbTopSecond);
        }
    };
    private Timer timer = new Timer(1000/freqActuVue, taskPerformer);
    
    // Propriétés de gestion des fichiers :
    private Document documentXML;
    private boolean configORsimu; // true -> Config, false -> Simu
    private JFileChooser jFileChooserXML;
    private Integer nbHeuresInit = 12;
    private Integer nbMinInit = 30;
    private Integer nbSecInit = 0;
    
    private BorderLayout layoutMain = new BorderLayout();
    private JPanelBackground panelCenter;
    private JPanelBackground panelOnglet;
    private JPanelInfo panelInfo;
    private JPanel panelInfoSup = new JPanel();
    private VueAeroportPanel vueAeroportPanel;
    //private JPanelBackground logo;    
    
    private JMenuBar barreMenu = new JMenuBar();
    private JMenu menuFichier = new JMenu();
    private JMenu menuAide = new JMenu();
    private JMenuItem menuAide_About = new JMenuItem();
    private JLabel statusBar = new JLabel();
    private JMenuItem menuFichier_ChargConfig = new JMenuItem();
    private JMenuItem menuFichier_SauvSimu = new JMenuItem();
    private JMenuItem menuFichier_ChargSimu = new JMenuItem();
    private JMenuItem menuFichier_Quit = new JMenuItem();

    private JPanelBackground panelIcoInfo;
    private XYLayout xYLayout1 = new XYLayout();
    private JComboBox comboBoxChoixVol = new JComboBox();
    private JSeparator jSeparator1 = new JSeparator();
    private JLabel labelAjoutBagages = new JLabel();
    private JBoutonImage panelBoutonAjoutBagage;
    private JBoutonImage panelBoutonStart;
    private JBoutonImage panelBoutonStop;
    private JBoutonImage panelBoutonPause;
    private JSlider sliderFreqActuVue = new JSlider(JSlider.HORIZONTAL, 10, 90, 10);
    private JSlider sliderFreqActuNoyau = new JSlider(JSlider.HORIZONTAL, 1, 9, 1);
    private JLabel jLabel1 = new JLabel();
    private JSeparator jSeparator2 = new JSeparator();
    
    // Curseurs :
    private final transient Image CURSOR_AJOUTBAG_IMG =
        new ImageIcon("src\\ihm\\images\\cursor_AjoutBagage.png").getImage();
    private final Point CURSOR_AJOUTBAG_HOTSPOT = new Point(1, 1);
    private final String CURSOR_AJOUTBAG_NAME = "Curseur Ajout Bagage";
        
    // Durée :
    private static final DateFormat FORMAT_MINUTES = new SimpleDateFormat("mm");
    private static final DateFormat FORMAT_SECONDES = new SimpleDateFormat("ss");
    private JFormattedTextField textFieldDureeHeure = new JFormattedTextField(NumberFormat.getIntegerInstance());
    private JFormattedTextField textFieldDureeSec = new JFormattedTextField(FORMAT_SECONDES);
    private JFormattedTextField  textFieldDureeMin = new JFormattedTextField(FORMAT_MINUTES);
    
    // Choix du mode :
    private ButtonGroup groupeBoutonsMode = new ButtonGroup();
    private JRadioButton radioBoutonModeAuto = new JRadioButton();
    private JRadioButton radioBoutonModeManu = new JRadioButton();   
    
    // Informations de la simulation
    private JLabel jLabel2 = new JLabel();
    private JLabel jLabel200 = new JLabel();
    private JLabel jLabel3 = new JLabel();
    private JLabel jLabel4 = new JLabel();
    private JLabel jLabel5 = new JLabel();
    private JSeparator jSeparator3 = new JSeparator();
    private JLabel jLabel6 = new JLabel();
    private JSeparator jSeparator4 = new JSeparator();
    private JLabel jLabel7 = new JLabel();
    private JLabel jLabel8 = new JLabel();
    private JSlider sliderFreqEve = new JSlider(JSlider.HORIZONTAL, 0, 20, 10);
    private JLabel jLabel9 = new JLabel();
    private JLabel jLabel10 = new JLabel();
    private JLabel jLabel100 = new JLabel();
    private JLabel labelInfoTitre = new JLabel();
    
    // Elément du Menu
    private JMenu menuEdition = new JMenu();
    private JMenu menuSelection = new JMenu();
    private JMenu menuAffichage = new JMenu();
    private JMenu menuSelectElements = new JMenu();
    private JMenuItem menuSelectAll = new JMenuItem();
    private JMenuItem menuDeselect = new JMenuItem();
    private JMenuItem menuReselect = new JMenuItem();
    private JMenuItem menuSelectChariots = new JMenuItem();
    private JMenuItem menuSelectVols = new JMenuItem();
    private JMenu menuSelectZones = new JMenu();
    private JMenuItem menuSelectRails = new JMenuItem();
    private JMenuItem menuSelectVoiesGarage = new JMenuItem();
    private JMenuItem menuSelectTapisRoulants = new JMenuItem();
    private JMenuItem menuSelectToboggans = new JMenuItem();
    private JMenu menuShowElements = new JMenu();
    private JMenu menuShowZones = new JMenu();
    private JCheckBoxMenuItem checkBoxChariot = new JCheckBoxMenuItem();
    private JCheckBoxMenuItem checkBoxBagage = new JCheckBoxMenuItem();
    private JCheckBoxMenuItem checkBoxRail = new JCheckBoxMenuItem();
    private JCheckBoxMenuItem checkBoxVoieGarage = new JCheckBoxMenuItem();
    private JCheckBoxMenuItem checkBoxTapisRoulant = new JCheckBoxMenuItem();
    private JCheckBoxMenuItem checkBoxToboggan = new JCheckBoxMenuItem();
    private Rectangle rectBoundsPanelInfo;
    private JMenuItem menuChargSkin = new JMenuItem();
    private JCheckBoxMenuItem checkBoxIntersection = new JCheckBoxMenuItem();

    public FenetrePrincipale() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    private void jbInit() throws Exception {
        
        vueAeroportPanel = new VueAeroportPanel();
        
        this.getContentPane().setLayout( layoutMain );
        groupeBoutonsMode.add(radioBoutonModeAuto);
        groupeBoutonsMode.add(radioBoutonModeManu);
        
        radioBoutonModeAuto.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    radioBoutonModeAuto_itemStateChanged(e);
                }

                private void radioBoutonModeAuto_itemStateChanged(ItemEvent itemEvent) {
                    if(radioBoutonModeAuto.isSelected()) {
                        modeAuto = true;
                        if(vueAeroportPanel.GetVueAeroport() != null) {
                            vueAeroportPanel.GetVueAeroport().supprimerVuesDirection();
                        }
                        
                        radioBoutonModeAuto.setFont(new Font("Calibri", 1, 20));
                        radioBoutonModeManu.setFont(new Font("Calibri", 0, 13));
                    }     
                }
        });
        radioBoutonModeManu.addItemListener(new ItemListener() {
                public void itemStateChanged(ItemEvent e) {
                    radioBoutonModeManu_itemStateChanged(e);
                }

                private void radioBoutonModeManu_itemStateChanged(ItemEvent itemEvent) {
                    if(radioBoutonModeManu.isSelected()) {
                        modeAuto = false;
                        if (vueAeroportPanel.getAeroport() != null) {
                            for (Chariot c : vueAeroportPanel.getAeroport().getChariots()) {
                                c.setTrajet(new LinkedList<CheminChariot>());
                            };
                        }
                        radioBoutonModeManu.setFont(new Font("Calibri", 1, 20));
                        radioBoutonModeAuto.setFont(new Font("Calibri", 0, 13));
                    }
                        
                }
            });
        
        //logo = new JPanelBackground("src\\ihm\\images\\logo_application.png");
        //this.add(logo, BorderLayout.CENTER);   
        this.setJMenuBar( barreMenu );
        panelCenter = new JPanelBackground("src\\ihm\\images\\background.jpg");
        panelOnglet = new JPanelBackground("src\\ihm\\images\\onglet.png");
        panelBoutonAjoutBagage = new JBoutonImage("src\\ihm\\images\\boutonBagageActive.png", "src\\ihm\\images\\boutonBagageHover.png", "src\\ihm\\images\\boutonBagageOnCLick.png", "src\\ihm\\images\\boutonBagageInactive.png", true);
        panelBoutonStart = new JBoutonImage("src\\ihm\\images\\boutonStartActive.png", "src\\ihm\\images\\boutonStartHover.png", "src\\ihm\\images\\boutonStartOnClick.png", "src\\ihm\\images\\boutonStartInactive.png", true);
        panelBoutonStop = new JBoutonImage("src\\ihm\\images\\boutonStopActive.png", "src\\ihm\\images\\boutonStopHover.png", "src\\ihm\\images\\boutonStopOnCLick.png", "src\\ihm\\images\\boutonStopInactive.png", true);
        panelBoutonPause = new JBoutonImage("src\\ihm\\images\\boutonPauseActive.png", "src\\ihm\\images\\boutonPauseHover.png", "src\\ihm\\images\\boutonPauseOnCLick.png", "src\\ihm\\images\\boutonPauseInactive.png", true);
        panelCenter.setLayout( null );
        barreMenu.setBounds(new Rectangle(50, 50, 1438, 26));
        
        barreMenu.setOpaque(false);
        Dimension tailleFenetre = getToolkit().getScreenSize();
        this.setSize(tailleFenetre);
        this.setDefaultLookAndFeelDecorated(true);
        this.setExtendedState(this.MAXIMIZED_BOTH);
        int espacePx = (int)(tailleFenetre.getWidth()*ESPACE);
        
       // logo.setBounds(new Rectangle(50, 50, 191, 94));
        
        panelOnglet.setBounds(new Rectangle((int)tailleFenetre.getWidth()-LARGEUR_ONGLET-espacePx, espacePx, LARGEUR_ONGLET, HAUTEUR_ONGLET)); // Largeur : 220
        vueAeroportPanel.setBounds(new Rectangle(espacePx, espacePx, (int)tailleFenetre.getWidth()-LARGEUR_ONGLET-3*espacePx, (int)tailleFenetre.getHeight()-6*espacePx));
        panelOnglet.scrollRectToVisible(new Rectangle((int)tailleFenetre.getWidth()-LARGEUR_ONGLET-espacePx, espacePx, LARGEUR_ONGLET, HAUTEUR_ONGLET));
        panelOnglet.setAutoscrolls(true);
        
        rectBoundsPanelInfo = new Rectangle((int)tailleFenetre.getWidth()-LARGEUR_ONGLET-espacePx, HAUTEUR_ONGLET+HAUTEUR_INFOSUP+2*espacePx, LARGEUR_ONGLET, (int)tailleFenetre.getHeight()-7*espacePx-HAUTEUR_ONGLET-HAUTEUR_INFOSUP);
        panelInfo = new JPanelInfoNul(rectBoundsPanelInfo);
        panelInfoSup.setBounds(new Rectangle((int)tailleFenetre.getWidth()-LARGEUR_ONGLET-espacePx-2, HAUTEUR_ONGLET+2*espacePx, LARGEUR_ONGLET+4, HAUTEUR_INFOSUP)); // Largeur : 220
        
        panelInfoSup.setBackground(COLOR_BLEUPALE2);
        panelInfoSup.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        panelInfoSup.setLayout(null);
        panelIcoInfo = new JPanelBackground("src\\ihm\\images\\info.png");
        panelIcoInfo.setBounds(new Rectangle(5, 5, 21, 36)); // Largeur : 220
        labelInfoTitre.setBounds(new Rectangle(2, 2, LARGEUR_ONGLET, 36));
        panelInfoSup.add(panelIcoInfo, null); 
        panelInfoSup.add(labelInfoTitre, null);
        this.setTitle("SysBag - Bagage InterFace Manager");
        this.setIconImage(ICO_LOGO);
        this.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    this_mouseClicked(e);
                }
            });

        menuFichier.setText("Fichier");
        menuFichier.setSize(new Dimension(70, 25));
        menuAide.setText("Aide");
        menuAide_About.setText( "About" );
        menuAide_About.addActionListener( new ActionListener() { public void actionPerformed( ActionEvent ae ) { helpAbout_ActionPerformed( ae ); } } );
        statusBar.setText( "" );
        menuFichier_ChargConfig.setText("Charger Configuration");
        menuFichier_ChargConfig.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, java.awt.Event.CTRL_MASK, false));
        menuFichier_ChargConfig.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    menuFichier_ChargConfig_actionPerformed(true);
                }
            });
        menuFichier_SauvSimu.setText("Sauvegarder Simulation");
        menuFichier_SauvSimu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, java.awt.Event.CTRL_MASK, false));
        menuFichier_SauvSimu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    menuFichier_SauvSimu_actionPerformed(e);
                }
            });
        menuFichier_SauvSimu.setEnabled(false);
        menuFichier_ChargSimu.setText("Charger Simulation");
        menuFichier_ChargSimu.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                    java.awt.Event.SHIFT_MASK | java.awt.Event.CTRL_MASK, false));
        menuFichier_ChargSimu.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    menuFichier_ChargSimu_actionPerformed(true);
                }
            });
        menuFichier_Quit.setText("Quitter");
        menuFichier_Quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                    java.awt.Event.SHIFT_MASK | java.awt.Event.CTRL_MASK, false));
        menuFichier_Quit.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    menuFichier_Quit_actionPerformed(e);
                }
            });
        menuSelectAll.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    menuSelectAll_actionPerformed(e);
                }
            });
        checkBoxChariot.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    afficheChariot(checkBoxChariot.isSelected());
                }
            });
        checkBoxBagage.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    afficheBagage(checkBoxBagage.isSelected());
                }
            });
        checkBoxIntersection.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    afficheIntersection(checkBoxIntersection.isSelected());
                }
            });
        checkBoxRail.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    afficheRail(checkBoxRail.isSelected());
                }
            });
        checkBoxTapisRoulant.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    afficheTapisRoulant(checkBoxTapisRoulant.isSelected());
                }
            });
        checkBoxToboggan.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    afficheToboggan(checkBoxToboggan.isSelected());
                }
            });
        checkBoxVoieGarage.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    afficheVoieGarage(checkBoxVoieGarage.isSelected());
                }
            });
        menuDeselect.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    menuDeselect_actionPerformed(e);
                }
            });
        menuChargSkin.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    menuChargSkin_actionPerformed(e);
                }
            });
        vueAeroportPanel.setBorder(BorderFactory.createLineBorder(COLOR_BLEUFONCE, 5));
        vueAeroportPanel.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    vueAeroportPanel_mouseClicked(e);
                }

                public void mouseEntered(MouseEvent e) {
                    vueAeroportPanel_mouseEntered(e);
                }

                public void mouseExited(MouseEvent e) {
                    vueAeroportPanel_mouseExited(e);
                }
            });
        vueAeroportPanel.addKeyListener(new KeyAdapter() {

                public void keyPressed(KeyEvent e) {
                    vueAeroportPanel_keyPressed(e);
                }
            });
        vueAeroportPanel.addMouseWheelListener(new MouseWheelListener() {
                public void mouseWheelMoved(MouseWheelEvent e) {
                    vueAeroportPanel_mouseWheelMoved(e);
                }
            });
        panelOnglet.setOpaque(false);
        panelOnglet.setLayout(xYLayout1);
        comboBoxChoixVol.setBackground(Color.white);
        menuFichier.add(menuFichier_ChargConfig);
        menuFichier.addSeparator();
        menuFichier.add(menuFichier_SauvSimu);
        menuFichier.add(menuFichier_ChargSimu);
        menuFichier.addSeparator();
        menuFichier.add(menuFichier_Quit);
        barreMenu.add(menuFichier);
        barreMenu.add(menuEdition);
        menuSelection.add(menuSelectAll);
        menuSelection.add(menuDeselect);
        menuSelection.add(menuReselect);
        menuSelection.addSeparator();
        menuSelectElements.add(menuSelectChariots);
        menuSelectElements.add(menuSelectVols);
        menuSelection.add(menuSelectElements);
        menuSelectZones.add(menuSelectRails);
        menuSelectZones.add(menuSelectVoiesGarage);
        menuSelectZones.addSeparator();
        menuSelectZones.add(menuSelectTapisRoulants);
        menuSelectZones.add(menuSelectToboggans);
        menuSelection.add(menuSelectZones);
        barreMenu.add(menuSelection);
        menuShowElements.add(checkBoxChariot);
        menuShowElements.add(checkBoxBagage);
        menuShowElements.add(checkBoxIntersection);
        menuAffichage.add(menuShowElements);
        menuShowZones.add(checkBoxRail);
        menuShowZones.add(checkBoxVoieGarage);
        menuShowZones.addSeparator();
        menuShowZones.add(checkBoxTapisRoulant);
        menuShowZones.add(checkBoxToboggan);
        menuAffichage.add(menuShowZones);
        menuAffichage.addSeparator();
        menuAffichage.add(menuChargSkin);
        barreMenu.add(menuAffichage);
        menuAide.add(menuAide_About);
        barreMenu.add(menuAide);
        this.getContentPane().add(statusBar, BorderLayout.SOUTH);
        panelOnglet.add(jLabel10, new XYConstraints(205, 375, 40, 30));
        panelOnglet.add(jLabel9, new XYConstraints(0, 390, 220, 25));
        panelOnglet.add(sliderFreqEve, new XYConstraints(10, 350, 195, 40));
        panelOnglet.add(jLabel8, new XYConstraints(10, 438, 125, 20));
        panelOnglet.add(jLabel7, new XYConstraints(5, 315, 160, 40));
        panelOnglet.add(jSeparator4, new XYConstraints(10, 415, 190, 10));
        panelOnglet.add(radioBoutonModeManu, new XYConstraints(10, 295, 95, 20));
        panelOnglet.add(radioBoutonModeAuto, new XYConstraints(105, 295, 95, 20));
        panelOnglet.add(jLabel6, new XYConstraints(5, 260, 140, 40));
        panelOnglet.add(jSeparator3, new XYConstraints(10, 265, 190, 10));
        panelOnglet.add(jLabel5, new XYConstraints(5, 180, 140, 40));
        panelOnglet.add(textFieldDureeSec, new XYConstraints(145, 215, 40, 40));
        panelOnglet.add(textFieldDureeHeure, new XYConstraints(25, 215, 40, 40));
        panelOnglet.add(jLabel4, new XYConstraints(120, 210, 30, 50));
        panelOnglet.add(jLabel3, new XYConstraints(60, 210, 30, 50));
        panelOnglet.add(textFieldDureeMin, new XYConstraints(85, 215, 40, 40));
        panelOnglet.add(jLabel2, new XYConstraints(202, 120, 40, 30));
        panelOnglet.add(jLabel200, new XYConstraints(202, 160, 40, 30));
        panelOnglet.add(jSeparator2, new XYConstraints(10, 185, 190, 20));

        panelOnglet.add(jLabel100, new XYConstraints(0, 100, 80, 25));
        panelOnglet.add(jLabel1, new XYConstraints(0, 140, 80, 25));
        panelOnglet.add(sliderFreqActuVue, new XYConstraints(90, 140, 110, 40));
        panelOnglet.add(sliderFreqActuNoyau, new XYConstraints(90, 100, 110, 40));

        panelOnglet.add(panelBoutonPause, new XYConstraints(22, 52, 40, 40));
        panelOnglet.add(panelBoutonStop, new XYConstraints(157, 52, 40, 40));
        panelOnglet.add(panelBoutonStart, new XYConstraints(82, 47, 55, 50));
        panelOnglet.add(panelBoutonAjoutBagage, new XYConstraints(150, 440, 45, 45));
        panelOnglet.add(labelAjoutBagages, new XYConstraints(5, 420, 120, 20));
        panelOnglet.add(jSeparator1, new XYConstraints(10, 320, 200, 10));
        panelOnglet.add(comboBoxChoixVol, new XYConstraints(10, 455, 125, 25));
        panelCenter.add(panelOnglet, null);
        panelCenter.add(vueAeroportPanel, null);
        panelCenter.add(panelInfo, null);
        panelCenter.add(panelInfoSup, null);

        this.getContentPane().add(panelCenter, BorderLayout.CENTER);
        comboBoxChoixVol.setModel(new DefaultComboBoxModel());
        labelAjoutBagages.setText("Ajout de bagages :");
        labelAjoutBagages.setFont(new Font("Calibri", 1, 13));
        panelBoutonAjoutBagage.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    panelBoutonAjoutBagage_mouseEntered(e);
                }

                public void mouseExited(MouseEvent e) {
                    panelBoutonAjoutBagage_mouseExited(e);
                }

                public void mousePressed(MouseEvent e) {
                    panelBoutonAjoutBagage_mousePressed(e);
                }

                public void mouseReleased(MouseEvent e) {
                    panelBoutonAjoutBagage_mouseReleased(e);
                }

                public void mouseClicked(MouseEvent e) {
                    panelBoutonAjoutBagage_mouseClicked(e);
                }
            });
        panelBoutonStart.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    panelBoutonStart_mouseEntered(e);
                }

                public void mouseExited(MouseEvent e) {
                    panelBoutonStart_mouseExited(e);
                }

                public void mousePressed(MouseEvent e) {
                    panelBoutonStart_mousePressed(e);
                }

                public void mouseReleased(MouseEvent e) {
                    panelBoutonStart_mouseReleased(e);
                }

                public void mouseClicked(MouseEvent e) {
                    panelBoutonStart_mouseClicked(e);
                }
            });
        panelBoutonPause.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    panelBoutonPause_mouseEntered(e);
                }

                public void mouseExited(MouseEvent e) {
                    panelBoutonPause_mouseExited(e);
                }

                public void mousePressed(MouseEvent e) {
                    panelBoutonPause_mousePressed(e);
                }

                public void mouseReleased(MouseEvent e) {
                    panelBoutonPause_mouseReleased(e);
                }

                public void mouseClicked(MouseEvent e) {
                    panelBoutonPause_mouseClicked(e);
                }
            });
        panelBoutonStop.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    panelBoutonStop_mouseEntered(e);
                }

                public void mouseExited(MouseEvent e) {
                    panelBoutonStop_mouseExited(e);
                }

                public void mousePressed(MouseEvent e) {
                    panelBoutonStop_mousePressed(e);
                }

                public void mouseReleased(MouseEvent e) {
                    panelBoutonStop_mouseReleased(e);
                }

                public void mouseClicked(MouseEvent e) {
                    panelBoutonStop_mouseClicked(e);
                }
            });


        sliderFreqActuVue.setMajorTickSpacing(20);
        sliderFreqActuVue.setMinorTickSpacing(10);
        sliderFreqActuNoyau.setMajorTickSpacing(2);
        sliderFreqActuNoyau.setMinorTickSpacing(1);
        sliderFreqActuNoyau.setSnapToTicks(true);
        sliderFreqActuNoyau.setBackground(Color.white);
        sliderFreqActuNoyau.setRequestFocusEnabled(false);
        sliderFreqActuNoyau.setPaintLabels(true);
        sliderFreqActuNoyau.setPaintTicks(true);
        sliderFreqActuNoyau.setForeground(Color.black);
        sliderFreqActuNoyau.setFont(new Font("Calibri", 0, 8));
        sliderFreqActuNoyau.setOpaque(false);
        sliderFreqActuNoyau.setFocusable(false);
        sliderFreqActuNoyau.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    sliderFreqActuNoyau_mouseReleased(e);
                }
            });
        sliderFreqEve.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    sliderFreqEv_mouseReleased(e);
                }
            });
        
        
        
        sliderFreqActuVue.setSnapToTicks(true);
        sliderFreqActuVue.setBackground(Color.white);
        sliderFreqActuVue.setRequestFocusEnabled(false);
        sliderFreqActuVue.setPaintLabels(true);
        sliderFreqActuVue.setPaintTicks(true);
        sliderFreqActuVue.setForeground(Color.black);
        sliderFreqActuVue.setFont(new Font("Calibri", 0, 8));
        sliderFreqActuVue.setOpaque(false);
        sliderFreqActuVue.setFocusable(false);
        sliderFreqActuVue.addMouseListener(new MouseAdapter() {
                public void mouseReleased(MouseEvent e) {
                    sliderTemps_mouseReleased(e);
                }
            });
        jLabel1.setText("Freq. Vue :");
        jLabel1.setFont(new Font("Calibri", 0, 12));
        jLabel1.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel1.setHorizontalTextPosition(SwingConstants.RIGHT);
        jLabel100.setText("Vit. Moteur :");
        jLabel100.setFont(new Font("Calibri", 0, 12));
        jLabel100.setHorizontalAlignment(SwingConstants.RIGHT);
        jLabel100.setHorizontalTextPosition(SwingConstants.RIGHT);
        jLabel2.setText("x1");
        jLabel2.setFont(new Font("Calibri", 0, 9));
        jLabel200.setText("Hz");
        jLabel200.setFont(new Font("Calibri", 0, 9));
        labelInfoTitre.setText("Général");
        labelInfoTitre.setFont(new Font("Calibri", 1, 20));

        labelInfoTitre.setForeground(Color.white);
        labelInfoTitre.setHorizontalAlignment(SwingConstants.CENTER);
        labelInfoTitre.setHorizontalTextPosition(SwingConstants.CENTER);
        menuEdition.setText("Edition");
        menuEdition.setEnabled(false);
        menuSelection.setText("Sélection");
        menuAffichage.setText("Affichage");
        menuSelectElements.setText("Sélectionner Eléments");
        menuSelectElements.setEnabled(false);
        menuSelectAll.setText("Sélectionner Tout");
        menuSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, java.awt.Event.CTRL_MASK, false));
        menuDeselect.setText("Déselectionner");
        menuDeselect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, java.awt.Event.CTRL_MASK, false));
        menuReselect.setText("Resélectionner");
        menuReselect.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
                    java.awt.Event.SHIFT_MASK | java.awt.Event.CTRL_MASK, false));
        menuReselect.setEnabled(false);
        menuSelectChariots.setText("Chariots");
        menuSelectVols.setText("Vols");
        menuSelectZones.setText("Sélectionner Zones");
        menuSelectZones.setEnabled(false);
        menuSelectRails.setText("Rails");
        menuSelectVoiesGarage.setText("Voies de garage");
        menuSelectTapisRoulants.setText("Tapis-roulants");
        menuSelectToboggans.setText("Toboggans");
        menuShowElements.setText("Afficher Eléments");
        menuShowZones.setText("Afficher Zones");
        checkBoxChariot.setText("Chariots");
        checkBoxChariot.setSelected(true);
        checkBoxBagage.setText("Bagages");
        checkBoxBagage.setSelected(true);
        checkBoxRail.setText("Rails");
        checkBoxRail.setSelected(true);
        checkBoxVoieGarage.setText("Voies de garage");
        checkBoxVoieGarage.setSelected(true);
        checkBoxTapisRoulant.setText("Tapis-roulants");
        checkBoxTapisRoulant.setSelected(true);
        checkBoxToboggan.setText("Toboggan");
        checkBoxToboggan.setSelected(true);
        menuChargSkin.setText("Charger Skin");
        menuChargSkin.setEnabled(false);
        checkBoxIntersection.setText("Intersection");
        checkBoxIntersection.setSelected(true);
        textFieldDureeMin.setBackground(COLOR_BLEUPALE);
        textFieldDureeMin.setFont(new Font("Calibri", 1, 27));
        textFieldDureeMin.setDisabledTextColor(Color.white);
        textFieldDureeMin.setText(this.nbMinInit.toString());
        textFieldDureeMin.setHorizontalAlignment(JTextField.CENTER);
        textFieldDureeMin.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    jTextField1_keyTyped(textFieldDureeMin, e);
                }
            });
        jLabel3.setText(":");
        jLabel3.setFont(new Font("Calibri", 1, 27));
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel4.setText(":");
        jLabel4.setFont(new Font("Calibri", 1, 27));
        jLabel4.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel5.setText("Durée de la simulation :");
        jLabel5.setFont(new Font("Calibri", 1, 13));
        jLabel6.setText("Mode :");
        jLabel6.setFont(new Font("Calibri", 1, 13));
        jLabel7.setText("Génération d'événements :");
        jLabel7.setFont(new Font("Calibri", 1, 13));
        jLabel8.setText("Vol");
        jLabel8.setFont(new Font("Calibri", 0, 12));
        jLabel8.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel8.setHorizontalTextPosition(SwingConstants.CENTER);
        sliderFreqEve.setMajorTickSpacing(5);
        sliderFreqEve.setMinorTickSpacing(1);
        sliderFreqEve.setSnapToTicks(true);
        sliderFreqEve.setBackground(Color.white);
        sliderFreqEve.setRequestFocusEnabled(false);
        sliderFreqEve.setPaintLabels(true);
        sliderFreqEve.setPaintTicks(true);
        sliderFreqEve.setForeground(Color.black);
        sliderFreqEve.setFont(new Font("Calibri", 0, 10));
        sliderFreqEve.setOpaque(false);
        sliderFreqEve.setFocusable(false);
        jLabel9.setText("Fréquence");
        jLabel9.setFont(new Font("Calibri", 0, 12));
        jLabel9.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel9.setHorizontalTextPosition(SwingConstants.CENTER);
        jLabel10.setText("La");
        jLabel10.setFont(new Font("Calibri", 0, 9));
        radioBoutonModeAuto.setText("Auto.");
        radioBoutonModeAuto.setFont(new Font("Calibri", 0, 13));
        radioBoutonModeAuto.setHorizontalTextPosition(SwingConstants.RIGHT);
        radioBoutonModeAuto.setHorizontalAlignment(SwingConstants.CENTER);
        radioBoutonModeAuto.setBackground(Color.white);
        radioBoutonModeManu.setText("Manu.");
        radioBoutonModeManu.setFont(new Font("Calibri", 0, 13));
        radioBoutonModeManu.setHorizontalAlignment(SwingConstants.CENTER);
        radioBoutonModeManu.setHorizontalTextPosition(SwingConstants.LEFT);
        radioBoutonModeManu.setBackground(Color.white);
        radioBoutonModeManu.setSelected(true);
        textFieldDureeHeure.setBackground(COLOR_BLEUPALE);
        textFieldDureeHeure.setFont(new Font("Calibri", 1, 27));
        textFieldDureeHeure.setDisabledTextColor(Color.white);
        textFieldDureeHeure.setText(this.nbHeuresInit.toString());
        textFieldDureeHeure.setHorizontalAlignment(JTextField.CENTER);
        textFieldDureeHeure.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    jTextField1_keyTyped(textFieldDureeHeure, e);
                }
            });
        textFieldDureeSec.setBackground(COLOR_BLEUPALE);
        textFieldDureeSec.setFont(new Font("Calibri", 1, 27));
        textFieldDureeSec.setDisabledTextColor(Color.white);
        textFieldDureeSec.setText(this.nbSecInit.toString());
        textFieldDureeSec.setHorizontalAlignment(JTextField.CENTER);
        textFieldDureeSec.addKeyListener(new KeyAdapter() {
                public void keyTyped(KeyEvent e) {
                    jTextField1_keyTyped(textFieldDureeSec,e);
                }
            });

    }
    
    // --------------------------------------------------------------------------------------------------------------
    // ----------------------------------------- Element Menu -------------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------

    void fileExit_ActionPerformed(ActionEvent e) {
        System.exit(0);
    }

    void helpAbout_ActionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, new FenetrePrincipale_AboutBoxPanel1(), "About", JOptionPane.PLAIN_MESSAGE);
    }

    private void menuFichier_SauvConfig_actionPerformed(ActionEvent e) {
        // Non implémenté
    }
            
            
    /** Méthode permettant de charger un fichier XML à partir d'un répertoire de votre choix, et d'importer la configuration
     *  qu'il contient dans l'application.
     *  Cette méthode est déclenché après appui sur le bouton Charger Configuration du menu
     * @param e
     */
    private void menuFichier_ChargConfig_actionPerformed(boolean newFile) {
        Document document;
        boolean chargOK = false;
            
        if (newFile) {
            jFileChooserXML = new JFileChooser();
            // Note: source for ExampleFileFilter can be found in FileChooserDemo,
            // under the demo/jfc directory in the JDK.
            ExampleFileFilter filter = new ExampleFileFilter();
            filter.addExtension("xml");
            filter.setDescription("Fichier XML");
            jFileChooserXML.setFileFilter(filter);
            jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
            int returnVal = jFileChooserXML.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                // debug
                System.out.println("nom de fichier " +
                        jFileChooserXML.getSelectedFile().getAbsolutePath());
                try {
                    // création d'une fabrique de documents
                    DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
    
                    // création d'un constructeur de documents
                    DocumentBuilder constructeur = fabrique.newDocumentBuilder();
    
                    // lecture du contenu d'un fichier XML avec DOM
                    File xml = new File(jFileChooserXML.getSelectedFile().getAbsolutePath());
                    document = constructeur.parse(xml);
                    chargementConfig(document);
    
                } catch (ParserConfigurationException pce) {
                    System.out.println("Erreur de configuration du parseur DOM");
                    System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
                } catch (SAXException se) {
                    System.out.println("Erreur lors du parsing du document");
                    System.out.println("lors de l'appel à construteur.parse(xml)");
                } catch (IOException ioe) {
                    System.out.println("Erreur d'entrée/sortie");
                    System.out.println("lors de l'appel à construteur.parse(xml)");
                }
            }
        } else {
            chargementConfig(this.documentXML);
        }

    }
    
    private void chargementConfig(Document document) {
        Element racine = document.getDocumentElement();
        int resultatConstruction = vueAeroportPanel.ConstruireVueAeroportAPartirDeDOMXML(racine);
        if (resultatConstruction != Aeroport.PARSE_OK) {
            //erreur de parsiong!
        } else {
            //création de la liste de vols dans comboBoxChoixVol
            comboBoxChoixVol.removeAllItems();
            LinkedList<Vol> vols = vueAeroportPanel.getVols();
            for(Vol volCourant : vols){
               comboBoxChoixVol.addItem(volCourant);
            }
            
            if (panelBoutonStart.isBlocked()) {
                panelBoutonStart.setStatut();
            }
            panelBoutonStart.setEtat(JBoutonImage.Etat.unhover);
            
            panelCenter.remove(panelInfo);
            panelInfo = new JPanelInfoGeneral(rectBoundsPanelInfo, vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueChariot"), vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueBagage"));
            panelInfo.setElement(vueAeroportPanel.getAeroport());
            panelCenter.add(panelInfo, null);
            panelCenter.repaint();
            labelInfoTitre.setText("Général");
            
            // On réinitialise le temps pour une nouvelle simulation :
            textFieldDureeHeure.setText("12");
            textFieldDureeMin.setText("30");
            textFieldDureeSec.setText("00");
            
            System.out.println("Configuration chargée.");
            menuChargSkin.setEnabled(true);
            
            // Sauvegarde du noeud XML pour recharger config :
            this.documentXML = document;
            this.configORsimu = true;
        }
    }


    /** Méthode permettant de sauvegarder sur forme XML les données de la simulation en cours
     *  Le choix du fichier se fait après clic sur le bouton du menu : Sauvegarder Simulation
     * @param e
     */
    private void menuFichier_SauvSimu_actionPerformed(ActionEvent e) {
        jFileChooserXML = new JFileChooser();
        // Note: source for ExampleFileFilter can be found in FileChooserDemo,
        // under the demo/jfc directory in the JDK.
        ExampleFileFilter filter = new ExampleFileFilter();
        filter.addExtension("xml");
        filter.setDescription("Fichier XML");
        jFileChooserXML.setFileFilter(filter);
        jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int returnVal = jFileChooserXML.showSaveDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("nom de fichier " +
                    jFileChooserXML.getSelectedFile().getAbsolutePath());

            //Création de l'infrastructure pour construire le XML
            DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
            DocumentBuilder constructeur;
            try {
                constructeur = fabrique.newDocumentBuilder();
            } catch (ParserConfigurationException pCe) {
                return;
            }
            Document document = constructeur.newDocument();
            
            // Sauvegarde du temps dans le fichier XML :
            Element racine = document.createElement(this.getClass().getName());
            
            Integer nbHeures = Integer.parseInt(textFieldDureeHeure.getText());
            Integer nbMin = Integer.parseInt(textFieldDureeMin.getText());
            Integer nbSec = Integer.parseInt(textFieldDureeSec.getText());
                   
            Attr timeHeureAttribut = document.createAttribute("nbHeures");
            racine.setAttributeNode(timeHeureAttribut);
            timeHeureAttribut.setValue(Integer.toString(nbHeures));
            
            Attr timeMinAttribut = document.createAttribute("nbMin");
            racine.setAttributeNode(timeMinAttribut);
            timeMinAttribut.setValue(Integer.toString(nbMin));
            
            Attr timeSecAttribut = document.createAttribute("nbSec");
            racine.setAttributeNode(timeSecAttribut);
            timeSecAttribut.setValue(Integer.toString(nbSec));
            
            
            Element noeud = vueAeroportPanel.SauvegarderVueAeroportAPartirDeDOMXML(document);
            if (noeud == null) {
                return;
                //erreur de parsing!
            }
            // Ajout au document
            racine.appendChild(noeud);
            document.appendChild(racine);
               
            try {
                // Prepare the DOM document for writing
                DOMSource source = new DOMSource(document);

                // Prepare the output file
                String fileName = jFileChooserXML.getSelectedFile().getAbsolutePath();
                File file = new File(fileName);
                StreamResult result = new StreamResult(file);

                // Write the DOM document to the file
                Transformer xformer = TransformerFactory.newInstance().newTransformer();
                xformer.setOutputProperty(OutputKeys.INDENT, "yes");

                xformer.transform(source, result);

            } catch (TransformerConfigurationException tCe) {
                System.out.println(tCe.getMessage());
                return;
            } catch (TransformerException te) {
                System.out.println(te.getMessage());
                return;
            }

        }
    }

   private void menuFichier_Quit_actionPerformed(ActionEvent e) {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.exit(0);
    }

    /** Méthode permettant le chargement d'une simulation de l'application. Cette méthode charge également la configuration associée
     *  ce qui permet de ne pas charger la configuration puis la simulation d'un même fichier
     * @param e
     */
    private void menuFichier_ChargSimu_actionPerformed(boolean newFile) {
        Document document;
        boolean chargOK = false;
            
        if (newFile) {
            jFileChooserXML = new JFileChooser();
            // Note: source for ExampleFileFilter can be found in FileChooserDemo,
            // under the demo/jfc directory in the JDK.
            ExampleFileFilter filter = new ExampleFileFilter();
            filter.addExtension("xml");
            filter.setDescription("Fichier XML");
            jFileChooserXML.setFileFilter(filter);
            jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);
    
            int returnVal = jFileChooserXML.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                // debug
                System.out.println("nom de fichier " +
                        jFileChooserXML.getSelectedFile().getAbsolutePath());
                try {
                    // création d'une fabrique de documents
                    DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
    
                    // création d'un constructeur de documents
                    DocumentBuilder constructeur = fabrique.newDocumentBuilder();
    
                    // lecture du contenu d'un fichier XML avec DOM
                    File xml = new File(jFileChooserXML.getSelectedFile().getAbsolutePath());
                    document = constructeur.parse(xml);
                    chargementSimu(document);
    
                } catch (ParserConfigurationException pce) {
                    System.out.println("Erreur de configuration du parseur DOM");
                    System.out.println("lors de l'appel à fabrique.newDocumentBuilder();");
                } catch (SAXException se) {
                    System.out.println("Erreur lors du parsing du document");
                    System.out.println("lors de l'appel à construteur.parse(xml)");
                } catch (IOException ioe) {
                    System.out.println("Erreur d'entrée/sortie");
                    System.out.println("lors de l'appel à construteur.parse(xml)");
                }

            }  
        } else {
            chargementSimu(this.documentXML);
        }
    }
    
    private void chargementSimu(Document document) {
        // On récupère le temps de la simulation :
        String tag = "ihm.FenetrePrincipale";
        NodeList liste = document.getElementsByTagName(tag);
        
        for (int i = 0; i < liste.getLength(); i++) {
            Element fenetrePrincipale = (Element) liste.item(i);
            
            String nbHeures = fenetrePrincipale.getAttribute("nbHeures");
            String nbMin = fenetrePrincipale.getAttribute("nbMin");
            String nbSec = fenetrePrincipale.getAttribute("nbSec");
        
            textFieldDureeHeure.setText(nbHeures);
            textFieldDureeMin.setText(nbMin);
            textFieldDureeSec.setText(nbSec);
        }
        
        Element racine = document.getDocumentElement();
        
        int resultatConstruction = vueAeroportPanel.ConstruireVueAeroportAPartirDeDOMXML(racine);
        if (resultatConstruction != Aeroport.PARSE_OK) {
        //erreur de parsiong!
        } else {
            //création de la liste de vols dans comboBoxChoixVol
            comboBoxChoixVol.removeAllItems();
            LinkedList<Vol> vols = vueAeroportPanel.getVols();
            for(Vol volCourant : vols){
               comboBoxChoixVol.addItem(volCourant);
            }
            
            if (panelBoutonStart.isBlocked()) {
                panelBoutonStart.setStatut();
            }
            panelBoutonStart.setEtat(JBoutonImage.Etat.unhover);
            
            panelCenter.remove(panelInfo);
            panelInfo = new JPanelInfoGeneral(rectBoundsPanelInfo, vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueChariot"), vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueBagage"));
            panelInfo.setElement(vueAeroportPanel.getAeroport());
            labelInfoTitre.setText("Général");
            panelCenter.add(panelInfo, null);
            panelCenter.repaint();
        }
        
        // Chargement des paramètres de simulations
        resultatConstruction = vueAeroportPanel.ConstruireSimulAPartirDeDOMXML(racine);
        if (resultatConstruction != Aeroport.PARSE_OK) {
        //erreur de parsiong!
        } else {
            panelCenter.remove(panelInfo);
            panelInfo = new JPanelInfoGeneral(rectBoundsPanelInfo, vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueChariot"), vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueBagage"));
            panelInfo.setElement(vueAeroportPanel.getAeroport());
            labelInfoTitre.setText("Général");
            panelCenter.add(panelInfo, null);
            panelCenter.repaint();
            comboBoxChoixVol.removeAllItems();
            LinkedList<Vol> vols = vueAeroportPanel.getVols();
            for(Vol volCourant : vols){
               comboBoxChoixVol.addItem(volCourant);
             }
            // Sauvegarde de la structure XML pour recharger sur besoin :
            
            this.documentXML = document;
            this.configORsimu = false;
        }
        
        System.out.println("Simulation chargée.");
        menuChargSkin.setEnabled(true);
    }
    
    private void menuSelectAll_actionPerformed(ActionEvent e) {
        if (vueAeroportPanel.GetVueAeroport() != null) {
            vueAeroportPanel.GetVueAeroport().deselectionnerTout();
            vueAeroportPanel.GetVueAeroport().supprimerVuesDirection();
            panelCenter.remove(panelInfo);
            panelInfo = new JPanelInfoGeneral(rectBoundsPanelInfo, vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueChariot"), vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueBagage"));
            panelInfo.setElement(vueAeroportPanel.getAeroport());
             labelInfoTitre.setText("Général");
            panelCenter.add(panelInfo, null);
            panelCenter.repaint();
        }
    }
    
    private void menuDeselect_actionPerformed(ActionEvent e) {
        if (vueAeroportPanel.GetVueAeroport() != null) {
            vueAeroportPanel.GetVueAeroport().deselectionnerTout();
            vueAeroportPanel.GetVueAeroport().supprimerVuesDirection();
        }
    }

    private void menuChargSkin_actionPerformed(ActionEvent e) {
        jFileChooserXML = new JFileChooser();
        // Note: source for ExampleFileFilter can be found in FileChooserDemo,
        // under the demo/jfc directory in the JDK.
        ExampleFileFilter filter = new ExampleFileFilter();
        filter.addExtension("xml");
        filter.setDescription("Fichier XML");
        jFileChooserXML.setFileFilter(filter);
        jFileChooserXML.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int returnVal = jFileChooserXML.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            // debug
            System.out.println("nom de fichier " +
                    jFileChooserXML.getSelectedFile().getAbsolutePath());
            try {
                // création d'une fabrique de documents
                DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();

                // création d'un constructeur de documents
                DocumentBuilder constructeur = fabrique.newDocumentBuilder();

                // lecture du contenu d'un fichier XML avec DOM
                File xml = new File(jFileChooserXML.getSelectedFile().getAbsolutePath());
                Document document = constructeur.parse(xml);
                
                Element racine = document.getDocumentElement();
                vueAeroportPanel.GetVueAeroport().getSkin().ImporterImagesSkin(racine);

            } catch (SAXException f) {
                f.printStackTrace();
            } catch (ParserConfigurationException f) {
            f.printStackTrace();
            } catch (IOException f) {
                f.printStackTrace();
            }
        }
    }
    
    
    // --------------------------------------------------------------------------------------------------------------
    // ----------------------------------------- GESTION DES BOUTONS ------------------------------------------------
    // --------------------------------------------------------------------------------------------------------------
    
    private void desactivateBoutonImage(JBoutonImage bouton) {
        if (bouton.isBlocked() && bouton.getEtat() == JBoutonImage.Etat.clicked) {
            bouton.setStatut();
            bouton.setEtat(JBoutonImage.Etat.unhover);
        }
        this.setCursor(Cursor.getDefaultCursor());
    }

    private void this_mouseClicked(MouseEvent e) {
        // Désactivation des boutons :
        desactivateBoutonImage(panelBoutonAjoutBagage);
    }

    private void panelBoutonAjoutBagage_mouseEntered(MouseEvent e) {
        panelBoutonAjoutBagage.setEtat(JBoutonImage.Etat.hover);
    }

    private void panelBoutonAjoutBagage_mouseExited(MouseEvent e) {
        panelBoutonAjoutBagage.setEtat(JBoutonImage.Etat.unhover);
    }

    private void panelBoutonAjoutBagage_mousePressed(MouseEvent e) {
        panelBoutonAjoutBagage.setEtat(JBoutonImage.Etat.clicked);
    }

    private void panelBoutonAjoutBagage_mouseReleased(MouseEvent e) {
        panelBoutonAjoutBagage.setEtat(JBoutonImage.Etat.hover);
    }

    private void panelBoutonAjoutBagage_mouseClicked(MouseEvent e) {
        // Désactivation des autres boutons :
        desactivateBoutonImage(panelBoutonStart);
        desactivateBoutonImage(panelBoutonPause);
        desactivateBoutonImage(panelBoutonStop);
        
        panelBoutonAjoutBagage.setEtat(JBoutonImage.Etat.clicked);
        if (!panelBoutonAjoutBagage.isBlocked()) {
            panelBoutonAjoutBagage.setStatut();
            this.setCursor(getToolkit().createCustomCursor(CURSOR_AJOUTBAG_IMG, CURSOR_AJOUTBAG_HOTSPOT, CURSOR_AJOUTBAG_NAME));
            vueAeroportPanel.GetVueAeroport().SurbrillanceTR(true);
        }
        panelBoutonAjoutBagage.setEtat(JBoutonImage.Etat.hover);
        
    }

    private void panelBoutonStart_mouseEntered(MouseEvent e) {
        panelBoutonStart.setEtat(JBoutonImage.Etat.hover);
    }

    private void panelBoutonStart_mouseExited(MouseEvent e) {
        panelBoutonStart.setEtat(JBoutonImage.Etat.unhover);
    }

    private void panelBoutonStart_mousePressed(MouseEvent e) {
        panelBoutonStart.setEtat(JBoutonImage.Etat.clicked);
    }

    private void panelBoutonStart_mouseReleased(MouseEvent e) {
        panelBoutonStart.setEtat(JBoutonImage.Etat.hover);
    }

    private void panelBoutonStart_mouseClicked(MouseEvent e) {
        timer.start();
        
        // Désactivation des autres boutons :
        desactivateBoutonImage(panelBoutonAjoutBagage);
        desactivateBoutonImage(panelBoutonPause);
        desactivateBoutonImage(panelBoutonStop);
        
        panelBoutonStart.setEtat(JBoutonImage.Etat.clicked);
        if (!panelBoutonStart.isBlocked()) {
            if(radioBoutonModeManu.isSelected()) {
                panelBoutonAjoutBagage.setStatut();
            }
            panelBoutonStart.setStatut();
            if (panelBoutonStop.isBlocked()) { 
                panelBoutonStop.setStatut();
            }
           if (panelBoutonPause.isBlocked()) {
               panelBoutonPause.setStatut();
           }
            panelBoutonStop.setEtat(JBoutonImage.Etat.unhover);
            panelBoutonPause.setEtat(JBoutonImage.Etat.unhover);
            textFieldDureeHeure.setEnabled(false);
            textFieldDureeHeure.setBackground(COLOR_BLEUFONCE);
            textFieldDureeMin.setEnabled(false);
            textFieldDureeMin.setBackground(COLOR_BLEUFONCE);
            textFieldDureeSec.setEnabled(false);
            textFieldDureeSec.setBackground(COLOR_BLEUFONCE);
            if (radioBoutonModeAuto.isSelected()) {
                radioBoutonModeAuto.setFont(new Font("Calibri", 1, 20));
            }
            else {
                radioBoutonModeManu.setFont(new Font("Calibri", 1, 20));
            }
            
            menuFichier_SauvSimu.setEnabled(true);
            this.nbHeuresInit = Integer.parseInt(this.textFieldDureeHeure.getText());
            this.nbMinInit = Integer.parseInt(this.textFieldDureeMin.getText());
            this.nbSecInit = Integer.parseInt(this.textFieldDureeSec.getText());
        }
    }
    
    private void panelBoutonStop_mouseEntered(MouseEvent e) {
        panelBoutonStop.setEtat(JBoutonImage.Etat.hover);
    }

    private void panelBoutonStop_mouseExited(MouseEvent e) {
        panelBoutonStop.setEtat(JBoutonImage.Etat.unhover);
    }

    private void panelBoutonStop_mousePressed(MouseEvent e) {
        panelBoutonStop.setEtat(JBoutonImage.Etat.clicked);
    }

    private void panelBoutonStop_mouseReleased(MouseEvent e) {
        panelBoutonStop.setEtat(JBoutonImage.Etat.hover);
    }

    private void panelBoutonStop_mouseClicked(MouseEvent e) {
        timer.stop();
        
        // Désactivation des autres boutons :
        desactivateBoutonImage(panelBoutonAjoutBagage);
        desactivateBoutonImage(panelBoutonStart);
        desactivateBoutonImage(panelBoutonPause);
        panelBoutonPause.setEtat(JBoutonImage.Etat.inactive);
        if (!panelBoutonPause.isBlocked()) {panelBoutonPause.setStatut();}
        if (!panelBoutonAjoutBagage.isBlocked()) {panelBoutonAjoutBagage.setStatut();}        
        panelBoutonStop.setEtat(JBoutonImage.Etat.inactive);
        if (!panelBoutonStop.isBlocked()) {
            panelBoutonStop.setStatut();
            panelBoutonStop.setEtat(JBoutonImage.Etat.hover);
            
            textFieldDureeHeure.setEnabled(true);
            textFieldDureeHeure.setBackground(COLOR_BLEUPALE);
            textFieldDureeMin.setEnabled(true);
            textFieldDureeMin.setBackground(COLOR_BLEUPALE);
            textFieldDureeSec.setEnabled(true);
            textFieldDureeSec.setBackground(COLOR_BLEUPALE);
            
            radioBoutonModeAuto.setFont(new Font("Calibri", 0, 13));
            radioBoutonModeManu.setFont(new Font("Calibri", 0, 13));
            
            // Etat initial :
            menuFichier_SauvSimu.setEnabled(false);
            if (this.configORsimu) {
                menuFichier_ChargConfig_actionPerformed(false);
            } else {
                menuFichier_ChargSimu_actionPerformed(false);
            }
            for (Chariot c : vueAeroportPanel.getAeroport().getChariots()) {
                c.setAction(Chariot.Action.garerChariot);
            }
            textFieldDureeSec.setText(this.nbSecInit.toString());
            textFieldDureeMin.setText(this.nbMinInit.toString());
            textFieldDureeHeure.setText(this.nbHeuresInit.toString());
        }
    }
    private void panelBoutonPause_mouseEntered(MouseEvent e) {
        panelBoutonPause.setEtat(JBoutonImage.Etat.hover);
    }

    private void panelBoutonPause_mouseExited(MouseEvent e) {
        panelBoutonPause.setEtat(JBoutonImage.Etat.unhover);
    }

    private void panelBoutonPause_mousePressed(MouseEvent e) {
        panelBoutonPause.setEtat(JBoutonImage.Etat.clicked);
    }

    private void panelBoutonPause_mouseReleased(MouseEvent e) {
        panelBoutonPause.setEtat(JBoutonImage.Etat.hover);
    }

    private void panelBoutonPause_mouseClicked(MouseEvent e) {
        timer.stop();
        
        // Désactivation des autres boutons :
        desactivateBoutonImage(panelBoutonAjoutBagage);
        desactivateBoutonImage(panelBoutonStart);
        desactivateBoutonImage(panelBoutonStop);
        
        panelBoutonPause.setEtat(JBoutonImage.Etat.clicked);
        if (!panelBoutonPause.isBlocked()) {
            panelBoutonPause.setStatut();
        }
        panelBoutonPause.setEtat(JBoutonImage.Etat.hover);
    }

    private void ActualiserSimulation() {
        vueAeroportPanel.getAeroport().Actualiser(vitesseNoyau/freqActuVue, modeAuto);
        vueAeroportPanel.Redessiner();
        panelInfo.update();
    }
    
    private long ActualiserDuree(long nbTop) {
        nbTop += this.vitesseNoyau;
        if (nbTop >= freqActuVue) {
            Integer sec = Integer.parseInt(textFieldDureeSec.getText());
            sec--;
            Integer heu = Integer.parseInt(textFieldDureeHeure.getText());
            Integer min = Integer.parseInt(textFieldDureeMin.getText());
            if (sec == 0 && min == 0 && heu == 0) {
                panelBoutonStop_mouseClicked(null); // Fin de simulation.
    }
            if (sec < 0) {
                sec = 59;
                min--;
                if (min < 0) {
                    min = 59;                  
                    heu--;
                    textFieldDureeHeure.setText(heu.toString());
                }
                textFieldDureeMin.setText(min.toString());
            }
            textFieldDureeSec.setText(sec.toString());
            nbTop -= freqActuVue;
        }
        return nbTop;
    }

    private void sliderTemps_mouseReleased(MouseEvent e) {
        this.freqActuVue = sliderFreqActuVue.getValue();
        if (this.freqActuVue == 0) {
            panelBoutonPause_mouseClicked(null);
        }
        else {
            timer.setDelay(1000/freqActuVue);
        }
    }
    
    private void sliderFreqActuNoyau_mouseReleased(MouseEvent e) {
        this.vitesseNoyau = (double)sliderFreqActuNoyau.getValue();
    }
    
    private void sliderFreqEv_mouseReleased(MouseEvent e) {
        this.vueAeroportPanel.getAeroport().setLambdaPoisson((double)sliderFreqEve.getValue());   
    }
    
    // -----------------------------------------------------------------------------------------------------------------
    // --------------------------------------- GESTION DES CLICS SUR AEROPORT ------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private void jTextField1_keyTyped(JFormattedTextField jText, KeyEvent e) {
        String s = jText.getText();
        if (s.length() > 1) { e.consume(); }
    }

    /**
     * @param e
     * Fonction qui traite le clic sur vueAeroportPanel
     */
    private void vueAeroportPanel_mouseClicked(MouseEvent e) {
        if (e.getButton() == e.BUTTON2) {
            vueAeroportPanel.GetVueAeroport().setCoefZoom(2.0);
            vueAeroportPanel.GetVueAeroport().setY0(0);
            vueAeroportPanel.GetVueAeroport().setX0(0);
        }
        int x = e.getX();
        int y = e.getY();
        VueElementsClickable elementChoisi = vueAeroportPanel.GetVueAeroport().selectElement(x,y);
        vueAeroportPanel.GetVueAeroport().deselectionnerTout();
        
        if (elementChoisi!=null){
            ElementNoyau objetCorresp = (ElementNoyau)elementChoisi.getElement();
        
            elementChoisi.preciserSelection(true);
            // Si c'est une intersection, et si elle est liée à un toboggan ou un tapis-roulant, alors on manipule les informations sur cet objet à la place :
            if (objetCorresp.getClass() == Intersection.class) {
                Intersection inters = (Intersection)objetCorresp;
                if (inters.getPointDepot() != null) {
                    objetCorresp = inters.getPointDepot();
                }
                else if (inters.getPointRetrait() != null) {
                    objetCorresp = inters.getPointRetrait();
                }
            }
            
            // Affichage des informations sur cet élément :
            
            if ((panelInfo.getElement() != null) && (!panelInfo.getElement().equals(objetCorresp))) {
                // Si c'est un élément différent du précédent sélectionné, on met à jour le panel d'informations :
                if (objetCorresp.getClass() == Chariot.class) {
                    panelCenter.remove(panelInfo);
                    panelInfo = new JPanelInfoChariot(rectBoundsPanelInfo, vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueChariot"));
                    panelInfo.setElement(objetCorresp);
                    labelInfoTitre.setText("Chariot");
                    panelCenter.add(panelInfo, null);
                    panelCenter.repaint();
                    
                }
                else if (objetCorresp.getClass() == TapisRoulant.class) {
                    panelCenter.remove(panelInfo);
                    panelInfo = new JPanelInfoTapisRoulant(rectBoundsPanelInfo, vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueTapisRoulant"), vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueBagage"));
                    panelInfo.setElement(objetCorresp);
                    labelInfoTitre.setText("Tapis-Roulant");
                    panelCenter.add(panelInfo, null);
                    panelCenter.repaint();
                    
                }
                else if (objetCorresp.getClass() == Toboggan.class) {
                    panelCenter.remove(panelInfo);
                    panelInfo = new JPanelInfoToboggan(rectBoundsPanelInfo, vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueToboggan"), vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueBagage"));
                    panelInfo.setElement(objetCorresp);
                    labelInfoTitre.setText("Toboggan");
                    panelCenter.add(panelInfo, null);
                    panelCenter.repaint();
                }
                else if (objetCorresp.getClass() == VoieGarage.class) {
                    panelCenter.remove(panelInfo);
                    panelInfo = new JPanelInfoVoieGarage(rectBoundsPanelInfo, vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueVoieGarage"));
                    panelInfo.setElement(objetCorresp);
                    labelInfoTitre.setText("Voie de Garage");
                    panelCenter.add(panelInfo, null);
                    panelCenter.repaint();
                }
                else {
                    panelCenter.remove(panelInfo);
                    panelInfo = new JPanelInfoGeneral(rectBoundsPanelInfo, vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueChariot"), vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueBagage"));
                    panelInfo.setElement(vueAeroportPanel.getAeroport());
                    labelInfoTitre.setText("Général");
                    panelCenter.add(panelInfo, null);
                    panelCenter.repaint();
                }
            }

            if(radioBoutonModeManu.isSelected()){ // on est en mode manuel

                // si on est en ajout de bagage
                if(panelBoutonAjoutBagage.getEtat()==JBoutonImage.Etat.clicked){
                    vueAeroportPanel.GetVueAeroport().supprimerVuesDirection();
                    if(objetCorresp.getClass()==TapisRoulant.class){ // on a cliqué sur un tapis roulant

                        Vol volChoisi = (Vol)comboBoxChoixVol.getSelectedItem();
                        TapisRoulant tapisRoulantChoisi= (TapisRoulant) objetCorresp;
                        vueAeroportPanel.GetVueAeroport().getAeroport().ajouterBagage(tapisRoulantChoisi, volChoisi);
                        vueAeroportPanel.GetVueAeroport().SurbrillanceTR(false);
                        panelBoutonAjoutBagage.setStatut();
                        panelBoutonAjoutBagage.setEtat(JBoutonImage.Etat.unhover);
                        this.setCursor(Cursor.getDefaultCursor());
                        vueAeroportPanel.Redessiner();
                    }
                    else{  // on a cliqué ailleurs que sur un tapis roulant
                        e.consume();
                    }
                }// fin si en ajout bagage
                else{ // on n'est pas en ajout bagage
                    if(objetCorresp.getClass()==Chariot.class){ // si on a cliqué sur un chariot
                        vueAeroportPanel.GetVueAeroport().supprimerVuesDirection();
                        VueChariot vueChariotChoisi= (VueChariot)elementChoisi;
                        Chariot chariotChoisi= (Chariot)objetCorresp;
                        if(chariotChoisi.getEstArrete()==true){// chariot arrété
                            VueMenuChoixDirection vueMenu= new VueMenuChoixDirection((Intersection)chariotChoisi.getCheminActuel().getNoeudFin(),
                                                                                     vueAeroportPanel.GetVueAeroport(),chariotChoisi );
                            
                        }
                    } // fin si cliqué sur chariot
                    else if(elementChoisi.getClass()==VueChoixDirection.class){ // on a choisi une direction
                        VueChoixDirection vueDirectionChoisie= (VueChoixDirection)elementChoisi;
                        if(vueDirectionChoisie.getElement().getClass()==Rail.class){ // on a choisi un rail
                            // on va orienter le chariot dans ce rail
                            vueAeroportPanel.GetVueAeroport().getAeroport().ajouterCheminATrajetChariot(vueDirectionChoisie.getChariot(),(Rail)vueDirectionChoisie.getElement());
                        }
                        else if(vueDirectionChoisie.getElement().getClass()==TapisRoulant.class){ // on a choisi un tapisroulant
                            // on va prendre le bagage sur le tapisroulant
                            vueAeroportPanel.GetVueAeroport().getAeroport().prendreBagageDuTapisRoulant(vueDirectionChoisie.getChariot());
                        }
                        else if(vueDirectionChoisie.getElement().getClass()==Toboggan.class){ // on a choisi un toboggan
                            // on va poser le bagage sur le toboggan
                            vueAeroportPanel.GetVueAeroport().getAeroport().poserBagageSurToboggan(vueDirectionChoisie.getChariot());
                        }
                        else if (vueDirectionChoisie.getElement().getClass()==VoieGarage.class){ // on a choisi la voie garage
                            vueAeroportPanel.GetVueAeroport().getAeroport().ajouterCheminATrajetChariot(vueDirectionChoisie.getChariot(),(VoieGarage)vueDirectionChoisie.getElement());
                        }
                        vueDirectionChoisie.getChariot().setEstArrete(false);

                        vueAeroportPanel.GetVueAeroport().supprimerVuesDirection();
                        
                    } // fin on a choisi une direction
                    else if(elementChoisi.getClass()==VueVoieGarage.class){ // on a cliqué sur une voiegarage
                        vueAeroportPanel.GetVueAeroport().supprimerVuesDirection();
                        //vueAeroportPanel.GetVueAeroport().getAeroport().debloquerChariotVoieGarage((VoieGarage) objetCorresp);
                        vueAeroportPanel.GetVueAeroport().getAeroport().debloquerChariotVoieGarageManuel((VoieGarage) objetCorresp);
                    }
                    else {
                        vueAeroportPanel.GetVueAeroport().supprimerVuesDirection();
                        e.consume();
                    }
                } // fin else si pas en ajout de bagage


            } // fin if si mode manuel
        } // fin si elementChoisi non null
        else {
            vueAeroportPanel.GetVueAeroport().supprimerVuesDirection();
            panelCenter.remove(panelInfo);
            panelInfo = new JPanelInfoGeneral(rectBoundsPanelInfo, vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueChariot"), vueAeroportPanel.GetVueAeroport().getSkin().getMapImages().get("vue.VueBagage"));
            panelInfo.setElement(vueAeroportPanel.getAeroport());
            labelInfoTitre.setText("Général");
            panelCenter.add(panelInfo, null);
            panelCenter.repaint();
        }
         
    } // fin vueAeroportPanel_mouseClicked
    
    
    
    private void afficheChariot(boolean aff) {
        vueAeroportPanel.GetVueAeroport().setAfficheChariot(aff);
    }
    
    private void afficheRail(boolean aff) {
        vueAeroportPanel.GetVueAeroport().setAfficheRail(aff);
    }
    
    private void afficheVoieGarage(boolean aff) {
        vueAeroportPanel.GetVueAeroport().setAfficheVoieGarage(aff);
    }
    
    private void afficheTapisRoulant(boolean aff) {
        vueAeroportPanel.GetVueAeroport().setAfficheTapisRoulant(aff);
    }
    
    private void afficheToboggan(boolean aff) {
        vueAeroportPanel.GetVueAeroport().setAfficheToboggan(aff);
    }
    
    private void afficheBagage(boolean aff) {
        vueAeroportPanel.GetVueAeroport().setAfficheBagage(aff);
    }

    private void afficheIntersection(boolean aff) {
        vueAeroportPanel.GetVueAeroport().setAfficheIntersection(aff);
    }
    
    // -----------------------------------------------------------------------------------------------------------------
    // --------------------------------------- GESTION DU ZOOM ---------------------------------------------------------
    // -----------------------------------------------------------------------------------------------------------------

    private void vueAeroportPanel_keyPressed(KeyEvent e) {
        if (e.getKeyChar() == '+') {
            vueAeroportPanel.GetVueAeroport().setCoefZoom(vueAeroportPanel.GetVueAeroport().getCoefZoom()*1.2);
        }
        else if (e.getKeyChar() == '-') {
            vueAeroportPanel.GetVueAeroport().setCoefZoom(vueAeroportPanel.GetVueAeroport().getCoefZoom()/1.2);
        }
        else if (e.getKeyChar() == 'z' || e.getKeyChar() == 'Z' || e.getKeyCode() == e.VK_UP) {
            vueAeroportPanel.GetVueAeroport().setY0(vueAeroportPanel.GetVueAeroport().getY0()-10);
        }
        else if (e.getKeyChar() == 's' || e.getKeyChar() == 'S' || e.getKeyCode() == e.VK_DOWN) {
            vueAeroportPanel.GetVueAeroport().setY0(vueAeroportPanel.GetVueAeroport().getY0()+10);
        }
        else if (e.getKeyChar() == 'q' || e.getKeyChar() == 'Q' || e.getKeyCode() == e.VK_LEFT) {
            vueAeroportPanel.GetVueAeroport().setX0(vueAeroportPanel.GetVueAeroport().getX0()-10);
        }
        else if (e.getKeyChar() == 'd' || e.getKeyChar() == 'D' || e.getKeyCode() == e.VK_RIGHT) {
            vueAeroportPanel.GetVueAeroport().setX0(vueAeroportPanel.GetVueAeroport().getX0()+10);
        }
    }

    private void vueAeroportPanel_mouseEntered(MouseEvent e) {
        vueAeroportPanel.requestFocus();
    }

    private void vueAeroportPanel_mouseExited(MouseEvent e) {
        vueAeroportPanel.transferFocus();
    }

    private void vueAeroportPanel_mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            vueAeroportPanel.GetVueAeroport().setCoefZoom(vueAeroportPanel.GetVueAeroport().getCoefZoom()*-1.1*(double)e.getWheelRotation());
        }
        else if (e.getWheelRotation() > 0) {
            vueAeroportPanel.GetVueAeroport().setCoefZoom(vueAeroportPanel.GetVueAeroport().getCoefZoom()/1.1*(double)e.getWheelRotation());
        }
        // else if (e.get)
    }

}