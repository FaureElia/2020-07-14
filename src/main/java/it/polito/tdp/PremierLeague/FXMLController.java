/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.PremierLeague;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.PremierLeague.model.Coppia;
import it.polito.tdp.PremierLeague.model.Model;
import it.polito.tdp.PremierLeague.model.Team;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnClassifica"
    private Button btnClassifica; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="cmbSquadra"
    private ComboBox<Team> cmbSquadra; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="txtX"
    private TextField txtX; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doClassifica(ActionEvent event) {
    	this.txtResult.clear();
    	Team team=this.cmbSquadra.getValue();
    	if(team==null) {
    		this.txtResult.setText("inserire una squadrsa");
    		return;
    	}
    	else {
    		this.txtResult.appendText(team+"ha battuto: \n");
    		for(Coppia c: this.model.Battute(team)) {
    			this.txtResult.appendText(c+"\n");
    		}
    		this.txtResult.appendText(team+"è stata sconfitta da: \n");
    		for(Coppia c: this.model.BattutaDa(team)) {
    			this.txtResult.appendText(c+"\n");
    		}
    	}

    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	List <Team> vertici=this.model.creaGrafo();
    	this.cmbSquadra.getItems().addAll(vertici);

    }

    @FXML
    void doSimula(ActionEvent event) {
    	String nString=this.txtN.getText();
    	String xString=this.txtX.getText();
    	if(nString=="" ||xString=="") {
    		this.txtResult.setText("inserire valori");
    		return;
    		
    	}
    	try {
    		int n=Integer.parseInt(nString);
    		int x=Integer.parseInt(xString);
    		this.model.doSimulazione(n,x);
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("inserire valori numerici");
    		
    	}

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnClassifica != null : "fx:id=\"btnClassifica\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbSquadra != null : "fx:id=\"cmbSquadra\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX != null : "fx:id=\"txtX\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    }
}
