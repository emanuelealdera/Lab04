package it.polito.tdp.lab04.model;

import java.util.List;

import it.polito.tdp.lab04.DAO.CorsoDAO;
import it.polito.tdp.lab04.DAO.StudenteDAO;

public class Model {
	
	StudenteDAO studenteDAO;
	CorsoDAO corsoDAO;

	public Model () {
		studenteDAO = new StudenteDAO();
		corsoDAO = new CorsoDAO();
	}
		
	
	public List <Corso> getCorsi() {
		return corsoDAO.getTuttiICorsi();
	}

	public Studente getStudenteByMatricola (Integer matricola) {
		return studenteDAO.getStudenteByMatricola(matricola);
	}

	public List <Studente> cercaIscrittiCorso (Corso corso) {
		return corsoDAO.getStudentiIscrittiAlCorso(corso);
	}

	public List <Corso> cercaCorsi (Integer matricola) {
		return studenteDAO.cercaCorsi(matricola);
	}

	public boolean iscrittoAlCorso (Corso corso, Integer matricola) {
		if (studenteDAO.cercaCorsi(matricola).contains(corso))
			return true;
		else
			return false;
	}
	
	public boolean iscriviStudente (Studente studente, Corso corso) {
		return corsoDAO.inscriviStudenteACorso(studente, corso);
	}
		
		
	}


