package hsl.imtpmd.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class ClientCommunicator implements Runnable
{
	private Thread thread;
	private Socket socket;
	private Server server;
	
	
	public ClientCommunicator( Socket socket, Server server )
	{
		this.server = server;
		this.socket = socket;

		this.thread = new Thread( this );	
		this.thread.start();
	}
	
	public void run()
	{
		readFromClient();
		writeToClient();
		
		System.out.println("exited client communication");
	}
	

	//bericht lezen
	private void readFromClient()
	{
		BufferedReader bufferedReader = null;
		
		try
		{
			InputStream inputStream = this.socket.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader( inputStream );
			
			bufferedReader = new BufferedReader( inputStreamReader );
		}
		
		catch (IOException e1)
		{
			e1.printStackTrace();
			InetAddress adress = this.socket.getInetAddress();
			server.addMessage( "Can't create inputStreamReader to talk to client " + adress );
		}
		
		if( bufferedReader != null )
		{
			try
			{
				String messageLine = bufferedReader.readLine();
				while( messageLine != null )
				{
					server.addMessage( "Client " + socket.getInetAddress() + " says: > " + messageLine );
					messageLine = bufferedReader.readLine();

					Thread.sleep( 100 );
				}
			}
			
			catch( IOException e )
			{
				e.printStackTrace();
			}
			
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	//bericht schrijven
	private void writeToClient()
	{
		OutputStreamWriter outputStreamWriter = null;
		
		try
		{
			outputStreamWriter = new OutputStreamWriter( socket.getOutputStream() );
		}
		
		catch (IOException e2)
		{
			e2.printStackTrace();
		}
		
		if( outputStreamWriter != null )
		{
			BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
			PrintWriter writer = new PrintWriter( bufferedWriter, true );
			
			writer.println( "Your message has been received on the server" );
			writer.flush();
		}
	}
}
 