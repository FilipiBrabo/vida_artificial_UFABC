import java.awt.Color;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class BurningShip {
    public static void main(String[] args) throws Exception {
        int width = 1920, height = 1920, max = 1000;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int black = 0;
        int[] colors = new int[max];
        for (int i = 0; i<max; i++) {
            colors[i] = Color.HSBtoRGB(i/256f, 1, i/(i+8f));
        }

        double centerx = -1.76, centery = -0.04;
        double del = 0.06;
        double minx = centerx - del, miny = centery - del;
        System.out.println(minx);
        System.out.println(miny);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                double c_re = col * 2 * del / width + minx;
                double c_im = row * 2 * del / height + miny;
                ComplexNumber c = new ComplexNumber(c_re, c_im);
                ComplexNumber z = new ComplexNumber(0, 0);
                double r2;
                int iteration = 0;
                while (z.mod() < 4 && iteration < max) {
                    z = z.abs().square();
                    z.add(c);
                    iteration++;
                }
                if (iteration < max) image.setRGB(col, row, colors[iteration]);
                else image.setRGB(col, row, black);
            }
        }
        ImageIO.write(image, "png", new File("burningship.png"));
    }
}
