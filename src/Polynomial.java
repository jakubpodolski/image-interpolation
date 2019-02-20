import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Polynomial {

    public List<Integer> xValues = new ArrayList<>();
    public List<Float> yValues = new ArrayList<>();
    public List<Float> cValues = new ArrayList<>();
    public List<Float> coefficients = new ArrayList<>(4);
    public int X;

    public Polynomial(List<Integer> x, List<Float> y, int X) {
        this.xValues = x;
        this.yValues = y;
        this.X = X;

    }

    public void calculateC() {
        float c0 = yValues.get(0);
        List<Float> cFirstColumn = new ArrayList<>();
        List<Float> cSecondColumn = new ArrayList<>();

        for(int i=0; i<3;i++){
            cFirstColumn.add((yValues.get(1+i)-yValues.get(i))/(xValues.get(1+i)-xValues.get(i)));
        }

        cSecondColumn.add((cFirstColumn.get(1)-cFirstColumn.get(0))/(xValues.get(2)-xValues.get(0)));
        cSecondColumn.add((cFirstColumn.get(2)-cFirstColumn.get(1))/(xValues.get(3)-xValues.get(1)));

        float c0123 = (cSecondColumn.get(1)-cSecondColumn.get(0))/(xValues.get(3)-xValues.get(0));

        List<Float> cValues = new ArrayList<>();
        Collections.addAll(cValues, c0,cFirstColumn.get(0),cSecondColumn.get(0),c0123);

        this.cValues = cValues;
    }



    public void setCoefficients() {
        float c0 = cValues.get(0);
        float c1 = cValues.get(1);
        float c2 = cValues.get(2);
        float c3 = cValues.get(3);

        float a = xValues.get(0);
        float b = xValues.get(1);
        float c = xValues.get(2);


        coefficients.add(0,c0-(c1*a)+(c2*a*b)-(c3*a*b*c));
        coefficients.add(1,c1+-c2*(a+b)+c3*(a*b+b*c+a*c));
        coefficients.add(2,c2-c3*(a+b+c));
        coefficients.add(3,c3);
    }

    public void showCValues(){
        System.out.println("SHOWING VALUES FOR C, X and Y");
        System.out.println("X and Y values");
        for(int i=0;i<xValues.size();i++){
            System.out.println("x " + xValues.get(i));
            System.out.println("y " + yValues.get(i));
        }

        System.out.println("C values");
        for(int i=0;i<cValues.size();i++){
            System.out.println(cValues.get(i));
        }

        System.out.println("Coefficients");
        for(int i=0;i<coefficients.size();i++){
            System.out.println(coefficients.get(i));
        }
    }

    public float calculateYValueInX (){

        float value = coefficients.get(0);
        for(int i = 1; i < 4; i++){
            value += coefficients.get(i) * Math.pow(X, i);
        }
        return value;
    }
}