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
 *  EvaluationFunction������������������ǰ��ֵľ���
 * 
 *  @author ����
 */
public class EvaluationFunction {
	/** ��·������׼�򣬸ý��ͨ���Ŵ��㷨�����Ż��õ� */
	public final static int[] SCOREOFROAD = {0, 17, 78, 141, 788, 1030, 10000 };
	/** ���е���· */
	public static Vector<Road> allRoads = new Vector<>(924);
	/** ���ӵ���Ч��·�� */
	public static int[] numberOfBlackRoad = new int[7];
	/** ���ӵ���Ч��·�� */
	public static int[] numberOfWhiteRoad = new int[7];
//  �෽��
	/**  
	 *  checkChessStatus����������鵱ǰ���״̬���������״̬�����¼ 
	 *  @param cbm ��ǰ���������ģ��
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
	 *  evaluateChessStatus��������������ֵľ�����Ϣ
	 *  @param chessColor Ҫ�������Ƶ�һ������������ɫ
	 *  @param cbm ��ǰ���������ģ��
	 *  @return current ��ǰ������ɫ��Ӧһ����ֵ���������
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
	 *  getChessPointScore��ȡ������㣬��Ӧ�������ʱ�ģ��������
	 *  @param chessColor ��Ҫ���������ӵ���ɫ
	 *  @param cbm ��ǰ���������ģ��
	 *  @param x ������ڵ�x����
	 *  @param y ������ڵ�y����
	 *  @return chessScore ��Ӧ���������Լ����������Ϣ
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
	 *  getTopPointsScore��ȡ����ֵΪǰtop�������
	 *  @param chessColor ��Ҫ��ȡ����ִ��һ��
	 *  @param cbm ��ǰ�������ģ��
	 *  @param top ��Ҫ��ȡ��������
	 *  @return chessPointsScore ��ȡ��һϵ��������꼰�������
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
	 *  isGameOver �����������ģ���ж�����Ƿ����
	 *  @param cbm �������ģ��
	 *  @return result �жϽ����true������ǰ��ֽ�����false(Ĭ��)������ǰ���δ����
	 */
	public static boolean isGameOver(ChessBoardModel cbm){
		boolean result=false;
		checkChessStatus(cbm);
		if(numberOfBlackRoad[6]>0||numberOfWhiteRoad[6]>0)
			result=true;
		return result;
	}
}
