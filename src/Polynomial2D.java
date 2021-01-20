import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Polynomial2D {
    public List<Integer> xValues = new ArrayList<>();
    public List<Integer> yValues = new ArrayList<>();
    public List<Float> color = new ArrayList<>();
    public List<Float> cValues = new ArrayList<>();
    public List<Float> coefficients = new ArrayList<>(4);
    public int X;
    public int Y;

    public Polynomial2D(List<Integer> x, List<Integer> y, List<Float> color ,int X, int Y) {
        this.xValues = x;
        this.yValues = y;
        this.color = color;
        this.X = X;
        this.Y = Y;

    }

    public float calculateValueInXY () {

        float fxy1 = ( ((float) (xValues.get(2)-X) / (xValues.get(2)-xValues.get(1)) )*color.get(0) )+(((float) (X-xValues.get(1))/(xValues.get(2)-xValues.get(1)))*color.get(2));
        float fxy2 = ( ((float) (xValues.get(2)-X) / (xValues.get(2)-xValues.get(1)) )*color.get(1) )+(((float) (X-xValues.get(1))/(xValues.get(2)-xValues.get(1)))*color.get(3));

        float value = ( ((float) (yValues.get(2)-Y) / (yValues.get(2)-yValues.get(1)) )*fxy1 )+(((float) (Y-yValues.get(1))/(yValues.get(2)-yValues.get(1)))*fxy2);
        if (value>1) return 1;
        else if(value<0) return 0;
        else return value;
    }
}
