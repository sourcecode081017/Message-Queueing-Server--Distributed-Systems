/* Anirudh Sivaramakrishnan
 * Student ID: 1001529484 
 */
/*CITATION:https://www.youtube.com/watch?v=hZgntu7889Q (Youtube video)
 * https://stackoverflow.com/questions/1383797/java-hashmap-how-to-get-key-from-value
 * JAVA - The Complete Reference -Herbert Schildt (9th edition)
 * Oracle Docs- Queue
 * Oracle Docs - Random

 */
package mqs;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;




//Message Queue Server
public class MQS extends javax.swing.JFrame {

	/**
	 * 
	 */
	//Declare a Global Queue object implemented as a Linked List
	Queue<String> messageQueue = new LinkedList<String>();
	//Declare Client output streams
	HashMap<PrintWriter,String> clientOutputStreams;
	//Declare user list
	ArrayList<String> users = new ArrayList<String>();
	private static final long serialVersionUID = 1L;
	//The listener thread which listens for incoming connections.
	Thread listener;
	public MQS(){
		initComponents();
		
	}
	//The GUI Stuff goes here
	private void initComponents() {
        jScrollPane1 = new javax.swing.JScrollPane();
		textChat = new javax.swing.JTextArea();
		btnStart = new javax.swing.JButton();
		btnEnd = new javax.swing.JButton();
		btnUsers = new javax.swing.JButton();
		btnClear = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Message Queue Sever");
		setName("MQS");
		setResizable(false);

		textChat.setColumns(20);
		textChat.setRows(5);
		jScrollPane1.setViewportView(textChat);

		btnStart.setText("START");
		btnStart.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_startActionPerformed(evt);
			}

			});

		btnEnd.setText("END");
		btnEnd.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_endActionPerformed(evt);
			}
		});

		btnUsers.setText("Online Users");
		btnUsers.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_usersActionPerformed(evt);
			}
		});

		btnClear.setText("Clear");
		btnClear.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				b_clearActionPerformed(evt);
			}
		});

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout
						.createSequentialGroup().addContainerGap().addGroup(layout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(
										jScrollPane1)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(btnEnd, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, 75,
														Short.MAX_VALUE))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 291,
												Short.MAX_VALUE)
										.addGroup(layout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
												.addComponent(btnClear, javax.swing.GroupLayout.DEFAULT_SIZE,
														javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addComponent(btnUsers, javax.swing.GroupLayout.DEFAULT_SIZE, 103,
														Short.MAX_VALUE))))
						.addContainerGap())
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addGap(209, 209, 209)));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								layout.createSequentialGroup().addContainerGap()
										.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 340,
												Short.MAX_VALUE)
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(btnStart).addComponent(btnUsers))
										.addGap(18, 18, 18)
										.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(btnClear).addComponent(btnEnd))
										.addGap(4, 4, 4)));

		pack();
	
		
	}
	//On clicking the start button of the server
	private void b_startActionPerformed(ActionEvent evt) {
		//Instantiate a new thread and star the server
		Thread starter = new Thread(new StartServer());
		starter.start();
		// Server started message is printed out
		textChat.append("Message Queue Server started...\n");
		
	}
	@SuppressWarnings("deprecation")
	//Method to shut down the server
	private void b_endActionPerformed(java.awt.event.ActionEvent evt){
		//Remove the connection streams
		textChat.append("Shutting down MQS....\n");
		users.clear();
		clientOutputStreams.clear();
		//Close the socket so that it can't listen to anymore incoming connections
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Suspend the listener thread
		listener.suspend();
		//Thread.currentThread().suspend();
		textChat.append("MQS Shut down!\n");
	}
	//Clear the GUI text
	private void b_clearActionPerformed(java.awt.event.ActionEvent evt) {
		textChat.setText("");
	}
	//Display the online users in real time
	private void b_usersActionPerformed(java.awt.event.ActionEvent evt) {
		textChat.append("\n Online users : \n");
		// Iterate through the list of clients and append to the server GUI
		for (String current_user : users) {
			textChat.append(current_user);
			textChat.append("\n");
		}

	}
	//Create the serverSocket object
	ServerSocket serverSocket;
	//This is called when server thread is started
	public class StartServer implements Runnable {

		@Override
		public void run() {
			clientOutputStreams = new HashMap<PrintWriter, String>();
			try {
				// listen at port 2222
				 serverSocket = new ServerSocket(2222);
				while(true){
					//Accept connection at this socket, spawn a thread for each connection
					//and continue listening
					Socket clientSocket = serverSocket.accept();
					PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
					clientOutputStreams.put(writer,"");
					listener = new Thread(new ClientHandler(clientSocket, writer));
				
					listener.start();
				}
			
		}
			catch(Exception e){
				e.printStackTrace();
			//textChat.append("Error making a connection. \n");
			}
		
	}
}
	//The client handler class, to handle incoming thread conncetions
	public class ClientHandler implements Runnable {
		BufferedReader reader;
		Socket socket;
		PrintWriter client;
		//The constructor for the client socket and the printwriter user
		public ClientHandler(Socket clientSocket, PrintWriter user){
			client = user;
			try {
				socket = clientSocket;
				// Instantiating the InputStream reader
				InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
				reader = new BufferedReader(isReader);
			} catch (Exception ex) {
				textChat.append("Unexpected error... \n");
			}
			
		}
        //The run method
		@Override
		public void run() {
			String message;
			
			String queueMessage;
			try {
				while((message = reader.readLine())!=null){
					//Declare the messageArr and split the incoming message by : using regex
					String messageArr[] = message.split(":");
					//Get the fields from the regex
					String dat = messageArr[1];
					String name = messageArr[0];
					//if the connection is online
					if(dat.equals("online")){
						//add to client output stream and user array list
						clientOutputStreams.put(client,name);
						users.add(name);
						textChat.append(name+" is "+dat+"\n");
					}
					//if offline remove the clients from the array
					else if(dat.equals("offline")){
						textChat.append(name+" is "+dat+"\n");
						clientOutputStreams.remove(client,name);
						users.remove(name);
					}
					//IF the advisor is polling,
					else if(dat.equals("poll")&&name.equals("advisor")){
						Iterator it = clientOutputStreams.entrySet().iterator();
						PrintWriter writer = null;
						//Find the advisors printwriter object
						while (it.hasNext()) {
							try {
								Map.Entry pair = (Map.Entry) it.next();
								String s = (String) pair.getValue();
								if (s.equals("advisor")) {
									//System.out.println("Got from advisor");
									writer = (PrintWriter) pair.getKey();
									}
								}
							catch(Exception e){
								
							}
						}
						//IF there are any messages for the advisor in the message queue
						label:
							//First peek the queue for any messages
						while((queueMessage= messageQueue.peek())!=null){
							//System.out.println("Good, queue is not empty");
							//System.out.println(queueMessage);
							String queueMessageArr[] = queueMessage.split(":");
							//IF that message is a course request from a student poll that request and write it to advisor's print write object
							if(queueMessageArr[0].equals("student")){
								messageQueue.poll();
							writer.println(queueMessageArr[0]+":"+queueMessageArr[1]+":"+queueMessageArr[2]);
							writer.flush();
							}
							else
								break label;
							
						}
						//System.out.println("Message queue is empty : poll");
						//Else send none to the advisor's print write object
						writer.println("none"+":"+""+":"+"");
						writer.flush();
					}
					//IF it is a poll request from the notification process
					else if(dat.equals("poll")&&name.equals("notification")){
						//System.out.println("From notification");
						Iterator it = clientOutputStreams.entrySet().iterator();
						PrintWriter writer = null;
						//Iterate the client output streams map for notifications' print writer object
						while (it.hasNext()) {
							try {
								Map.Entry pair = (Map.Entry) it.next();
								String s = (String) pair.getValue();
								if (s.equals("notification")) {
									//System.out.println("Got from notification");
									writer = (PrintWriter) pair.getKey();
									}
								}
							catch(Exception e){
								
							}
						}
						//First peek the queue for any messages from the advisor
						first:
						while((queueMessage= messageQueue.peek())!=null){
							
							String queueMessageArr[] = queueMessage.split(":");
							if(queueMessageArr[0].equals("advisor")){
							//IF advisor messages are present poll the queue and write it to the output stream object	
								messageQueue.poll();
							writer.println(queueMessageArr[0]+":"+queueMessageArr[1]+":"+queueMessageArr[2]+":"+queueMessageArr[3]);
							writer.flush();
							}
							else
								break first;
						}
						//Else print no message form the advisor yet.
						System.out.println("No messages from advisor yet");
						writer.println("none"+":"+""+":"+"");
						writer.flush();
					}
					//IF it is request from student push it to the queue
					else if(messageArr[messageArr.length-1].equals("push")&&name.equals("student")){
				  
				 messageQueue.add(name+":"+messageArr[1]+":"+" ");
				 textChat.append("COURSE "+dat+" ADDED TO MQS"+"\n");
					}
					//IF it is from the advisor push it to the queue
					else if(messageArr[messageArr.length-1].equals("push")&&name.equals("advisor")){
						messageQueue.add(name+":"+messageArr[1]+":"+messageArr[2]+":"+"student");
						textChat.append(messageArr[2]+" MESSAGE FOR "+messageArr[1]+" ADDED TO MQS BY ADVISOR \n");
						
					}
				}
			} catch (IOException e) {
		
				e.printStackTrace();
			}
			
		}
	
		
		
	}
	public class Message{
		String course;
		boolean approval;
		public Message(String course, boolean approval){
			this.course = course;
			this.approval = approval;
		}
	}
	public static void main(String args[]) {
		/*
		 * Invoke later doesn't let the server GUI hang while it continuously
		 * listens for client connections
		 */
		java.awt.EventQueue.invokeLater(new Runnable() {
			@Override
			// Invoke the run method
			public void run() {
				new MQS().setVisible(true);
			}
		});
	}
	private javax.swing.JButton btnClear;
	private javax.swing.JButton btnEnd;
	private javax.swing.JButton btnStart;
	private javax.swing.JButton btnUsers;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JTextArea textChat;

}
