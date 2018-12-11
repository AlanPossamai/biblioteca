package br.ifrs.biblioteca.dao;

import br.ifrs.biblioteca.model.Aluno;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class AlunoDAO {

	private EntityManager em;

	public List<Aluno> pesquisar(String nome) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		TypedQuery<Aluno> query = this.em.createQuery("SELECT a FROM Aluno a WHERE lower(a.nome) LIKE :nome ORDER BY a.nome", Aluno.class);
		query.setParameter("nome", "%" + nome.toLowerCase() + "%");
		List<Aluno> alunos = query.getResultList();
		this.em.close();
		return alunos;
	}

	public Aluno obter(long id) {
		this.em = EntityManagerProvider.getInstance();
		Aluno aluno = em.find(Aluno.class, id);
		return aluno;
	}

	public void salvar(Aluno aluno) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		this.em.getTransaction().begin();

		if (aluno.getId() == null) {
			this.em.persist(aluno);
		} else {
			this.em.merge(aluno);
		}

		this.em.getTransaction().commit();
		this.em.close();
	}

	public List<Aluno> obterTodos() throws Exception {
		this.em = EntityManagerProvider.getInstance();
		TypedQuery<Aluno> query = this.em.createQuery("SELECT a FROM Aluno a ORDER BY a.nome", Aluno.class);
		List<Aluno> alunos = query.getResultList();
		this.em.close();
		return alunos;
	}

	public void excluir(Long id) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		this.em.getTransaction().begin();
		Aluno entity = this.em.find(Aluno.class, id);
		if (entity != null) {
			this.em.remove(entity);
		}
		this.em.getTransaction().commit();
		this.em.close();
	}

}
