package br.com.financeiro.util;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	public static final SessionFactory sessionFactory = criarSessionFactory();

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static Connection getConnection() {
		Session session = sessionFactory.openSession();
		Connection conexao = session.doReturningWork(new ReturningWork<Connection>() {
			@Override
			public Connection execute(Connection conn) throws SQLException {
				return conn;
			}
		});
		return conexao;
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
