package br.com.whatec.controller;

import br.com.whatec.ModSerial.SerialComm;
import br.com.whatec.model.DeviceModel;
import br.com.whatec.ui.DeviceView;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author armani
 */
public class DeviceController {
    private static final int BYTE_RETORNO = 3;
    private final SerialComm serialComm;
    private DeviceModel device;
    private DeviceView view;
    
    public DeviceController(SerialComm serialComm, String name, String endereco) {
        this.serialComm = serialComm;
        this.device = new DeviceModel(name, endereco);
        this.view = new DeviceView(name);
    }
    
    public void checaEstadoSerial() {
        byte[] bytesParaEnviar = new byte[9];
        bytesParaEnviar[0] = '1';
        bytesParaEnviar[1] = '1';
        bytesParaEnviar[2] = '8';
        bytesParaEnviar[3] = '1';
        bytesParaEnviar[4] = '0';
        bytesParaEnviar[5] = '1';
        bytesParaEnviar[6] = (byte) 0xF4;
        bytesParaEnviar[7] = '\r';
        bytesParaEnviar[8] = '\n';
        
        try {
            serialComm.txRx(bytesParaEnviar, 9, 300);
            byte[] bytesLidos = serialComm.read();
            
            if(bytesLidos.length == 7) {
                if(bytesLidos[BYTE_RETORNO] == 0x01) {
                    this.view.setLedVermelho();
                }
                else {
                    this.view.setLedVermelho();
                }
            }
            else {
                this.view.setLedAmarelo();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(DeviceController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DeviceModel getDevice() {
        return device;
    }

    public void setDevice(DeviceModel device) {
        this.device = device;
    }

    public DeviceView getView() {
        return view;
    }

    public void setView(DeviceView view) {
        this.view = view;
    }
}
