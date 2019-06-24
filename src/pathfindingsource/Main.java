/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathfindingsource;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Carla
 */
public class Main implements ActionListener{
    public static JLabel costValueLabel = new JLabel("0");
    public static JRadioButton chebyshevRadioButton = new JRadioButton("Chebyshev");
    public static JRadioButton euclideanRadioButton = new JRadioButton("Euclidean", true);
    public static JRadioButton manhattanRadioButton = new JRadioButton("Manhattan");
    
    private Grid gridPanel;
    private JFrame frame;
    private JPanel topPanel = new JPanel();
    
    private int gridSizeX = 20;
    private int gridSizeY = 20;
    private int cellWidth = 40;
    
    public Main() {
        frame = new JFrame("Path Finding - 1570462");
        frame.setLayout(new BoxLayout(frame.getContentPane(),BoxLayout.Y_AXIS));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(java.awt.SystemColor.inactiveCaption);
        

        gridPanel = new Grid(gridSizeX, gridSizeY, cellWidth);
        
        JPanel lastLinePanel = new JPanel();
        lastLinePanel.setBackground(java.awt.SystemColor.inactiveCaption);
        
        JLabel labelCost = new JLabel("Total cost of the shortest path: ");
        
        labelCost.setFont(new java.awt.Font("Calibri", 1, 20));
        costValueLabel.setFont(new java.awt.Font("Calibri", 1, 20));

        JPanel panelWithRadioButtons = new JPanel(new FlowLayout(FlowLayout.CENTER,70,0));        
        panelWithRadioButtons.setPreferredSize(new Dimension(frame.getWidth(), 70));
        panelWithRadioButtons.setBorder(new EmptyBorder(20, 0, 10, 0));
        panelWithRadioButtons.setBackground(java.awt.SystemColor.inactiveCaption);
        
        ButtonGroup radioGroup=new ButtonGroup();  
        
        euclideanRadioButton.setOpaque(false);
        euclideanRadioButton.setFont(new java.awt.Font("Tahoma", 1, 24)); 
        euclideanRadioButton.addActionListener(this);
        
        chebyshevRadioButton.setOpaque(false);
        chebyshevRadioButton.setFont(new java.awt.Font("Tahoma", 1, 24)); 
        chebyshevRadioButton.addActionListener(this);
        
        manhattanRadioButton.setOpaque(false);
        manhattanRadioButton.setFont(new java.awt.Font("Tahoma", 1, 24));         
        manhattanRadioButton.addActionListener(this);
        
        radioGroup.add(euclideanRadioButton);
        radioGroup.add(chebyshevRadioButton);
        radioGroup.add(manhattanRadioButton);
        
        panelWithRadioButtons.add(euclideanRadioButton);
        panelWithRadioButtons.add(chebyshevRadioButton);
        panelWithRadioButtons.add(manhattanRadioButton);        

        lastLinePanel.add(labelCost, BorderLayout.CENTER);
        lastLinePanel.add(costValueLabel, BorderLayout.CENTER);
        
        topPanel.add(gridPanel);
        
        frame.getContentPane().add(topPanel);
        frame.getContentPane().add(panelWithRadioButtons);
        frame.getContentPane().add(lastLinePanel);
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }   
    
    public void actionPerformed(ActionEvent e){ 
        if(e.getSource() == euclideanRadioButton || e.getSource() == chebyshevRadioButton || e.getSource() == manhattanRadioButton){
            topPanel.remove(gridPanel);
            gridPanel = new Grid(gridSizeX, gridSizeY, cellWidth);
            topPanel.add(gridPanel);
            costValueLabel.setText("0");
            frame.repaint();
            frame.validate();
        }
    }
    
   public static void main(String[] args) {
      new Main();
   }  
}