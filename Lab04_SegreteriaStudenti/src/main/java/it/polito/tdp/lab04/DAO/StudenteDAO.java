package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class StudenteDAO {
	
	public StudenteDAO() {
	}
	
	public Studente getStudenteByMatricola (Integer matricola) {
		
		String sql = "select * from studente where matricola = ?";
		Studente studente = null;
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, matricola);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				studente = new Studente (rs.getInt("matricola"), rs.getString("nome"), rs.getString("cognome"),
					rs.getString("CDS"));
			}
			conn.close();
			
		}  catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nel caricamento dal DB dello studente corrispondente al numero matricola");
		}
		
		return studente; 
		
	}
	
	/**
	 * restituisce la lista dei corsi a cui il parametro {@code Integer } matricola è iscritto
	 * @param matricola numero matricola 
	 * @return {@code List <Corso>}  
	 */
	public List <Corso> cercaCorsi (Integer matricola) {
		
		String sql = "select corso.nome, corso.codins, corso.pd, corso.crediti from corso,iscrizione where corso.codins=iscrizione.codins and iscrizione.matricola=?";
		List <Corso> corsi = new ArrayList <Corso> ();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			if (getStudenteByMatricola(matricola)==null) {
				return corsi;
			}
			
			ps.setInt(1, matricola);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				String codins = rs.getString("codins");
				Integer crediti = rs.getInt("crediti");
				Integer pd = rs.getInt("pd");
				String nome = rs.getString("nome");
				corsi.add(new Corso (codins, crediti, nome, pd));
			}
			conn.close();
		
		}  catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Errore nella ricerca corsi di uno studente data la matricola");
		}
		
		return corsi;
		
	}

}
