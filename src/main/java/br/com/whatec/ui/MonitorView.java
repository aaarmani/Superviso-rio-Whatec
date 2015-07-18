package br.com.whatec.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;

/**
 *
 * @author armani
 */
public class MonitorView extends VBox {
    Label lblTitle;
    Separator separator;
    VBox viewDevices;
    
    public MonitorView() {
        inicializaComponentes();
        this.getChildren().addAll(lblTitle, separator, viewDevices);
    }
    
    private void inicializaComponentes() {
        lblTitle = new Label("Dispositivos:");
        separator = new Separator();
        viewDevices = new VBox();
    }
    
    public void addDevice(Node node) {
        if(!viewDevices.getChildren().contains(node))
            viewDevices.getChildren().add(node);
    }
    
    public void removeDevice(Node node) {
        viewDevices.getChildren().remove(node);
    }
}
