package com.jf.main;

import java.util.Stack;

import com.jf.bean.ChessData;

public class Test {
	public static void main(String[] args) {
		Stack<ChessData> s=new Stack<>();
		ChessData c1=new ChessData();
		c1.setX(1);
		c1.setY(1);
		ChessData c2=new ChessData();
		c2.setX(2);
		c2.setY(2);
		ChessData c3=new ChessData();
		c3.setX(3);
		c3.setY(3);
		s.add(c1);
		s.add(c2);
		s.add(c3);
		@SuppressWarnings("unchecked")
		Stack<ChessData> s2=(Stack<ChessData>) s.clone();
		c3.setX(4);
		System.out.println(s2.pop().getX());
	}
}