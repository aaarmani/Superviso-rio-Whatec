package br.com.whatec.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import org.controlsfx.glyphfont.FontAwesome;

/**
 *
 * @author armani
 */
public class DeviceView extends HBox {
    private Button btnLed;
    private Label lblName;
    private Button btnReset;
    private FontAwesome fontAwesome;
    
    public DeviceView(String name) {
        initializeComponents(name);
        this.getChildren().addAll(btnLed, lblName, btnReset);
    }
    
    private void initializeComponents(String name) {
        fontAwesome = new FontAwesome();
        btnLed = new Button();
        btnLed.setId("btnLed");
        setLedVermelho();
        
        btnReset = new Button("Resetar");
        btnReset.setId("btnReset");
        
        lblName = new Label(name);
    }
    
    
    public void setLedVermelho() {
        btnLed.setGraphic(fontAwesome.create("square").color(Color.RED));
    }
    
    public void setLedAmarelo() {
        btnLed.setGraphic(fontAwesome.create("square").color(Color.YELLOW));
    }
    
    public void setLedVerde() {
        btnLed.setGraphic(fontAwesome.create("square").color(Color.GREEN));
    }

    public Button getBtnLed() {
        return btnLed;
    }

    public void setBtnLed(Button btnLed) {
        this.btnLed = btnLed;
    }

    public Label getLblName() {
        return lblName;
    }

    public void setLblName(Label lblName) {
        this.lblName = lblName;
    }

    public Button getBtnReset() {
        return btnReset;
    }

    public void setBtnReset(Button btnReset) {
        this.btnReset = btnReset;
    }
}
