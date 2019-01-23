import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class GUI implements ActionListener {

	public static void main(String [] args) { new GUI(); }
	
	JFrame window;
	JTextField[][] board;
	JButton solveBtn, clearBtn;
	Solver solver = new Solver();

	public GUI() {

		window = new JFrame( "Latin Squares" );
		window.setBounds(20, 20, 200, 230);
		window.getContentPane().setLayout( new BorderLayout() );
		window.getContentPane().setBackground( Color.black );
		window.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		window.setResizable( false );

		board = new JTextField[6][6];

		JPanel boardPnl = new JPanel();
		boardPnl.setBackground( Color.black );
		boardPnl.setLayout(new GridLayout(6,6));
		// add user board
		for(int r=0; r<board.length; r++)
		{
			for(int c=0; c<board[r].length; c++)
			{
				this.board[r][c] = new JTextField();
				this.board[r][c].setHorizontalAlignment(JTextField.CENTER);
				this.board[r][c].setBackground(Color.white);
				boardPnl.add(this.board[r][c]);
			}
		}

		window.getContentPane().add(boardPnl, BorderLayout.CENTER);

		JPanel btnPanel = new JPanel();
		btnPanel.setBackground(Color.black);

		solveBtn = new JButton("Solve");
		solveBtn.addActionListener(this);
		btnPanel.add(solveBtn);

		clearBtn = new JButton("Clear");
		clearBtn.addActionListener(this);
		btnPanel.add(clearBtn);

		window.getContentPane().add(btnPanel, BorderLayout.SOUTH);

		window.setVisible(true);

	}

	

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==this.solveBtn) {
			this.solve();
		} else if(e.getSource()==this.clearBtn) {
			this.clear();
		}
		this.window.invalidate();	
		this.window.repaint();
	}

	public void clear() {

		for(int r=0; r<board.length; r++) {
			for(int c=0; c<board[r].length; c++) {
				this.board[r][c].setText("");
			}
		}
	}

	public void solve() {

		int [][] vals = new int[6][6];

		for(int r=0; r<vals.length; r++) {

			for(int c=0; c<vals[r].length; c++) {

				String s = this.board[r][c].getText();

				if(s.length() > 1) {
					return;
				} else if(s.length()==0) {
					vals[r][c] = 0;
				} else {
					char ch = s.charAt(0);
					int n = ch - '0';
					if(n<1 || n > 6) {
						return;
					}
					vals[r][c] = n;
				}
				
			}

		}

		if(!this.solver.solve(vals)) {
			return;
		}


		for(int r=0; r<vals.length; r++) {

			for(int c=0; c<vals[r].length; c++) {

				this.board[r][c].setText(""+vals[r][c]);

			}

		}
	}

}
