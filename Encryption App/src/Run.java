

import java.io.PrintWriter;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class Run extends JFrame implements ActionListener {
    
    private JFrame frame = new JFrame("Encryption App");
    private JPanel contentPane = new JPanel();
    private JLabel label = new JLabel("File Path");
    private JButton button = new JButton("Choose a File");
    private JButton enButton = new JButton("Shift Up");
    private JButton deButton = new JButton("Shift Down");
    private JTextField keyIn = new JTextField("Enter Key Here");
    private JLabel status = new JLabel("");
    private JFileChooser fc = new JFileChooser();
    private File f = null;
    private Encrypt en;
   
    
    boolean isPNG = false;
    boolean isKeyValid = false;
    
	public Run() {
   	 
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    	contentPane.setLayout(null);

   	 
    	label.setSize(480, 30);
    	label.setLocation(10, 60);
    	label.setHorizontalAlignment(SwingConstants.CENTER);
    	contentPane.add(label);
  	 
    	button.setSize(120, 30);
    	button.setLocation(190, 30);
    	button.setHorizontalAlignment(SwingConstants.CENTER);
    	contentPane.add(button);
    	button.addActionListener(this);
   	 
   	 
   	 
    	enButton.setSize(120, 30);
    	enButton.setLocation(100, 150);
    	enButton.setHorizontalAlignment(SwingConstants.CENTER);
    	contentPane.add(enButton);
    	enButton.addActionListener(this);
   	 
    	deButton.setSize(120, 30);
    	deButton.setLocation(280, 150);
    	deButton.setHorizontalAlignment(SwingConstants.CENTER);
    	contentPane.add(deButton);
    	deButton.addActionListener(this);
   	 
    	keyIn.setSize(200, 30);
    	keyIn.setLocation(150, 100);
    	keyIn.setHorizontalAlignment(SwingConstants.CENTER);
    	contentPane.add(keyIn);
   	 
    	
    	status.setSize(480, 30);
    	status.setLocation(10, 175);
    	status.setHorizontalAlignment(SwingConstants.CENTER);
    	contentPane.add(status);
    	
   	 	
    	
    	
    	frame.setContentPane(contentPane);
    	frame.setSize(500, 250);
    	frame.setResizable(false);
    	frame.setLocationByPlatform(true);
    	frame.setVisible(true);
    	frame.setResizable(false);
   	 
   	 
	}

	public void actionPerformed(ActionEvent e) {
   	 
   	 if(e.getSource() == button) {
   		 int returnValue = fc.showOpenDialog(button);
   		 if(returnValue == JFileChooser.APPROVE_OPTION) {
   			 
   			 f = fc.getSelectedFile();
   			 String path = f.getAbsolutePath();
   			 if(path.substring(path.lastIndexOf(".") + 1).equals("png")) {    
   				 isPNG = true;
   				 en = new Encrypt(f);
   				 label.setText(f.getAbsolutePath());
   			 }
   			 else {
   				 isPNG = false;
   				 label.setText("FILE MUST HAVE EXTENTION .png");
   			 }
   		 }
   	 }
   	 
   	 else if(e.getSource() == enButton) {
   		 if(isPNG == false) {
   			 status.setText("You must choose a .png file");
   		 }
   		 else {
   			 en.key(keyIn.getText());
   			 en.encrypt(f);
   			 status.setText("");
   		 }
   	 }
   	 else if(e.getSource() == deButton) {
   		 if(isPNG == false) {
   			 status.setText("You must choose a .png file");
   		 }
   		 
   		 else {
   			 en.key(keyIn.getText());
   			 en.decrypt(f);
   			 status.setText("");
   		 }
   	 }
	}
    
    
	
    
    
    
    
	public static void main(String[] args) {
   	 new Run();
  	 
	}
}

