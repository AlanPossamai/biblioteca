package br.ifrs.biblioteca.controller;

import br.ifrs.biblioteca.dao.AlunoDAO;
import br.ifrs.biblioteca.model.Categoria;
import br.ifrs.biblioteca.model.Aluno;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AlunoController", urlPatterns = {"/AlunoController"})
public class AlunoController extends HttpServlet {

	private final AlunoDAO dao;

	public AlunoController() {
		super();
		this.dao = new AlunoDAO();
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
					List<Aluno> alunos = this.dao.pesquisar(pesquisa);
					String jsonAlunos = gson.toJson(alunos);
					response.getWriter().write(jsonAlunos);
					break;

				case "obter":
					Long id = Long.parseLong(request.getParameter("id"));
					Aluno aluno = this.dao.obter(id);
					String jsonAluno = gson.toJson(aluno);
					System.out.println(jsonAluno);
					response.getWriter().write(jsonAluno);
					break;

				default:
					List<Aluno> listagemAlunos = this.dao.obterTodos();
					System.out.println(listagemAlunos);
					String jsonListagem = gson.toJson(listagemAlunos);
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
			Gson gson = new Gson();

			switch (acao) {
				case "salvar":
					Aluno aluno = new Aluno();

					if (!request.getParameter("id").equals("")) {
						aluno.setId(Long.parseLong(request.getParameter("id")));
					}

					aluno.setCurso(request.getParameter("curso"));
					aluno.setEmail(request.getParameter("email"));
					aluno.setMatricula(request.getParameter("matricula"));
					aluno.setNome(request.getParameter("nome"));
					aluno.setTurma(request.getParameter("turma"));

					this.dao.salvar(aluno);
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
