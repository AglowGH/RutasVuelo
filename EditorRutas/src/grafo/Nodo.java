package grafo;

import java.awt.Color;
import java.awt.geom.*;
import java.io.Serializable;

public class Nodo implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String nombre;
	private Point2D coordenadas;
	private Nodo next = null;
	private Color color;
	
	public Nodo(String n,Point2D c)
	{
		nombre = n;
		coordenadas = c;
		color  = Color.black;
	}
	
	public Nodo(String name)
	{
		nombre = name;
		color  = Color.black;
	}
	
	public void setNombre(String n)
	{
		nombre = n;
	}
	
	public String getNombre()
	{
		return nombre;
	}
	
	public void setCoordenadas(Point2D c)
	{
		coordenadas = c;
	}
	
	public Point2D getCoordenadas()
	{
		return coordenadas;
	}
	
	public void setNext(Nodo c)
	{
		next = c;
	}
	
	public Nodo getNext()
	{
		return next;
	}
	
	public void setColor(Color c)
	{
		color = c;
	}
	
	public Color getColor()
	{
		return color;
	}
}
