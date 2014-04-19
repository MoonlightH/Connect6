package com.jf.ui;

import java.awt.Desktop;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Vector;

import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JButton;

import com.jf.algorithm.GenerateMoves;
import com.jf.algorithm.SearchAlgorithm;
import com.jf.bean.ChessData;
import com.jf.bean.Move;
import com.jf.config.GameConfig;
import com.jf.ui.model.ChessBoardModel;
import com.jf.ui.model.DefaultChessBoardModel;

/**
 *  ������������
 *  
 *  @author ����
 */
public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	/**
	 *  �޲ι��캯��,ʹ��Ĭ�ϵ���������ģ����ʵ��������
	 *  
	 */
	public MainFrame() {
		setTitle("������");
		setIconImage(new ImageIcon("./images/icon.png").getImage());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(880, 700);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		// ������Ĺ��ö���
		Font myFont = new Font("΢���ź�", Font.PLAIN, 12);

		/**
		 * ����˵���
		 */
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 900, 25);
		getContentPane().add(menuBar);

		// ��˵�������ӡ���Ϸ��
		JMenu game = new JMenu("��Ϸ");
		game.setFont(myFont);
		menuBar.add(game);

		JMenuItem openNewGame = new JMenuItem("���¾�");
		openNewGame.setFont(myFont);
		openNewGame.setAccelerator(KeyStroke.getKeyStroke('O',
				InputEvent.CTRL_DOWN_MASK));
		game.add(openNewGame);

		JSeparator separator_1 = new JSeparator();
		game.add(separator_1);

		JMenuItem loadGame = new JMenuItem("��ȡ�浵");
		loadGame.setFont(myFont);
		loadGame.setAccelerator(KeyStroke.getKeyStroke('L',
				InputEvent.CTRL_DOWN_MASK));
		game.add(loadGame);

		JMenuItem saveGame = new JMenuItem("����浵");
		saveGame.setFont(myFont);
		saveGame.setAccelerator(KeyStroke.getKeyStroke('S',
				InputEvent.CTRL_DOWN_MASK));
		game.add(saveGame);

		JSeparator separator_2 = new JSeparator();
		game.add(separator_2);

		JMenuItem quitGame = new JMenuItem("�뿪");
		quitGame.setFont(myFont);
		quitGame.setAccelerator(KeyStroke.getKeyStroke('X',
				InputEvent.CTRL_DOWN_MASK));
		quitGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		game.add(quitGame);

		// ��˵�������ӡ�����ģʽ��
		JMenu chessModel = new JMenu("����ģʽ");
		chessModel.setFont(myFont);
		menuBar.add(chessModel);

		JMenuItem start = new JMenuItem("��ʼ");
		start.setFont(myFont);
		start.setAccelerator(KeyStroke.getKeyStroke('P',
				InputEvent.SHIFT_DOWN_MASK));
		chessModel.add(start);

		JMenuItem stop = new JMenuItem("ֹͣ");
		stop.setFont(myFont);
		stop.setAccelerator(KeyStroke.getKeyStroke('S',
				InputEvent.SHIFT_DOWN_MASK));
		chessModel.add(stop);

		JSeparator separator_3 = new JSeparator();
		chessModel.add(separator_3);

		JMenuItem retract = new JMenuItem("����");
		retract.setFont(myFont);
		retract.setAccelerator(KeyStroke.getKeyStroke('Z',
				InputEvent.CTRL_DOWN_MASK));
		chessModel.add(retract);

		// ��˵�������ӡ����ڡ�
		JMenu about = new JMenu("����");
		about.setFont(myFont);
		menuBar.add(about);

		JMenuItem contect6 = new JMenuItem("������");
		contect6.setFont(myFont);
		contect6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		contect6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Desktop dt = Desktop.getDesktop();
				try {
					dt.browse(new URI("http://zh.wikipedia.org/wiki/������"));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
		about.add(contect6);

		/**
		 * �������н��������������
		 */
		final ChessBoard chessBoard = ChessBoard.getInstance();
		chessBoard.setBounds(10, 35, 621, 621);
		getContentPane().add(chessBoard);

		/**
		 * �������������
		 */
		JButton gstart = new JButton("��ʼ");
		gstart.setBounds(641, 54, 93, 23);
		getContentPane().add(gstart);

		JButton gstop = new JButton("ֹͣ");
		gstop.setBounds(744, 54, 93, 23);
		getContentPane().add(gstop);

		JPanel vsWays = new JPanel();
		TitledBorder tBorder_vs = BorderFactory.createTitledBorder("��սģʽ");
		tBorder_vs.setTitleFont(myFont);
		vsWays.setBorder(tBorder_vs);
		vsWays.setBounds(641, 87, 225, 46);
		getContentPane().add(vsWays);
		vsWays.setLayout(null);

		ButtonGroup vsWay = new ButtonGroup();

		JRadioButton playerVsAi = new JRadioButton("�˻���ս");
		playerVsAi.setBounds(26, 15, 80, 25);
		vsWays.add(playerVsAi);
		playerVsAi.setFont(myFont);
		playerVsAi.setSelected(true);
		vsWay.add(playerVsAi);

		JRadioButton playerVsPlayer = new JRadioButton("���˶�ս");
		playerVsPlayer.setBounds(123, 16, 80, 23);
		vsWays.add(playerVsPlayer);
		playerVsPlayer.setFont(myFont);
		vsWay.add(playerVsPlayer);

		JPanel black = new JPanel();
		black.setBounds(641, 143, 222, 70);
		TitledBorder tBorder_b = BorderFactory.createTitledBorder("�ڷ�");
		tBorder_b.setTitleFont(myFont);
		black.setBorder(tBorder_b);
		getContentPane().add(black);
		black.setLayout(null);

		final JRadioButton play_1 = new JRadioButton("���");
		play_1.setBounds(36, 16, 80, 23);
		play_1.setFont(myFont);
		play_1.setSelected(true);
		black.add(play_1);

		final JRadioButton play_2 = new JRadioButton("AI");
		play_2.setBounds(36, 41, 80, 23);
		play_2.setFont(myFont);
		black.add(play_2);

		ButtonGroup blackPlayer = new ButtonGroup();
		blackPlayer.add(play_1);
		blackPlayer.add(play_2);

		JLabel lblNewLabel = new JLabel("00:00:00");
		lblNewLabel.setBounds(122, 20, 54, 15);
		black.add(lblNewLabel);

		JPanel white = new JPanel();
		white.setBounds(641, 212, 222, 70);
		TitledBorder tBorder_w = BorderFactory.createTitledBorder("�׷�");
		tBorder_w.setTitleFont(myFont);
		white.setBorder(tBorder_w);
		getContentPane().add(white);
		white.setLayout(null);

		final JRadioButton play_3 = new JRadioButton("���");
		play_3.setBounds(36, 16, 80, 23);
		play_3.setFont(myFont);
		white.add(play_3);

		final JRadioButton play_4 = new JRadioButton("AI");
		play_4.setBounds(36, 41, 80, 23);
		play_4.setFont(myFont);
		play_4.setSelected(true);
		white.add(play_4);

		ButtonGroup whitePlayer = new ButtonGroup();
		whitePlayer.add(play_3);
		whitePlayer.add(play_4);

		JLabel label = new JLabel("00:00:00");
		label.setBounds(122, 20, 54, 15);
		white.add(label);

		final JLabel lblAi = new JLabel("AI�ȼ�");
		lblAi.setBounds(672, 292, 42, 15);
		getContentPane().add(lblAi);

		final JComboBox<String> aiLevel = new JComboBox<String>();
		aiLevel.setModel(new DefaultComboBoxModel<String>(new String[] { "����",
				"��ͨ", "��Ӣ" }));
		aiLevel.setBounds(724, 289, 78, 21);
		getContentPane().add(aiLevel);

		final JProgressBar thinking = new JProgressBar();
		thinking.setBounds(641, 317, 222, 14);
		getContentPane().add(thinking);

		JTextArea chessManualShower = new JTextArea();
		chessManualShower.setBounds(641, 341, 222, 148);
		getContentPane().add(chessManualShower);

		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(641, 499, 225, 157);
		getContentPane().add(textArea_1);
		////////////////////////////////////////////////////
		gstart.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ChessBoard chessBoard=ChessBoard.getInstance();
				DefaultChessBoardModel dcbm=(DefaultChessBoardModel)chessBoard.getModel();
				Move bestMove=SearchAlgorithm.getNextMoves(dcbm);
				for(ChessData chessData:bestMove.getChessDataArray()){
					dcbm.addChess(chessData);
				}
			}
		});
		
		gstop.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ChessBoardModel cbm=ChessBoard.getInstance().getModel();
				DefaultChessBoardModel dcbm=(DefaultChessBoardModel)cbm;
				char chessColor=dcbm.getNextStepChessColor();
				dcbm.addChess(new ChessData(1,2, chessColor));
			}
		});
		/////////////////////////////////////////////////////////////////////////
		// Ϊ��սģʽ��ѡ��ť����¼�����
		// �˻���ս����ѡ��AI�ȼ�
		playerVsAi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				play_1.setText("���");
				play_2.setText("AI");
				play_3.setText("���");
				play_4.setText("AI");
				lblAi.setVisible(true);
				aiLevel.setVisible(true);
				thinking.setVisible(true);
				GameConfig.VSWay=GameConfig.PLAYERVSAI;
			}
		});
		// ���˶�սʱ����ѡ��AI�ȼ�
		playerVsPlayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				play_1.setText("���1");
				play_2.setText("���2");
				play_3.setText("���1");
				play_4.setText("���2");
				lblAi.setVisible(false);
				aiLevel.setVisible(false);
				thinking.setVisible(false);
				GameConfig.VSWay=GameConfig.PLAYERVSPLAYER;
			}
		});

		// Ϊ����ѡ��ѡ��ť����¼�����,ʹ���û�����
		play_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				play_3.setSelected(false);
				play_4.setSelected(true);
			}
		});

		play_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				play_4.setSelected(false);
				play_3.setSelected(true);
			}
		});

		play_3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				play_1.setSelected(false);
				play_2.setSelected(true);
			}
		});

		play_4.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				play_2.setSelected(false);
				play_1.setSelected(true);
			}
		});
	}
}
