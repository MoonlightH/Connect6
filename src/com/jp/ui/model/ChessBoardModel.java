package com.jp.ui.model;

import com.jp.ui.event.ChessBoardModelEvent;
import com.jp.ui.event.ChessBoardModelListener;
import com.jp.ui.event.RoleChangeEvent;
import com.jp.ui.event.RoleChangeListener;

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
	 * �Ƴ���ɫ������¼�����
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
