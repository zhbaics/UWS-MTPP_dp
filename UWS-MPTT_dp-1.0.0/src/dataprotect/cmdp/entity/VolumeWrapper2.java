/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataprotect.cmdp.entity;

import dataprotect.data.Volume;
import dataprotect.ui.SanBootView;

/**
 * VolumeWrapper2.java
 *
 * Created on 2010-6-24, 11:34:41
 */
public class VolumeWrapper2 extends Volume{
    public VolumeWrapper2(){
    }
    @Override public String toString(){
        return SanBootView.res.getString("common.protected");
    }
}
