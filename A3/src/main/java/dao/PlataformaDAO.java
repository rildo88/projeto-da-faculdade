package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexao.ConnectionFactory;
import model.Plataforma;
import model.Serie;
import model.Usuario;

@SuppressWarnings("unused")
public class PlataformaDAO {
	
	private ConnectionFactory connection;
	
	public PlataformaDAO() {
		this.connection = new ConnectionFactory();
	}
	
	public void salvarPlataforma(Plataforma plataforma) {
		String sql = "insert into plataforma (nome , usuario_id)"
				+ "values ( ? , ?);";
		Connection conn = connection.conexao();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, plataforma.getNome());
			preparedStatement.setInt(2, plataforma.getUsuario().getId());

			if(!preparedStatement.execute()) {
				System.out.println("Plataforma cadastrada com sucesso!");
			}
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			throw new RuntimeException(e);
		}
	}
	
	public List<Plataforma> listarPlataforma(Usuario usuario) {
		List<Plataforma> plataformas = new ArrayList<>();

		Connection conn = connection.conexao();

		try {
			String sql = "select nome from plataforma where usuario_id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, usuario.getId());
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Plataforma plataforma = new Plataforma();
				plataforma.setNome(rs.getString("nome"));
				plataformas.add(plataforma);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return plataformas;
	}
	
	public int obterId(Plataforma plataforma) {
		String sql = "(select * from plataforma where nome = ? and usuario_id = ?)";
		Connection conn = connection.conexao();
		int id = 0;
		try {
			PreparedStatement prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, plataforma.getNome());
			prepareStatement.setInt(2, plataforma.getUsuario().getId());
			ResultSet rs =  prepareStatement.executeQuery();
			if(rs.next()) {
				id = rs.getInt("id");
			}else {
				id = 0;
			}
		} catch (SQLException e) {
			
		}
		return id;
	}
	
	public String obterNome(Plataforma plataforma) {
		String sql = "(select * from plataforma where id = ?)";
		Connection conn = connection.conexao();
		String nome = null;
		try {
			PreparedStatement prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setInt(1, plataforma.getId());
			ResultSet rs =  prepareStatement.executeQuery();
			if(rs.next()) {
				nome = rs.getString("nome");
			}
		} catch (SQLException e) {
			
		}
		return nome;
	}
	
	public boolean removerPlataforma(Plataforma plataforma) {
		Connection conn = connection.conexao();
		boolean check = false;
		
		
		//Deletar todas as séries relacionadas a plataforma
		String sql = "delete from serie where plataforma_id = ?";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1 , plataforma.getId());
			preparedStatement.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//Deletar a plataforma
		sql = "delete from plataforma where id = ?;";
		try {
			preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, plataforma.getId());
			if (!preparedStatement.execute()) {
				System.out.println("Plataforma removida com sucesso!");
				check = true;
			}

		} catch (SQLException e) {
			System.out.println("Plataforma não encontrada, tente novamente!");
		}

		return check;

	}
	
}
