
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class PIXELSORT {
    private int bufferedImageWidth;
    private int bufferedImageHeight;
    private String progressString;

    private PIXELSORT() {}

    void sortPixels(String pathname, String maskPath) throws IOException {
        String outName = pathname.substring(pathname.lastIndexOf("/") + 1, pathname.lastIndexOf("."));

        BufferedImage imageToSort = getInputImage(pathname);

        bufferedImageWidth = imageToSort.getWidth();
        bufferedImageHeight = imageToSort.getHeight();

        Color[][] imgArray = extractImg(imageToSort);

        updateProgressString(20);

        BufferedImage imageMask;
        boolean[][] mask = null;

        if(maskPath != null) {
            imageMask = getInputImage(maskPath);
            mask = extractMask(imageMask);
        }

        updateProgressString(40);

        Color[][] sortedPixels = sort(imageToSort);

        updateProgressString(90);

        ImageIO.write(applyMask(imgArray, sortedPixels, mask), "png", new File("./sorted_" + outName + ".png"));
        updateProgressString(100);
    }

    private BufferedImage getInputImage(String pathname) throws FileNotFoundException {
        BufferedImage img = null;

        try {
            img = ImageIO.read(new File(pathname));
        } catch (IOException e) {
            System.out.println(e);
        }
        return img;
    }

    private int rgbAsSingleValue(int rgbValue) {
        int r = (rgbValue >> 16) & 0xff;
        int g = (rgbValue >> 8) & 0xff;
        int b = rgbValue & 0xff;

        return ((r * 299 + g * 587 + b * 114) / 1000);
    }

    private boolean[][] extractMask(BufferedImage maskImg) {
        boolean[][] mask = new boolean[bufferedImageWidth][bufferedImageHeight];

        for (int x = 0; x < bufferedImageWidth; x++) {
            for (int y = 0; y < bufferedImageHeight; y++) {
                if(maskImg.getRGB(x, y) == 0xFF000000) {
                    mask[x][y] =  false;
                }
                if(maskImg.getRGB(x, y) == 0xFFFFFFFF) {
                    mask[x][y] =  true;
                }
            }
        }
        return mask;
    }

    private Color[][] extractImg(BufferedImage bufferedImage) {
        Color[][] img = new Color[bufferedImageWidth][bufferedImageHeight];

        for (int x = 0; x < bufferedImageWidth; x++) {
            for (int y = 0; y < bufferedImageHeight; y++) {
                img[x][y] = new Color(bufferedImage.getRGB(x, y));
            }
        }
        return img;
    }

    private Color[][] sort(BufferedImage bufferedImage) {
        Color[][] sortedImgArray = new Color[bufferedImageWidth][bufferedImageHeight];

        for (int x = 0; x < bufferedImageWidth; x++) {
            Color[] tempArray = new Color[bufferedImageHeight];

            for (int y = 0; y < bufferedImageHeight; y++) {
                tempArray[y] = new Color(bufferedImage.getRGB(x, y));
            }

            ArrayList<Color> tempArrayAsList = new ArrayList<>(Arrays.asList(tempArray));

            Collections.sort(tempArrayAsList, (o1, o2) -> rgbAsSingleValue(o1.getRGB()) - rgbAsSingleValue(o2.getRGB()));

            for (int y = 0; y < bufferedImageHeight; y++) {
                tempArray[y] = tempArrayAsList.get(y);
            }
            
            sortedImgArray[x] = tempArray;
        }
        return sortedImgArray;
    }

    private BufferedImage applyMask(Color[][] img, Color[][] sortedImgArray, boolean[][] mask) {
        BufferedImage sortedImage = new BufferedImage(bufferedImageWidth, bufferedImageHeight, BufferedImage.TYPE_INT_RGB);

        if(mask == null) {
            for (int x = 0; x < bufferedImageWidth; x++) {
                for (int y = 0; y < bufferedImageHeight; y++) {
                    sortedImage.setRGB(x, y, sortedImgArray[x][y].getRGB());
                }
            }
            return sortedImage;
        } else {
            for (int x = 0; x < bufferedImageWidth; x++) {
                for (int y = 0; y < bufferedImageHeight; y++) {
                    if(mask[x][y]) {
                        sortedImage.setRGB(x, y, img[x][y].getRGB());
                    }
                    if(!mask[x][y]) {
                        sortedImage.setRGB(x, y, sortedImgArray[x][y].getRGB());
                    }
                }
            }
            return sortedImage;
        }
    }

    private void updateProgressString(int percentage) {
        String progressBar = "";

        for(int i = 0; i < (percentage / 5); i++) {
            progressBar += "=";
        }

        int emptySpaceLength = (20 - progressBar.length());

        for(int i = 0; i < emptySpaceLength; i++) {
            progressBar += " ";
        }

        if(progressString != null) {
            String backspaceString = "";
            for(int i = 0; i < progressString.length(); i++) {
                backspaceString += "\b";
            }
            System.out.print(backspaceString);
        }

        progressString = "[" + progressBar + "] " + percentage + "%";

        System.out.print(progressString);
        if(percentage == 100) {
            System.out.println();
            System.out.println("DONE.");
        }
    }

    public static void main(String args[]) {
        PIXELSORT pixelsort = new PIXELSORT();

        String imagePath = null;
        String maskPath = null;

        if(args.length > 1) {
            imagePath = args[0];
            maskPath = args[1];
        } else if (args.length == 1) {
            imagePath = args[0];
        } else {
            System.out.println("Usage: java PIXELSORT <image> [mask]");
        }

        try {
            pixelsort.sortPixels(imagePath, maskPath);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

// 78 79 32 77 65 73 68 69 78 83 63