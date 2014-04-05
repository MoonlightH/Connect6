package com.jf.main;

import javax.swing.SwingUtilities;

import com.jf.ui.MainFrame;

public class Main {
	/**
	 * ³ÌÐòÈë¿Ú
	 */
	public static void main(String[] args) {
//		long start1=System.currentTimeMillis();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainFrame().setVisible(true);
			}
		});
//		long end1=System.currentTimeMillis();
//		System.out.println(end1-start1);
		
//		long start2=System.currentTimeMillis();
//		new MainFrame().setVisible(true);
//		long end2=System.currentTimeMillis();
//		System.out.println(end2-start2);
	}
}
