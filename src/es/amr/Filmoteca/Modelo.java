package es.amr.Filmoteca;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Modelo
{
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/filmoteca";
	String login = "adminFilm";
	String password = "FilMoteCa2025;";
	String sentencia = "";
	Connection connection = null;
	Statement statement = null;
	ResultSet resultset = null;
	
	public Connection conectar()
	{
		try
		{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, login, password);
		} 
		catch (ClassNotFoundException cnfe)
		{
			return null;
		} 
		catch (SQLException sqle)
		{
			return null;
		}
		return connection;
	}
	
	public void desconectar(Connection conexion)
	{
		try
		{
			if (conexion != null)
			{
				conexion.close();
			}
		} 
		catch (SQLException sqle)
		{
		}
	}
	
//----------------------Ver Lista----------------------
	public String obtenerPeliculas(Connection connection)
	{
		StringBuilder contenidoTextarea = new StringBuilder();
		try
		{
			sentencia = "SELECT * FROM peliculas ORDER BY tituloPelicula";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			contenidoTextarea.append(String.format("%-40s %-15s %-30s %-50s \n", 
					"Título", "Estreno", "Dirección", "Sección"));
			contenidoTextarea.append("=============================================="
					+ "==================================================\n");
			while (resultset.next())
			{
				contenidoTextarea.append(String.format("%-40s %-15s %-33s %-30s \n",
						resultset.getString("tituloPelicula"), 
						resultset.getString("anioPelicula"), 
						resultset.getString("directorPelicula"),
						resultset.getString("seccionPelicula")));
				contenidoTextarea.append("-----------------------------------------------------"
						+ "-----------------------------------------\n");
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return contenidoTextarea.toString();
	}
	
//----------------------Número Total Películas----------------------
	public int totalPeliculas(Connection conexion) 
	{
		int total = 0;
		try
		{
			sentencia = "SELECT * FROM peliculas";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			while (resultset.next())
			{
				total++;
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return total;
	}
	// Total - Búsqueda título
	public int totalTitulo(Connection conexion, String titulo) 
	{
		int total = 0;
		try
		{
			sentencia = "SELECT * FROM peliculas WHERE tituloPelicula LIKE '%" + titulo + "%' ORDER BY tituloPelicula";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			while (resultset.next())
			{
				total++;
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return total;
	}
	// Total - Búsqueda Año
	public int totalAnio(Connection conexion, String anio) 
	{
		int total = 0;
		try
		{
			sentencia = "SELECT * FROM peliculas WHERE anioPelicula BETWEEN " + anio + " AND YEAR(CURDATE()) ORDER BY anioPelicula, tituloPelicula";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			while (resultset.next())
			{
				total++;
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return total;
	}
	// Total - Búsqueda director/a
	public int totalDireccion(Connection conexion, String direc) 
	{
		int total = 0;
		try
		{
			sentencia = "SELECT * FROM peliculas WHERE directorPelicula LIKE '%" + direc + "%' ORDER BY tituloPelicula";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			while (resultset.next())
			{
				total++;
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return total;
	}
	// Total - Búsqueda Sección
	public int totalSeccion(Connection conexion, String seccion) 
	{
		int total = 0;
		try
		{
			sentencia = "SELECT * FROM peliculas WHERE seccionPelicula = " + seccion + " ORDER BY tituloPelicula";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			while (resultset.next())
			{
				total++;
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return total;
	}
	
//----------------------Base de datos----------------------
	public boolean altaPelicula(Connection connection, String titulo, String anio, String director, String seccion, String duracion, String enlace)
	{
		boolean resultado = false;
		try
		{
			statement = connection.createStatement();
			sentencia = "INSERT INTO peliculas VALUES (null,'" + titulo + "', " + anio + ", '" + director + "', " + seccion + ", '"
														+ enlace + "', " + duracion + ");";
			statement.executeUpdate(sentencia);
			System.out.println(sentencia);
			resultado = true;
		} 
		catch (SQLException sqle)
		{
			System.out.println(sentencia);
			resultado = false;
		}
		return resultado;
	}
	
	/*Modificación película*/
	
//----------------------Búsqueda----------------------
	// Búsqueda - Mostrar todo
	public String [] buscarPeliculas(Connection connection, String [] peliculas)
	{
		int pos = 0;

		try
		{
			sentencia = "SELECT * FROM peliculas ORDER BY tituloPelicula";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			while (resultset.next())
			{
				peliculas[pos] = resultset.getString("idPelicula") + " -- " 
									+ resultset.getString("tituloPelicula") + "\n";
				pos++;
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return peliculas;
	}
	// Búsqueda - Título
	public String [] buscarTitulo(Connection connection, String [] peliculas, String titulo)
	{
		int pos = 0;

		try
		{
			sentencia = "SELECT * FROM peliculas WHERE tituloPelicula LIKE '%" + titulo + "%' ORDER BY tituloPelicula";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			while (resultset.next())
			{
				peliculas[pos] = resultset.getString("idPelicula") + " -- " 
									+ resultset.getString("tituloPelicula") + "\n";
				pos++;
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return peliculas;
	}
	// Búsqueda - Año
	public String [] buscarAnio(Connection connection, String [] peliculas, String anio)
	{
		int pos = 0;

		try
		{
			sentencia = "SELECT * FROM peliculas WHERE anioPelicula BETWEEN " + anio + " AND YEAR(CURDATE()) ORDER BY anioPelicula, tituloPelicula";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			while (resultset.next())
			{
				peliculas[pos] = resultset.getString("idPelicula") + " -- " 
									+ resultset.getString("tituloPelicula") + " -- "
									+ resultset.getString("anioPelicula") + "\n";
				pos++;
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return peliculas;
	}
	// Búsqueda - Director/a
	public String [] buscarDireccion(Connection connection, String [] peliculas, String direc)
	{
		int pos = 0;

		try
		{
			sentencia = "SELECT * FROM peliculas WHERE directorPelicula LIKE '%" + direc + "%' ORDER BY tituloPelicula";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			while (resultset.next())
			{
				peliculas[pos] = resultset.getString("idPelicula") + " -- " 
									+ resultset.getString("tituloPelicula") + " -- "
									+ resultset.getString("directorPelicula") + "\n";
				pos++;
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return peliculas;
	}
	// Búsqueda - Sección
	public String [] buscarSeccion(Connection connection, String [] peliculas, String seccion)
	{
		int pos = 0;

		try
		{
			sentencia = "SELECT * FROM peliculas WHERE seccionPelicula = " + seccion + " ORDER BY tituloPelicula";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			while (resultset.next())
			{
				peliculas[pos] = resultset.getString("idPelicula") + " -- " 
									+ resultset.getString("tituloPelicula") + " -- "
									+ resultset.getString("seccionPelicula") + "\n";
				pos++;
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		return peliculas;
	}
	
//----------------------Ficha----------------------
	public String direccion(Connection connection, int id) 
	{
		String direc = "";
		
		try
		{
			sentencia = "SELECT directorPelicula FROM peliculas WHERE idPelicula = " + id;
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			if (resultset.next())
			{
				direc = resultset.getString("directorPelicula");
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		
		return direc;
	}
	
	public String estreno(Connection connection, int id) 
	{
		{
			String anio = "";
			
			try
			{
				sentencia = "SELECT anioPelicula FROM peliculas WHERE idPelicula = " + id;
				statement = connection.createStatement();
				resultset = statement.executeQuery(sentencia);
				if (resultset.next())
				{
					anio = resultset.getString("anioPelicula");
				}
			} 
			catch (SQLException e)
			{
				System.out.println("Error en la sentencia SQL");
			}
			
			return anio;
		}
	}
	
	public String seccionPeli(Connection connection, int id) 
	{
		String seccion = "";
		
		try
		{
			sentencia = "SELECT seccionPelicula FROM peliculas WHERE idPelicula = " + id;
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			if (resultset.next())
			{
				seccion = resultset.getString("seccionPelicula");
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		
		return seccion;
	}
	
	public String nombrePeli(Connection connection, int id) 
	{
		String titulo = "";
		
		try
		{
			sentencia = "SELECT tituloPelicula FROM peliculas WHERE idPelicula = " + id;
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			if (resultset.next())
			{
				titulo = resultset.getString("tituloPelicula");
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		
		return titulo;
	}
	
	public String duracion(Connection connection, int id) 
	{
		String duracion = "";
		
		try
		{
			sentencia = "SELECT duracionPelicula FROM peliculas WHERE idPelicula = " + id;
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			if (resultset.next())
			{
				duracion = resultset.getString("duracionPelicula");
			}
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
		}
		
		return duracion;
	}
	
//----------------------Exportar----------------------
	// EXCEL
	public boolean excel(Connection connection) 
	{
		boolean resultado = false;
		try
		{
			String nombreArchivo = "Peliculas_Tabla.xlsx";
			String rutaArchivo = "Listas\\" + nombreArchivo;
			String hoja = "Hoja1";
			XSSFWorkbook libro = new XSSFWorkbook();
			XSSFSheet hoja1 = libro.createSheet(hoja);
			String[] header = new String[]{ "Título", "Año de estreno", "Director", "Sección" };
			sentencia = "SELECT * FROM peliculas ORDER BY tituloPelicula";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			int numeroFilas = 0;
			while (resultset.next())
			{
				numeroFilas++;
			}
			String[][] document = new String[numeroFilas][4];
			sentencia = "SELECT * FROM peliculas ORDER BY tituloPelicula";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			int fila = 0;
			while (resultset.next())
			{
				document[fila][0] = resultset.getString("tituloPelicula");
				document[fila][1] = resultset.getString("anioPelicula");
				document[fila][2] = resultset.getString("directorPelicula");
				document[fila][3] = resultset.getString("seccionPelicula");
				fila++;
			}

			// Poner en negrita la cabecera
			CellStyle style = libro.createCellStyle();
			XSSFFont font = libro.createFont();
			font.setBold(true);
			style.setFont(font);
			// Generar los datos para el fichero
			for (int i = 0; i <= document.length; i++)
			{
				XSSFRow row = hoja1.createRow(i); // Se crean las filas
				for (int j = 0; j < header.length; j++)
				{
					if (i == 0)
					{ 	// Para la cabecera
						XSSFCell cell = row.createCell(j); // Se crean las celdas para la cabecera, junto con la posición
						cell.setCellStyle(style); // Se añade el style creado anteriormente
						cell.setCellValue(header[j]); // Se añade la cabecera
					} 
					else
					{ 	// Para el contenido
						XSSFCell cell = row.createCell(j); // Se crean las celdas para el contenido, junto con la posición
						cell.setCellValue(document[i - 1][j]); // Se añade el contenido
					}
				}
			}
			// Crear el fichero
			File file;
			file = new File(rutaArchivo);
			try (FileOutputStream fileOuS = new FileOutputStream(file))
			{
				if (file.exists())
				{ 	// Si el archivo ya existe, se elimina
					file.delete();
					System.out.println("Archivo eliminado");
				}
				// Se guarda la información en el fichero
				libro.write(fileOuS);
				fileOuS.flush();
				fileOuS.close();
				System.out.println("Archivo Creado");
				libro.close();
			} 
			catch (FileNotFoundException e)
			{
				System.out.println("El archivo no se encuentra o está en uso...");
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
			resultado = true;
		} 
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
			resultado = false;
		}
		return resultado;
	}
	
	// CSV
	public boolean csv(Connection connection) 
	{
		boolean resultado = false;
		try
		{
			sentencia = "SELECT * FROM peliculas";
			statement = connection.createStatement();
			resultset = statement.executeQuery(sentencia);
			FileWriter fw = new FileWriter("Listas\\CSVPeliculas.csv", false);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter salida = new PrintWriter(bw);
			salida.println("id, titulo, anio, direct, seccion, enlace, duracion");
			while (resultset.next())
			{
				salida.println(resultset.getInt("idPelicula") + ", " +
						resultset.getString("tituloPelicula") + ", " +
						resultset.getString("anioPelicula") + ", " +
						resultset.getString("directorPelicula") + ", " +
						resultset.getString("seccionPelicula") + ", " +
						resultset.getString("enlacePelicula") + ", " +
						resultset.getString("duracionPelicula"));
			}
			salida.close();
			bw.close();
			fw.close();
			resultado = true;
		} 
		catch (IOException i)
		{
			System.out.println("Error al crear el archivo");
			resultado = false;
		}
		catch (SQLException e)
		{
			System.out.println("Error en la sentencia SQL");
			resultado = false;
		}
		return resultado;
	}
}
