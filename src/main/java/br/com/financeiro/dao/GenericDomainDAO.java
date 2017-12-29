package br.com.financeiro.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import br.com.financeiro.util.HibernateUtil;

public class GenericDomainDAO<Entidade> {

	// API REFLECT => SEMPRE QUE O GENERIC FOR INSTANCIADO, É CAPTURADO O TIPO DA
	// CLASSE COM UM CONSTRUTOR DO PRÓPRIO GENERIC
	private Class<Entidade> classe;

	@SuppressWarnings("unchecked")
	public GenericDomainDAO() {
		this.classe = (Class<Entidade>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[0];
	}

	public void salvar(Entidade entidade) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.merge(entidade);
			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Entidade> listar(String campoOrder) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = session.createCriteria(classe);
			consulta.addOrder(Order.asc(campoOrder));
			List<Entidade> resultado = consulta.list();
			return resultado;
		} catch (RuntimeException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	public Entidade buscar(Long codigo) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		try {
			Criteria consulta = sessao.createCriteria(classe);
			consulta.add(Restrictions.idEq(codigo));
			Entidade resultado = (Entidade) consulta.uniqueResult();
			return resultado;
		} catch (RuntimeException erro) {
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public void excluir(Entidade entidade) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			sessao.delete(entidade);
			transacao.commit();
		} catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
			}
			throw erro;
		} finally {
			sessao.close();
		}
	}

	public void editar(Entidade entidade) {
		Session sessao = HibernateUtil.getSessionFactory().openSession();
		Transaction transacao = null;
		try {
			transacao = sessao.beginTransaction();
			sessao.update(entidade);
			transacao.commit();
		} catch (RuntimeException erro) {
			if (transacao != null) {
				transacao.rollback();
				throw erro;
			}
		} finally {
			sessao.close();
		}
	}

	public void merge(Entidade entidade) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.merge(entidade);
			transaction.commit();
		} catch (RuntimeException e) {
			if (transaction != null) {
				transaction.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}
}
