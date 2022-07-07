package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.mariadb.jdbc.internal.protocol.tls.MariaDbX509KeyManager;

import it.polito.tdp.artsmia.model.Arco;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola artisti connessi\n");
    	if(!model.isCreato()) {
    		txtResult.appendText("CREARE IL GRAFO!");
    		return;
    	}
    	List<Arco> connessi = new ArrayList<>();
    	connessi = model.getConnessi();
    	for(Arco a : connessi) {
    		txtResult.appendText(a.getId1() + "   " +a.getId2() + "   "+ a.getPeso() + "\n");
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso\n");
    	String id = txtArtista.getText();
    	try {
    		Integer artista = Integer.parseInt(id);
    		List<Integer> vertici = model.getVertici();
//    		boolean presente = false;
//    		for(Integer v : vertici) {
//    			if(v == artista)
//    				presente = true;
//    			else
//    				presente = false;
//    			}
//			if(presente == false) {
//				txtResult.appendText("L'artista inserito non è presente nel grafo!");
//				return;
//    		}
			
			List<Integer> best = model.cercaCammino(artista);
			for(Integer b : best) {
				txtResult.appendText(b+"\n");
			}
    	}catch(NumberFormatException e) {
    		txtResult.appendText("Inserire un id numerico!");
    		return;
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Crea grafo\n");
    	String ruolo = boxRuolo.getValue();
    	if(ruolo == "") {
    		txtResult.appendText("SELEZIONARE UN RUOLO!");
    		return;
    	}
    	model.creaGrafo(ruolo);
    	txtResult.appendText("#VERTICI: " + model.getNumeroVertici()+"\n");
    	txtResult.appendText("#ARCHI: " +model.getNumeroArchi() + "\n");
    	
    }

    public void setModel(Model model) {
    	this.model = model;
    	boxRuolo.getItems().addAll(model.getRuoli());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
