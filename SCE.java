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
           f = new File("ChestertownWeightedSum_Remap_Discrete.jpg");        
           img = ImageIO.read(f);
        }
        catch(IOException e){
            System.out.println(e);
        }
        //creating testing variables
        ArrayList<Point> answer = new ArrayList<Point>();
        Point point1 = new Point(img.getWidth()/2,img.getHeight()/2);
        Point point2 = new Point(img.getWidth()/2+100,img.getHeight()/2+100);
        //find least cost path
        CostPath lCP = new CostPath();
        answer = lCP.leastCostPath(point1, point2, img);
        Point pnt = null;
        //add path to image
        BufferedImage img2 = img;
        ImagePath pathNew = new ImagePath();
        pathNew.addPathToImage(answer, img2);
        
        //display original image in JFrame
        JFrame original = new JFrame("Original Image");
        original.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        original.setLocationRelativeTo(null);
        original.setSize(600,600);
        ImageIcon org = new ImageIcon(img);
        original.add(new JLabel(org));
        original.setVisible(true);
        //display new image in JFrame
        JFrame newImg = new JFrame("New Image");
        newImg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newImg.setLocationRelativeTo(null);
        newImg.setSize(600,600);
        ImageIcon imgNew = new ImageIcon(img2);
        newImg.add(new JLabel(imgNew));
        newImg.setVisible(true);
    }
}