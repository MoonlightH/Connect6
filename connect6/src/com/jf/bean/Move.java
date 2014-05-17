package com.jf.bean;

import java.util.ArrayList;

/**
 * Move表示某一方的一次下棋过程(两子或一子)
 * @author 蒋鹏
 */
public class Move {
//对象属性
	/** 棋子坐标数组，用来表示可下的棋子 */
	private ArrayList<Integer> coordArray=new ArrayList<>(2);
//构造方法
	/**
	 * 只下一个棋子的，下棋过程构造方法
	 * @param cpd 棋点对象
	 */
	public Move(int coord){
		coordArray.add(coord);
	}
	/**
	 * 下两个棋子的，下棋过程构造方法
	 * @param cOne 第一个棋点对象
	 * @param cTwo 第二个棋点对象
	 */
	public Move(int cOne,int cTwo){
		coordArray.add(cOne);
		coordArray.add(cTwo);
	}
//属性设置或查询方法
	public ArrayList<Integer> getCoordArray() {
		return coordArray;
	}
}
