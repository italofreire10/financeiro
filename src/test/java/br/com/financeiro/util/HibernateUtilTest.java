package br.com.financeiro.util;

import org.hibernate.Session;
import org.junit.Test;

import br.com.financeiro.util.HibernateUtil;

public class HibernateUtilTest {
	@Test
	public void conectar() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.close();
		HibernateUtil.getSessionFactory().close();
		
	}
}