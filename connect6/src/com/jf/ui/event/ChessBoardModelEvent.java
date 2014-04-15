package com.jf.ui.event;

import java.util.EventObject;
import java.util.Vector;

import com.jf.bean.ChessData;
import com.jf.ui.model.ChessBoardModel;

/**
 *  ChessBoardModelEvent��������ģ�͵��¼�
 *  
 *  @author ����
 */
public class ChessBoardModelEvent extends EventObject{

	private static final long serialVersionUID = 1L;
	/** ֻ���µ������ */
	public final static int UPDATECHESSPOINT=0;
	/** ����������������ģ�� */
	public final static int UPDATECHESSMODEL=1;
	
	private ChessData chessData;
	private Vector<ChessData> chessDataArray;
	//�������̵�����
	private int updateWay=-1;
//
//  ��ѯ����
//
	public ChessData getChessData() {
		return chessData;
	}
	
	public Vector<ChessData> getChessDataArray(){
		return chessDataArray;
	}
	
	public int getUpdateWay(){
		return updateWay;
	}
//
//  ���췽��
//
	/**
	 *  ���췽���Ĳ���������������ģ��
	 *  @param source �¼�Դ����
	 *  @param chessData ���Ӷ�������ģ��
	 */
	public ChessBoardModelEvent(ChessBoardModel source,ChessData chessData) {
		super(source);
		this.chessData=chessData;
		updateWay=UPDATECHESSPOINT;
	}
	/**
	 *  ���췽�����������ı����������ģ������
	 *  
	 */
	public ChessBoardModelEvent(ChessBoardModel source,Vector<ChessData> chessDataArray) {
		super(source);
		this.chessDataArray=chessDataArray;
		updateWay=UPDATECHESSMODEL;
	}
}
