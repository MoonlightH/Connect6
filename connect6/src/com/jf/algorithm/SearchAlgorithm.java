package com.jf.algorithm;

import java.util.Vector;

import com.jf.bean.Move;
import com.jf.config.GameConfig;
import com.jf.ui.model.ChessBoardModel;
import com.jf.ui.model.DefaultChessBoardModel;

/**
 *  SearchAlgorithm类，提供对博弈树的搜索方法
 *  
 *  @author 蒋鹏
 */
public class SearchAlgorithm {
	
	public static Move bestMove=null;
	
	/**
	 *  alphaBeat方法采用α-β剪枝进行递归搜索(采用负极大值方法)
	 *  @param chessBoardMOdel 用α-β剪枝的棋局数据模型
	 *  @param alpha alpha值
	 *  @param beta beta值
	 *  @param depth 搜索的深度
	 */
	public static int alphaBeta(ChessBoardModel chessBoardModel,int alpha,int beta,int depth){
		int value,current=Integer.MIN_VALUE;
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)chessBoardModel;
		char chessColor=dcbm.getNextStepChessColor();
		//如果棋局结束或当前节点为叶子节点则返回评估值
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
				//beta剪枝
				if(value>=beta)
					break;
			}
		}
		return current;
	}
	/**
	 *  getNextMoves方法，获取当前棋局局面的下一步走法(走法包含两个棋子)
	 *  @param chessBoardModel 当前棋局的数据模型
	 *  @return move 返回一回合的棋子走法
	 */
	public static Move getNextMoves(ChessBoardModel chessBoardModel){
		Move move=null;
		alphaBeta(chessBoardModel, Integer.MIN_VALUE, Integer.MAX_VALUE, GameConfig.AILevel);
		if(bestMove!=null)
			move=bestMove;
		return move;
	}
}
