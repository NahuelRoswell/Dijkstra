package RapidoYFurioso;

import java.awt.Color;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;
import org.openstreetmap.gui.jmapviewer.MapPolygonImpl;
import org.openstreetmap.gui.jmapviewer.interfaces.MapMarker;
import org.openstreetmap.gui.jmapviewer.interfaces.MapPolygon;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;

public class Mapa implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Coordinate> coordenadas = new ArrayList<Coordinate>();
	private LinkedList<MapMarker> marcadores = new LinkedList<MapMarker>();
	private JTextField IngresarLongitud;
	private JTextField IngresarLatitud;
	private JTextField distancia;
	private JTextField cantPeajes;
	private JMapViewer mapa;
	private JComboBox<String> seleccionDestino;
	private JComboBox<String> SeleccionInicio;
	private JComboBox<String> AristaBx;
	private JComboBox<String> aristaBx2;
	private JFrame frmRoswellMaps;
	private Grafo grafo;
	private static int ids = 0;
	private int id;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Mapa window = new Mapa();
					window.frmRoswellMaps.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Mapa() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		grafo = new Grafo(0);
		seleccionDestino = new JComboBox<String>();
		SeleccionInicio = new JComboBox<String>();
		AristaBx = new JComboBox<String>();
		aristaBx2 = new JComboBox<String>();
		JCheckBox checkPeaje = new JCheckBox("Peaje");

		frmRoswellMaps = new JFrame();
		frmRoswellMaps.setType(Type.UTILITY);
		frmRoswellMaps.setTitle("Roswell Maps");
		frmRoswellMaps.setBounds(0, 0, 1290, 1046);
		frmRoswellMaps.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRoswellMaps.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 13, 994, 989);
		frmRoswellMaps.getContentPane().add(panel);
		panel.setLayout(null);

		mapa = new JMapViewer();
		mapa.setBounds(0, 0, 994, 989);
		mapa.getMapPosition(-34.5233260, -58.7008550);
		panel.add(mapa);

		JButton ocultarMenu = new JButton("");
		ocultarMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ocultarMenu();
			}

			private void ocultarMenu() {
				panel.setSize(2772, 989);
				mapa.setSize(1267, 989);
				ocultarMenu.setVisible(false);

				JButton aparecerMenu = new JButton("..");
				aparecerMenu.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						panel.setSize(994, 989);
						mapa.setSize(994, 989);
						ocultarMenu.setVisible(true);
						aparecerMenu.setVisible(false);
					}
				});
				aparecerMenu.setBounds(1200, 421, 33, 33);
				mapa.add(aparecerMenu);
			}
		});
		ocultarMenu.setBounds(949, 421, 33, 33);
		mapa.add(ocultarMenu);

		JSeparator separator = new JSeparator();
		separator.setForeground(Color.BLACK);
		separator.setBounds(1028, 139, 231, 12);
		frmRoswellMaps.getContentPane().add(separator);

		JPanel AgregarVertice = new JPanel();
		AgregarVertice.setBounds(1006, 13, 265, 122);
		frmRoswellMaps.getContentPane().add(AgregarVertice);
		AgregarVertice.setLayout(null);

		JLabel lblAgregarCiudad = new JLabel("Latitud");
		lblAgregarCiudad.setBounds(25, 37, 125, 36);
		AgregarVertice.add(lblAgregarCiudad);
		lblAgregarCiudad.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JButton btnAgregarVertice = new JButton("Agregar Ciudad");
		btnAgregarVertice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chequearLatitud() && chequearLongitud()) {
					Coordinate coordenada = tomarCoordenadas();
					coordenadas.add(coordenada);

					grafo.agregarVertice(coordenada);

					crearMarcador(coordenada);
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "La latitud y la longitud ingresada " + "deben ser un número! [nnnn.nn]",
							"¡Error!", JOptionPane.WARNING_MESSAGE);
				}
			}

			private boolean chequearValides(String coordenada) {
				Pattern pat = Pattern.compile("[-]?[\\d]{1,4}|[-]?[\\d]{1,4}[\\.][\\d]{1,2}");

				Matcher mat = pat.matcher(coordenada);

				return mat.matches();
			}

			private boolean chequearLongitud() {
				String longitud = IngresarLongitud.getText();

				return chequearValides(longitud);
			}

			private boolean chequearLatitud() {
				String latitud = IngresarLatitud.getText();

				return chequearValides(latitud);
			}

			private Coordinate tomarCoordenadas() {
				double latitud = Double.parseDouble(IngresarLatitud.getText());
				double longitud = Double.parseDouble(IngresarLongitud.getText());
				Coordinate coordenada = new Coordinate(latitud, longitud);

				return coordenada;
			}
		});
		btnAgregarVertice.setBounds(100, 82, 153, 28);
		AgregarVertice.add(btnAgregarVertice);
		btnAgregarVertice.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JLabel lblLongitud = new JLabel("Longitud");
		lblLongitud.setBounds(25, 0, 125, 36);
		AgregarVertice.add(lblLongitud);
		lblLongitud.setFont(new Font("Tahoma", Font.PLAIN, 17));

		IngresarLongitud = new JTextField();
		IngresarLongitud.setBounds(100, 8, 145, 24);
		AgregarVertice.add(IngresarLongitud);
		IngresarLongitud.setColumns(10);

		IngresarLatitud = new JTextField();
		IngresarLatitud.setBounds(100, 45, 145, 24);
		AgregarVertice.add(IngresarLatitud);
		IngresarLatitud.setColumns(10);

		JPanel AgregarArista = new JPanel();
		AgregarArista.setLayout(null);
		AgregarArista.setBounds(1006, 147, 265, 180);
		frmRoswellMaps.getContentPane().add(AgregarArista);

		JLabel lblCiudad1 = new JLabel("Ciudad");
		lblCiudad1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCiudad1.setBounds(25, 37, 125, 36);
		AgregarArista.add(lblCiudad1);

		JButton btnAgregarArista = new JButton("Agregar ruta");
		btnAgregarArista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (chequearNumeroEntero(distancia.getText())) {
					int inicio = Integer.parseInt((String) AristaBx.getSelectedItem()) - 1;
					int destino = Integer.parseInt((String) aristaBx2.getSelectedItem()) - 1;
					int peso = Integer.parseInt(distancia.getText());
					boolean tienePeaje = checkPeaje.isSelected();

					grafo.agregarArista(inicio, destino, peso, tienePeaje);

					agregarLineaArista(inicio, destino, Color.CYAN);
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "La distancia ingresada " + "debe ser un numero entero [nnnn]",
							"¡Error!", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnAgregarArista.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnAgregarArista.setBounds(100, 121, 145, 28);
		AgregarArista.add(btnAgregarArista);

		JLabel lblCiudad = new JLabel("Ciudad");
		lblCiudad.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCiudad.setBounds(25, 0, 125, 36);
		AgregarArista.add(lblCiudad);

		JLabel lblDistancia = new JLabel("Distancia");
		lblDistancia.setBounds(25, 76, 125, 36);
		AgregarArista.add(lblDistancia);
		lblDistancia.setFont(new Font("Tahoma", Font.PLAIN, 17));

		distancia = new JTextField();
		distancia.setBounds(100, 84, 145, 24);
		AgregarArista.add(distancia);
		distancia.setColumns(10);

		checkPeaje.setBounds(25, 123, 69, 25);
		AgregarArista.add(checkPeaje);
		checkPeaje.setFont(new Font("Tahoma", Font.PLAIN, 17));

		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(Color.BLACK);
		separator_1.setBounds(14, 168, 231, 12);
		AgregarArista.add(separator_1);

		AristaBx.setBounds(100, 9, 145, 22);
		AgregarArista.add(AristaBx);

		aristaBx2.setBounds(100, 46, 145, 22);
		AgregarArista.add(aristaBx2);

		JPanel Dijkstra = new JPanel();
		Dijkstra.setLayout(null);
		Dijkstra.setBounds(1006, 328, 265, 180);
		frmRoswellMaps.getContentPane().add(Dijkstra);

		JLabel lblDestino = new JLabel("Destino");
		lblDestino.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblDestino.setBounds(25, 37, 125, 36);
		Dijkstra.add(lblDestino);

		JButton btnCalcularRuta = new JButton("Calcular");
		btnCalcularRuta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (chequearNumeroEntero(cantPeajes.getText())) {
					int inicio = Integer.parseInt((String) SeleccionInicio.getSelectedItem()) - 1;
					int destino = Integer.parseInt((String) seleccionDestino.getSelectedItem()) - 1;
					int peajes = Integer.parseInt(cantPeajes.getText());

					buscarSolucion(inicio, destino, peajes);
				} else {
					Toolkit.getDefaultToolkit().beep();
					JOptionPane.showMessageDialog(null, "La cantidad de peajes" + "debe ser un número entero [nnnn]",
							"¡Error!", JOptionPane.WARNING_MESSAGE);
				}
			}

			private void buscarSolucion(int inicio, int destino, int peajes) {
				Dijkstra solucion = new Dijkstra(grafo, inicio, destino, peajes);
				solucion.resolver();

				String resultado = solucion.mostrarResultado();
				int distancia = solucion.getDistanciaRecorrida();
				if (resultado.length() > 1)
					for (int i = 1; i < resultado.length(); i++) {
						int a = Integer.parseInt("" + resultado.charAt(i));
						int b = Integer.parseInt("" + resultado.charAt(i - 1));
						agregarLineaArista(a, b, Color.red);
					}

				JOptionPane.showMessageDialog(null,
						"Recorrer en el siguiente orden: " + resultado + "\nLa distancia recorrida es: " + distancia,
						"Solucion", JOptionPane.PLAIN_MESSAGE);
			}

		});
		btnCalcularRuta.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnCalcularRuta.setBounds(100, 121, 145, 28);
		Dijkstra.add(btnCalcularRuta);

		JLabel lblInicio = new JLabel("Inicio");
		lblInicio.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblInicio.setBounds(25, 0, 125, 36);
		Dijkstra.add(lblInicio);

		JLabel lblCantidadDePeajes = new JLabel("Cantidad de\n peajes");
		lblCantidadDePeajes.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblCantidadDePeajes.setBounds(25, 76, 152, 36);
		Dijkstra.add(lblCantidadDePeajes);

		cantPeajes = new JTextField();
		cantPeajes.setColumns(10);
		cantPeajes.setBounds(198, 84, 47, 24);
		Dijkstra.add(cantPeajes);

		seleccionDestino.setBounds(100, 46, 145, 22);
		Dijkstra.add(seleccionDestino);

		SeleccionInicio.setBounds(100, 9, 145, 22);
		Dijkstra.add(SeleccionInicio);

		JPanel guardarCargar = new JPanel();
		guardarCargar.setLayout(null);
		guardarCargar.setBounds(1006, 822, 265, 180);
		frmRoswellMaps.getContentPane().add(guardarCargar);

		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardar();
			}
		});
		btnGuardar.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnGuardar.setBounds(149, 139, 101, 28);
		guardarCargar.add(btnGuardar);

		JButton btnCargar = new JButton("Cargar");
		btnCargar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargar();
			}
		});
		btnCargar.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnCargar.setBounds(29, 139, 91, 28);
		guardarCargar.add(btnCargar);
	}

	private boolean chequearNumeroEntero(String esNumero) {
		Pattern pat = Pattern.compile("[\\d]{1,4}"); // 1-9999

		Matcher mat = pat.matcher(esNumero);
		return mat.matches();
	}

	private void agregarLineaArista(int inicio, int destino, Color color) {
		Coordinate a = coordenadas.get(inicio);
		Coordinate b = coordenadas.get(destino);

		ArrayList<Coordinate> coordenadas = new ArrayList<Coordinate>();
		coordenadas.add(a);
		coordenadas.add(b);
		coordenadas.add(a);

		MapPolygon poligono = new MapPolygonImpl(coordenadas);
		poligono.getStyle().setColor(color);
		mapa.addMapPolygon(poligono);
	}

	private String crearNombre() {
		id = ++ids;
		String identificador = "" + id;
		return identificador;
	}

	private void agregarMarcador(Coordinate coordenada, String identificador, JMapViewer mapa) {
		marcadores.add(new MapMarkerDot(identificador, new Coordinate(coordenada.getLat(), coordenada.getLon())));

		marcadores.getLast().getStyle().setBackColor(Color.RED);
		marcadores.getLast().getStyle().setColor(Color.GREEN);

		mapa.addMapMarker(marcadores.getLast());
	}

	private void añadirComboBox(String nuevaOpcion) {
		seleccionDestino.addItem(nuevaOpcion);
		SeleccionInicio.addItem(nuevaOpcion);
		AristaBx.addItem(nuevaOpcion);
		aristaBx2.addItem(nuevaOpcion);
	}

	private void crearMarcador(Coordinate coordenada) {
		String nombreCiudad = crearNombre();
		agregarMarcador(coordenada, nombreCiudad, mapa);
		añadirComboBox(nombreCiudad);
	}

	public void guardar() {
		try {
			FileOutputStream fos = new FileOutputStream("Mapa.txt");
			ObjectOutputStream out = new ObjectOutputStream(fos);

			out.writeObject(coordenadas);
			out.writeObject(grafo);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void cargar() {
		try {
			FileInputStream fis = new FileInputStream("Mapa.txt");
			ObjectInputStream in = new ObjectInputStream(fis);

			coordenadas = (ArrayList<Coordinate>) in.readObject();
			grafo = (Grafo) in.readObject();

			cargarCoordenadasSerializadas();
			in.close();
		} catch (Exception ex) {
		}
	}

	private void cargarCoordenadasSerializadas() {
		for (Coordinate enCoordenada : coordenadas)
			crearMarcador(enCoordenada);
	}
}
