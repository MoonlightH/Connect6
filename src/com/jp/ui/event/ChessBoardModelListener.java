package com.jp.ui.event;

import java.util.EventListener;

/**
 * ChessBoardModelListener ��������ģ���¼���������
 * 
 * @author ����
 */
public interface ChessBoardModelListener extends EventListener {
	public void chessBoardChanged(ChessBoardModelEvent e);
}
