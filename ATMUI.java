import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ATMUI{
	/**
	*主方法
	*/
	public static void main(String [] args){
		ATMUI ui = new ATMUI();
		ui.initBO();
		ui.initFrame();
		ui.showLogin();
		
	}
	/**
	*初始业务对象
	*/
	public ATMBO bo = null;
	public void initBO(){
		bo = new ATMBO();
		
	}
	
	/**
	*初始化窗口
	*/
	public int width = 960;
	public int height = 720;
	//界面窗口
	public JFrame jFrame = null;
	//层叠容器
	public JLayeredPane layeredPane = null;
	//背景层
	public JPanel backLayer = null;
	//前景层
	public JPanel frontLayer = null;
	//前景层布局器
	public CardLayout cardLayout = null;
	
	public void initFrame(){
		jFrame = new JFrame("ATM触摸屏系统");
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(width,height));
		jFrame.add(layeredPane);
		jFrame.setResizable(false);
		jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		backLayer = new JPanel();
		((FlowLayout)backLayer.getLayout()).setHgap(0);
		((FlowLayout)backLayer.getLayout()).setVgap(0);
		backLayer.setSize(width,height);
		backLayer.setLocation(0, 0);
		JLabel bg = new JLabel(new ImageIcon("img/bg.jpg"));
		backLayer.add(bg);
		layeredPane.add(backLayer, new Integer(0));
		frontLayer = new JPanel();
		cardLayout = new CardLayout(0,0);
		frontLayer.setLayout(cardLayout);
		frontLayer.setOpaque(false);
		frontLayer.setSize(width,height);
		frontLayer.setLocation(0,0);
		layeredPane.add(frontLayer, new Integer(1));
		
		
		
	}
	/**
	*登录界面
	*/
	public JPanel loginPane = null;
	public JTextField loginCodeInput = null;
	public JPasswordField loginPassInput = null;
	public JLabel loginTipsLabel = null;
	public void showLogin(){
		if(loginPane == null){
			loginPane = new JPanel();
			loginPane.setOpaque(false);
			Box loginBox = Box.createVerticalBox();
			loginBox.add(Box.createVerticalStrut(180));
			JPanel welcome_panel = new JPanel();
			welcome_panel.setOpaque(false);
			JLabel welcome_label = new JLabel("欢迎使用海阁银行");
			welcome_label.setForeground(Color.WHITE);
			welcome_label.setFont(new Font("微软雅黑", Font.PLAIN, 30));
			welcome_panel.add(welcome_label);
			loginBox.add(welcome_panel);
			loginBox.add(Box.createVerticalStrut(30));
			JPanel code_panel = new JPanel();
			code_panel.setOpaque(false);
			JLabel code_label = new JLabel("请输入卡号：");
			code_label.setForeground(Color.WHITE);
			code_label.setFont(new Font("微软雅黑",Font.PLAIN, 25));
			code_panel.add(code_label);
			loginCodeInput = new JTextField(10);
			loginCodeInput.setFont(new Font("微软雅黑",Font.PLAIN, 25));
			code_panel.add(loginCodeInput);
			loginBox.add(code_panel);
			
			loginBox.add(Box.createVerticalStrut(20));
			
			
			JPanel pass_panel = new JPanel();
			pass_panel.setOpaque(false);
			JLabel pass_label = new JLabel("请输入密码： ");
			pass_label.setForeground(Color.WHITE);
			pass_label.setFont(new Font("微软雅黑",Font.PLAIN, 25));
			pass_panel.add(pass_label);
			loginPassInput = new JPasswordField(10);
			loginPassInput.setFont(new Font("微软雅黑",Font.PLAIN, 25));
			pass_panel.add(loginPassInput);
			loginBox.add(pass_panel);
			
			loginBox.add(Box.createVerticalStrut(30));
			JPanel btn_panel = new JPanel();
			btn_panel.setOpaque(false);
			JButton login_btn = new JButton("登 录");
			login_btn.setFont(new Font("微软雅黑",Font.PLAIN, 15));
			btn_panel.add(login_btn);
			JButton reset_btn = new JButton("重 置");
			reset_btn.setFont(new Font("微软雅黑",Font.PLAIN, 15));
			
			
			btn_panel.add(reset_btn);
			
			loginBox.add(btn_panel);
			
			loginBox.add(Box.createVerticalStrut(10));
			
			JPanel tips_panel = new JPanel();
			tips_panel.setOpaque(false);
			
			loginTipsLabel = new JLabel("");
			loginTipsLabel.setForeground(new Color(238,32,32));
			loginTipsLabel.setFont(new Font("微软雅黑",Font.PLAIN, 20));
			tips_panel.add(loginTipsLabel);
			loginBox.add(tips_panel);
			loginPane.add(loginBox);
			frontLayer.add("loginPane",loginPane);
			cardLayout.show(frontLayer,"loginPane");
			frontLayer.validate();
			loginCodeInput.requestFocus();
			
			reset_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					loginCodeInput.setText("");
					loginPassInput.setText("");
					loginTipsLabel.setText("");
					loginCodeInput.requestFocus();
					
				}
			});
			login_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					String code_str = loginCodeInput.getText();
					String pass_str = new String(loginPassInput.getPassword());
					if("".equals(code_str)){
						loginTipsLabel.setText("卡号不能为空！");
						loginCodeInput.requestFocus();
					}else if("".equals(pass_str)){
						loginTipsLabel.setText("密码不能为空！");
						loginPassInput.requestFocus();
					}else{
						int login_rtn = bo.doLogin(code_str,Integer.valueOf(pass_str));
						if (login_rtn == -1){
							showMenu();
						}else if(login_rtn == 3){
							showTunka();
						}else{
							loginCodeInput.setText("");
							loginPassInput.setText("");
							loginTipsLabel.setText("卡号或密码错误，请重新输入，您还有"
							+(3-login_rtn)+"次机会！");
							loginCodeInput.requestFocus();
						}
						
					}
				}
			});
			
			
			
			
			
			
		}else{
			cardLayout.show(frontLayer,"loginPane");
			loginCodeInput.setText("");
			loginPassInput.setText("");
			loginTipsLabel.setText("");
			loginCodeInput.requestFocus();
			
		}
		
		
	}
	/**
	*吞卡提示界面
	*/
	public JPanel tunkaPane = null;
	public void showTunka(){
		if(tunkaPane == null){
			tunkaPane = new JPanel();
			tunkaPane.setOpaque(false);
			Box tunkaBox = Box.createVerticalBox();
			tunkaBox.add(Box.createVerticalStrut(100));
			JPanel tunka_panel= new JPanel();
			tunka_panel.setOpaque(false);
			JLabel tunka_label = new JLabel("您已经超过3次输出机会，系统吞卡");
			tunka_label.setForeground(Color.WHITE);
			tunka_label.setFont(new Font("微软雅黑",Font.PLAIN,30));
			tunka_panel.add(tunka_label);
			tunkaBox.add(tunka_panel);
			tunkaBox.add(Box.createVerticalStrut(30));
			JPanel btn_panel = new JPanel();
			btn_panel.setOpaque(false);
			JButton tunka_btn = new JButton("确定");
			tunka_btn.setFont(new Font("微软雅黑",Font.PLAIN,15));
			btn_panel .add(tunka_btn);
			tunkaBox.add(btn_panel);
			tunkaPane.add(tunkaBox);
			frontLayer.add("tunkaPane",tunkaPane);
			cardLayout.show(frontLayer,"tunkaPane");
			frontLayer.validate();
			tunka_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					quit();
				}
			});
			
			
		}else{
			cardLayout.show(frontLayer,"tunkaPane");
		}
	}
	/**
	*主菜单页面
	*/
	public JPanel menuPane = null;
	public void showMenu(){
		if(menuPane == null){
			menuPane = new JPanel();
			menuPane.setOpaque(false);
			
			menuPane.setLayout(new BorderLayout());
			
			Box tipsBox = Box.createVerticalBox();
			menuPane.add(tipsBox,BorderLayout.NORTH);
			
			tipsBox.add(Box.createVerticalStrut(150));
			JLabel tip_label = new JLabel("请选择你需要的服务");
			tip_label.setForeground(Color.WHITE);
			tip_label.setFont(new Font("微软雅黑",Font.PLAIN,30));
			tip_label.setAlignmentX(Component.CENTER_ALIGNMENT);
			tipsBox.add(tip_label);
			
			Box menuLeft= Box.createVerticalBox();
			menuPane.add(menuLeft,BorderLayout.WEST);
			
			menuLeft.add(Box.createVerticalStrut(50));
			
			JButton chaxun_btn = new JButton("查询余额");
			chaxun_btn.setFont(new Font("微软雅黑",Font.PLAIN,25));
			menuLeft.add(chaxun_btn);
			
			menuLeft.add(Box.createVerticalStrut(100));
			
			JButton cunkuan_btn = new JButton("存 款");
			cunkuan_btn.setFont(new Font("微软雅黑",Font.PLAIN,25));
			menuLeft.add(cunkuan_btn);
			
			menuLeft.add(Box.createVerticalStrut(100));
			
			JButton qukuan_btn = new JButton("取款");
			qukuan_btn.setFont(new Font("微软雅黑",Font.PLAIN,25));
			menuLeft.add(qukuan_btn);
			
			Box menuRight = Box.createVerticalBox();
			menuPane.add(menuRight,BorderLayout.EAST);
			
			
			menuRight.add(Box.createVerticalStrut(50));
			
			JButton xiugai_btn = new JButton("修改密码");
			xiugai_btn.setFont(new Font("微软雅黑",Font.PLAIN,25));
			xiugai_btn.setAlignmentX(Component.RIGHT_ALIGNMENT);
			menuRight.add(xiugai_btn);
			
			
			menuRight.add(Box.createVerticalStrut(100));
			
			JButton quit_btn = new JButton("退 卡");
			quit_btn.setFont(new Font("微软雅黑",Font.PLAIN, 25));
			quit_btn.setAlignmentX(Component.RIGHT_ALIGNMENT);
			menuRight.add(quit_btn);
			
			frontLayer.add("menuPane",menuPane);
			cardLayout.show(frontLayer,"menuPane");
			frontLayer.validate();
			
			chaxun_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					showChaxun();
				}
			});
			
			cunkuan_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					showcunkuan();
				}
			});
			
			qukuan_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					
					showQukuan();
					
				}
			});
			
			xiugai_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					showXiugai();
				}
			});
			
			quit_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					quit();
				}
				
			});
			
			
			
			
			
			
			
		
		
	}else{
		cardLayout.show(frontLayer,"menuPane");
		}
	}
	/**
	*查询界面
	*/
	//查询余额界面层容器
	public JPanel chaxunPane = null;
	public JLabel chaxun_label = null;
	public void showChaxun(){
		if(chaxunPane == null){
			//查询层未初始化时---
			//创建查询层容器对象
			chaxunPane = new JPanel();
			//把查询层的背景色设为透明
			chaxunPane.setOpaque(false);
			
			//往查询层容器中添加组件
			//创建一个垂直BOX容器
			Box chaxunBox = Box.createVerticalBox();
			
			//往垂直BOx添加180的高度距离
			chaxunBox.add(Box.createVerticalStrut(180));
			//创建一个查询信息的容器
			JPanel chaxun_panel = new JPanel();
			//背景色透明
			chaxun_panel.setOpaque(false);
			//创建查询余额信息“您的账户余额为+money元””
			double rtn = bo.doChaxun();
			chaxun_label = new JLabel("你的余额为:"+rtn);
			//设置信息字体
			chaxun_label.setFont(new Font("微软雅黑",Font.PLAIN,30));
			chaxun_label.setForeground(Color.WHITE);
			//把查询信息添加到查询信息容器中
			chaxun_panel.add(chaxun_label);
			//把查询信息容器添加到垂直Box容器中
			chaxunBox.add(chaxun_panel);
			
			//在垂直Box容器中添加30高度的距离高度的距离
			chaxunBox.add(Box.createVerticalStrut(30));
			
			//创建暗流容器
			JPanel btn_panel = new JPanel();
			//把按钮容器的背景色设成透明
			btn_panel.setOpaque(false);
			//创建返回按钮兵设置字体
			JButton chaxun_btn = new JButton("返回");
			chaxun_btn.setFont(new Font("微软雅黑",Font.PLAIN,15));
			//把返回按钮添加到按钮容器
			btn_panel.add(chaxun_btn);
			//把按钮容器添加到垂直Box
			chaxunBox.add(btn_panel);
			//把垂直容器添加到查询层容器中
			chaxunPane.add(chaxunBox);
			
			//显示查询提示界面层
			//把查询层添加到前景层容器中
			frontLayer.add("chaxunPane",chaxunPane);
			//是查询层在前景层容器中置于顶层显示
			cardLayout.show(frontLayer,"chaxunPane");
			//刷新前景层使其可视化
			frontLayer.validate();
			
			//监听按钮事件
			chaxun_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
			showMenu();
			
				}
		
			});
		
		
		}else{
		//查询余额界面层已经初始化时
		cardLayout.show(frontLayer,"chaxunPane");
		double rtn = bo.doChaxun();
		chaxun_label.setText("你的余额为:"+rtn);

		
		}
	}
	/**
	*存款界面
	*/
	//存款层容器
	public JPanel cunkuanPane = null;
	//存款输入框
	public JTextField cunkuanMoneyInput = null;
	//存款提示信息
	public JLabel cunkuanTipsLabel = null;
	public void showcunkuan(){
		cunkuanPane = new JPanel();
		cunkuanPane.setOpaque(false);
		Box cunkuanBox  = Box.createVerticalBox();
		cunkuanBox.add(Box.createVerticalStrut(180));
		JPanel money_panel = new JPanel();
		money_panel.setOpaque(false);
		JLabel money_label = new JLabel("请输入存款金额； ");
		money_label.setForeground(Color.WHITE);
		money_label.setFont(new Font("微软雅黑", Font.PLAIN,25));
		money_panel.add(money_label);
		cunkuanMoneyInput = new JTextField(10);
		cunkuanMoneyInput.setFont(new Font("微软雅黑",Font.PLAIN,25));
		money_panel.add(cunkuanMoneyInput);
		cunkuanBox.add(money_panel);
		cunkuanBox.add(Box.createVerticalStrut(30));
		JPanel btn_panel = new JPanel();
		btn_panel.setOpaque(false);
		JButton queding_btn = new JButton("确 定");
		queding_btn.setFont(new Font("微软雅黑",Font.PLAIN,15));
		btn_panel.add(queding_btn);
		JButton fanhui_btn = new JButton("返回");
		fanhui_btn.setFont(new Font("微软雅黑",Font.PLAIN,15));
		btn_panel.add(fanhui_btn);
		cunkuanBox.add(btn_panel);
		cunkuanBox.add(Box.createVerticalStrut(10));
		JPanel tips_panel = new JPanel();
		tips_panel.setOpaque(false);
		cunkuanTipsLabel = new JLabel("");
		cunkuanTipsLabel.setForeground(new Color(238,32,32));
		cunkuanTipsLabel.setFont(new Font("微软雅黑",Font.PLAIN,20));
		tips_panel.add(cunkuanTipsLabel);
		cunkuanBox.add(tips_panel);
		cunkuanPane.add(cunkuanBox);
		frontLayer.add("cunkuanPane",cunkuanPane);
		cardLayout.show(frontLayer,"cunkuanPane");
		frontLayer.validate();
		cunkuanMoneyInput.requestFocus();
		fanhui_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
			showMenu();
		
				}
		});
		queding_btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				String money_dou= cunkuanMoneyInput.getText();
				if("".equals(money_dou)){
					cunkuanTipsLabel.setText("存款不能为空！");
					cunkuanMoneyInput.requestFocus();
				}else{
					int cunkuan_rtn = bo.doCunkuan(Double.valueOf(money_dou));
					if(cunkuan_rtn == -2){
						showCunkuanSuccess();
					}else {
					cunkuanMoneyInput.setText("");
					cunkuanTipsLabel.setText("输入不为整百，请输入整百金额");
					cunkuanMoneyInput.requestFocus();
					}
				}
				
				
		
			}
		});
		
		
		
		
	}
	/**
	*存款成功提示界面
	*/
	public JPanel cunkuanSuccessPane = null;
	public void showCunkuanSuccess(){
		if(cunkuanSuccessPane == null){
			cunkuanSuccessPane = new JPanel();
			cunkuanSuccessPane.setOpaque(false);
			Box cunkuanSuccessBox = Box.createVerticalBox();
			cunkuanSuccessBox.add(Box.createVerticalStrut(180));
			JPanel cunkuanSuccess_panel= new JPanel();
			cunkuanSuccess_panel.setOpaque(false);
			JLabel cunkuanSuccess_label =new JLabel ("存款成功！");
			cunkuanSuccess_label.setForeground(Color.WHITE);
			cunkuanSuccess_label.setFont(new Font("微软雅黑",Font.PLAIN,30));
			cunkuanSuccess_panel.add(cunkuanSuccess_label);
			cunkuanSuccessBox.add(cunkuanSuccess_panel);
			cunkuanSuccessBox.add(Box.createVerticalStrut(30));
			JPanel btn_panel = new JPanel();
			btn_panel.setOpaque(false);
			JButton cunkuanSuccess_btn = new JButton("返回");
			cunkuanSuccess_btn.setFont(new Font("微软雅黑",Font.PLAIN,15));
			btn_panel.add(cunkuanSuccess_btn);
			cunkuanSuccessBox.add(btn_panel);
			cunkuanSuccessPane.add(cunkuanSuccessBox);
			frontLayer.add("cunkuanSuccessPane",cunkuanSuccessPane);
			cardLayout.show(frontLayer,"cunkuanSuccessPane");
			frontLayer.validate();
			cunkuanSuccess_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					showMenu();
				}
			});
			
		}else{
			cardLayout.show(frontLayer,"cunkuanSuccessPane");
		}
		
		
		
	}
	/**
	*取款界面
	*/public JPanel qukuanPane = null;
	//款输入框
	public JTextField qukuanMoneyInput = null;
	//提示信息
	public JLabel qukuanTipsLabel = null;
	public void showQukuan(){
		qukuanPane = new JPanel();
		qukuanPane.setOpaque(false);
		Box qukuanBox  = Box.createVerticalBox();
		qukuanBox.add(Box.createVerticalStrut(180));
		JPanel money_panel = new JPanel();
		money_panel.setOpaque(false);
		JLabel money_label = new JLabel("请输入取款金额； ");
		money_label.setForeground(Color.WHITE);
		money_label.setFont(new Font("微软雅黑", Font.PLAIN,25));
		money_panel.add(money_label);
		qukuanMoneyInput = new JTextField(10);
		qukuanMoneyInput.setFont(new Font("微软雅黑",Font.PLAIN,25));
		money_panel.add(qukuanMoneyInput);
		qukuanBox.add(money_panel);
		qukuanBox.add(Box.createVerticalStrut(30));
		JPanel btn_panel = new JPanel();
		btn_panel.setOpaque(false);
		JButton queding_btn = new JButton("确 定");
		queding_btn.setFont(new Font("微软雅黑",Font.PLAIN,15));
		btn_panel.add(queding_btn);
		JButton fanhui_btn = new JButton("返回");
		fanhui_btn.setFont(new Font("微软雅黑",Font.PLAIN,15));
		btn_panel.add(fanhui_btn);
		qukuanBox.add(btn_panel);
		qukuanBox.add(Box.createVerticalStrut(10));
		JPanel tips_panel = new JPanel();
		tips_panel.setOpaque(false);
		qukuanTipsLabel = new JLabel("");
		qukuanTipsLabel.setForeground(new Color(238,32,32));
		qukuanTipsLabel.setFont(new Font("微软雅黑",Font.PLAIN,20));
		tips_panel.add(qukuanTipsLabel);
		qukuanBox.add(tips_panel);
		qukuanPane.add(qukuanBox);
		frontLayer.add("qukuanPane",qukuanPane);
		cardLayout.show(frontLayer,"qukuanPane");
		frontLayer.validate();
		qukuanMoneyInput.requestFocus();
		fanhui_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
			showMenu();
		
				}
		});
		queding_btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				String money1_dou= qukuanMoneyInput.getText();
				if("".equals(money1_dou)){
					qukuanTipsLabel.setText("金额不能为空！");
					qukuanMoneyInput.requestFocus();
					
				}else{
					int qukuan_rtn = bo.doQukuan(Double.valueOf(money1_dou));
					if(qukuan_rtn == -1 ){
						showQukuanSuccess();
					}else if(qukuan_rtn == -2){
						qukuanTipsLabel.setText("输入的金额超出余额");
						qukuanMoneyInput.requestFocus();
					}else{
						qukuanTipsLabel.setText("输入的金额不为整百");
						qukuanMoneyInput.requestFocus();
					}
					
				}
				
			}
			
		});
	}
		
		
		
		
	
	/**
	*取款成功界面
	*/
	public JPanel qukuanSuccessPane = null;
	public void showQukuanSuccess(){
		if(qukuanSuccessPane == null){
			qukuanSuccessPane = new JPanel();
			qukuanSuccessPane.setOpaque(false);
			Box qukuanSuccessBox = Box.createVerticalBox();
			qukuanSuccessBox.add(Box.createVerticalStrut(180));
			JPanel qukuanSuccess_panel= new JPanel();
			qukuanSuccess_panel.setOpaque(false);
			JLabel qukuanSuccess_label =new JLabel ("取款成功！");
			qukuanSuccess_label.setForeground(Color.WHITE);
			qukuanSuccess_label.setFont(new Font("微软雅黑",Font.PLAIN,30));
			qukuanSuccess_panel.add(qukuanSuccess_label);
			qukuanSuccessBox.add(qukuanSuccess_panel);
			qukuanSuccessBox.add(Box.createVerticalStrut(30));
			JPanel btn_panel = new JPanel();
			btn_panel.setOpaque(false);
			JButton qukuanSuccess_btn = new JButton("返回");
			qukuanSuccess_btn.setFont(new Font("微软雅黑",Font.PLAIN,15));
			btn_panel.add(qukuanSuccess_btn);
			qukuanSuccessBox.add(btn_panel);
			qukuanSuccessPane.add(qukuanSuccessBox);
			frontLayer.add("qukuanSuccessPane",qukuanSuccessPane);
			cardLayout.show(frontLayer,"qukuanSuccessPane");
			frontLayer.validate();
			qukuanSuccess_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					showMenu();
				}
			});
			
		}else{
			cardLayout.show(frontLayer,"qukuanSuccessPane");
		}
		
		
	}
	/**
	*密码修改界面
	*/
	public JPanel xiugaiPane = null;
	public JTextField mima1Input = null;
	public JTextField mima2Input = null;
	public JTextField mima3Input = null;
	public JLabel xiugaiTipsLabel = null;
	public void showXiugai(){
		xiugaiPane = new JPanel();
		xiugaiPane.setOpaque(false);
		Box xiugaiBox = Box.createVerticalBox();
		xiugaiBox.add(Box.createVerticalStrut(180));
		JPanel mima1_panel = new JPanel();
		mima1_panel.setOpaque(false);
		JLabel mima1_label = new JLabel("请输旧密码：");
		mima1_label.setForeground(Color.WHITE);
		mima1_label.setFont(new Font("微软雅黑",Font.PLAIN, 25));
		mima1_panel.add(mima1_label);
		mima1Input = new JTextField(10);
		mima1Input.setFont(new Font("微软雅黑",Font.PLAIN, 25));
		mima1_panel.add(mima1Input);
		xiugaiBox.add(mima1_panel);
		
		xiugaiBox.add(Box.createVerticalStrut(30));
		JPanel mima2_panel = new JPanel();
		mima2_panel.setOpaque(false);
		JLabel mima2_label = new JLabel("请输新密码：");
		mima2_label.setForeground(Color.WHITE);
		mima2_label.setFont(new Font("微软雅黑",Font.PLAIN, 25));
		mima2_panel.add(mima2_label);
		mima2Input = new JTextField(10);
		mima2Input.setFont(new Font("微软雅黑",Font.PLAIN, 25));
		mima2_panel.add(mima2Input);
		xiugaiBox.add(mima2_panel);
		
		xiugaiBox.add(Box.createVerticalStrut(30));
		JPanel mima3_panel = new JPanel();
		mima3_panel.setOpaque(false);
		JLabel mima3_label = new JLabel("请再次输入密码：");
		mima3_label.setForeground(Color.WHITE);
		mima3_label.setFont(new Font("微软雅黑",Font.PLAIN, 25));
		mima3_panel.add(mima3_label);
		mima3Input = new JTextField(10);
		mima3Input.setFont(new Font("微软雅黑",Font.PLAIN, 25));
		mima3_panel.add(mima3Input);
		xiugaiBox.add(mima3_panel);
		//设置按钮，提示，
		xiugaiBox.add(Box.createVerticalStrut(30));
		JPanel btn_panel = new JPanel();
		btn_panel.setOpaque(false);
		JButton queding_btn = new JButton("确 定");
		queding_btn.setFont(new Font("微软雅黑",Font.PLAIN,15));
		btn_panel.add(queding_btn);
		JButton fanhui_btn = new JButton("返回");
		fanhui_btn.setFont(new Font("微软雅黑",Font.PLAIN,15));
		btn_panel.add(fanhui_btn);
		xiugaiBox.add(btn_panel);
		xiugaiBox.add(Box.createVerticalStrut(10));
		JPanel tips_panel = new JPanel();
		tips_panel.setOpaque(false);
		xiugaiTipsLabel = new JLabel("");
		xiugaiTipsLabel.setForeground(new Color(238,32,32));
		xiugaiTipsLabel.setFont(new Font("微软雅黑",Font.PLAIN,20));
		tips_panel.add(xiugaiTipsLabel);
		xiugaiBox.add(tips_panel);
		xiugaiPane.add(xiugaiBox);
		frontLayer.add("xiugaiPane",xiugaiPane);
		cardLayout.show(frontLayer,"xiugaiPane");
		frontLayer.validate();
		mima1Input.requestFocus();
		fanhui_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
			showMenu();
		
				}
		});
		queding_btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				String mima1= mima1Input.getText();
				String mima2= mima2Input.getText();
				String mima3= mima3Input.getText();
				if("".equals(mima1)||"".equals(mima2)||"".equals(mima3)){
					xiugaiTipsLabel.setText("输入不能为空");
					mima1Input.requestFocus();
					
				}else{
					int xiugai_rtn = bo.doXiugai(Integer.valueOf(mima1),Integer.valueOf(mima2),Integer.valueOf(mima3));
					if(xiugai_rtn == -1){
						showXiugaiSuccess();
					}else if(xiugai_rtn == -2){
						mima2Input.setText("");
						mima3Input.setText("");
						xiugaiTipsLabel.setText("两次新密码输入不一致，请重新输入");
						mima2Input.requestFocus();
						
					}else{
						mima1Input.setText("");
						xiugaiTipsLabel.setText("原密码输入错误，请重新输入");
						mima1Input.requestFocus();
						
					}
				}
				
			}
			
		});
		
	}
		
		
		
		
		
	
	/**
	*xiugai成功界面
	*/
	public JPanel xiugaiSuccessPane = null;
	public void showXiugaiSuccess(){
		if(xiugaiSuccessPane == null){
			xiugaiSuccessPane = new JPanel();
			xiugaiSuccessPane.setOpaque(false);
			Box xiugaiSuccessBox = Box.createVerticalBox();
			xiugaiSuccessBox.add(Box.createVerticalStrut(180));
			JPanel xiugaiSuccess_panel= new JPanel();
			xiugaiSuccess_panel.setOpaque(false);
			JLabel xiugaiSuccess_label =new JLabel ("修改成功！");
			xiugaiSuccess_label.setForeground(Color.WHITE);
			xiugaiSuccess_label.setFont(new Font("微软雅黑",Font.PLAIN,30));
			xiugaiSuccess_panel.add(xiugaiSuccess_label);
			xiugaiSuccessBox.add(xiugaiSuccess_panel);
			xiugaiSuccessBox.add(Box.createVerticalStrut(30));
			JPanel btn_panel = new JPanel();
			btn_panel.setOpaque(false);
			JButton xiugaiSuccess_btn = new JButton("返回");
			xiugaiSuccess_btn.setFont(new Font("微软雅黑",Font.PLAIN,15));
			btn_panel.add(xiugaiSuccess_btn);
			xiugaiSuccessBox.add(btn_panel);
			xiugaiSuccessPane.add(xiugaiSuccessBox);
			frontLayer.add("xiugaiSuccessPane",xiugaiSuccessPane);
			cardLayout.show(frontLayer,"xiugaiSuccessPane");
			frontLayer.validate();
			xiugaiSuccess_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
					showMenu();
				}
			});
			
		}else{
			cardLayout.show(frontLayer,"xiugaiSuccessPane");
		}
		
	}
	/**
	*tuika
	*/
	public void quit(){
		initBO();
		showLogin();
		
	}
}