package grafo;
import java.awt.Color;
import java.util.*;
import gui.*;
public class ThreadColores extends Thread 
{
	private ArrayList<Arista> aristas;
	
	public ThreadColores()
	{
		aristas = new ArrayList<Arista>();
	}
	
	public void run()
	{
		
		if(!aristas.isEmpty())
		{
			for(Arista arista: aristas)
			{
				MyFrame.activarMouse =false;
				MyFrame.eliminarNodo.setEnabled(false);
				MyFrame.eliminarArista.setEnabled(false);
				MyFrame.Dijkstra.setEnabled(false);
				if(arista != null)
				{
				arista.setColor(Color.RED);
				arista.getPrimerNodo().setColor(Color.blue);
				arista.getSegundoNodo().setColor(Color.blue);
				try
				{
					MyFrame.panel.setVisible(false);
					MyFrame.panel.setVisible(true);
					MyPanel.reImprimir = true;
					MyFrame.panel.repaint();
					sleep(3000);
					
				}catch(InterruptedException e)
				{
					MyFrame.activarMouse =true;
				}
				
				}
			}
		}
		MyFrame.Dijkstra.setEnabled(true);
		MyFrame.eliminarNodo.setEnabled(true);
		MyFrame.activarMouse =true;
		MyFrame.eliminarArista.setEnabled(true);
	}
	
	public void voltearLista()
	{
		ArrayList<Arista> aux = new ArrayList<Arista>();
		
		for(int i = aristas.size();i>0;i--)
		{
			aux.add(aristas.get(i-1));
		}
		aristas = aux;
	}
	
	public void agrgarArista(Arista arista)
	{
		aristas.add(arista);
	}
}
