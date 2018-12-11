package br.ifrs.biblioteca.inc;

import java.io.IOException;
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
public class ControleAcesso {

	public static void verificar(HttpServletRequest request, HttpServletResponse response) {
		if (ControleAcesso.estaLogado(request, response) == false) {
			request.setAttribute("mensagem", "<script>exibirMensagemLogin('Acesso negado!')</script>");
			ControleAcesso.logout(request, response);
		}
	}

	public static boolean estaLogado(HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession(true);
			return (session.getAttribute("idUsuario") != null);
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	public static void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(true);
		session.invalidate();
		RequestDispatcher view = request.getRequestDispatcher("/login.jsp");

		try {
			view.forward(request, response);
		} catch (ServletException | IOException ex) {
			System.out.println(ex);
		}
	}

}
