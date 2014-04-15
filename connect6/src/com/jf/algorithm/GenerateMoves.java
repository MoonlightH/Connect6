package com.jf.algorithm;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import com.jf.bean.ChessData;
import com.jf.bean.ChessPointData;
import com.jf.bean.GameTreeNode;
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
		DefaultChessBoardModel destDCBM = null;
		HashSet<ChessPointData> reserveChessPointData=getReserveChessPoints(sourceDCBM);
		//通过迭代器来实现一次下棋回合中的重复步数，可以使得生成的节点减少一半
		Iterator<ChessPointData> iStepOne=reserveChessPointData.iterator();
		while(iStepOne.hasNext()){
			ChessPointData chessPointDataOne=iStepOne.next();
			iStepOne.remove();
			@SuppressWarnings("unchecked")
			Iterator<ChessPointData> iStepTwo=((HashSet<ChessPointData>) reserveChessPointData.clone()).iterator();
			while(iStepTwo.hasNext()){
				ChessPointData chessPointDataTwo=iStepTwo.next();
				destDCBM=sourceDCBM
			}
		}
	}
	/**
	 *  generateStepCompleteNode方法，在落子并不完全的情况下生成当前节点的所有子节点
	 *  @param fatherNode 要生成树的父节点，实际就是博弈树的根节点
	 *  @return copyFatherNode 返回生成子节点后的父节点的副本 
	 */
	public static void generateStepUnCompleteNode(GameTreeNode fatherNode){
//		DefaultChessBoardModel sourceDCBM=(DefaultChessBoardModel)fatherNode.getCbm();
//		DefaultChessBoardModel destDCBM = null;
//		char currentChessColor=sourceDCBM.getLastStepChessColor();
//		Vector<ChessData> step=new Vector<>(GameConfig.GENERATEMOVESWIDTH);
//		step=EvaluationFunction.getTopPointsScore(currentChessColor, sourceDCBM, GameConfig.GENERATEMOVESWIDTH);
//		for(ChessData chess:step){
//			destDCBM = sourceDCBM.deepClone();
//			destDCBM.setChess(chess.getX(), chess.getY());
//			GameTreeNode gtn=new GameTreeNode(destDCBM);
//			fatherNode.addChildNode(gtn);
//		}
	}
	/**
	 *  getReserveChessPoints方法，获取备选棋点用来生成下一步走法(备选棋点的获取采用已下棋子向外扫描5个有效棋点)
	 *  @param chessBoardModel
	 */
	public static HashSet<ChessPointData> getReserveChessPoints(ChessBoardModel chessBoardModel){
		DefaultChessBoardModel defaultChessBoardModel=(DefaultChessBoardModel)chessBoardModel;
		Hashtable<Integer, ChessData> chessDataTable=defaultChessBoardModel.getChessDataTable();
		HashSet<ChessPointData> reserveChessPoints=new HashSet<>(200);
		Set<Integer> chessDataKeys=chessDataTable.keySet();
		for (Integer integer : chessDataKeys) {
			int x=integer/100;
			int y=integer%100;
			for(int i=1;i<6;i++){
				int[] xArray={x-i,x,x+i,x+i,x+i,x,x-i,x-i};
				int[] yArray={y-i,y-i,y-i,y,y+i,y+i,y+i,y};
				for(int j=0;j<8;j++){
					ChessPointData tempCpd=new ChessPointData(xArray[j],yArray[j]);
					if(validtaeChessPoint(tempCpd, defaultChessBoardModel)){
						reserveChessPoints.add(tempCpd);
					}
				}
			}
		}
		return reserveChessPoints;
	}
	/**
	 *  validateChessPoint方法，检测当前棋点是否有效
	 *  @param chesPointData 需要检测的棋点数据模型
	 *  @param chessBoardModel 棋点所处的棋局数据模型
	 */
	public static boolean validtaeChessPoint(ChessPointData chessPointData,ChessBoardModel chessBoardModel){
		DefaultChessBoardModel defaultChessBoardModel=(DefaultChessBoardModel)chessBoardModel;
		Hashtable<Integer, ChessData> chessDataTable=defaultChessBoardModel.getChessDataTable();
		int x=chessPointData.getX();
		int y=chessPointData.getY();
		boolean result=false;
		if(chessDataTable.get(x*100+y)==null){
			if(x>0 && x<20 && y>0 && y<20){
				result=true;
			}
		}
		return result;
	}
	
}