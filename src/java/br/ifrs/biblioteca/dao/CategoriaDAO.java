package br.ifrs.biblioteca.dao;

import br.ifrs.biblioteca.model.Categoria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class CategoriaDAO {

	private EntityManager em;

	public Categoria obter(Long id) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		Categoria categoria = this.em.find(Categoria.class, id);
		return categoria;
	}

	public List<Categoria> obterTodos() throws Exception {
		this.em = EntityManagerProvider.getInstance();

		TypedQuery<Categoria> query = this.em.createQuery("SELECT c FROM Categoria c", Categoria.class);
		List<Categoria> categorias = query.getResultList();
		this.em.close();
		return categorias;
	}

	public List<Categoria> pesquisar(String nome) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		TypedQuery<Categoria> query = this.em.createQuery("SELECT c from Categoria c where lower(c.nome) like :nome order by c.nome", Categoria.class);
		query.setParameter("nome", "%" + nome.toLowerCase() + "%");
		List<Categoria> categorias = query.getResultList();
		this.em.close();
		return categorias;
	}

	public void salvar(Categoria categoria) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		this.em.getTransaction().begin();

		if (categoria.getId() == null) {
			this.em.persist(categoria);
		} else {
			this.em.merge(categoria);
		}

		this.em.getTransaction().commit();
		this.em.close();
	}

	public void excluir(Long id) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		this.em.getTransaction().begin();
		Categoria entity = this.em.find(Categoria.class, id);

		if (entity != null) {
			this.em.remove(entity);
		}

		this.em.getTransaction().commit();
		this.em.close();
	}
}
