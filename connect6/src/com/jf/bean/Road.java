package com.jf.bean;

import java.util.Vector;

import com.jf.ui.ChessPoint;

/**
 *  Way类，通过定义“路”来解决六子棋棋形判断的问题
 *  
 *  @author 蒋鹏
 */

public class Road {
//
//  实例属性
//
	/** 当前路有效的情况下棋子的颜色，如果无效则棋子颜色为无棋子状态 */
	private char chessColor=ChessPoint.NOCHESS;
	/** 当前有效路中棋子的个数 */
	private int validChessCount=0;
	/** 当前棋路中棋子的信息 */
	private Vector<Character> chesses=new Vector<>(6);
//
//  构造方法
//
	public Road(Vector<Character> chesses) {
		this.chesses=chesses;
		int bChessNum=0;
		int wChessNum=0;
		for(char c:chesses){
			if(c==ChessPoint.BLACKCHESS){
				bChessNum++;
			}
			if(c==ChessPoint.WHITECHESS){
				wChessNum++;
			}
		}
		if(bChessNum>0 && wChessNum==0){
			this.chessColor=ChessPoint.BLACKCHESS;
			this.validChessCount=bChessNum;
		}
		if(wChessNum>0 && bChessNum==0){
			this.chessColor=ChessPoint.WHITECHESS;
			this.validChessCount=wChessNum;
		}
	}
//
//  查询或设置实例属性的方法
//
	/** 
	 *  getChessColor获取当前路的颜色
	 *  @return chessColor ChessPoint.BLACKCHESS或'b'表明当前棋路为黑子棋路、
	 *  	ChessPoint.WHITECHESS或'w'表明当前棋路为白子棋路、ChessPoint.NOCHESS
	 *  	或'n'表明当前棋路无效
	 */
	public char getChessColor() {
		return chessColor;
	}
	/**
	 *  getValidChessCount获取当前有效路的有效棋子个数
	 *  @return validChessCount 有效路中有效棋子的个数
	 */
	public int getValidChessCount() {
		return validChessCount;
	}
	/**
	 *  getChess获取当前路中棋子的信息
	 *  @return chesses 当前路中的棋子信息
	 */
	public Vector<Character> getChesses() {
		return chesses;
	}
	
}
