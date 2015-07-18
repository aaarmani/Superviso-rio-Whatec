package br.com.whatec.controller;

import br.com.whatec.ModSerial.SerialComm;
import br.com.whatec.ui.MainView;
import br.com.whatec.ui.MonitorView;

/**
 *
 * @author armani
 */
public class MonitorController extends AbstractController {
    private MainView mainView;
    private MonitorView view;
    private SerialComm serialComm;

    public MonitorController(AbstractController parent, MainView mainView, SerialComm serialComm) {
        super(parent);
        this.mainView = mainView;
        this.view = new MonitorView();
        this.serialComm = serialComm;
        
        view.addDevice(new DeviceController(serialComm, "Teste Armani", "81").getView());
        showView();
    }
    
    public void showView() {
        this.mainView.getPaneCenter().getChildren().add(this.view);
    }
    
}
