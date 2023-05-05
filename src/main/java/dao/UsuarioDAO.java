package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import modelo.Usuario;

/**
 *
 * Esta classe DAO permite operações de inserção, leitura, atualização e
 * exclusão na tabela de 'usuario' do banco de dados.
 *
 */

public class UsuarioDAO {
	// URL de conexão do banco de dados 'MaxSystem' do SGBD MySQL
	private String jdbcURL = "jdbc:mysql://localhost/maxsystem?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
	// Nome de usuário 'root' para acesso ao banco de dados do SGBD MySQL
	private String jdbcNomeUsuario = "root";
	// Senha do usuário 'root' para acesso ao banco de dados do SGBD MySQL
	private String jdbcSenha = "Rsb@1208";

	private static final String INSERIR_USUARIO = "INSERT INTO morador" + " (nome, dia, hora) VALUES "
			+ " (?, ?, ?);";
	private static final String SELECIONAR_USUARIO = "SELECT codigo, nome, dia, hora FROM morador WHERE codigo = ?";
	private static final String SELECIONAR_USUARIOS = "SELECT * FROM morador";
	private static final String DELETAR_USUARIO = "DELETE FROM morador WHERE codigo = ?;";
	private static final String ATUALIZAR_USUARIO = "UPDATE morador SET nome = ?, dia = ? , hora = ? WHERE codigo = ?;";

	public UsuarioDAO() {
	}

	protected Connection getConnection() {
		Connection conexao = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection(jdbcURL, jdbcNomeUsuario, jdbcSenha);
		} catch (SQLException erro) {
			erro.printStackTrace();
		} catch (ClassNotFoundException erro) {
			erro.printStackTrace();
		}
		return conexao;
	}

	public void inserirUsuario(Usuario usuario) throws SQLException {
		// Fecha automaticamente a conexão após o uso
		try (Connection conexao = getConnection();
				PreparedStatement executarComando = conexao.prepareStatement(INSERIR_USUARIO)) {
			// O codigo do usuário é omitido do comando, pois foi definido na tabela como
			// autoincremento
			executarComando.setString(1, usuario.getNome());
			executarComando.setDate(2, usuario.getDia());
			executarComando.setTime(3, usuario.getHora());
			System.out.println(executarComando);
			executarComando.executeUpdate();
		} catch (SQLException erro) {
			printSQLException(erro);
		}
	}

	public Usuario selecionarUsuario(int codigo) {
		Usuario usuario = null;
		// Etapa 1: estabelece a conexão
		try (Connection conexao = getConnection();
				// Etapa 2: cria o comando da instrução usando o objeto da conexão
				PreparedStatement executarComando = conexao.prepareStatement(SELECIONAR_USUARIO);) {
			executarComando.setInt(1, codigo);
			System.out.println(executarComando);
			// Etapa 3: executa ou atualiza a query
			ResultSet resultado = executarComando.executeQuery();
			// Etapa 4: processa o objeto ResultSet
			while (resultado.next()) {
				String nome = resultado.getString("nome");
				Date dia = resultado.getDate("dia");
				Time hora = resultado.getTime("hora");
				usuario = new Usuario(codigo, nome, dia, hora);
			}
		} catch (SQLException erro) {
			printSQLException(erro);
		}
		return usuario;
	}

	public List selecionarUsuarios() {
		List usuarios = new ArrayList<>();
		// Código boilerplate
		// Etapa 1: estabelece a conexão
		try (Connection conexao = getConnection();
				// Etapa 2: cria o comando da instrução usando o objeto da conexão
				PreparedStatement executarComando = conexao.prepareStatement(SELECIONAR_USUARIOS);) {
			System.out.println(executarComando);
			// Etapa 3: executa ou atualiza a query
			ResultSet resultado = executarComando.executeQuery();
			// Etapa 4: processa o objeto ResultSet
			while (resultado.next()) {
				int codigo = resultado.getInt("codigo");
				String nome = resultado.getString("nome");
				Date dia = resultado.getDate("dia");
				Time hora = resultado.getTime("hora");
				usuarios.add(new Usuario(codigo, nome, dia, hora));
			}
		} catch (SQLException erro) {
			printSQLException(erro);
		}
		return usuarios;
	}

	public boolean deletarUsuario(int codigo) throws SQLException {
		boolean registroDeletado;
		try (Connection conexao = getConnection();
				PreparedStatement executarComando = conexao.prepareStatement(DELETAR_USUARIO);) {
			executarComando.setInt(1, codigo);
			System.out.println(executarComando);
			registroDeletado = executarComando.executeUpdate() > 0;
		}
		return registroDeletado;
	}

	public boolean atualizarUsuario(Usuario usuario) throws SQLException {
		boolean registroAtualizado;
		try (Connection connection = getConnection();
				PreparedStatement executarComando = connection.prepareStatement(ATUALIZAR_USUARIO);) {
			executarComando.setString(1, usuario.getNome());
			executarComando.setDate(2, usuario.getDia());
			executarComando.setTime(3, usuario.getHora());
			executarComando.setInt(4, usuario.getCodigo());
			registroAtualizado = executarComando.executeUpdate() > 0;
		}
		return registroAtualizado;
	}

	private void printSQLException(SQLException erro) {
		for (Throwable e : erro) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("Estado do SQL: " + ((SQLException) e).getSQLState());
				System.err.println("Código de erro: " + ((SQLException) e).getErrorCode());
				System.err.println("Mensagem: " + e.getMessage());
				Throwable causa = erro.getCause();
				while (causa != null) {
					System.out.println("Causa: " + causa);
					causa = causa.getCause();
				}
			}
		}
	}
}