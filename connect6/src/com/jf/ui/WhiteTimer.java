package com.jf.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;

public class WhiteTimer extends JLabel{
	private static final long serialVersionUID = 1L;

	private static WhiteTimer whiteTimer;
	
	private int second=0;
	
	private Timer timer;
	
	private WhiteTimer() {
		this.setText("00:00:00");
		ActionListener task=new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				second++;
				int hour = second/3600;
				int min = (second%3600)/60;
				int sec = (second%3600)%60;
				String h = hour>9?String.valueOf(hour):String.valueOf("0"+hour);
				String m = min>9?String.valueOf(min):String.valueOf("0"+min);
				String s = sec>9?String.valueOf(sec):String.valueOf("0"+sec);
				WhiteTimer.this.setText(h+":"+m+":"+s);
			}
		};
		timer=new Timer(1000, task);
	}
	
	public void start(){
		timer.start();
	}
	
	public void stop(){
		timer.stop();
		this.setText("00:00:00");
		this.second=0;
	}
	
	public static WhiteTimer getInstance(){
		if(whiteTimer==null){
			whiteTimer=new WhiteTimer();
		}
		return whiteTimer;
	}
	
	public Timer getTimer() {
		return timer;
	}
}
