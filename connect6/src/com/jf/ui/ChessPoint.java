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
 *  ChessPoint ���������̱�ʾ���ӵ����
 *  
 *  @author ����
 */
public class ChessPoint extends JPanel {
	private static final long serialVersionUID = 1L;
	/** BLACKCHESS��ʾ��ǰ����Ǻ��� */
	public final static char BLACKCHESS='b';
	/** WHITECHESS��ʾ��ǰ����ǰ��� */
	public final static char WHITECHESS='w';
	/** NOCHESS��ʾ��ǰ���û������ */
	public final static char NOCHESS='n'; 
	/** BLACKICON��ʾ����ͼƬ���� */
	public final static ImageIcon BLACKICON=new ImageIcon("./images/blackchess.png");
	/** WHITEICON��ʾ����ͼƬ���� */
	public final static ImageIcon WHITEICON=new ImageIcon("./images/whitechess.png");
//
//  ʵ������
//
	/** ����㴦���ӵ����� */
	private char chessType = NOCHESS;
	/** ����������ϵ�x������ */
	private int x=0;
	/** ����������ϵ�y������ */
	private int y=0;
	/** ����¼������������������� */
	private final MouseAdapter ma=new MouseAdapter()  {
		@Override
		public void mouseEntered(MouseEvent e) {
			// ���������panelʱ��ʾ�߿�
			if(ChessPoint.this.chessType==NOCHESS)
				ChessPoint.this.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			// ������Ƴ�panelʱ�߿򲻿ɼ�
			ChessPoint.this.setBorder(null);
		}
		
		@Override
		public void mouseClicked(MouseEvent e) {
			ChessBoard currentChessBoard=ChessBoard.getInstance();
			DefaultChessBoardModel dcbm=(DefaultChessBoardModel)currentChessBoard.getModel();
			//�����ϵ�x����������Եõߵ�x,y��ֵ
			int x=ChessPoint.this.y+1;
			int y=ChessPoint.this.x+1;
			char chessColor=dcbm.getNextStepChessColor();
			dcbm.addChess(new ChessData(x,y,chessColor));
		}
	};
//
//  ��ѯ�����ñ����ķ���
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
//  ���췽��
//
	/**
	 *  �����Ĺ��췽��
	 */
	public ChessPoint(int x,int y) {
		this.setBorder(null);
		this.setOpaque(false);
		this.x=x;
		this.y=y;
		this.addMouseListener(ma);
	}
//
//  ��ͨ����
//
	/**
	 *  ��дpaintComponent����,������û�������������¼���������
	 *  �����Ƴ������¼�������
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
