package org.fiware.client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.fiware.client.util.NgsiRequest;

import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UserInterface extends JFrame {
	
	public JLabel lblNewLabel_1;

	/**
	 * 
	 */
	private static final long serialVersionUID = -7678391141063714799L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserInterface frame = new UserInterface();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserInterface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel_1 = new JLabel("Distance: 0 cm");
		lblNewLabel_1.setForeground(Color.BLACK);
		lblNewLabel_1.setBounds(230, 35, 191, 45);
		contentPane.add(lblNewLabel_1);
		
		JButton btnToggleLamp = new JButton("Toggle Lamp");
		btnToggleLamp.setBackground(Color.BLACK);
		btnToggleLamp.setForeground(Color.WHITE);
		btnToggleLamp.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				String json = "";
				
				if(e.getComponent().getBackground() == Color.BLACK) {
					e.getComponent().setBackground(Color.WHITE);
					e.getComponent().setForeground(Color.BLACK);
					json = 
							"{" + 
							"  \"status\": {" + 
							"    \"value\": \"on\"," + 
							"    \"type\": \"command\"" + 
							"  }" + 
							"}";
				} else {
					e.getComponent().setBackground(Color.BLACK);
					e.getComponent().setForeground(Color.WHITE);
					json = 
							"{" + 
							"  \"status\": {" + 
							"    \"value\": \"off\"," + 
							"    \"type\": \"command\"" + 
							"  }" + 
							"}";
				}

				System.out.println(new NgsiRequest("fiware", "/fiware/things").sendPost("/v2/entities/lamp1/attrs", json));
			}
		});
		btnToggleLamp.setBounds(24, 45, 153, 35);
		contentPane.add(btnToggleLamp);
	}
}
