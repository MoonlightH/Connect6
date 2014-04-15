package com.jf.bean;

import java.util.Vector;

import com.jf.ui.model.ChessBoardModel;
import com.jf.ui.model.DefaultChessBoardModel;

/** 
 *  GameTreeNode 博弈树节点类，一个节点代表一种棋局，并存储节点所代表棋局的评分
 *  
 *  @author 蒋鹏
 */
public class GameTreeNode {
//  成员变量
	/** cbm表示节点所代表的棋局局势 */
	private ChessBoardModel cbm;
	/** chessColor表示节点所代表的棋局黑方或白方中的一方 */
	private char chessColor;
	/** evaluateScore表示节点评估值 */
	private int evaluateScore;
	/** fatherNode表示当前节点的父节点 */
	private GameTreeNode fatherNode;
	/** childNodes表示的是当前节点的子节点 */
	private Vector<GameTreeNode> childNodes=new Vector<>();
//  构造方法
	public GameTreeNode(ChessBoardModel cbm) {
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
		this.cbm=cbm;
		this.chessColor=dcbm.getLastStepChessColor();
	}
//  查询或设置成员变量的一些方法
	/**
	 *  获取当前节点的棋局数据模型
	 *  @return data 棋局数据模型
	 */
	public ChessBoardModel getCbm() {
		return cbm;
	}
	/**
	 *  获取当前节点的棋局所代表的棋子颜色
	 *  @return chessColor 当前节点棋局颜色
	 */
	public char getChessColor() {
		return chessColor;
	}
	/**
	 *  设置当前棋局的评估值
	 *  @param evaluateScore 当前棋局的评估值
	 */
	public void setEvaluateScore(int evaluateScore) {
		this.evaluateScore = evaluateScore;
	}
	/**
	 *  获取当前棋局的评估值
	 *  @return evaluateScore 当前棋局的评估值
	 */
	public int getEvaluateScore() {
		return evaluateScore;
	}
	/**
	 *  获取当前节点的父节点
	 *  @param fatherNode 当前节点的父节点
	 */
	public GameTreeNode getFatherNode() {
		return fatherNode;
	}
	/**
	 *  为当前节点设置父节点
	 *  @param fatherNode 父节点 
	 */
	public void setFatherNode(GameTreeNode fatherNode) {
		this.fatherNode = fatherNode;
	}
	/**
	 *  获取当前节点的所有子节点
	 *  @return childNodes 当前节点的子节点
	 */
	public Vector<GameTreeNode> getChildNodes() {
		return childNodes;
	}
	/**
	 *  为当前节点添加子节点
	 *  @param gtn 需要添加的子节点
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
	 *  移除当前节点的子节点
	 *  @param gtn 需要移除的子节点
	 */
	public boolean removeChildNode(GameTreeNode gtn){
		boolean result=false;
		if(gtn!=null){
			result=childNodes.remove(gtn);
		}
		return result;
	}
}
