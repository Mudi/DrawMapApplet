/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package drawmapsovelma;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

/**
 *
 * @author Olli Koskinen
 */
//TODO: Applet version for browsers
public class GUI extends JApplet {
    
    private float lastDelta = 1;
    final JFileChooser chooser = new JFileChooser();
    private DrawMapController controller;
    // napit
    private JButton loadMap;
    private JButton lenghten;
    private JButton reloadMap;
    private JButton showLineCount;
    // Paneelit
    private JPanel paaPaneeli;
    private JPanel karttaPaneeli;
    private JPanel buttonPanel;
    private JPanel sliderPanel;
    private JPanel commandPanel;
    // reunat
    private Border reunus;
    //Slider
    private JSlider lengthSlider;
    //Slider values
    static final int LENGTH_MIN = 1;
    static final int LENGTH_MAX = 50;
    static final int LENGTH_INIT = 5; //initial length
    //JLabels
    private JLabel lengthenLabel;
    private JLabel lengthenCount;



    /*
     * Applet starts here
     * 
     */
    @Override
    public void init() {
        GUI view = new GUI();
        DrawMap dm = new DrawMap();

        this.controller = new DrawMapController(view, dm);
        registerComponents();
    }

    //Constructor
    public GUI() {
        //  super("DrawMap");
    }

    public void registerComponents() {
        //  setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //  setSize(300 * DrawMap.SCALE + 10, 225 * DrawMap.SCALE + 30);

        //Slider
        lengthSlider = new JSlider(JSlider.HORIZONTAL, LENGTH_MIN, LENGTH_MAX, LENGTH_INIT);
        
        //JLabel
        lengthenLabel = new JLabel("Lengthen lines by");
        lengthenCount = new JLabel("5");

        // Buttons
        loadMap = new JButton("Load Map");
        reloadMap = new JButton("Reload Map");
        showLineCount = new JButton("LineCount");

        // Panels
        paaPaneeli = new JPanel();
        buttonPanel = new JPanel();
        karttaPaneeli = controller.getDrawMapPanel();
        sliderPanel = new JPanel();
        commandPanel = new JPanel();

        // Layouts
        paaPaneeli.setLayout(new BorderLayout());
        commandPanel.setLayout(new BorderLayout());
        buttonPanel.setLayout(new GridLayout(1,3));
        sliderPanel.setLayout(new FlowLayout());
        sliderPanel.add(lengthenLabel,FlowLayout.LEFT);
        sliderPanel.add(lengthSlider);
        sliderPanel.add(lengthenCount);
        buttonPanel.add(loadMap);
        buttonPanel.add(reloadMap);
        buttonPanel.add(showLineCount);
        commandPanel.add(sliderPanel,BorderLayout.NORTH);
        commandPanel.add(buttonPanel,BorderLayout.SOUTH);
        paaPaneeli.add(commandPanel, BorderLayout.SOUTH);
        paaPaneeli.add(karttaPaneeli);

        // Borders
        reunus = BorderFactory.createEtchedBorder();
        paaPaneeli.setBorder(reunus);
        loadMap.setBorder(reunus);
        reloadMap.setBorder(reunus);
        showLineCount.setBorder(reunus);
        karttaPaneeli.setBorder(reunus);

        // Center the window
        // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //Dimension frameSize = getSize();

        //setLocation(new Point((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.width) / 2));

        //Action listeners
        showLineCount.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                controller.showLineCount();
                repaint();
            }
        });


        lengthSlider.setMajorTickSpacing(10);
        lengthSlider.setMinorTickSpacing(2);
        lengthSlider.setPaintTicks(true);
        lengthSlider.setPaintLabels(true);
        lengthSlider.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                 float delta = (float) source.getValue();
              if(DrawMap.mapsLoaded){
                if (!source.getValueIsAdjusting()) {  //jos arvoa ei muuteta
                    controller.lenghtenLines(-lastDelta);
                    repaint();
                    lastDelta = delta;
                    controller.lenghtenLines(delta);
                }
              }
                lengthenCount.setText(String.valueOf(delta));
            }
        });



        //Skaalauksen muunnos
        //TODO korjaa scaalaus
        paaPaneeli.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent ke) {
                int keyCode = ke.getKeyCode();
                if (keyCode == KeyEvent.VK_UP) {    // Päivitetään näkymä panamalla nuolta ylöspäin
                    controller.increaseScale();
                    controller.redrawMapPanel();
                    repaint();
                } else if (keyCode == KeyEvent.VK_DOWN) {    // Päivitetään näkymä panamalla nuolta ylöspäin
                    controller.decreaseScale();
                    repaint();
                }
            }
        });



        // Load map button function
        loadMap.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                String inputFileName =
                        System.getProperty("user.home");
                chooser.setCurrentDirectory(new File(inputFileName));

                int state = chooser.showOpenDialog(null);
                File file = chooser.getSelectedFile();

                if ((file != null) && (state == JFileChooser.APPROVE_OPTION)) {
                    if (DrawMap.debug) {
                        JOptionPane.showMessageDialog(null, file.getPath());
                        
                    }
                    controller.drawMapFromFile(file);
                } else if (state == JFileChooser.CANCEL_OPTION) {
                    if (DrawMap.debug) {
                        JOptionPane.showMessageDialog(null, "Canceled");
                    }
                }
                controller.redrawMapPanel();
                repaint();
            }
        });
        reloadMap.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                repaint();
            }
        });
        setContentPane(paaPaneeli);
        setVisible(true);
    }

    void setLengthenCount(float delta) {
        lengthenCount.setText(String.valueOf(delta));

    }
}
