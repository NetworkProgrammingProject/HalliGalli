import java.awt.Container;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;



public class InGameScreen extends GameScreen {

	static final String TITLE = "Play Game";
	static final int PLAYER1 = 1;
	static final int PLAYER2 = 3;
	static final int PLAYER3 = 5;
	static final int PLAYER4 = 7;
	static final int BELL = 4;
	
	static final int[][] PLAYER_OUT_GRID = {{2,1},{1,2},{1,2},{2,1}};
	static final int[][] PLAYER_IN_GRID = {{1,3},{3,1},{3,1},{1,3}};	
	
	public InGameScreen(Halligalli halliGalli)
	{
		super(TITLE,halliGalli);
		setContentPane();
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
					playPanel[i].setLayout(bagLayout);
					bagConstraints.weightx = 0.1;
					bagConstraints.weighty = 0.1;
					bagConstraints.fill = GridBagConstraints.BOTH;
					playPanel[i].add(bellButton,bagConstraints);
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
	
	private void setPlayerPanel(int pId, JPanel playPanel)
	{
		GridLayout grid = new GridLayout(PLAYER_OUT_GRID[pId][0],PLAYER_OUT_GRID[pId][1]) ;
		GridBagLayout bagLayout = new GridBagLayout();
		GridBagConstraints bagConstraints = new GridBagConstraints(); 
		
		JPanel[] pansInnerGrid = new JPanel[2]; 
		pansInnerGrid[0] = new JPanel();
		pansInnerGrid[1] = new JPanel();
		
		
		JPanel panRevCard = new JPanel();
		JPanel panPname = new JPanel();
		JPanel panGetCards = new JPanel();
		JPanel panPoints = new JPanel();
		
		JLabel labRevCard = new JLabel("Empty");
		JLabel labPname  = new JLabel("Player" + (pId+1));
		JLabel labGetCards = new JLabel("0");
		JLabel labPoints = new JLabel("0");
		
		playPanel.setLayout(grid);
		grid = new GridLayout(PLAYER_IN_GRID[pId][0],PLAYER_IN_GRID[pId][1]) ;
		pansInnerGrid[pId/2].setLayout(grid);
		pansInnerGrid[1-pId/2].setLayout(bagLayout);
		
		panRevCard.add(labRevCard);
		panPname.add(labPname);
		panGetCards.add(labGetCards);
		panPoints.add(labPoints);
		
		
		pansInnerGrid[1-pId/2].add(panRevCard, bagConstraints);
		pansInnerGrid[pId/2].add(panPname);
		pansInnerGrid[pId/2].add(panGetCards);
		pansInnerGrid[pId/2].add(panPoints);
		
		playPanel.add(pansInnerGrid[0]);
		playPanel.add(pansInnerGrid[1]);
		
	}
}
