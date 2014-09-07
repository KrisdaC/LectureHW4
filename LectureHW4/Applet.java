package topic4;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.geom.Ellipse2D;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Applet extends JFrame {
	Background bg = new Background();
	JSlider slider = new JSlider();
	JPanel p1 = new JPanel();
	JRadioButton red = new JRadioButton("Red");
	JRadioButton blue = new JRadioButton("Blue");
	JRadioButton orange = new JRadioButton("Orange");
	ButtonGroup grp = new ButtonGroup();
	Color selcolor = Color.RED;
	int d = 50;

	public Applet() {
		this.setPreferredSize(new Dimension(700, 500));
		this.setLayout(new BorderLayout());
		this.add(bg, BorderLayout.CENTER);
		
		slider.setPaintLabels(true);
	    slider.setMajorTickSpacing(5);
		this.add(slider, BorderLayout.SOUTH);
		
		p1.setLayout(new GridLayout(3,1));
		this.add(p1,BorderLayout.EAST);
		
		red.addActionListener(new ColorAction("red"));
		blue.addActionListener(new ColorAction("blue"));
		orange.addActionListener(new ColorAction("orange"));
		
		grp.add(red);
		p1.add(red);
		grp.add(blue);
		p1.add(blue);
		grp.add(orange);
		p1.add(orange);
		slider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) {
				d = slider.getValue();
			}
		});
	}

	public static void createGUI() {
		Applet app = new Applet();
		app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		app.pack();
		app.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createGUI();
			}
		});
	}
	
	class ColorAction extends AbstractAction{
		String color;
		public ColorAction(String s){
			color = s;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(color.equals("red")){
				selcolor = Color.RED;
			}else if(color.equals("blue")){
				selcolor = Color.BLUE;
			}else if(color.equals("orange")){
				selcolor = Color.ORANGE;
			}}
		}
		
	
	class Background extends JPanel implements Runnable {
		Thread draw;
		int x = 250;
		int y = 250;
		Ellipse2D.Double s;

		public Background() {
			setBackground(Color.WHITE);
			initShapes();
			InputMap inputMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
			inputMap.put(KeyStroke.getKeyStroke("A"), "moveLeft");
			inputMap.put(KeyStroke.getKeyStroke("D"), "moveRight");
			inputMap.put(KeyStroke.getKeyStroke("W"), "moveUp");
			inputMap.put(KeyStroke.getKeyStroke("S"), "moveDown");
			ActionMap actionMap = this.getActionMap();
			actionMap.put("moveLeft", new MoveAction("left"));
			actionMap.put("moveRight", new MoveAction("right"));
			actionMap.put("moveUp", new MoveAction("up"));
			actionMap.put("moveDown", new MoveAction("down"));
			draw = new Thread(this);
			draw.start();
		}

		public void initShapes() {
		}

		@Override
		protected void paintComponent(Graphics g) {
			// TODO Auto-generated method stub
			super.paintComponent(g);
			s = new Ellipse2D.Double(x, y, d, d);
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(selcolor);
			g2.fill(s);
		}

		class MoveAction extends AbstractAction {
			String direction;
			public MoveAction(String direction) {
				this.direction = direction;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				if (direction.equals("left")) {
					x = x - 3;
				} else if (direction.equals("right")) {
					x = x + 3;
				} else if (direction.equals("up")){
					y = y - 3;
				}
				else if (direction.equals("down")){
					y = y + 3;
				}
			}
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				repaint();
			}
		}
	}
}