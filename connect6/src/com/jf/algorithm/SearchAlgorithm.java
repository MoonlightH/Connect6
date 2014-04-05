package com.jf.algorithm;

import java.util.Vector;

import com.jf.bean.GameTreeNode;
import com.jf.config.GameConfig;
import com.jf.ui.model.ChessBoardModel;
import com.jf.ui.model.DefaultChessBoardModel;

/**
 *  SearchAlgorithm�࣬�ṩ�Բ���������������
 *  
 *  @author ����
 */
public class SearchAlgorithm {
	/**
	 *  alphaBeat�������æ�-�¼�֦���еݹ�����(���ø�����ֵ����)
	 *  @param gameTreeNode �æ�-�¼�֦�Ľڵ�
	 *  @param alpha alphaֵ
	 *  @param beta betaֵ
	 *  @param depth ���������
	 */
	public static int alphaBeta(GameTreeNode gameTreeNode,int alpha,int beta,int depth){
		int value,current=Integer.MIN_VALUE;
		ChessBoardModel cbm=gameTreeNode.getCbm();
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
		char chessColor=dcbm.getLastStepChessColor();
		//�����ֽ�����ǰ�ڵ�ΪҶ�ӽڵ��򷵻�����ֵ
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
				//beta��֦
				if(value>=beta)
					break;
			}
		}
		gameTreeNode.setEvaluateScore(current);
		return current;
	}
	/**
	 *  getNextMoves��������ȡ��ǰ��־������һ���߷�(�߷�������������)
	 *  @param chessBoardModel ��ǰ��ֵ�����ģ��
	 *  @return cbm �����߹�һ������������ģ��
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
