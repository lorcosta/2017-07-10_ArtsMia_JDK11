package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<?> boxLUN;

    @FXML
    private Button btnCalcolaComponenteConnessa;

    @FXML
    private Button btnCercaOggetti;

    @FXML
    private Button btnAnalizzaOggetti;

    @FXML
    private TextField txtObjectId;

    @FXML
    private TextArea txtResult;

    @FXML
    void doAnalizzaOggetti(ActionEvent event) {
    	this.txtResult.clear();
    	model.creaGrafo();
    	Integer vertici=model.getNumVertici(), archi=model.getNumArchi();
    	if(vertici==0 || archi.equals(0)) {
    		this.txtResult.appendText("ATTENZIONE! Qualcosa e' andato storto nella creazione del grafo.\n");
    		return;
    	}
    	this.txtResult.appendText("GRAFO CREATO!\n #VERTICI: "+vertici+" e #ARCHI: "+archi+"\n");
    	
    }

    @FXML
    void doCalcolaComponenteConnessa(ActionEvent event) {
    	this.txtResult.clear();
    	String toBeParsed=this.txtObjectId.getText();
    	Integer objectId=null;
    	try {
    		objectId=Integer.parseInt(toBeParsed);
    	}catch (NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ATTENZIONE! L'id oggettoinserito non è un numero.\n");
    	}
    	if(!model.test(objectId)) {
    		this.txtResult.appendText("ATTENZIONE! L'id oggetto inserito non esiste nel database");
    		return;
    	}else {
    		List<ArtObject> visita=model.componenteConnessa(objectId);
    		this.txtResult.appendText("La componente connessa e' composta da "+visita.size()+" vertici.\n");
    		/*for(ArtObject o:visita) {
    			this.txtResult.appendText(o+"\n");
    		}*/
    	}
    	
    }

    @FXML
    void doCercaOggetti(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert boxLUN != null : "fx:id=\"boxLUN\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaComponenteConnessa != null : "fx:id=\"btnCalcolaComponenteConnessa\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCercaOggetti != null : "fx:id=\"btnCercaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnAnalizzaOggetti != null : "fx:id=\"btnAnalizzaOggetti\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtObjectId != null : "fx:id=\"txtObjectId\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
