package br.com.whatec.ui;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.glyphfont.FontAwesome;

/**
 *
 * @author armani
 */
public class MainView {
    private Scene mainScene;
    private Pane paneCenter;
    private BorderPane bpMenu;
    private BorderPane bpAction;
    private ImageView imgvLogo;
    private Button btnNewItem;
    private Button btnSerialAction;
    private Button btnMonitor;
    
    private FontAwesome fontAwesome;

    public MainView(Stage stage) {
        inicializaComponentes();
        
        BorderPane borderPane = new BorderPane();
        mainScene = new Scene(borderPane);
        mainScene.getStylesheets().add("style.css");
        borderPane.getStyleClass().add("MainScreen");

        bpMenu = getBorderPaneMenu();
        paneCenter = createPaneCenter();
        paneCenter.setStyle("-fx-background-image: url('whatec-logo.png'); " +
                   "-fx-background-position: center center; " +
                   "-fx-background-repeat: stretch;");
        bpAction = createBpAction();
        
        borderPane.setLeft(bpMenu);
        borderPane.setCenter(paneCenter);
        borderPane.setRight(bpAction);

        stage.setTitle("Supervisório Whatec");
        stage.setWidth(1000);
        stage.setHeight(620);
        stage.setScene(mainScene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
    
    private void inicializaComponentes() {
        fontAwesome = new FontAwesome();
    }
    
    private BorderPane getBorderPaneMenu() {
        BorderPane borderPane = new BorderPane();
        borderPane.setMinWidth(150);
        borderPane.setMaxWidth(150);
        borderPane.getStyleClass().add("MenuMain");

        //borderPane.setTop(getLogo());
        borderPane.setCenter(getMenu());
        //borderPane.setBottom(getFlags());

        return borderPane;
    }
    
    /**
     *
     * @return VBox with WHATEC image logo
     */
    private VBox getLogo() {
        Image logoImg = new Image("whatec-logo.png");
        imgvLogo = new ImageView(logoImg);
        imgvLogo.setFitWidth(130);
        imgvLogo.setFitHeight(40);
        imgvLogo.setPreserveRatio(true);
        imgvLogo.setSmooth(true);
        imgvLogo.setCache(true);
       //imgvLogo.getStyleClass().add("Img");

        VBox logoVBox = new VBox();
        logoVBox.getStyleClass().add("LogoPanel");
        logoVBox.getChildren().add(imgvLogo);
        return logoVBox;
    }
    
    /**
     * 
     * @return VBox menu
     */
    private VBox getMenu() {
        VBox vbMenu = new VBox();
        
        btnSerialAction = newMenuItem("Serial", "GEARS");
        btnNewItem = newMenuItem("Novo", "GEARS");
        btnMonitor = newMenuItem("Monitor", "GEARS");
        
        vbMenu.getChildren().addAll(btnSerialAction, btnNewItem, btnMonitor);
        return vbMenu;
    }
    
    private Pane createPaneCenter() {
        return new Pane();
    }
    
    private BorderPane createBpAction() {
        BorderPane bpAct = new BorderPane();
        bpAct.setMinWidth(50);
        bpAct.setMaxWidth(50);
        bpAct.getStyleClass().add("MenuMain");
        
        //bpAct.setTop(getUserLogo());
        //bpAct.setBottom(getDownloadButton());
        
        return bpAct;
    }
    
    private Button newMenuItem(String item, String strImage) {
        Button btn = new Button(item, fontAwesome.create(strImage));
        btn.setMinSize(150, 40);
        btn.getStyleClass().add("flatButton");
        btn.setId(item);
        btn.setAlignment(Pos.CENTER_LEFT);
        return btn;
    }

    public Scene getMainScene() {
        return mainScene;
    }

    public void setMainScene(Scene mainScene) {
        this.mainScene = mainScene;
    }

    public Pane getPaneCenter() {
        return paneCenter;
    }

    public void setPaneCenter(Pane paneCenter) {
        this.paneCenter = paneCenter;
    }

    public BorderPane getBpMenu() {
        return bpMenu;
    }

    public void setBpMenu(BorderPane bpMenu) {
        this.bpMenu = bpMenu;
    }

    public BorderPane getBpAction() {
        return bpAction;
    }

    public void setBpAction(BorderPane bpAction) {
        this.bpAction = bpAction;
    }

    public ImageView getImgvLogo() {
        return imgvLogo;
    }

    public void setImgvLogo(ImageView imgvLogo) {
        this.imgvLogo = imgvLogo;
    }

    public Button getBtnNewItem() {
        return btnNewItem;
    }

    public void setBtnNewItem(Button btnNewItem) {
        this.btnNewItem = btnNewItem;
    }

    public Button getBtnSerialAction() {
        return btnSerialAction;
    }

    public void setBtnSerialAction(Button btnSerialAction) {
        this.btnSerialAction = btnSerialAction;
    }

    public Button getBtnMonitor() {
        return btnMonitor;
    }

    public void setBtnMonitor(Button btnMonitor) {
        this.btnMonitor = btnMonitor;
    }

    public FontAwesome getFontAwesome() {
        return fontAwesome;
    }

    public void setFontAwesome(FontAwesome fontAwesome) {
        this.fontAwesome = fontAwesome;
    }
}
