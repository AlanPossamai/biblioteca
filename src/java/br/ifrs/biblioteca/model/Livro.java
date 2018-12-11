package br.ifrs.biblioteca.model;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
@Table(name = "livro")
public class Livro implements Serializable {

	public Livro() {
	}

	public Livro(Long id, String foto, String isbn, String nome, int volume, String autor, int quantidade, Categoria categoria, List<UnidadeLivro> unidades) {
		this.id = id;
		this.foto = foto;
		this.isbn = isbn;
		this.nome = nome;
		this.volume = volume;
		this.autor = autor;
		this.quantidade = quantidade;
		this.categoria = categoria;
		this.unidades = unidades;
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Expose
	private Long id;

	@Column(nullable = false)
	@Expose
	private String foto;

	@Column(nullable = false, length = 13)
	@Expose
	private String isbn;

	@Column(nullable = false)
	@Expose
	private String nome;

	@Column(nullable = false)
	@Expose
	private int volume;

	@Column(nullable = false)
	@Expose
	private String autor;

	@Column(nullable = false)
	@Expose
	private int quantidade;

	@ManyToOne
	@JoinColumn(name = "idCategoria")
	@Expose
	private Categoria categoria;

	@OneToMany(mappedBy = "livro", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@Expose
	private List<UnidadeLivro> unidades = new ArrayList<UnidadeLivro>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<UnidadeLivro> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<UnidadeLivro> unidades) {
		this.unidades = unidades;
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
		if (!(object instanceof Livro)) {
			return false;
		}
		Livro other = (Livro) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Livro{" + "id=" + id + ", foto=" + foto + ", isbn=" + isbn + ", nome=" + nome + ", volume=" + volume + ", autor=" + autor + ", quantidade=" + quantidade + ", categoria=" + categoria + ", unidades=" + unidades + '}';
	}

}
