import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

import java.lang.IllegalArgumentException;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;


public class GameScreen extends JFrame {

	static final int DEFAULT_X = 0;
	static final int DEFAULT_Y = 0;
	static final int DEFAULT_WIDTH = 400;
	static final int DEFAULT_HEIGHT = 400;
	
	Halligalli halliGalli;
	
	
	public GameScreen(String title, Halligalli halliGalli)
	{
		super(title);
		setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
		setLocationRelativeTo(null);
		setHalligalli(halliGalli);
	}
	
	public GameScreen(int x, int y,int w, int h) {
		makeFrame(x,y,w,h);
	}
	
	public void makeFrame(int x, int y, int w, int h) {
		try {
			if(x<0||y<0||w<=0 || h<=0)
				throw new IllegalArgumentException();
		}catch(IllegalArgumentException e){
			if(x<0)
				x = DEFAULT_X;
			if(y<0)
				y = DEFAULT_Y;
			if(w<=0)
				w = DEFAULT_WIDTH;
			if(h<=0)
				h = DEFAULT_HEIGHT;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		finally{
			setBounds(x,y,w,h);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setVisible(true);
		}
	}
	
	public void setHalligalli(Halligalli halliGalli) {
		this.halliGalli = halliGalli;
	}
	
}