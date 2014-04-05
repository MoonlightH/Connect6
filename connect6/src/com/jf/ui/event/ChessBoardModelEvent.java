package com.jf.ui.event;

import java.util.EventObject;
import java.util.Vector;

import com.jf.ui.model.ChessBoardModel;

/**
 *  ChessBoardModelEvent棋盘数据模型的事件
 *  
 *  @author 蒋鹏
 */
public class ChessBoardModelEvent extends EventObject{

	private static final long serialVersionUID = 1L;
	/** UPDATECHESSPOINT表明是只改变单个棋点的事件 */
	public static final int UPDATECHESSPOINT=0;
	/** UPDATEMODEL表明是变更棋盘数据模型的事件 */
	public static final int UPDATEMODEL=1;
//	
//  实例变量
//
	private int type=-1;
	private Vector<Integer> location=new Vector<>(2);
	private Vector<Vector<Integer>> locations=new Vector<>(20,3);
//
//  查询方法
//
	public Vector<Integer> getLocation() {
		return location;
	}
	
	public Vector<Vector<Integer>> getLocations() {
		return locations;
	}
	
	public int getType() {
		return type;
	}
//
//  构造方法
//
	public ChessBoardModelEvent(ChessBoardModel source) {
		super(source);
	}
	/**
	 *  构造方法的参数含有棋点坐标，表明事件类型为改变单个棋点棋子类型
	 *  @param source 事件源对象
	 *  @param x 需要跟新棋点在数组的一维下标
	 *  @param y 需要跟新棋点在数组的二维下标
	 */
	public ChessBoardModelEvent(ChessBoardModel source,int x,int y) {
		super(source);
		location.add(x);
		location.add(y);
		type=UPDATECHESSPOINT;
	}
	/**
	 *  构造方法的参数含有棋点坐标集合，表明事件类型为改变多个棋点棋子类型
	 *  @param source 事件源对象
	 *  @param v 需要跟新棋点坐标的集合
	 */
	public ChessBoardModelEvent(ChessBoardModel source,Vector<Vector<Integer>> v) {
		super(source);
		locations=v;
		type=UPDATEMODEL;
	}
}
