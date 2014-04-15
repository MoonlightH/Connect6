package com.jf.bean;

import java.util.Vector;

import com.jf.ui.model.ChessBoardModel;
import com.jf.ui.model.DefaultChessBoardModel;

/** 
 *  GameTreeNode �������ڵ��࣬һ���ڵ����һ����֣����洢�ڵ���������ֵ�����
 *  
 *  @author ����
 */
public class GameTreeNode {
//  ��Ա����
	/** cbm��ʾ�ڵ����������־��� */
	private ChessBoardModel cbm;
	/** chessColor��ʾ�ڵ����������ֺڷ���׷��е�һ�� */
	private char chessColor;
	/** evaluateScore��ʾ�ڵ�����ֵ */
	private int evaluateScore;
	/** fatherNode��ʾ��ǰ�ڵ�ĸ��ڵ� */
	private GameTreeNode fatherNode;
	/** childNodes��ʾ���ǵ�ǰ�ڵ���ӽڵ� */
	private Vector<GameTreeNode> childNodes=new Vector<>();
//  ���췽��
	public GameTreeNode(ChessBoardModel cbm) {
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
		this.cbm=cbm;
		this.chessColor=dcbm.getLastStepChessColor();
	}
//  ��ѯ�����ó�Ա������һЩ����
	/**
	 *  ��ȡ��ǰ�ڵ���������ģ��
	 *  @return data �������ģ��
	 */
	public ChessBoardModel getCbm() {
		return cbm;
	}
	/**
	 *  ��ȡ��ǰ�ڵ������������������ɫ
	 *  @return chessColor ��ǰ�ڵ������ɫ
	 */
	public char getChessColor() {
		return chessColor;
	}
	/**
	 *  ���õ�ǰ��ֵ�����ֵ
	 *  @param evaluateScore ��ǰ��ֵ�����ֵ
	 */
	public void setEvaluateScore(int evaluateScore) {
		this.evaluateScore = evaluateScore;
	}
	/**
	 *  ��ȡ��ǰ��ֵ�����ֵ
	 *  @return evaluateScore ��ǰ��ֵ�����ֵ
	 */
	public int getEvaluateScore() {
		return evaluateScore;
	}
	/**
	 *  ��ȡ��ǰ�ڵ�ĸ��ڵ�
	 *  @param fatherNode ��ǰ�ڵ�ĸ��ڵ�
	 */
	public GameTreeNode getFatherNode() {
		return fatherNode;
	}
	/**
	 *  Ϊ��ǰ�ڵ����ø��ڵ�
	 *  @param fatherNode ���ڵ� 
	 */
	public void setFatherNode(GameTreeNode fatherNode) {
		this.fatherNode = fatherNode;
	}
	/**
	 *  ��ȡ��ǰ�ڵ�������ӽڵ�
	 *  @return childNodes ��ǰ�ڵ���ӽڵ�
	 */
	public Vector<GameTreeNode> getChildNodes() {
		return childNodes;
	}
	/**
	 *  Ϊ��ǰ�ڵ�����ӽڵ�
	 *  @param gtn ��Ҫ��ӵ��ӽڵ�
	 */
	public boolean addChildNode(GameTreeNode gtn){
		boolean result=false;
		if(gtn!=null){
			gtn.setFatherNode(this);
			result=childNodes.add(gtn);
		}
		return result;
	}
	/**
	 *  �Ƴ���ǰ�ڵ���ӽڵ�
	 *  @param gtn ��Ҫ�Ƴ����ӽڵ�
	 */
	public boolean removeChildNode(GameTreeNode gtn){
		boolean result=false;
		if(gtn!=null){
			result=childNodes.remove(gtn);
		}
		return result;
	}
}
