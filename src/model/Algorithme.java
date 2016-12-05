package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Algorithme {
	List<Station> hp = new ArrayList<Station>();
	public String source, destination;
	Graphe graphe;
	Calendar date;

	public Algorithme(String source, String destination, Graphe graphe) {
		super();
		this.source = source;
		this.destination = destination;
		this.graphe = graphe;
	}

	void initialise() {
		date = Calendar.getInstance();
		Station station = new Station("0");
		station.addSuccesseur(new Lien("-1",this.source, 0.0,""));
		station.setArrived(date.getTime());
		hp.add(station);
		date.add(Calendar.YEAR, 1);;
		for (Station object : graphe.stations) {
			object.setArrived(date.getTime());
			object.setPredecesseur("");
			hp.add(object);
		}
	}

	public List<Station> execute() {
//		Step 1
		initialise();
		Station closeStation;
		do {

//			Step 2
			Collections.sort(hp);
			closeStation = hp.get(0);
			hp.remove(0);
			
//			Step 3
			if (closeStation.getId().equals(this.destination))
				break;

//			Step 4
			for (Lien successeur : closeStation.getSuccesseurs()) {
				Date leaving = earliest(closeStation);
				date.setTime(leaving);
				date.add(Calendar.SECOND, successeur.getTempTrajet().intValue());
				if (date.getTime().compareTo(graphe.getStationById(successeur.getStationTo()).getArrived())<0) {
					closeStation.setLigne(successeur.getLigne());
					Station stationTo = graphe.getStationById(successeur.getStationTo());
					stationTo.setLigne(successeur.getLigne());
					stationTo.setArrived(date.getTime());
					stationTo.setPredecesseur(closeStation.getId());
				}
			}
		} while (closeStation.getId() != this.destination || hp.size()==0);

//		Step 5
		Station s;
		String index = this.destination;
		List<Station> result = new ArrayList<>();
//		System.out.println("minimum time :");
		while (!index.equals(this.source)) {
			s = graphe.getStationById(index);
			result.add(s);
			index = s.getPredecesseur();
			if(index.equals("")){
				return null;
			}
			if(index.equals(this.source)){
				s = graphe.getStationById(index);
				result.add(s);
			}
		}
		return result;
	}

	private Date earliest(Station station) {
		if (station.getDeparts().size() > 0) {
			for (Date depart : station.getDeparts()) {
				if (depart.compareTo(station.getArrived())>=0)
					return depart;
			}
		}
		return station.getArrived();
	}
}
