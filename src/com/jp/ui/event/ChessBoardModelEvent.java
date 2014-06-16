package com.jp.ui.event;

import java.util.EventObject;
import java.util.HashSet;

import com.jp.ui.model.ChessBoardModel;

/**
 * ChessBoardModelEvent棋盘数据模型的事件
 * @author 蒋鹏
 */
public class ChessBoardModelEvent extends EventObject{

	private static final long serialVersionUID = 1L;
	/** 只更新单个棋点 */
	public final static int UPDATECHESSPOINT=0;
	/** 更新整个棋盘数据模型 */
	public final static int UPDATECHESSMODEL=1;
	/** 需要更新的单个棋点坐标复合值 */
	private Integer coord;
	/** 需要更新的棋点坐标复合值集合 */
	private HashSet<Integer> coordSet;
	//跟新棋盘的类型
	private int updateWay=-1;
//
//  构造方法
//
	/**
	 * 构造方法的参数含有棋子数据模型
	 * @param source 事件源对象
	 */
	public ChessBoardModelEvent(ChessBoardModel source) {
		super(source);
	}
	/**
	 * 构造方法的参数含有棋子数据模型
	 * @param source 事件源对象
	 * @param coord 棋点坐标
	 */
	public ChessBoardModelEvent(ChessBoardModel source,Integer coord) {
		super(source);
		this.coord=coord;
		updateWay=UPDATECHESSPOINT;
	}
	/**
	 * 构造方法参数包含改变的棋子数据模型数组
	 * @param source 事件源对象
	 * @param coordSet 棋点坐标集合
	 */
	public ChessBoardModelEvent(ChessBoardModel source,HashSet<Integer> coordSet) {
		super(source);
		this.coordSet=coordSet;
		updateWay=UPDATECHESSMODEL;
	}
//
//  查询方法
//
	public Integer getCoord() {
		return coord;
	}
	
	public HashSet<Integer> getCoordSet(){
		return coordSet;
	}
	
	public int getUpdateWay(){
		return updateWay;
	}
}
