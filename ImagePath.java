/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sce;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.util.*;
import javax.imageio.ImageIO;
import sce.Point;
/**
 *
 * @author Parker
 */
public class ImagePath {
    
    public BufferedImage addPathToImage(ArrayList<Point> path, BufferedImage img){
        BufferedImage imgNew = img;
        Color white = new Color(255, 255, 255);
        int rgb = white.getRGB();
        Point pathPT;
        Point neighPT[];
        
        for(int i = 0; i < path.size(); i++){
            pathPT = path.get(i);
            imgNew.setRGB(pathPT.getX(), pathPT.getY(), rgb);
            
            neighPT = pathPT.getNeighbors();
            for(int j = 0; j < neighPT.length; j++){
                imgNew.setRGB(neighPT[j].getX(), neighPT[j].getY(), rgb);
            }
        }
    return imgNew; 
    }
}
