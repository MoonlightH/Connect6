package com.jp.main;

import javax.swing.SwingUtilities;

import com.jp.ui.MainFrame;

public class Main {
	/**
	 * 程序入口
	 */
	public static void main(String[] args) {
//		long start1=System.currentTimeMillis();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame.getInstance().setVisible(true);
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
