package com.jp.ui.event;

import java.util.EventListener;
/**
 * RoleChangeListener ��ɫ����ļ�����
 * 
 * @author ����
 */
public interface RoleChangeListener extends EventListener {
	public void roleChanged(RoleChangeEvent e);
}
