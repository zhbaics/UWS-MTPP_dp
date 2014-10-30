package dataprotect.ui;

import javax.swing.*;
import mylib.UI.BasicAction;

public class GeneralActionForMainUi extends BasicAction{
    protected SanBootView view = null;
  
    public GeneralActionForMainUi(Icon menuIcon,Icon btnIcon,String text,int funcID) {
        super(menuIcon,btnIcon,text,funcID);
    }
  
    public void setView(SanBootView _view){
        view = _view; 
    }
}
