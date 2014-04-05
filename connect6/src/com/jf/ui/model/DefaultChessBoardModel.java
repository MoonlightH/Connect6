package com.jf.ui.model;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import com.jf.bean.ChessData;
import com.jf.bean.ChessInfo;
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
	/** 棋盘信息数组 */
	private ChessInfo chessInfo=new ChessInfo(); 
//
//  构造方法
//
	/** 
	 *  无参构造方法,初始化棋盘数据模型
	 */
	public DefaultChessBoardModel() {
		
	}
	/**
	 *  用棋子数据信息，来初始化默认棋盘数据模型
	 *  @param dataModel 棋子的数据信息
	 */
	public DefaultChessBoardModel(ChessInfo chessInfo){
		this.chessInfo=chessInfo.deepClone();
	}
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
	 *  setChess方法设置棋盘数据模型中棋子(该方法会触发所有添加在该模型上的棋子改变事件)
	 *  @param x 棋子在棋盘上的x坐标
	 *  @param y 棋子在棋盘上的y坐标
	 */
	public void setChess(int x,int y){
		chessInfo.chessInfo.get(x).set(y, getNextStepChessColor());
		notifyChessBoardModelEvent(new ChessBoardModelEvent(this,x,y));
	}
	/**
	 *  获取当前棋局所有棋子的总数目
	 *  @return count 当前棋子总数目
	 */
	public int getAllChessNumber(){
		int count=0;
		for (Vector<Character> v : chessInfo.chessInfo) {
			for(Character c:v){
				if(c.charValue()!=ChessPoint.NOCHESS){
					count++;
				}
			}
		}
		return count;
	}
	/**
	 *  获取当前棋局下所有空棋点的坐标
	 *  @return nullPoints 所有的空棋点
	 */
	public Vector<ChessData> getAllNullChessPoints(){
		Vector<ChessData> nullPoints=new Vector<>();
		for(int i = 0; i < 19; i++){
			for (int j = 0; j < 19; j++) {
				if(chessInfo.chessInfo.get(i).get(j)==ChessPoint.NOCHESS){
					ChessData chess=new ChessData(i,j);
					nullPoints.add(chess);
				}
			}
		}
		return nullPoints;
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
	 *  deepClone方法实现深度复制（通用方法是用序列化、反序列化实现对象深度复制,但代价比较大）
	 */
	public DefaultChessBoardModel deepClone(){
		DefaultChessBoardModel defaultChessBoardModel=new DefaultChessBoardModel(this.chessInfo);
		return defaultChessBoardModel;
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

	public ChessInfo getChessInfo() {
		return chessInfo;
	}

	public void setDataModel(ChessInfo chessInfo) {
		this.chessInfo = chessInfo;
	}
}
