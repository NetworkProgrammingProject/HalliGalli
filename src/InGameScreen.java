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

    static final int[][] PLAYER_OUT_GRID = {{2, 1}, {1, 2}, {1, 2}, {2, 1}};
    //static final int[][] PLAYER_IN_GRID = {{1,3},{3,1},{3,1},{1,3}};
    static final int[][] PLAYER_IN_GRID = {{1, 3}, {3, 1}, {3, 1}, {1, 3}};


    Timer m_timer;
    ScheduledJob job;

    JPanel playPanel[];
    JPanel[][] pansInnerGrid;
    JPanel[] panRevCard;
    JPanel[] panPname;
    JPanel[] panGetCards;
    JPanel[] panRemCards;
    
    JLabel[] labRevCard;
    JLabel[] labPname;
    JButton[] butGetCards;
    JLabel[] labGetCards;
    JLabel[] labRemCards;
    

    public InGameScreen(Halligalli halliGalli, int id) {
        super(TITLE, halliGalli);
        pansInnerGrid = new JPanel[4][2];
        panRevCard = new JPanel[4];
        panPname = new JPanel[4];
        panGetCards = new JPanel[4];
        panRemCards = new JPanel[4];
        
        labRevCard = new JLabel[4];
        labPname = new JLabel[4];
        butGetCards = new JButton[4];
        labRemCards = new JLabel[4];

        setContentPane();
        m_timer = new Timer();
        job = new ScheduledJob(this, halliGalli, id);
        m_timer.schedule(job, 0, 100);

    }

    public InGameScreen(Halligalli halliGalli, int x, int y) {
        super(TITLE, halliGalli);
        makeFrame(x, y, 0, 0);
        setContentPane();
    }

    public InGameScreen(Halligalli halliGalli, int x, int y, int w, int h) {
        super(TITLE, halliGalli);
        makeFrame(x, y, w, h);
        setContentPane();
    }

    public void setContentPane() {
        Container contentPane = this.getContentPane();
        JPanel[] playPanel = new JPanel[9];
        GridLayout grid = new GridLayout(3, 3);
        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints bagConstraints = new GridBagConstraints();
        JButton bellButton;


        try {
            contentPane.setLayout(grid);

            for (int i = 0; i < 9; i++) {
                playPanel[i] = new JPanel();
                if (i == BELL) {
                    bellButton = new JButton("BELL");
                    ActionListener btnClickListener = new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JButton clickButton = (JButton) e.getSource();
                            if (clickButton.getText().contentEquals("BELL")) {
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
                    playPanel[i].add(bellButton, bagConstraints);
                    bellButton.addActionListener(btnClickListener);
                } else if ((i < 9) && (i % 2 == 1))
                    setPlayerPanel(i / 2, playPanel[i]);
                contentPane.add(playPanel[i]);
            }
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setContentPane(StatusRes stae) {
    }

    private void setPlayerPanel(int pId, JPanel playPanel) {
        GridLayout grid = new GridLayout(PLAYER_OUT_GRID[pId][0], PLAYER_OUT_GRID[pId][1]);
        GridBagLayout bagLayout = new GridBagLayout();
        GridBagConstraints bagConstraints = new GridBagConstraints();


        pansInnerGrid[pId][0] = new JPanel();
        pansInnerGrid[pId][1] = new JPanel();

        panRevCard[pId] = new JPanel();
        panPname[pId] = new JPanel();
        panGetCards[pId] = new JPanel();
        panRemCards[pId] = new JPanel();


        labRevCard[pId] = new JLabel("Empty");
        labPname[pId] = new JLabel("");
        butGetCards[pId] = new JButton("Open");
        labRemCards[pId] = new JLabel("0");

        ActionListener btnClickListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JButton clickButton = (JButton) e.getSource();
                if (clickButton.getText().contentEquals("Open")) {
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
        
        playPanel.setLayout(grid);
        grid = new GridLayout(PLAYER_IN_GRID[pId][0], PLAYER_IN_GRID[pId][1]);

        pansInnerGrid[pId][pId / 2].setLayout(grid);
        pansInnerGrid[pId][1 - pId / 2].setLayout(bagLayout);

        panRevCard[pId].add(labRevCard[pId]);
        panPname[pId].add(labPname[pId]);
        panGetCards[pId].add(butGetCards[pId]);
        panRemCards[pId].add(labRemCards[pId]);

        pansInnerGrid[pId][1 - pId / 2].add(panRevCard[pId], bagConstraints);
        pansInnerGrid[pId][pId / 2].add(panPname[pId]);
        pansInnerGrid[pId][pId / 2].add(panGetCards[pId]);
        pansInnerGrid[pId][pId/2].add(panRemCards[pId]);

        playPanel.add(pansInnerGrid[pId][0]);
        playPanel.add(pansInnerGrid[pId][1]);
		
		

    }

    public void updateScreenWhenHitBell(StatusRes curState) {
    	if(curState.bellUser > 0)
    		for (int i = 0; i < 4; i++)
    			labRevCard[i].setText("Empty");
    	
    	for(int i=0;i<4;i++)
    		labRemCards[i].setText(""+curState.remainingCards.get(i));
    }



    public void updateScreen(StatusRes state) {
        int curUser = state.turn;
        int fruitNum = state.openCards[curUser].fruit;
        String fruit = "";
        String num = Integer.toString(state.openCards[curUser].num);

        switch (fruitNum) {
            case 1:
                fruit = "바나나";
                break;
            case 2:
                fruit = "딸기";
                break;
            case 3:
                fruit = "키위";
                break;
            case 4:
                fruit = "자두";
                break;
        }


        labRevCard[curUser].setText(fruit + num);
        labPname[curUser].setText("Player" + Integer.toString(state.users.get(curUser)));
        labRemCards[curUser].setText(Integer.toString(state.remainingCards.get(curUser)));
        //labGetCards[curUser].setText(state.);

    }

    public void updatePlayerName(StatusRes state) {
    		for(int i=0;i<state.users.size();i++)
    			labPname[i].setText("Player"+state.users.get(i));
    }
    
    public void endScreen() {
    	HalligalliClient.id = -1;
        m_timer.cancel();
        dispose();
    }
}
