package com.jp.ui.event;

import java.util.EventListener;
/**
 * RoleChangeListener 角色变更的监听器
 * 
 * @author 蒋鹏
 */
public interface RoleChangeListener extends EventListener {
	public void roleChanged(RoleChangeEvent e);
}
