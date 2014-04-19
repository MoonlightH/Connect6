package com.jf.algorithm;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import com.jf.bean.ChessData;
import com.jf.bean.Move;
import com.jf.ui.model.ChessBoardModel;
import com.jf.ui.model.DefaultChessBoardModel;

/**
 *  GenerateMoves走法生成类，根据当前棋局局势生成相关博弈树节点
 *  
 *  @author 蒋鹏
 */
public class GenerateMoves {
	/**
	 *  generateMoves生成当棋局的所有合适的走法回合
	 *  @param chessBoardModel 需要生成走法回合的棋局数据模型
	 *  @return moves 返回生成的走法回合数组
	 */
	public static Vector<Move> generateMoves(ChessBoardModel chessBoardModel){
		DefaultChessBoardModel defaultChessBoardModel=(DefaultChessBoardModel)chessBoardModel;
		HashSet<ChessData> reserveChessPointData=getReserveChessPoints(defaultChessBoardModel);
		Vector<Move> moves=new Vector<>();
		//通过迭代器来实现一次下棋回合中的重复步数，可以使得生成的节点减少一半
		Iterator<ChessData> iStepOne=reserveChessPointData.iterator();
		while(iStepOne.hasNext()){
			ChessData cOne=iStepOne.next();
			iStepOne.remove();
			@SuppressWarnings("unchecked")
			Iterator<ChessData> iStepTwo=((HashSet<ChessData>) reserveChessPointData.clone()).iterator();
			while(iStepTwo.hasNext()){
				ChessData cTwo=iStepTwo.next();
				Move m=new Move(cOne,cTwo);
				moves.add(m);
			}
		}
		System.out.println(moves.size());
		return moves;
	}
	/**
	 *  getReserveChessPoints方法，获取备选棋点用来生成下一步走法(备选棋点的获取采用已下棋子向外扫描5个有效棋点)
	 *  @param chessBoardModel 需要生成备选棋点的棋局数据模型
	 *  @return reserveChessPoints 备选棋点数组
	 */
	public static HashSet<ChessData> getReserveChessPoints(ChessBoardModel chessBoardModel){
		DefaultChessBoardModel defaultChessBoardModel=(DefaultChessBoardModel)chessBoardModel;
		char reserveChessPointColor=defaultChessBoardModel.getNextStepChessColor();
		Hashtable<Integer, ChessData> chessDataTable=defaultChessBoardModel.getChessDataTable();
		HashSet<ChessData> reserveChessPoints=new HashSet<>(200);
		Set<Integer> chessDataKeys=chessDataTable.keySet();
		for (Integer integer : chessDataKeys) {
			int x=integer/100;
			int y=integer%100;
			for(int i=1;i<3;i++){
				int[] xArray={x-i,x,x+i,x+i,x+i,x,x-i,x-i};
				int[] yArray={y-i,y-i,y-i,y,y+i,y+i,y+i,y};
				for(int j=0;j<8;j++){
					ChessData tempCd=new ChessData(xArray[j],yArray[j],reserveChessPointColor);
					if(validtaeChessPoint(tempCd, defaultChessBoardModel)){
						reserveChessPoints.add(tempCd);
					}
				}
			}
		}
		return reserveChessPoints;
	}
	/**
	 *  validateChessPoint方法，检测当前棋点是否有效
	 *  @param chessData 需要检测的棋子数据模型
	 *  @param chessBoardModel 棋子所处的棋局数据模型
	 *  @return result 棋子是否有效，有效结果为true,无效则为false
	 */
	public static boolean validtaeChessPoint(ChessData chessData,ChessBoardModel chessBoardModel){
		DefaultChessBoardModel defaultChessBoardModel=(DefaultChessBoardModel)chessBoardModel;
		Hashtable<Integer, ChessData> chessDataTable=defaultChessBoardModel.getChessDataTable();
		int x=chessData.getX();
		int y=chessData.getY();
		boolean result=false;
		if(chessDataTable.get(x*100+y)==null){
			if(x>0 && x<20 && y>0 && y<20){
				result=true;
			}
		}
		return result;
	}
	
}