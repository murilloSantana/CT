package br.com.cta.DAO;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


public abstract class AbstractDAO<PK extends Serializable, T>  {

	private final Class<T> classePersistente;
	@Autowired
	private SessionFactory sessionFactory;

	
	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.classePersistente = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
				.getActualTypeArguments()[1];
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	public Boolean salvar(T obj) {
		try {
			getSession().save(obj);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public Boolean salvar(List<?> obj) {
		try {
			for (Object object : obj) {
				getSession().save(object);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public Boolean editar(T obj) {
		try {
			getSession().update(obj);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean excluir(T obj) {
		try {
			getSession().delete(obj);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean excluirPorChave(Long chave) {
		try {
			excluir(pesquisarPorChave(chave));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	protected List<?> listarTodos() {
		try {
			
			return getSession().createCriteria(this.classePersistente).list();
		} catch (Exception e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	public T pesquisarPorChave(Long chave) {
		return (T) getSession().get(this.classePersistente, chave);
	}

	public List<?> pesquisarPorNome(String nomeAtributo, String valor) {
		Criteria crit = null;
		try {
			crit = getSession().createCriteria(this.classePersistente);
			crit.add(Restrictions.eq(nomeAtributo, valor));
		} catch (Exception e) {
		}
		return crit.list();
	}	

}