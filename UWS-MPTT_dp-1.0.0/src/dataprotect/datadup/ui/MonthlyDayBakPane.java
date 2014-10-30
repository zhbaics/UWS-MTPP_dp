package dataprotect.datadup.ui;

import java.awt.*;
import javax.swing.*;
import java.util.Vector;
import dataprotect.datadup.data.*;

public class MonthlyDayBakPane extends JPanel {
    Strategy monthDayStrategy; 
    GridBagLayout gridBagLayout1 = new GridBagLayout();
    JCheckBox jCheckBoxList[] = new JCheckBox[31];

    public MonthlyDayBakPane( Strategy _monthDayStrategy ) { 
        try {
            jbInit();
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }

    public MonthlyDayBakPane(){
        try{
            jbInit();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    void jbInit() {
        this.setLayout(gridBagLayout1);
        for( int i=0;i<31;i++ ){
          jCheckBoxList[i] = new JCheckBox();
          jCheckBoxList[i].setText(""+(i+1));
          int comboxY = (i/8)*2+1;
          this.add(jCheckBoxList[i],new GridBagConstraints((i%8),comboxY,1,1,0.0,0.0
                ,GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), -66, 0));
          jCheckBoxList[i].setMinimumSize(new Dimension(110,18));
          jCheckBoxList[i].setPreferredSize(new Dimension(110,18));
        }
    }

    public void initMonthPane(Vector list){
        for( int i=0;i<31;i++){
          if( list.contains( new Integer( i+1 ) ) ){
            jCheckBoxList[i].setSelected( true );
          }
        }
    }
    
    public void enableOwn(boolean val){
        for( int i=0; i<31; i++ ){
            jCheckBoxList[i].setEnabled( val );
        }
    }

    public String getInfo(){
        StringBuffer info = new StringBuffer("");
        boolean flag = true;

        for( int i=0; i<31; i++ ){
            if( jCheckBoxList[i].isSelected() ){
                if( !flag )
                    info.append(",").append(i+1);       //info+=","+(i+1);
                else{
                    info.append(i+1).append("");        //info+=(i+1)+"";
                    flag = false;
                }
            }
        }
        return info.toString();
    }
}
