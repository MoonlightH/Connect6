package com.jf.bean;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.jf.algorithm.EvaluationFunction;
import com.jf.ui.BlackTimer;
import com.jf.ui.ChessBoard;
import com.jf.ui.ChessPoint;
import com.jf.ui.MainFrame;
import com.jf.ui.WhiteTimer;
import com.jf.ui.event.ChessBoardModelEvent;
import com.jf.ui.event.ChessBoardModelListener;
import com.jf.ui.event.RoleChangeEvent;
import com.jf.ui.event.RoleChangeListener;
import com.jf.ui.model.ChessBoardModel;
import com.jf.ui.model.DefaultChessBoardModel;

/**
 * Judge类，裁判对象类，用于在游戏过程中接管游戏
 * 
 * @author 蒋鹏
 */
public class Judge implements RoleChangeListener,ChessBoardModelListener{
	private static Judge judge;
//构造方法
	/** 构造方法，初始化属性 */
	private Judge() {
		
	}
//普通方法
	/**
	 * init方法，使得裁判对象开始监听角色转换
	 */
	public void init(){
		ChessBoardModel cbm=ChessBoard.getInstance().getModel();
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
		dcbm.addRoleChangeListener(this);
		dcbm.addChessBoardModelListener(this);
	}
	/**
	 * destory方法，移除裁判对象对角色转换的监听
	 */
	public void destory(){
		WhiteTimer wt=WhiteTimer.getInstance();
		BlackTimer bt=BlackTimer.getInstance();
		wt.stop();
		bt.stop();
		ChessBoardModel cbm=ChessBoard.getInstance().getModel();
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
		dcbm.removeRoleChangeListener(this);
		dcbm.removeChessBoardModelListener(this);
	}
	/**
	 * chessBoardChanged,向棋盘模型中添加棋子后触发的事件处理函数
	 */
	@Override
	public void chessBoardChanged(ChessBoardModelEvent e) {
		ChessBoardModel cbm=ChessBoard.getInstance().getModel();
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
		boolean result=EvaluationFunction.isGameOver(dcbm);
		//根据棋局局势判断棋局是否已经结束
		if(result){
			destory();
			MainFrame.getInstance().getGstop().doClick();
			char role=dcbm.getLastStepChessColor();
			if(role==ChessPoint.BLACKCHESS){
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JOptionPane.showMessageDialog(MainFrame.getInstance(), "黑方获胜！");
					}
				});
			}
			if(role==ChessPoint.WHITECHESS){
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JOptionPane.showMessageDialog(MainFrame.getInstance(), "白方获胜！");
					}
				});
			}
		}
	}
	/**
	 * roleChanged，角色转换后的处理方法 
	 */
	@Override
	public void roleChanged(RoleChangeEvent e) {
		new MoveCalculate().execute();
	}
	/**
	 * 获得裁判单例对象
	 * @return judge 裁判对象
	 */
	public static Judge getInstance() {
		if(judge==null){
			judge=new Judge();
		}
		return judge;
	}
}
