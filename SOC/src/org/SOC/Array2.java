package org.SOC;

import java.awt.GridLayout;
import java.awt.Label;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JOptionPane;
import javax.xml.transform.OutputKeys;

public class Array2 {

	public Node[][] nodes;
	public int xsize;
	public int ysize;
	
	public Array2(int integer, int integer2) {
		this.xsize = integer;
		this.ysize = integer2;
		nodes = new Node[integer][integer2];	
	}

	public int getXSize() {
		return xsize;
	}
	
	public int getYSize() {
		return ysize;
	}

	public void setSize(int size, int size2) {
		this.xsize = size;
		this.ysize = size2;
	}
	
	public Node[][] getArrays() {
		return nodes;
	}
	
	public void setNodes(Node[][] nodes) {
		this.nodes = nodes;
	}

	public boolean randomGen() {
		boolean status = false;
		int ztO = (Math.random()<0.0)?0:1;
		if (ztO == 0) {
			status = false;
			return status;
		} else {
			status = true;
			return status;
		}
	}
	
	public void getArrayStatus() {
		System.out.println("##### Full Array #####");
		System.out.println("East-North links:");

		Node[][] a1 = this.getArrays();
		
		for (int i = 0; i < a1.length; i++) {
			
			System.out.println("");
			for (int j = 0; j < a1[i].length; j++) {
				if (j != 0) {
				}
				System.out.print("[" + i + "," + j + "]");
				if (j != a1[i].length - 1) {
					if (a1[i][j].getEast_link() != false) {
						System.out.print("-->");
					} else {
						System.out.print("-x>");
					}
				}
			}
			if (i != a1.length - 1) {
				System.out.println("");
				for (int j = 0; j < a1[i].length; j++) {			
					if (a1[i+1][j].getNorth_link() != false) {
						System.out.print("  |  " + "   ");
					} else {
						System.out.print("  x  " + "   ");
					}
				}
			}
		}
		
		System.out.println("\n\nWest-South links:");
		for (int i = 0; i < a1.length; i++) {
			
			System.out.println("");
			for (int j = 0; j < a1[i].length; j++) {
				if (j != 0) {
					if (a1[i][j].getWest_link() != false) {
						System.out.print("<--");
					} else {
						System.out.print("<x-");
					}
				}
				System.out.print("[" + i + "," + j + "]");
			}
			if (i != a1.length - 1) {
				System.out.println("");
				for (int j = 0; j < a1[i].length; j++) {			
					if (a1[i][j].getSouth_link() != false) {
						System.out.print("  |  " + "   ");
					} else {
						System.out.print("  x  " + "   ");
					}
				}
			}
		}
	}
	
	public boolean backtrack(int xpos, int ypos, int destx, int desty, ArrayList<String> list2) {
		boolean success = false;
		
		Node[][] a1 = this.nodes;
		String unreached = null;
		String reached = null;
		
		if (xpos == destx && ypos == desty) {
			System.out.println("Final Position" + "[" + xpos + "][" + ypos + "]");
			//return true;
		}
		
		Node node = a1[xpos][ypos];
		System.out.println("New Position" + "[" + xpos + "][" + ypos + "]");
		if (node != a1[destx][desty]) { // Let's not mark final node as visited to for example get counter
			node.isVisited = true; // To Avoid duplicate visits
		}
		if (!(list2.isEmpty())) {
			list2.remove(0);
		}
		
		if (ypos <= desty) {
			if (desty == ypos && destx < xpos) {
				System.out.println("TRY NORTH ");
				success = node.getNorth_link();
				if (!(success)) { // If link is broken
					unreached = 0 + "," + ypos + "," + (xpos - 1) + "," + ypos;
					if (listCheck(list2, unreached)) { // Check for duplicates
						list2.add(unreached);
					}
				}
			} else if (desty == ypos && destx > xpos) {
				System.out.println("TRY SOUTH ");
				success = node.getSouth_link();
			} else if (destx == xpos && ypos != this.ysize - 1) {
				System.out.println("TRY EAST ");
				success = node.getEast_link();
				if ((a1[xpos][ypos + 1].isVisited == false) && success && this.backtrack(xpos, ypos + 1, destx, desty, list2)) {
					return true;
				}
			} else {
				if (ypos != this.ysize - 1) {
					System.out.println("TRY EAST");
					success = node.getEast_link();
					if (!(success)) {
						unreached = 0 + "," + (ypos + 1) + "," + xpos + "," + (a1[xpos].length - 1);
						if (listCheck(list2, unreached)) {
							list2.add(unreached);
						}
					}
					if ((a1[xpos][ypos + 1].isVisited == false) && success && this.backtrack(xpos, ypos + 1, destx, desty, list2)) {
						return true;
					} else {
						if (destx < xpos) { // For minimal routing
							System.out.println("TRY NORTH from Position" + "[" + xpos + "][" + ypos + "]");
							success = node.getNorth_link();
							if (success) {
								reached = 0 + "," + ypos + "," + (xpos - 1) + "," + (a1[ypos].length - 1);
								System.out.println("Reached: " + reached);
							}
						} else if (destx > xpos && xpos != this.xsize - 1) { // For minimal routing
							System.out.println("TRY SOUTH from Position" + "[" + xpos + "][" + ypos + "]");
							success = node.getSouth_link();
						}
					}
				}
			}
		} else if (ypos >= desty) {
			return true;
		}
				
		return false;
	}
	
	public static void main(String[] args) {
				
		JTextField xval = new JTextField("3",5);
		JTextField yval = new JTextField("3");
		JTextField sxval = new JTextField("2");
		JTextField syval = new JTextField("0");
		JTextField dxval = new JTextField("0");
		JTextField dyval = new JTextField("2");
		
		JPanel panel = new JPanel(new GridLayout(3,2));
		panel.add(  new JLabel("X size: ") );
		panel.add( xval );
		panel.add(  new JLabel(" Y size: ") );
		panel.add( yval );
		panel.add(  new JLabel("X starting cord: ") );
		panel.add( sxval );
		panel.add(  new JLabel(" Y starting cord: ") );
		panel.add( syval );
		panel.add(  new JLabel("X destination cord: ") );
		panel.add( dxval );
		panel.add(  new JLabel(" Y destination cord: ") );
		panel.add( dyval );
		int res = JOptionPane.showConfirmDialog(null, panel, "SOC GUI 0.1 | Main", JOptionPane.OK_CANCEL_OPTION);
		
		String xnum = xval.getText();
		String ynum = yval.getText();
		String sxnum = sxval.getText();
		String synum = syval.getText();
		String dynum = dyval.getText();
		String dxnum = dxval.getText();
				
		int sizex = Integer.parseInt(xnum);
		int sizey = Integer.parseInt(ynum);
		int xpos = Integer.parseInt(sxnum);
		int ypos = Integer.parseInt(synum);
		int destx = Integer.parseInt(dxnum);
		int desty = Integer.parseInt(dynum);
		
		if (ypos > desty) {
			JOptionPane.showMessageDialog(null, "Due do the limited functinality, starting X pos can't be greater than destination X pos");
			return;
		}
		
		if (res == 2) {
			JOptionPane.showMessageDialog(null, "Closing...");
			return;
		}
		
		Array2 a1 = new Array2(sizex, sizey);
			
		for (int i = 0; i < a1.getXSize(); i++) {
			for (int j = 0; j < a1.getYSize(); j++) {
				if (i == 0) {
					if (j == 0) {
						a1.nodes[i][j] = new Node(false, a1.randomGen(), false, a1.randomGen());
					} else {
						a1.nodes[i][j] = new Node(a1.randomGen(), a1.randomGen(), false, a1.randomGen());
					}
				} else if (i > 0) {
					if (j == 0) {
						a1.nodes[i][j] = new Node(false, a1.randomGen(), a1.randomGen(), a1.randomGen());
					} else {

						a1.nodes[i][j] = new Node(a1.randomGen(), a1.randomGen(), a1.randomGen(), a1.randomGen());	
					}
				}
				//System.out.println("E" + a1.getArrays()[j][i].east_link + " " + "N" + a1.getArrays()[j][i].north_link + " " + "S" + a1.getArrays()[j][i].south_link + " " + "W" + a1.getArrays()[j][i].west_link);
			}
		}
		//JFrame myFrame = new JFrame("Adaptive Routing");
		//myFrame.setSize(500,500);
		//myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//myFrame.setVisible(true);
		
		//a1.nodes[0][1].setEast_link(false);
		
		//a1.nodes[1][0].setEast_link(false);
		//a1.nodes[1][2].setNorth_link(false);
		
		a1.getArrayStatus();
		
		JTextField xcord = new JTextField(3);
		JTextField ycord = new JTextField();
		JComboBox value = new JComboBox(new String[] { "True", "False" });
		JComboBox direction = new JComboBox(new String[] { "North", "South", "East", "West" });
		
        JPanel manPanel = new JPanel(new GridLayout(0, 1));
        manPanel.add(  new JLabel("In here you can set link status separately") );
        manPanel.add(  new JLabel("") );
        manPanel.add(  new JLabel(" Node X-coordinate: ") );
        manPanel.add( xcord );
        manPanel.add(  new JLabel(" Node Y-coordinate: ") );
        manPanel.add( ycord );
        manPanel.add(  new JLabel(" Link Direction: ") );
        manPanel.add( direction );
        manPanel.add(  new JLabel(" Link Status: ") );
        manPanel.add( value );
        manPanel.add(  new JLabel("") );
		
		String[] buttons = { "Add", "OK"};
		if (res == 0) {
			int res2 = JOptionPane.showOptionDialog(null, manPanel, "SOC GUI 0.1 -> Link Status", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[1]);			
			if (res2 == 0) {
				boolean flag = false;
				while (flag == false) {
					if (res2 == 0) {
						String x_node_s = xcord.getText();
						String y_node_s = ycord.getText();
						int node_dir = direction.getSelectedIndex();
						int node_val = value.getSelectedIndex();
						
						int node_x = Integer.parseInt(x_node_s);
						int node_y = Integer.parseInt(y_node_s);
						
						if (node_dir == 0) {
							if (node_val == 0) {
								a1.nodes[node_x][node_y].setNorth_link(true);
							} else {
								a1.nodes[node_x][node_y].setNorth_link(false);
							}
						} else if (node_dir == 1) {
							if (node_val == 0) {
								a1.nodes[node_x][node_y].setSouth_link(true);
							} else {
								a1.nodes[node_x][node_y].setSouth_link(false);
							}
						} else if (node_dir == 2) {
							if (node_val == 0) {
								a1.nodes[node_x][node_y].setEast_link(true);
							} else {
								a1.nodes[node_x][node_y].setEast_link(false);
							}
						} else if (node_dir == 3) {
							if (node_val == 0) {
								a1.nodes[node_x][node_y].setWest_link(true);
							} else {
								a1.nodes[node_x][node_y].setWest_link(false);
							}
						}
						res2 = 2;
					}
					res2 = JOptionPane.showOptionDialog(null, manPanel, "SOC GUI 0.1 -> Link Status", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttons, buttons[1]);
					if (res2 == 1) {
						flag = true;
						System.out.println("\n\nNew:");
						a1.getArrayStatus();
					}
				}
			}
		}
		//a1.nodes[2][1].setNorth_link(false);
		System.out.println("\n### Backtrack ###\n");
		a1.backtrackingControl();
		a1.getRTStatus();
		a1.routing(xpos, ypos, destx, desty);
	}
	
	public void routing(int xpos, int ypos, int destx, int desty) {

		int i = xpos;
		int j = ypos;
		boolean loop = true;
		
		System.out.println("\n\nDyXY-Routing with usage of backtracking information");
		System.out.println("Starting: " + xpos + "," + ypos
				+ "\tDestination: " + destx + "," + desty + "\n");
		Node[][] a1 = this.nodes;
		
		do {
			System.out.println("Current [" + i + "][" + j + "]");
            if (ypos <= desty) {
				if (destx == i && desty == j) {
					System.out.println("Response[" + i + "][" + j + "]: OK");
                	loop = false;
                	break;
				} else if (desty == j) {
					if (destx < i) {
						Node node = a1[i][j];
						if (node.getNorth_link()) {
							i--;
						} else {
							System.out.println("Fail [" + i + "][" + j + "]");
							break;
						}
					} else {
						Node node = a1[i][j];
						if (node.getSouth_link()) {
							i++;
						} else {
							System.out.println("Fail [" + i + "][" + j + "]");
							break;
						}
					}
				} else if (destx == i) {
					Node node = a1[i][j];
					if (node.getEast_link()) {
						j++;
					} else {
						System.out.println("Fail [" + i + "][" + j + "]");
						break;
					}
				} else {
					// Usually check loads/congestion here but our case we just choose N first and then E and finally S
					Node node = a1[i][j];
					//System.out.println("RT: " + Node.getRTbyIndex(0));
					// If current Node North link is OK AND it is not on first row AND Its neighbor node is not in RT list
//					if (node.east_link_is_OK.getRT() != null) {
						if (j != this.ysize - 1 && compareRT(a1[i][j], destx, desty, a1[i][j].east_link_is_OK)) {
							j++;
						} else if (i != 0 && compareRT(a1[i][j], destx, desty, a1[i][j].north_link_is_OK)) {
							i--;
						} else if (node.getSouth_link() && i != this.xsize - 1) {
							i++;
						} else {
							System.out.println("Fail [" + i + "][" + j + "]");
							break;
						}
				}
			} else if (ypos >= desty) {
				//TODO
				return;
			}
		} while (loop == true);
	}
	
	public void backtrackingControl() {
		// Backtracking control
		// Maximum source-destination diagonal calculating
		int xpos = xsize - 1;
		int ypos = 0;
		int destx = 0;
		int desty = ysize - 1;
		ArrayList list = new ArrayList<String>();
		
		if (this.backtrack(xpos, ypos, destx, desty, list)) {
			System.out.println("Destination Reached with Backtracking");
		} else {
			System.out.println("Backtracking Finished");
		}
	}
	
	public boolean compareRT(Node node, int i, int j, Link link) {
		for (String s1 : link.getRT()) {
			String result1 = i + "," + j;
			System.out.println("Comp: " + result1 + " to " + s1);
			String[] result2 = s1.split(",");
			int res[] = new int[4];
			for (int k = 0; k < result2.length; k++) {
				res[k] = Integer.parseInt(result2[k]);
			}
			System.out.println(res[1]);
			if (j >= res[1] && j <= res[3] && i >= res[0] && i <= res[2]) {
				return false;
			}
			
			//if (result1.equals(result2[1])) {
			//	return false;
			//}
		}		
		return true;
	}
	
	public boolean compareArea(ArrayList<String> list, String string) {
		for (String s1 : list) {
			String[] result1 = string.split(",");
			System.out.println("Comp: " + string + " to " + s1);
			String[] result2 = s1.split(",");
			int res[] = new int[4];
			for (int k = 0; k < result2.length; k++) {
				res[k] = Integer.parseInt(result2[k]);
			}
			int res2[] = new int[4];
			for (int k = 0; k < result1.length; k++) {
				res2[k] = Integer.parseInt(result1[k]);
			}
			System.out.println(res[1]);
			if (res2[1] >= res[1] && res2[3] <= res[3] && res2[0] >= res[0] && res2[2] <= res[2]) {
				return false;
			}
		}		
		return true;
	}
	
	public String compList(ArrayList<String> list, String s1) {
		String rez = null;
		boolean flag = false;
		String comp = null;
		
		if (list.isEmpty()) {
			rez = s1;
		} else {	
			for (String string : list) {
				rez = "";
				System.out.println("Compare: " + string + " to " + s1);
				String[] result1 = string.split(",");
				String[] result2 = s1.split(",");
				int res[] = new int[4];
				for (int k = 0; k < result1.length; k++) {
					res[k] = Integer.parseInt(result1[k]);
				}
				int res2[] = new int[4];
				for (int k = 0; k < result2.length; k++) {
					res2[k] = Integer.parseInt(result2[k]);
				}
				
				if (retangleCheck(res, res2)) {
					flag = true;
					for (int k = 0; k < 4; k++) {
						if (k < 2) { // Compare for minimal part
							if (res[k] < res2[k]) {
								if (k == 3) {
									rez = rez + Integer.toString(res[k]);
								} else {
									rez = rez + res[k] + ",";
								}
							} else {
								if (k == 3) {
									rez = rez + Integer.toString(res2[k]);
								} else {
									rez = rez + res2[k] + ",";
								}
							}
						} else { // Compare for maximum part
							if (res[k] > res2[k]) {
								if (k == 3) {
									rez = rez + Integer.toString(res[k]);
								} else {
									rez = rez + res[k] + ",";
								}
							} else {
								if (k == 3) {
									rez = rez + Integer.toString(res2[k]);
								} else {
									rez = rez + res2[k] + ",";
								}
							}
						}	
					}
					comp = string;
				} else {
					for (int k = 0; k < 4; k++) {
						if (k == 3) {
							rez = rez + Integer.toString(res[k]);
						} else {
							rez = rez + res[k] + ",";
						}
					}
				}
			}
			if (flag) {
				for (int l = 0; l < list.size(); l++) {
					if (list.get(l).equals(comp)) {
						list.remove(l);
					}
				}
				flag = false;
			}
		}
		System.out.println("CompResult: " + rez);
		return rez;
	}
	
	public boolean retangleCheck(int array[], int array2[]) {
		
		// Tuleks võibolla eraldi klassi peale mõelda..
		
		int[] temp = new int[4];
		int[] temp2 = new int[4];
		
		int j = 0;
		for (int i : array) {
			temp[j] = i;
			j++;
		}
		j = 0;
		for (int i : array2) {
			temp2[j] = i;
			j++;
		}
		
		if (temp[0] != 0) {
			temp[0] = temp[0] - 1;
		}
		if (temp[1] != 0) {
			temp[1] = temp[1] - 1;
		}
		if (temp[2] != (this.xsize - 1)) {
			temp[2] = temp[2] + 1;
		}
		if (temp[3] != (this.ysize - 1)) {
			temp[3] = temp[3] + 1;
		}
		
		for (int k = 0; k < 4; k++) {
			if (k < 2) { // Compare for minimal part
				if (temp[k] < temp2[k]) {
					return true;
				}
			} else {
				if (temp[k] > temp2[k]) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean listCheck(ArrayList<String> list, String s2) {
		for (String s1 : list) {
			if (s1 == s2) {
				return false;
			}
		}
		if (s2 == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public void getRTStatus() {
		System.out.println("\n##### Retangles #####");

		Node[][] a1 = this.getArrays();
		Link link;
		
		System.out.println("");
		for (int i = 0; i < a1.length; i++) {		
			for (int j = 0; j < a1[i].length; j++) {
				link = a1[i][j].east_link_is_OK;
				for (String s1 : link.getRT()) {
					if (s1 != null || s1 != "") {
						System.out.println("East link [" + i + "," + j + "] " + s1);
					}
				}
				link = a1[i][j].north_link_is_OK;
				for (String s1 : link.getRT()) {
					if (s1 != null || s1 != "") {
						System.out.println("North link [" + i + "," + j + "] " + s1);
					}
				}
				link = a1[i][j].south_link_is_OK;
				for (String s1 : link.getRT()) {
					if (s1 != null || s1 != "") {
						System.out.println("South link [" + i + "," + j + "] " + s1);
					}
				}
				link = a1[i][j].west_link_is_OK;
				for (String s1 : link.getRT()) {
					if (s1 != null || s1 != "") {
						System.out.println("West link [" + i + "," + j + "] " + s1);
					}
				}
			}
		}
	}
}
