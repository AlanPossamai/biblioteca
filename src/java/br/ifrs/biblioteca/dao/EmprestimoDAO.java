package br.ifrs.biblioteca.dao;

import br.ifrs.biblioteca.model.Emprestimo;
import br.ifrs.biblioteca.model.UnidadeLivro;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class EmprestimoDAO {

	private EntityManager em;

	public Emprestimo obter(Long id) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		Emprestimo emprestimo = this.em.find(Emprestimo.class, id);
		return emprestimo;
	}

	public List<Emprestimo> obterTodos() throws Exception {
		this.em = EntityManagerProvider.getInstance();

		TypedQuery<Emprestimo> query = this.em.createQuery("SELECT e FROM Emprestimo e", Emprestimo.class);
		List<Emprestimo> emprestimos = query.getResultList();
		this.em.close();
		return emprestimos;
	}

	public List<Emprestimo> pesquisarPorCodigoBarras(String codigoBarras) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		TypedQuery<Emprestimo> query = this.em.createQuery("SELECT e FROM Emprestimo e JOIN e.unidadeLivro ul WHERE LPAD(ul.id, 13, 0) LIKE :codigoBarras", Emprestimo.class);
		query.setParameter("codigoBarras", "%" + codigoBarras + "%");
		List<Emprestimo> emprestimos = query.getResultList();
		this.em.close();
		return emprestimos;
	}

	public List<Emprestimo> pesquisarPorAluno(String nomeAluno) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		TypedQuery<Emprestimo> query = this.em.createQuery("SELECT e FROM Emprestimo e JOIN e.aluno a WHERE a.nome LIKE :nomeAluno", Emprestimo.class);
		query.setParameter("nomeAluno", "%" + nomeAluno + "%");
		List<Emprestimo> emprestimos = query.getResultList();
		this.em.close();
		return emprestimos;
	}

	public List<Emprestimo> listarAtrasados() throws Exception {
		this.em = EntityManagerProvider.getInstance();
		TypedQuery<Emprestimo> query = this.em.createQuery("SELECT e FROM Emprestimo e WHERE e.dataDevolucao < curdate()", Emprestimo.class);
		List<Emprestimo> emprestimos = query.getResultList();
		this.em.close();
		return emprestimos;
	}

	public void salvar(Emprestimo emprestimo) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		this.em.getTransaction().begin();

		if (emprestimo.getId() == null) {
			this.em.persist(emprestimo);
		} else {
			this.em.merge(emprestimo);
		}

		this.em.getTransaction().commit();
		this.em.close();
	}

	public void excluir(Long id) throws Exception {
		this.em = EntityManagerProvider.getInstance();
		this.em.getTransaction().begin();
		Emprestimo entity = this.em.find(Emprestimo.class, id);

		if (entity != null) {
			this.em.remove(entity);
		}

		this.em.getTransaction().commit();
		this.em.close();
	}
}
