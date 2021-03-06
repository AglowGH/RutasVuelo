package grafo;
import java.awt.Color;
import java.io.Serializable;

public class Arista implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Nodo n1;
	private Nodo n2;
	private double peso;
	private Arista next = null;
	private Color color;
	
	public Arista(Nodo primerNodo, Nodo segundoNodo,int peso)
	{
		n1 = primerNodo;
		n2 = segundoNodo;
		this.peso = peso;
		color  = Color.black;
	}
	
	public void setPrimerNodo(Nodo nodo)
	{
		n1 = nodo;
	}
	
	public Nodo getPrimerNodo()
	{
		return n1;
	}
	
	public void setSegundoNodo(Nodo nodo)
	{
		n2 = nodo; 
	}
	
	public Nodo getSegundoNodo()
	{
		return n2;
	}
	
	public void setPeso(double peso)
	{
		this.peso = peso;
	}
	
	public double getPeso()
	{
		return peso;
	}
	
	public void setNext(Arista arista)
	{
		next = arista;
	}
	
	public Arista getNext()
	{
		return next;
	}
	
	public Nodo getNodo(Nodo n)
	{
		
		if(n1.getNombre().equals(n.getNombre()))return n2;
		if(n2.getNombre().equals(n.getNombre()))return n1;
		
		return null;
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
