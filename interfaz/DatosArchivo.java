package interfaz;

import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

public class DatosArchivo extends JPanel {
	private JTextField tfNombre;
	private JTextField tfFechaSubida;
	private JTextField tfUltimaModificacion;
	private JLabel lblPropietario;
	private JLabel lblTamao;
	private JTextField tfTamanyo;

	/**
	 * Create the panel.
	 */
	public DatosArchivo() {
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Datos de Archivo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 255)));
		setLayout(new GridLayout(4, 2, 5, 5));
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblNombre);
		
		tfNombre = new JTextField();
		tfNombre.setEditable(false);
		add(tfNombre);
		tfNombre.setColumns(10);
		
		JLabel lblFechaDeSubida = new JLabel("Fecha de Subida");
		lblFechaDeSubida.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblFechaDeSubida);
		
		tfFechaSubida = new JTextField();
		tfFechaSubida.setEditable(false);
		add(tfFechaSubida);
		tfFechaSubida.setColumns(10);
		
		lblPropietario = new JLabel("Propietario");
		lblPropietario.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblPropietario);
		
		tfUltimaModificacion = new JTextField();
		tfUltimaModificacion.setEditable(false);
		add(tfUltimaModificacion);
		tfUltimaModificacion.setColumns(10);
		
		lblTamao = new JLabel("Tama\u00F1o");
		lblTamao.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(lblTamao);
		
		tfTamanyo = new JTextField();
		tfTamanyo.setEditable(false);
		add(tfTamanyo);
		tfTamanyo.setColumns(10);

	}

}
