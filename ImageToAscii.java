import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

public class ImageToAscii {

	public static void imageToAscii(String imagePath, int percent) {
		try {
			String asciiScale = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/|()1{}[]?-_+~<>i!lI;:,^`'. ";
			char[] asciiPix = asciiScale.toCharArray();
			File input = new File(imagePath);
			BufferedImage image = ImageIO.read(input);
			int width = image.getWidth();
			int height = image.getHeight();
			File file = new File("image.txt");
			FileOutputStream os = new FileOutputStream(file);
			PrintWriter ps = new PrintWriter(os);
			int heightRes = (int) (height / (height * (percent/100.0)));
			heightRes = (int) (heightRes * 2);
			int widthRes = (int) (width / (width * (percent/100.0)));
			for(int i=0; i<height; i += heightRes) {
				for(int j=0; j<width; j += widthRes) {
					double avg = 0;
					for(int k=0; k<heightRes && k+i < height; k++) {
						for(int l=0; l<widthRes && l+j < width; l++) {
							Color c = new Color(image.getRGB(j+l, i+k));
							avg += (int)(c.getRed() * 0.299);
							avg += (int)(c.getGreen() * 0.587);
							avg += (int)(c.getBlue() * 0.114);
						}
					}
					avg /= heightRes * widthRes * 255;
					ps.print(asciiPix[(int)(avg * asciiPix.length)]);
				}
				ps.println();
			}
			ps.close();
			os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static public void main(String args[]) {
		imageToAscii("dog.jpg", 25);
	}
}
