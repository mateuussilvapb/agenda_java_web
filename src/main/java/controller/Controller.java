package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insertContato", "/select", "/update", "/delete", "/report" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DAO dao = new DAO();

	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insertContato")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			selecionarContato(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {
			deletarContato(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		} else {
			response.sendRedirect("index.html");
		}
//		// Teste de conex�o
//		dao.testeConexao();
	}

	// Gerar relat�rio
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document documento = new Document();
		try {
			// Tipo de conte�do
			response.setContentType("application/pdf");
			// Nome do documento
			response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");
			// Criando o documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			// Abrindo o documento -> Conte�do.
			documento.open();
			// Adicionando um par�grafo
			documento.add(new Paragraph("Lista de Contatos: "));
			// Quebrando uma linha
			documento.add(new Paragraph(" "));
			// Criando uma tabela
			PdfPTable tabela = new PdfPTable(3);
			// Cabe�alho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			// Populando a tabela com os contatos
			ArrayList<JavaBeans> lista = dao.listarContatos();
			for (JavaBeans contato : lista) {
				tabela.addCell(contato.getNome());
				tabela.addCell(contato.getFone());
				tabela.addCell(contato.getEmail());
			}
			documento.add(tabela);
			documento.close();
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
	}

	// Deletar contato
	protected void deletarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Setando o ID na vari�vel JavaBeans
		contato.setIdcon(request.getParameter("idcon"));
		// Executando o m�todo para deletar o contato
		boolean result = dao.deletarContato(contato);
		// Redirecionando para a p�gina principal (agenda.jsp) atualizando as altera��es
		if (result) {
			response.setHeader("retornoOk", "E");
		} else {
			response.setHeader("retornoOk", "N");
		}
		RequestDispatcher rd = request.getRequestDispatcher("main");
		rd.forward(request, response);
	}

	// Editar contato
	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Setando as vari�veis JavaBeans;
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// Executando o m�todo para alterar o contato
		boolean result = dao.alterarContato(contato);
		// Redirecionando para a p�gina principal (agenda.jsp) atualizando as altera��es
		if (result) {
			response.setHeader("retornoOk", "S");
		} else {
			response.setHeader("retornoOk", "N");
		}
		RequestDispatcher rd = request.getRequestDispatcher("main");
		rd.forward(request, response);
	}

	// Selecionar contato
	protected void selecionarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Recebendo o id do contato que ser� editado
		String idcon = request.getParameter("idcon");
		// Setando a vari�vel JavaBeans
		contato.setIdcon(idcon);
		// Executar o m�todo selecionarContato (DAO)
		dao.selecionarContato(contato);
		// Setar os atributos do formul�rio com o conte�do JavaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		// Encaminhando informa��es para o documento JSP
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}

	// Listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Criando um Array que receber� os dados recuperados do banco de dados
		ArrayList<JavaBeans> contatos = dao.listarContatos();
		// Encaminhar a lista ao documento agenda.jsp
		request.setAttribute("contatos", contatos);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
//		for (JavaBeans contato : contatos) {
//			System.out.println(contato.toString());
//		}
	}

	// Inserir contato
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Capturando as informa��es da requisi��o
		String nome = request.getParameter("nome");
		String fone = request.getParameter("fone");
		String email = request.getParameter("email");
		// Setando os valores da requisi��o no Bean contato
		contato.setNome(nome);
		contato.setFone(fone);
		contato.setEmail(email);
		// Invocando o m�todo inserirContato passando o objeto contato
		dao.inserirContato(contato);
		// Redirecionando para o documento agenda.jsp
		response.sendRedirect("main");
	}
}
