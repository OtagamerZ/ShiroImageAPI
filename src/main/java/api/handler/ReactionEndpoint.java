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

package api.handler;

import api.model.Reaction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class ReactionEndpoint {
	private static final String BASE_PATH = "https://raw.githubusercontent.com/OtagamerZ/ShiroImageAPI/master/src/main/resources/reactions/%s/%s";

	@RequestMapping(value = "/reaction", method = RequestMethod.GET)
	public Reaction reaction(@RequestParam(value = "type") String type) {
		try {
			URL path = this.getClass().getClassLoader().getResource("reactions/" + type);
			if (path == null) return new Reaction(404, BASE_PATH.formatted("notfound", "000.gif"));

			File[] content = new File(path.getFile()).listFiles();
			assert content != null;
			List<String> reactions = new ArrayList<>();
			for (File file : content) {
				if (file.isFile() && !file.getName().startsWith("."))
					reactions.add(file.getName());
			}

			int index = new Random().nextInt(reactions.size());
			return new Reaction(index, BASE_PATH.formatted(type, reactions.get(index)));
		} catch (IllegalArgumentException e) {
			return new Reaction(404, BASE_PATH.formatted("notfound", "404.gif"));
		}
	}
}
