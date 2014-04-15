package com.jf.bean;

/**
 *  Chess棋子的数据模型
 * 
 *  @author 蒋鹏
 */
public class ChessData{
	
	public ChessData() {
		
	}
	
	public ChessData(int x,int y,char chessColor){
		this.x=x;
		this.y=y;
		this.chessColor=chessColor;
	}
	
	private int x;
	private int y;
	private char chessColor;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public char getChessColor() {
		return chessColor;
	}

	public void setChessColor(char chessColor) {
		this.chessColor = chessColor;
	}
	
}
