package interfaz;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;

public class SubirArchivo extends JPanel {
	private JTextField tfRutaArchivo;

	/**
	 * Create the panel.
	 */
	public SubirArchivo() {
		setBorder(new TitledBorder(null, "Subir Archivo", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		setLayout(null);
		
		tfRutaArchivo = new JTextField();
		tfRutaArchivo.setBounds(27, 28, 259, 20);
		add(tfRutaArchivo);
		tfRutaArchivo.setColumns(10);
		
		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.setBounds(315, 27, 89, 23);
		add(btnBuscar);
		
		JButton btnSubir = new JButton("Subir");
		btnSubir.setBounds(315, 61, 89, 23);
		add(btnSubir);

	}
}
