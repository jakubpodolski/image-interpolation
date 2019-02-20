import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        List<Integer> x = new ArrayList<>();
        Collections.addAll(x, -2, -1, 0, 1);

        List<Integer> y = new ArrayList<>();
        Collections.addAll(y, -75, -4, -1, 6);

        double X = 2; //=> 41.0

        /*Polynomial test = new Polynomial(x,y,X);
        test.calculateC();
        test.setCoefficients();
        test.showCValues();
        System.out.println(test.calculateYValueInX());*/

        Image testIMG = new Image();
        testIMG.smoothOut();
        testIMG.sharpOut();

    }
}
