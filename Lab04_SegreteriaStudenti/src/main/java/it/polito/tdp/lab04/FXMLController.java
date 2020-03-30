package it.polito.tdp.lab04;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.lab04.model.Corso;
import it.polito.tdp.lab04.model.Model;
import it.polito.tdp.lab04.model.Studente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;


    @FXML  
    private ChoiceBox <String> choiceBox; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaIscrittiCorso"
    private Button btnCercaIscrittiCorso; // Value injected by FXMLLoader

    @FXML // fx:id="txtMatricola"
    private TextField txtMatricola; // Value injected by FXMLLoader

    @FXML // fx:id="btnRicercaStudente"
    private Button btnRicercaStudente; // Value injected by FXMLLoader

    @FXML // fx:id="txtNome"
    private TextField txtNome; // Value injected by FXMLLoader

    @FXML // fx:id="txtCognome"
    private TextField txtCognome; // Value injected by FXMLLoader

    @FXML // fx:id="btnCercaCorsi"
    private Button btnCercaCorsi; // Value injected by FXMLLoader

    @FXML // fx:id="btnIscrivi"
    private Button btnIscrivi; // Value injected by FXMLLoader

    @FXML // fx:id="txtArea"
    private TextArea txtArea; // Value injected by FXMLLoader

    @FXML // fx:id="btnReset"
    private Button btnReset; // Value injected by FXMLLoader
    
    
    public void caricaCorsi() {
    	choiceBox.getItems().add("Corsi");
    	for (Corso c : model.getCorsi()) {
    		choiceBox.getItems().add(c.getNome());
    	}
    }

    @FXML
    void cercaCorsi(ActionEvent event) {

    	txtArea.clear(); txtNome.clear(); txtCognome.clear();

    	String matricolaString = txtMatricola.getText();
    	Integer matricola = -1;

    	try {
    		matricola = Integer.parseInt(matricolaString);
    	} catch (NumberFormatException nfe) {
    		nfe.printStackTrace();
    		txtArea.setText("Si prega di inserire un valore numerico per la matricola");
    	}

    	if (model.getStudenteByMatricola(matricola) == null)  {
    		txtArea.setText("Matricola non presente nel sistema");
    		return;
    	}

    	// nella choiceBox il valore è corsi e devo restituire l'elenco dei corsi

    	try {


    		if (choiceBox.getValue().equals("Corsi")) {  
    			String elencoCorsi = "";
    			for (Corso c : model.cercaCorsi(matricola)) {
    				elencoCorsi+=c.getCodIns()+"          "+c.getNome()+"        "+c.getNumeroCrediti()+"        "+c.getPd()+"\n";
    			}
    			txtArea.setText(elencoCorsi);

    			if (elencoCorsi.equals(""))
    				txtArea.setText("Lo studente non è iscritto a nessun corso");
    		} 


    		// nella choiceBox il valore è un corso e devo stabilire se la matricola selezionata è iscritta o meno
    		else { 
    			
    			String nomeCorso = choiceBox.getValue();
    			Corso temp = null;
    			for (Corso c : model.getCorsi()) {
    				if (c.getNome().equals(nomeCorso))
    					temp = c;
    			}
    			if (model.iscrittoAlCorso(temp, matricola))
    				txtArea.setText("Lo studente è iscritto al corso");
    			else
    				txtArea.setText("Lo studente non è iscritto al corso");


    		}} catch (NullPointerException npe) {
    			txtArea.setText("Si prega di selezionare un corso dal menu a tendina in alto");
    		}


    	}

    @FXML
    void cercaIscrittiCorso(ActionEvent event) {
    	
    	try {
    	String nomeCorso = choiceBox.getValue();
    	Corso temp=null;
    	for (Corso c:model.getCorsi()) {
    		if (c.getNome().equals(nomeCorso)) 
    			temp=c;
    	}
    	List <Studente> listaStudentiIscritti = model.cercaIscrittiCorso(temp);
    	txtArea.clear();
    	String list="";
    	for (Studente s : listaStudentiIscritti) {
    		list+=s.getMatricola()+"        "+s.getNome()+"         "+s.getCognome()+"         "+s.getCds()+"\n";
    	}
    	txtArea.setText(list);
    	if (listaStudentiIscritti.isEmpty())
    		txtArea.setText("Nessun iscritto a questo corso");
    	
    	} 
    	catch (NullPointerException npe) {
    		txtArea.clear();
    		txtArea.setText("Si prega di selezionare un corso dal menu a tendina in cima alla pagina");
    	}
    }

    @FXML
    void doReset(ActionEvent event) {

    	this.txtArea.clear();
    	this.txtCognome.clear();
    	this.txtNome.clear();
    	this.txtMatricola.clear();
    }


    @FXML
    void iscriviStudente(ActionEvent event) {
    	
    	txtArea.clear(); txtNome.clear(); txtCognome.clear();
    	
    	String nomeCorso = choiceBox.getValue();
    	String matricolaString = txtMatricola.getText();
    	int matricola = -1;
    	
    	try {
    		matricola = Integer.parseInt(matricolaString);
    	} catch (NumberFormatException nfe) {
    		nfe.printStackTrace();
    		txtArea.setText("Inserire un valore numerico nel campo matricola");
    	}
    	
    	Corso corsoT = null;
    	for (Corso c : model.getCorsi()) {
    		if (c.getNome().equals(nomeCorso))
    			corsoT=c;
    	}
    	
    	if (model.getStudenteByMatricola(matricola)==null) {
    		txtArea.setText("Matricola non presente nel sistema");
    		return;
    	}
    	
    	boolean temp = model.iscriviStudente(model.getStudenteByMatricola(matricola), corsoT);
    	
    	if (temp==true) 
    		txtArea.setText("Studente inserito con successo");
    	else
    		txtArea.setText("Studente già iscritto al corso");
    	
    }

    @FXML
    void ricercaStudente(ActionEvent event) {
    	
    	String matricolaString = txtMatricola.getText();
    	Integer matricola = -1;
    	
    	try {
    		matricola = Integer.parseInt(matricolaString);
    	} catch (NumberFormatException nfe) {
    		nfe.printStackTrace();
    		txtArea.setText("La matricola deve essere un valore numerico");
    	}
    	
    	Studente studente = model.getStudenteByMatricola(matricola);
    	if (studente==null) {
    		txtArea.clear();
    		txtArea.setText("La matricola da lei inserita non corrisponde ad uno studente");
    	}
    	else {
    		txtNome.setText(studente.getNome());
    		txtCognome.setText(studente.getCognome());
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert choiceBox != null : "fx:id=\"comboBoxCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaIscrittiCorso != null : "fx:id=\"btnCercaIscrittiCorso\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtMatricola != null : "fx:id=\"txtMatricola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnRicercaStudente != null : "fx:id=\"btnRicercaStudente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCognome != null : "fx:id=\"txtCognome\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCercaCorsi != null : "fx:id=\"btnCercaCorsi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnIscrivi != null : "fx:id=\"btnIscrivi\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'Scene.fxml'.";

    }

	public void setModel(Model model) {
		this.model=model;
		
	}
}
