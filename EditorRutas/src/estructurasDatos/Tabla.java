package estructurasDatos;
import grafo.*;

public class Tabla 
{
	public Nodo nodo;
	public boolean visitado;
	public double distancia;
	public Nodo antecesor;
	
	final public double VALOR_MAS_GRANDE = 2_147_483_647;
	
	public Tabla()
	{
		nodo = null;
		visitado = false;
		distancia = VALOR_MAS_GRANDE;//Es el valor mas grande en el rango de los valores permitidos
		antecesor = null;
	}
	
}
