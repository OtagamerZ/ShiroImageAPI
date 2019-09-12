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

package api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Application {
	public static final List<String> queue = new ArrayList<>();

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		Executors.newSingleThreadExecutor().execute(() -> {
			Thread.currentThread().setName("Canvas-Thread");
			//noinspection InfiniteLoopStatement
			while (true) {
				if (queue.size() > 10) {
					try {
						Thread.sleep(5000);
						queue.remove(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else if (queue.size() > 0) {
					try {
						Thread.sleep(3000);
						queue.remove(0);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
