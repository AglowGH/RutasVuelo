package gui;
import grafo.*;
import estructurasDatos.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class MyFrame extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 1L;

	public BorderLayout MyFlow;
	public GridLayout MyGrid;
	public GridLayout MyGrid2;
	
	public static MyPanel panel;
	public static JPanel cascaron;
	public JPanel botones;
	public JPanel resultados;
	
	public JButton cambiarNombre;
	public JButton cambiarPeso;
	public JButton BFS;
	public JButton DFS;
	public static JButton Dijkstra;
	public static JButton eliminarNodo;
	public static JButton eliminarArista;
	public static JButton Prim;
	
	public JLabel label1;
	public static JLabel resultadoBFS;
	public JLabel label2;
	public static JLabel resultadoDFS;
	public JLabel label3;
	public JLabel resultadoDijkstra;
	public JLabel resultadoCostoDijkstra;
	public JLabel label4;
	public JLabel resultadoCostoPrim;

	public static String nombre = null;
	public static int peso = 0;
	public static Nodo verticeSeleccionado = null;
	public static Arista aristaSeleccionada = null;
	public static boolean activarMouse = true;
	public static double costoDijkstra = 0;
	public static ThreadColores hilo;
	public static boolean numeroNegativo = false;
	public static double costoPrim = 0;
	public static Arista aristasPrim[] = null;
	
	///////////////
	private JFileChooser fc;
	private JButton selectMap;
	private JButton guardarMapa;
	///////////////
	
	MyFrame()
	{
		fc = new JFileChooser();
	}
	
	public void mostrarFrame()
	{
		MyFlow = new BorderLayout();
		setLayout(MyFlow);
		
		panel = new MyPanel();
		cascaron = new JPanel();
		cascaron.add(panel);
		cascaron.setPreferredSize(new Dimension(600,600));  
		cascaron.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Pizarra"));
		this.add(cascaron,BorderLayout.CENTER);
		
		botones = new JPanel();
		MyGrid = new GridLayout();
		MyGrid.setColumns(1);
		MyGrid.setVgap(5);
		MyGrid.setRows(12);
		MyGrid.setHgap(5);
		botones.setLayout(MyGrid);
		botones.setPreferredSize(new Dimension(200,600));
		botones.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Opciones"));
		add(botones,BorderLayout.LINE_END);
		
		
		resultados = new JPanel();
		MyGrid2 = new GridLayout();
		MyGrid2.setColumns(1);
		MyGrid2.setVgap(5);
		MyGrid2.setRows(10);
		MyGrid2.setHgap(5);
		resultados.setLayout(MyGrid2);
		resultados.setPreferredSize(new Dimension(800,360));
		resultados.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),"Resultados"));
		add(resultados,BorderLayout.PAGE_END);
		
		cambiarNombre = new JButton("Cambiar Nombre");
		cambiarNombre.addActionListener(this);
		botones.add(cambiarNombre);
		
		cambiarPeso = new JButton("Cambiar Peso");
		cambiarPeso.addActionListener(this);
		botones.add(cambiarPeso);
		
		BFS = new JButton("BFS");
		BFS.addActionListener(this);
		botones.add(BFS);
		
		DFS = new JButton("DFS");
		DFS.setSize(100, 50);
		DFS.addActionListener(this);
		botones.add(DFS);
		
		Dijkstra = new JButton("Dijkstra");
		Dijkstra.addActionListener(this);
		botones.add(Dijkstra);
		
		eliminarNodo = new JButton("Eliminar nodo");
		eliminarNodo.addActionListener(this);
		botones.add(eliminarNodo);
		
		eliminarArista = new JButton("Eliminar Arista");
		eliminarArista.addActionListener(this);
		botones.add(eliminarArista);
		
		Prim = new JButton("Prim");
		Prim.addActionListener(this);
		botones.add(Prim);
		
		label1 = new JLabel("BFS: ");
		resultados.add(label1);
		
		resultadoBFS = new JLabel(" ");
		JScrollPane scroll1 = new JScrollPane(resultadoBFS);
		resultados.add(scroll1);
		
		label2 = new JLabel("DFS: ");
		resultados.add(label2);
		
		resultadoDFS = new JLabel(" ");
		JScrollPane scroll2 = new JScrollPane(resultadoDFS);
		resultados.add(scroll2);
		
		label3 = new JLabel("Dijkstra: ");
		label3.setPreferredSize(new Dimension(100,40));
		resultados.add(label3);
		
		resultadoDijkstra = new JLabel(" ");
		JScrollPane scroll3 = new JScrollPane(resultadoDijkstra);
		resultados.add(scroll3);
		
		resultadoCostoDijkstra = new JLabel(" ");
		resultadoCostoDijkstra.setPreferredSize(new Dimension(100,40));
		resultados.add(resultadoCostoDijkstra);
		
		label4 = new JLabel("Prim");
		resultados.add(label4);
		
		resultadoCostoPrim = new JLabel(" ");
		resultados.add(resultadoCostoPrim);
		
		////////////////////////////////////////////////////////////
		selectMap = new JButton("Importar Mapa");
		selectMap.addActionListener(this);
		botones.add(selectMap);
		
		guardarMapa = new JButton("Guardar Mapa");
		guardarMapa.addActionListener(this);
		botones.add(guardarMapa);
		////////////////////////////////////////////////////////////
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		this.setResizable(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Editor de Rutas");
		
	}
	
	public void guardarSerie(String nombreArchivo1, String nombreArchivo2)
	{
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/home/rick/github/RutasVuelo/EditorRutas/src/"+nombreArchivo1));
			oos.writeObject(MyPanel.lista);
			oos.close();
		}catch(IOException e)
		{
			
		}
		
		try
		{
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/home/rick/github/RutasVuelo/EditorRutas/src/"+nombreArchivo2));
			oos.writeObject(MyPanel.aristas);
			oos.close();
			
		}catch(IOException e)
		{
			
		}
	}
	
	public void obtenerNombre()
	{
		if(verticeSeleccionado != null) 
		{
			nombre = (String)JOptionPane.showInputDialog("Escribe Nombre: ");
			
			if(nombre == null || nombre.equals(""))
			{
				nombre = null;
				verticeSeleccionado = null;
				JOptionPane.showMessageDialog(botones,"Nombre no valido!!!!");
				return;
			}
			
			if(MyPanel.mismosNombres(nombre))
			{
				nombre = null;
				verticeSeleccionado = null;
				JOptionPane.showMessageDialog(botones,"Este nombre ya fue ocupado!!!!");
				return;
			}
			
			verticeSeleccionado.setNombre(nombre.replace(" ",""));
			verticeSeleccionado = null;
			nombre = null;
		}else
		{
			JOptionPane.showMessageDialog(botones,"Selecciona un vertice!!!!");
		}
	}
	
	public void obtenerPeso()
	{
		if(aristaSeleccionada != null)
		{
			String aux = (String) JOptionPane.showInputDialog("Escribe peso: ");
			
			if(aux == null || aux.equals(""))
			{
				return;
			}
			
			double auxN;
			try {
				auxN = Double.parseDouble(aux);
			}catch(Exception e) {
				JOptionPane.showMessageDialog(botones, "Solo escribe numeros.");
				auxN = 0;
			}
			
			if(auxN < 0)
			{
				numeroNegativo = true;
			}
			
			aristaSeleccionada.setPeso(auxN);
			aristaSeleccionada = null;
			peso = 0;
		}else
		{
			JOptionPane.showMessageDialog(botones,"Escoje una arista!!!");
		}
		
	}
	
	public boolean obtenerDijkstra()
	{
		Grafo.actualizarInfo();
		if(!Grafo.nodosConectados())
		{
			JOptionPane.showMessageDialog(null, "Todos los nodos deben estar conectados!!!");
			return false;
		}
		String inicio = (String) JOptionPane.showInputDialog("Escribe el vertice de origen:");
		String destino = (String) JOptionPane.showInputDialog("Escribe el vertice destino:");
		
		if(inicio == null || inicio.equals("") || destino == null || destino.equals(""))
		{
			JOptionPane.showMessageDialog(null, "No se admiten campos vacios!!!");
			return false;
		}
		
		if(inicio.equals(destino))
		{
			JOptionPane.showMessageDialog(null, "El origen y el destino son iguales!!!!");
			return false;
		}
		
		Nodo origen = MyPanel.lista.buscarNodo(inicio);
		Nodo fin = MyPanel.lista.buscarNodo(destino);
		
		if(origen == null || fin == null)
		{
			JOptionPane.showMessageDialog(null, "Error, vertices no encontrados!!!!");
			return false;
		}
		
		mostrarCamino(origen,fin);
		return true;
	}
	
	private void mostrarCamino(Nodo origen, Nodo destino)
	{
		Grafo.metodoDijkstra(origen);
		Pila pila = new Pila(ListaNodo.orden);
		String str = "";
		buscarCostoDijkstra(destino);
		while(!destino.getNombre().equals(origen.getNombre()))
		{
			pila.push(destino.getNombre());
			
			Nodo anterior = destino;
			destino = buscarAncestro(destino);
			hilo.agrgarArista(MyPanel.aristas.buscarArista(destino,anterior));
		}
		pila.push(destino.getNombre());
		
		while(pila.peek() != null)
		{
			str += pila.pop();
			if(pila.peek() != null)
			{
				str += " -> ";
			}
		}
		hilo.voltearLista();
		resultadoCostoDijkstra.setText(" El costo es de : " + costoDijkstra);
		costoDijkstra = 0;
		resultadoDijkstra.setText(str);
	}
	
	public Nodo buscarAncestro(Nodo nodo)
	{
		for(int i=0;i<ListaNodo.orden;i++)
		{
			if(Grafo.tabla[i].nodo.getNombre().equals(nodo.getNombre())) 
			{
				return Grafo.tabla[i].antecesor;
			}
		}
		
		return null;
	}
	
	public void buscarCostoDijkstra(Nodo nodo)
	{
		for(int i=0;i<ListaNodo.orden;i++)
		{
			if(Grafo.tabla[i].nodo.getNombre().equals(nodo.getNombre())) 
			{
				costoDijkstra = Grafo.tabla[i].distancia;
			}
		}
		
	}
	
	public void eliminarVertice()
	{
		if(verticeSeleccionado != null) 
		{
			MyPanel.aristas.eliminarAristas(verticeSeleccionado);
			MyPanel.lista.eliminar(verticeSeleccionado);
			verticeSeleccionado = null;
		}else
		{
			JOptionPane.showMessageDialog(botones,"Selecciona un vertice!!!!");
		}
	}
	
	public void eliminarA()
	{
		if(aristaSeleccionada != null)
		{
			MyPanel.aristas.eliminarArista(aristaSeleccionada);
			aristaSeleccionada = null;
			peso = 0;
		}else
		{
			JOptionPane.showMessageDialog(botones,"Escoje una arista!!!");
		}
		
	}
	
	
	public boolean obtenerPrim()
	{
		Grafo.actualizarInfo();
		if(!Grafo.nodosConectados())
		{
			JOptionPane.showMessageDialog(null, "Todos los nodos deben estar conectados!!!");
			return false;
		}
		
		mostrarCaminoPrim();
		return true;
	}
	
	private void mostrarCaminoPrim()
	{
		Grafo.prim();
		aristasPrim = new Arista[ListaNodo.orden-1];
		
		int i = 0;
		for(Tabla t: Grafo.tablaPrim)
		{
			if(!t.antecesor.getNombre().equals("Inicio"))
			{
				aristasPrim[i] = MyPanel.aristas.buscarArista(t.nodo, t.antecesor);
				costoPrim += t.distancia;
				i++;
			}
		}
		
		Arista aux = MyPanel.aristas.peek();
		int contador = MyPanel.aristas.getContador();
		Arista[] marcadas = new Arista[contador];
		int j=0;
		
		while(aux != null)
		{
			
			if(!aristaPerteneceAristas(aux))
			{
				marcadas[j] = aux;
				j++;
			}
			
			aux = aux.getNext();
		}
		
		for(int k = 0; k < j;k++)
		{
			MyPanel.aristas.eliminarArista(marcadas[k]);
		}
		
		resultadoCostoPrim.setText(" El costo es de : " + costoPrim);
		costoPrim = 0;
	}
	
	public boolean aristaPerteneceAristas(Arista arista)
	{
		for(Arista a: aristasPrim)
		{
			if(a == arista)return true;
		}
		
		return false;
	}
	//comp
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == cambiarPeso)
		{
			activarMouse = false;
			obtenerPeso();
			verticeSeleccionado = null;
			activarMouse =true;
			MyPanel.coordenadas = null;
			cascaron.setVisible(false);
			cascaron.setVisible(true);
			panel.setVisible(false);
			panel.setVisible(true);
			MyPanel.reImprimir = true;
		}
		
		if(arg0.getSource() == BFS)
		{
			if(verticeSeleccionado != null)
			{
				Grafo.actualizarInfo();
				Grafo.mostrarBFS(verticeSeleccionado);
				verticeSeleccionado = null;
				MyFrame.cascaron.setVisible(false);
				MyFrame.cascaron.setVisible(true);
				hilo.start();
			}else
			{
				JOptionPane.showMessageDialog(null, "Escoje un nodo!!!!");
			}
		}
		
		if(arg0.getSource() == DFS) 
		{
			if(cambiarNombre ==  arg0.getSource())
			{
				activarMouse = false;
				obtenerNombre();
				MyPanel.coordenadas2 = null;
				MyPanel.coordenadas = null;
				activarMouse = true;
				cascaron.setVisible(false);
				cascaron.setVisible(true);
				panel.setVisible(false);
				panel.setVisible(true);
				MyPanel.reImprimir = true;
			}
			
			
			if(verticeSeleccionado != null)
			{
				Grafo.actualizarInfo();
				Grafo.DFS(verticeSeleccionado);
				Grafo.mostrarDFS();
				verticeSeleccionado = null;
				MyFrame.cascaron.setVisible(false);
				MyFrame.cascaron.setVisible(true);
				hilo.start();
			}else
			{
				JOptionPane.showMessageDialog(null, "Escoje un nodo!!!!");
			}
		}
		
		if(arg0.getSource() == Dijkstra)
		{
			if(!numeroNegativo)
			{
				if(obtenerDijkstra())
				{
					MyFrame.cascaron.setVisible(false);
					MyFrame.cascaron.setVisible(true);
					hilo.start();
				}
			}else
			{
				JOptionPane.showMessageDialog(null, "Todos los pesos deben ser positivos!!!");
			}
		}
		
		if(arg0.getSource() == eliminarNodo)
		{
			activarMouse = false;
			eliminarVertice();
			activarMouse =true;
			MyPanel.coordenadas = null;
			MyPanel.coordenadas2 = null;
			cascaron.setVisible(false);
			cascaron.setVisible(true);
			panel.setVisible(false);
			panel.setVisible(true);
			MyPanel.reImprimir = true;
		}
		
		if(arg0.getSource() == eliminarArista)
		{
			activarMouse = false;
			eliminarA();
			activarMouse =true;
			MyPanel.coordenadas = null;
			MyPanel.coordenadas2 = null;
			cascaron.setVisible(false);
			cascaron.setVisible(true);
			panel.setVisible(false);
			panel.setVisible(true);
			MyPanel.reImprimir = true;
		}
		
		if(arg0.getSource() == Prim)
		{
			if(obtenerPrim())
			{
				MyFrame.cascaron.setVisible(false);
				MyFrame.cascaron.setVisible(true);
				panel.setVisible(true);
				MyPanel.reImprimir = true;
			}
		}
		
		if(arg0.getSource() == selectMap)
		{
			int seleccion = fc.showOpenDialog(this);
			
			if(seleccion == JFileChooser.APPROVE_OPTION)
			{
				File imagen = fc.getSelectedFile();
				MyPanel.archivoMapa = imagen;
				panel.repaint();
				
			}
		}
		
		if(arg0.getSource() == guardarMapa)
		{
			guardarSerie("archivo1.txt","archivo2.txt");
		}
	}
}