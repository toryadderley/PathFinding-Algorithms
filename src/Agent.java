import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.TreeSet;
import java.util.Collections;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Line2D;
import javax.swing.*;

public class Agent {

	public boolean checked = false;
	public ArrayList<State> solutionPath = new ArrayList<State>();
	public PriorityQueue<State> frontier = new PriorityQueue<State>();
	private Color color1 = Color.WHITE;
	private Color color2 = Color.RED;
	private Color color3 = Color.ORANGE;
	public float xDestination = -1;
	public float yDestination = -1;
	public int mouseClick;

	private enum searchType {
		ASTAR, UCS;
	}

	searchType type;

	void update(Model m) {
		if (!checked) {
			State.setModel(m);
			State.setSpeed();
			checked = true;
		}
		if (xDestination >= 0 && yDestination >= 0)
			m.setDestination(xDestination, yDestination);
		Controller c = m.getController();
		while (true) {
			MouseEvent e = c.nextMouseEvent();
			if (e == null)
				break;
			m.setDestination(e.getX(), e.getY()); // Set destination from mouse event
			mouseClick = e.getButton();
			xDestination = e.getX();
			yDestination = e.getY();
		}
		explore(xDestination, yDestination, m);
	}

	// Draw methods
	void drawFrontier(Graphics g) {
		if (type == searchType.UCS)
			g.setColor(color3);
		if (type == searchType.ASTAR)
			g.setColor(color2);
		if (frontier != null)
			for (State state : frontier)
				g.fillOval(state.getX(), state.getY(), 10, 10);
	}

	void drawPath(Graphics2D g, Model m) {
		g.setColor(color1);
		if (!solutionPath.isEmpty())
			for (State state : solutionPath) {
				Shape s = new Line2D.Float(state.getParentState().getX(), state.getParentState().getY(), state.getX(),
						state.getY());
				g.draw(s);
			}
	}

	void explore(float x, float y, Model m) {
		float x2 = x;
		float y2 = y;

		int x1 = (int) (m.getX() / 10) * 10;
		int y1 = (int) (m.getY() / 10) * 10;
		int xDest = (int) (m.getDestinationX() / 10) * 10;
		int yDest = (int) (m.getDestinationY() / 10) * 10;

		if (x1 != xDest || y1 != yDest) {
			State startState = new State(0.0, null, x1, y1);
			State goalState = new State(0.0, null, xDest, yDest);
			State stateOnPath = new State();
			if (mouseClick == MouseEvent.BUTTON1) {
				type = searchType.UCS;
				Search UCS = new Search();
				stateOnPath = UCS.UniformCostSearch(startState, goalState);
				this.frontier = UCS.getFrontier();
			} else if (mouseClick == MouseEvent.BUTTON3) {
				type = searchType.ASTAR;
				Search Astar = new Search();
				stateOnPath = Astar.AStarSearch(startState, goalState);
				this.frontier = Astar.getFrontier();
			}
			State cur = new State(stateOnPath);
			solutionPath.clear();
			while (cur.getParentState() != null) {
				solutionPath.add(cur);
				cur = cur.getParentState();
			}

			if (solutionPath.size() - 1 >= 0)
				m.setDestination(solutionPath.get(solutionPath.size() - 1).getX(),
						solutionPath.get(solutionPath.size() - 1).getY());
			else {
				m.setDestination(x2, y2);
				solutionPath.clear();
			}
			Collections.reverse(solutionPath);

		}

	}

	public static void main(String[] args) throws Exception {
		Controller.playGame();
	}
}
