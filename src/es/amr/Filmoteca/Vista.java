package es.amr.Filmoteca;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Toolkit;
import java.sql.Connection;

public class Vista extends Frame
{
	int totalPeliculas = 0;
	private static final long serialVersionUID = 1L;
	Image titulo;
	Image btnAdd;
	Image btnAddClick;
	Toolkit herramienta;
	Connection connection = null;
	
//--------------------VENTANA Menú Principal--------------------
	Button btnAnadir = new Button("+ Añadir Película");
	Button btnBuscar = new Button("Buscar");
	Button btnLista = new Button("Ver Lista");
	Button btnSalir = new Button(" Salir ");
	
//--------------------VENTANA Añadir Película--------------------
	Frame vAnadir = new Frame("Añadir Película");
	Label lblTitulo = new Label("Título:");
	TextField txfTitulo = new TextField(20);
	Panel panelTitulo = new Panel();
	Label lblAnio = new Label("Año de Estreno:");
	TextField txfAnio = new TextField(20);
	Panel panelAnio = new Panel();
	Label lblDirector = new Label("Dirección:");
	TextField txfDirector = new TextField(20);
	Panel panelDirector = new Panel();
	Label lblSeccion = new Label("Sección:");
	TextField txfSeccion = new TextField(20);
	Panel panelSeccion = new Panel();
	Label lblDuracion = new Label("Duración (minutos):");
	TextField txfImagen = new TextField(20);
	Panel panelImagen = new Panel();
	Label lblUrl = new Label("URL:");
	TextField txfUrl = new TextField(30);
	Panel panelUrl = new Panel();
	Button btnAceptar = new Button("Aceptar");
	Button btnVolver = new Button("Volver");
	Panel panelBotonesAnadir = new Panel();
	
//--------------------VENTANA Ver Lista--------------------
	Frame vLista = new Frame("Listado Completo");
	TextArea listado = new TextArea(30, 30);
	Label lblTotalPeli = new Label("Total Películas: ");
	Label lblTotalNumero = new Label("XXXX");
	Panel panelTotal = new Panel();
	Button btnExcel = new Button("Exportar Excel");
	Button btnCSV = new Button("Exportar CSV");
	Panel panelListaBtn1 = new Panel();
	
//--------------------VENTANA Buscar--------------------
	Frame vBuscar = new Frame("Buscar");
	CheckboxGroup gpBuscar = new CheckboxGroup();
	Checkbox chkTitulo = new Checkbox("Título", true, gpBuscar);
	Checkbox chkAnio = new Checkbox("Año", false, gpBuscar);
	Checkbox chkDirector = new Checkbox("Director/a", false, gpBuscar);
	Checkbox chkSeccion = new Checkbox("Sección", false, gpBuscar);
	Panel panelBuscar = new Panel();
	TextField txfBuscar = new TextField(25);
	Panel panelBuscar2 = new Panel();
	Button btnBuscarBuscar = new Button("Buscar");
	Button btnMostrar = new Button("Mostrar todo");
	Panel panelBuscar3 = new Panel();
	
	// VENTANA Resultado búsqueda
	Frame vBusqueda = new Frame("Resultado de la búsqueda");
	List liBusqueda = new List(20, false);
	Button btnVerFicha = new Button("Ficha");
	Panel panelBusqueda = new Panel();
	Label lblTotalPeli2 = new Label("Total: ");
	Label lblTotalNumero2 = new Label("XXXX");
	Panel panelTotal2 = new Panel();
	
	// DIÁLOGO Auxiliar
	Dialog feedback = new Dialog(vAnadir, "Éxito", true);
	Label mensaje = new Label("Película registrada con éxito");
	
	// DIÁLOGO Éxito Exportar
	Dialog dlgExportar = new Dialog(vLista, "Exportación completada", true);
	Label lblExportar = new Label("Archivo creado");
	Label lblExportar2 = new Label("Ir a la carpeta");
	Panel pnlExportar = new Panel();
	
	Dimension dimensionBtn2 = new Dimension(110, 32);
	Dimension dimensionBtn = new Dimension(60, 22);
	Font fntButton = new Font("Arial", Font.BOLD, 13);
	Font fntExport = new Font("Arial", Font.BOLD, 15);
	Color clrButton = new Color(189, 200, 255);
	
	public Vista() 
	{
		herramienta = getToolkit();
		titulo = herramienta.getImage("img\\titulo.png");
		
		// VENTANA Menú Principal
		setTitle("Filmoteca");
		setLayout(null);
		setSize(700, 350);
		setBackground(new Color(48, 48, 48));
		setResizable(false);
		setLocationRelativeTo(null);
		btnAnadir.setBounds(275, 190, 150, 35);
		btnAnadir.setFont(new Font("Arial", Font.BOLD, 15));
		btnAnadir.setBackground(new Color(85, 84, 138));
		btnAnadir.setForeground(Color.white);
		add(btnAnadir);
		btnBuscar.setBounds(220, 240, 120, 30);
		btnBuscar.setFont(fntButton);
		btnBuscar.setBackground(clrButton);
		add(btnBuscar);
		btnLista.setBounds(360, 240, 120, 30);
		btnLista.setFont(fntButton);
		btnLista.setBackground(clrButton);
		add(btnLista);
		btnSalir.setBounds(325, 290, 50, 27);
		btnSalir.setForeground(Color.white);
		btnSalir.setBackground(Color.darkGray);
		add(btnSalir);
		setVisible(true);
		
		// VENTANA Añadir Película
		vAnadir.setLayout(new GridLayout(13, 1));
		vAnadir.setSize(450, 600);
		vAnadir.setBackground(Color.gray);
		vAnadir.setResizable(true);
		vAnadir.setLocationRelativeTo(null);
		panelTitulo.add(txfTitulo);
		lblTitulo.setAlignment(Label.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
		vAnadir.add(lblTitulo);
		vAnadir.add(panelTitulo);
		panelAnio.add(txfAnio);
		vAnadir.add(lblAnio);
		lblAnio.setAlignment(Label.CENTER);
		lblAnio.setFont(fntButton);
		vAnadir.add(panelAnio);
		panelDirector.add(txfDirector);
		vAnadir.add(lblDirector);
		lblDirector.setAlignment(Label.CENTER);
		lblDirector.setFont(fntButton);
		vAnadir.add(panelDirector);
		panelSeccion.add(txfSeccion);
		vAnadir.add(lblSeccion);
		lblSeccion.setAlignment(Label.CENTER);
		lblSeccion.setFont(fntButton);
		vAnadir.add(panelSeccion);
		panelImagen.add(txfImagen);
		vAnadir.add(lblDuracion);
		lblDuracion.setAlignment(Label.CENTER);
		lblDuracion.setFont(new Font("Arial", Font.PLAIN, 13));
		vAnadir.add(panelImagen);
		panelUrl.add(txfUrl);
		vAnadir.add(lblUrl);
		lblUrl.setAlignment(Label.CENTER);
		lblUrl.setFont(new Font("Arial", Font.PLAIN, 13));
		vAnadir.add(panelUrl);
		btnAceptar.setPreferredSize(dimensionBtn2);
		btnAceptar.setFont(fntButton);
		btnAceptar.setBackground(Color.lightGray);
		btnVolver.setPreferredSize(dimensionBtn2);
		btnVolver.setFont(fntButton);
		btnVolver.setForeground(Color.white);
		btnVolver.setBackground(Color.darkGray);
		panelBotonesAnadir.add(btnAceptar);
		panelBotonesAnadir.add(btnVolver);
		vAnadir.add(panelBotonesAnadir);
		
		// VENTANA Ver Lista
		vLista.setLayout(new BorderLayout());
		vLista.setSize(900, 700);
		vLista.setBackground(Color.gray);
		vLista.setResizable(true);
		vLista.setLocationRelativeTo(null);
		listado.setFont(new Font("Monospaced", Font.BOLD, 14));
		listado.setBackground(Color.lightGray);
		listado.setEditable(false);
		vLista.add(listado, BorderLayout.NORTH);
		lblTotalPeli.setFont(fntButton);
		lblTotalNumero.setFont(new Font("Arial", Font.BOLD, 14));
		panelTotal.add(lblTotalPeli);
		panelTotal.add(lblTotalNumero);
		vLista.add(panelTotal, BorderLayout.CENTER);
		btnExcel.setPreferredSize(dimensionBtn2);
		btnCSV.setPreferredSize(dimensionBtn2);
		btnExcel.setFont(fntButton);
		btnExcel.setForeground(Color.white);
		btnExcel.setBackground(new Color(27, 27, 125));
		btnCSV.setFont(fntButton);
		btnCSV.setForeground(Color.white);
		btnCSV.setBackground(new Color(158, 25, 25));
		panelListaBtn1.add(btnExcel);
		panelListaBtn1.add(btnCSV);
		vLista.add(panelListaBtn1, BorderLayout.SOUTH);
		
		// VENTANA Buscar
		vBuscar.setLayout(new GridLayout(3, 1));
		vBuscar.setSize(450, 250);
		vBuscar.setBackground(Color.gray);
		vBuscar.setResizable(true);
		vBuscar.setLocationRelativeTo(null);
		panelBuscar.add(chkTitulo);
		panelBuscar.add(chkAnio);
		panelBuscar.add(chkDirector);
		panelBuscar.add(chkSeccion);
		vBuscar.add(panelBuscar);
		panelBuscar2.add(txfBuscar);
		vBuscar.add(panelBuscar2);
		btnBuscarBuscar.setPreferredSize(dimensionBtn2);
		btnBuscarBuscar.setFont(fntButton);
		btnBuscarBuscar.setBackground(Color.lightGray);
		panelBuscar3.add(btnBuscarBuscar);
		btnMostrar.setPreferredSize(dimensionBtn2);
		btnMostrar.setFont(fntButton);
		btnMostrar.setBackground(Color.lightGray);
		panelBuscar3.add(btnMostrar);
		vBuscar.add(panelBuscar3);
		
			// Ventana Resultado Búsqueda
		vBusqueda.setLayout(new BorderLayout());
		vBusqueda.setSize(450, 490);
		vBusqueda.setBackground(Color.gray);
		vBusqueda.setResizable(true);
		vBusqueda.setLocationRelativeTo(null);
		liBusqueda.setFont(new Font("Arial", Font.BOLD, 14));
		liBusqueda.setBackground(Color.lightGray);
		vBusqueda.add(liBusqueda, BorderLayout.NORTH);
		lblTotalPeli2.setFont(fntButton);
		lblTotalNumero2.setFont(new Font("Arial", Font.BOLD, 14));
		panelTotal2.add(lblTotalPeli2);
		panelTotal2.add(lblTotalNumero2);
		vBusqueda.add(panelTotal2, BorderLayout.CENTER);
		btnVerFicha.setPreferredSize(dimensionBtn2);
		btnVerFicha.setFont(fntButton);
		btnVerFicha.setForeground(Color.darkGray);
		btnVerFicha.setBackground(new Color(230, 230, 0));
		panelBusqueda.add(btnVerFicha);
		vBusqueda.add(panelBusqueda, BorderLayout.SOUTH);
		
		// DIÁLOGO Auxiliar
		feedback.setLayout(new FlowLayout());
		feedback.setSize(240, 100);
		feedback.setResizable(false);
		feedback.add(mensaje);
		feedback.setLocationRelativeTo(null);
		
		// DIÁLOGO Exportar
		dlgExportar.setLayout(new GridLayout(2,1));
		dlgExportar.setSize(260, 150);
		dlgExportar.setResizable(false);
		lblExportar.setAlignment(Label.CENTER);
		lblExportar.setFont(fntExport);
		lblExportar2.setFont(fntExport);
		lblExportar2.setPreferredSize(new Dimension(105,20));
		lblExportar2.setAlignment(Label.CENTER);
		lblExportar2.setForeground(Color.BLUE);
		lblExportar2.setBackground(Color.lightGray);
		lblExportar2.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnlExportar.add(lblExportar2);
		dlgExportar.add(lblExportar);
		dlgExportar.add(pnlExportar);
		dlgExportar.setLocationRelativeTo(null);
	}
	
	public void paint(Graphics g) 
	{
		g.drawImage(titulo, 0, 30, this);
	}
}
