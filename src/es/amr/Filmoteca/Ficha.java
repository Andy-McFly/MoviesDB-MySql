package es.amr.Filmoteca;

import java.awt.Button;
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
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;

public class Ficha extends Frame implements WindowListener, ActionListener, MouseListener, MouseMotionListener
{
	private static final long serialVersionUID = 1L;
	Modelo modelo = new Modelo();
	int clickX, clickY;
	int idPelicula;
	String direc = "";
	String anio = "";
	String dura = "";
	String enlace = "";
	String seccion = "";
	String duracion = "";
	String titulo = "Título de la película";
	Font fntInfo = new Font("Calibri", Font.BOLD, 20);
	Image poster;
	Toolkit herramienta;
	Connection connection = null;
	
	Button btnEditar = new Button("Editar");
	// VENTANA Editar datos
	Frame vModificar = new Frame("Editando datos");
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
	TextField txfDuracion = new TextField(20);
	Panel panelDuracion = new Panel();
	Label lblUrl = new Label("URL:");
	TextField txfUrl = new TextField(30);
	Panel panelUrl = new Panel();
	Button btnAceptar = new Button("Modificar");
	Button btnVolver = new Button("Volver");
	Panel panelBotonesModificar = new Panel();
	// DIÁLOGO Auxiliar
	Dialog dlgMod = new Dialog(vModificar, "Éxito", true);
	Label lblMod = new Label("Datos de la película modificados");
	
	Dimension dimensionBtn = new Dimension(110, 32);
	Font fntButton = new Font("Arial", Font.BOLD, 13);
	
	public Ficha(int id, String d, String a, String s, String t, String dur, String url) 
	{
		idPelicula = id;
		titulo = t;
		direc = d;
		anio = a;
		seccion = s;
		duracion = dur;
		enlace = url;
		herramienta = getToolkit();
		poster = herramienta.getImage("posters\\" + titulo + ".png");
		
		addWindowListener(this);
		vModificar.addWindowListener(this);
		dlgMod.addWindowListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		btnEditar.addActionListener(this);
		btnAceptar.addActionListener(this);
		btnVolver.addActionListener(this);
		
		// VENTANA Ficha
		setLayout(null);
		setSize(1192, 678);
		setTitle(titulo);
		setBackground(new Color(48, 48, 48));
		setResizable(true);
		setLocationRelativeTo(null);
		btnEditar.setBackground(new Color(85, 84, 138));
		btnEditar.setForeground(Color.white);
		btnEditar.setBounds(602, 540, 125, 45);
		btnEditar.setFont(new Font("Calibri", Font.BOLD, 25));
		add(btnEditar);
		setVisible(true);
		
		// VENTANA Editar datos
		vModificar.setLayout(new GridLayout(13, 1));
		vModificar.setSize(450, 600);
		vModificar.setBackground(Color.gray);
		vModificar.setResizable(true);
		vModificar.setLocationRelativeTo(null);
		txfTitulo.setText(titulo);
		txfAnio.setText(anio);
		txfDirector.setText(direc);
		txfDuracion.setText(duracion);
		txfSeccion.setText(seccion);
		txfUrl.setText(enlace);
		panelTitulo.add(txfTitulo);
		lblTitulo.setAlignment(Label.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
		vModificar.add(lblTitulo);
		vModificar.add(panelTitulo);
		panelAnio.add(txfAnio);
		vModificar.add(lblAnio);
		lblAnio.setAlignment(Label.CENTER);
		lblAnio.setFont(new Font("Arial", Font.BOLD, 13));
		vModificar.add(panelAnio);
		panelDirector.add(txfDirector);
		vModificar.add(lblDirector);
		lblDirector.setAlignment(Label.CENTER);
		lblDirector.setFont(new Font("Arial", Font.BOLD, 13));
		vModificar.add(panelDirector);
		panelSeccion.add(txfSeccion);
		vModificar.add(lblSeccion);
		lblSeccion.setAlignment(Label.CENTER);
		lblSeccion.setFont(new Font("Arial", Font.BOLD, 13));
		vModificar.add(panelSeccion);
		panelDuracion.add(txfDuracion);
		vModificar.add(lblDuracion);
		lblDuracion.setAlignment(Label.CENTER);
		lblDuracion.setFont(new Font("Arial", Font.PLAIN, 13));
		vModificar.add(panelDuracion);
		panelUrl.add(txfUrl);
		vModificar.add(lblUrl);
		lblUrl.setAlignment(Label.CENTER);
		lblUrl.setFont(new Font("Arial", Font.PLAIN, 13));
		vModificar.add(panelUrl);
		btnAceptar.setPreferredSize(dimensionBtn);
		btnAceptar.setBackground(Color.lightGray);
		btnAceptar.setFont(fntButton);
		btnVolver.setPreferredSize(dimensionBtn);
		btnVolver.setForeground(Color.white);
		btnVolver.setBackground(Color.darkGray);
		btnVolver.setFont(fntButton);
		panelBotonesModificar.add(btnAceptar);
		panelBotonesModificar.add(btnVolver);
		vModificar.add(panelBotonesModificar);
		// DIÁLOGO Auxiliar
		dlgMod.setLayout(new FlowLayout());
		dlgMod.setSize(240, 100);
		dlgMod.setResizable(false);
		dlgMod.add(lblMod);
		dlgMod.setLocationRelativeTo(null);
	}
	
	// VENTANA Ficha
	public void paint(Graphics g) 
	{
		g.drawImage(poster, 37, 60, this);
		g.setColor(new Color(150, 150, 150));
		g.setFont(fntInfo);
		g.drawString("Dirección:", 602, 120);
		g.drawString("Año de estreno:", 602, 170);
		g.drawString("Duración:", 602, 220);
		g.drawString("Enlace:", 602, 270);
		g.drawString("Sección:", 602, 320);
		g.setColor(Color.white);
		g.drawString(direc, 710, 120);
		g.drawString(anio, 762, 170);
		g.drawString(duracion + " min.", 680 + 28, 220);
		g.drawString(enlace, 689, 270);
		g.drawString(seccion, 669 + 28, 320);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// VENTANA Ficha
		if (e.getSource().equals(btnEditar))
		{
			vModificar.setVisible(true);
		}
		
		// VENTANA Editar datos
		else if (e.getSource().equals(btnAceptar))
		{
			String tituloMod = txfTitulo.getText();
			String anioMod = txfAnio.getText();
			String direcMod = txfDirector.getText();
			String seccionMod = txfSeccion.getText();
			String duracionMod = txfDuracion.getText();
			String enlaceMod = txfUrl.getText();
			connection = modelo.conectar();
			if(modelo.modificarPelicula(connection, idPelicula, tituloMod, anioMod, direcMod, seccionMod, duracionMod, enlaceMod)) 
			{
				dlgMod.setVisible(true);
			}
			modelo.desconectar(connection);
		}
		
		else if (e.getSource().equals(btnVolver))
		{
			vModificar.dispose();
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent me)
	{
		clickX = me.getX();
		clickY = me.getY();
		if(clickX >= 690 && clickX <= 1145 && clickY >= 253 && clickY <= 272) 
		{
			modelo.web(enlace);
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent me)
	{
		clickX = me.getX();
		clickY = me.getY();
		if(clickX >= 690 && clickX <= 1145 && clickY >= 253 && clickY <= 272) 
		{
			setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
		else 
		{
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	}
	
	@Override
	public void windowClosing(WindowEvent e)
	{
		// VENTANA Ficha
		if (e.getSource().equals(this))
		{
			dispose();
		}
		// VENTANA Editar datos
		else if (e.getSource().equals(vModificar))
		{
			vModificar.dispose();
		}
		else if (e.getSource().equals(dlgMod))
		{
			dlgMod.dispose();
		}
	}
	
	@Override public void windowActivated(WindowEvent e){}@Override public void windowClosed(WindowEvent e){}
	@Override public void windowDeactivated(WindowEvent e){}@Override public void windowDeiconified(WindowEvent e){}
	@Override public void windowIconified(WindowEvent e){}@Override public void windowOpened(WindowEvent e){}
	@Override public void mousePressed(MouseEvent e){}@Override public void mouseReleased(MouseEvent e){}
	@Override public void mouseEntered(MouseEvent e){}@Override public void mouseExited(MouseEvent e){}
	@Override public void mouseDragged(MouseEvent e){}
}
