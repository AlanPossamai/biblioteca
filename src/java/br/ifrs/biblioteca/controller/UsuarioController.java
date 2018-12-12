package br.ifrs.biblioteca.controller;

import br.ifrs.biblioteca.dao.UsuarioDAO;
import br.ifrs.biblioteca.model.Usuario;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;

@WebServlet(name = "UsuarioController", urlPatterns = {"/UsuarioController"})
public class UsuarioController extends HttpServlet {

	private final UsuarioDAO dao;

	public UsuarioController() {
		super();
		this.dao = new UsuarioDAO();
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
					List<Usuario> usuarios = this.dao.pesquisar(pesquisa);
					String jsonUsuarios = gson.toJson(usuarios);
					response.getWriter().write(jsonUsuarios);
					break;

				case "obter":
					Long id = Long.parseLong(request.getParameter("id"));
					Usuario usuario = this.dao.obter(id);
					String jsonUsuario = gson.toJson(usuario);
					response.getWriter().write(jsonUsuario);
					break;

				default:
					List<Usuario> usuariosListagem = this.dao.obterTodos();
					String jsonListagem = gson.toJson(usuariosListagem);
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
					Usuario usuario = new Usuario();

					if (!request.getParameter("id").equals("")) {
						usuario.setId(Long.parseLong(request.getParameter("id")));
					}

					usuario.setNome(request.getParameter("nome"));
					usuario.setEmail(request.getParameter("email"));
					usuario.setSenha(request.getParameter("senha"));

					this.dao.salvar(usuario);
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
