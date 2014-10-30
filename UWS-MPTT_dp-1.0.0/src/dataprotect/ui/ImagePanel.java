package dataprotect.ui;

import java.awt.*;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

public class ImagePanel extends Panel{
  static int m_imageNum = 1;
  Image m_image;

  public ImagePanel(ImageIcon a ){
    m_image = null;
    m_image = a.getImage();
    MediaTracker tracker = new MediaTracker(this);
    tracker.addImage(m_image, ImagePanel.m_imageNum++);
        try {
            tracker.waitForAll();
        } catch (InterruptedException ex) {
            Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
  }

  public ImagePanel(URL url){
    m_image = null;
    m_image = java.awt.Toolkit.getDefaultToolkit().getImage(url);
    MediaTracker tracker = new MediaTracker(this);
    tracker.addImage(m_image, ImagePanel.m_imageNum++);
        try {
            tracker.waitForAll();
        } catch (InterruptedException ex) {
            Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
  }

  public void changeit(ImageIcon a ){
    m_image = a.getImage();
    MediaTracker tracker = new MediaTracker(this);
    tracker.addImage(m_image, ImagePanel.m_imageNum++);
        try {
            tracker.waitForAll();
        } catch (InterruptedException ex) {
            Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
  }

//  private void makeit(URL url){
//    m_image = java.awt.Toolkit.getDefaultToolkit().getImage(url);
//    MediaTracker tracker = new MediaTracker(this);
//    tracker.addImage(m_image, ImagePanel.m_imageNum++);
//        try {
//            tracker.waitForAll();
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ImagePanel.class.getName()).log(Level.SEVERE, null, ex);
//        }
//  }

  @Override public void paint(java.awt.Graphics g){
    g.drawImage(m_image, 0, 0, this);
  }

  @Override public Dimension getMinimumSize(){
    return new Dimension(m_image.getWidth(this), m_image.getHeight(this));
  }

  @Override public Dimension getPreferredSize(){
    return getMinimumSize();
  }
}
