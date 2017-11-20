package sce;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import javax.imageio.ImageIO;

import java.io.*;
import java.util.*;

import sce.CostPath;
import sce.Point;
import sce.ImagePath;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
        
/**
 *
 * @author Parker
 */

class SCE {
    
        public static void main(String[] args) {
        //read in processing image 
        BufferedImage img = null;
        File f = null;
        try{
           f = new File("ChestertownWeightedSum_1.png");        
           img = ImageIO.read(f);
        }
        catch(IOException e){
            System.out.println(e); 
       }
            
        
        BufferedImage img2 = null;
        File f2 = null;
        try{
           f2 = new File("Chestertown_FullColor_NAIP.png");        
           img2 = ImageIO.read(f2);
        }
        catch(IOException e){
            System.out.println(e);
        }
        
       
        PathWindow panel = new PathWindow(img2);
        panel.setSize(600, 600);
        panel.setVisible(false);
        panel.setDataImage(img);
        panel.setDisplayImage(img2);
         
        OpeningPane start = new OpeningPane();
        start.setSize(600, 600);
        start.setVisible(true);
        start.addMouseListener(new MouseListener(){
            @Override
            public void mouseClicked(MouseEvent e) {
                start.setVisible(false);
                panel.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
                
            }
            
        });
        
        
        }
        
}