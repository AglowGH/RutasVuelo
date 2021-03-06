package estructurasDatos;
import grafo.*;

public class Cola
{
	Nodo[] valores = null;
	int max;
	int frente, fondo;
	int contador;
	
	public Cola(int size)
	{
		max = size;
		frente = 0;
		fondo = 0;
		contador = 0;
		valores = new Nodo[size];
	}
	
	public boolean estaVacia()
	{
		return contador == 0;
	}
	
	public boolean estaLlena()
	{
		return contador == max;
	}
	
	public int getSize()
	{
		return max;
	}
	
	public int getContador()
	{
		return contador;
	}
	
	public boolean encolar(Nodo valor)
	{
		if(!estaLlena())
		{
			valores[fondo] = valor;
			fondo = (fondo + 1)%max;
			contador++;
			return true;
		}
		return false;
	}
	
	public Nodo desencolar()
	{
		if(!estaVacia())
		{
			Nodo valor = valores[frente];
			frente = (frente+1)%max;
			contador--;
			return valor;
		}
		return null;
	}
	

	public boolean estaAqui(Nodo valor)
	{
		int frente = this.frente;
		int contador = this.contador;
		
		while(contador > 0)
		{
			if(valores[frente].getNombre().equals(valor.getNombre()))
			{
				return true;
			}
			frente = (frente+1)%max;
			contador--;
		}
		
		return false;
	}
}
