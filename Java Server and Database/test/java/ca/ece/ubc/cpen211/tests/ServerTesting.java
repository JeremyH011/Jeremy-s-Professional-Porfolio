package ca.ece.ubc.cpen211.tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import ca.ece.ubc.cpen221.mp5.YelpDBClient;
import ca.ece.ubc.cpen221.mp5.YelpDBClient2;
import ca.ece.ubc.cpen221.mp5.YelpDBServer;

public class ServerTesting {

	@Test
	public void test0() {
		String[] a = new String [1];
		a[0] = "4949";
		
		ServerRunnable sr = new ServerRunnable();
	    Thread serverThread = new Thread(sr);
	    serverThread.start();
	    
	    //SecondClientRunnable ss = new SecondClientRunnable();
	    //Thread client = new Thread(ss);
	    
	    try {
			TimeUnit.SECONDS.sleep(3);
			//client.start();
			YelpDBClient.main(a);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
	}
	
	class SecondClientRunnable implements Runnable{
		public SecondClientRunnable() {
		
		}
		public void run() {
			String[] a = new String [1];
    		a[0] = "4949";
            YelpDBClient2.main(a);
		}
	}
	class ServerRunnable implements Runnable {
       
        public ServerRunnable(){
        }
        
        public void run(){
        	String[] a = new String [1];
    		a[0] = "4949";
            YelpDBServer.main(a);
        }
    }
}
