package model;

import java.util.List;

import dao.PlataformaDAO;

public class Plataforma {
	private int id;
	private String nome;
	private Usuario usuario;
	
    public Plataforma() {
        
    }
    
    public void verPlataforma(Usuario usuario) {
    	PlataformaDAO platDao = new PlataformaDAO();
    	List<Plataforma> plataformas = platDao.listarPlataforma(usuario);
    	int num = 0;
    	
    	for (Plataforma plataforma : plataformas) {
    		System.out.println(plataforma.getNome());
    		num++;
    	}
    	
    	if(num == 0) {
    		System.out.println("Você não possui nenhuma plataforma cadastrada!");
    	}
    	
    }

	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
    
    
}
