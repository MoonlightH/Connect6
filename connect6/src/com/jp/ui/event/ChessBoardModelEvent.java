package com.jp.ui.event;

import java.util.EventObject;
import java.util.HashSet;

import com.jp.ui.model.ChessBoardModel;

/**
 * ChessBoardModelEvent��������ģ�͵��¼�
 * @author ����
 */
public class ChessBoardModelEvent extends EventObject{

	private static final long serialVersionUID = 1L;
	/** ֻ���µ������ */
	public final static int UPDATECHESSPOINT=0;
	/** ����������������ģ�� */
	public final static int UPDATECHESSMODEL=1;
	/** ��Ҫ���µĵ���������긴��ֵ */
	private Integer coord;
	/** ��Ҫ���µ�������긴��ֵ���� */
	private HashSet<Integer> coordSet;
	//�������̵�����
	private int updateWay=-1;
//
//  ���췽��
//
	/**
	 * ���췽���Ĳ���������������ģ��
	 * @param source �¼�Դ����
	 */
	public ChessBoardModelEvent(ChessBoardModel source) {
		super(source);
	}
	/**
	 * ���췽���Ĳ���������������ģ��
	 * @param source �¼�Դ����
	 * @param coord �������
	 */
	public ChessBoardModelEvent(ChessBoardModel source,Integer coord) {
		super(source);
		this.coord=coord;
		updateWay=UPDATECHESSPOINT;
	}
	/**
	 * ���췽�����������ı����������ģ������
	 * @param source �¼�Դ����
	 * @param coordSet ������꼯��
	 */
	public ChessBoardModelEvent(ChessBoardModel source,HashSet<Integer> coordSet) {
		super(source);
		this.coordSet=coordSet;
		updateWay=UPDATECHESSMODEL;
	}
//
//  ��ѯ����
//
	public Integer getCoord() {
		return coord;
	}
	
	public HashSet<Integer> getCoordSet(){
		return coordSet;
	}
	
	public int getUpdateWay(){
		return updateWay;
	}
}
