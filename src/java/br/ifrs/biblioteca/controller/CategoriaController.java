package br.ifrs.biblioteca.controller;

import br.ifrs.biblioteca.dao.CategoriaDAO;
import br.ifrs.biblioteca.model.Categoria;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;

@WebServlet(name = "CategoriaController", urlPatterns = {"/CategoriaController"})
public class CategoriaController extends HttpServlet {

	private final CategoriaDAO dao;

	public CategoriaController() {
		super();
		this.dao = new CategoriaDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String acao = request.getParameter("acao").toLowerCase();

		try {
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

			switch (acao) {
				case "pesquisar":
					String pesquisa = request.getParameter("pesquisa");
					List<Categoria> categorias = this.dao.pesquisar(pesquisa);
					String jsonCategorias = gson.toJson(categorias);
					response.getWriter().write(jsonCategorias);
					break;

				case "obter":
					Long id = Long.parseLong(request.getParameter("id"));
					Categoria categoria = this.dao.obter(id);
					String jsonCategoria = gson.toJson(categoria);
					response.getWriter().write(jsonCategoria);
					break;

				default:
					List<Categoria> categoriasListagem = this.dao.obterTodos();
					String jsonListagem = gson.toJson(categoriasListagem);
					response.getWriter().write(jsonListagem);
					break;
			}

		} catch (Exception e) {
			System.out.println(e);
			response.getWriter().write("false");
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String acao = request.getParameter("acao").toLowerCase();

		try {
			switch (acao) {
				case "salvar":
					Categoria categoria = new Categoria();

					if (!request.getParameter("id").equals("")) {
						categoria.setId(Long.parseLong(request.getParameter("id")));
					}

					categoria.setNome(request.getParameter("nome"));
					this.dao.salvar(categoria);
					response.getWriter().write("true");
					break;

				case "excluir":
					Long id = Long.parseLong(request.getParameter("id"));
					this.dao.excluir(id);
					response.getWriter().write("true");
					break;

				default:
					response.getWriter().write("false");
					break;
			}

		} catch (Exception e) {
			System.out.println(e);
			response.getWriter().write("false");
		}
	}

}
