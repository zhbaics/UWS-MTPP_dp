/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataprotect.remotemirror.entity;

import dataprotect.data.MirrorJob;

/**
 * MJWrapper.java
 *
 * Created on 2010-9-9, 13:56:45
 */
public class MJWrapper {
    public MirrorJob mj;

    public MJWrapper( MirrorJob mj ){
        this.mj = mj;
    }

    @Override public String toString(){
        return mj.getMj_job_name();
    }
}
