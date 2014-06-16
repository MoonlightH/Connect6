package com.jp.bean;

import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import com.jp.algorithm.SearchAlgorithm;
import com.jp.config.GameConfig;
import com.jp.ui.BlackTimer;
import com.jp.ui.ChessBoard;
import com.jp.ui.ChessPoint;
import com.jp.ui.MainFrame;
import com.jp.ui.WhiteTimer;
import com.jp.ui.model.ChessBoardModel;
import com.jp.ui.model.DefaultChessBoardModel;
/**
 * MoveCalculate类
 * @author 蒋鹏
 */
public class MoveCalculate extends SwingWorker<Move, Object> {
	@Override
	protected Move doInBackground() throws Exception {
		ChessBoardModel cbm=ChessBoard.getInstance().getModel();
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
		Move move = null;
		char currentRole=dcbm.getCurrentRole();
		WhiteTimer wt=WhiteTimer.getInstance();
		BlackTimer bt=BlackTimer.getInstance();
		if(currentRole==ChessPoint.BLACKCHESS){
			wt.stop();
			bt.start();
		}
		if(currentRole==ChessPoint.WHITECHESS){
			bt.stop();
			wt.start();
		}
		//如果是人机对战
		if(GameConfig.VSWay==GameConfig.PLAYERVSAI){
			publish(new Object());
			//玩家执黑，AI执白
			if(GameConfig.BlackStatus==GameConfig.PLAYER && GameConfig.WhiteStatus==GameConfig.AI){
				if(currentRole==ChessPoint.WHITECHESS){
					move=SearchAlgorithm.getNextMoves(dcbm);
				}
			}
			//AI执黑，玩家执白
			if(GameConfig.BlackStatus==GameConfig.AI && GameConfig.WhiteStatus==GameConfig.PLAYER){
				if(currentRole==ChessPoint.BLACKCHESS){
					if(dcbm.getAllChessNumber()==0){
						move=new Move(1010);
					}else{
						move=SearchAlgorithm.getNextMoves(dcbm);
					}
				}
			}
		}
		return move;
	}
	
	@Override
	protected void process(List<Object> chunks) {
		MainFrame.getInstance().getThinking().setIndeterminate(true);
	}
	
	@Override
	protected void done() {
		MainFrame mainFrame=MainFrame.getInstance();
		try {
			Move temp=get();
			mainFrame.getThinking().setIndeterminate(false);
			if(temp!=null){
				ChessBoardModel cbm=ChessBoard.getInstance().getModel();
				DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
				String chessManual=null;
				if(dcbm.getCurrentRole()==ChessPoint.WHITECHESS && GameConfig.WhiteStatus==GameConfig.AI){
					chessManual="白：";	
				}
				if(dcbm.getCurrentRole()==ChessPoint.BLACKCHESS && GameConfig.BlackStatus==GameConfig.AI){
					chessManual="黑：";	
				}
				for(int coord:temp.getCoordArray()){
					dcbm.addChess(coord);
					chessManual+="("+(coord/100)+","+(coord%100)+")";
				}
				mainFrame.getChessManualShower().append(chessManual+"\n");
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
