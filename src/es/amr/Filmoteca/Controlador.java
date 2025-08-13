package es.amr.Filmoteca;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;



public class Controlador implements WindowListener, ActionListener, MouseListener
{
	Modelo modelo;
	Vista vista;
	Connection connection = null;
	String rutaCarpeta = "Listas";
	
	public Controlador(Modelo m, Vista v) 
	{
		modelo = m;
		vista = v;
		// Ventanas
		v.addWindowListener(this);
		v.vAnadir.addWindowListener(this);
		v.vLista.addWindowListener(this);
		v.vBuscar.addWindowListener(this);
		v.vBusqueda.addWindowListener(this);
		v.feedback.addWindowListener(this);
		v.dlgExportar.addWindowListener(this);
		// Botones
		v.btnAnadir.addActionListener(this);
		v.btnBuscar.addActionListener(this);
		v.btnLista.addActionListener(this);
		v.btnExcel.addActionListener(this);
		v.btnCSV.addActionListener(this);
		v.btnSalir.addActionListener(this);
		v.btnVolver.addActionListener(this);
		v.btnAceptar.addActionListener(this);
		v.btnBuscarBuscar.addActionListener(this);
		v.btnMostrar.addActionListener(this);
		v.btnVerFicha.addActionListener(this);
		
		v.lblExportar2.addMouseListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
	//VENTANA Filmoteca (Menú Principal)
		//BOTÓN Salir
		if (e.getSource().equals(vista.btnSalir))
			{
				System.exit(0);
			}
		//BOTÓN Añadir película
		else if (e.getSource().equals(vista.btnAnadir))
		{
			vista.txfTitulo.setText("");
			vista.txfTitulo.requestFocus();
			vista.txfAnio.setText("");
			vista.txfDirector.setText("");
			vista.txfSeccion.setText("");
			vista.txfImagen.setText("");
			vista.txfUrl.setText("");
			vista.setVisible(false);
			vista.vAnadir.setVisible(true);
		}
		//BOTÓN Ver lista
		else if (e.getSource().equals(vista.btnLista)) 
		{
			vista.listado.setText("");
			connection = modelo.conectar();
			vista.listado.append(modelo.obtenerPeliculas(connection));
			vista.lblTotalNumero.setText(modelo.totalPeliculas(connection)+"");
			modelo.desconectar(connection);
			vista.setVisible(false);
			vista.vLista.setVisible(true);
		}
		//BOTÓN Buscar
		else if (e.getSource().equals(vista.btnBuscar)) 
		{
			vista.setVisible(false);
			vista.txfBuscar.setText("");
			vista.vBuscar.setVisible(true);
		}
	//VENTANA Ver Lista
		else if (e.getSource().equals(vista.btnExcel)) 
		{
			connection = modelo.conectar();
			if(modelo.excel(connection)) 
			{
				vista.dlgExportar.setVisible(true);
			}
			else
			{
				vista.mensaje.setText("Error al crear el archivo.");
				vista.feedback.setVisible(true);
			}
			modelo.desconectar(connection);
		}
		else if (e.getSource().equals(vista.btnCSV)) 
		{
			connection = modelo.conectar();
			if(modelo.csv(connection)) 
			{
				vista.dlgExportar.setVisible(true);
			}
			else
			{
				vista.mensaje.setText("Error al crear el archivo.");
				vista.feedback.setVisible(true);
			}
			modelo.desconectar(connection);
		}
		
	//VENTANA Añadir película
		else if(e.getSource().equals(vista.btnAceptar)) 
		{
			if (!vista.txfTitulo.getText().isEmpty()) 
			{
				connection = modelo.conectar();
				if (modelo.altaPelicula(connection, vista.txfTitulo.getText(), vista.txfAnio.getText(),
						vista.txfDirector.getText(), vista.txfSeccion.getText(), vista.txfImagen.getText(),
						vista.txfUrl.getText()))
				{
					vista.txfTitulo.setText("");
					vista.txfTitulo.requestFocus();
					vista.txfAnio.setText("");
					vista.txfDirector.setText("");
					vista.txfSeccion.setText("");
					vista.txfImagen.setText("");
					vista.txfUrl.setText("");
					vista.mensaje.setText("Película registrada con éxito");
					vista.feedback.setTitle("Registro completo");
					vista.feedback.setVisible(true);
				} 
				else
				{
					vista.mensaje.setText("Error en Alta");
					vista.feedback.setVisible(true);
				}
				modelo.desconectar(connection);
			}
			else 
			{
				vista.mensaje.setText("Debe indicar un título");
				vista.feedback.setTitle("Fallo en el registro");
				vista.feedback.setVisible(true);
			}
		}
		else if(e.getSource().equals(vista.btnVolver)) 
		{
			vista.vAnadir.dispose();
			vista.setVisible(true);
		}
	//VENTANA Buscar
		else if(e.getSource().equals(vista.btnMostrar)) 
		{
			vista.liBusqueda.removeAll();
			vista.vBuscar.setVisible(false);
			connection = modelo.conectar();
			vista.totalPeliculas = modelo.totalPeliculas(connection);
			String [] peliculas = new String[vista.totalPeliculas];
			modelo.buscarPeliculas(connection, peliculas);
			for(String peli: peliculas) 
			{
				vista.liBusqueda.add(peli);
			}
			vista.lblTotalNumero2.setText(modelo.totalPeliculas(connection)+"");
			modelo.desconectar(connection);
			vista.vBusqueda.setVisible(true);
		}
		
		else if(e.getSource().equals(vista.btnBuscarBuscar)) 
		{
			if(vista.chkTitulo.getState()) 
			{
				if(!vista.txfBuscar.getText().isBlank()) 
				{
					vista.liBusqueda.removeAll();
					vista.vBuscar.setVisible(false);
					connection = modelo.conectar();
					vista.totalPeliculas = modelo.totalTitulo(connection, vista.txfBuscar.getText());
					String[] peliculas = new String[vista.totalPeliculas];
					modelo.buscarTitulo(connection, peliculas, vista.txfBuscar.getText());
					for (String peli : peliculas)
					{
						vista.liBusqueda.add(peli);
					}
					vista.lblTotalNumero2.setText(modelo.totalTitulo(connection, vista.txfBuscar.getText()) + "");
					modelo.desconectar(connection);
					vista.vBusqueda.setVisible(true);
				}
			}
			else if(vista.chkAnio.getState()) 
			{
				if(!vista.txfBuscar.getText().isBlank() && vista.txfBuscar.getText().matches("\\d{4}")) 
				{
					vista.liBusqueda.removeAll();
					vista.vBuscar.setVisible(false);
					connection = modelo.conectar();
					vista.totalPeliculas = modelo.totalAnio(connection, vista.txfBuscar.getText());
					String [] peliculas = new String[vista.totalPeliculas];
					modelo.buscarAnio(connection, peliculas, vista.txfBuscar.getText());
					for(String peli: peliculas) 
					{
						vista.liBusqueda.add(peli);
					}
					vista.lblTotalNumero2.setText(modelo.totalAnio(connection, vista.txfBuscar.getText())+"");
					modelo.desconectar(connection);
					vista.vBusqueda.setVisible(true);
				}
			}
			else if(vista.chkDirector.getState()) 
			{
				if(!vista.txfBuscar.getText().isBlank()) 
				{
					vista.liBusqueda.removeAll();
					vista.vBuscar.setVisible(false);
					connection = modelo.conectar();
					vista.totalPeliculas = modelo.totalDireccion(connection, vista.txfBuscar.getText());
					String[] peliculas = new String[vista.totalPeliculas];
					modelo.buscarDireccion(connection, peliculas, vista.txfBuscar.getText());
					for (String peli : peliculas)
					{
						vista.liBusqueda.add(peli);
					}
					vista.lblTotalNumero2.setText(modelo.totalDireccion(connection, vista.txfBuscar.getText()) + "");
					modelo.desconectar(connection);
					vista.vBusqueda.setVisible(true);
				}
			}
			else if(vista.chkSeccion.getState()) 
			{
				if(!vista.txfBuscar.getText().isBlank()) 
				{
					vista.liBusqueda.removeAll();
					vista.vBuscar.setVisible(false);
					connection = modelo.conectar();
					vista.totalPeliculas = modelo.totalSeccion(connection, vista.txfBuscar.getText());
					String[] peliculas = new String[vista.totalPeliculas];
					modelo.buscarSeccion(connection, peliculas, vista.txfBuscar.getText());
					for (String peli : peliculas)
					{
						vista.liBusqueda.add(peli);
					}
					vista.lblTotalNumero2.setText(modelo.totalSeccion(connection, vista.txfBuscar.getText()) + "");
					modelo.desconectar(connection);
					vista.vBusqueda.setVisible(true);
				}
			}
		}
		// VENTANA Resultado búsqueda
		else if(e.getSource().equals(vista.btnVerFicha)) 
		try
		{
			connection = modelo.conectar();
			String d = modelo.direccion(connection, Integer.parseInt(vista.liBusqueda.getSelectedItem().split(" -- ")[0]));
			String a = modelo.estreno(connection, Integer.parseInt(vista.liBusqueda.getSelectedItem().split(" -- ")[0]));
			String s = modelo.seccionPeli(connection, Integer.parseInt(vista.liBusqueda.getSelectedItem().split(" -- ")[0]));
			String t = modelo.nombrePeli(connection, Integer.parseInt(vista.liBusqueda.getSelectedItem().split(" -- ")[0]));
			String dur = modelo.duracion(connection, Integer.parseInt(vista.liBusqueda.getSelectedItem().split(" -- ")[0]));
			modelo.desconectar(connection);
			new Ficha(d, a, s, t, dur);
		}
		catch(NullPointerException npe){}
	}
	
	@Override
	public void mouseClicked(MouseEvent e)
	{
		try 
		{
            Desktop.getDesktop().open(new File(rutaCarpeta).getAbsoluteFile());
        } 
		catch (IOException ex) 
		{
            ex.printStackTrace();
        }
	}
	
	@Override
	public void windowClosing(WindowEvent e)
	{
		if (e.getSource().equals(vista))
		{
			System.exit(0);
		}
		else if (e.getSource().equals(vista.vAnadir))
		{
			vista.vAnadir.setVisible(false);
			vista.setVisible(true);
		}
		else if (e.getSource().equals(vista.vLista))
		{
			vista.vLista.setVisible(false);
			vista.setVisible(true);
		}
		else if (e.getSource().equals(vista.vBuscar))
		{
			vista.vBuscar.setVisible(false);
			vista.setVisible(true);
		}
		else if (e.getSource().equals(vista.vBusqueda))
		{
			vista.vBusqueda.setVisible(false);
			vista.vBuscar.setVisible(true);
		}
		else if (e.getSource().equals(vista.feedback))
		{
			vista.feedback.setVisible(false);
		}
		else if (e.getSource().equals(vista.dlgExportar))
		{
			vista.dlgExportar.setVisible(false);
		}
	}

	@Override public void windowActivated(WindowEvent e){}@Override public void windowClosed(WindowEvent e){}
	@Override public void windowDeactivated(WindowEvent e){}@Override public void windowDeiconified(WindowEvent e){}
	@Override public void windowIconified(WindowEvent e){}@Override public void windowOpened(WindowEvent e){}
	@Override public void mousePressed(MouseEvent e){}@Override public void mouseReleased(MouseEvent e){}
	@Override public void mouseEntered(MouseEvent e){}@Override public void mouseExited(MouseEvent e){}
}
