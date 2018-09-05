import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.Graphics;
import java.awt.Color;

//Add searches 
//Take steps of size ten 
class Agent {

	// Draw frontier here and path as well
	void drawPlan(Graphics g, Model m) {
		g.setColor(Color.red);
		g.drawLine((int) m.getX(), (int) m.getY(), (int) m.getDestinationX(), (int) m.getDestinationY());
	}

	// A controller obj from model
	// This is where you get the mouse events
	// Checks for mouse event and send coordinates of mouse clicks
	// Add breadth first search here
	void update(Model m) {
		Controller c = m.getController();
		while (true) {
			MouseEvent e = c.nextMouseEvent();
			if (e == null)
				break;
			m.setDestination(e.getX(), e.getY()); // Set destination from mouse event
		}
	}

	public static void main(String[] args) throws Exception {
		Controller.playGame();
	}
}
