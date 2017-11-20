/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sce;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

/**
 *
 * @author Parker
 */
public class OpeningPane extends JFrame{
    
    ImageIcon icon = new ImageIcon("Icon.jpg");
    JTextArea welcome = new JTextArea("         LandNav Mobile");
    Font title = new Font("Serif", Font.BOLD, 52);
    JTextArea instruct = new JTextArea("                      Click Anywhere To Begin");
    Font subTitle = new Font("Serif", Font.BOLD, 26);
    
    public OpeningPane(){
        
        getContentPane().setBackground(Color.WHITE);
        setResizable(false);
        setLayout(new BorderLayout());
        welcome.setFont(title);
        welcome.setEditable(false);
        add(welcome, BorderLayout.NORTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(icon.getImage());
        setTitle("LandNav Mobile");
        add(new JLabel(icon), BorderLayout.CENTER);
        instruct.setFont(subTitle);
        instruct.setEditable(false);
        add(instruct, BorderLayout.SOUTH);
        pack();
    }
    
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OpeningPane().setVisible(true);
            }
        });
    }
}
