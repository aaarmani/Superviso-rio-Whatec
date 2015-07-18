package br.com.whatec.controller;

import br.com.whatec.ModSerial.SerialComm;
import br.com.whatec.action.AbstractAction;
import br.com.whatec.event.ComSelecionada;
import br.com.whatec.ui.MainView;
import br.com.whatec.ui.SerialView;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author armani
 */
public class SerialController extends PersistenceController {
    private final MainView mainView;
    private final SerialView view;
    private Timer timer;
    
    public SerialController(AbstractController parent, MainView mainView) {
        super(parent);
        this.mainView = mainView;
        this.view = new SerialView();
        
        registerAction(this.view.getBtnOK(), new AbstractAction() {
            @Override
            protected void action() {
                String strCom = view.getCbSerial().getSelectionModel().getSelectedItem();
                if(strCom != null && !strCom.isEmpty()) {
                    fireEvent(new ComSelecionada(strCom));
                }
            }
        });
        
    }
    
    public void showView() {
        this.mainView.getPaneCenter().getChildren().add(this.view);
        listSeriais();
    }
    
    public void closeView() {
        timer.cancel();
        if(this.mainView.getPaneCenter().getChildren().contains(this.view))
            this.mainView.getPaneCenter().getChildren().remove(this.view);
    }
    
    private void listSeriais() {
        timer = new Timer();
        timer.schedule(
            new TimerTask() {

                @Override
                public void run() {
                        List<String> serialList = SerialComm.getListSerial();
                        view.getCbSerial().getItems().setAll(serialList);
                    }
                }, 0, 5000);
    }

    @Override
    protected void cleanUp() {
        super.cleanUp(); //To change body of generated methods, choose Tools | Templates.
        timer.cancel();
    }
}
