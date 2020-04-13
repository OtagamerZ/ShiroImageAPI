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

package api.database;

import api.model.PixelCanvas;
import api.model.Profile;
import api.model.Token;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class MySQL {
	private static EntityManagerFactory emf;

	private static EntityManager getEntityManager() {
		Map<String, String> props = new HashMap<>();
		props.put("javax.persistence.jdbc.user", System.getenv("DB_LOGIN"));
		props.put("javax.persistence.jdbc.password", System.getenv("DB_PASS"));
		props.put("javax.persistence.jdbc.url", "jdbc:postgresql://" + System.getenv("SERVER_URL") + "/Shiro?serverTimezone=EST5EDT&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8&interactiveClient=true");

		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("shiro_remote", props);
		}

		emf.getCache().evictAll();

		return emf.createEntityManager();
	}

	public static Profile getGlobalProfile(String id) {
		EntityManager em = getEntityManager();

		Query q = em.createNativeQuery("CALL getGlobalMember(?1)");
		q.setParameter(1, id);

		String ID = (String) ((Object[]) q.getSingleResult())[0];
		BigDecimal XP = (BigDecimal) ((Object[]) q.getSingleResult())[1];
		Profile p = new Profile(ID, XP);

		em.close();
		return p;
	}

	public static Profile getLocalProfile(String id) {
		EntityManager em = getEntityManager();

		Query q = em.createNativeQuery("CALL getMember(?1)");
		q.setParameter(1, id);

		String ID = (String) ((Object[]) q.getSingleResult())[0];
		BigDecimal XP = (BigDecimal) ((Object[]) q.getSingleResult())[1];
		Profile p = new Profile(ID, XP);

		em.close();
		return p;
	}

	public static PixelCanvas getCanvas() throws NoResultException {
		EntityManager em = getEntityManager();

		Query q = em.createQuery("SELECT c FROM PixelCanvas c WHERE shelved = 0", PixelCanvas.class);
		q.setMaxResults(1);

		PixelCanvas c = (PixelCanvas) q.getSingleResult();
		em.close();

		return c;
	}

	public static void saveCanvas(PixelCanvas canvas) {
		EntityManager em = getEntityManager();

		em.getTransaction().begin();
		em.merge(canvas);
		em.getTransaction().commit();

		em.close();
	}

	public static boolean validateToken(String token) {
		EntityManager em = getEntityManager();

		Query q = em.createQuery("SELECT t FROM Token t WHERE token LIKE :token", Token.class);
		q.setParameter("token", token);
		q.setMaxResults(1);

		try {
			Token t = (Token) q.getSingleResult();

			em.getTransaction().begin();
			em.merge(t.addCall());
			em.getTransaction().commit();

			em.close();

			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
}
