
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PIXELSORT {
    public PIXELSORT() {

    }
    public void sortPixels(String pathname) throws IOException {
        String outName = pathname.substring(pathname.lastIndexOf("/") + 1, pathname.lastIndexOf("."));
        BufferedImage img = getInputImage(pathname); // input image
        ArrayList<ArrayList<Color>> pixelArray = new ArrayList<ArrayList<Color>>();
        for (int x = 0; x < img.getWidth(); x++) { // "height pointer"
            ArrayList<Color> tempArray = new ArrayList<Color>();
            for (int y = 0; y < img.getHeight(); y++) { // "width pointer"
                tempArray.add(new Color(img.getRGB(x, y)));
            }
            Collections.sort(tempArray, (o1, o2) -> singleValue(o1.getRGB()) - singleValue(o2.getRGB())); // BZZZZZTT
            pixelArray.add(tempArray);
        }
        BufferedImage sortedImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < img.getWidth(); x++) { // "height pointer"
            for (int y = 0; y < img.getHeight(); y++) { // "width pointer"
                sortedImage.setRGB(x, y, pixelArray.get(x).get(y).getRGB());
            }
        }
        ImageIO.write(sortedImage, "png", new File("./sorted_" + outName + ".png"));
    }
    private BufferedImage getInputImage(String pathname) throws FileNotFoundException { // gets the image file
        BufferedImage img = null;
        // getting the image file
        try {
            img = ImageIO.read(new File(pathname)); // set the file at pathname as image
        } catch (IOException e) {
            System.out.println(e);
        }
        return img;
    }

    private int singleValue(int rgbValue) {
        int r = (rgbValue >> 16) & 0xff;
        int g = (rgbValue >> 8) & 0xff; // getting the rgb values
        int b = rgbValue & 0xff;
        return ((r * 299 + g * 587 + b * 114) / 1000);
    }

    public static void main(String args[]) {
        try {
            PIXELSORT s = new PIXELSORT();
            s.sortPixels(args[0]);
        } catch (Exception e) {
            System.out.println("Usage: java PIXELSORT <pathname> " + args[0]);
            System.out.println("False arguments or missing pathname");
        }
    }
}

// 78 79 32 77 65 73 68 69 78 83 63