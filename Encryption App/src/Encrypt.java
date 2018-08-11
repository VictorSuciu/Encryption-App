
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Encrypt {

	private String keyFileName = "PictureEncryptionKey.txt";
	private File keyFile;
	private File imgFile;
	private BufferedImage img;
	private int width;
	private int height;
	Scanner readTXT;
	PrintWriter txtWrite;

	public Encrypt(File f) {

		imgFile = f;
		try {
			img = ImageIO.read(imgFile);

		} catch (IOException e) {
			System.out.println("Could not read file" + e);
		}
		this.width = img.getWidth();
		this.height = img.getHeight();
	}

	public void key(String seedLetters) {
		keyFile = new File(keyFileName);
		System.out.println("Generating Key...");

		System.out.println();
		ArrayList<Character> seedList = new ArrayList();
		int seed = 0;

		String alphabet = "0123456789abcdefghijklmnopqrstuvwxyz";
		int count = 0;
		int alIndex = 0;
		Random rand = new Random();

		for (int i = 0; i < seedLetters.length(); i++) {
			while (seedLetters.charAt(i) != alphabet.charAt(alIndex)) {
				alIndex++;
				if (alIndex == alphabet.length()) {
					alIndex = 10;
					break;

				}
			}
			seed -= alIndex;
			alIndex = 0;
		}

		rand.setSeed(Math.abs(seed));

		// Creates the key. A separate int (0 - 255) for the red, green, and blue values
		// for each pixel of the image. This means that there will be three times as
		// many numbers
		// as there are pixels in the image
		try {

			PrintWriter txtWrite = new PrintWriter(keyFile);
			while (count < width * height * 4) {

				txtWrite.print(rand.nextInt(256) + " ");

				count++;
			}
		} catch (IOException e) {
			System.out.println("KeyFile Error " + e);
		}

	}

	public void encrypt(File f) {
		System.out.println("Encrypting pixel values...");
		try {
			readTXT = new Scanner(keyFile);
		} catch (IOException e) {
			System.out.println("Could not initialize Scanner readTXT" + e);
		}
		int redNum;
		int greenNum;
		int blueNum;

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = img.getRGB(x, y);

				int a = (p >> 24) & 0xFF;
				int r = (p >> 16) & 0xFF;
				int g = (p >> 8) & 0xFF;
				int b = p & 0xFF;

				redNum = readTXT.nextInt();

				greenNum = readTXT.nextInt();

				blueNum = readTXT.nextInt();

				// Red encryption
				if (redNum + r > 255) {
					r = (redNum + r) - 256;

				} else {
					r += redNum;
				}

				// Green encryption
				if (greenNum + g > 255) {
					g = (greenNum + g) - 256;

				} else {
					g += greenNum;
				}

				// Blue encryption
				if (blueNum + b > 255) {
					b = (blueNum + b) - 256;
				} else {
					b += blueNum;
				}

				p = (a << 24) | (r << 16) | (g << 8) | b;
				img.setRGB(x, y, p);
			}

		}

		write(imgFile, img);
		keyFile.delete();
	}

	// Decrypt the image
	public void decrypt(File f) {
		int redNum;
		int greenNum;
		int blueNum;

		System.out.println("Decrypting pixel values...");

		try {
			readTXT = new Scanner(keyFile);
		} catch (IOException e) {
			System.out.println("Could not initialize Scanner readTXT" + e);
		}

		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int p = img.getRGB(x, y);

				int a = (p >> 24) & 0xFF;
				int r = (p >> 16) & 0xFF;
				int g = (p >> 8) & 0xFF;
				int b = p & 0xFF;

				redNum = readTXT.nextInt();
				greenNum = readTXT.nextInt();
				blueNum = readTXT.nextInt();
				// Red encryption

				if (r - redNum < 0) {
					r = (256 + r) - redNum;
				} else {
					r -= redNum;
				}

				// Green encryption
				if (g - greenNum < 0) {
					g = (256 + g) - greenNum;
				} else {
					g -= greenNum;
				}

				// Blue encryption
				if (b - blueNum < 0) {
					b = (256 + b) - blueNum;
				} else {
					b -= blueNum;
				}

				p = (a << 24) | (r << 16) | (g << 8) | b;
				img.setRGB(x, y, p);
			}
		}

		write(imgFile, img);
		keyFile.delete();
	}

	private void write(File f, BufferedImage img) {
		System.out.println("Writing to image file...");
		try {
			// File file = new File("H:\\My_Documents\\My_Pictures\\Jumbled.png");
			ImageIO.write(img, "png", f);
			System.out.println("Done.");
		} catch (IOException e) {
			System.out.println("Failed to write: " + e);
		}

	}

}
