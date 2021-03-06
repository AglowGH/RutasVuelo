package grafo;
import estructurasDatos.*;
import gui.*;
public class Grafo 
{
	public static Cola colaBFS;
	public static Cola colaDFS;
	public static double [][]mAdyacencia;
	public static Tabla[] tabla;
	final static double VALOR_MAS_GRANDE = 2_147_483_647;
	public static Tabla[] tablaPrim;
	
	static {
		colaBFS = null;
		colaDFS = null;
		mAdyacencia = null;
		tabla = null;
	}
	
	public Grafo()
	{
		
	}
	
	public boolean eliminarNodo()
	{
		return false;
	}
	
	public boolean eliminarArista()
	{
		return false;
	}
	
	public static void actualizarInfo()
	{
		MyFrame.hilo = new ThreadColores();
		MyPanel.aristas.regresarColor();
		colaBFS = new Cola(ListaNodo.orden);
		colaDFS = new Cola(ListaNodo.orden);
		mAdyacencia = new double[ListaNodo.orden][ListaNodo.orden];
		tabla = new Tabla[ListaNodo.orden];
		tablaPrim = new Tabla[ListaNodo.orden];
		
		actualizarMatriz();
		actualizarTabla();
		actualizarTablaPrim();
	}
	
	public static boolean BFS(Nodo nSeleccionado)
	{
		Cola cola = new Cola (ListaNodo.orden);
		
		if(ListaNodo.orden != 0)
		{
			colaBFS.encolar(nSeleccionado);
			cola.encolar(nSeleccionado);
			
			while(!cola.estaVacia())
			{
				Nodo auxN = cola.desencolar();
				int i = MyPanel.lista.buscarPosicion(auxN);
				
				for(int k = 0; k <ListaNodo.orden;k++)
				{
					if(mAdyacencia[i][k] != 0)
					{
						Nodo auxN2 = MyPanel.lista.buscarNodo(k);
						if(!colaBFS.estaAqui(auxN2))
						{
							colaBFS.encolar(auxN2);
							MyFrame.hilo.agrgarArista(MyPanel.aristas.buscarArista(auxN, auxN2));
							cola.encolar(auxN2);
						}
					}
				}
			}
			
			return true;
		}
		return false;
	}
	
	public static void DFS(Nodo nodo)
	{
		int posicion = MyPanel.lista.buscarPosicion(nodo);
		colaDFS.encolar(nodo);
		for(int i = 0; i < ListaNodo.orden; i++)
		{
			if(mAdyacencia[posicion][i] != 0)
			{
				if(!colaDFS.estaAqui(MyPanel.lista.buscarNodo(i)))
				{
					MyFrame.hilo.agrgarArista(MyPanel.aristas.buscarArista(nodo,MyPanel.lista.buscarNodo(i)));
					DFS(MyPanel.lista.buscarNodo(i));
				}
			}
		}
	}
	
	private static void actualizarMatriz()
	{
		Nodo n1 = MyPanel.lista.peek();
		for(int i = 0; i < ListaNodo.orden;i++)
		{
			Nodo n2 = MyPanel.lista.peek();
			for(int j=0;j<ListaNodo.orden;j++)
			{
				Arista auxA = MyPanel.aristas.buscarArista(n1, n2);
				if(auxA != null && n1 != n2)
				{
					mAdyacencia [i][j] = 1;//auxA.getPeso();//////////////////////////////////////////////////////////////////////
				}else 
				{
					mAdyacencia [i][j] = 0;
				}
				n2 = n2.getNext();
			}
			n1 = n1.getNext();
		}
	}
	
	private static void actualizarTabla()
	{
		for(int i=0;i<ListaNodo.orden;i++)
		{
			tabla[i] = new Tabla();
			tabla[i].nodo = MyPanel.lista.buscarNodo(i);
		}
	}
	
	private static void actualizarTablaPrim()
	{
		for(int i=0;i<ListaNodo.orden;i++)
		{
			tablaPrim[i] = new Tabla();
			tablaPrim[i].nodo = MyPanel.lista.buscarNodo(i);
		}
	}
	
	public static void metodoDijkstra(Nodo origen)
	{
		Nodo inicio = new Nodo("Inicio");
		int posicion = buscarVerticeT(origen);
		tabla[posicion].visitado = true;
		tabla[posicion].distancia = 0;
		tabla[posicion].antecesor = inicio;
		while(!dijkstraCompletado())
		{
			for(int i = 0; i < ListaNodo.orden;i++)
			{
				if(tabla[i].visitado)
				{
					for(int j = 0; j< ListaNodo.orden;j++)
					{
						if(mAdyacencia[i][j] != 0)
						{
							if(!tabla[j].visitado)
							{
								double nuevaDistancia =  MyPanel.aristas.buscarArista(tabla[i].nodo, tabla[j].nodo).getPeso() + tabla[i].distancia;//peso y distancia son lo mismo
								if(tabla[j].distancia > nuevaDistancia)
								{
									tabla[j].distancia = nuevaDistancia;
									tabla[j].antecesor = tabla[i].nodo;
								}
								
							}
						}
					}
				}
			}
			
			valorMasChico();
		}
		
	}
	
	public static void valorMasChico()
	{
		double aux = VALOR_MAS_GRANDE;
		int auxPosicion = -1;
		for(int i = 0; i < ListaNodo.orden ;i++)
		{
			if(!tabla[i].visitado && tabla[i].distancia != VALOR_MAS_GRANDE)
			{
				if(tabla[i].distancia < aux)
				{
					aux = tabla[i].distancia;
					auxPosicion = i;
				}
			}
		}
		
		if(auxPosicion != -1)
		{
			tabla[auxPosicion].visitado = true;
		}
	}
	
	public static int buscarVerticeT(Nodo v)
	{
		
		for(int i = 0;i<ListaNodo.orden;i++)
		{
			if(tabla[i].nodo.getNombre().equals(v.getNombre()))return i;
		}
		
		return -1;
	}
	
	public static boolean dijkstraCompletado()
	{
		
		for(int i =0; i < ListaNodo.orden;i++)
		{
			if(!tabla[i].visitado)return false;
		}
		
		return true;
	}
	
	
	public static boolean primCompletado()
	{
		
		for(int i =0; i < ListaNodo.orden;i++)
		{
			if(!tablaPrim[i].visitado)return false;
		}
		
		return true;
	}
	
	
	public static boolean nodosConectados()
	{
		int sumaFila = 0;
		for(int i=0;i<ListaNodo.orden;i++)
		{
			for(int j=0;j<ListaNodo.orden;j++)
			{
				sumaFila += mAdyacencia[i][j];
			}
			if(sumaFila == 0)return false;
			sumaFila =0;
		}
		
		return true;
	}
	
	public static void mostrarDFS()
	{
		String str = "";
		if(ListaNodo.orden > 0)
		{
			while(colaDFS.getContador() > 0)
			{
				str +=colaDFS.desencolar().getNombre() + " ";
			}
			MyFrame.resultadoDFS.setText(str);
		}
	}
	
	public static void mostrarBFS(Nodo nSeleccionado)
	{
		String str = "";
		if(BFS(nSeleccionado))
		{
			while(colaBFS.getContador() > 0)
			{
				str +=colaBFS.desencolar().getNombre() + " ";
			}
			MyFrame.resultadoBFS.setText(str);
		}
	}
	
	
	private static void valorMasChicoPrim()
	{
		double aux = VALOR_MAS_GRANDE;
		int auxPosicion = -1;
		for(int i = 0; i < ListaNodo.orden ;i++)
		{
			if(!tablaPrim[i].visitado && tablaPrim[i].distancia != VALOR_MAS_GRANDE)
			{
				if(tablaPrim[i].distancia < aux)
				{
					aux = tablaPrim[i].distancia;
					auxPosicion = i;
				}
			}
		}
		
		if(auxPosicion != -1)
		{
			tablaPrim[auxPosicion].visitado = true;
		}
	}
	
	public static void prim()
	{
		Arista arista = MyPanel.aristas.aristaMasChica();
		Nodo inicio = new Nodo("Inicio");
		int posicion = buscarVerticeT(arista.getPrimerNodo());
		tablaPrim[posicion].visitado = true;
		tablaPrim[posicion].distancia = 0;
		tablaPrim[posicion].antecesor = inicio;
		
		
		while(!primCompletado())
		{
			
			for(int i = 0; i < ListaNodo.orden;i++)
			{
				if(tablaPrim[i].visitado) 
				{
					for(int j = 0; j< ListaNodo.orden;j++)
					{
						if(mAdyacencia[i][j] != 0)
						{
							if(!tablaPrim[j].visitado)
							{
								double nuevaDistancia =  MyPanel.aristas.buscarArista(tablaPrim[i].nodo, tablaPrim[j].nodo).getPeso();
								if(tablaPrim[j].distancia > nuevaDistancia)
								{
									tablaPrim[j].distancia = nuevaDistancia;
									tablaPrim[j].antecesor = tablaPrim[i].nodo;
								}
								
							}
						}
					}
				}
			}
			valorMasChicoPrim();
		}
	}
	
	
}
