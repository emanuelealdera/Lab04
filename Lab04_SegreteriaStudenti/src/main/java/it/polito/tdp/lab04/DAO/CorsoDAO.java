package it.polito.tdp.lab04.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Studente;

public class CorsoDAO {
	
	/*
	 * Ottengo tutti i corsi salvati nel Db
	 */
	public List<Corso> getTuttiICorsi() {

		final String sql = "SELECT * FROM corso";

		List<Corso> corsi = new LinkedList<Corso>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				String codins = rs.getString("codins");
				int numeroCrediti = rs.getInt("crediti");
				String nome = rs.getString("nome");
				int periodoDidattico = rs.getInt("pd");

				// Crea un nuovo JAVA Bean Corso
				// Aggiungi il nuovo oggetto Corso alla lista corsi
				corsi.add(new Corso(codins,numeroCrediti,nome,periodoDidattico));
			}

			conn.close(); st.close();
			
			return corsi;
			

		} catch (SQLException e) {
			// e.printStackTrace();
			throw new RuntimeException("Errore Db", e);
		}
	}
	
	
	/*
	 * Dato un codice insegnamento, ottengo il corso
	 */
	public Corso getCorso(String codiceCorso) {
		
		Corso result = null;
		
		try {
			
			final String sql = "select * from corso where corso.nome = ?";
			
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, codiceCorso);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				result = new Corso (rs.getString("codIns"), 
						rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
			}
			
			conn.close(); rs.close(); st.close();
			
		
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/*
	 * Ottengo tutti gli studenti iscritti al Corso
	 */
	public List <Studente> getStudentiIscrittiAlCorso(Corso corso) {
		
		final String sql = "select studente.nome, studente.matricola, studente.cognome, studente.CDS from studente, iscrizione where studente.matricola = iscrizione.matricola and iscrizione.codins = ?";
		List <Studente> listaStudenti = new ArrayList <Studente> ();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, corso.getCodIns());
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Integer matricola = rs.getInt("studente.matricola");
				String nome = rs.getString("studente.nome");
				String cognome = rs.getString("studente.cognome");
				String cds = rs.getString("studente.cds");
				listaStudenti.add(new Studente (matricola,nome,cognome,cds));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listaStudenti;
	}

	/*
	 * Data una matricola ed il codice insegnamento, iscrivi lo studente al corso.
	 */
	public boolean inscriviStudenteACorso(Studente studente, Corso corso) {
		List <Studente> studentiIscritti = getStudentiIscrittiAlCorso(corso);
		if (studentiIscritti.contains(studente))
			return false;
		else  {
			if (getTuttiICorsi().contains(corso)) {
				try {
					
					String sql = "insert into iscrizione(matricola,codins) values (? , ?)";
					
					Connection conn = ConnectDB.getConnection();
					PreparedStatement ps = conn.prepareStatement(sql);
					
					ps.setInt(1, studente.getMatricola());
					ps.setString(2, corso.getCodIns());
					ps.execute();
					conn.close();
					return true;
					
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Errore nell'iscrizione di una matricola ad un corso");
					return false;
				}
			}
		}
		return false;
	}

}
