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
 *  六子棋主窗体
 *  
 *  @author 蒋鹏
 */
public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	/**
	 *  无参构造函数,使用默认的棋盘数据模型来实例化棋盘
	 *  
	 */
	public MainFrame() {
		setTitle("六子棋");
		setIconImage(new ImageIcon("./images/icon.png").getImage());
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(880, 700);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);

		// 主窗体的公用对象
		Font myFont = new Font("微软雅黑", Font.PLAIN, 12);

		/**
		 * 程序菜单栏
		 */
		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 900, 25);
		getContentPane().add(menuBar);

		// 向菜单栏中添加“游戏”
		JMenu game = new JMenu("游戏");
		game.setFont(myFont);
		menuBar.add(game);

		JMenuItem openNewGame = new JMenuItem("开新局");
		openNewGame.setFont(myFont);
		openNewGame.setAccelerator(KeyStroke.getKeyStroke('O',
				InputEvent.CTRL_DOWN_MASK));
		game.add(openNewGame);

		JSeparator separator_1 = new JSeparator();
		game.add(separator_1);

		JMenuItem loadGame = new JMenuItem("读取存档");
		loadGame.setFont(myFont);
		loadGame.setAccelerator(KeyStroke.getKeyStroke('L',
				InputEvent.CTRL_DOWN_MASK));
		game.add(loadGame);

		JMenuItem saveGame = new JMenuItem("保存存档");
		saveGame.setFont(myFont);
		saveGame.setAccelerator(KeyStroke.getKeyStroke('S',
				InputEvent.CTRL_DOWN_MASK));
		game.add(saveGame);

		JSeparator separator_2 = new JSeparator();
		game.add(separator_2);

		JMenuItem quitGame = new JMenuItem("离开");
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

		// 向菜单栏中添加“下棋模式”
		JMenu chessModel = new JMenu("下棋模式");
		chessModel.setFont(myFont);
		menuBar.add(chessModel);

		JMenuItem start = new JMenuItem("开始");
		start.setFont(myFont);
		start.setAccelerator(KeyStroke.getKeyStroke('P',
				InputEvent.SHIFT_DOWN_MASK));
		chessModel.add(start);

		JMenuItem stop = new JMenuItem("停止");
		stop.setFont(myFont);
		stop.setAccelerator(KeyStroke.getKeyStroke('S',
				InputEvent.SHIFT_DOWN_MASK));
		chessModel.add(stop);

		JSeparator separator_3 = new JSeparator();
		chessModel.add(separator_3);

		JMenuItem retract = new JMenuItem("悔棋");
		retract.setFont(myFont);
		retract.setAccelerator(KeyStroke.getKeyStroke('Z',
				InputEvent.CTRL_DOWN_MASK));
		chessModel.add(retract);

		// 向菜单栏中添加“关于”
		JMenu about = new JMenu("关于");
		about.setFont(myFont);
		menuBar.add(about);

		JMenuItem contect6 = new JMenuItem("六子棋");
		contect6.setFont(myFont);
		contect6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		contect6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Desktop dt = Desktop.getDesktop();
				try {
					dt.browse(new URI("http://zh.wikipedia.org/wiki/六子棋"));
				} catch (IOException | URISyntaxException e1) {
					e1.printStackTrace();
				}
			}
		});
		about.add(contect6);

		/**
		 * 程序运行结果表现载体棋盘
		 */
		final ChessBoard chessBoard = ChessBoard.getInstance();
		chessBoard.setBounds(10, 35, 621, 621);
		getContentPane().add(chessBoard);

		/**
		 * 程序控制器部分
		 */
		JButton gstart = new JButton("开始");
		gstart.setBounds(641, 54, 93, 23);
		getContentPane().add(gstart);

		JButton gstop = new JButton("停止");
		gstop.setBounds(744, 54, 93, 23);
		getContentPane().add(gstop);

		JPanel vsWays = new JPanel();
		TitledBorder tBorder_vs = BorderFactory.createTitledBorder("对战模式");
		tBorder_vs.setTitleFont(myFont);
		vsWays.setBorder(tBorder_vs);
		vsWays.setBounds(641, 87, 225, 46);
		getContentPane().add(vsWays);
		vsWays.setLayout(null);

		ButtonGroup vsWay = new ButtonGroup();

		JRadioButton playerVsAi = new JRadioButton("人机对战");
		playerVsAi.setBounds(26, 15, 80, 25);
		vsWays.add(playerVsAi);
		playerVsAi.setFont(myFont);
		playerVsAi.setSelected(true);
		vsWay.add(playerVsAi);

		JRadioButton playerVsPlayer = new JRadioButton("人人对战");
		playerVsPlayer.setBounds(123, 16, 80, 23);
		vsWays.add(playerVsPlayer);
		playerVsPlayer.setFont(myFont);
		vsWay.add(playerVsPlayer);

		JPanel black = new JPanel();
		black.setBounds(641, 143, 222, 70);
		TitledBorder tBorder_b = BorderFactory.createTitledBorder("黑方");
		tBorder_b.setTitleFont(myFont);
		black.setBorder(tBorder_b);
		getContentPane().add(black);
		black.setLayout(null);

		final JRadioButton play_1 = new JRadioButton("玩家");
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
		TitledBorder tBorder_w = BorderFactory.createTitledBorder("白方");
		tBorder_w.setTitleFont(myFont);
		white.setBorder(tBorder_w);
		getContentPane().add(white);
		white.setLayout(null);

		final JRadioButton play_3 = new JRadioButton("玩家");
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

		final JLabel lblAi = new JLabel("AI等级");
		lblAi.setBounds(672, 292, 42, 15);
		getContentPane().add(lblAi);

		final JComboBox<String> aiLevel = new JComboBox<String>();
		aiLevel.setModel(new DefaultComboBoxModel<String>(new String[] { "新手",
				"普通", "精英" }));
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
		// 为对战模式单选按钮添加事件侦听
		// 人机对战可以选择AI等级
		playerVsAi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				play_1.setText("玩家");
				play_2.setText("AI");
				play_3.setText("玩家");
				play_4.setText("AI");
				lblAi.setVisible(true);
				aiLevel.setVisible(true);
				thinking.setVisible(true);
				GameConfig.VSWay=GameConfig.PLAYERVSAI;
			}
		});
		// 人人对战时不能选择AI等级
		playerVsPlayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				play_1.setText("玩家1");
				play_2.setText("玩家2");
				play_3.setText("玩家1");
				play_4.setText("玩家2");
				lblAi.setVisible(false);
				aiLevel.setVisible(false);
				thinking.setVisible(false);
				GameConfig.VSWay=GameConfig.PLAYERVSPLAYER;
			}
		});

		// 为对象选择单选按钮添加事件侦听,使得用户互斥
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
