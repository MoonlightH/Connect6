package com.jf.algorithm;

import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import com.jf.bean.ChessData;
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
	public final static int[] SCOREOFROAD = {0, 17, 78, 141, 788, 1030, 10000 };
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
		Vector<Vector<Character>> chessInfo=dcbm.getChessInfo().chessInfo;
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 14; j++) {
				Vector<Character> horizontal = new Vector<>(6);
				horizontal.add(chessInfo.get(i).get(j));
				horizontal.add(chessInfo.get(i).get(j + 1));
				horizontal.add(chessInfo.get(i).get(j + 2));
				horizontal.add(chessInfo.get(i).get(j + 3));
				horizontal.add(chessInfo.get(i).get(j + 4));
				horizontal.add(chessInfo.get(i).get(j + 5));
				Road hR = new Road(horizontal);
				allRoads.add(hR);
				Vector<Character> vertical = new Vector<>(6);
				vertical.add(chessInfo.get(j).get(i));
				vertical.add(chessInfo.get(j + 1).get(i));
				vertical.add(chessInfo.get(j + 2).get(i));
				vertical.add(chessInfo.get(j + 3).get(i));
				vertical.add(chessInfo.get(j + 4).get(i));
				vertical.add(chessInfo.get(j + 5).get(i));
				Road vR = new Road(vertical);
				allRoads.add(vR);
			}
		}
		for (int i = 0; i < 14; i++) {
			for (int j = 0; j < 14; j++) {
				Vector<Character> leftOblique = new Vector<>(6);
				leftOblique.add(chessInfo.get(i).get(j));
				leftOblique.add(chessInfo.get(i + 1).get(j + 1));
				leftOblique.add(chessInfo.get(i + 2).get(j + 2));
				leftOblique.add(chessInfo.get(i + 3).get(j + 3));
				leftOblique.add(chessInfo.get(i + 4).get(j + 4));
				leftOblique.add(chessInfo.get(i + 5).get(j + 5));
				Road lR = new Road(leftOblique);
				allRoads.add(lR);
				Vector<Character> rightOblique = new Vector<>(6);
				rightOblique.add(chessInfo.get(i).get(j + 5));
				rightOblique.add(chessInfo.get(i + 1).get(j + 4));
				rightOblique.add(chessInfo.get(i + 2).get(j + 3));
				rightOblique.add(chessInfo.get(i + 3).get(j + 2));
				rightOblique.add(chessInfo.get(i + 4).get(j + 1));
				rightOblique.add(chessInfo.get(i + 5).get(j));
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
		for (int i = 1; i < 7; i++) {
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
	 *  getChessPointScore获取单个棋点，对应相关棋子时的，棋点评分
	 *  @param chessColor 需要评估的棋子的颜色
	 *  @param cbm 当前的棋局数据模型
	 *  @param x 棋点所在的x坐标
	 *  @param y 棋点所在的y坐标
	 *  @return chessScore 对应棋点的评分以及棋点坐标信息
	 */
	public static ChessData getChessPointScore(char chessColor,
			ChessBoardModel cbm, int x, int y) {
		ChessData chess = new ChessData();
		DefaultChessBoardModel oldDcbm=(DefaultChessBoardModel)cbm;
		DefaultChessBoardModel newDcbm = oldDcbm.deepClone();
		newDcbm.setChess(x, y);
		int oldChessScore = evaluateChessStatus(chessColor, oldDcbm);
		int newChessScore = evaluateChessStatus(chessColor, newDcbm);
		chess.setX(x);
		chess.setY(y);
		chess.setEvaluateScore(newChessScore - oldChessScore);
		return chess;
	}
	/**
	 *  getTopPointsScore获取评分值为前top个的棋点
	 *  @param chessColor 需要获取棋点的执棋一方
	 *  @param cbm 当前棋局数据模型
	 *  @param top 需要获取的棋点个数
	 *  @return chessPointsScore 获取的一系列棋点坐标及棋点评分
	 */
	public static Vector<ChessData> getTopPointsScore(char chessColor,
			ChessBoardModel cbm, int top) {
		Vector<ChessData> chesses=new Vector<>(top);
		Vector<ChessData> temp=new Vector<>();
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
		Vector<Vector<Character>> chessInfo=dcbm.getChessInfo().chessInfo;
		for(int i = 0; i < 19; i++){
			for (int j = 0; j < 19; j++) {
				if(chessInfo.get(i).get(j)==ChessPoint.NOCHESS){
					temp.add(EvaluationFunction.getChessPointScore(chessColor, cbm, i, j));
				}
			}
		}
		Collections.sort(temp, new Comparator<ChessData>() {
			@Override
			public int compare(ChessData o1, ChessData o2) {
				int result=0;
				if(o1.getEvaluateScore()>o2.getEvaluateScore()){
					result=-1;
				}else if(o1.getEvaluateScore()<o2.getEvaluateScore()){
					result=1;
				}
				return result;
			}
		});
		for (int i = 0; i < top; i++) {
			chesses.add(temp.get(i));
		}
		return chesses;
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
