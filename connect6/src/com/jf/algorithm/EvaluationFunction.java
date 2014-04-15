package com.jf.algorithm;

//import java.util.Collections;
//import java.util.Comparator;
import java.util.Hashtable;
import java.util.Vector;



import com.jf.bean.ChessData;
//import com.jf.bean.ChessData;
import com.jf.bean.Road;
import com.jf.ui.ChessPoint;
import com.jf.ui.model.ChessBoardModel;
import com.jf.ui.model.DefaultChessBoardModel;

/**
 *  EvaluationFunction评估函数用来评估当前棋局的局势
 * 
 *  @author 蒋鹏
 */
public class EvaluationFunction {
	/** 对路的评分准则，该结果通过遗传算法离线优化得到 */
	public final static int[] SCOREOFROAD = {0,17, 78, 141, 788, 1030};
	/** 所有的棋路 */
	public static Vector<Road> allRoads = new Vector<>(924);
	/** 黑子的有效棋路数 */
	public static int[] numberOfBlackRoad = new int[7];
	/** 白子的有效棋路数 */
	public static int[] numberOfWhiteRoad = new int[7];
//  类方法
	/**  
	 *  checkChessStatus方法用来检查当前棋局状态，并将棋局状态结果记录 
	 *  @param cbm 当前的棋局数据模型
	 */
	public static void checkChessStatus(ChessBoardModel cbm) {
		allRoads = new Vector<>(924);
		numberOfBlackRoad = new int[7];
		numberOfWhiteRoad = new int[7];
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
		Hashtable<Integer, ChessData> chessDataTable=dcbm.getChessDataTable();
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 14; j++) {
				Vector<ChessData> horizontal = new Vector<>(6);
				horizontal.add(chessDataTable.get(i*100+j));
				horizontal.add(chessDataTable.get(i*100+j+1));
				horizontal.add(chessDataTable.get(i*100+j+2));
				horizontal.add(chessDataTable.get(i*100+j+3));
				horizontal.add(chessDataTable.get(i*100+j+4));
				horizontal.add(chessDataTable.get(i*100+j+5));
				Road hR = new Road(horizontal);
				allRoads.add(hR);
				Vector<ChessData> vertical = new Vector<>(6);
				vertical.add(chessDataTable.get(j*100+i));
				vertical.add(chessDataTable.get((j+1)*100+i));
				vertical.add(chessDataTable.get((j+2)*100+i));
				vertical.add(chessDataTable.get((j+3)*100+i));
				vertical.add(chessDataTable.get((j+4)*100+i));
				vertical.add(chessDataTable.get((j+5)*100+i));
				Road vR = new Road(vertical);
				allRoads.add(vR);
			}
		}
		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 14; j++) {
				Vector<ChessData> leftOblique = new Vector<>(6);
				leftOblique.add(chessDataTable.get(i*100+j));
				leftOblique.add(chessDataTable.get((i+1)*100+j+1));
				leftOblique.add(chessDataTable.get((i+2)*100+j+2));
				leftOblique.add(chessDataTable.get((i+3)*100+j+3));
				leftOblique.add(chessDataTable.get((i+4)*100+j+4));
				leftOblique.add(chessDataTable.get((i+5)*100+j+5));
				Road lR = new Road(leftOblique);
				allRoads.add(lR);
				Vector<ChessData> rightOblique = new Vector<>(6);
				rightOblique.add(chessDataTable.get(i*100+j+5));
				rightOblique.add(chessDataTable.get((i+1)*100+j+4));
				rightOblique.add(chessDataTable.get((i+2)*100+j+3));
				rightOblique.add(chessDataTable.get((i+3)*100+j+2));
				rightOblique.add(chessDataTable.get((i+4)*100+j+1));
				rightOblique.add(chessDataTable.get((i+5)*100+j));
				Road rR = new Road(rightOblique);
				allRoads.add(rR);
			}
		}
		for (Road r : allRoads) {
			if (r.getChessColor() == ChessPoint.BLACKCHESS) {
				numberOfBlackRoad[r.getValidChessCount()]++;
			}
			if (r.getChessColor() == ChessPoint.WHITECHESS) {
				numberOfWhiteRoad[r.getValidChessCount()]++;
			}
		}
	}
	/**
	 *  evaluateChessStatus方法用来评估棋局的局势信息
	 *  @param chessColor 要评估局势的一方所持棋子颜色
	 *  @param cbm 当前的棋局数据模型
	 *  @return current 当前棋子颜色对应一方棋局的评估分数
	 */
	public static int evaluateChessStatus(char chessColor,
			ChessBoardModel cbm) {
		int currentScore = 0;
		int blackRoadScore = 0;
		int whiteRoadScore = 0;
		checkChessStatus(cbm);
		for (int i = 1; i < 6; i++) {
			blackRoadScore += numberOfBlackRoad[i] * SCOREOFROAD[i];
			whiteRoadScore += numberOfWhiteRoad[i] * SCOREOFROAD[i];
		}
		if (chessColor == ChessPoint.BLACKCHESS) {
			currentScore = blackRoadScore - whiteRoadScore;
		}
		if (chessColor == ChessPoint.WHITECHESS) {
			currentScore = whiteRoadScore - blackRoadScore;
		}
		return currentScore;
	}
	/**
	 *  isGameOver 根据棋局数据模型判断棋局是否结束
	 *  @param cbm 棋局数据模型
	 *  @return result 判断结果，true表明当前棋局结束，false(默认)表明当前棋局未结束
	 */
	public static boolean isGameOver(ChessBoardModel cbm){
		boolean result=false;
		checkChessStatus(cbm);
		if(numberOfBlackRoad[6]>0||numberOfWhiteRoad[6]>0)
			result=true;
		return result;
	}
}
