package estructurasDatos;

public class Pila 
{
	private String[] valores;
	private int contador;
	private int maximo;
	
	public Pila(int size)
	{
		valores = new String[size];
		contador = 0;
		maximo = size;
	}
	
	private boolean estaVacia()
	{
		return contador == 0;
	}
	
	private boolean estaLlena()
	{
		return contador == maximo;
	}
	
	public boolean push(String cadena)
	{
		if(!estaLlena())
		{
			valores[contador++] = cadena;
			return true;
		}
		
		return false;
	}
	
	public String pop()
	{
		if(!estaVacia())
		{
			return valores[--contador];
		}
		return null;
	}
	
	public String peek()
	{
		if(!estaVacia())
		{
			return valores[contador-1];
		}
		return null;
	}
}
