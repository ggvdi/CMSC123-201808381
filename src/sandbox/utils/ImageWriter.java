package sandbox.utils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageWriter {
	private String path, filenames;
	public ImageWriter(String path) {
		this.path = path;
	}
	
	public void writeImage(Image image, String filename) {
		try {
			BufferedImage bimage = (BufferedImage)image;
			ImageIO.write(bimage, "png", new File(path + filename + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}