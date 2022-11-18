package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {

	/**
	 * Módulo de conexão
	 */
	// Parâmetros de conexão
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "root";

	// Método de conexão
	private Connection conectar() {
		Connection con = null;
		try {
			// Carrega o driver de conexão com o banco de dados
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/**
	 * CRUD CREATE
	 */
	public void inserirContato(JavaBeans contato) {
		String query = "INSERT INTO contatos (nome, fone, email) VALUES (?, ?, ?)";
		try {
			// Abrindo conexão com banco
			Connection con = conectar();
			// Preparando query para ser executada
			PreparedStatement pst = con.prepareStatement(query);
			// Substituindo os parâmetros (?) pelo conteúdo das variáveis JavaBeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			// Executando a query
			pst.executeUpdate();
			// Encerrando a conexão com o banco
			con.close();
			System.out.println(query);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(query);
		}
	}

	/**
	 * CRUD READ
	 */
	public ArrayList<JavaBeans> listarContatos() {
		// Criando um objeto para armazenar os contatos recuperados do banco de dados
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		// Criando a String que servirá como query de consulta
		String query = "SELECT * FROM contatos ORDER BY nome";
		try {
			// Abrindo conexão com banco
			Connection con = conectar();
			// Preparando query para ser executada
			PreparedStatement pst = con.prepareStatement(query);
			// Armazenando o retorno do banco de dados temporariamente em um result set
			ResultSet rs = pst.executeQuery();
			// Declarando as variáveis que armazenarão os resultados da consulta ao banco de
			// dados
			String idcon;
			String nome;
			String fone;
			String email;
			// Laço de repetição que é executado enquanto houver contatos
			while (rs.next()) {
				// Armazenando os dados recuperados do banco
				idcon = rs.getString(1);
				nome = rs.getString(2);
				fone = rs.getString(3);
				email = rs.getString(4);
				// Armazenando o contato na lista de contatos
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			// Encerrando a conexão com o banco
			con.close();
			System.out.println(query);
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			System.out.println(query);
			return null;
		}
	}

	/**
	 * CRUD UPDATE
	 */
	public boolean alterarContato(JavaBeans contato) {
		String query = "UPDATE contatos SET nome = ?, fone = ?, email = ? WHERE idcon = ?";
		try {
			Connection conx = conectar();
			PreparedStatement pst = conx.prepareStatement(query);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			Integer result = pst.executeUpdate();
			conx.close();
			if (result == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	/**
	 * CRUD DELETE
	 */
	public boolean deletarContato(JavaBeans contato) {
		String query = "DELETE FROM contatos WHERE idcon = ?";
		try {
			Connection conx = conectar();
			PreparedStatement pst = conx.prepareStatement(query);
			pst.setString(1, contato.getIdcon());
			Integer result = pst.executeUpdate();
			conx.close();
			if (result == 1) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}

	// Selecionando o contato
	public void selecionarContato(JavaBeans contato) {
		String query = "SELECT * FROM contatos WHERE idcon = ?";
		try {
			Connection conx = conectar();
			PreparedStatement pst = conx.prepareStatement(query);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			conx.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

// Teste de conexão
//	public void testeConexao() {
//		try {
//			Connection con = conectar();
//			System.out.println(con);
//			con.close();
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}
}
