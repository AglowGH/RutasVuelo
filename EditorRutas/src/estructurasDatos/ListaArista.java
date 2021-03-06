package estructurasDatos;
import grafo.*;
import java.awt.Color;
import java.io.Serializable;

public class ListaArista implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Arista first;
	private int contador;
	
	public ListaArista()
	{
		contador = 0;
		first = null;
	}
	
	public void insertar(Arista arista)
	{
		if(first == null)
		{
			first = arista;
		}else
		{
			Arista aux = first;
			while(aux.getNext() != null)
			{
				aux = aux.getNext();
			}
			aux.setNext(arista);
		}
		
		contador++;
	}
	
	public boolean eliminarArista(Arista arista)//Siempre se manda a esta funcion a una arista que exista
	{
		
		if(arista == first)
		{
			first = first.getNext();
			contador--;
			return true;
		}
		
		Arista aux = first;
		
		while(aux.getNext() != null)
		{
			if(aux.getNext() == arista)
			{
				aux.setNext(aux.getNext().getNext());
				contador--;
				return true;
			}
			
			aux = aux.getNext();
		}
		return false;
	}
	
	public void eliminarAristas(Nodo nodo)///Se entrega un nodo que existe
	{
		Arista aux = first;
		Nodo auxA;
		Arista[] marcadas = new Arista[contador];
		int i = 0;
		while(aux != null)
		{
			auxA = aux.getNodo(nodo);
			
			if(auxA != null)
			{
				marcadas[i] = aux;
				i++;
			}
			aux = aux.getNext();
		}
		
		for(int j = 0; j < i;j++)
		{
			eliminarArista(marcadas[j]);
		}
	}
	
	public Arista peek() {
		return first;
	}
	
	public int getContador()
	{
		return contador;
	}
	
	//Buscar si arista existe en dos punto seleccionados, si es asi retornala
	public Arista buscarArista(Nodo n1, Nodo n2)
	{
		Arista aux = first;
		
		while(aux != null)
		{
			if(aux.getPrimerNodo().getNombre().equals(n1.getNombre()) && aux.getSegundoNodo().getNombre().equals(n2.getNombre())) return aux;
			if(aux.getPrimerNodo().getNombre().equals(n2.getNombre()) && aux.getSegundoNodo().getNombre().equals(n1.getNombre())) return aux;
			
			aux = aux.getNext();
		}
		
		return null;
	}
	
	public void regresarColor()
	{
		Arista aux = first;
		
		while(aux != null)
		{
			aux.setColor(Color.black);
			aux.getPrimerNodo().setColor(Color.black);
			aux.getSegundoNodo().setColor(Color.black);
			
			aux = aux.getNext();
		}
	}
	
	public Arista aristaMasChica()
	{
		if(first!=null)
		{
			Arista arista = first;
			Arista resultado = first;
			
			while(arista != null)
			{
				
				if(arista.getPeso() < resultado.getPeso())resultado = arista;
				
				arista = arista.getNext();
			}
			
			return resultado;
		}
		
		return null;
	}
}
