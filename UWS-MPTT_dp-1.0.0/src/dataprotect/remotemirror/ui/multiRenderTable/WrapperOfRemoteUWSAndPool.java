/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataprotect.remotemirror.ui.multiRenderTable;

import dataprotect.data.PoolWrapper;
import dataprotect.data.UWSrvNode;
import java.util.ArrayList;

/**
 * WrapperOfRemoteUWSAndPool.java
 *
 * Created on 2010-8-30, 10:45:25
 */
public class WrapperOfRemoteUWSAndPool {
    public UWSrvNode uwsNode;
    public PoolWrapper pool;
    public ArrayList<PoolWrapper> poolList = new ArrayList<PoolWrapper>();

    public WrapperOfRemoteUWSAndPool(
        UWSrvNode uwsNode,
        PoolWrapper pool
    ){
        this.uwsNode = uwsNode;
        this.pool = pool;
    }

    public void addPoolItem( PoolWrapper one ){
        this.poolList.add( one );
    }
}
