package br.ifrs.biblioteca.controller;

import br.ifrs.biblioteca.dao.UsuarioDAO;
import br.ifrs.biblioteca.model.Usuario;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alan Possamai
 */
@WebServlet(name = "UsuarioController", urlPatterns = {"/UsuarioController"})
public class UsuarioController extends HttpServlet {

	private static final String ERRO = "/login.jsp";
	private static final String HOME = "/home.jsp";

	private final UsuarioDAO dao;

	public UsuarioController() {
		super();
		this.dao = new UsuarioDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String acao = request.getParameter("acao");

		if (request.getParameter("pesquisa") != null) {
			response.setContentType("application/json");

			try {
				String pesquisa = request.getParameter("pesquisa");
				List<Usuario> usuarios = this.dao.pesquisar(pesquisa);

				String json = "[";
				int arraySize = usuarios.size();
				for (int i = 0; i < arraySize; i++) {
					Usuario usuario = usuarios.get(i);
					json += usuario.toString();

					if ((arraySize - 1) > i) {
						json += ",";
					}
				}
				json += "]";

				response.getWriter().write(json);
			} catch (Exception e) {
				System.out.println(e);
				response.getWriter().write("[]");
			}
		} else if (acao.equalsIgnoreCase("excluir")) {
			try {
				Long idUsuario = Long.parseLong(request.getParameter("id"));
				this.dao.excluir(idUsuario);
				response.setContentType("text/plain");
				response.getWriter().write("true");
			} catch (Exception e) {
				System.out.println(e);
				response.setContentType("text/plain");
				response.getWriter().write("false");
			}
		} else if (acao.equalsIgnoreCase("atualizar")) {
			try {
				Long idUsuario = Long.parseLong(request.getParameter("id"));
				Usuario usuario = this.dao.obter(idUsuario);
				request.setAttribute("usuario", usuario);
				RequestDispatcher view = request.getRequestDispatcher("/cadusuario.jsp");
				view.forward(request, response);
			} catch (Exception e) {
				System.out.println(e);
				request.setAttribute("mensagem", e.getMessage());
				RequestDispatcher view = request.getRequestDispatcher("/erro.jsp");
				view.forward(request, response);
			}
		} else {
			String email = request.getParameter("email").trim();
			String usuarioEncontrado = "false";

			if (email != null || !("".equals(email))) {
				try {
					Usuario u = this.dao.obterPorEmail(email);
					if (u instanceof Usuario) {
						usuarioEncontrado = "true";
					}
				} catch (Exception e) {
					System.out.println(e);
					usuarioEncontrado = "true";
				}
			}

			response.setContentType("text/plain");
			response.getWriter().write(usuarioEncontrado);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forward = ERRO;

		String acao = request.getParameter("acao");

		if ("salvar".equalsIgnoreCase(acao)) {
			Usuario u = new Usuario();

			Long idUsuario = Long.parseLong(request.getParameter("id"));
			if (idUsuario > 0) {
				u.setId(idUsuario);
			}

			u.setNome(request.getParameter("nome"));
			u.setEmail(request.getParameter("email"));
			u.setSenha(request.getParameter("senha"));

			response.setContentType("text/plain");
			try {
				this.dao.salvar(u);
				response.getWriter().write("OK");
			} catch (Exception e) {
				System.out.println(e);
				response.getWriter().write(e.getMessage());
			}
		} else {
			try {
				List<Usuario> usuarios = this.dao.obterTodos();
				if (usuarios.isEmpty()) {
					request.setAttribute("mensagem", "<script>exibirMensagemLogin('Nenhum usuário cadastrado. Execute o script para inserção de usuários e reinicie a conexão com a base.')</script>");
				} else if (request.getParameter("email") != null) {
					Usuario usuario = this.dao.obterPorEmail(request.getParameter("email"));

					if (usuario == null) {
						request.setAttribute("mensagem", "<script>exibirMensagemLogin('Usuário não encontrado.')</script>");
					} else if (usuario.getSenha().equals(request.getParameter("senha"))) {
						forward = HOME;
						String mensagem = "<div class='alert alert-success'><strong>Sucesso!</strong> Logado com sucesso.</div>";
						request.setAttribute("mensagem", mensagem);

						HttpSession session = request.getSession(true);
						session.setAttribute("idUsuario", usuario.getId());
						session.setAttribute("emailUsuario", usuario.getEmail());
					} else {
						request.setAttribute("mensagem", "<script>exibirMensagemLogin('Senha incorreta.')</script>");
					}
				} else {
					request.setAttribute("mensagem", "<script>exibirMensagemLogin('Digite o seu e-mail.')</script>");
				}
			} catch (Exception e) {
				request.setAttribute("mensagem", "<script>exibirMensagemLogin('" + e.toString() + "')</script>");
			} finally {
				RequestDispatcher view = request.getRequestDispatcher(forward);
				view.forward(request, response);
			}
		}
	}
}
