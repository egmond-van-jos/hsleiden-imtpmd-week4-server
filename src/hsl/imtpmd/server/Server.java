package hsl.imtpmd.server;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Server extends JFrame implements Runnable, WindowListener
{
	private JTextArea messageArea;
	private Thread thread;
	
	private ServerSocket serverSocket;
	
	
	public Server()
	{
		this.messageArea = new JTextArea();
		this.add( this.messageArea );
		
		this.setDefaultCloseOperation( EXIT_ON_CLOSE );
		this.setBounds( 50, 50, 600, 500 );
		this.setVisible( true );
		
		//start de server loop
		this.thread = new Thread( this );
		this.thread.start();
	}
	
	
	public void run()
	{
		serverSocket = null;
		
		try
		{
			serverSocket = new ServerSocket( 4444 );
			this.addMessage( "Server is gereed op poort " + serverSocket.getLocalPort() );
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		//keep receiving new incomming requests from clients
		while( serverSocket != null )
		{
			Socket socket = null;
			
			try
			{
				socket = serverSocket.accept();
				new ClientCommunicator( socket, this ); //object that will run its own thread to reply to each client
			}
			
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	

	public void addMessage( String message )
	{
		this.messageArea.setText( message + "\n" + this.messageArea.getText() );
	}
	
	
	public static void main( String args[] )
	{
		new Server();
	}
	
	


	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	//bij het sluiten van de applicatie moet de thread stoppen!
	@Override
	public void windowClosed(WindowEvent e)
	{
		serverSocket = null;
	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
