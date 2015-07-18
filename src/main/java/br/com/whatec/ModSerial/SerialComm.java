package br.com.whatec.ModSerial;

/**
 * <p>Esta classe é responsável pela abstração da serial dos pcs. Trabalha com nrjavaserial-3.8.8.jar.
 * @see nrjavaserial-3.8.8.jar
 * 
 * @author		Armani
 * @version		v00.01 04/2014
 */

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.TooManyListenersException;

/**
 * Serial é uma abstração de controle de porta serial
 * @author armani
 */
public class SerialComm implements SerialPortEventListener{
	static final int OPEN_TIMEOUT = 200;
	private SerialPort serialPort = null;
	private InputStream inputStream = null;
	private OutputStream outputStream = null;
	
	//buffer de recpção
	private List<byte[]> lstReadBuffer = new ArrayList<byte[]>();
	

	//Parity
	public static final int PARITY_NONE = 0;
	public static final int PARITY_ODD = 1;
	public static final int PARITY_EVEN = 2;
	public static final int PARITY_MARK = 3;
	public static final int PARITY_SPACE = 4;
	private static final int MAX_PARITY = PARITY_SPACE;
	
	//BaudRate
	public static final int BAUD_9600 = 9600;
	public static final int BAUD_19200 = 19200;
	public static final int BAUD_38400 = 38400;
	public static final int BAUD_57600 = 57600;
	public static final int BAUD_115200 = 115200;
	private static final int MAX_BAUD = BAUD_115200;
	
	//DataBits
	public static final int DATABITS_5 = 5;
	public static final int DATABITS_6 = 6;
	public static final int DATABITS_7 = 7;
	public static final int DATABITS_8 = 8;
	private static final int MAX_DATABITS = DATABITS_8;
	
	//StopBits
	public static final int STOPBITS_1 = 1;
  	public static final int STOPBITS_2 = 2;
  	public static final int STOPBITS_1_5 = 3;
  	private static final int MAX_STOPBITS = STOPBITS_1_5;
  	
  	//FlowControl
  	public static final int FLOWCONTROL_NONE = 0;
  	public static final int FLOWCONTROL_RTSCTS_IN = 1;
  	public static final int FLOWCONTROL_RTSCTS_OUT = 2;
  	public static final int FLOWCONTROL_XONXOFF_IN = 4;
  	public static final int FLOWCONTROL_XONXOFF_OUT = 8;
  	//private static final int MAX_FLOWCONTROL = 21; //LÓGICA OU DOS ANTERIORES
	
	/**
	 * <p>Método de abertura de porta serial. Trabalha com serial e USBSerial. 
	 * @param portName - Nome da porta para o sistema operacional
	 * @throws PortInUseException 
	 * @throws IOException 
	 * @throws TooManyListenersException 
	 * @throws IllegalArgumentException, PortInUserException
	 */
	public void open(String portName) throws PortInUseException, IOException, TooManyListenersException {
		CommPortIdentifier cpiPort = getPortIdentifier(portName);
		
		if(cpiPort == null) {
			throw new IllegalArgumentException("Porta serial não encontrada.");
		}
			
		serialPort = cpiPort.open(this.getClass().getName(), OPEN_TIMEOUT);

		inputStream = serialPort.getInputStream();
		outputStream = serialPort.getOutputStream();
		
		serialPort.addEventListener(this);
        serialPort.notifyOnDataAvailable(true);
	}

	/**
	 * <p>Fecha porta serial em uso e remove listener
	 */
	public void close() {
		if(serialPort != null) {
			//para de escutar evento na serial
			serialPort.removeEventListener();
			
			//Fecha porta aberta
			serialPort.close();
			serialPort = null;
		}
	}
	
	/**
	 * <p> Seta os parâmetros de comunicação para a porta
	 * @param baudRate
	 * @param dataBits
	 * @param stopBits
	 * @param parity
	 * @throws IllegalArgumentException
	 */
	public void setParameters(int baudRate, int dataBits, int stopBits, int parity) throws IllegalArgumentException{
		//Verificação de entrada irregular
		if (((baudRate < 0) || (baudRate > MAX_BAUD)) ||
			((dataBits < 0) || (dataBits > MAX_DATABITS)) ||
			((stopBits < 0) || (stopBits > MAX_STOPBITS)) ||
			((parity < 0) || (parity > MAX_PARITY))){
			throw new IllegalArgumentException("Parâmetros não suportados.");
		}
		
		if(serialPort == null) {
			throw new IllegalArgumentException("Necessário abrir a porta antes.");
		}
		
		try {
			serialPort.setSerialPortParams(baudRate, dataBits, stopBits, parity);
		} catch (UnsupportedCommOperationException e) {
			throw new IllegalArgumentException("Parâmetros não suportados.");
		}
	}
	
	/**
	 * <p>Método de leitura de dados da serial
	 * @return byte[]
	 */
	public byte[] read() {
		byte[] btReceived = new byte[2000];
		int intRecLen = 0;
		int intLen = lstReadBuffer.size();
		
		for(int i = 0; i < intLen; i++) {
			byte[] btAux = lstReadBuffer.get(0);
			System.arraycopy(btAux, 0, btReceived, intRecLen, btAux.length);
			intRecLen += btAux.length;
			
			lstReadBuffer.remove(0);
		}
		
		//cria um buffer do tamanho dos dados recebidos
		byte[] btReturnArray = new byte[intRecLen];
		System.arraycopy(btReceived, 0, btReturnArray, 0, intRecLen);
		
                for(int i = 0; i < btReturnArray.length; i++) {
                    System.out.println("### = " + btReturnArray[i]);
                }
                
		return btReturnArray;
	}
	
	/**
	 * Método de envio de dados pela serial
	 * @param btSend - byte[] Array de bytes a serem enviados
	 * @throws IOException 
	 */
	public void write(byte[] btSend) throws IOException {
		this.outputStream.write(btSend);
	}
	
	/**
	 * <p>Método de transmissão e recepção de dados pela serial.
	 * Este método envia dados e aguarda o retorno de recLength bytes de dados ou estouro de recTimeout
	 * @param btSend - byte[] com dados a serem enviados
	 * @param recTimeout - Timeout em milesegundos para recepção da resposta
	 * @param recLength - Tamanho em bytes da resposta esperada
	 * @return - byte[] com dados da resposta
	 * @throws IOException 
	 */
	public byte[] txRx(byte[] btSend, int recLength, int recTimeout) throws IOException {
		write(btSend);
		
		long endTime = System.currentTimeMillis() + recTimeout;
		
		while ((getReceivedLen() < recLength) && (endTime > System.currentTimeMillis())){
			try {
				//Aguarda receber os dados
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		byte[] receiveBuffer = read();
		
		return receiveBuffer;
	}

	/**
	 * Método privado que retorna o número de bytes lidos e guardados na lista
	 * @return intBytes - número de bytes no buffer de recepção
	 */
	private int getReceivedLen() {
		int intBytes = 0;
		
		for(int i = 0; i < lstReadBuffer.size(); i++) {
			intBytes += lstReadBuffer.get(i).length;
		}
		
		return intBytes;
	}

	/**
	 * <p>Método que retorna lista de portas disponíveis no dispositivo.
	 * @return Retorna LinkedList com nome das portas 
	 * @see LinkedList
	 */
	public static LinkedList<String> getListSerial() {
		String strPortName = null;
		String strPortNameSO = null;
		LinkedList<String> lstPortNames = null;
		LinkedList<String> lstPorts = new LinkedList<String>();
		
		lstPortNames = getPortNameSO();
		
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> enumPorts = CommPortIdentifier.getPortIdentifiers();
	    
        while (enumPorts.hasMoreElements()) {
            CommPortIdentifier portId = enumPorts.nextElement();
            
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
            	
            	for(int i = 0; i < lstPortNames.size() ; i++) {
            		strPortNameSO = lstPortNames.get(i);
            		strPortName = portId.getName();
            		
            		if (strPortName.startsWith(strPortNameSO)) {
                        lstPorts.add(strPortName);
            		}
            	}
            }
        }
        return lstPorts;
	}
	
	/**
	 * <p>Método que obtém o nome das portas seriais para o sistema operacional alvo.
	 * @return LinkedList com os nomes das portas seriais de acordo com o sistema operacional em uso
	 */
	private static LinkedList<String> getPortNameSO() {
		LinkedList<String> lstNames = new LinkedList<String>();
		String osname = System.getProperty("os.name","").toLowerCase();
		
        if ( osname.startsWith("windows") ) {
        	lstNames.add("COM");
        } else if (osname.startsWith("linux")) {
        	lstNames.add("/dev/ttyS");
        	lstNames.add("/dev/ttyUSB");
        } else if ( osname.startsWith("mac") ) {
        	lstNames.add("/dev/tty.usbserial");
        } else {
            System.out.println("Sistema Operacional não suportado!");
        }
        
        return lstNames;
	}
	
	/**
	 * <p>Método que pega um PortIdentifier através do nome da parta
	 * @param String portName
	 * @return CommPortIdentifier
	 */
	private CommPortIdentifier getPortIdentifier(String portName) {
		@SuppressWarnings("unchecked")
		Enumeration<CommPortIdentifier> enumPortList = CommPortIdentifier.getPortIdentifiers();
	    CommPortIdentifier cpiReturn = null;
	    
        while (enumPortList.hasMoreElements()) {
            CommPortIdentifier cpiPortId = enumPortList.nextElement();
            String strCpiName = cpiPortId.getName();
            
            if ((cpiPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) && (strCpiName.equals(portName))) {
            	cpiReturn = cpiPortId;
            	break;
            } 
        } 
        
        return cpiReturn;
	}

	/**
	 * <p>Método que trata eventos da porta serial. Seta buffer de recepção
	 * @param SerialPortEvent event
	 * @return void
	 */
	@Override
	public void serialEvent(SerialPortEvent event) {

        switch (event.getEventType()) {
        /*case SerialPortEvent.BI:
        case SerialPortEvent.OE:
        case SerialPortEvent.FE:
        case SerialPortEvent.PE:
        case SerialPortEvent.CD:
        case SerialPortEvent.CTS:
        case SerialPortEvent.DSR:
        case SerialPortEvent.RI:
        case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
            break;*/
        case SerialPortEvent.DATA_AVAILABLE:
            
            try {
               
                try {
                	//tempo para receber mais bytes
                	Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
                
                //pega levas de dados e coloca na lista
                int intLength;
                while ((intLength = inputStream.available()) > 0) {
                	byte[] btBuffer = new byte[intLength];
                	
                    inputStream.read(btBuffer, 0, intLength);
                    
                    lstReadBuffer.add(btBuffer);
                }                                           
            } catch (IOException e) {
            	System.out.println("IOException no EventHandler");
            }
 
            break;
        }
	}
}