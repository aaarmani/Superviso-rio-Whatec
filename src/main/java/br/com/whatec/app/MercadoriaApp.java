package br.com.whatec.app;

import br.com.whatec.controller.MainController;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Ponto de entrada da aplicacão.
 * 
 * @author YaW Tecnologia
 */
public class MercadoriaApp extends Application {
    //private ResourceBundle rb;
    private MainController controller;
    
    @Override
    public void start(Stage stage){
        //Locale.setDefault(new Locale("pt","BR"));
        //controller = new ListaMercadoriaController(stage);
        try {
            Font.loadFont(MercadoriaApp.class.getResource("/fonts/fontawesome-webfont.ttf").toExternalForm(), 12);
            //Locale.setDefault(Locale.ENGLISH);
            //rb = ResourceBundle.getBundle("languages.messages");
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        controller = new MainController(stage);
    }

    @Override
    public void stop() throws Exception {
        controller.cleanUp();
    }
    
    public static void main(String[] args) {
        MercadoriaApp.launch(args);
    }
    
}
