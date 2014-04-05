package com.jf.ui.model;

import com.jf.ui.event.ChessBoardModelEvent;
import com.jf.ui.event.ChessBoardModelListener;

/**
 *  ChessBoardModel 棋盘数据模型接口
 *  @author 蒋鹏
 */
public interface ChessBoardModel {
	/**
	 *  为棋盘数据模型添加事件侦听
	 *  @param l 棋盘模型侦听器
	 */
	public void addChessBoardModelListener(ChessBoardModelListener l);
	/**
	 *  移除棋盘数据模型的事件侦听
	 *  @param l 棋盘模型侦听器
	 */
	public void removeChessBoardModelListener(ChessBoardModelListener l);
	/**
	 *  触发棋盘模型事件
	 *  @param e 棋盘事件对象
	 */
	public void notifyChessBoardModelEvent(ChessBoardModelEvent e);
}
