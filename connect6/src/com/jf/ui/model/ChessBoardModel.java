package com.jf.ui.model;

import com.jf.ui.event.ChessBoardModelEvent;
import com.jf.ui.event.ChessBoardModelListener;

/**
 *  ChessBoardModel ��������ģ�ͽӿ�
 *  @author ����
 */
public interface ChessBoardModel {
	/**
	 *  Ϊ��������ģ������¼�����
	 *  @param l ����ģ��������
	 */
	public void addChessBoardModelListener(ChessBoardModelListener l);
	/**
	 *  �Ƴ���������ģ�͵��¼�����
	 *  @param l ����ģ��������
	 */
	public void removeChessBoardModelListener(ChessBoardModelListener l);
	/**
	 *  ��������ģ���¼�
	 *  @param e �����¼�����
	 */
	public void notifyChessBoardModelEvent(ChessBoardModelEvent e);
}
