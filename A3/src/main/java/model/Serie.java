 package model;

import java.util.List;

import dao.SerieDAO;

public class Serie {
	private int id;
	private String nome;
    private int temporadas;
    private String lancamento;
    private Status status;
    private Plataforma plataforma;
    private Usuario usuario;
    
    public Serie() {
    	
    }
    
    public void verSeries(Usuario usuario) {
    	SerieDAO serieDao = new SerieDAO();
    	List<Serie> series = serieDao.listarSerie(usuario);
    	int num = 0;
    	System.out.println("\nSuas séries: \n");
    	for (Serie serie : series) {
    		System.out.println(serie.getNome());
    		num++;
    	}
    	
    	if(num == 0) {
    		System.out.println("Você não possui nenhuma série cadastrada!");
    	}
    	
    }
    
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getTemporadas() {
		return temporadas;
	}
	public void setTemporadas(int temporadas) {
		this.temporadas = temporadas;
	}
	public String getLancamento() {
		return lancamento;
	}
	public void setLancamento(String lancamento) {
		this.lancamento = lancamento;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Plataforma getPlataforma() {
		return plataforma;
	}
	public void setPlataforma(Plataforma plataforma) {
		this.plataforma = plataforma;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
    
	public String toString() {
		return String.format("\nNome: %s"
						 + "\nTemporadas: %d"
						 + "\nLançamento: %s"
						 + "\nStatus: %s"
						 + "\nPlataforma: %s"
						 , this.getNome(), this.getTemporadas(), this.getLancamento(), this.getStatus().name()
						 , this.getPlataforma().getNome());
	}
	
    
}
