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
 *  �����࣬��ȫ��Ӧ��������ֻ��һ������chessBoardӦ��Ϊ�����࣬
 *  ��������һ�����̶���
 *  
 *  @author ����
 */
public class ChessBoard extends JPanel implements ChessBoardModelListener{
	
	private static final long serialVersionUID = 1L;
	/** ���������  */
	private static ChessBoard instance=null;
//
//  ʵ������
//
	/** ���������� */
	private ChessPoint[][] chessPoints=null;
	/** ��������ģ�ͱ��� */
	private ChessBoardModel dataModel;
//
//  ���췽��
//
	/**
	 *  ��������޲ι��췽������Ĭ����������ģ��ʵ�������̶���
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
//  �෽��
//
	/**
	 *  getInstance������������ȡ�������Ψһ����
	 *  @return instance ��������� 
	 */
	public static ChessBoard getInstance(){
		if(instance==null){
			instance=new ChessBoard();
		}
		return instance;
	}
//
//  ��ͨ����
//
	/**
	 *  ��дpaintComponent��������Ϊ���̶�����Ʊ���ͼƬ
	 */
	@Override
	protected void paintComponent(Graphics g) {
		ImageIcon icon=new ImageIcon("./images/chessboard.png");
		Image bgImage=icon.getImage();
		g.drawImage(bgImage, 0, 0, icon.getIconWidth(),icon.getIconHeight(),icon.getImageObserver());
	}
	/**
	 *  ʵ��chessBoardModelListener�ӿڵķ�������������chessBoardModel�¼�
	 *  @param e ��������ģ���¼�
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
//  ��ѯ�����ñ���ֵ�ķ���
//
	/**
	 *  Ϊ���̶��������µ���������ģ�ͣ����Ƴ���ģ�͵��¼�������
	 *  Ϊ������ģ���������ģ���¼�����
	 *  @param dataModel ���̶����������Դ
	 *  @exception IllegalArgumentException �����ģ��Ϊ��
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
				//�Ƴ���ӵ��¼�������
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
	 *  ��ȡ���̶������������ģ��
	 *  @return dataModel ������������ģ�Ͷ���
	 */
	public ChessBoardModel getModel(){
		return dataModel;
	}
}
