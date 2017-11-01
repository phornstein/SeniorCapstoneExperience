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
        //creating testing variables
        ArrayList<Point> answer = new ArrayList<Point>();
        Point point1 = new Point(1234,1234);
        Point point2 = new Point(1250,1218);
        
        //find least cost path
        CostPath lCP = new CostPath();
        answer = lCP.leastCostPath(point1, point2, img);
        System.out.println("\n..........Printing Path........\n");
        for(int i = 0; i < answer.size(); i++){
            System.out.println(answer.get(i).getX() + ", " + answer.get(i).getY());
        }
        
        
        //add path to image
       // BufferedImage img2 = img;
        BufferedImage imgNew;
        ImagePath pathNew = new ImagePath();
        imgNew = pathNew.addPathToImage(answer, img);
        
                BufferedImage img2 = null;
        File f2 = null;
        try{
           f2 = new File("ChestertownWeightedSum_Remap.jpg");        
           img2 = ImageIO.read(f2);
        }
        catch(IOException e){
            System.out.println(e);
        }
        
        Color white = new Color(255, 5, 5);
         int rgb = white.getRGB();
         Point pathPT;
         Point neighPT[];
         
         for(int i = 0; i < answer.size(); i++){
             pathPT = answer.get(i);
             img2.setRGB(pathPT.getX(), pathPT.getY(), rgb);
             
            // neighPT = pathPT.getNeighbors();
            // for(int j = 0; j < neighPT.length; j++){
             //    img2.setRGB(neighPT[j].getX(), neighPT[j].getY(), rgb);
            // }
         }
        //display original image in JFrame
        JFrame original = new JFrame("Original Image");
        original.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        original.setLocationRelativeTo(null);
        original.setSize(600,600);
        ImageIcon org = new ImageIcon(img2);
        original.add(new JLabel(org));
        original.setVisible(true);
    
        }
}