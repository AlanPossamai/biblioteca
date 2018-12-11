package br.ifrs.biblioteca.dao;

import br.ifrs.biblioteca.model.UnidadeLivro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class UnidadeLivroDAO {

	private EntityManager em;

	public UnidadeLivro obter(Long id) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		UnidadeLivro unidadeLivro = this.em.find(UnidadeLivro.class, id);
		return unidadeLivro;
	}

	public List<UnidadeLivro> obterTodos() throws Exception {
		this.em = EntityManagerProvider.getInstance();

		TypedQuery<UnidadeLivro> query = this.em.createQuery("SELECT ul FROM UnidadeLivro ul ORDER BY ul.id", UnidadeLivro.class);
		List<UnidadeLivro> unidadeLivros = query.getResultList();
		this.em.close();
		return unidadeLivros;
	}

}
