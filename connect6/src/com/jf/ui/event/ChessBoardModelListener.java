package com.jf.ui.event;

import java.util.EventListener;

/**
 *  ChessBoardModelListener 棋盘数据模型事件的侦听器
 *  @author 蒋鹏
 */
public interface ChessBoardModelListener extends EventListener {
	public void chessBoardChanged(ChessBoardModelEvent e);
}
