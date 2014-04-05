package com.jf.bean;

import java.util.Vector;

import com.jf.ui.ChessPoint;
/**
 *  棋盘中的棋子信息类，用来保存棋盘中的棋子信息
 *  
 *  @author 蒋鹏
 */
public class ChessInfo {
	
	public Vector<Vector<Character>> chessInfo=new Vector<>(19);
	
	public ChessInfo() {
		for(int i=0;i<19;i++){
			Vector<Character> v=new Vector<>(19);
			for(int j=0;j<19;j++){
				v.add(ChessPoint.NOCHESS);
			}
			this.chessInfo.add(v);
		}
	}
	
	@SuppressWarnings("unchecked")
	public ChessInfo(Vector<Vector<Character>> oldChessInfo){
		for (Vector<Character> temp : oldChessInfo) {
			chessInfo.add((Vector<Character>) temp.clone());
		}
	}
	
	public ChessInfo deepClone(){
		ChessInfo newChessInfo=new ChessInfo(this.chessInfo);
		return newChessInfo;
	}
}
