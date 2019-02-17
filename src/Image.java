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
            File input = new File("C:\\Users\\Jacob\\Desktop\\studia\\UEK\\Metody Numeryczne\\projekt\\interpolacja\\src\\rgb_test-50-dot.png");
            System.out.println("Is readable "+input.canRead());

            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();

            newImageSmoothOuttoDraw = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            newImageSmoothOut = new File("C:\\Users\\Jacob\\Desktop\\studia\\UEK\\Metody Numeryczne\\projekt\\interpolacja\\src\\newImageSmoothOut.png");
            newImageSharpOut = new File("C:\\Users\\Jacob\\Desktop\\studia\\UEK\\Metody Numeryczne\\projekt\\interpolacja\\src\\newImageSharpOut.png");

        } catch (Exception e) {
            System.out.println("Error, reason: " + e);
        }
    }

    public void smoothOut() {
        for(int i=0; i<height; i++) {
            for(int j=0; j<width-4; j++) {

                List<Integer> x = new ArrayList<>();
                List<Integer> y = new ArrayList<>();
                int X = j+2;

                for(int t=0; t<5;t++) {
                    if (t==2) continue;

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



                newImageSmoothOuttoDraw.setRGB(pixelTracker, i, (int) RGB);
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
        int count = 0;
        for(int i=0; i<height; i++) {
            for(int j=0; j<width; j++) {

                Color c = new Color(image.getRGB(j, i));

                //System.out.println("S.No: " + count + " Red: " + c.getRed() +"  Green: " + c.getGreen() + " Blue: " + c.getBlue());
                System.out.println(c.getRed()+c.getGreen()+c.getBlue());
            }
        }
    }
}
