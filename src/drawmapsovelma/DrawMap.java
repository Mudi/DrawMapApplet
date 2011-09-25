package drawmapsovelma;

/*
*
* @author Olli Koskinen
 */

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class DrawMap extends JPanel {
    public static int     SCALE = 3;
    public static final boolean debug = false;
    private ArrayList<Line>  mapLines;
    private ArrayList<Line>  initialMapLines;
    public static boolean mapsLoaded = false;

    public DrawMap() {
        mapLines = new ArrayList<Line>();
    }

    public void loadMapFromFile(File inFile) {
        try {
            readLinesFromFile(inFile);
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(null, "Error while loading a map", "Error loading a map",
                                          JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    private void readLinesFromFile(File inFile) throws IOException {
        // Scanneri lukee tiedostoa rivi kerrallaan
        Scanner freader = new Scanner(new FileReader(inFile));
        int     x1, y1, x2, y2;
        String  line = null;

        // Loopataan rivejä ja parsetaan niitä toisen scannerin avulla
        while (freader.hasNextLine()) {
            line = freader.nextLine();

            Scanner parse = new Scanner(line);
            parse.useDelimiter(",");

            try {
                x1 = Math.abs(Integer.parseInt(parse.next().trim()));
                y1 = Math.abs(Integer.parseInt(parse.next().trim()));
                x2 = Math.abs(Integer.parseInt(parse.next().trim()));
                y2 = Math.abs(Integer.parseInt(parse.next().trim()));
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "The file data is corrupted. Please provide a new file.",
                                              "Corrupted file", JOptionPane.WARNING_MESSAGE);
                break;
            }

            // Debuggausta varten, nähdään mitkä luvut on luettu
            if (debug) {
                System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
            }
            this.mapLines.add(new Line(x1, y1, x2, y2));
            //this.initialMapLines.add(new Line(x1, y1, x2, y2));
        }
        
        this.mapsLoaded = true;

        // System.out.println("readLinesFromFile metodista kutsuttaessa mapLines.size() on: "+
        if (debug) {
            this.mapLines.size();
        }
        freader.close();

        repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.red);
        g2.setStroke(new BasicStroke(5));
        super.paint(g);
        g.setColor(Color.red);

        // Keskitetään rectangle
        int x = (int) ((int) getWidth() * 0.15);
        int y = (int) ((int) getHeight() * 0.15);

        // Piirretään aloitusneliö
        g.drawRect(x, y, 200 * SCALE, 135 * SCALE);
        g.setColor(Color.RED);

        // Loopataan läpi ja piirretään kaikki mahdolliset viivat ArrayLististä
        if (this.mapLines != null) {
            for (Line l : this.mapLines) {
                Line2D line = new Line2D.Double(l.getX1() * SCALE + x, l.getY1() * SCALE + y, l.getX2() * SCALE + x,
                                                l.getY2() * SCALE + y);
                g2.draw(line);
            }
        }
    }

    public ArrayList<Line> getMapLines() {
        return mapLines;
    }

    void clearLineArrayList() {
        mapLines.clear();
    }

    public void increaseScale() {
        if (SCALE < 10) {
            SCALE++;
        } else {
            SCALE = 10;
        }
    }

    public void decreaseScale() {
        if (SCALE > 1) {
            SCALE--;
        } else {
            SCALE = 1;
        }
    }

    void drawMap() {
        this.repaint();
    }

    // Näytetään käyttäjälle viivojen määrä popupilla
    void showLineCount() {
        JOptionPane.showMessageDialog(null, "Line count is: " + this.mapLines.size());
    }

    void lenghtenLines(float delta) {
          for(Line l:mapLines){
                l.lengthen(delta);
            }
    }
}

