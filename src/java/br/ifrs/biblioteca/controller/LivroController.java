package br.ifrs.biblioteca.controller;

import br.ifrs.biblioteca.dao.CategoriaDAO;
import br.ifrs.biblioteca.dao.LivroDAO;
import br.ifrs.biblioteca.dao.UnidadeLivroDAO;
import br.ifrs.biblioteca.model.Categoria;
import br.ifrs.biblioteca.model.Livro;
import br.ifrs.biblioteca.model.UnidadeLivro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "LivroController", urlPatterns = {"/LivroController"})
public class LivroController extends HttpServlet {

	private final LivroDAO dao;
	private final CategoriaDAO categoriaDAO;
	private final UnidadeLivroDAO unidadesDAO;

	public LivroController() {
		super();
		this.dao = new LivroDAO();
		this.categoriaDAO = new CategoriaDAO();
		this.unidadesDAO = new UnidadeLivroDAO();

	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String acao = request.getParameter("acao").toLowerCase();

		try {
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			System.out.println(acao);

			switch (acao) {
				case "pesquisar":
					String pesquisa = request.getParameter("pesquisa");
					List<Livro> livros = this.dao.pesquisar(pesquisa);
					String jsonLivros = gson.toJson(livros);
					response.getWriter().write(jsonLivros);
					break;

				case "obter":
					Long id = Long.parseLong(request.getParameter("id"));
					Livro livro = this.dao.obter(id);
					String jsonLivro = gson.toJson(livro);
					response.getWriter().write(jsonLivro);
					break;

				case "obterunidades":
					List<UnidadeLivro> unidades = this.unidadesDAO.obterTodos();
					String jsonUnidades = gson.toJson(unidades);
					response.getWriter().write(jsonUnidades);
					break;

				default:
					List<Livro> listagemLivros = this.dao.obterTodos();
					String jsonListagem = gson.toJson(listagemLivros);
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

		try {
			if (request.getContentType().indexOf("multipart/form-data") >= 0) {
				String saveFile = new String();
				String contentType = request.getContentType();

				if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
					DataInputStream in = new DataInputStream(request.getInputStream());
					int formDataLength = request.getContentLength();
					byte dataBytes[] = new byte[formDataLength];
					int byteRead = 0;
					int totalBytesRead = 0;

					while (totalBytesRead < formDataLength) {
						byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
						totalBytesRead += byteRead;
					}

					String file = new String(dataBytes);
					saveFile = file.substring(file.indexOf("filename=\"") + 10);
					saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
					saveFile = saveFile.substring(saveFile.lastIndexOf("\\") + 1, saveFile.indexOf("\""));

					int lastIndex = contentType.lastIndexOf("=");
					String boundary = contentType.substring(lastIndex + 1, contentType.length());

					int pos;

					pos = file.indexOf("filename=\"");
					pos = file.indexOf("\n", pos) + 1;
					pos = file.indexOf("\n", pos) + 1;
					pos = file.indexOf("\n", pos) + 1;

					int boundaryLocation = file.indexOf(boundary, pos) - 4;

					int startPos = ((file.substring(0, pos)).getBytes()).length;
					int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;

					String path = request.getServletContext().getRealPath("capas") + File.separator;
					request.getServletContext().getContextPath();

					//saveFile = "C:/work/" + saveFile;
					String resposta = request.getServletContext().getContextPath();
					resposta += "/capas/" + saveFile;
					saveFile = path + saveFile;

					File ff = new File(saveFile);

					try {
						FileOutputStream fileOut = new FileOutputStream(ff);
						fileOut.write(dataBytes, startPos, (endPos - startPos));
						fileOut.flush();
						fileOut.close();
					} catch (Exception e) {
						System.out.println(e);
					}

					System.out.println(resposta);
					response.getWriter().write(resposta);
				}

				return;
			}

			String acao = request.getParameter("acao").toLowerCase();
			switch (acao) {
				case "salvar":
					Livro livro = new Livro();

					if (!request.getParameter("id").equals("")) {
						livro.setId(Long.parseLong(request.getParameter("id")));
					}

					ArrayList<UnidadeLivro> unidadesLivro = new ArrayList<>();
					String idsUnidades[] = request.getParameterValues("unidadesLivro[]");
					int quantidadeUnidadesSalvas = 0;

					if (idsUnidades != null) {
						quantidadeUnidadesSalvas = idsUnidades.length;
						for (String idUnidade : idsUnidades) {
							UnidadeLivro unidadeLivro = this.unidadesDAO.obter(Long.parseLong(idUnidade));
							unidadesLivro.add(unidadeLivro);
						}
					}

					int quantidade = Integer.parseInt(request.getParameter("quantidade"));
					if (quantidade < quantidadeUnidadesSalvas) {
						quantidade = quantidadeUnidadesSalvas;
					}

					livro.setUnidades(unidadesLivro);
					Categoria categoria = this.categoriaDAO.obter(Long.parseLong(request.getParameter("idCategoria")));
					livro.setCategoria(categoria);
					livro.setNome(request.getParameter("nome"));
					livro.setAutor(request.getParameter("autor"));
					livro.setFoto("");
					livro.setIsbn(request.getParameter("isbn"));
					livro.setQuantidade(quantidade);
					livro.setVolume(Integer.parseInt(request.getParameter("volume")));

					this.dao.salvar(livro);

					if (quantidade > quantidadeUnidadesSalvas) {
						for (int i = 0; i < (quantidade - quantidadeUnidadesSalvas); i++) {
							UnidadeLivro unidadeLivro = new UnidadeLivro(livro);
							unidadesLivro.add(unidadeLivro);
						}

						livro.setUnidades(unidadesLivro);
						this.dao.salvar(livro);
					}

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
