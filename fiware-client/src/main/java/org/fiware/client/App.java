package org.fiware.client;

import org.fiware.client.util.NioSocketServer;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	UserInterface frame = new UserInterface();
		frame.setVisible(true);
		NioSocketServer nio = new NioSocketServer(frame);
		
    }
}
