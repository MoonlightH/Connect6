package com.jp.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.jp.bean.Move;
import com.jp.config.GameConfig;
import com.jp.ui.ChessPoint;
import com.jp.ui.model.ChessBoardModel;
import com.jp.ui.model.DefaultChessBoardModel;

/**
 * GenerateMoves走法生成类，根据当前棋局局势生成相关博弈树节点
 * 
 * @author 蒋鹏
 */
public class GenerateMoves {
	/**
	 * generateMoves生成当棋局的所有合适的走法回合
	 * 
	 * @param chessBoardModel
	 *            需要生成走法回合的棋局数据模型
	 * 
	 * @return moves 返回生成的走法回合数组
	 */
	public static ArrayList<Move> generateMoves(ChessBoardModel chessBoardModel) {
		DefaultChessBoardModel dcbm = (DefaultChessBoardModel) chessBoardModel;
		ArrayList<Move> moves = new ArrayList<>();
		ArrayList<Integer> step1 = getTopPoint(dcbm);
		for (Integer coord1 : step1) {
			dcbm.makeChess(coord1);
			ArrayList<Integer> step2 = getTopPoint(dcbm);
			for (Integer coord2 : step2) {
				moves.add(new Move(coord1, coord2));
			}
			dcbm.unMakeChess();
		}
		return moves;
	}

	/**
	 * 根据局面获取评分高的棋点坐标
	 * 
	 * @param chessBoadrModel 当前棋局局势
	 *            
	 * @return coords 评分较高的棋点坐标
	 */
	public static ArrayList<Integer> getTopPoint(ChessBoardModel chessBoardModel) {
		ArrayList<Integer> coords = new ArrayList<>(
				GameConfig.GENERATEMOVESWIDTH);
		DefaultChessBoardModel dcbm = (DefaultChessBoardModel) chessBoardModel;
		char color = dcbm.getNextStepChessColor();
		HashMap<Integer, Integer> coordScore = new HashMap<>();
		HashSet<Integer> ReserveCoords = getReserveCoords(dcbm);
		for (Integer coord : ReserveCoords) {
			dcbm.makeChess(coord);
			int score = EvaluationFunction.evaluateChessStatus(color, dcbm);
			coordScore.put(coord, score);
			dcbm.unMakeChess();
		}
		List<Map.Entry<Integer, Integer>> coordScoreList = new ArrayList<Map.Entry<Integer, Integer>>(
				coordScore.entrySet());
		Collections.sort(coordScoreList,
				new Comparator<Map.Entry<Integer, Integer>>() {
					public int compare(Map.Entry<Integer, Integer> o1,
							Map.Entry<Integer, Integer> o2) {
						return (o2.getValue() - o1.getValue());
					}
				});
		for (int i = 0; i < GameConfig.GENERATEMOVESWIDTH; i++) {
			coords.add(coordScoreList.get(i).getKey());
		}
		return coords;
	}

	/**
	 * getReserveChessPoints方法，获取备选棋点用来生成下一步走法
	 * 
	 * @param chessBoardModel
	 *            需要生成备选棋点的棋局数据模型
	 * 
	 * @return ReserveCoords 备选棋点坐标集合
	 */
	private static HashSet<Integer> getReserveCoords(
			ChessBoardModel chessBoardModel) {
		DefaultChessBoardModel dcbm = (DefaultChessBoardModel) chessBoardModel;
		HashSet<Integer> ReserveCoords = new HashSet<>();
		char[][] compsitionData = dcbm.getCompositionData();
		int xMin = dcbm.getxMin() - 2;
		int xMax = dcbm.getxMax() + 2;
		int yMin = dcbm.getyMin() - 2;
		int yMax = dcbm.getyMax() + 2;
		xMin = xMin < 1 ? 1 : xMin;
		xMax = xMax > 19 ? 19 : xMax;
		yMin = yMin < 1 ? 1 : yMin;
		yMax = yMax > 19 ? 19 : yMax;
		for (int i = xMin; i <= xMax; i++) {
			for (int j = yMin; j <= yMax; j++) {
				if (compsitionData[j - 1][i - 1] == ChessPoint.NOCHESS) {
					ReserveCoords.add(i * 100 + j);
				}
			}
		}
		return ReserveCoords;
	}
}
