package com.jf.config;

/**
 *   ��������Ϸ��������Ϣ��
 *   
 *   @author ����
 */
public class GameConfig {
	/** ��սģʽѡ���е��˻���ս */
	public final static int PLAYERVSAI=0;
	/** ��սģʽѡ���е����˶�ս */
	public final static int PLAYERVSPLAYER=1;
	/** ����˫�������-��� */
	public final static int PLAYER=0;
	/** ����˫�������-AI */
	public final static int AI=1;
	/** �Ѷ�Ϊ����ʱ�Ĳ������������ */
	public final static int GENERATEMOVESNOVICEDEEP=2;
	/** �Ѷ�Ϊ��ͨʱ�Ĳ������������ */
	public final static int GENERATEMOVESNORMALDEEP=3;
	/** �Ѷ�Ϊ��Ӣʱ�Ĳ������������ */
	public final static int GENERATEMOVESHARDDEEP=4;
//GenerateMoves
	/** �߷�����ʱ��������������ʱȡ����ֵ��ߵ�Ŀ�� */
	public final static int GENERATEMOVESWIDTH=5;
//MainFrame
	/** ��ǰ��ֵĶ�սģʽ,Ĭ�����˻���ս */
	public static int VSWay=GameConfig.PLAYERVSAI;
	/** ��ǰAI�ĵȼ���Ĭ�������ֵȼ� */
	public static int AILevel=GameConfig.GENERATEMOVESNOVICEDEEP;
	/** ��ǰ�ڷ���ݣ�Ĭ������� */
	public static int BlackStatus=GameConfig.PLAYER;
	/** ��ǰ�׷���ݣ�Ĭ����AI */
	public static int WhiteStatus=GameConfig.AI; 
}
