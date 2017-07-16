import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class ATMUI{
	/**
	*������
	*/
	public static void main(String [] args){
		ATMUI ui = new ATMUI();
		ui.initBO();
		ui.initFrame();
		ui.showLogin();
		
	}
	/**
	*��ʼҵ�����
	*/
	public ATMBO bo = null;
	public void initBO(){
		bo = new ATMBO();
		
	}
	
	/**
	*��ʼ������
	*/
	public int width = 960;
	public int height = 720;
	//���洰��
	public JFrame jFrame = null;
	//�������
	public JLayeredPane layeredPane = null;
	//������
	public JPanel backLayer = null;
	//ǰ����
	public JPanel frontLayer = null;
	//ǰ���㲼����
	public CardLayout cardLayout = null;
	
	public void initFrame(){
		jFrame = new JFrame("ATM������ϵͳ");
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
	*��¼����
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
			JLabel welcome_label = new JLabel("��ӭʹ�ú�������");
			welcome_label.setForeground(Color.WHITE);
			welcome_label.setFont(new Font("΢���ź�", Font.PLAIN, 30));
			welcome_panel.add(welcome_label);
			loginBox.add(welcome_panel);
			loginBox.add(Box.createVerticalStrut(30));
			JPanel code_panel = new JPanel();
			code_panel.setOpaque(false);
			JLabel code_label = new JLabel("�����뿨�ţ�");
			code_label.setForeground(Color.WHITE);
			code_label.setFont(new Font("΢���ź�",Font.PLAIN, 25));
			code_panel.add(code_label);
			loginCodeInput = new JTextField(10);
			loginCodeInput.setFont(new Font("΢���ź�",Font.PLAIN, 25));
			code_panel.add(loginCodeInput);
			loginBox.add(code_panel);
			
			loginBox.add(Box.createVerticalStrut(20));
			
			
			JPanel pass_panel = new JPanel();
			pass_panel.setOpaque(false);
			JLabel pass_label = new JLabel("���������룺 ");
			pass_label.setForeground(Color.WHITE);
			pass_label.setFont(new Font("΢���ź�",Font.PLAIN, 25));
			pass_panel.add(pass_label);
			loginPassInput = new JPasswordField(10);
			loginPassInput.setFont(new Font("΢���ź�",Font.PLAIN, 25));
			pass_panel.add(loginPassInput);
			loginBox.add(pass_panel);
			
			loginBox.add(Box.createVerticalStrut(30));
			JPanel btn_panel = new JPanel();
			btn_panel.setOpaque(false);
			JButton login_btn = new JButton("�� ¼");
			login_btn.setFont(new Font("΢���ź�",Font.PLAIN, 15));
			btn_panel.add(login_btn);
			JButton reset_btn = new JButton("�� ��");
			reset_btn.setFont(new Font("΢���ź�",Font.PLAIN, 15));
			
			
			btn_panel.add(reset_btn);
			
			loginBox.add(btn_panel);
			
			loginBox.add(Box.createVerticalStrut(10));
			
			JPanel tips_panel = new JPanel();
			tips_panel.setOpaque(false);
			
			loginTipsLabel = new JLabel("");
			loginTipsLabel.setForeground(new Color(238,32,32));
			loginTipsLabel.setFont(new Font("΢���ź�",Font.PLAIN, 20));
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
						loginTipsLabel.setText("���Ų���Ϊ�գ�");
						loginCodeInput.requestFocus();
					}else if("".equals(pass_str)){
						loginTipsLabel.setText("���벻��Ϊ�գ�");
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
							loginTipsLabel.setText("���Ż�����������������룬������"
							+(3-login_rtn)+"�λ��ᣡ");
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
	*�̿���ʾ����
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
			JLabel tunka_label = new JLabel("���Ѿ�����3��������ᣬϵͳ�̿�");
			tunka_label.setForeground(Color.WHITE);
			tunka_label.setFont(new Font("΢���ź�",Font.PLAIN,30));
			tunka_panel.add(tunka_label);
			tunkaBox.add(tunka_panel);
			tunkaBox.add(Box.createVerticalStrut(30));
			JPanel btn_panel = new JPanel();
			btn_panel.setOpaque(false);
			JButton tunka_btn = new JButton("ȷ��");
			tunka_btn.setFont(new Font("΢���ź�",Font.PLAIN,15));
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
	*���˵�ҳ��
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
			JLabel tip_label = new JLabel("��ѡ������Ҫ�ķ���");
			tip_label.setForeground(Color.WHITE);
			tip_label.setFont(new Font("΢���ź�",Font.PLAIN,30));
			tip_label.setAlignmentX(Component.CENTER_ALIGNMENT);
			tipsBox.add(tip_label);
			
			Box menuLeft= Box.createVerticalBox();
			menuPane.add(menuLeft,BorderLayout.WEST);
			
			menuLeft.add(Box.createVerticalStrut(50));
			
			JButton chaxun_btn = new JButton("��ѯ���");
			chaxun_btn.setFont(new Font("΢���ź�",Font.PLAIN,25));
			menuLeft.add(chaxun_btn);
			
			menuLeft.add(Box.createVerticalStrut(100));
			
			JButton cunkuan_btn = new JButton("�� ��");
			cunkuan_btn.setFont(new Font("΢���ź�",Font.PLAIN,25));
			menuLeft.add(cunkuan_btn);
			
			menuLeft.add(Box.createVerticalStrut(100));
			
			JButton qukuan_btn = new JButton("ȡ��");
			qukuan_btn.setFont(new Font("΢���ź�",Font.PLAIN,25));
			menuLeft.add(qukuan_btn);
			
			Box menuRight = Box.createVerticalBox();
			menuPane.add(menuRight,BorderLayout.EAST);
			
			
			menuRight.add(Box.createVerticalStrut(50));
			
			JButton xiugai_btn = new JButton("�޸�����");
			xiugai_btn.setFont(new Font("΢���ź�",Font.PLAIN,25));
			xiugai_btn.setAlignmentX(Component.RIGHT_ALIGNMENT);
			menuRight.add(xiugai_btn);
			
			
			menuRight.add(Box.createVerticalStrut(100));
			
			JButton quit_btn = new JButton("�� ��");
			quit_btn.setFont(new Font("΢���ź�",Font.PLAIN, 25));
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
	*��ѯ����
	*/
	//��ѯ�����������
	public JPanel chaxunPane = null;
	public JLabel chaxun_label = null;
	public void showChaxun(){
		if(chaxunPane == null){
			//��ѯ��δ��ʼ��ʱ---
			//������ѯ����������
			chaxunPane = new JPanel();
			//�Ѳ�ѯ��ı���ɫ��Ϊ͸��
			chaxunPane.setOpaque(false);
			
			//����ѯ��������������
			//����һ����ֱBOX����
			Box chaxunBox = Box.createVerticalBox();
			
			//����ֱBOx���180�ĸ߶Ⱦ���
			chaxunBox.add(Box.createVerticalStrut(180));
			//����һ����ѯ��Ϣ������
			JPanel chaxun_panel = new JPanel();
			//����ɫ͸��
			chaxun_panel.setOpaque(false);
			//������ѯ�����Ϣ�������˻����Ϊ+moneyԪ����
			double rtn = bo.doChaxun();
			chaxun_label = new JLabel("������Ϊ:"+rtn);
			//������Ϣ����
			chaxun_label.setFont(new Font("΢���ź�",Font.PLAIN,30));
			chaxun_label.setForeground(Color.WHITE);
			//�Ѳ�ѯ��Ϣ��ӵ���ѯ��Ϣ������
			chaxun_panel.add(chaxun_label);
			//�Ѳ�ѯ��Ϣ������ӵ���ֱBox������
			chaxunBox.add(chaxun_panel);
			
			//�ڴ�ֱBox���������30�߶ȵľ���߶ȵľ���
			chaxunBox.add(Box.createVerticalStrut(30));
			
			//������������
			JPanel btn_panel = new JPanel();
			//�Ѱ�ť�����ı���ɫ���͸��
			btn_panel.setOpaque(false);
			//�������ذ�ť����������
			JButton chaxun_btn = new JButton("����");
			chaxun_btn.setFont(new Font("΢���ź�",Font.PLAIN,15));
			//�ѷ��ذ�ť��ӵ���ť����
			btn_panel.add(chaxun_btn);
			//�Ѱ�ť������ӵ���ֱBox
			chaxunBox.add(btn_panel);
			//�Ѵ�ֱ������ӵ���ѯ��������
			chaxunPane.add(chaxunBox);
			
			//��ʾ��ѯ��ʾ�����
			//�Ѳ�ѯ����ӵ�ǰ����������
			frontLayer.add("chaxunPane",chaxunPane);
			//�ǲ�ѯ����ǰ�������������ڶ�����ʾ
			cardLayout.show(frontLayer,"chaxunPane");
			//ˢ��ǰ����ʹ����ӻ�
			frontLayer.validate();
			
			//������ť�¼�
			chaxun_btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent ae){
			showMenu();
			
				}
		
			});
		
		
		}else{
		//��ѯ��������Ѿ���ʼ��ʱ
		cardLayout.show(frontLayer,"chaxunPane");
		double rtn = bo.doChaxun();
		chaxun_label.setText("������Ϊ:"+rtn);

		
		}
	}
	/**
	*������
	*/
	//��������
	public JPanel cunkuanPane = null;
	//��������
	public JTextField cunkuanMoneyInput = null;
	//�����ʾ��Ϣ
	public JLabel cunkuanTipsLabel = null;
	public void showcunkuan(){
		cunkuanPane = new JPanel();
		cunkuanPane.setOpaque(false);
		Box cunkuanBox  = Box.createVerticalBox();
		cunkuanBox.add(Box.createVerticalStrut(180));
		JPanel money_panel = new JPanel();
		money_panel.setOpaque(false);
		JLabel money_label = new JLabel("��������� ");
		money_label.setForeground(Color.WHITE);
		money_label.setFont(new Font("΢���ź�", Font.PLAIN,25));
		money_panel.add(money_label);
		cunkuanMoneyInput = new JTextField(10);
		cunkuanMoneyInput.setFont(new Font("΢���ź�",Font.PLAIN,25));
		money_panel.add(cunkuanMoneyInput);
		cunkuanBox.add(money_panel);
		cunkuanBox.add(Box.createVerticalStrut(30));
		JPanel btn_panel = new JPanel();
		btn_panel.setOpaque(false);
		JButton queding_btn = new JButton("ȷ ��");
		queding_btn.setFont(new Font("΢���ź�",Font.PLAIN,15));
		btn_panel.add(queding_btn);
		JButton fanhui_btn = new JButton("����");
		fanhui_btn.setFont(new Font("΢���ź�",Font.PLAIN,15));
		btn_panel.add(fanhui_btn);
		cunkuanBox.add(btn_panel);
		cunkuanBox.add(Box.createVerticalStrut(10));
		JPanel tips_panel = new JPanel();
		tips_panel.setOpaque(false);
		cunkuanTipsLabel = new JLabel("");
		cunkuanTipsLabel.setForeground(new Color(238,32,32));
		cunkuanTipsLabel.setFont(new Font("΢���ź�",Font.PLAIN,20));
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
					cunkuanTipsLabel.setText("����Ϊ�գ�");
					cunkuanMoneyInput.requestFocus();
				}else{
					int cunkuan_rtn = bo.doCunkuan(Double.valueOf(money_dou));
					if(cunkuan_rtn == -2){
						showCunkuanSuccess();
					}else {
					cunkuanMoneyInput.setText("");
					cunkuanTipsLabel.setText("���벻Ϊ���٣����������ٽ��");
					cunkuanMoneyInput.requestFocus();
					}
				}
				
				
		
			}
		});
		
		
		
		
	}
	/**
	*���ɹ���ʾ����
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
			JLabel cunkuanSuccess_label =new JLabel ("���ɹ���");
			cunkuanSuccess_label.setForeground(Color.WHITE);
			cunkuanSuccess_label.setFont(new Font("΢���ź�",Font.PLAIN,30));
			cunkuanSuccess_panel.add(cunkuanSuccess_label);
			cunkuanSuccessBox.add(cunkuanSuccess_panel);
			cunkuanSuccessBox.add(Box.createVerticalStrut(30));
			JPanel btn_panel = new JPanel();
			btn_panel.setOpaque(false);
			JButton cunkuanSuccess_btn = new JButton("����");
			cunkuanSuccess_btn.setFont(new Font("΢���ź�",Font.PLAIN,15));
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
	*ȡ�����
	*/public JPanel qukuanPane = null;
	//�������
	public JTextField qukuanMoneyInput = null;
	//��ʾ��Ϣ
	public JLabel qukuanTipsLabel = null;
	public void showQukuan(){
		qukuanPane = new JPanel();
		qukuanPane.setOpaque(false);
		Box qukuanBox  = Box.createVerticalBox();
		qukuanBox.add(Box.createVerticalStrut(180));
		JPanel money_panel = new JPanel();
		money_panel.setOpaque(false);
		JLabel money_label = new JLabel("������ȡ��� ");
		money_label.setForeground(Color.WHITE);
		money_label.setFont(new Font("΢���ź�", Font.PLAIN,25));
		money_panel.add(money_label);
		qukuanMoneyInput = new JTextField(10);
		qukuanMoneyInput.setFont(new Font("΢���ź�",Font.PLAIN,25));
		money_panel.add(qukuanMoneyInput);
		qukuanBox.add(money_panel);
		qukuanBox.add(Box.createVerticalStrut(30));
		JPanel btn_panel = new JPanel();
		btn_panel.setOpaque(false);
		JButton queding_btn = new JButton("ȷ ��");
		queding_btn.setFont(new Font("΢���ź�",Font.PLAIN,15));
		btn_panel.add(queding_btn);
		JButton fanhui_btn = new JButton("����");
		fanhui_btn.setFont(new Font("΢���ź�",Font.PLAIN,15));
		btn_panel.add(fanhui_btn);
		qukuanBox.add(btn_panel);
		qukuanBox.add(Box.createVerticalStrut(10));
		JPanel tips_panel = new JPanel();
		tips_panel.setOpaque(false);
		qukuanTipsLabel = new JLabel("");
		qukuanTipsLabel.setForeground(new Color(238,32,32));
		qukuanTipsLabel.setFont(new Font("΢���ź�",Font.PLAIN,20));
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
					qukuanTipsLabel.setText("����Ϊ�գ�");
					qukuanMoneyInput.requestFocus();
					
				}else{
					int qukuan_rtn = bo.doQukuan(Double.valueOf(money1_dou));
					if(qukuan_rtn == -1 ){
						showQukuanSuccess();
					}else if(qukuan_rtn == -2){
						qukuanTipsLabel.setText("����Ľ������");
						qukuanMoneyInput.requestFocus();
					}else{
						qukuanTipsLabel.setText("����Ľ�Ϊ����");
						qukuanMoneyInput.requestFocus();
					}
					
				}
				
			}
			
		});
	}
		
		
		
		
	
	/**
	*ȡ��ɹ�����
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
			JLabel qukuanSuccess_label =new JLabel ("ȡ��ɹ���");
			qukuanSuccess_label.setForeground(Color.WHITE);
			qukuanSuccess_label.setFont(new Font("΢���ź�",Font.PLAIN,30));
			qukuanSuccess_panel.add(qukuanSuccess_label);
			qukuanSuccessBox.add(qukuanSuccess_panel);
			qukuanSuccessBox.add(Box.createVerticalStrut(30));
			JPanel btn_panel = new JPanel();
			btn_panel.setOpaque(false);
			JButton qukuanSuccess_btn = new JButton("����");
			qukuanSuccess_btn.setFont(new Font("΢���ź�",Font.PLAIN,15));
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
	*�����޸Ľ���
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
		JLabel mima1_label = new JLabel("��������룺");
		mima1_label.setForeground(Color.WHITE);
		mima1_label.setFont(new Font("΢���ź�",Font.PLAIN, 25));
		mima1_panel.add(mima1_label);
		mima1Input = new JTextField(10);
		mima1Input.setFont(new Font("΢���ź�",Font.PLAIN, 25));
		mima1_panel.add(mima1Input);
		xiugaiBox.add(mima1_panel);
		
		xiugaiBox.add(Box.createVerticalStrut(30));
		JPanel mima2_panel = new JPanel();
		mima2_panel.setOpaque(false);
		JLabel mima2_label = new JLabel("���������룺");
		mima2_label.setForeground(Color.WHITE);
		mima2_label.setFont(new Font("΢���ź�",Font.PLAIN, 25));
		mima2_panel.add(mima2_label);
		mima2Input = new JTextField(10);
		mima2Input.setFont(new Font("΢���ź�",Font.PLAIN, 25));
		mima2_panel.add(mima2Input);
		xiugaiBox.add(mima2_panel);
		
		xiugaiBox.add(Box.createVerticalStrut(30));
		JPanel mima3_panel = new JPanel();
		mima3_panel.setOpaque(false);
		JLabel mima3_label = new JLabel("���ٴ��������룺");
		mima3_label.setForeground(Color.WHITE);
		mima3_label.setFont(new Font("΢���ź�",Font.PLAIN, 25));
		mima3_panel.add(mima3_label);
		mima3Input = new JTextField(10);
		mima3Input.setFont(new Font("΢���ź�",Font.PLAIN, 25));
		mima3_panel.add(mima3Input);
		xiugaiBox.add(mima3_panel);
		//���ð�ť����ʾ��
		xiugaiBox.add(Box.createVerticalStrut(30));
		JPanel btn_panel = new JPanel();
		btn_panel.setOpaque(false);
		JButton queding_btn = new JButton("ȷ ��");
		queding_btn.setFont(new Font("΢���ź�",Font.PLAIN,15));
		btn_panel.add(queding_btn);
		JButton fanhui_btn = new JButton("����");
		fanhui_btn.setFont(new Font("΢���ź�",Font.PLAIN,15));
		btn_panel.add(fanhui_btn);
		xiugaiBox.add(btn_panel);
		xiugaiBox.add(Box.createVerticalStrut(10));
		JPanel tips_panel = new JPanel();
		tips_panel.setOpaque(false);
		xiugaiTipsLabel = new JLabel("");
		xiugaiTipsLabel.setForeground(new Color(238,32,32));
		xiugaiTipsLabel.setFont(new Font("΢���ź�",Font.PLAIN,20));
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
					xiugaiTipsLabel.setText("���벻��Ϊ��");
					mima1Input.requestFocus();
					
				}else{
					int xiugai_rtn = bo.doXiugai(Integer.valueOf(mima1),Integer.valueOf(mima2),Integer.valueOf(mima3));
					if(xiugai_rtn == -1){
						showXiugaiSuccess();
					}else if(xiugai_rtn == -2){
						mima2Input.setText("");
						mima3Input.setText("");
						xiugaiTipsLabel.setText("�������������벻һ�£�����������");
						mima2Input.requestFocus();
						
					}else{
						mima1Input.setText("");
						xiugaiTipsLabel.setText("ԭ���������������������");
						mima1Input.requestFocus();
						
					}
				}
				
			}
			
		});
		
	}
		
		
		
		
		
	
	/**
	*xiugai�ɹ�����
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
			JLabel xiugaiSuccess_label =new JLabel ("�޸ĳɹ���");
			xiugaiSuccess_label.setForeground(Color.WHITE);
			xiugaiSuccess_label.setFont(new Font("΢���ź�",Font.PLAIN,30));
			xiugaiSuccess_panel.add(xiugaiSuccess_label);
			xiugaiSuccessBox.add(xiugaiSuccess_panel);
			xiugaiSuccessBox.add(Box.createVerticalStrut(30));
			JPanel btn_panel = new JPanel();
			btn_panel.setOpaque(false);
			JButton xiugaiSuccess_btn = new JButton("����");
			xiugaiSuccess_btn.setFont(new Font("΢���ź�",Font.PLAIN,15));
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