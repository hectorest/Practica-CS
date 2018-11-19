package interfaz;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JTextField;

public class Comentario extends JPanel {
	private JTextField tfComentario;

	/**
	 * Create the panel.
	 */
	public Comentario() {
		setBorder(new TitledBorder(null, "Comentario", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setLayout(new GridLayout(1, 0, 5, 5));
		
		tfComentario = new JTextField();
		add(tfComentario);
		tfComentario.setColumns(10);

	}

}
