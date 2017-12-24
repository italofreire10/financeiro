package br.com.financeiro.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	public static final SessionFactory sessionFactory = criarSessionFactory();

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private static SessionFactory criarSessionFactory() {
		try {
			Configuration configuration = new Configuration().configure();

			ServiceRegistry registry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
					.build();

			SessionFactory factory = configuration.buildSessionFactory(registry);

			return factory;
		} catch (Throwable e) {
			System.out.println("Ocorreu um erro ao criar a fábrica de sessões:" + e);
			throw new ExceptionInInitializerError(e);
		}
	}
}
