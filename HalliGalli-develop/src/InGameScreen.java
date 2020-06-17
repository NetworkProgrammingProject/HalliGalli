import java.awt.Container;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Timer;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;



public class InGameScreen extends GameScreen implements Serializable {

	static final String TITLE = "Play Game";
	static final int PLAYER1 = 1;
	static final int PLAYER2 = 3;
	static final int PLAYER3 = 5;
	static final int PLAYER4 = 7;
	static final int BELL = 4;
	
	static final int[][] PLAYER_OUT_GRID = {{2,1},{1,2},{1,2},{2,1}};
	//static final int[][] PLAYER_IN_GRID = {{1,3},{3,1},{3,1},{1,3}};	
	static final int[][] PLAYER_IN_GRID = {{1,2},{2,1},{2,1},{1,2}};	
	
	
	Timer m_timer;	
	ScheduledJob job;
	
	JPanel playPanel[];
	JPanel[][] pansInnerGrid;
	JPanel[] panRevCard;
	JPanel[] panPname;
	JPanel[] panGetCards;
	
	JLabel[] labRevCard;
	JLabel[] labPname;
	JButton[] butGetCards;
	//JLabel[] labGetCards;
	
	
	public InGameScreen(Halligalli halliGalli,int id)
	{
		super(TITLE,halliGalli);
		pansInnerGrid = new JPanel[4][2];
		panRevCard = new JPanel[4];
		panPname = new JPanel[4];
		panGetCards = new JPanel[4];
		
		labRevCard = new JLabel[4];
		labPname = new JLabel[4];
		butGetCards = new JButton[4];
		//labGetCards = new JLabel[4];
		
		setContentPane();
		m_timer = new Timer();
    	job = new ScheduledJob(this,halliGalli, id);
        m_timer.schedule(job, 0, 1000);
        
	}
	
	public InGameScreen(Halligalli halliGalli,int x,int y){
		super(TITLE,halliGalli);
		makeFrame(x,y,0,0);
		setContentPane();
	}
	
	public InGameScreen(Halligalli halliGalli,int x,int y,int w,int h) {
		super(TITLE,halliGalli);
		makeFrame(x,y,w,h);
		setContentPane();
	}
	
	public void setContentPane()
	{
		Container contentPane = this.getContentPane();
		JPanel[] playPanel = new JPanel[9];
		GridLayout grid = new GridLayout(3,3);
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints bagConstraints = new GridBagConstraints(); 
		JButton bellButton;
		
		
		try {	
			contentPane.setLayout(grid);
			
			for(int i=0;i<9;i++)
			{
				playPanel[i] = new JPanel();
				if(i==BELL)
				{
					bellButton = new JButton("BELL");
					ActionListener btnClickListener = new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JButton clickButton = (JButton) e.getSource();
							if(clickButton.getText().contentEquals("BELL"))
							{
								try {
									halliGalli.hitBell(HalligalliClient.id);
								} catch (RemoteException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						}
					};
					playPanel[i].setLayout(bagLayout);
					bagConstraints.weightx = 0.1;
					bagConstraints.weighty = 0.1;
					bagConstraints.fill = GridBagConstraints.BOTH;
					playPanel[i].add(bellButton,bagConstraints);
					bellButton.addActionListener(btnClickListener);
				}
				else if((i<9) && (i%2==1))
					setPlayerPanel(i/2, playPanel[i]);
				contentPane.add(playPanel[i]);
			}		
			setVisible(true);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void setContentPane(StatusRes stae)
	{
	}
	
	private void setPlayerPanel(int pId, JPanel playPanel)
	{
		GridLayout grid = new GridLayout(PLAYER_OUT_GRID[pId][0],PLAYER_OUT_GRID[pId][1]) ;
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints bagConstraints = new GridBagConstraints(); 
		
		//JPanel[] pansInnerGrid = new JPanel[2]; 
		//pansInnerGrid[0] = new JPanel();
		//pansInnerGrid[1] = new JPanel();
		
		pansInnerGrid[pId][0] = new JPanel();
		pansInnerGrid[pId][1] = new JPanel();
				
		panRevCard[pId] = new JPanel();
		panPname[pId] = new JPanel();
		panGetCards[pId] = new JPanel();
		//panPoints[] = new JPanel();
		
		
		labRevCard[pId] = new JLabel("Empty");
		labPname[pId]  = new JLabel("");
		butGetCards[pId] = new JButton("Open");
		//labGetCards[pId] = new JLabel("0");
		
		ActionListener btnClickListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton clickButton = (JButton) e.getSource();
				if(clickButton.getText().contentEquals("Open"))
				{
					try {
						halliGalli.openCard(HalligalliClient.id);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		};
		butGetCards[pId].addActionListener(btnClickListener);
		
		//JPanel panRevCard = new JPanel();
		//JPanel panPname = new JPanel();
		//JPanel panGetCards = new JPanel();
		//JPanel panPoints = new JPanel();
		
		//JLabel labRevCard = new JLabel("Empty");
		//JLabel labPname  = new JLabel("Player" + (pId+1));
		//JLabel labGetCards = new JLabel("0");
		//JLabel labPoints = new JLabel("0");
		
		playPanel.setLayout(grid);
		grid = new GridLayout(PLAYER_IN_GRID[pId][0],PLAYER_IN_GRID[pId][1]) ;
		
		pansInnerGrid[pId][pId/2].setLayout(grid);
		pansInnerGrid[pId][1-pId/2].setLayout(bagLayout);
			
		panRevCard[pId].add(labRevCard[pId]);
		panPname[pId].add(labPname[pId]);
		panGetCards[pId].add(butGetCards[pId]);
		//panGetCards[pId].add(labGetCards[pId]);
		
		//pansInnerGrid[pId/2].setLayout(grid);
		//pansInnerGrid[1-pId/2].setLayout(bagLayout);
		
		//panRevCard.add(labRevCard);
		//panPname.add(labPname);
		//panGetCards.add(labGetCards);
		//panPoints.add(labPoints);
		
		pansInnerGrid[pId][1-pId/2].add(panRevCard[pId], bagConstraints);
		pansInnerGrid[pId][pId/2].add(panPname[pId]);
		pansInnerGrid[pId][pId/2].add(panGetCards[pId]);
		//pansInnerGrid[pId][pId/2].add(panGetCards[pId]);
		//pansInnerGrid[pId][pId/2].add(panPoints);
		
		playPanel.add(pansInnerGrid[pId][0]);
		playPanel.add(pansInnerGrid[pId][1]);
		
		
	/*	pansInnerGrid[1-pId/2].add(panRevCard, bagConstraints);
		pansInnerGrid[pId/2].add(panPname);
		pansInnerGrid[pId/2].add(panGetCards);
		pansInnerGrid[pId/2].add(panPoints);
		*/
		/*
		playPanel.add(pansInnerGrid[0]);
		playPanel.add(pansInnerGrid[1]);
		*/	
	}
	
	public void updateScreenWhenHitBell() {
		for(int i=0;i<4;i++)
			labRevCard[i].setText("");
	}
	
	
	// 1 = 바나나  2= 딸기  3= 키위 4= 자두
	public void updateScreen(StatusRes state) {
		int curUser = state.turn;
		int fruitNum = state.openCards[curUser].fruit;
		String fruit = "";
		String num = Integer.toString(state.openCards[curUser].num);
		
		switch(fruitNum) {
		case 1:
			fruit = "BA";
			break;
		case 2:
			fruit = "ST";
			break;
		case 3:
			fruit = "KI";
			break;
		case 4:
			fruit = "PL";
			break;
		}
		
		
		labRevCard[curUser].setText(fruit+num);
		labPname[curUser].setText("Player"+Integer.toString(state.users.get(curUser)));
		//labGetCards[curUser].setText(state.);
	
	}
	
	public void endScreen() {
		m_timer.cancel();
		dispose();
	}
}
