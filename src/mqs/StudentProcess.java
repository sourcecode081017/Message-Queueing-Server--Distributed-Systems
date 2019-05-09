package mqs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class StudentProcess extends javax.swing.JFrame  {
	
	/**
	 * 
	 */
	//The student process
	private static final long serialVersionUID = 1L;
	String username, address = "localhost";
	int port = 2222;
	Boolean isConnected = false;
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	//Initialize the UI Components
	public StudentProcess(){
		initComponents();
	}
	private void initComponents(){
		
		lblUsername = new javax.swing.JLabel();
		tfUsername = new javax.swing.JTextField();
		btnConnect = new javax.swing.JButton();
		btnDisconnect = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		taChat = new javax.swing.JTextArea();
		tfChat = new javax.swing.JTextField();
		btnSend = new javax.swing.JButton();
		
		

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Student Process");
		setName("Student Process");
		setResizable(false);

		lblUsername.setText("Student Name :");
		

		tfUsername.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//tf_usernameActionPerformed(evt);
			}
		});
		/*tfSendTo.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//tf_usernameActionPerformed(evt);
			}
		});*/

		btnConnect.setText("Connect");
		btnConnect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_connectActionPerformed(evt);
			}
		});

		btnDisconnect.setText("Kill");
		btnDisconnect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_disconnectActionPerformed(evt);
			}
		});

		taChat.setColumns(20);
		taChat.setRows(5);
		jScrollPane1.setViewportView(taChat);
         //Button to push course to the MQS
		btnSend.setText("Push to MQS");
		btnSend.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_sendActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(layout
				.createSequentialGroup().addContainerGap()
				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(layout.createSequentialGroup()
								.addComponent(tfChat, javax.swing.GroupLayout.PREFERRED_SIZE, 352,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
						.addComponent(jScrollPane1)
						.addGroup(layout.createSequentialGroup()
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
										.addComponent(lblUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 62,
												Short.MAX_VALUE)

								).addGap(18, 18, 18).addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)

										.addComponent(tfUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 62,
												Short.MAX_VALUE))
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)

										/*.addComponent(lblSendTo, javax.swing.GroupLayout.DEFAULT_SIZE, 62,
												Short.MAX_VALUE)*/)
								.addGap(18, 18, 18)
								.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)

										/*.addComponent(tfSendTo, javax.swing.GroupLayout.DEFAULT_SIZE, 62,
												Short.MAX_VALUE)*/)

								.addGroup(
										layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(layout.createSequentialGroup().addComponent(btnConnect)
														.addGap(2, 2, 2).addComponent(btnDisconnect)
														.addGap(0, 0, Short.MAX_VALUE)))))
				.addContainerGap())
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)

						.addGap(201, 201, 201)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()

						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)

								.addComponent(tfUsername).addComponent(lblUsername)/*.addComponent(lblSendTo)
								.addComponent(tfSendTo)*/.addComponent(btnConnect).addComponent(btnDisconnect))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 310,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(tfChat)
								.addComponent(btnSend, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)));

		pack();
		
	}
	private void b_connectActionPerformed(java.awt.event.ActionEvent evt) {
		// Check if the client is already connected to the server
		if (isConnected == false) {
			username = "student";
			tfUsername.setEditable(false);

			try {
				// Create a new socket at the local host , port 2222
				socket = new Socket(address, port);
				// Declare the input and output writers / readers of the socket
				InputStreamReader streamreader = new InputStreamReader(socket.getInputStream());
				reader = new BufferedReader(streamreader);
				writer = new PrintWriter(socket.getOutputStream());
				// Write to the socket the corresponding username which was
				// connected
				writer.println(username+":"+"online");
				writer.flush();
				isConnected = true;
			} catch (Exception ex) {
				taChat.append("Cannot Connect! Try Again. \n");
				tfUsername.setEditable(true);
			}
			// Call the listen thread for incoming message from the server
			//ListenThread();

		}
		// If the User is already connected
		else if (isConnected == true) {
			taChat.append("You are already connected. \n");
		}
	}
	private void b_sendActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_b_sendActionPerformed
		String nothing = "";
		// If the text is empty do not send
		if ((tfChat.getText()).equals(nothing)) {
			tfChat.setText("");
			tfChat.requestFocus();
		}
		// Else write the message to the client socket telling the username,
		// message and chat
		else {
			try {   
				   String course = tfChat.getText();
				   writer.println(username+":"+course+":"+"push");
				  
					writer.flush(); // flushes the buffer
				
			} catch (Exception ex) {
				taChat.append("Message was not sent. \n");
			}
			tfChat.setText("");
			tfChat.requestFocus();
		}

		tfChat.setText("");
		tfChat.requestFocus();
	}
	private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_b_disconnectActionPerformed
		sendDisconnect();
		Disconnect();
	}
	public void sendDisconnect() {
		String bye = (username+":"+"offline");
		try {
			writer.println(bye);
			writer.flush();
		} catch (Exception e) {
			taChat.append("Could not send Disconnect message.\n");
		}
	}
	public void Disconnect() {
		try{
			socket.close();
		}
		catch(Exception e){
			e.printStackTrace();
			taChat.append("Failed to disconnect. \n");
		}
		isConnected = false;
		tfUsername.setEditable(true);
	}
	
	public static void main(String args[]) {
		/*
		 * Calling the invoke later because new forking a new thread the current
		 * or (main) thread will wait till the new thread finishes, hence the UI
		 * will be blocked,
		 */
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			// The run method, which will call the ClientEngine Constructor
			public void run() {
				new StudentProcess().setVisible(true);
			}
		});
	}
	private javax.swing.JButton btnConnect;
	private javax.swing.JButton btnDisconnect;
	private javax.swing.JButton btnSend;
	private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblUsername;
	private javax.swing.JTextArea taChat;
	private javax.swing.JTextField tfChat;
	private javax.swing.JTextField tfUsername;

}
