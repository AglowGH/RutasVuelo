package gui;
import grafo.*;
import estructurasDatos.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class MyPanel extends JPanel implements MouseListener
{
	public static ListaNodo lista = new ListaNodo();
	public static ListaArista aristas =new ListaArista();
	public static Point2D coordenadas = null;
	public static Point2D coordenadas2 = null;
	private String name = null;
	public int contadorNodos = 0;
	private static final long serialVersionUID = 1L; 
	public static boolean reImprimir = false;
	
	
	////////////////////////////
	private Image map;
	public static File archivoMapa;
	public static int ballSize = 16;
	public static int grosor = 1;
	public static int distanciaNodo=16;
	public static boolean drawed = false;
	public static boolean ver = false;
	public static String nombreAeropuerto = null;
	////////////////////////////
	
	MyPanel()
	{
		archivoMapa = null;
		this.setBorder(BorderFactory.createTitledBorder("title"));
		this.setPreferredSize(new Dimension(600,600));
		this.addMouseListener(this);
	}
	
	public void paint(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		if(drawed)
		{
			if(!ver)
			{
				dibujarMapa(g2);
				pintarListas(g2);
				
				if(coordenadas != null)
				{
					if(!lista.buscarEntreRangos(coordenadas, distanciaNodo,ballSize,ballSize))
					{
						pintarNodo(g2);
						
					}else if(lista.buscarEntreRangos(coordenadas, 0,ballSize,ballSize))
					{
						pintarArista(g2);
					}
				}
			}else
			{
				dibujarMapa(g2);
				if(nombreAeropuerto != null)
				{
					pintarAeropuerto(g2,nombreAeropuerto);
				}
				coordenadas2 = null;
				coordenadas = null;
			}
		}
	}
	public void dibujarMapa(Graphics2D g2)
	{
		if(archivoMapa != null)
		{
			File image = new File(archivoMapa.getAbsolutePath());
			
			try 
			{
				map = ImageIO.read(image);
			}catch(IOException e)
			{
				e.printStackTrace();
			}
			g2.drawImage(map,0,0,getWidth(),getHeight(),this);
		}
	}
	
	public void pintarArista(Graphics2D g2)
	{
		if(coordenadas2 == null)
		{
			MyFrame.verticeSeleccionado = lista.buscarNodo(coordenadas,ballSize,ballSize);
			coordenadas2 = coordenadas;
		}else
		{
			Nodo n1 = lista.buscarNodo(coordenadas,ballSize,ballSize);
			Nodo n2 = lista.buscarNodo(coordenadas2,ballSize,ballSize);
			Arista arista = aristas.buscarArista(n1, n2);
			if(arista == null && n1 != n2)
			{
				g2.setStroke(new BasicStroke(grosor));
				g2.drawLine((int)coordenadas2.getX(),(int)coordenadas2.getY(),(int)coordenadas.getX(),(int)coordenadas.getY());
				
				Point puntoMedio = calcularPuntoMedio(coordenadas2,coordenadas);
				g2.setColor(Color.RED);
				g2.setFont(new Font("Ink Free",Font.BOLD,10));
				g2.drawString("1",(int)puntoMedio.getX() + 5,(int)puntoMedio.getY());
				g2.setColor(Color.black);
				aristas.insertar(new Arista(n2,n1,1));
			}else if(n1 != n2)
			{
				MyFrame.aristaSeleccionada = arista;
			}else
			{
				MyFrame.aristaSeleccionada = null;
			}
			
			coordenadas2 = null;
			coordenadas = null;
			MyFrame.verticeSeleccionado = null;
		}
	}
	
	public void pintarNodo(Graphics2D g2)
	{
		g2.setPaint(Color.black);
		contadorNodos +=1;
		name = String.valueOf(contadorNodos);
		lista.insertar(coordenadas,name);
		g2.fillOval((int)coordenadas.getX(),(int)coordenadas.getY(),ballSize,ballSize);
		g2.setFont(new Font("Ink Free",Font.BOLD,10));
		g2.setColor(Color.RED);
		g2.drawString(name,(int)coordenadas.getX(),(int)coordenadas.getY()-10);
		coordenadas2 = null;
		g2.setColor(Color.black);
		MyFrame.aeropuertos.addItem(name);
		MyFrame.verticeSeleccionado = null;
	}
	
	public void pintarListas(Graphics2D g2)
	{
		Nodo auxN = lista.peek();
		Arista auxA = aristas.peek();
		while(auxN != null) 
		{
			g2.setPaint(auxN.getColor());
			g2.fillOval((int)auxN.getCoordenadas().getX(),(int)auxN.getCoordenadas().getY(),ballSize,ballSize);
			g2.setPaint(Color.RED);
			g2.setFont(new Font("Ink Free",Font.BOLD,10));
			g2.drawString(auxN.getNombre(),(int)auxN.getCoordenadas().getX(),(int)auxN.getCoordenadas().getY()-10);
			auxN = auxN.getNext();
			g2.setColor(Color.black);
		}
		
		while(auxA != null)
		{
			g2.setPaint(auxA.getColor());
			g2.setStroke(new BasicStroke(grosor));
			g2.drawLine((int)auxA.getPrimerNodo().getCoordenadas().getX() + 7,(int)auxA.getPrimerNodo().getCoordenadas().getY() + 7 ,(int)auxA.getSegundoNodo().getCoordenadas().getX() +7,(int)auxA.getSegundoNodo().getCoordenadas().getY() + 7);
			g2.setPaint(Color.RED);
			Point puntoMedio = calcularPuntoMedio(auxA.getPrimerNodo().getCoordenadas(),auxA.getSegundoNodo().getCoordenadas());
			g2.drawString(String.valueOf(auxA.getPeso()),(int)puntoMedio.getX() + 5,(int)puntoMedio.getY());
			auxA = auxA.getNext();
			g2.setColor(Color.black);
		}
		reImprimir = false;
	}
	
	public void pintarAeropuerto(Graphics2D g2,String nombre)
	{
		Arista auxA = aristas.peek();
		Nodo node = lista.buscarNodo(nombre);
		g2.setPaint(node.getColor());
		g2.fillOval((int)node.getCoordenadas().getX(),(int)node.getCoordenadas().getY(),ballSize,ballSize);
		g2.setPaint(Color.RED);
		g2.setFont(new Font("Ink Free",Font.BOLD,10));
		g2.drawString(node.getNombre(),(int)node.getCoordenadas().getX(),(int)node.getCoordenadas().getY()-10);
		g2.setColor(Color.black);
		
		while(auxA != null)
		{
			if(auxA.getPrimerNodo().getNombre().equals(nombre) || auxA.getSegundoNodo().getNombre().equals(nombre))
			{
				g2.setPaint(auxA.getColor());
				g2.setStroke(new BasicStroke(grosor));
				g2.drawLine((int)auxA.getPrimerNodo().getCoordenadas().getX() + 7,(int)auxA.getPrimerNodo().getCoordenadas().getY() + 7 ,(int)auxA.getSegundoNodo().getCoordenadas().getX() +7,(int)auxA.getSegundoNodo().getCoordenadas().getY() + 7);
				g2.setPaint(Color.RED);
				Point puntoMedio = calcularPuntoMedio(auxA.getPrimerNodo().getCoordenadas(),auxA.getSegundoNodo().getCoordenadas());
				g2.drawString(String.valueOf(auxA.getPeso()),(int)puntoMedio.getX() + 5,(int)puntoMedio.getY());
				g2.setColor(Color.black);
				
				Nodo n1 = auxA.getPrimerNodo();
				Nodo n2 = auxA.getSegundoNodo();
				
				g2.setPaint(n1.getColor());
				g2.fillOval((int)n1.getCoordenadas().getX(),(int)n1.getCoordenadas().getY(),ballSize,ballSize);
				g2.setPaint(Color.RED);
				g2.setFont(new Font("Ink Free",Font.BOLD,10));
				g2.drawString(n1.getNombre(),(int)n1.getCoordenadas().getX(),(int)n1.getCoordenadas().getY()-10);
				g2.setColor(Color.black);
				
				g2.setPaint(n2.getColor());
				g2.fillOval((int)n2.getCoordenadas().getX(),(int)n2.getCoordenadas().getY(),ballSize,ballSize);
				g2.setPaint(Color.RED);
				g2.setFont(new Font("Ink Free",Font.BOLD,10));
				g2.drawString(n2.getNombre(),(int)n2.getCoordenadas().getX(),(int)n2.getCoordenadas().getY()-10);
				g2.setColor(Color.black);
				
			}
			auxA = auxA.getNext();
		}
		
	}
	
	public Point calcularPuntoMedio(Point2D p1, Point2D p2)
	{
		
		int x = (int)(p1.getX() + p2.getX())/2;
		int y = (int)(p1.getY() + p2.getY())/2;
		
		return new Point(x,y);
	}
	
	public static boolean mismosNombres(String nuevoNombre)
	{
		Nodo auxN = lista.peek();
		
		while(auxN != null)
		{
			if(auxN.getNombre().equals(nuevoNombre))return true;
			auxN = auxN.getNext();
		}
		
		return false;
	}

	@Override
	public void mouseClicked(MouseEvent evento) 
	{
		if(MyFrame.activarMouse == true)
		{
			coordenadas = evento.getPoint();
			repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
}
