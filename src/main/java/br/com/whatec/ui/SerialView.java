package br.com.whatec.ui;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 *
 * @author armani
 */
public class SerialView extends VBox {
    private ComboBox<String> cbSerial;
    private Button btnOK;
    private Button btnCancel;

    public SerialView() {
        this.getStyleClass().add("centerPane");
        initializeComponents();
    }
    
    private void initializeComponents() {
        VBox head = buildHead();
        HBox inputs = buildInputs();
        HBox buttons = buildButtons();
        
        this.getChildren().addAll(head, inputs, buttons);  
    }
    
    private VBox buildHead() {
        VBox vbox = new VBox();
        return vbox;
    }
    
    private HBox buildInputs() {
        Label lblSerial = new Label("Seriais:");
        cbSerial = new ComboBox();
        cbSerial.setMaxWidth(Double.MAX_VALUE);
        cbSerial.setPromptText("Escolha uma porta");
        
        HBox hbox = new HBox(lblSerial, cbSerial);
        
        return hbox;
    }
    
    private HBox buildButtons() {
        btnCancel = new Button("Cancelar");
        btnCancel.setId("cancel");
        btnCancel.setCancelButton(true);
        btnCancel.getStyleClass().add("flatButton");
        
        btnOK = new Button("Abrir");
        btnOK.setId("ok");
        btnOK.setDefaultButton(true);
        btnOK.getStyleClass().add("flatButton");
        
        HBox hbox = new HBox();
        hbox.getChildren().addAll(btnOK);   
        return hbox;
    }

    public ComboBox<String> getCbSerial() {
        return cbSerial;
    }

    public void setCbSerial(ComboBox<String> cbSerial) {
        this.cbSerial = cbSerial;
    }

    public Button getBtnOK() {
        return btnOK;
    }

    public void setBtnOK(Button btnOK) {
        this.btnOK = btnOK;
    }

    public Button getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(Button btnCancel) {
        this.btnCancel = btnCancel;
    }
    
    
}
