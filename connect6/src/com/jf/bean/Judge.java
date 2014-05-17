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
