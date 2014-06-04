package com.jp.bean;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import com.jp.algorithm.EvaluationFunction;
import com.jp.ui.BlackTimer;
import com.jp.ui.ChessBoard;
import com.jp.ui.ChessPoint;
import com.jp.ui.MainFrame;
import com.jp.ui.WhiteTimer;
import com.jp.ui.event.ChessBoardModelEvent;
import com.jp.ui.event.ChessBoardModelListener;
import com.jp.ui.event.RoleChangeEvent;
import com.jp.ui.event.RoleChangeListener;
import com.jp.ui.model.ChessBoardModel;
import com.jp.ui.model.DefaultChessBoardModel;

/**
 * Judge�࣬���ж����࣬��������Ϸ�����нӹ���Ϸ
 * 
 * @author ����
 */
public class Judge implements RoleChangeListener,ChessBoardModelListener{
	private static Judge judge;
//���췽��
	/** ���췽������ʼ������ */
	private Judge() {
		
	}
//��ͨ����
	/**
	 * init������ʹ�ò��ж���ʼ������ɫת��
	 */
	public void init(){
		ChessBoardModel cbm=ChessBoard.getInstance().getModel();
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
		dcbm.addRoleChangeListener(this);
		dcbm.addChessBoardModelListener(this);
	}
	/**
	 * destory�������Ƴ����ж���Խ�ɫת���ļ���
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
	 * chessBoardChanged,������ģ����������Ӻ󴥷����¼�������
	 */
	@Override
	public void chessBoardChanged(ChessBoardModelEvent e) {
		ChessBoardModel cbm=ChessBoard.getInstance().getModel();
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
		boolean result=EvaluationFunction.isGameOver(dcbm);
		//������־����ж�����Ƿ��Ѿ�����
		if(result){
			destory();
			MainFrame.getInstance().getGstop().doClick();
			char role=dcbm.getLastStepChessColor();
			if(role==ChessPoint.BLACKCHESS){
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JOptionPane.showMessageDialog(MainFrame.getInstance(), "�ڷ���ʤ��");
					}
				});
			}
			if(role==ChessPoint.WHITECHESS){
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						JOptionPane.showMessageDialog(MainFrame.getInstance(), "�׷���ʤ��");
					}
				});
			}
		}
	}
	/**
	 * roleChanged����ɫת����Ĵ����� 
	 */
	@Override
	public void roleChanged(RoleChangeEvent e) {
		new MoveCalculate().execute();
	}
	/**
	 * ��ò��е�������
	 * @return judge ���ж���
	 */
	public static Judge getInstance() {
		if(judge==null){
			judge=new Judge();
		}
		return judge;
	}
}
