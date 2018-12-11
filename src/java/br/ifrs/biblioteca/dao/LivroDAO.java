package br.ifrs.biblioteca.dao;

import br.ifrs.biblioteca.model.Livro;
import br.ifrs.biblioteca.model.UnidadeLivro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class LivroDAO {

	private EntityManager em;

	public Livro obter(Long id) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		Livro livro = this.em.find(Livro.class, id);
		return livro;
	}

	public List<Livro> obterTodos() throws Exception {
		this.em = EntityManagerProvider.getInstance();

		TypedQuery<Livro> query = this.em.createQuery("SELECT l FROM Livro l JOIN FETCH l.categoria", Livro.class);
		List<Livro> livros = query.getResultList();
		this.em.close();
		return livros;
	}

	public List<Livro> pesquisar(String nome) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		TypedQuery<Livro> query = this.em.createQuery("SELECT l from Livro l where lower(l.nome) like :nome order by l.nome", Livro.class);
		query.setParameter("nome", "%" + nome.toLowerCase() + "%");
		List<Livro> livros = query.getResultList();
		this.em.close();
		return livros;
	}

	public void salvar(Livro livro) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		this.em.getTransaction().begin();

		if (livro.getId() == null) {
			this.em.persist(livro);
		} else {
			this.em.merge(livro);
		}

		this.em.getTransaction().commit();
		this.em.close();
	}

	public void excluir(Long id) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		this.em.getTransaction().begin();
		Livro entity = this.em.find(Livro.class, id);

		if (entity != null) {
			this.em.remove(entity);
		}

		this.em.getTransaction().commit();
		this.em.close();
	}
}
