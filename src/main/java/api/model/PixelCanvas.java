/*
 * This file is part of Shiro J Bot.
 *
 *     Shiro J Bot is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Shiro J Bot is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Shiro J Bot.  If not, see <https://www.gnu.org/licenses/>
 */

package api.model;

import javax.imageio.ImageIO;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@Entity
public class PixelCanvas {
	@Id
	private int id;
	@Column(columnDefinition = "String default \"\"")
	private String canvas;
	@Column(columnDefinition = "boolean default false")
	private boolean shelved;
	transient private final int CANVAS_SIZE = 1024;

	public String getBase64Canvas() {
		return canvas;
	}

	public BufferedImage getCanvas() {
		if (canvas != null) {
			try {
				byte[] bytes = Base64.getDecoder().decode(canvas);
				ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
				return ImageIO.read(bais);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public String viewChunk(int[] coords, int zoom) {
		int fac = (int) Math.pow(2, zoom);
		int chunkSize = CANVAS_SIZE / fac;
		try {
			BufferedImage chunk = new BufferedImage(CANVAS_SIZE, CANVAS_SIZE, BufferedImage.TYPE_INT_RGB);
			BufferedImage canvas = new BufferedImage(CANVAS_SIZE + chunkSize, CANVAS_SIZE + chunkSize, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = canvas.createGraphics();

			g2d.drawImage(getCanvas(), (canvas.getWidth() / 2) - CANVAS_SIZE / 2, (canvas.getHeight() / 2) - CANVAS_SIZE / 2, null);

			g2d = chunk.createGraphics();
			int x = (CANVAS_SIZE / 2 / fac) + (coords[0] + CANVAS_SIZE / 2) - (chunkSize / 2);
			int y = (CANVAS_SIZE / 2 / fac) + (CANVAS_SIZE / 2 - coords[1]) - (chunkSize / 2);
			g2d.drawImage(canvas.getSubimage(x, y, chunkSize, chunkSize).getScaledInstance(CANVAS_SIZE, CANVAS_SIZE, 0), 0, 0, null);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(chunk, "png", baos);

			return Base64.getEncoder().encodeToString(baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String addPixel(int[] coords, Color color) {
		BufferedImage canvas = getCanvas();
		canvas.setRGB(coords[0] + CANVAS_SIZE / 2, CANVAS_SIZE / 2 - coords[1], color.getRGB());
		saveCanvas(canvas);

		return getBase64Canvas();
	}

	private void saveCanvas(BufferedImage canvas) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(canvas, "png", baos);
			this.canvas = Base64.getEncoder().encodeToString(baos.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
