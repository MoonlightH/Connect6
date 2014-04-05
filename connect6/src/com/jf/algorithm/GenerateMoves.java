package com.jf.algorithm;

import java.util.Vector;

import com.jf.bean.ChessData;
import com.jf.bean.GameTreeNode;
import com.jf.config.GameConfig;
import com.jf.ui.model.ChessBoardModel;
import com.jf.ui.model.DefaultChessBoardModel;

/**
 *  GenerateMoves走法生成类，根据当前棋局局势生成相关博弈树节点
 *  
 *  @author 蒋鹏
 */
public class GenerateMoves {
	/**
	 *  generateChildNodes生成当前节点的子节点
	 *  @param fatherNode 需要生成子节点的父节点
	 *  @return chessBoardModel 返回生成子节点后的父节点
	 */
	public static void generateChildNodes(GameTreeNode fatherNode){
		ChessBoardModel cbm=fatherNode.getCbm();
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
		int allChessNumber=dcbm.getAllChessNumber();
		//通过棋子数目来判断棋子步数是否完整
		boolean chessStepIsComplete=allChessNumber%2==1;
		if(chessStepIsComplete && allChessNumber>0){
			generateStepCompleteNode(fatherNode);
		}
		if(!chessStepIsComplete && allChessNumber>0){
			generateStepUnCompleteNode(fatherNode);
		}
	}
	/**
	 *  generateStepCompleteNode方法，在两步落子完全后生成当前节点的所有子节点
	 *  @param fatherNode 要生成树的父节点，实际就是博弈树的根节点
	 */
	public static void generateStepCompleteNode(GameTreeNode fatherNode){
		DefaultChessBoardModel sourceDCBM=(DefaultChessBoardModel)fatherNode.getCbm();
		char currentChessColor=sourceDCBM.getNextStepChessColor();
		Vector<ChessData> step1=new Vector<>(GameConfig.GENERATEMOVESWIDTH);
		step1=EvaluationFunction.getTopPointsScore(currentChessColor, sourceDCBM, GameConfig.GENERATEMOVESWIDTH);
		for(int i=0;i<step1.size();i++){
			DefaultChessBoardModel destDCBM = sourceDCBM.deepClone();
			ChessData chess1=step1.get(i);
			destDCBM.setChess(chess1.getX(),chess1.getY());
			int width=step1.size()-i;
			Vector<ChessData> step2=new Vector<>(width);
			step2=EvaluationFunction.getTopPointsScore(currentChessColor, destDCBM, width);
			for(ChessData chess2:step2){
				DefaultChessBoardModel copyDestDCBM=destDCBM.deepClone();
				copyDestDCBM.setChess(chess2.getX(), chess2.getY());
				GameTreeNode gtn=new GameTreeNode(copyDestDCBM);
				fatherNode.addChildNode(gtn);
			}
		}
	}
	/**
	 *  generateStepCompleteNode方法，在落子并不完全的情况下生成当前节点的所有子节点
	 *  @param fatherNode 要生成树的父节点，实际就是博弈树的根节点
	 *  @return copyFatherNode 返回生成子节点后的父节点的副本 
	 */
	public static void generateStepUnCompleteNode(GameTreeNode fatherNode){
		DefaultChessBoardModel sourceDCBM=(DefaultChessBoardModel)fatherNode.getCbm();
		DefaultChessBoardModel destDCBM = null;
		char currentChessColor=sourceDCBM.getLastStepChessColor();
		Vector<ChessData> step=new Vector<>(GameConfig.GENERATEMOVESWIDTH);
		step=EvaluationFunction.getTopPointsScore(currentChessColor, sourceDCBM, GameConfig.GENERATEMOVESWIDTH);
		for(ChessData chess:step){
			destDCBM = sourceDCBM.deepClone();
			destDCBM.setChess(chess.getX(), chess.getY());
			GameTreeNode gtn=new GameTreeNode(destDCBM);
			fatherNode.addChildNode(gtn);
		}
	}
	
}