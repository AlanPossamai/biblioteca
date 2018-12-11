package br.ifrs.biblioteca.dao;

import br.ifrs.biblioteca.model.Usuario;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

public class UsuarioDAO {

	private EntityManager em;

	public Usuario obter(Long id) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		Usuario usuario = this.em.find(Usuario.class, id);
		return usuario;
	}

	public Usuario obterPorEmail(String email) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		Query query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :email");
		query.setParameter("email", email);

		Usuario usuario;
		try {
			usuario = (Usuario) query.getSingleResult();
		} catch (NoResultException e) {
			usuario = null;
		}

		return usuario;
	}

	public List<Usuario> obterTodos() throws Exception {
		this.em = EntityManagerProvider.getInstance();
		this.em.getEntityManagerFactory().getCache().evictAll();

		TypedQuery<Usuario> query = this.em.createQuery("SELECT c FROM Usuario c", Usuario.class);
		List<Usuario> usuarios = query.getResultList();
		this.em.close();
		return usuarios;
	}

	public List<Usuario> pesquisar(String nome) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		TypedQuery<Usuario> query = this.em.createQuery(" Select u from Usuario u where lower(u.nome) like :nome order by u.nome", Usuario.class);
		query.setParameter("nome", "%" + nome.toLowerCase() + "%");
		List<Usuario> usuarios = query.getResultList();
		this.em.close();
		return usuarios;
	}

	public void salvar(Usuario usuario) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		this.em.getTransaction().begin();

		if (usuario.getId() == null) {
			this.em.persist(usuario);
		} else {
			this.em.merge(usuario);
		}

		this.em.getTransaction().commit();
		this.em.close();
	}

	public void excluir(Long id) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		this.em.getTransaction().begin();
		Usuario entity = this.em.find(Usuario.class, id);
		if (entity != null) {
			this.em.remove(entity);
		}
		this.em.getTransaction().commit();
		this.em.close();
	}
}
