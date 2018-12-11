package br.ifrs.biblioteca.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "aluno")
public class Aluno implements Serializable {

	public Aluno() {
	}

	public Aluno(Long id, String matricula, String nome, String curso, String turma, String email, List<Emprestimo> emprestimos) {
		this.id = id;
		this.matricula = matricula;
		this.nome = nome;
		this.curso = curso;
		this.turma = turma;
		this.email = email;
		this.emprestimos = emprestimos;
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	private Long id;

	@Column(nullable = false)
	@Expose
	private String matricula;

	@Column(nullable = false)
	@Expose
	private String nome;

	@Column(nullable = false)
	@Expose
	private String curso;

	@Column(nullable = false)
	@Expose
	private String turma;

	@Column(nullable = false)
	@Expose
	private String email;

	@OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
	private List<Emprestimo> emprestimos;

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCurso() {
		return curso;
	}

	public void setCurso(String curso) {
		this.curso = curso;
	}

	public String getTurma() {
		return turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		if (!(object instanceof Aluno)) {
			return false;
		}
		Aluno other = (Aluno) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Aluno{" + "id=" + id + ", matricula=" + matricula + ", nome=" + nome + ", curso=" + curso + ", turma=" + turma + ", email=" + email + '}';
	}

}
