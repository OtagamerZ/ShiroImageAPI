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

import api.database.MySQL;
import api.model.Profile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.NoResultException;
import java.math.BigDecimal;

@RestController
public class ProfileController {
	@RequestMapping(value = "/profile/global", method = RequestMethod.GET)
	public Profile globalProfile(@RequestParam(value = "id", defaultValue = "404") String id) {
		try {
			return MySQL.getGlobalProfile(id);
		} catch (IllegalArgumentException | NoResultException e) {
			return new Profile("404", new BigDecimal(0));
		}
	}

	@RequestMapping(value = "/profile/local", method = RequestMethod.GET)
	public Profile localProfile(@RequestParam(value = "id", defaultValue = "404") String id) {
		try {
			return MySQL.getLocalProfile(id);
		} catch (IllegalArgumentException | NoResultException e) {
			return new Profile("404", new BigDecimal(0));
		}
	}
}
