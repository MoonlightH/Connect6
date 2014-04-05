package com.jf.ui.event;

import java.util.EventObject;
import java.util.Vector;

import com.jf.ui.model.ChessBoardModel;

/**
 *  ChessBoardModelEvent��������ģ�͵��¼�
 *  
 *  @author ����
 */
public class ChessBoardModelEvent extends EventObject{

	private static final long serialVersionUID = 1L;
	/** UPDATECHESSPOINT������ֻ�ı䵥�������¼� */
	public static final int UPDATECHESSPOINT=0;
	/** UPDATEMODEL�����Ǳ����������ģ�͵��¼� */
	public static final int UPDATEMODEL=1;
//	
//  ʵ������
//
	private int type=-1;
	private Vector<Integer> location=new Vector<>(2);
	private Vector<Vector<Integer>> locations=new Vector<>(20,3);
//
//  ��ѯ����
//
	public Vector<Integer> getLocation() {
		return location;
	}
	
	public Vector<Vector<Integer>> getLocations() {
		return locations;
	}
	
	public int getType() {
		return type;
	}
//
//  ���췽��
//
	public ChessBoardModelEvent(ChessBoardModel source) {
		super(source);
	}
	/**
	 *  ���췽���Ĳ�������������꣬�����¼�����Ϊ�ı䵥�������������
	 *  @param source �¼�Դ����
	 *  @param x ��Ҫ��������������һά�±�
	 *  @param y ��Ҫ�������������Ķ�ά�±�
	 */
	public ChessBoardModelEvent(ChessBoardModel source,int x,int y) {
		super(source);
		location.add(x);
		location.add(y);
		type=UPDATECHESSPOINT;
	}
	/**
	 *  ���췽���Ĳ�������������꼯�ϣ������¼�����Ϊ�ı��������������
	 *  @param source �¼�Դ����
	 *  @param v ��Ҫ�����������ļ���
	 */
	public ChessBoardModelEvent(ChessBoardModel source,Vector<Vector<Integer>> v) {
		super(source);
		locations=v;
		type=UPDATEMODEL;
	}
}
