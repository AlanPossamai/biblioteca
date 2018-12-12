package br.ifrs.biblioteca.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "emprestimo")
public class Emprestimo implements Serializable {

	public Emprestimo() {
	}

	public Emprestimo(Long id, Aluno aluno, UnidadeLivro unidadeLivro, int periodo, int status, Date dataDevolucao, int estado) {
		this.id = id;
		this.aluno = aluno;
		this.unidadeLivro = unidadeLivro;
		this.periodo = periodo;
		this.status = status;
		this.dataDevolucao = dataDevolucao;
		this.estado = estado;
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	private Long id;

	public Long getId() {
		return id;
	}

	@ManyToOne
	@JoinColumn(name = "idAluno")
	@Expose
	private Aluno aluno;

	@ManyToOne
	@JoinColumn(name = "idUnidadeLivro")
	@Expose
	private UnidadeLivro unidadeLivro;

	@Column(nullable = false, length = 11)
	@Expose
	private int periodo;

	@Column(nullable = false, length = 1)
	@Expose
	private int status;

	@Expose
	private Date dataDevolucao;

	@Column(nullable = true, length = 1)
	@Expose
	private int estado;

	public void setId(Long id) {
		this.id = id;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public UnidadeLivro getUnidadeLivro() {
		return unidadeLivro;
	}

	public void setUnidadeLivro(UnidadeLivro unidadeLivro) {
		this.unidadeLivro = unidadeLivro;
	}

	public int getPeriodo() {
		return periodo;
	}

	public void setPeriodo(int periodo) {
		this.periodo = periodo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getDataDevolucao() {
		return dataDevolucao;
	}

	public void setDataDevolucao(Date dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
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
		if (!(object instanceof Emprestimo)) {
			return false;
		}
		Emprestimo other = (Emprestimo) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Emprestimo{" + "id=" + id + ", aluno=" + aluno + ", unidadeLivro=" + unidadeLivro + ", periodo=" + periodo + ", status=" + status + ", dataDevolucao=" + dataDevolucao + ", estado=" + estado + '}';
	}

}
