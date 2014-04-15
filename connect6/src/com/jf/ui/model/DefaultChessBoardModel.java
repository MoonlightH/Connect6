package com.jf.ui.model;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Stack;

import javax.swing.event.EventListenerList;

import com.jf.bean.ChessData;
import com.jf.ui.ChessPoint;
import com.jf.ui.event.ChessBoardModelEvent;
import com.jf.ui.event.ChessBoardModelListener;

/**
 *  Ĭ�ϵ�����������ģ��
 *  
 *  @author ����
 */
public class DefaultChessBoardModel implements ChessBoardModel,Serializable {
	private static final long serialVersionUID = 1L;
//
//  ʵ������
//
	/** �¼��������б�ʵ���������洢��Ҫ��ӵ��¼� */
	private EventListenerList repository=new EventListenerList();
	/** �������������ӹ�ϣ�б� */
	private Hashtable<Integer, ChessData> chessDataTable=new Hashtable<>(50);
	/** �����������˳��ջ */
	private Stack<ChessData> play_stack=new Stack<>();
	
//
//  ���췽��
//
	/** 
	 *  �޲ι��췽��,��ʼ����������ģ��
	 */
	public DefaultChessBoardModel() {
		
	}
	/**
	 *  
	 */
//
//  ��ͨ����
//
	/**
	 *  ���¼��������б�������¼�������
	 *  @param l ��ӵ��¼�������
	 */
	@Override
	public void addChessBoardModelListener(ChessBoardModelListener l) {
		repository.add(ChessBoardModelListener.class, l);
	}
	/**
	 *  ���¼��������б���ɾ���¼�������
	 *  @param l Ҫɾ�����¼�������
	 */
	@Override
	public void removeChessBoardModelListener(ChessBoardModelListener l) {
		repository.remove(ChessBoardModelListener.class, l);
	}
	/**
	 *  �����¼��������б��е���������ģ���¼����͵�������
	 *  @param e �������¼�ʵ��
	 */
	@Override
	public void notifyChessBoardModelEvent(ChessBoardModelEvent e) {
		if(repository==null){
			return;
		}
		// ȷ������һ���ǿ�����
		Object[] listeners=repository.getListenerList();
		// �Ե���ķ�ʽ�����¼�
		for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==ChessBoardModelListener.class) {
                ((ChessBoardModelListener)listeners[i+1]).chessBoardChanged(e);
            }
        }
	}
	/**
	 *  addChess�������(�÷����ᴥ����������ڸ�ģ���ϵ����Ӹı��¼�)
	 *  @param chessData ���ӵ�����ģ��
	 */
	public void addChess(ChessData chessData){
		//�������˳��ջ���������
		play_stack.push(chessData);
		//����������hash�б����������
		chessDataTable.put(chessData.getX()*100+chessData.getY(), chessData);
		notifyChessBoardModelEvent(new ChessBoardModelEvent(this,chessData));
	}
	/**
	 *  removeChess�Ƴ�����()
	 *  @param chessData ��Ҫ�Ƴ������Ӷ�������ģ��
	 */
	public void removeChess(ChessData chessData){
		//�Ƴ�˳��ջ������
		play_stack.pop();
		//�Ƴ���������hash�б��е�����
		chessDataTable.remove(chessData);
	}
	/**
	 *  ��ȡ��ǰ����������ӵ�����Ŀ
	 *  @return count ��ǰ��������Ŀ
	 */
	public int getAllChessNumber(){
		int count=chessDataTable.size();
		return count;
	}
	/**
	 *  ��ȡ��ǰ��־����£���һ���ӵ���ɫ
	 *  @return chessColor ��ǰҪ�µ����ӵ���ɫ
	 */
	public char getNextStepChessColor(){
		char chessColor=ChessPoint.NOCHESS;
		int chessNum=getAllChessNumber();
		switch (chessNum%4) {
		case 0:
		case 3:
			chessColor=ChessPoint.BLACKCHESS;
			break;
		case 1:
		case 2:
			chessColor=ChessPoint.WHITECHESS;
			break;
		}
		return chessColor;
	}
	/**
	 *  ��ȡ��ǰ��־����£��µ����һ���ӵ���ɫ
	 *  @return chessColor ����µ����ӵ���ɫ
	 */
	public char getLastStepChessColor(){
		char chessColor=ChessPoint.NOCHESS;
		int chessNum=getAllChessNumber();
		switch (chessNum%4) {
		case 0:
		case 1:
			chessColor=ChessPoint.BLACKCHESS;
			break;
		case 2:
		case 3:
			chessColor=ChessPoint.WHITECHESS;
			break;
		}
		return chessColor;
	}
	/**
	 *  ��ȸ���Ĭ���������ģ�ͣ���Ϊ�¼�������ǣ������϶���û��Ҫ�������Ժ���
	 *  
	 */
	public DefaultChessBoardModel deepClone(){
		
	}
//
//  ��ѯ�����ñ����ķ���
//
	public EventListenerList getRepository() {
		return repository;
	}

	public void setRepository(EventListenerList repository) {
		this.repository = repository;
	}
	
	public Hashtable<Integer, ChessData> getChessDataTable() {
		return chessDataTable;
	}
	
	public Stack<ChessData> getPlay_stack(){
		return play_stack;
	}
}
