package br.com.whatec.controller;

import br.com.whatec.ui.MainView;
import br.com.whatec.ui.NewItemView;

/**
 *
 * @author armani
 */
public class NewItemController extends PersistenceController {
    private MainView mainView;
    private NewItemView view;
    
    public NewItemController(AbstractController parent, MainView mainView) {
        super(parent);
        this.mainView = mainView;
        this.view = new NewItemView();
    }
    
    public void showView() {
        this.mainView.getPaneCenter().getChildren().add(this.view);
    }
    
}
