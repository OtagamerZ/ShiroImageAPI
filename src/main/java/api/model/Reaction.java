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

public class Reaction {
	private int id;
	private String url;

	public Reaction(ReactionType type) {
		switch (type) {
			case HUG:
				this.id = (int) ReactionList.hug()[0];
				this.url = (String) ReactionList.hug()[1];
				break;
			case FACEDESK:
				this.id = (int) ReactionList.facedesk()[0];
				this.url = (String) ReactionList.facedesk()[1];
				return;
			case NOPE:
				this.id = (int) ReactionList.nope()[0];
				this.url = (String) ReactionList.nope()[1];
				return;
			case RUN:
				this.id = (int) ReactionList.run()[0];
				this.url = (String) ReactionList.run()[1];
				return;
			case SLAP:
				this.id = (int) ReactionList.slap()[0];
				this.url = (String) ReactionList.slap()[1];
				return;
			case SMASH:
				this.id = (int) ReactionList.smash()[0];
				this.url = (String) ReactionList.smash()[1];
				return;
			case STARE:
				this.id = (int) ReactionList.stare()[0];
				this.url = (String) ReactionList.stare()[1];
				return;
			case BLUSH:
				this.id = (int) ReactionList.blush()[0];
				this.url = (String) ReactionList.blush()[1];
				return;
			case LAUGH:
				this.id = (int) ReactionList.laugh()[0];
				this.url = (String) ReactionList.laugh()[1];
				return;
			case SAD:
				this.id = (int) ReactionList.sad()[0];
				this.url = (String) ReactionList.sad()[1];
				return;
			case KISS:
				this.id = (int) ReactionList.kiss()[0];
				this.url = (String) ReactionList.kiss()[1];
				return;
			case DANCE:
				this.id = (int) ReactionList.dance()[0];
				this.url = (String) ReactionList.dance()[1];
				return;
			case PAT:
				this.id = (int) ReactionList.pat()[0];
				this.url = (String) ReactionList.pat()[1];
				return;
			case BITE:
				this.id = (int) ReactionList.bite()[0];
				this.url = (String) ReactionList.bite()[1];
				return;
			default:
				this.id = (int) ReactionList.notfound()[0];
				this.url = (String) ReactionList.notfound()[1];
		}
	}

	public int getId() {
		return id;
	}

	public String getUrl() {
		return url;
	}
}
