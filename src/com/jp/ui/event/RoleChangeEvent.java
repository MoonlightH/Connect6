package com.jp.ui.event;

import java.util.EventObject;
/**
 * RoleChangeEvent 角色变更事件
 * 
 * @author 蒋鹏
 */
public class RoleChangeEvent extends EventObject {
	
	private static final long serialVersionUID = 1L;
	
	public RoleChangeEvent(Object obj) {
		super(obj);
	}
}
