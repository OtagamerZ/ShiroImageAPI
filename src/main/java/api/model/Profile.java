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

import java.math.BigDecimal;

public class Profile {
	private String id;
	private final int level;
	private long xp;

	public Profile(String id, BigDecimal xp) {
		this.id = id;
		this.xp = xp.longValue();
		level = (int) Math.ceil(Math.sqrt(xp.doubleValue() / 100));
	}

	public String getId() {
		return id;
	}

	public Profile setId(String id) {
		this.id = id;
		return this;
	}

	public long getXp() {
		return xp;
	}

	public Profile setXp(long xp) {
		this.xp = xp;
		return this;
	}

	public int getLevel() {
		return level;
	}
}
