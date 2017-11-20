/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sce;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author Parker
 */
public class DisplayWindow extends JFrame {
    
    BufferedImage image = null;
    ScrollPane scroll = new ScrollPane();
    ImageIcon icon = new ImageIcon("Icon.jpeg");
        
    public DisplayWindow(){
        
   }
    
   @Override
   public Dimension getPreferredSize(){
       return new Dimension(100, 80);
   }
    
    public DisplayWindow(BufferedImage img){
        setLayout(new BorderLayout());
        image = img;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(600,600);
        setIconImage(icon.getImage());
        setTitle("LandNav Mobile");
        
        ImageIcon im = new ImageIcon(image);
        scroll.add(new JLabel(im));
        add(scroll, BorderLayout.CENTER);
        setVisible(false);
        
        
    }

    void setImage(BufferedImage map) {
       image = map;
       repaint();
    }


public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DisplayWindow().setVisible(true);
            }
        });
    }
}