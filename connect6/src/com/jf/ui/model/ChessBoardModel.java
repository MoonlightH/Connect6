package com.jf.ui.model;

import com.jf.ui.event.ChessBoardModelEvent;
import com.jf.ui.event.ChessBoardModelListener;
import com.jf.ui.event.RoleChangeEvent;
import com.jf.ui.event.RoleChangeListener;

/**
 * ChessBoardModel ��������ģ�ͽӿ�
 * @author ����
 */
public interface ChessBoardModel {
	/**
	 * Ϊ��������ģ������¼�����
	 * @param l ����ģ��������
	 */
	public void addChessBoardModelListener(ChessBoardModelListener l);
	/**
	 * �Ƴ���������ģ�͵��¼�����
	 * @param l ����ģ��������
	 */
	public void removeChessBoardModelListener(ChessBoardModelListener l);
	/**
	 * Ϊ��ɫ�������¼�����
	 * @param l ����ģ��������
	 */
	public void addRoleChangeListener(RoleChangeListener l);
	/**
	 * �ƽ�ɫ������¼�����
	 * @param l ����ģ��������
	 */
	public void removeRoleChangeListener(RoleChangeListener l);
	/**
	 * ��������ģ���¼�
	 * @param e �����¼�����
	 */
	public void notifyChessBoardModelEvent(ChessBoardModelEvent e);
	/**
	 * ������ɫ����¼�
	 * @param e ��ɫ����¼�����
	 */
	public void notifyRoleChangeEvent(RoleChangeEvent e);
}
