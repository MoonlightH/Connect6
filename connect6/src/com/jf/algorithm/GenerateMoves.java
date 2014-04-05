package com.jf.algorithm;

import java.util.Vector;

import com.jf.bean.ChessData;
import com.jf.bean.GameTreeNode;
import com.jf.config.GameConfig;
import com.jf.ui.model.ChessBoardModel;
import com.jf.ui.model.DefaultChessBoardModel;

/**
 *  GenerateMoves�߷������࣬���ݵ�ǰ��־���������ز������ڵ�
 *  
 *  @author ����
 */
public class GenerateMoves {
	/**
	 *  generateChildNodes���ɵ�ǰ�ڵ���ӽڵ�
	 *  @param fatherNode ��Ҫ�����ӽڵ�ĸ��ڵ�
	 *  @return chessBoardModel ���������ӽڵ��ĸ��ڵ�
	 */
	public static void generateChildNodes(GameTreeNode fatherNode){
		ChessBoardModel cbm=fatherNode.getCbm();
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
		int allChessNumber=dcbm.getAllChessNumber();
		//ͨ��������Ŀ���ж����Ӳ����Ƿ�����
		boolean chessStepIsComplete=allChessNumber%2==1;
		if(chessStepIsComplete && allChessNumber>0){
			generateStepCompleteNode(fatherNode);
		}
		if(!chessStepIsComplete && allChessNumber>0){
			generateStepUnCompleteNode(fatherNode);
		}
	}
	/**
	 *  generateStepCompleteNode������������������ȫ�����ɵ�ǰ�ڵ�������ӽڵ�
	 *  @param fatherNode Ҫ�������ĸ��ڵ㣬ʵ�ʾ��ǲ������ĸ��ڵ�
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
	 *  generateStepCompleteNode�����������Ӳ�����ȫ����������ɵ�ǰ�ڵ�������ӽڵ�
	 *  @param fatherNode Ҫ�������ĸ��ڵ㣬ʵ�ʾ��ǲ������ĸ��ڵ�
	 *  @return copyFatherNode ���������ӽڵ��ĸ��ڵ�ĸ��� 
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