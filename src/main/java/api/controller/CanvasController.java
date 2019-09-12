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

package api.controller;

import api.Application;
import api.database.MySQL;
import api.model.PixelCanvas;
import api.model.PixelCanvasResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CanvasController {
	@RequestMapping(value = "/canvas", method = RequestMethod.GET)
	public PixelCanvasResponse globalProfile(@RequestParam(value = "p") String p, @RequestParam(value = "token") String token) {
		try {
			if (Application.queue.contains(token)) {
				return new PixelCanvasResponse(403, "Ratelimit excedido");
			} else if (!MySQL.validateToken(token)) {
				return new PixelCanvasResponse(403, "Token inválido");
			}

			if (p.split(",").length < 3) {
				return new PixelCanvasResponse(200, MySQL.getCanvas().getBase64Canvas());
			} else {
				int[] coords = new int[]{Integer.parseInt(p.split(";")[0]), Integer.parseInt(p.split(";")[1])};
				int zoom = Integer.parseInt(p.split(";")[2]);
				PixelCanvas c = MySQL.getCanvas();

				Application.queue.add(token);
				return new PixelCanvasResponse(200, c.viewChunk(coords, zoom));
			}
		} catch (NumberFormatException e) {
			return new PixelCanvasResponse(400, "As coordenadas e o zoom precisam ser valores numéricos separados por vírgula");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return new PixelCanvasResponse(400, "O zoom deve ser um valor inteiro entre 1 e 10");
		}
	}

	@RequestMapping(value = "/canvas/pixel", method = RequestMethod.GET)
	public PixelCanvasResponse localProfile(@RequestParam(value = "p") String p, @RequestParam(value = "token") String token) {
		try {
			if (Application.queue.contains(token)) {
				return new PixelCanvasResponse(403, "Ratelimit excedido");
			} else if (!MySQL.validateToken(token)) {
				return new PixelCanvasResponse(403, "Token inválido");
			}

			if (p.split(",").length < 3) {
				return new PixelCanvasResponse(403, "É necessário informar as coordenadas e a cor do pixel (hexadecimal sem #)");
			}

			int[] coords = new int[]{Integer.parseInt(p.split(";")[0]), Integer.parseInt(p.split(";")[1])};
			int zoom = Integer.parseInt(p.split(";")[2]);
			PixelCanvas c = MySQL.getCanvas();

			Application.queue.add(token);
			return new PixelCanvasResponse(200, c.viewChunk(coords, zoom));
		} catch (NumberFormatException e) {
			return new PixelCanvasResponse(400, "As coordenadas precisam ser valores numéricos e a cor precisa ser um valor hexadecimal (sem #), todos separados por vírgula");
		} catch (ArrayIndexOutOfBoundsException e) {
			return new PixelCanvasResponse(400, "Coordenadas fora da grade. As coordenadas devem estar dentra do intervalo de -512 à 512");
		}
	}
}
