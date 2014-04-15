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
		DefaultChessBoardModel destDCBM = null;
		HashSet<ChessPointData> reserveChessPointData=getReserveChessPoints(sourceDCBM);
		//ͨ����������ʵ��һ������غ��е��ظ�����������ʹ�����ɵĽڵ����һ��
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
	 *  generateStepCompleteNode�����������Ӳ�����ȫ����������ɵ�ǰ�ڵ�������ӽڵ�
	 *  @param fatherNode Ҫ�������ĸ��ڵ㣬ʵ�ʾ��ǲ������ĸ��ڵ�
	 *  @return copyFatherNode ���������ӽڵ��ĸ��ڵ�ĸ��� 
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
	 *  getReserveChessPoints��������ȡ��ѡ�������������һ���߷�(��ѡ���Ļ�ȡ����������������ɨ��5����Ч���)
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
	 *  validateChessPoint��������⵱ǰ����Ƿ���Ч
	 *  @param chesPointData ��Ҫ�����������ģ��
	 *  @param chessBoardModel ����������������ģ��
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