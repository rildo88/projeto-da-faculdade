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
import model.Status;
import model.Usuario;

public class SerieDAO {

	private ConnectionFactory connection;

	public SerieDAO() {
		this.connection = new ConnectionFactory();
	}

	public void salvarSerie(Serie serie) {
		
		String sql = "insert into serie (nome, plataforma_id, temporadas, status_serie, lancamento , usuario_id)"
				+ "values (? , ? , ? , ? , ? , ? );";
		Connection conn = connection.conexao();
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setString(1, serie.getNome());
			preparedStatement.setInt(2, serie.getPlataforma().getId());
			preparedStatement.setInt(3, serie.getTemporadas());
			preparedStatement.setString(4, serie.getStatus().name());
			preparedStatement.setString(5, serie.getLancamento());
			preparedStatement.setInt(6, serie.getUsuario().getId());

			if(!preparedStatement.execute()) {
				System.out.println("\nSérie salva com sucesso!");
			}
		} catch (SQLException e) {
				e.printStackTrace();
			
		}
	}

	public List<Serie> listarSerie(Usuario usuario) {
		List<Serie> series = new ArrayList<>();

		Connection conn = connection.conexao();

		try {
			String sql = "select nome from serie where usuario_id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, usuario.getId());
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Serie serie = new Serie();
				serie.setNome(rs.getString("nome"));
				series.add(serie);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return series;
	}
	
	public Serie detalhesSerie(Serie s) {
		
		Serie serie = new Serie();
		Connection conn = connection.conexao();
		
		try {
			String sql = "select * from serie where id = ?";
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, s.getId());
			ResultSet rs = preparedStatement.executeQuery();
			
			if (rs.next()) {
				Plataforma plat = new Plataforma();
				PlataformaDAO platDao = new PlataformaDAO();
				serie.setNome(rs.getString("nome"));
				serie.setLancamento(rs.getString("lancamento"));
				switch(rs.getString("status_serie")) {
				case "QUERO_ASSISTIR":
					serie.setStatus(Status.QUERO_ASSISTIR);
					break;
				case "ASSISTINDO":
					serie.setStatus(Status.ASSISTINDO);
					break;
				case "CONCLUIDA":
					serie.setStatus(Status.CONCLUIDA);
					break;
				}
				serie.setTemporadas(rs.getInt("temporadas"));
				plat.setId(rs.getInt("plataforma_id"));
				plat.setNome(platDao.obterNome(plat));
				serie.setPlataforma(plat);
				serie.setId(rs.getInt("id"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return serie;
	}

	public boolean removerSerie(Serie serie) {
		Connection conn = connection.conexao();
		boolean check = false;
		String sql = "delete from serie where id = ?;";
		try {
			PreparedStatement preparedStatement = conn.prepareStatement(sql);
			preparedStatement.setInt(1, serie.getId());
			if (!preparedStatement.execute()) {
				System.out.println("Série removida com sucesso!");
				check = true;
			}

		} catch (SQLException e) {
			System.out.println("Nome de série não encontrado, tente novamente!");
		}

		return check;

	}

	public int obterId(Serie serie, Usuario usuario) {
		String sql = "select * from serie where nome = ? and usuario_id = ?";
		Connection conn = connection.conexao();
		int id = 0;
		try {
			PreparedStatement prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, serie.getNome());
			prepareStatement.setInt(2, usuario.getId());
			ResultSet rs = prepareStatement.executeQuery();
			if (rs.next()) {
				id = rs.getInt("id");
			} else {
				id = 0;
			}
		} catch (SQLException e) {
			
		}
		
		return id;
	}

	public boolean alterarNomeSerie(Serie serie) {
		boolean check = false;
		
		Connection conn = connection.conexao();
		String sql = "update serie set nome = ? where id = ?;";
		try {
			PreparedStatement prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, serie.getNome());
			prepareStatement.setInt(2, serie.getId());
			
			if(!prepareStatement.execute()) {
				System.out.println("\nNome da série alterado com sucesso!");
				check = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return check;
	}
	
	public boolean alterarTemporadasSerie(Serie serie) {
		boolean check = false;
		
		Connection conn = connection.conexao();
		String sql = "update serie set temporadas = ? where id = ?;";
		try {
			PreparedStatement prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setInt(1, serie.getTemporadas());
			prepareStatement.setInt(2, serie.getId());
			
			if(!prepareStatement.execute()) {
				System.out.println("\nTemporadas da série alterada com sucesso!");
				check = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return check;
	}
	
	public boolean alterarLancamentoSerie(Serie serie) {
		boolean check = false;
		
		Connection conn = connection.conexao();
		String sql = "update serie set lancamento = ? where id = ?;";
		try {
			PreparedStatement prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, serie.getLancamento());
			prepareStatement.setInt(2, serie.getId());
			
			if(!prepareStatement.execute()) {
				System.out.println("\nLançamento da série alterado com sucesso!");
				check = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return check;
	}
	
	public boolean alterarStatusSerie(Serie serie) {
		boolean check = false;
		
		Connection conn = connection.conexao();
		String sql = "update serie set status_serie = ? where id = ?;";
		try {
			PreparedStatement prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setString(1, serie.getStatus().name());
			prepareStatement.setInt(2, serie.getId());
			
			if(!prepareStatement.execute()) {
				System.out.println("\nStatus da série alterado com sucesso!");
				check = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return check;
	}
	
	public boolean alterarPlataformaSerie(Serie serie) {
		boolean check = false;
		
		Connection conn = connection.conexao();
		String sql = "update serie set plataforma_id = ? where id = ?;";
		try {
			PreparedStatement prepareStatement = conn.prepareStatement(sql);
			prepareStatement.setInt(1, serie.getPlataforma().getId());
			prepareStatement.setInt(2, serie.getId());
			
			if(!prepareStatement.execute()) {
				System.out.println("\nPlataforma da série alterada com sucesso!");
				check = true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return check;
	}

}
