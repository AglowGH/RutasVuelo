package estructurasDatos;
import grafo.*;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class ListaNodo implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Nodo first = null;
	public static int orden;//numero de nodos del grafo
	
	static {
		orden = 0;
	}
	
	public ListaNodo()
	{
		first = null;
	}
	
	public void insertar(Point2D coordenadas, String nombre)
	{
		if(first == null)
		{
			first = new Nodo(nombre,coordenadas);
		}else
		{
			Nodo aux = first;
			while(aux.getNext() != null)
			{
				aux = aux.getNext();
			}
			aux.setNext(new Nodo(nombre,coordenadas));
		}
		orden++;
	}
	
	public Nodo peek()
	{
		return first;
	}
	
	public boolean eliminar(Nodo nodo)
	{
		Nodo aux = first;
		
		while(aux != null)
		{
			if(aux.getNombre().equals(nodo.getNombre()))
			{
				if(aux == first)
				{
					first = first.getNext();
					orden--;
					return true;
				}
				
				if(aux == buscarNodo(orden-1))
				{
					buscarNodo(orden-2).setNext(null);
					orden--;
					return true;
				}
				
				int posicionAnterior = buscarPosicion(aux)-1;
				buscarNodo(posicionAnterior).setNext(aux.getNext());
				orden--;
				return true;
				
			}
			
			aux = aux.getNext();
		}
		return false;
	}
	
	public boolean buscarEntreRangos(Point2D punto,int distancia,int base, int altura)
	{
		//Todo buscar si es posible no insertar un vertice entre determinado espacio
	
		Nodo aux = first;
		while(aux != null)
		{
			
			if(intervalo(punto,aux.getCoordenadas(),distancia,base,altura))
			{
				return true;
			}
			
			aux = aux.getNext();
		}
		
		return false;
	}
	
	private boolean intervalo(Point2D punto,Point2D p2,int distancia,int base, int altura)
	{
		int xInicio = (int)p2.getX() - distancia;
		int xFinal =(int)p2.getX() + base + distancia;
		
		int yInicio = (int)p2.getY() - distancia;
		int yFinal = (int)p2.getY() + altura + distancia;
		
		int x = (int)punto.getX();
		int y = (int)punto.getY();
		
		if((x >= xInicio) && (x <= xFinal) && (y >= yInicio) && (y <= yFinal))return true;
		
		return false;
	}
	
	public Nodo buscarNodo(Point2D punto,int base, int altura)
	{
		Nodo aux = first;
		while(aux != null)
		{
			
			if(intervalo(punto,aux.getCoordenadas(),0,base,altura))
			{
				return aux;
			}
			
			aux = aux.getNext();
		}
		
		return null;
	}
	
	public int buscarPosicion(Nodo nodo)
	{
		int posicion = 0;
		Nodo auxN = first;
		
		while (auxN != null)
		{
			if(auxN.getNombre().equals(nodo.getNombre()))
			{
				return posicion;
			}
			auxN = auxN.getNext();
			posicion++;
		}
		
		return posicion;
		
	}
	
	public Nodo buscarNodo(int posicion)
	{
		Nodo auxN = first;
		for(int i = 0; i < posicion;i++)
		{
			auxN = auxN.getNext();
		}
		return auxN;
	}
	
	
	public Nodo buscarNodo(String nombre)
	{
		Nodo auxN = first;
		for(int i = 0; i < orden;i++)
		{
			if(nombre.equals(auxN.getNombre()))return auxN;
			auxN = auxN.getNext();
		}
		return null;
	}
	
	public void actualizarOrden()
	{
		int contador = 0;
		Nodo aux = first;
		
		while(aux != null)
		{
			contador +=1;
			aux = aux.getNext();
		}
		
		orden = contador;
	}
	
}
