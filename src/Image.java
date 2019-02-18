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
    BufferedImage newImageSmoothOuttoDraw;
    BufferedImage newImageSharpOutDraw;
    File newImageSmoothOut;
    File newImageSharpOut;

    int pixelTracker = 0;

    public Image() {
        try {
            //File input = new File("/home/jacob/Desktop/image-interpolation/src/rgb_test-300.png");
            File input = new File("/home/jacob/Desktop/image-interpolation/src/test_complex.jpeg");
            System.out.println("Is readable "+input.canRead());

            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();

            newImageSmoothOuttoDraw = new BufferedImage(width-4, height, BufferedImage.TYPE_INT_RGB);
            newImageSharpOutDraw = new BufferedImage(width-4, height, BufferedImage.TYPE_INT_RGB);
            newImageSmoothOut = new File("/home/jacob/Desktop/image-interpolation/src/newImageSmoothOut.png");
            newImageSharpOut = new File("/home/jacob/Desktop/image-interpolation/src/newImageSharpOut.png");

        } catch (Exception e) {
            System.out.println("Error, reason: " + e);
        }
    }

    public void smoothOut() {
        System.out.println("----------SmoothOut--------------");
        for(int i=0; i<height; i++) {
            for(int j=0; j<width-4; j++) {

                if (j==1 | j ==2 | j==width-5| j==width-6) {
                    Color border = new Color(image.getRGB(j,i));
                    int RGB = (border.getRed()<<16)|(border.getGreen()<<8)|border.getBlue();
                    newImageSmoothOuttoDraw.setRGB(j, i, RGB);
                    continue;
                }

                List<Integer> x = new ArrayList<>();

                List<Integer> R = new ArrayList<>();
                List<Integer> G = new ArrayList<>();
                List<Integer> B = new ArrayList<>();

                int X = j+2;

                for(int t=0; t<5;t++) {
                    if (t==2) continue;

                    Color c = new Color(image.getRGB((t+j), i));

                    R.add(c.getRed());
                    G.add(c.getGreen());
                    B.add(c.getBlue());

                    x.add(t);
                }

                Polynomial polyR = new Polynomial(x, R, X);
                Polynomial polyG = new Polynomial(x, G, X);
                Polynomial polyB = new Polynomial(x, B, X);

                polyR.calculateC();
                polyR.setCoefficients();

                polyG.calculateC();
                polyG.setCoefficients();

                polyB.calculateC();
                polyB.setCoefficients();


                int RGB = (polyR.calculateYValueInX()<<16)|(polyG.calculateYValueInX()<<8)|polyB.calculateYValueInX();

                newImageSmoothOuttoDraw.setRGB(pixelTracker, i, RGB);
                pixelTracker++;
            }
            pixelTracker=0;
        }

        try {ImageIO.write(newImageSmoothOuttoDraw, "PNG", newImageSmoothOut);}
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sharpOut() {
        System.out.println("-------------SharpOut--------------");
        for(int i=0; i<height; i++) {
            for(int j=0; j<width-4; j++) {

                List<Integer> x = new ArrayList<>();
                List<Integer> y = new ArrayList<>();
                int X = j;

                for(int t=1; t<5;t++) {
                    Color c = new Color(image.getRGB(j+t, i));

                    String rgbValueToHex = String.format("%02X", c.getRed())+String.format("%02X", c.getGreen())+String.format("%02X", c.getBlue());

                    int hexToInt = Integer.parseInt(rgbValueToHex,16);

                    y.add(hexToInt);
                    x.add(t);
                }
                Polynomial polyOne = new Polynomial(x,y, X);

                polyOne.calculateC();
                polyOne.setCoefficients();

                int RGB = polyOne.calculateYValueInX();

                System.out.println("Y in X value: " + polyOne.calculateYValueInX());

                newImageSharpOutDraw.setRGB(pixelTracker, i, (int) RGB);
                pixelTracker++;
            }
            pixelTracker=0;
        }

        try {ImageIO.write(newImageSharpOutDraw, "PNG", newImageSharpOut);}
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void sharpOutVer2() {
        for(int i=0; i<height; i++) {
            for(int j=0; j<width-4; j++) {

                List<Integer> x = new ArrayList<>();
                //List<Integer> y = new ArrayList<>();
                List<Integer> R = new ArrayList<>();
                List<Integer> G = new ArrayList<>();
                List<Integer> B = new ArrayList<>();

                int X = j;

                for(int t=1; t<5;t++) {


                    Color c = new Color(image.getRGB(j+t, i));

                    R.add(c.getRed());
                    G.add(c.getGreen());
                    B.add(c.getBlue());

                    x.add(t);
                }

                Polynomial polyR = new Polynomial(x, R, X);
                Polynomial polyG = new Polynomial(x, G, X);
                Polynomial polyB = new Polynomial(x, B, X);

                polyR.calculateC();
                polyR.setCoefficients();

                polyG.calculateC();
                polyG.setCoefficients();

                polyB.calculateC();
                polyB.setCoefficients();


                int RGB = (polyR.calculateYValueInX()<<16)|(polyG.calculateYValueInX()<<8)|polyB.calculateYValueInX();

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
