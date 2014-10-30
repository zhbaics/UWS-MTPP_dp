/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dataprotect.ui;

import dataprotect.data.RetValObj;

/**
 *
 * @author zjj
 */
public interface RestoreOriginalDiskInterface {
    public void reGetSysPart();
    public boolean reGetVolInfo();
    public RetValObj sizeIsMatched(String src,String dest);
}
