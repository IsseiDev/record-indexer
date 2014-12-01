package client.UI;

import java.awt.EventQueue;

import client.UI.FrameController;

public class UIThread {

	public static void main(final String[] args) {

		if(args.length == 2)
		{
			final FrameController controller = new FrameController();
	
			/*
				In Swing, all user interface operations must occur on the UI thread.
				All components should be created on the UI thread.
				All method calls on UI components should happen on the UI thread.
				EventQueue.invokeLater runs the specified code on the UI thread.
				The main method for Swing programs should call EventQueue.invokeLater to create the UI.
				The main thread exits immediately after calling EventQueue.invokeLater, 
					but the UI thread keeps the program running.
			 */
			EventQueue.invokeLater(new Runnable() {		
				public void run() {
					//controller.runLogin(args[0], args[1]);
					controller.testIndexer();
				}
			});
		}
		else
		{
			System.out.println("Please include the Port and Hostname as the arguments.");
		}
	}
}
