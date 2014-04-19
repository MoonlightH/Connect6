package com.jf.algorithm;

import java.util.Vector;

import com.jf.bean.Move;
import com.jf.config.GameConfig;
import com.jf.ui.model.ChessBoardModel;
import com.jf.ui.model.DefaultChessBoardModel;

/**
 *  SearchAlgorithm�࣬�ṩ�Բ���������������
 *  
 *  @author ����
 */
public class SearchAlgorithm {
	
	public static Move bestMove=null;
	
	/**
	 *  alphaBeat�������æ�-�¼�֦���еݹ�����(���ø�����ֵ����)
	 *  @param chessBoardMOdel �æ�-�¼�֦���������ģ��
	 *  @param alpha alphaֵ
	 *  @param beta betaֵ
	 *  @param depth ���������
	 */
	public static int alphaBeta(ChessBoardModel chessBoardModel,int alpha,int beta,int depth){
		int value,current=Integer.MIN_VALUE;
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)chessBoardModel;
		char chessColor=dcbm.getNextStepChessColor();
		//�����ֽ�����ǰ�ڵ�ΪҶ�ӽڵ��򷵻�����ֵ
		if(EvaluationFunction.isGameOver(dcbm)||depth<=0){
			int evaluateScore=EvaluationFunction.evaluateChessStatus(chessColor, dcbm);
			return evaluateScore;
		}
		Vector<Move> moves=GenerateMoves.generateMoves(chessBoardModel);
		for (Move move : moves) {
			dcbm.makeNextMove(move);
			value=-alphaBeta(chessBoardModel, -beta, -alpha,depth-1);
			dcbm.unMakeMove();
			if(value>=current){
				current=value;
				if(value>=alpha){
					alpha=value;
					bestMove=move;
				}
				//beta��֦
				if(value>=beta)
					break;
			}
		}
		return current;
	}
	/**
	 *  getNextMoves��������ȡ��ǰ��־������һ���߷�(�߷�������������)
	 *  @param chessBoardModel ��ǰ��ֵ�����ģ��
	 *  @return move ����һ�غϵ������߷�
	 */
	public static Move getNextMoves(ChessBoardModel chessBoardModel){
		Move move=null;
		alphaBeta(chessBoardModel, Integer.MIN_VALUE, Integer.MAX_VALUE, GameConfig.AILevel);
		if(bestMove!=null)
			move=bestMove;
		return move;
	}
}
