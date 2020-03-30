package it.polito.tdp.lab04.model;

public class Studente {
	
	private Integer matricola;
	private String nome;
	private String cognome;
	private String cds;
	
	/**
	 * @param matricola
	 * @param nome
	 * @param cognome
	 * @param cds
	 */
	public Studente(Integer matricola, String nome, String cognome, String cds) {
		super();
		this.matricola = matricola;
		this.nome = nome;
		this.cognome = cognome;
		this.cds = cds;
	}

	public Studente() {
		// TODO Auto-generated constructor stub
	}

	public Integer getMatricola() {
		return matricola;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getCds() {
		return cds;
	}
	
	
	
	

}
