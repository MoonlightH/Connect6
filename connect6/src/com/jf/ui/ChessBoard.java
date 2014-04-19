package com.jf.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.jf.bean.ChessData;
import com.jf.ui.event.ChessBoardModelEvent;
import com.jf.ui.event.ChessBoardModelListener;
import com.jf.ui.model.ChessBoardModel;
import com.jf.ui.model.DefaultChessBoardModel;

/**
 *  棋盘类，在全部应用中棋盘只有一个所以chessBoard应该为单例类，
 *  用来创建一个棋盘对象
 *  
 *  @author 蒋鹏
 */
public class ChessBoard extends JPanel implements ChessBoardModelListener{
	
	private static final long serialVersionUID = 1L;
	/** 棋盘类变量  */
	private static ChessBoard instance=null;
//
//  实例变量
//
	/** 棋点数组变量 */
	private ChessPoint[][] chessPoints=null;
	/** 棋盘数据模型变量 */
	private ChessBoardModel dataModel;
//
//  构造方法
//
	/**
	 *  棋盘类的无参构造方法，用默认棋盘数据模型实例化棋盘对象
	 */
	private ChessBoard() {
		this.setLayout(null);
		if(dataModel==null){
			setModel(new DefaultChessBoardModel());
		}
		chessPoints= new ChessPoint[19][19];
		int x = 16, y = 16;
		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++) {
				chessPoints[i][j]=new ChessPoint(i,j);
				chessPoints[i][j].setBounds(x+31*j, y+31*i, 30, 30);
				this.add(chessPoints[i][j]);
			}
		}
	}
//
//  类方法
//
	/**
	 *  getInstance方法，用来获取棋盘类的唯一对象
	 *  @return instance 棋盘类对象 
	 */
	public static ChessBoard getInstance(){
		if(instance==null){
			instance=new ChessBoard();
		}
		return instance;
	}
//
//  普通方法
//
	/**
	 *  重写paintComponent方法，来为棋盘对象绘制背景图片
	 */
	@Override
	protected void paintComponent(Graphics g) {
		ImageIcon icon=new ImageIcon("./images/chessboard.png");
		Image bgImage=icon.getImage();
		g.drawImage(bgImage, 0, 0, icon.getIconWidth(),icon.getIconHeight(),icon.getImageObserver());
	}
	/**
	 *  实现chessBoardModelListener接口的方法，用来处理chessBoardModel事件
	 *  @param e 棋盘数据模型事件
	 */
	@Override
	public void chessBoardChanged(ChessBoardModelEvent e) {
		if(e.getUpdateWay()==ChessBoardModelEvent.UPDATECHESSPOINT){
			ChessData chessData = e.getChessData();
			int x=chessData.getY()-1;
			int y=chessData.getX()-1;
			chessPoints[x][y].setChessType(chessData.getChessColor());
		}
		if(e.getUpdateWay()==ChessBoardModelEvent.UPDATECHESSMODEL){
			Vector<ChessData> chessDataArray=e.getChessDataArray();
			for (ChessData chessData : chessDataArray) {
				int x=chessData.getY()-1;
				int y=chessData.getX()-1;
				chessPoints[x][y].setChessType(chessData.getChessColor());
			}
		}
	}
//
//  查询、设置变量值的方法
//
	/**
	 *  为棋盘对象设置新的棋盘数据模型，并移除旧模型的事件侦听，
	 *  为新数据模型添加数据模型事件侦听
	 *  @param dataModel 棋盘对象的新数据源
	 *  @exception IllegalArgumentException 如果新模型为空
	 */
	public void setModel(ChessBoardModel dataModel) {
		if(dataModel==null){
			throw new IllegalArgumentException("cann't set a null chessBoardModel");
		}
		if(this.dataModel!=dataModel){
			ChessBoardModel old=this.dataModel;
			Vector<ChessData> changedChessDataArray=new Vector<>(50);
			Hashtable<Integer, ChessData> newChessDataTable=((DefaultChessBoardModel)dataModel).getChessDataTable();
			for(Enumeration<Integer> newKeys=newChessDataTable.keys();newKeys.hasMoreElements();){
				Integer key=newKeys.nextElement();
				ChessData newChessData=newChessDataTable.get(key);
				changedChessDataArray.add(newChessData);
			}
			if(old!=null){
				//移除添加的事件侦听器
				old.removeChessBoardModelListener(this);
				Hashtable<Integer, ChessData> oldChessDataTable=((DefaultChessBoardModel)old).getChessDataTable();
				for(Enumeration<Integer> oldKeys=oldChessDataTable.keys();oldKeys.hasMoreElements();){
					Integer key=oldKeys.nextElement();
					ChessData newChessData=newChessDataTable.get(key);
					if(newChessData==null){
						changedChessDataArray.add(new ChessData(key/100,key%100,ChessPoint.NOCHESS));
					}
				}
			}
			this.dataModel=dataModel;
			this.dataModel.addChessBoardModelListener(this);
			chessBoardChanged(new  ChessBoardModelEvent(this.dataModel,changedChessDataArray));
		}
	}
	
	/**
	 *  获取棋盘对象的棋盘数据模型
	 *  @return dataModel 返回棋盘数据模型对象
	 */
	public ChessBoardModel getModel(){
		return dataModel;
	}
}
