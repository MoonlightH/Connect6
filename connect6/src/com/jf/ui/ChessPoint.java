package com.jf.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.jf.bean.ChessData;
import com.jf.ui.model.DefaultChessBoardModel;

/**
 *  ChessPoint 用来在棋盘表示棋子的落点
 *  
 *  @author 蒋鹏
 */
public class ChessPoint extends JPanel {
	private static final long serialVersionUID = 1L;
	/** BLACKCHESS表示当前棋点是黑子 */
	public final static char BLACKCHESS='b';
	/** WHITECHESS表示当前棋点是白子 */
	public final static char WHITECHESS='w';
	/** NOCHESS表示当前棋点没有棋子 */
	public final static char NOCHESS='n'; 
	/** BLACKICON表示黑子图片对象 */
	public final static ImageIcon BLACKICON=new ImageIcon("./images/blackchess.png");
	/** WHITEICON表示白子图片对象 */
	public final static ImageIcon WHITEICON=new ImageIcon("./images/whitechess.png");
//
//  实例变量
//
	/** 该棋点处棋子的类型 */
	private char chessType = NOCHESS;
	/** 棋点在棋盘上的x轴坐标 */
	private int x=0;
	/** 棋点在棋盘上的y轴坐标 */
	private int y=0;
	/** 鼠标事件侦听器的适配器变量 */
	private final MouseAdapter ma=new MouseAdapter()  {
		@Override
		public void mouseEntered(MouseEvent e) {
			// 当鼠标移入panel时显示边框
			if(ChessPoint.this.chessType==NOCHESS)
				ChessPoint.this.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// 当鼠标移除panel时边框不可见
			ChessPoint.this.setBorder(null);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			ChessBoard currentChessBoard=ChessBoard.getInstance();
			DefaultChessBoardModel dcbm=(DefaultChessBoardModel)currentChessBoard.getModel();
			//坐标上的x是纵向的所以得颠倒x,y的值
			int x=ChessPoint.this.y+1;
			int y=ChessPoint.this.x+1;
			char chessColor=dcbm.getNextStepChessColor();
			dcbm.addChess(new ChessData(x,y,chessColor));
		}
	};
//
//  查询、设置变量的方法
//
	public char getChessType() {
		return chessType;
	}
	public void setChessType(char chessType) {
		this.chessType = chessType;
		this.removeMouseListener(ma);
		this.setBorder(null);
		repaint();
		
	}	
//
//  构造方法
//
	/**
	 *  棋点类的构造方法
	 */
	public ChessPoint(int x,int y) {
		this.setBorder(null);
		this.setOpaque(false);
		this.x=x;
		this.y=y;
		this.addMouseListener(ma);
	}
//
//  普通方法
//
	/**
	 *  重写paintComponent方法,如果棋点没棋子则添加鼠标事件侦听器，
	 *  否则移除鼠标的事件侦听器
 	 */
	@Override
	protected void paintComponent(Graphics g) {
		if(this.chessType!=NOCHESS){
			ImageIcon icon=null;
			if(this.chessType==BLACKCHESS){
				icon=BLACKICON;
			}else if(this.chessType==WHITECHESS){
				icon=WHITEICON;
			}
			Image bgImage=icon.getImage();
			g.drawImage(bgImage, 0, 0, icon.getIconWidth(),icon.getIconHeight(),icon.getImageObserver());
		}
	}
}
