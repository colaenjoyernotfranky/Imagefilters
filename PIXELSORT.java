
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
    public void sortPixels(String pathname, String maskPath) throws IOException {
        String outName = pathname.substring(pathname.lastIndexOf("/") + 1, pathname.lastIndexOf("."));
        BufferedImage img = getInputImage(pathname); // input image
        BufferedImage maskImg = getInputImage(maskPath); // mask

        ArrayList<Color> imgArray = extractImg(img);
        ArrayList<Boolean> mask = extractMask(maskImg);
        ArrayList<ArrayList<Color>> pixel = sort(img);

        ImageIO.write(applyMask(img, pixel, mask, imgArray), "png", new File("./sorted_" + outName + ".png"));
        System.out.println("Done");
    }

    //Util-Functions//////////////////////////////////////////////////////////////////////////////////////////
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

    private ArrayList<Boolean> extractMask(BufferedImage maskImg) {
        ArrayList<Boolean> mask = new ArrayList<Boolean>();
        for (int x = 0; x < maskImg.getWidth(); x++) { // "height pointer"
            for (int y = 0; y < maskImg.getHeight(); y++) { // "width pointer"
                boolean maskBoolean = false; 
                if(maskImg.getRGB(x, y) == 0xFF000000)
                    maskBoolean = false;
                if(maskImg.getRGB(x, y) == 0xFFFFFFFF)
                    maskBoolean = true;
                mask.add(maskBoolean);
            }
        }
        return mask;
    }

    private ArrayList<Color> extractImg(BufferedImage bufferedImage) {
        ArrayList<Color> img = new ArrayList<Color>();
        for (int x = 0; x < bufferedImage.getWidth(); x++) { // "height pointer"
            for (int y = 0; y < bufferedImage.getHeight(); y++) { // "width pointer"
                img.add(new Color(bufferedImage.getRGB(x, y)));
            }
        }
        return img;
    }

    private ArrayList<ArrayList<Color>> sort(BufferedImage bufferedImage) {
        ArrayList<ArrayList<Color>> sortedImgArray = new ArrayList<ArrayList<Color>>();
        for (int x = 0; x < bufferedImage.getWidth(); x++) { // "height pointer"
            ArrayList<Color> tempArray = new ArrayList<Color>();
            for (int y = 0; y < bufferedImage.getHeight(); y++) { // "width pointer"
                tempArray.add(new Color(bufferedImage.getRGB(x, y)));
            }
            Collections.sort(tempArray, (o1, o2) -> singleValue(o1.getRGB()) - singleValue(o2.getRGB()));
            sortedImgArray.add(tempArray);
        }
        return sortedImgArray;
    }

    private BufferedImage applyMask(BufferedImage bufferedImage, ArrayList<ArrayList<Color>> sortedImgArray, ArrayList<Boolean> mask, ArrayList<Color> img) {
        BufferedImage sortedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        int index = 0;
        for (int x = 0; x < bufferedImage.getWidth(); x++) { // "height pointer"
            for (int y = 0; y < bufferedImage.getHeight(); y++) { // "width pointer"
                if(mask.get(index))
                    sortedImage.setRGB(x, y, img.get(index).getRGB());
                if(!mask.get(index))
                    sortedImage.setRGB(x, y, sortedImgArray.get(x).get(y).getRGB());
                index++;
            }
        }
        return sortedImage;
    }

    public static void main(String args[]) {
        try {
            PIXELSORT s = new PIXELSORT();
            s.sortPixels(args[0], args[1]);
        } catch (Exception e) {
            System.out.println("Usage: java PIXELSORT <image> <mask>");
            System.out.println("False arguments or missing image or mask pathname");
        }
    }
}

// 78 79 32 77 65 73 68 69 78 83 63