package com.jf.ui.model;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Stack;

import javax.swing.event.EventListenerList;

import com.jf.bean.ChessData;
import com.jf.ui.ChessPoint;
import com.jf.ui.event.ChessBoardModelEvent;
import com.jf.ui.event.ChessBoardModelListener;

/**
 *  默认的棋盘类数据模型
 *  
 *  @author 蒋鹏
 */
public class DefaultChessBoardModel implements ChessBoardModel,Serializable {
	private static final long serialVersionUID = 1L;
//
//  实例属性
//
	/** 事件侦听器列表实例，用来存储需要添加的事件 */
	private EventListenerList repository=new EventListenerList();
	/** 棋盘上已下棋子哈希列表 */
	private Hashtable<Integer, ChessData> chessDataTable=new Hashtable<>(50);
	/** 棋盘上下棋的顺序栈 */
	private Stack<ChessData> play_stack=new Stack<>();
	
//
//  构造方法
//
	/** 
	 *  无参构造方法,初始化棋盘数据模型
	 */
	public DefaultChessBoardModel() {
		
	}
	/**
	 *  
	 */
//
//  普通方法
//
	/**
	 *  向事件侦听器列表中添加事件侦听器
	 *  @param l 添加的事件侦听器
	 */
	@Override
	public void addChessBoardModelListener(ChessBoardModelListener l) {
		repository.add(ChessBoardModelListener.class, l);
	}
	/**
	 *  从事件侦听器列表中删除事件侦听器
	 *  @param l 要删除的事件侦听器
	 */
	@Override
	public void removeChessBoardModelListener(ChessBoardModelListener l) {
		repository.remove(ChessBoardModelListener.class, l);
	}
	/**
	 *  触发事件侦听器列表中的棋盘数据模型事件类型的侦听器
	 *  @param e 触发的事件实例
	 */
	@Override
	public void notifyChessBoardModelEvent(ChessBoardModelEvent e) {
		if(repository==null){
			return;
		}
		// 确保返回一个非空数组
		Object[] listeners=repository.getListenerList();
		// 以倒叙的方式触发事件
		for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ChessBoardModelListener.class) {
                ((ChessBoardModelListener)listeners[i+1]).chessBoardChanged(e);
            }
        }
	}
	/**
	 *  addChess添加棋子(该方法会触发所有添加在该模型上的棋子改变事件)
	 *  @param chessData 棋子的数据模型
	 */
	public void addChess(ChessData chessData){
		//向下棋的顺序栈中添加棋子
		play_stack.push(chessData);
		//向已下棋子hash列表中添加棋子
		chessDataTable.put(chessData.getX()*100+chessData.getY(), chessData);
		notifyChessBoardModelEvent(new ChessBoardModelEvent(this,chessData));
	}
	/**
	 *  removeChess移除棋子()
	 *  @param chessData 需要移除的棋子对象数据模型
	 */
	public void removeChess(ChessData chessData){
		//移除顺序栈顶棋子
		play_stack.pop();
		//移除已下棋子hash列表中的棋子
		chessDataTable.remove(chessData);
	}
	/**
	 *  获取当前棋局所有棋子的总数目
	 *  @return count 当前棋子总数目
	 */
	public int getAllChessNumber(){
		int count=chessDataTable.size();
		return count;
	}
	/**
	 *  获取当前棋局局势下，下一个子的颜色
	 *  @return chessColor 当前要下的棋子的颜色
	 */
	public char getNextStepChessColor(){
		char chessColor=ChessPoint.NOCHESS;
		int chessNum=getAllChessNumber();
		switch (chessNum%4) {
		case 0:
		case 3:
			chessColor=ChessPoint.BLACKCHESS;
			break;
		case 1:
		case 2:
			chessColor=ChessPoint.WHITECHESS;
			break;
		}
		return chessColor;
	}
	/**
	 *  获取当前棋局局势下，下的最后一个子的颜色
	 *  @return chessColor 最后下的棋子的颜色
	 */
	public char getLastStepChessColor(){
		char chessColor=ChessPoint.NOCHESS;
		int chessNum=getAllChessNumber();
		switch (chessNum%4) {
		case 0:
		case 1:
			chessColor=ChessPoint.BLACKCHESS;
			break;
		case 2:
		case 3:
			chessColor=ChessPoint.WHITECHESS;
			break;
		}
		return chessColor;
	}
	/**
	 *  深度复制默认棋局数据模型，因为事件侦听器牵扯对象较多且没必要复制所以忽略
	 *  
	 */
	public DefaultChessBoardModel deepClone(){
		
	}
//
//  查询、设置变量的方法
//
	public EventListenerList getRepository() {
		return repository;
	}

	public void setRepository(EventListenerList repository) {
		this.repository = repository;
	}
	
	public Hashtable<Integer, ChessData> getChessDataTable() {
		return chessDataTable;
	}
	
	public Stack<ChessData> getPlay_stack(){
		return play_stack;
	}
}
