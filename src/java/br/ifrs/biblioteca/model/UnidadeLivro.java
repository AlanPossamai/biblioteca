package br.ifrs.biblioteca.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "unidade_livro")
public class UnidadeLivro implements Serializable {

	public UnidadeLivro() {
	}

	public UnidadeLivro(Livro livro) {
		this.livro = livro;
	}

	public UnidadeLivro(Long id, Livro livro, List<Emprestimo> emprestimos) {
		this.id = id;
		this.livro = livro;
		this.emprestimos = emprestimos;
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	private Long id;

	@ManyToOne
	@JoinColumn(name = "idLivro")
	private Livro livro;

	@OneToMany(mappedBy = "unidadeLivro", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Emprestimo> emprestimos;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public List<Emprestimo> getEmprestimos() {
		return emprestimos;
	}

	public void setEmprestimos(List<Emprestimo> emprestimos) {
		this.emprestimos = emprestimos;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof UnidadeLivro)) {
			return false;
		}
		UnidadeLivro other = (UnidadeLivro) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "UnidadeLivro{" + "id=" + id + '}';
	}

}
