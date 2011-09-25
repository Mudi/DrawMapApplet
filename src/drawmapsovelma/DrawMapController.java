package drawmapsovelma;

import java.io.File;

import javax.swing.JPanel;

/**
 *
 * @author Olli Koskinen
 */
public class DrawMapController {

    private GUI view;
    private DrawMap dm;

    public DrawMapController(GUI view, DrawMap dm) {
        this.view = view;
        this.dm = dm;
    }

    void drawMapFromFile(File file) {
        dm.clearLineArrayList();
        dm.loadMapFromFile(file);
    }

    void redrawMapPanel() {
        dm.drawMap();
    }
    
    void increaseScale(){
    	dm.increaseScale();
    }
    
    void lenghtenLines(float delta){
        dm.lenghtenLines(delta);
    }
    
    void decreaseScale(){
    	dm.decreaseScale();
    }

    void showLineCount() {
        dm.showLineCount();
    }

	public JPanel getDrawMapPanel() {
		return dm;
	}

    void setLengthenCount(float delta) {
        view.setLengthenCount(delta);
    }
}
