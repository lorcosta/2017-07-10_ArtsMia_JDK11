package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import java.util.stream.*;
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
    private ChoiceBox<Integer> boxLUN;

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
    		
    		List<Integer> lun=new ArrayList<>();
    		for(int i=2;i<visita.size();i++) {
    			lun.add(i);
    			System.out.println(i);
    		}
    		this.boxLUN.getItems().addAll(lun);
    	}
    	
    }

    @FXML
    void doCercaOggetti(ActionEvent event) {
    	String toBeParsed=this.txtObjectId.getText();
    	Integer objectId=null;
    	try {
    		objectId=Integer.parseInt(toBeParsed);
    	}catch (NumberFormatException e) {
    		e.printStackTrace();
    		this.txtResult.appendText("ATTENZIONE! L'id oggettoinserito non è un numero.\n");
    	}
    	/*Integer lun=this.boxLUN.getValue();
    	if(lun==null) {
    		this.txtResult.appendText("ATTENZIONE! Nessun LUN selezionato!\n");
    		return;
    	}*/
    	if(!model.test(objectId)) {
    		this.txtResult.appendText("ATTENZIONE! L'id oggetto inserito non esiste nel database");
    		return;
    	}else {
    		Integer lun=2;
    		List<ArtObject> cammino=model.cercaOggetti(objectId,lun);
    		this.txtResult.appendText("PESO TOTALE: "+model.getPesoCammino()+"\n");
    		for(ArtObject o:cammino) {
    			this.txtResult.appendText(o+"\n");
    		}
    	}
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
