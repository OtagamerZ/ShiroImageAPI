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

import api.model.Reaction;
import api.model.ReactionType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReactionController {
	@RequestMapping(value = "/reaction", method = RequestMethod.GET)
	public Reaction reaction(@RequestParam(value = "type", defaultValue = "404") String type) {
		try {
			return new Reaction(ReactionType.valueOf(type.toUpperCase()));
		} catch (IllegalArgumentException e) {
			return new Reaction(ReactionType.NOTFOUND);
		}
	}
}
