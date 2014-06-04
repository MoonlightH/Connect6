package com.jp.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.jp.ui.event.ChessBoardModelEvent;
import com.jp.ui.event.ChessBoardModelListener;
import com.jp.ui.model.ChessBoardModel;
import com.jp.ui.model.DefaultChessBoardModel;

/**
 * �����࣬��ȫ��Ӧ��������ֻ��һ������chessBoardӦ��Ϊ�����࣬
 * ��������һ�����̶���  
 * @author ����
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
				chessPoints[i][j]=new ChessPoint(j,i);
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
		DefaultChessBoardModel dcbm=(DefaultChessBoardModel)dataModel;
		if(e.getUpdateWay()==ChessBoardModelEvent.UPDATECHESSPOINT){
			int coord = e.getCoord();
			int x=coord/100;
			int y=coord%100;
			char color=dcbm.getChessColorByCoord(x, y);
			chessPoints[y-1][x-1].setChessType(color);
		}
		if(e.getUpdateWay()==ChessBoardModelEvent.UPDATECHESSMODEL){
			HashSet<Integer> coordSet=e.getCoordSet();
			for (int coord : coordSet) {
				int x=coord/100;
				int y=coord%100;
				char color=dcbm.getChessColorByCoord(x, y);
				chessPoints[y-1][x-1].setChessType(color);
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
			DefaultChessBoardModel oldModel=(DefaultChessBoardModel)this.dataModel;
			DefaultChessBoardModel newModel=(DefaultChessBoardModel)dataModel;
			char[][] newCompositionData=newModel.getCompositionData();
			HashSet<Integer> changedCoordSet=new HashSet<>(40);
			if(this.dataModel!=null){
				char[][] oldCompositionData=oldModel.getCompositionData();
				//�Ƴ���ӵ��¼�������
				oldModel.removeChessBoardModelListener(this);
				for (int i = 0; i < 19; i++) {
					for (int j = 0; j < 19; j++) {
						char newValue=newCompositionData[i][j];
						if(newValue!=ChessPoint.NOCHESS){
							changedCoordSet.add((j+1)*100+(i+1));
						}
						char oldValue=oldCompositionData[i][j];
						if(oldValue!=ChessPoint.NOCHESS){
							changedCoordSet.add((j+1)*100+(i+1));
						}
					}
				}
			}else{
				for (int i = 0; i < 19; i++) {
					for (int j = 0; j < 19; j++) {
						char newValue=newCompositionData[i][j];
						if(newValue!=ChessPoint.NOCHESS){
							changedCoordSet.add((j+1)*100+(i+1));
						}
					}
				}
			}
			this.dataModel=dataModel;
			this.dataModel.addChessBoardModelListener(this);
			chessBoardChanged(new  ChessBoardModelEvent(this.dataModel,changedCoordSet));
		}
	}
	
	/**
	 *  ��ȡ���̶������������ģ��
	 *  @return dataModel ������������ģ�Ͷ���
	 */
	public ChessBoardModel getModel(){
		return dataModel;
	}
	public ChessPoint[][] getChessPoints() {
		return chessPoints;
	}
}
