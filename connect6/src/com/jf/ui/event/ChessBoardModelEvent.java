package com.jf.ui.event;

import java.util.EventObject;
import java.util.Vector;

import com.jf.bean.ChessData;
import com.jf.ui.model.ChessBoardModel;

/**
 *  ChessBoardModelEvent棋盘数据模型的事件
 *  
 *  @author 蒋鹏
 */
public class ChessBoardModelEvent extends EventObject{

	private static final long serialVersionUID = 1L;
	/** 只跟新单个棋点 */
	public final static int UPDATECHESSPOINT=0;
	/** 跟新整个棋盘数据模型 */
	public final static int UPDATECHESSMODEL=1;
	
	private ChessData chessData;
	private Vector<ChessData> chessDataArray;
	//跟新棋盘的类型
	private int updateWay=-1;
//
//  查询方法
//
	public ChessData getChessData() {
		return chessData;
	}
	
	public Vector<ChessData> getChessDataArray(){
		return chessDataArray;
	}
	
	public int getUpdateWay(){
		return updateWay;
	}
//
//  构造方法
//
	/**
	 *  构造方法的参数含有棋子数据模型
	 *  @param source 事件源对象
	 *  @param chessData 棋子对象数据模型
	 */
	public ChessBoardModelEvent(ChessBoardModel source,ChessData chessData) {
		super(source);
		this.chessData=chessData;
		updateWay=UPDATECHESSPOINT;
	}
	/**
	 *  构造方法参数包含改变的棋子数据模型数组
	 *  
	 */
	public ChessBoardModelEvent(ChessBoardModel source,Vector<ChessData> chessDataArray) {
		super(source);
		this.chessDataArray=chessDataArray;
		updateWay=UPDATECHESSMODEL;
	}
}
