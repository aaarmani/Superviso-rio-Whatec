package br.com.whatec.controller;

import br.com.whatec.ModSerial.SerialComm;
import br.com.whatec.action.AbstractAction;
import br.com.whatec.event.AbstractEventListener;
import br.com.whatec.event.ComSelecionada;
import br.com.whatec.ui.MainView;
import gnu.io.PortInUseException;
import java.io.IOException;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;

/**
 *
 * @author armani
 */
public class MainController extends AbstractController {
    private MainView view;
    private SerialController serialController;
    private NewItemController newItemController;
    private MonitorController monitorController;
    private SerialComm serialComm;
    
    public MainController(Stage stage) {
        this.view = new MainView(stage);
        this.serialComm = new SerialComm();
        
        registerAction(this.view.getBtnSerialAction(), new AbstractAction() {
            @Override
            protected void action() {
                showSerialController();
            }
        });
        
        registerAction(this.view.getBtnNewItem(), new AbstractAction() {
            @Override
            protected void action() {
                showNewItemController();
            }
        });
        
        registerAction(this.view.getBtnMonitor(), new AbstractAction() {
            @Override
            protected void action() {
                showMonitorController();
            }
        });
        
        registerEventListener(ComSelecionada.class, new AbstractEventListener<ComSelecionada>() {
            @Override
            public void handleEvent(ComSelecionada event) {
                String strCom = event.getTarget();
                try {
                    serialComm.open(strCom);
                    serialComm.setParameters(SerialComm.BAUD_9600, SerialComm.DATABITS_7, SerialComm.STOPBITS_1, SerialComm.PARITY_EVEN);
                    serialController.closeView();
                    showMonitorController();
                } catch (PortInUseException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (TooManyListenersException ex) {
                    Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        showSerialController();
    }
    
    private void showSerialController() {
        //cleanUpOldControllers();
        if(serialController == null)
            serialController = new SerialController(this, this.view);
        serialController.showView();
    }
    
    private void showNewItemController() {
        //cleanUpOldControllers();
        if(newItemController == null)
            newItemController = new NewItemController(this, this.view);
        newItemController.showView();
    }
    
    private void showMonitorController() {
        //cleanUpOldControllers();
        if(monitorController == null)
            monitorController = new MonitorController(this, this.view, serialComm);
        monitorController.showView();
    }
    
    private void cleanUpOldControllers() {
        if(serialController != null) {
            serialController.cleanUp();
            serialController = null;
        }
        
        if(newItemController != null) {
            newItemController.cleanUp();
            newItemController = null;
        }
        
        if(monitorController != null) {
            //monitorController.cleanUp();
            monitorController = new MonitorController(this, view, serialComm);//null;
        }
    }
    
    @Override
    public void cleanUp() {
        
    }
}
