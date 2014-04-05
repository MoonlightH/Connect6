package com.jf.ui.model;

import java.io.Serializable;
import java.util.Vector;

import javax.swing.event.EventListenerList;

import com.jf.bean.ChessData;
import com.jf.bean.ChessInfo;
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
	/** ������Ϣ���� */
	private ChessInfo chessInfo=new ChessInfo(); 
//
//  ���췽��
//
	/** 
	 *  �޲ι��췽��,��ʼ����������ģ��
	 */
	public DefaultChessBoardModel() {
		
	}
	/**
	 *  ������������Ϣ������ʼ��Ĭ����������ģ��
	 *  @param dataModel ���ӵ�������Ϣ
	 */
	public DefaultChessBoardModel(ChessInfo chessInfo){
		this.chessInfo=chessInfo.deepClone();
	}
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
	 *  setChess����������������ģ��������(�÷����ᴥ����������ڸ�ģ���ϵ����Ӹı��¼�)
	 *  @param x �����������ϵ�x����
	 *  @param y �����������ϵ�y����
	 */
	public void setChess(int x,int y){
		chessInfo.chessInfo.get(x).set(y, getNextStepChessColor());
		notifyChessBoardModelEvent(new ChessBoardModelEvent(this,x,y));
	}
	/**
	 *  ��ȡ��ǰ����������ӵ�����Ŀ
	 *  @return count ��ǰ��������Ŀ
	 */
	public int getAllChessNumber(){
		int count=0;
		for (Vector<Character> v : chessInfo.chessInfo) {
			for(Character c:v){
				if(c.charValue()!=ChessPoint.NOCHESS){
					count++;
				}
			}
		}
		return count;
	}
	/**
	 *  ��ȡ��ǰ��������п���������
	 *  @return nullPoints ���еĿ����
	 */
	public Vector<ChessData> getAllNullChessPoints(){
		Vector<ChessData> nullPoints=new Vector<>();
		for(int i = 0; i < 19; i++){
			for (int j = 0; j < 19; j++) {
				if(chessInfo.chessInfo.get(i).get(j)==ChessPoint.NOCHESS){
					ChessData chess=new ChessData(i,j);
					nullPoints.add(chess);
				}
			}
		}
		return nullPoints;
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
	 *  deepClone����ʵ����ȸ��ƣ�ͨ�÷����������л��������л�ʵ�ֶ�����ȸ���,�����۱Ƚϴ�
	 */
	public DefaultChessBoardModel deepClone(){
		DefaultChessBoardModel defaultChessBoardModel=new DefaultChessBoardModel(this.chessInfo);
		return defaultChessBoardModel;
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

	public ChessInfo getChessInfo() {
		return chessInfo;
	}

	public void setDataModel(ChessInfo chessInfo) {
		this.chessInfo = chessInfo;
	}
}
