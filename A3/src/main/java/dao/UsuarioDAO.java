package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conexao.ConnectionFactory;
import model.Usuario;

public class UsuarioDAO {
	
	private ConnectionFactory connection;
	
	public UsuarioDAO() {
		this.connection = new ConnectionFactory();
	}
	
	public boolean salvarUsuario(Usuario usuario) {
		boolean check = false;
		String sql = "insert into usuario (nome, login, senha, email)"
				+ "values (? , ? , ? , ? )";
		Connection conn = connection.conexao();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, usuario.getNome());
			preparedStatement.setString(2, usuario.getLogin());
			preparedStatement.setString(3, usuario.getSenha());
			preparedStatement.setString(4, usuario.getEmail());
			if(!preparedStatement.execute()) {
				check = true;
				System.out.println("\nCadastro realizado com sucesso!");
			}
		} catch (SQLException e) {
			System.out.println("\nLogin já existente, informe um login diferente!");
			check = false;
		}
		
		return check;
	}
	
	public boolean login(Usuario usuario) {
				
		Connection conn = connection.conexao();
		PreparedStatement preparedStatement = null;
		ResultSet rs = null;
		boolean check = false;
		String sql = "select * from usuario where login = ? and senha = ?";
		
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, usuario.getLogin());
			preparedStatement.setString(2, usuario.getSenha());
			rs = preparedStatement.executeQuery();
			
			if(rs.next()) {
				System.out.println("\nLogin efetuado com sucesso!");
				check = true;
			}else {
				System.out.println("\nLogin ou senhas inválidos, tente novamente!");
				check = false;
			}
			
		} catch (SQLException e) {
			System.out.println("\nLogin ou senhas inválidos, tente novamente!");
			
		}
		
		return check;
		
	}
	
	public int obterId(Usuario usuario) {
		String sql = "(select * from usuario where login = ?)";
		Connection conn = connection.conexao();
		int id = 0;
		try {
			PreparedStatement prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, usuario.getLogin());
			ResultSet rs =  prepareStatement.executeQuery();
			if(rs.next()) {
				id = rs.getInt("id");
				
			}
		} catch (SQLException e) {
			
		}
		
		return id;
	}
	
	
	
}
