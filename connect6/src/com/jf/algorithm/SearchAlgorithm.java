package com.jf.algorithm;

import java.util.Vector;

import com.jf.bean.GameTreeNode;
import com.jf.config.GameConfig;
import com.jf.ui.model.ChessBoardModel;
import com.jf.ui.model.DefaultChessBoardModel;

/**
 *  SearchAlgorithm类，提供对博弈树的搜索方法
 *  
 *  @author 蒋鹏
 */
public class SearchAlgorithm {
	/**
	 *  alphaBeat方法采用α-β剪枝进行递归搜索(采用负极大值方法)
	 *  @param gameTreeNode 用α-β剪枝的节点
	 *  @param alpha alpha值
	 *  @param beta beta值
	 *  @param depth 搜索的深度
	 */
	public static int alphaBeta(GameTreeNode gameTreeNode,int alpha,int beta,int depth){
		int value,current=Integer.MIN_VALUE;
		ChessBoardModel cbm=gameTreeNode.getCbm();
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
		char chessColor=dcbm.getLastStepChessColor();
		//如果棋局结束或当前节点为叶子节点则返回评估值
		if(EvaluationFunction.isGameOver(cbm)||depth<=0){
			int evaluateScore=EvaluationFunction.evaluateChessStatus(chessColor, cbm);
			gameTreeNode.setEvaluateScore(evaluateScore);
			return evaluateScore;
		}
		GenerateMoves.generateChildNodes(gameTreeNode);
		Vector<GameTreeNode> childNodes=gameTreeNode.getChildNodes();
		for (GameTreeNode gtn : childNodes) {
			value=-alphaBeta(gtn, -beta, -alpha,depth-1);
			if(value>=current){
				current=value;
				if(value>=alpha)
					alpha=value;
				//beta剪枝
				if(value>=beta)
					break;
			}
		}
		gameTreeNode.setEvaluateScore(current);
		return current;
	}
	/**
	 *  getNextMoves方法，获取当前棋局局面的下一步走法(走法包含两个棋子)
	 *  @param chessBoardModel 当前棋局的数据模型
	 *  @return cbm 返回走过一步后的棋局数据模型
	 */
	public static ChessBoardModel getNextMoves(ChessBoardModel chessBoardModel){
		ChessBoardModel copyChessBoardModel=null;
		copyChessBoardModel=((DefaultChessBoardModel)chessBoardModel).deepClone();
		GameTreeNode gameTreeNode=new GameTreeNode(copyChessBoardModel);
		GameTreeNode bestMoveNode = null;
		int score=alphaBeta(gameTreeNode, Integer.MIN_VALUE, Integer.MIN_VALUE, GameConfig.AILevel);
		Vector<GameTreeNode> childNodes=gameTreeNode.getChildNodes();
		for (GameTreeNode gtn : childNodes) {
			if(gtn.getEvaluateScore()==-score){
				bestMoveNode=gtn;
			}
		}
		ChessBoardModel cbm=bestMoveNode.getCbm();
		return cbm;
	}
}
