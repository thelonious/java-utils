/**
 * Copyright 2012, Kevin Lindsey
 * See LICENSE file for licensing information
 */
package com.kevlindev.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

/**
 * ImageUtils
 * 
 * @author Kevin Lindsey
 * @version 1.0
 */
public class ImageUtils {
	/**
	 * Split up an image into cols by rows tiles
	 * 
	 * @param img
	 *            The image to slice
	 * @param cols
	 *            The total number of columns in the source image
	 * @param rows
	 *            The total number of rows in the source image
	 * @return Returns a BufferedImage array, one entry per slice
	 */
	public static BufferedImage[] sliceImage(BufferedImage img, int cols, int rows) {
		// create container for our slices
		BufferedImage[] result = new BufferedImage[cols * rows];

		// cache character size
		int characterWidth = img.getWidth() / cols;
		int characterHeight = img.getHeight() / rows;

		// init slice index
		int index = 0;

		for (int row = 0; row < rows; row++) {
			for (int col = 0; col < cols; col++, index++) {
				// create new image for slice
				result[index] = new BufferedImage(characterWidth, characterHeight, img.getType());

				// Use the slice as our graphics context
				Graphics2D g = result[index].createGraphics();

				// calculate tile's bounding box
				int x1 = characterWidth * col;
				int y1 = characterHeight * row;
				int x2 = x1 + characterWidth;
				int y2 = y1 + characterHeight;

				// draw slice
				g.drawImage(img, 0, 0, characterWidth, characterHeight, x1, y1, x2, y2, null);

				// we're done with the graphics context
				g.dispose();
			}
		}

		return result;
	}

	public static BufferedImage getBufferedImage(Image image) {
		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufferedImage.createGraphics();

		g.drawImage(image, null, null);
		// waitForImage(bufferedImage);

		return bufferedImage;
	}

	private ImageUtils() {
	}
}
