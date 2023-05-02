package dao.controle;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDAO;
import modelo.Usuario;

/**
 * Este servlet atua como um controlador de página da aplicação, atendendo as
 * solicitações do usuário.
 *
 */

@WebServlet("/")
public class UsuarioServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UsuarioDAO usuarioDAO;

	public void init() {
		usuarioDAO = new UsuarioDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getServletPath();

		try {
			switch (acao) {
			case "/novo":
				exibirFormularioInclusao(request, response);
				break;
			case "/inserir":
				inserirUsuario(request, response);
				break;
			case "/deletar":
				deletarUsuario(request, response);
				break;
			case "/editar":
				exibirFormularioEdicao(request, response);
				break;
			case "/atualizar":
				atualizarUsuario(request, response);
				break;
			default:
				listarUsuario(request, response);
				break;
			}
		} catch (SQLException erro) {
			throw new ServletException(erro);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void listarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List listarUsuario = usuarioDAO.selecionarUsuarios();
		request.setAttribute("listarUsuario", listarUsuario);
		RequestDispatcher renderizar = request.getRequestDispatcher("listagem-usuario.jsp");
		renderizar.forward(request, response);
	}

	private void exibirFormularioInclusao(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher renderizar = request.getRequestDispatcher("formulario-usuario.jsp");
		renderizar.forward(request, response);
	}

	private void exibirFormularioEdicao(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int codigo = Integer.parseInt(request.getParameter("codigo"));
		Usuario selecaoUsuario = usuarioDAO.selecionarUsuario(codigo);
		RequestDispatcher renderizar = request.getRequestDispatcher("formulario-usuario.jsp");
		request.setAttribute("usuario", selecaoUsuario);
		renderizar.forward(request, response);
	}

	private void inserirUsuario(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ParseException {
				DateTimeFormatter formattime = DateTimeFormatter.ofPattern("HH:mm");
		String nome = request.getParameter("nome");
		Date dia = new SimpleDateFormat("yyyy-mm-dd").parse(request.getParameter("dia"));
		Time hora = (Time) new SimpleDateFormat("hh:mm").parse(request.getParameter("hora"));
		Usuario adicaoUsuario = new Usuario(nome, dia, hora);
		usuarioDAO.inserirUsuario(adicaoUsuario);
		response.sendRedirect("listagem");
	}

	private void atualizarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ParseException {
		int codigo = Integer.parseInt(request.getParameter("codigo"));
		String nome = request.getParameter("nome");
		Date dia = new SimpleDateFormat("yyyy-mm-dd").parse(request.getParameter("dia"));
		Time hora = (Time) new SimpleDateFormat("hh:mm").parse(request.getParameter("hora"));
		Usuario atualizacaoUsuario = new Usuario(codigo, nome, dia, hora);
		usuarioDAO.atualizarUsuario(atualizacaoUsuario);
		response.sendRedirect("listagem");
	}

	private void deletarUsuario(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException {
		int codigo = Integer.parseInt(request.getParameter("codigo"));
		usuarioDAO.deletarUsuario(codigo);
		response.sendRedirect("listagem");
	}

}