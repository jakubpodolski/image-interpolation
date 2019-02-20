import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;


class Image {
    BufferedImage image;
    int width;
    int height;
    BufferedImage newImageSmoothOutDraw;
    BufferedImage newImageSharpOutDraw;
    File newImageSmoothOut;
    File newImageSharpOut;

    int pixelTracker = 0;

    public Image() {
        try {
            File input = new File("C:\\User\\");   //należy wpisać scieżke bezwzględną do pliku
            System.out.println("Is readable "+input.canRead());

            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();

            newImageSmoothOutDraw = new BufferedImage(width-4, height, BufferedImage.TYPE_INT_RGB);
            newImageSharpOutDraw = new BufferedImage(width-4, height, BufferedImage.TYPE_INT_RGB);
            newImageSmoothOut = new File("C:\\Users\\"); //należy wpisać scieżke bezwzględną miejsca zapisu pliku
            newImageSharpOut = new File("C:\\Users\\"); //należy wpisać scieżke bezwzględną miejsca zapisu pliku

        } catch (Exception e) {
            System.out.println("Error, reason: " + e);
        }
    }

    public void smoothOut() {
        System.out.println("----------SmoothOut--------------");
        for(int i=0; i<height; i++) {
            for(int j=0; j<width-4; j++) {

                if (j==0 | j==1 | j==width-4| j==width-5) {
                    Color border = new Color(image.getRGB(j,i));
                    int RGB = (border.getRed()<<16) | (border.getGreen()<<8) | border.getBlue();
                    newImageSmoothOutDraw.setRGB(j, i, RGB);
                    continue;
                }

                List<Integer> x = new ArrayList<>();

                List<Float> R = new ArrayList<>();
                List<Float> G = new ArrayList<>();
                List<Float> B = new ArrayList<>();

                int X = j+2;

                for(int t=0; t<5;t++) {
                    if (t==2) continue;
                    Color c = new Color(image.getRGB((t+j), i));

                    R.add((float) c.getRed()/255);
                    G.add((float) c.getGreen()/255);
                    B.add((float) c.getBlue()/255);

                    x.add(t+j);

                }

                Polynomial polyR = new Polynomial(x, R, X);
                Polynomial polyG = new Polynomial(x, G, X);
                Polynomial polyB = new Polynomial(x, B, X);

                polyR.calculateC();
                polyR.setCoefficients();
                float rValue = polyR.calculateYValueInX()*255;


                polyG.calculateC();
                polyG.setCoefficients();
                float gValue = polyG.calculateYValueInX()*255;

                polyB.calculateC();
                polyB.setCoefficients();
                float bValue = polyB.calculateYValueInX()*255;



                int RGB = ( ((int) rValue) << 16 ) | ( ((int) gValue) << 8 ) | (int) bValue;
                if (RGB<0) RGB=0;
                if (RGB>16777215) RGB=16777215;


                newImageSmoothOutDraw.setRGB(pixelTracker, i, RGB);
                pixelTracker++;
            }
            pixelTracker=0;
        }

        try {ImageIO.write(newImageSmoothOutDraw, "PNG", newImageSmoothOut);}
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sharpOut() {
        System.out.println("----------SharpOut---------------");
        for(int i=0; i<height; i++) {
            for(int j=0; j<width-4; j++) {

                if (j==0 | j==1 | j==width-5| j==width-6) {
                    Color border = new Color(image.getRGB(j,i));
                    int RGB = (border.getRed()<<16) | (border.getGreen()<<8) | border.getBlue();
                    newImageSharpOutDraw.setRGB(j, i, RGB);
                    continue;
                }

                List<Integer> x = new ArrayList<>();

                List<Float> R = new ArrayList<>();
                List<Float> G = new ArrayList<>();
                List<Float> B = new ArrayList<>();

                int X = j;

                for(int t=1; t<5;t++) {

                    Color c = new Color(image.getRGB((t+j), i));

                    R.add((float) c.getRed()/255);
                    G.add((float) c.getGreen()/255);
                    B.add((float) c.getBlue()/255);

                    x.add(t+j);

                }

                Polynomial polyR = new Polynomial(x, R, X);
                Polynomial polyG = new Polynomial(x, G, X);
                Polynomial polyB = new Polynomial(x, B, X);

                polyR.calculateC();
                polyR.setCoefficients();
                float rValue = polyR.calculateYValueInX()*255;


                polyG.calculateC();
                polyG.setCoefficients();
                float gValue = polyG.calculateYValueInX()*255;

                polyB.calculateC();
                polyB.setCoefficients();
                float bValue = polyB.calculateYValueInX()*255;



                int RGB = ( ((int) rValue) << 16 ) | ( ((int) gValue) << 8 ) | (int) bValue;
                if (RGB<0) RGB=0;
                if (RGB>16777215) RGB=16777215;



                newImageSharpOutDraw.setRGB(pixelTracker, i, RGB);
                pixelTracker++;
            }
            pixelTracker=0;
        }

        try {ImageIO.write(newImageSharpOutDraw, "PNG", newImageSharpOut);}
        catch(Exception e){
            e.printStackTrace();
        }
    }
}