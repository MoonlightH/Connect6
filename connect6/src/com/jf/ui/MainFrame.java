package com.jf.ui;

import java.awt.Desktop;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JRadioButton;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JButton;

import com.jf.bean.Judge;
import com.jf.config.GameConfig;
import com.jf.ui.event.RoleChangeEvent;
import com.jf.ui.model.ChessBoardModel;
import com.jf.ui.model.DefaultChessBoardModel;

/**
 * 六子棋主窗体 
 * 
 * @author 蒋鹏
 */
public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private static MainFrame mainFrame;
	
	private JProgressBar thinking;
	
	private JTextArea chessManualShower;
	
	private JButton gstart;
	
	private JButton gstop;
	
	/**
	 * 无参构造函数,使用默认的棋盘数据模型来实例化棋盘  
	 */
	private MainFrame() {
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
		openNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChessBoard.getInstance().setModel(new DefaultChessBoardModel());
				chessManualShower.setText("");
			}
		});
		game.add(openNewGame);

		JSeparator separator_1 = new JSeparator();
		game.add(separator_1);

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

		final JMenuItem start = new JMenuItem("开始");
		start.setFont(myFont);
		start.setAccelerator(KeyStroke.getKeyStroke('P',
				InputEvent.SHIFT_DOWN_MASK));
		chessModel.add(start);

		JMenuItem stop = new JMenuItem("停止");
		stop.setFont(myFont);
		stop.setAccelerator(KeyStroke.getKeyStroke('S',
				InputEvent.SHIFT_DOWN_MASK));
		chessModel.add(stop);

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
		gstart = new JButton("开始");
		gstart.setBounds(641, 54, 93, 23);
		getContentPane().add(gstart);
		

		gstop = new JButton("停止");
		gstop.setBounds(744, 54, 93, 23);
		getContentPane().add(gstop);
		

		final JPanel vsWays = new JPanel();
		TitledBorder tBorder_vs = BorderFactory.createTitledBorder("对战模式");
		tBorder_vs.setTitleFont(myFont);
		vsWays.setBorder(tBorder_vs);
		vsWays.setBounds(641, 87, 225, 46);
		getContentPane().add(vsWays);
		vsWays.setLayout(null);

		ButtonGroup vsWay = new ButtonGroup();

		final JRadioButton playerVsAi = new JRadioButton("人机对战");
		playerVsAi.setBounds(26, 15, 80, 25);
		vsWays.add(playerVsAi);
		playerVsAi.setFont(myFont);
		playerVsAi.setSelected(true);
		vsWay.add(playerVsAi);
		playerVsAi.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GameConfig.VSWay=GameConfig.PLAYERVSAI;
			}
		});

		final JRadioButton playerVsPlayer = new JRadioButton("人人对战");
		playerVsPlayer.setBounds(123, 16, 80, 23);
		vsWays.add(playerVsPlayer);
		playerVsPlayer.setFont(myFont);
		vsWay.add(playerVsPlayer);
		playerVsPlayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GameConfig.VSWay=GameConfig.PLAYERVSPLAYER;
			}
		});

		final JPanel black = new JPanel();
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
		play_1.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JRadioButton jrb=(JRadioButton)e.getSource();
				if(jrb.isSelected()){
					GameConfig.BlackStatus=GameConfig.PLAYER;
				}
			}
		});

		final JRadioButton play_2 = new JRadioButton("AI");
		play_2.setBounds(36, 41, 80, 23);
		play_2.setFont(myFont);
		black.add(play_2);
		play_2.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JRadioButton jrb=(JRadioButton)e.getSource();
				if(jrb.isSelected()){
					GameConfig.BlackStatus=GameConfig.AI;
				}
			}
		});

		ButtonGroup blackPlayer = new ButtonGroup();
		blackPlayer.add(play_1);
		blackPlayer.add(play_2);

		final BlackTimer blackTimer = BlackTimer.getInstance();
		blackTimer.setBounds(122, 20, 54, 15);
		black.add(blackTimer);

		final JPanel white = new JPanel();
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
		play_3.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JRadioButton jrb=(JRadioButton)e.getSource();
				if(jrb.isSelected()){
					GameConfig.WhiteStatus=GameConfig.PLAYER;
				}
			}
		});

		final JRadioButton play_4 = new JRadioButton("AI");
		play_4.setBounds(36, 41, 80, 23);
		play_4.setFont(myFont);
		play_4.setSelected(true);
		white.add(play_4);
		play_4.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				JRadioButton jrb=(JRadioButton)e.getSource();
				if(jrb.isSelected()){
					GameConfig.WhiteStatus=GameConfig.AI;
				}
			}
		});

		ButtonGroup whitePlayer = new ButtonGroup();
		whitePlayer.add(play_3);
		whitePlayer.add(play_4);

		WhiteTimer whiteTimer = WhiteTimer.getInstance();
		whiteTimer.setBounds(122, 20, 54, 15);
		white.add(whiteTimer);

		final JLabel lblAi = new JLabel("AI等级");
		lblAi.setBounds(672, 292, 42, 15);
		getContentPane().add(lblAi);

		final JComboBox<String> aiLevel = new JComboBox<String>();
		aiLevel.setModel(new DefaultComboBoxModel<String>(new String[] { "新手",
				"普通", "精英" }));
		aiLevel.setBounds(724, 289, 78, 21);
		getContentPane().add(aiLevel);
		aiLevel.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				String item=e.getItem().toString();
				switch (item) {
				case "新手":
					GameConfig.AILevel=GameConfig.NOVICEDEEP;
					break;
				case "普通":
					GameConfig.AILevel=GameConfig.NORMALDEEP;
					break;
				case "精英":
					GameConfig.AILevel=GameConfig.HARDDEEP;
					break;
				}
			}
		});

		thinking = new JProgressBar();
		thinking.setBounds(641, 317, 222, 14);
		getContentPane().add(thinking);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(641, 341, 222, 306);
		getContentPane().add(scrollPane);
		
		chessManualShower = new JTextArea();
		chessManualShower.setEditable(false);
		chessManualShower.setBounds(641, 341, 222, 306);
		chessManualShower.setMargin(new Insets(3, 5, 3, 5));
		scrollPane.setViewportView(chessManualShower);
		
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
		
		gstart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gstart.setEnabled(false);
				start.setEnabled(false);
				playerVsAi.setEnabled(false);
				playerVsPlayer.setEnabled(false);
				play_1.setEnabled(false);
				play_2.setEnabled(false);
				play_3.setEnabled(false);
				play_4.setEnabled(false);
				aiLevel.setEnabled(false);
				ChessBoardModel cbm=ChessBoard.getInstance().getModel();
				Judge judge=Judge.getInstance();
				judge.init();
				judge.roleChanged(new RoleChangeEvent(cbm));
			}
		});
		
		gstop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gstart.setEnabled(true);
				start.setEnabled(true);
				playerVsAi.setEnabled(true);
				playerVsPlayer.setEnabled(true);
				play_1.setEnabled(true);
				play_2.setEnabled(true);
				play_3.setEnabled(true);
				play_4.setEnabled(true);
				aiLevel.setEnabled(true);
				Judge judge=Judge.getInstance();
				judge.destory();
			}
		});
		
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gstart.doClick();
			}
		});
		
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gstop.doClick();
			}
		});
		
	}
	
	public JProgressBar getThinking() {
		return thinking;
	}
	
	public JTextArea getChessManualShower() {
		return chessManualShower;
	}

	public JButton getGstop() {
		return gstop;
	}

	public static MainFrame getInstance(){
		if(mainFrame==null){
			mainFrame=new MainFrame();
		}
		return mainFrame;
	}
}
