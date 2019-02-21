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
    BufferedImage newImageInterpolation2DDraw;
    File newImageSmoothOut;
    File newImageSharpOut;
    File newImageInterpolation2D;

    int pixelTracker = 0;

    public Image() {
        try {
            File input = new File("C:\\Users\\Jacob\\Desktop\\studia\\UEK\\Metody Numeryczne\\projekt\\interpolacja\\src\\mars.png");   //należy wpisać scieżke bezwzględną do pliku
            System.out.println("Is readable "+input.canRead());

            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();

            newImageSmoothOutDraw = new BufferedImage(width-6, height, BufferedImage.TYPE_INT_RGB);
            newImageSharpOutDraw = new BufferedImage(width-6, height, BufferedImage.TYPE_INT_RGB);
            newImageInterpolation2DDraw = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            newImageSmoothOut = new File("C:\\Users\\Jacob\\Desktop\\studia\\UEK\\Metody Numeryczne\\projekt\\interpolacja\\src\\smoothOut.png"); //należy wpisać scieżke bezwzględną miejsca zapisu pliku
            newImageSharpOut = new File("C:\\Users\\Jacob\\Desktop\\studia\\UEK\\Metody Numeryczne\\projekt\\interpolacja\\src\\sharpOut.png"); //należy wpisać scieżke bezwzględną miejsca zapisu pliku
            newImageInterpolation2D = new File("C:\\Users\\Jacob\\Desktop\\studia\\UEK\\Metody Numeryczne\\projekt\\interpolacja\\src\\interpolation2D.png");

        } catch (Exception e) {
            System.out.println("Error, reason: " + e);
        }
    }

    public void smoothOut() {
        System.out.println("----------SmoothOut--------------");
        long startTime = System.nanoTime();
        for(int i=0; i<height; i++) {
            for(int j=0; j<width-4; j++) {

                if (j==0 | j==1 | j==width-1 | j==width-2) {
                    Color border = new Color(image.getRGB(j,i));
                    int RGB = (border.getRed()<<16) | (border.getGreen()<<8) | border.getBlue();
                    newImageSmoothOutDraw.setRGB(j, i, RGB);
                    continue;
                }

                List<Float> x = new ArrayList<>();

                List<Float> R = new ArrayList<>();
                List<Float> G = new ArrayList<>();
                List<Float> B = new ArrayList<>();

                float X = 2;

                for(float t=0; t<5;t++) {
                    if (t==2) continue;
                    Color c = new Color(image.getRGB((int) (t+j), i));

                    R.add( ((float) c.getRed() )/ 255 );
                    G.add( ((float) c.getGreen() )/255);
                    B.add( ((float) c.getBlue() )/255);

                    x.add(t);

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

        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Time: "+estimatedTime/1.0E-9);
        try {ImageIO.write(newImageSmoothOutDraw, "PNG", newImageSmoothOut);}
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sharpOut() {
        System.out.println("----------SharpOut---------------");
        long startTime = System.nanoTime();
        for(int i=0; i<height; i++) {
            for(int j=0; j<width-4; j++) {

                if (j==0 | j==1 | j==width-1 | j==width-2) {
                    Color border = new Color(image.getRGB(j,i));
                    int RGB = (border.getRed()<<16) | (border.getGreen()<<8) | border.getBlue();
                    newImageSmoothOutDraw.setRGB(j, i, RGB);
                    continue;
                }

                List<Float> x = new ArrayList<>();

                List<Float> R = new ArrayList<>();
                List<Float> G = new ArrayList<>();
                List<Float> B = new ArrayList<>();

                float X = 0;

                for(float t=1; t<5;t++) {

                    Color c = new Color(image.getRGB((int) (t+j), i));

                    R.add( ((float) c.getRed() )/ (float) 255 );
                    G.add( ((float) c.getGreen() )/ (float) 255);
                    B.add( ((float) c.getBlue() )/ (float) 255);

                    x.add(t);

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

        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Time: "+estimatedTime/1.0E-9);
        try {ImageIO.write(newImageSharpOutDraw, "PNG", newImageSharpOut);}
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void interpolation2D() {
        System.out.println("----------interpolation2D---------------");
        long startTime = System.nanoTime();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if (j == 0 | i == 0 | i == height-1 | j == width-1 )  {
                    Color border = new Color(image.getRGB(j, i));
                    int RGB = (border.getRed() << 16) | (border.getGreen() << 8) | border.getBlue();
                    newImageInterpolation2DDraw.setRGB(j, i, RGB);
                    continue;
                }

                List<Integer> x = new ArrayList<>();
                List<Integer> y = new ArrayList<>();

                List<Float> R = new ArrayList<>();
                List<Float> G = new ArrayList<>();
                List<Float> B = new ArrayList<>();

                int X = j;
                int Y = i;

                for(int t=-1; t<2;t+=2) {

                    for(int k=-1; k<2; k+=2) {

                        Color c = new Color(image.getRGB(j+t,i+k));
                        R.add((float) c.getRed()/255);
                        G.add((float) c.getGreen()/255);
                        B.add((float) c.getBlue()/255);

                        x.add(j+t);
                        y.add(i+k);
                    }
                }


                Polynomial2D poly2DR = new Polynomial2D(x,y,R,X,Y);
                float rValue = poly2DR.calculateValueInXY()*255;

                Polynomial2D poly2DG = new Polynomial2D(x,y,G,X,Y);
                float gValue = poly2DG.calculateValueInXY()*255;

                Polynomial2D poly2DB = new Polynomial2D(x,y,B,X,Y);
                float bValue = poly2DB.calculateValueInXY()*255;



                int RGB = ( ((int) rValue) << 16 ) | ( ((int) gValue) << 8 ) | (int) bValue;

                if (RGB<0) RGB=0;
                if (RGB>16777215) RGB=16777215;

                newImageInterpolation2DDraw.setRGB(j, i, RGB);

            }
        }

        long estimatedTime = (System.nanoTime() - startTime);
        System.out.println("Time: "+estimatedTime/1.0E-9);

        try {
            ImageIO.write(newImageInterpolation2DDraw, "PNG", newImageInterpolation2D);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}