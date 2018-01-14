package client.view;

import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


/*
 * Opis: Klasa ta jest wykorzystywana w :
 * - oknie g��wnym programu Messanger : wyb�r statusu 
 * - oknie lgowania LoginBox : wyb�r statusu 
 */
@SuppressWarnings({ "serial", "rawtypes" })
public class ComboBoxRenderer extends JLabel implements ListCellRenderer{
	
	// Constructor 
	public ComboBoxRenderer() {
		// Ustawienie textu wzgl�dem ikonki
		setHorizontalAlignment(LEFT);
		setVerticalAlignment(CENTER);
	}
	
	@Override
	public Component getListCellRendererComponent(JList arg0, Object arg1,
			int arg2, boolean arg3, boolean arg4) {
		
		int selectedIndex = (Integer) arg1;


		setFont(arg0.getFont());
		
		return this;
	}
}
