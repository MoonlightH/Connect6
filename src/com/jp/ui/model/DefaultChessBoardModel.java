package com.jp.ui.model;

import java.util.Stack;

import javax.swing.event.EventListenerList;

import static com.jp.ui.ChessPoint.BLACKCHESS;
import static com.jp.ui.ChessPoint.NOCHESS;
import static com.jp.ui.ChessPoint.WHITECHESS;

import com.jp.bean.Move;
import com.jp.ui.event.ChessBoardModelEvent;
import com.jp.ui.event.ChessBoardModelListener;
import com.jp.ui.event.RoleChangeEvent;
import com.jp.ui.event.RoleChangeListener;

/**
 * 默认的棋盘类数据模型
 * 
 * @author 蒋鹏
 */
public class DefaultChessBoardModel implements ChessBoardModel {
//
// 实例属性
//
	/** 事件侦听器列表实例，用来存储需要添加的事件 */
	private EventListenerList repository = new EventListenerList();
	/** 棋盘局势数组 */
	private char[][] compositionData = new char[19][19];
	/** 棋盘上下棋的顺序栈 */
	private Stack<Integer> play_stack = new Stack<>();
	/** 脱离战场范围的范围 */
	private int xMin = 20, xMax = 0, yMin = 20, yMax = 0;
	/** 当前下棋角色 */
	private char currentRole=BLACKCHESS;

//
// 构造方法
//
	/**
	 * 无参构造方法,初始化棋盘数据模型
	 */
	public DefaultChessBoardModel() {
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				compositionData[i][j] = NOCHESS;
			}
		}
	}

//
// 普通方法
//
	/**
	 * 向事件侦听器列表中添加棋盘数据模型事件侦听器
	 * 
	 * @param l 添加的棋盘数据模型事件侦听器
	 */
	@Override
	public void addChessBoardModelListener(ChessBoardModelListener l) {
		repository.add(ChessBoardModelListener.class, l);
	}

	/**
	 * 从事件侦听器列表中移除棋盘数据模型事件侦听器
	 * 
	 * @param l 要删除的棋盘数据模型事件侦听器
	 */
	@Override
	public void removeChessBoardModelListener(ChessBoardModelListener l) {
		repository.remove(ChessBoardModelListener.class, l);
	}
	
	/**
	 * 从事件侦听器列表中添加角色变更事件侦听器
	 * 
	 * @param l 添加的角色变更事件侦听器
	 */
	@Override
	public void addRoleChangeListener(RoleChangeListener l) {
		repository.add(RoleChangeListener.class, l);
	}
	
	/**
	 * 从事件侦听器列表中移除棋盘数据模型事件侦听器
	 * 
	 * @param l 移除角色变更事件侦听器
	 */
	@Override
	public void removeRoleChangeListener(RoleChangeListener l) {
		repository.remove(RoleChangeListener.class, l);
	};
	/**
	 * 触发事件侦听器列表中的棋盘数据模型事件类型的侦听器
	 * 
	 * @param e 触发的事件实例
	 */
	@Override
	public void notifyChessBoardModelEvent(ChessBoardModelEvent e) {
		if (repository == null) {
			return;
		}
		// 确保返回一个非空数组
		Object[] listeners = repository.getListenerList();
		// 以倒叙的方式触发事件
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChessBoardModelListener.class) {
				((ChessBoardModelListener) listeners[i + 1])
						.chessBoardChanged(e);
			}
		}
	}
	
	/**
	 * 触发事件侦听器列表中的角色变更类型的侦听器
	 * 
	 * @param e 触发的事件实例
	 */
	@Override
	public void notifyRoleChangeEvent(RoleChangeEvent e){
		if (repository == null) {
			return;
		}
		// 确保返回一个非空数组
		Object[] listeners = repository.getListenerList();
		// 以倒叙的方式触发事件
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == RoleChangeListener.class) {
				((RoleChangeListener) listeners[i + 1]).roleChanged(e);
			}
		}
	}
	/**
	 * addChess添加棋子(该方法会触发所有添加在该模型上的棋子改变事件)
	 * 
	 * @param coords 棋点坐标的复合值（coords=x*100+y）
	 */
	public void addChess(int coord) {
		int x = coord/100;
		int y = coord%100;
		if (x < xMin) {
			xMin = x;
		}
		if (x > xMax) {
			xMax = x;
		}
		if (y < yMin) {
			yMin = y;
		}
		if (y > yMax) {
			yMax = y;
		}
		// 向棋局局势数组中添加棋子
		compositionData[y - 1][x - 1] = getNextStepChessColor();
		// 向下棋的顺序栈中添加棋子
		play_stack.push(coord);
		notifyChessBoardModelEvent(new ChessBoardModelEvent(this, coord));
		char role=getNextStepChessColor();
		if(currentRole!=role){
			notifyRoleChangeEvent(new RoleChangeEvent(this));
		}
		currentRole=role;
	}

	/**
	 * 获取当前棋局所有棋子的总数目
	 * 
	 * @return count 当前棋子总数目
	 */
	public int getAllChessNumber() {
		int count = play_stack.size();
		return count;
	}

	/**
	 * 获取当前棋局局势下，下一个子的颜色
	 * 
	 * @return chessColor 当前要下的棋子的颜色
	 */
	public char getNextStepChessColor() {
		char chessColor = NOCHESS;
		int chessNum = getAllChessNumber();
		switch (chessNum % 4) {
		case 0:
		case 3:
			chessColor = BLACKCHESS;
			break;
		case 1:
		case 2:
			chessColor = WHITECHESS;
			break;
		}
		return chessColor;
	}

	/**
	 * 获取当前棋局局势下，下的最后一个子的颜色
	 * 
	 * @return chessColor 最后下的棋子的颜色
	 */
	public char getLastStepChessColor() {
		char chessColor = NOCHESS;
		int chessNum = getAllChessNumber();
		switch (chessNum % 4) {
		case 0:
		case 1:
			chessColor = BLACKCHESS;
			break;
		case 2:
		case 3:
			chessColor = WHITECHESS;
			break;
		}
		return chessColor;
	}

	/**
	 * 根据棋点坐标获取棋子颜色
	 * 
	 * @param x,y 棋点的横纵坐标
	 * @return color 棋子的颜色
	 */
	public char getChessColorByCoord(int x,int y){
		char color=compositionData[y-1][x-1];
		return color;
	}
	/**
	 * 执行下一回合走法
	 * 
	 * @param move 一回合走法
	 */
	public void makeNextMove(Move move) {
		for (int coord : move.getCoordArray()) {
			makeChess(coord);
		}
	}

	/**
	 * 撤消本回合走法所下棋子
	 */
	public void unMakeMove() {
		for (int i = 0; i < 2; i++) {
			unMakeChess();
		}
	}

	/**
	 * 下一个棋子
	 * 
	 * @param coord
	 *            棋点坐标
	 */
	public void makeChess(int coord) {
		int x=coord/100;
		int y=coord%100;
		// 向棋局局势数组中添加棋子
		compositionData[y-1][x-1] = getNextStepChessColor();
		// 向下棋的顺序栈中添加棋子
		play_stack.push(coord);
	}

	/**
	 * 移除一个棋子
	 */
	public void unMakeChess() {
		// 从下棋顺序栈中弹出添加的棋子
		int coord = play_stack.pop();
		int x=coord/100;
		int y=coord%100;
		// 从已下棋子hash列表中移除添加的棋子
		compositionData[y-1][x-1] = NOCHESS;
	}
//
// 属性设置或查询到方法
//
	public EventListenerList getRepository() {
		return repository;
	}

	public char[][] getCompositionData() {
		return compositionData;
	}

	public Stack<Integer> getPlay_stack() {
		return play_stack;
	}

	public int getxMin() {
		return xMin;
	}

	public int getxMax() {
		return xMax;
	}

	public int getyMin() {
		return yMin;
	}

	public int getyMax() {
		return yMax;
	}

	public char getCurrentRole() {
		return currentRole;
	}
}
