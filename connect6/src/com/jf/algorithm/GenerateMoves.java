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
 *  GenerateMoves�߷������࣬���ݵ�ǰ��־���������ز������ڵ�
 *  
 *  @author ����
 */
public class GenerateMoves {
	/**
	 *  generateMoves���ɵ���ֵ����к��ʵ��߷��غ�
	 *  @param chessBoardModel ��Ҫ�����߷��غϵ��������ģ��
	 *  @return moves �������ɵ��߷��غ�����
	 */
	public static Vector<Move> generateMoves(ChessBoardModel chessBoardModel){
		DefaultChessBoardModel defaultChessBoardModel=(DefaultChessBoardModel)chessBoardModel;
		HashSet<ChessData> reserveChessPointData=getReserveChessPoints(defaultChessBoardModel);
		Vector<Move> moves=new Vector<>();
		//ͨ����������ʵ��һ������غ��е��ظ�����������ʹ�����ɵĽڵ����һ��
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
	 *  getReserveChessPoints��������ȡ��ѡ�������������һ���߷�(��ѡ���Ļ�ȡ����������������ɨ��5����Ч���)
	 *  @param chessBoardModel ��Ҫ���ɱ�ѡ�����������ģ��
	 *  @return reserveChessPoints ��ѡ�������
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
	 *  validateChessPoint��������⵱ǰ����Ƿ���Ч
	 *  @param chessData ��Ҫ������������ģ��
	 *  @param chessBoardModel �����������������ģ��
	 *  @return result �����Ƿ���Ч����Ч���Ϊtrue,��Ч��Ϊfalse
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