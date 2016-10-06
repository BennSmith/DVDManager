import java.awt.Color;

/*	Author: 	Ben Smith
 * 	Date: 		April 24, 2016
 * 	
 * 	CSE 274 Final Project
 * 
 */
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Style {

	public Style() {

	}

	public void styleFrame(JFrame frm) {
		frm.getContentPane().setBackground(new Color(238, 238, 238));
	}
	
	public void styleJPanel(JPanel pnl) {
		pnl.setBackground(new Color(33,33,33));
	}

	public void styleButton(final JButton btn) {
		final Color buttonBackground = new Color(33,33,33);
		final Color buttonForeground = Color.WHITE;
		final Border buttonBorder = BorderFactory.createLineBorder(buttonBackground, 1);

		final Color buttonHoverBackground = new Color(66,66,66);
		final Color buttonHoverForeground = Color.WHITE;
		final Border buttonHoverBorder = BorderFactory.createLineBorder(buttonHoverBackground, 1);

		btn.setBackground(buttonBackground);
		btn.setBorder(buttonBorder);
		btn.setOpaque(true);
		btn.setForeground(Color.WHITE);
		btn.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				btn.setBorder(buttonHoverBorder);
				btn.setBackground(buttonHoverBackground);
				btn.setForeground(buttonHoverForeground);
			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				btn.setBorder(buttonBorder);
				btn.setBackground(buttonBackground);
				btn.setForeground(buttonForeground);
			}
		});
	}
	
	public void styleTextField(final JTextField txt) {
		final Color borderColor = new Color(33,33,33);
		final Color backgroundColor = new Color(245,245,245);
		final Color foregroundColor = new Color(33,33,33);
		
		final Border txtBorder = BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(borderColor, 1),
				BorderFactory.createEmptyBorder(5,5,5,5)
				);
		
		txt.setBackground(backgroundColor);
		txt.setBorder(txtBorder);
		txt.setOpaque(true);
		txt.setForeground(foregroundColor);
	}

	public void styleTextArea(final JTextArea txt) {
		final Color borderColor = new Color(211,47,47);
		final Color backgroundColor = new Color(250, 250, 250);
		final Font txtFont = new Font("Sans-Serif", 16, Font.PLAIN);

		Border textBorder = BorderFactory.createMatteBorder(0, 3, 0, 0, borderColor);
		textBorder = BorderFactory.createCompoundBorder(textBorder,
				BorderFactory.createEmptyBorder(15, 32, 15, 32));

		txt.setBorder(textBorder);
		txt.setBackground(backgroundColor);
		txt.setOpaque(true);
		txt.setForeground(borderColor);
	}

	public void styleTextAreaGood(final JTextArea txt) {
		txt.setForeground(new Color(56,142,60));
		txt.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0,3,0,0,new Color(56,142,60)),
				BorderFactory.createEmptyBorder(15, 32, 15, 32)));
	}

	public void styleTextAreaBad(final JTextArea txt) {
		txt.setForeground(new Color(211,47,47));
		txt.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(0,3,0,0,new Color(211,47,47)),
				BorderFactory.createEmptyBorder(15, 32, 15, 32)));
	}
	
	public void styleScrollPane(final JScrollPane pnl) {
		final Color borderColor= new Color(33,33,33);
		final Color backgroundColor = new Color(248,248,248);
		final Color foregroundColor = new Color(33,33,33);
		
		final Border txtBorder = BorderFactory.createCompoundBorder(
				BorderFactory.createLineBorder(borderColor, 1),
				BorderFactory.createEmptyBorder(5,5,5,5)
				);
		
		pnl.setBackground(backgroundColor);
		pnl.setBorder(txtBorder);
		pnl.setOpaque(true);
		pnl.setForeground(foregroundColor);
	}
}
