package notification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import mqs.AdvisorProcess.IncomingReader;

public class NotificationProcess  extends javax.swing.JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String username, address = "localhost";
	int port = 2222;
	Boolean isConnected = false;
	Socket socket;
	BufferedReader reader;
	PrintWriter writer;
	
	public NotificationProcess(){
		initComponents();
		
	}
private void initComponents() {

		
		lblUsername = new javax.swing.JLabel();
		tfUsername = new javax.swing.JTextField();
		tfUsername.setText("UTA");
		tfUsername.setEditable(false);
		btnConnect = new javax.swing.JButton();
		btnDisconnect = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		taChat = new javax.swing.JTextArea();
		tfChat = new javax.swing.JTextField();
		tfChat.setEnabled(false);
		btnSend = new javax.swing.JButton();
		btnSend.setEnabled(false);
		
		

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Notification Process");
		setName("Notification Process");
		setResizable(false);

		lblUsername.setText("Notification Process:");
		

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

		btnSend.setText("");
		btnSend.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				//b_sendActionPerformed(evt);
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
		username = "notification";
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
	}
		// Call the listen thread for incoming message from the server
		ListenThread();

	}
//Call the listenThread method to invoke the incoming reader thread
public void ListenThread() {
	Thread IncomingReader = new Thread(new IncomingReader());
	IncomingReader.start();
}
//The incoming reader thread, listed for messages from the MQS
public class IncomingReader implements Runnable {

	@Override
	public void run() {
		String stream;
		String data[];
		pollServer();
	    try {
	    	//Send poll request only if messages are present from the advisor in the message queue
			while((stream = reader.readLine())!=null){
				data = stream.split(":");
				if(data[0].equals("advisor")){
					taChat.append(data[0]+" "+data[2]+" Course "+data[1]+" for "+data[3]+"\n");
				}
				else{
					//IF no messages sleep for 7 seconds
					taChat.append("No messages to be notified yet. Sleeping for 7 Seconds...\n");
					try {
						Thread.sleep(7000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	pollServer();
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
//Disconnect from the server
private void b_disconnectActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_b_disconnectActionPerformed
	sendDisconnect();
	Disconnect();
}
public void sendDisconnect() {
	String bye = ("notification"+":"+"offline");
	try {
		writer.println(bye);
		writer.flush();
	} catch (Exception e) {
		taChat.append("Could not send Disconnect message.\n");
	}
}
public void Disconnect() {
	//Close the socket to disconnect
	try{
		socket.close();
	}
	catch(Exception e){
		e.printStackTrace();
		taChat.append("Failed to disconnect. \n");
	}
	isConnected = false;
	//tfUsername.setEditable(true);
}
private void pollServer(){
	//Poll the server for messages form the message queue
	try{
		writer.println("notification"+":"+"poll");
		taChat.append("polling server...\n");
		writer.flush();
	}
	catch(Exception e){
		taChat.append("Failed to poll server\n");
	}
	
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
			new NotificationProcess().setVisible(true);
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
