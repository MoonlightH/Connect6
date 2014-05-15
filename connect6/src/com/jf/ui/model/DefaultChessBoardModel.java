package com.jf.ui.model;

import java.util.Stack;

import javax.swing.event.EventListenerList;

import com.jf.bean.Move;

import static com.jf.ui.ChessPoint.NOCHESS;
import static com.jf.ui.ChessPoint.BLACKCHESS;
import static com.jf.ui.ChessPoint.WHITECHESS;

import com.jf.ui.event.ChessBoardModelEvent;
import com.jf.ui.event.ChessBoardModelListener;
import com.jf.ui.event.RoleChangeEvent;
import com.jf.ui.event.RoleChangeListener;

/**
 * Ĭ�ϵ�����������ģ��
 * 
 * @author ����
 */
public class DefaultChessBoardModel implements ChessBoardModel {
//
// ʵ������
//
	/** �¼��������б�ʵ���������洢��Ҫ��ӵ��¼� */
	private EventListenerList repository = new EventListenerList();
	/** ���̾������� */
	private char[][] compositionData = new char[19][19];
	/** �����������˳��ջ */
	private Stack<Integer> play_stack = new Stack<>();
	/** ����ս����Χ�ķ�Χ */
	private int xMin = 20, xMax = 0, yMin = 20, yMax = 0;
	/** ��ǰ�����ɫ */
	private char currentRole=BLACKCHESS;

//
// ���췽��
//
	/**
	 * �޲ι��췽��,��ʼ����������ģ��
	 */
	public DefaultChessBoardModel() {
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				compositionData[i][j] = NOCHESS;
			}
		}
	}

//
// ��ͨ����
//
	/**
	 * ���¼��������б��������������ģ���¼�������
	 * 
	 * @param l ��ӵ���������ģ���¼�������
	 */
	@Override
	public void addChessBoardModelListener(ChessBoardModelListener l) {
		repository.add(ChessBoardModelListener.class, l);
	}

	/**
	 * ���¼��������б����Ƴ���������ģ���¼�������
	 * 
	 * @param l Ҫɾ������������ģ���¼�������
	 */
	@Override
	public void removeChessBoardModelListener(ChessBoardModelListener l) {
		repository.remove(ChessBoardModelListener.class, l);
	}
	
	/**
	 * ���¼��������б�����ӽ�ɫ����¼�������
	 * 
	 * @param l ��ӵĽ�ɫ����¼�������
	 */
	@Override
	public void addRoleChangeListener(RoleChangeListener l) {
		repository.add(RoleChangeListener.class, l);
	}
	
	/**
	 * ���¼��������б����Ƴ���������ģ���¼�������
	 * 
	 * @param l �Ƴ���ɫ����¼�������
	 */
	@Override
	public void removeRoleChangeListener(RoleChangeListener l) {
		repository.remove(RoleChangeListener.class, l);
	};
	/**
	 * �����¼��������б��е���������ģ���¼����͵�������
	 * 
	 * @param e �������¼�ʵ��
	 */
	@Override
	public void notifyChessBoardModelEvent(ChessBoardModelEvent e) {
		if (repository == null) {
			return;
		}
		// ȷ������һ���ǿ�����
		Object[] listeners = repository.getListenerList();
		// �Ե���ķ�ʽ�����¼�
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ChessBoardModelListener.class) {
				((ChessBoardModelListener) listeners[i + 1])
						.chessBoardChanged(e);
			}
		}
	}
	
	/**
	 * �����¼��������б��еĽ�ɫ������͵�������
	 * 
	 * @param e �������¼�ʵ��
	 */
	@Override
	public void notifyRoleChangeEvent(RoleChangeEvent e){
		if (repository == null) {
			return;
		}
		// ȷ������һ���ǿ�����
		Object[] listeners = repository.getListenerList();
		// �Ե���ķ�ʽ�����¼�
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == RoleChangeListener.class) {
				((RoleChangeListener) listeners[i + 1]).roleChanged(e);
			}
		}
	}
	/**
	 * addChess�������(�÷����ᴥ����������ڸ�ģ���ϵ����Ӹı��¼�)
	 * 
	 * @param coords �������ĸ���ֵ��coords=x*100+y��
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
		// ����־����������������
		compositionData[y - 1][x - 1] = getNextStepChessColor();
		// �������˳��ջ���������
		play_stack.push(coord);
		notifyChessBoardModelEvent(new ChessBoardModelEvent(this, coord));
		char role=getNextStepChessColor();
		if(currentRole!=role){
			notifyRoleChangeEvent(new RoleChangeEvent(this));
		}
		currentRole=role;
	}

	/**
	 * ��ȡ��ǰ����������ӵ�����Ŀ
	 * 
	 * @return count ��ǰ��������Ŀ
	 */
	public int getAllChessNumber() {
		int count = play_stack.size();
		return count;
	}

	/**
	 * ��ȡ��ǰ��־����£���һ���ӵ���ɫ
	 * 
	 * @return chessColor ��ǰҪ�µ����ӵ���ɫ
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
	 * ��ȡ��ǰ��־����£��µ����һ���ӵ���ɫ
	 * 
	 * @return chessColor ����µ����ӵ���ɫ
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
	 * ������������ȡ������ɫ
	 * 
	 * @param x,y ���ĺ�������
	 * @return color ���ӵ���ɫ
	 */
	public char getChessColorByCoord(int x,int y){
		char color=compositionData[y-1][x-1];
		return color;
	}
	/**
	 * ִ����һ�غ��߷�
	 * 
	 * @param move һ�غ��߷�
	 */
	public void makeNextMove(Move move) {
		for (int coord : move.getCoordArray()) {
			makeChess(coord);
		}
	}

	/**
	 * �������غ��߷���������
	 */
	public void unMakeMove() {
		for (int i = 0; i < 2; i++) {
			unMakeChess();
		}
	}

	/**
	 * ��һ������
	 * 
	 * @param coord
	 *            �������
	 */
	public void makeChess(int coord) {
		int x=coord/100;
		int y=coord%100;
		// ����־����������������
		compositionData[y-1][x-1] = getNextStepChessColor();
		// �������˳��ջ���������
		play_stack.push(coord);
	}

	/**
	 * �Ƴ�һ������
	 */
	public void unMakeChess() {
		// ������˳��ջ�е�����ӵ�����
		int coord = play_stack.pop();
		int x=coord/100;
		int y=coord%100;
		// ����������hash�б����Ƴ���ӵ�����
		compositionData[y-1][x-1] = NOCHESS;
	}
//
// �������û��ѯ������
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
