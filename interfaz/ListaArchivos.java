package interfaz;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.border.LineBorder;
import javax.swing.JMenuItem;
import javax.swing.JScrollBar;

public class ListaArchivos extends JPanel {

	/**
	 * Create the panel.
	 */
	public ListaArchivos() {
		setBorder(new TitledBorder(null, "Lista de Archivos en la Nube", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setLayout(new GridLayout(1, 1, 5, 5));
		
		JList list = new JList();
		list.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {"Archivo1.txt", "Archivo2.mp3", "Archivo3.zip"};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		add(list);

	}
}
