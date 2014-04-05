package com.jf.bean;

/**
 *  ChessData棋点的数据类型
 * 
 *  @author 蒋鹏
 */
public class ChessData {
	
	public ChessData() {
		
	}
	
	public ChessData(int x,int y){
		this.x=x;
		this.y=y;
	}
	
	private int x;
	private int y;
	private int evaluateScore = -1;

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

	public int getEvaluateScore() {
		return evaluateScore;
	}

	public void setEvaluateScore(int evaluateScore) {
		this.evaluateScore = evaluateScore;
	}

}
