package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Algorithme;
import model.Graphe;
import model.Lien;
import model.Ligne;
import model.Station;

/**
 * Servlet implementation class HomeController
 */
@WebServlet("/index")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static Graphe graphe = new Graphe();
	public static boolean ready = false;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//if(!ready){
			prepareData();
			ready = true;
		//}
		RequestDispatcher dispatcher = request.getRequestDispatcher("view/simulator.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private void prepareData(){
		System.gc();
		graphe = new Graphe();
		String csvFile = "C:/Users/Abdelhafid/Desktop/jee test data/London stations.csv";
		String csvFile2 = "C:/Users/Abdelhafid/Desktop/jee test data/London tube lines.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		try {
			boolean first = true;
			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				if(first){first=false;continue;}
				//Station,OS X,OS Y,Latitude,Longitude,Zone,Postcode 
				String[] data = line.split(cvsSplitBy);
				Station s = new Station(data[0], data[5], Double.valueOf(data[1]), Double.valueOf(data[2]), Double.valueOf(data[3]), Double.valueOf(data[4]));
				graphe.stations.add(s);
//				graphe.stations.add(new Station("_"+data[0], data[5], Double.valueOf(data[1]), Double.valueOf(data[2]), Double.valueOf(data[3]), Double.valueOf(data[4])));
			}
			br.close();
			br = new BufferedReader(new FileReader(csvFile2));
			String flag = "new";
			Ligne ligne = null;
			first=true;
			while ((line = br.readLine()) != null) { 
				if(first){first=false;continue;}
				//Tube Line,From Station,To Station
				String[] data = line.split(cvsSplitBy);
				Station stationFrom = graphe.getStationById(data[1]), stationTo = graphe.getStationById(data[2]);
				if(!flag.equals(data[0])){
					ligne = new Ligne(data[0]);
					System.out.println("new ligne "+ligne.getId());
					ligne.setColor(data[3]);
					ligne.setIcon(data[4]);
					ligne.setVitesse(40.0);
					if(data.length>5){
						ligne.setDebut(Double.valueOf(data[5]));
						ligne.setFin(Double.valueOf(data[6]));
						ligne.setMarge(Double.valueOf(data[7]));
						ligne.setVitesse(Double.valueOf(data[8]));
					}
					graphe.lignes.add(ligne);				
					flag = data[0];
				}
				System.out.println("from "+stationFrom+" to "+stationTo);
				ligne.addStation(stationFrom);
				ligne.addStation(stationTo);
				stationFrom.setLigne(stationFrom.getLigne()==null?ligne.toString():stationFrom.getLigne().contains(ligne.toString())?stationFrom.getLigne():stationFrom.getLigne()+"<br>"+ligne.toString());
				stationTo.setLigne(stationTo.getLigne()==null?ligne.toString():stationTo.getLigne().contains(ligne.toString())?stationTo.getLigne():stationTo.getLigne()+"<br>"+ligne.toString());
				//stationTo.setLigne(ligne.toString());	
				graphe.liens.add(new Lien(null, stationFrom, stationTo,ligne)); 
//				stationFrom = graphe.getStationById("_"+data[1]);
//				stationTo = graphe.getStationById("_"+data[2]);
//				graphe.liens.add(new Lien(null, stationTo,stationFrom, ligne));
			} 
			System.out.println("Data properly loaded");
			graphe.lignes.get(0).getStations().forEach(a -> System.out.println(a.getId()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
//		Hackney Central >> New Cross Gate
//		Neasden >> Kennington
//		Wood Green >> Kennington
//		Brent Cross >> Honor Oak Park
		HomeController test = new HomeController();
		test.prepareData();
        System.out.println("________________________________________________");
        System.out.println("________________________________________________");
		Long start,end;
		Double average = 0.0;
		int i=0,failed=0,limit = graphe.stations.size()-1,s,d;
		Random rand = new Random();
		Algorithme app;
		List<Station> result;
		while(i!=50){
			s = rand.nextInt(limit);
			d = rand.nextInt(limit);
			app = new Algorithme(graphe.getStationByIndex(s).getId(), graphe.getStationByIndex(d).getId(), graphe);
			start = System.nanoTime();
			result = app.execute();
	        end = System.nanoTime();
	        average += (end - start)/1000000.0;
	        System.out.println("" + (Double)((end - start)/1000000.0));//milliseconds
	        //System.out.println("________________________________________________");
	        if (result==null) {
				failed++;
			}
			i++;
			}
		System.out.println("average equals " + average/Double.parseDouble(""+i) + " millisecond");
		System.out.println("failed operations count "+failed);
		
//		start = System.nanoTime();
//		new Algorithme("Maida Vale", "Bethnal Green", graphe);
//        end = System.nanoTime();
//        System.out.println("algorithme takes : " + ((double) (end - start) / 1000000000.0)+" second");
		System.out.println("________________________________________________");
		System.out.println("________________________________________________");
		
//        start = System.nanoTime();
//		new Algorithme("Neasden", "Kennington", graphe).execute();
//        end = System.nanoTime();
//        System.out.println("algorithme takes : " + ((double) (end - start) / 1000000000.0)+" second");
//        System.out.println("________________________________________________");
//		
//        start = System.nanoTime();
//		new Algorithme("Kennington", "Neasden", graphe).execute();
//        end = System.nanoTime();
//        System.out.println("algorithme takes : " + ((double) (end - start) / 1000000000.0)+" second");
//        System.out.println("________________________________________________");
//		
//        start = System.nanoTime();
//		new Algorithme("Wood Green", "Kennington", graphe).execute();
//        end = System.nanoTime();
//        System.out.println("algorithme takes : " + ((double) (end - start) / 1000000000.0)+" second");
//        System.out.println("________________________________________________");
//		
//        start = System.nanoTime();
//		new Algorithme("Kennington","Wood Green", graphe).execute();
//        end = System.nanoTime();
//        System.out.println("algorithme takes : " + ((double) (end - start) / 1000000000.0)+" second");
//        System.out.println("________________________________________________");
//		
//        start = System.nanoTime();
//		new Algorithme("Brent Cross", "Penge West", graphe).execute();
//        end = System.nanoTime();
//        System.out.println("algorithme takes : " + ((double) (end - start) / 1000000000.0)+" second");
//        System.out.println("________________________________________________");
//		
//        start = System.nanoTime();
//		new Algorithme("Penge West", "Brent Cross", graphe).execute();
//        end = System.nanoTime();
//        System.out.println("algorithme takes : " + ((double) (end - start) / 1000000000.0)+" second");
//        System.out.println("________________________________________________");
	}

}
