package com.jf.bean;

import java.util.ArrayList;

/**
 * Move��ʾĳһ����һ���������(���ӻ�һ��)
 * @author ����
 */
public class Move {
//��������
	/** �����������飬������ʾ���µ����� */
	private ArrayList<Integer> coordArray=new ArrayList<>(2);
//���췽��
	/**
	 * ֻ��һ�����ӵģ�������̹��췽��
	 * @param cpd ������
	 */
	public Move(int coord){
		coordArray.add(coord);
	}
	/**
	 * ���������ӵģ�������̹��췽��
	 * @param cOne ��һ��������
	 * @param cTwo �ڶ���������
	 */
	public Move(int cOne,int cTwo){
		coordArray.add(cOne);
		coordArray.add(cTwo);
	}
//�������û��ѯ����
	public ArrayList<Integer> getCoordArray() {
		return coordArray;
	}
}
