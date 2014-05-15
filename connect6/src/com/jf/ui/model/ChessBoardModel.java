package com.jf.ui.model;

import com.jf.ui.event.ChessBoardModelEvent;
import com.jf.ui.event.ChessBoardModelListener;
import com.jf.ui.event.RoleChangeEvent;
import com.jf.ui.event.RoleChangeListener;

/**
 * ChessBoardModel 棋盘数据模型接口
 * @author 蒋鹏
 */
public interface ChessBoardModel {
	/**
	 * 为棋盘数据模型添加事件侦听
	 * @param l 棋盘模型侦听器
	 */
	public void addChessBoardModelListener(ChessBoardModelListener l);
	/**
	 * 移除棋盘数据模型的事件侦听
	 * @param l 棋盘模型侦听器
	 */
	public void removeChessBoardModelListener(ChessBoardModelListener l);
	/**
	 * 为角色变更添加事件侦听
	 * @param l 棋盘模型侦听器
	 */
	public void addRoleChangeListener(RoleChangeListener l);
	/**
	 * 移角色变更的事件侦听
	 * @param l 棋盘模型侦听器
	 */
	public void removeRoleChangeListener(RoleChangeListener l);
	/**
	 * 触发棋盘模型事件
	 * @param e 棋盘事件对象
	 */
	public void notifyChessBoardModelEvent(ChessBoardModelEvent e);
	/**
	 * 触发角色变更事件
	 * @param e 角色变更事件对象
	 */
	public void notifyRoleChangeEvent(RoleChangeEvent e);
}
