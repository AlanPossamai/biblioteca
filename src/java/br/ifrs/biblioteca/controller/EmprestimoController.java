package br.ifrs.biblioteca.controller;

import br.ifrs.biblioteca.dao.AlunoDAO;
import br.ifrs.biblioteca.dao.EmprestimoDAO;
import br.ifrs.biblioteca.dao.UnidadeLivroDAO;
import br.ifrs.biblioteca.model.Aluno;
import br.ifrs.biblioteca.model.Emprestimo;
import br.ifrs.biblioteca.model.UnidadeLivro;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@WebServlet(name = "EmprestimoController", urlPatterns = {"/EmprestimoController"})
public class EmprestimoController extends HttpServlet {

	private final EmprestimoDAO dao;
	private final UnidadeLivroDAO unidadeLivroDAO;
	private final AlunoDAO alunoDAO;

	public EmprestimoController() {
		super();
		this.dao = new EmprestimoDAO();
		this.unidadeLivroDAO = new UnidadeLivroDAO();
		this.alunoDAO = new AlunoDAO();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String acao = request.getParameter("acao").toLowerCase();

		try {
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

			switch (acao) {
				case "pesquisaraluno":
					String pesquisa = request.getParameter("pesquisa");
					//List<Emprestimo> emprestimos = this.dao.pesquisarPorAluno(pesquisa);
					//String jsonEmprestimos = gson.toJson(emprestimos);
					//response.getWriter().write(jsonEmprestimos);
					//break;
					response.setContentType("text/plain");
					List<Emprestimo> emprestimos = this.dao.pesquisarPorAluno(pesquisa);
					String htmlPesquisa1 = "";
					for (int i = 0; i < emprestimos.size(); i++) {
						Emprestimo emp = emprestimos.get(i);

						htmlPesquisa1 += "<tr>"
								+ "<td>" + emp.getAluno().getNome() + "</td>"
								+ "<td>" + emp.getUnidadeLivro().getLivro().getNome() + " - " + this.padLeftLoopRaw(emp.getUnidadeLivro().getId().toString(), '0', 12) + "</td>"
								+ "<td>" + this.getBadge(emp.getStatus()) + "</td>"
								+ "<td>"
								+ "<div class=\"btn-group btn-group-sm\" role=\"group\">"
								+ "<a href=\"emprestimo.jsp?id=" + emp.getId() + " \" class=\"btn btn-primary\">Editar</a>"
								+ "<button type=\"button\" class=\"btn btn-danger\" onClick='excluir(" + emp.getId() + ")'>Excluir</button>"
								+ "</div>"
								+ "</td>"
								+ "</tr>";
					}
					response.getWriter().write(htmlPesquisa1);
					break;

				case "pesquisarlivro":
					String pesquisaLivro = request.getParameter("pesquisa");
					//List<Emprestimo> emprestimosLivro = this.dao.pesquisarPorCodigoBarras(pesquisaLivro);
					//String jsonEmprestimosLivro = gson.toJson(emprestimosLivro);
					//response.getWriter().write(jsonEmprestimosLivro);
					//break;
					response.setContentType("text/plain");
					List<Emprestimo> emprestimosLivro = this.dao.pesquisarPorCodigoBarras(pesquisaLivro);
					String htmlPesquisa2 = "";
					for (int i = 0; i < emprestimosLivro.size(); i++) {
						Emprestimo emp = emprestimosLivro.get(i);

						htmlPesquisa2 += "<tr>"
								+ "<td>" + emp.getAluno().getNome() + "</td>"
								+ "<td>" + emp.getUnidadeLivro().getLivro().getNome() + " - " + this.padLeftLoopRaw(emp.getUnidadeLivro().getId().toString(), '0', 12) + "</td>"
								+ "<td>" + this.getBadge(emp.getStatus()) + "</td>"
								+ "<td>"
								+ "<div class=\"btn-group btn-group-sm\" role=\"group\">"
								+ "<a href=\"emprestimo.jsp?id=" + emp.getId() + " \" class=\"btn btn-primary\">Editar</a>"
								+ "<button type=\"button\" class=\"btn btn-danger\" onClick='excluir(" + emp.getId() + ")'>Excluir</button>"
								+ "</div>"
								+ "</td>"
								+ "</tr>";
					}
					response.getWriter().write(htmlPesquisa2);
					break;

				case "obteratrasados":
					//List<Emprestimo> emprestimosAtrasados = this.dao.listarAtrasados();
					//String jsonAtrasados = gson.toJson(emprestimosAtrasados);
					//response.getWriter().write(jsonAtrasados);
					//break;
					response.setContentType("text/plain");
					List<Emprestimo> emprestimosAtrasados = this.dao.listarAtrasados();
					String htmlAtrasados = "";
					for (int i = 0; i < emprestimosAtrasados.size(); i++) {
						Emprestimo emp = emprestimosAtrasados.get(i);

						htmlAtrasados += "<tr>"
								+ "<td>" + emp.getAluno().getNome() + "</td>"
								+ "<td>" + emp.getUnidadeLivro().getLivro().getNome() + " - " + this.padLeftLoopRaw(emp.getUnidadeLivro().getId().toString(), '0', 12) + "</td>"
								+ "<td>" + this.getBadge(emp.getStatus()) + "<span class='badge badge-pill badge-warning'>Atrasado</span></td>"
								+ "<td>"
								+ "<div class=\"btn-group btn-group-sm\" role=\"group\">"
								+ "<a href=\"emprestimo.jsp?id=" + emp.getId() + " \" class=\"btn btn-primary\">Editar</a>"
								+ "<button type=\"button\" class=\"btn btn-danger\" onClick='excluir(" + emp.getId() + ")'>Excluir</button>"
								+ "</div>"
								+ "</td>"
								+ "</tr>";
					}
					response.getWriter().write(htmlAtrasados);
					break;

				case "obter":
					Long id = Long.parseLong(request.getParameter("id"));
					Emprestimo emprestimo = this.dao.obter(id);
					String jsonEmprestimo = gson.toJson(emprestimo);
					response.getWriter().write(jsonEmprestimo);
					break;

				default:
					response.setContentType("text/plain");
					List<Emprestimo> emprestimosListagem = this.dao.obterTodos();

					String htmlListagem = "";

					for (int i = 0; i < emprestimosListagem.size(); i++) {
						Emprestimo emp = emprestimosListagem.get(i);

						htmlListagem += "<tr>"
								+ "<td>" + emp.getAluno().getNome() + "</td>"
								+ "<td>" + emp.getUnidadeLivro().getLivro().getNome() + " - " + this.padLeftLoopRaw(emp.getUnidadeLivro().getId().toString(), '0', 12) + "</td>"
								+ "<td>" + this.getBadge(emp.getStatus()) + "</td>"
								+ "<td>"
								+ "<div class=\"btn-group btn-group-sm\" role=\"group\">"
								+ "<a href=\"emprestimo.jsp?id=" + emp.getId() + " \" class=\"btn btn-primary\">Editar</a>"
								+ "<button type=\"button\" class=\"btn btn-danger\" onClick='excluir(" + emp.getId() + ")'>Excluir</button>"
								+ "</div>"
								+ "</td>"
								+ "</tr>";
					}
					response.getWriter().write(htmlListagem);
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
					Emprestimo emprestimo = new Emprestimo();

					int periodo = Integer.parseInt(request.getParameter("periodo"));

					if (!request.getParameter("id").equals("")) {
						emprestimo.setId(Long.parseLong(request.getParameter("id")));

						DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
						Date date = (Date) formatter.parse(request.getParameter("dataDevolucao"));
						emprestimo.setDataDevolucao(date);

						emprestimo.setEstado(Integer.parseInt(request.getParameter("estado")));
					} else {
						DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
						Date currentDate = new Date();
						Calendar c = Calendar.getInstance();
						c.setTime(currentDate);
						c.add(Calendar.YEAR, periodo);
						Date date = c.getTime();
						emprestimo.setDataDevolucao(date);

						emprestimo.setEstado(1);
					}

					UnidadeLivro unLivro = this.unidadeLivroDAO.obter(Long.parseLong(request.getParameter("idUnidadeLivro")));
					emprestimo.setUnidadeLivro(unLivro);

					Aluno aluno = this.alunoDAO.obter(Long.parseLong(request.getParameter("idAluno")));
					emprestimo.setAluno(aluno);

					emprestimo.setPeriodo(periodo);
					emprestimo.setStatus(Integer.parseInt(request.getParameter("status")));

					this.dao.salvar(emprestimo);
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

	private String padLeftLoopRaw(String input, char padChar, int padUpTo) {
		String out = new String();
		for (int toPrepend = padUpTo - input.length(); toPrepend > 0; toPrepend--) {
			out += padChar;
		}
		return out + input;
	}

	private String getBadge(int status) {
		if (status == 1) {
			return "<span class='badge badge-pill badge-success'>Ativo</span>";
		} else {
			return "<span class='badge badge-pill badge-primary'>Devolvido</span>";
		}
	}
}
