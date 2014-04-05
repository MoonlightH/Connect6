package com.jf.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

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
		Vector<Vector<Character>> temp=((DefaultChessBoardModel)e.getSource()).getChessInfo().chessInfo;
		if(e.getType()==ChessBoardModelEvent.UPDATECHESSPOINT){
			Vector<Integer> v=e.getLocation();
			int x=v.get(0);
			int y=v.get(1);
			chessPoints[x][y].setChessType(temp.get(x).get(y).charValue());
		}else if(e.getType()==ChessBoardModelEvent.UPDATEMODEL){
			Vector<Vector<Integer>> v=e.getLocations();
			for(Vector<Integer> t:v){
				int x=t.get(0);
				int y=t.get(1);
				chessPoints[x][y].setChessType(temp.get(x).get(y).charValue());
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
			Vector<Vector<Character>> newV=((DefaultChessBoardModel)dataModel).getChessInfo().chessInfo;
			//��¼�¾�ģ���в�������λ��
			Vector<Vector<Integer>> locations=new Vector<>();
			if(old!=null){
				Vector<Vector<Character>> oldV=((DefaultChessBoardModel)old).getChessInfo().chessInfo;
				old.removeChessBoardModelListener(this);
				for (int i = 0; i < 19; i++) {
					for (int j = 0; j < 19; j++) {
						if(oldV.get(i).get(j)!=newV.get(i).get(j)){
							Vector<Integer> temp=new Vector<>();
							temp.add(i);
							temp.add(j);
							locations.add(temp);
						}
					}
				}
			}else{
				for (int i = 0; i < 19; i++) {
					for (int j = 0; j < 19; j++) {
						if(newV.get(i).get(j)!=ChessPoint.NOCHESS ){
							Vector<Integer> temp=new Vector<>();
							temp.add(i);
							temp.add(j);
							locations.add(temp);
						}
					}
				}
			}
			this.dataModel=dataModel;
			this.dataModel.addChessBoardModelListener(this);
			chessBoardChanged(new  ChessBoardModelEvent(this.dataModel,locations));
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
