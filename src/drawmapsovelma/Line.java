package drawmapsovelma;
/**
 *
 * @author Olli Koskinen
 */
class Line {

    private float x1;
    private float y1;
    private float x2;
    private float y2;	

    public Line(float x1, float y1, float x2, float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    public Line getLine(){
            return this;
}
    
public void lengthen( float delta){
    double  angle = Math.atan2(y2 - y1,x2- x1);
    x1 = x1 - delta * (float)Math.cos(angle);
    y1 = y1 - delta * (float)Math.sin(angle);
    x2 = x2 + delta * (float)Math.cos(angle);
    y2 = y2 + delta * (float)Math.sin(angle);
  }


    public float getX1() {
        return x1;
    }

    public float getX2() {
        return x2;
    }

    public float getY1() {
        return y1;
    }

    public float getY2() {
        return y2;
    }
}
