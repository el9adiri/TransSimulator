package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
 
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Ligne { 
	/*
	 * debut > l'heur de depart de ligne
	 * fin > l'heur de l'arret de ligne
	 * marge > marge de temps entre chaque voyage en minutes
	 * vitesse > moyen de la vitesse des vehicules
	 */	
	private Double debut=null,fin=null,marge=null,vitesse;
	private String id, mode, icon, color;
	private ArrayList<Station> stations = new ArrayList<>();
	
	
	public Ligne(String id, String mode, Double debut, Double fin, Double marge,Double vitesse) {
		super();
		this.id = id; 
		this.mode = mode;
		this.debut = debut;
		this.fin = fin;
		this.marge = marge;
		this.vitesse = vitesse;
	}
	
	public Ligne(String id) {
		super();
		this.id = id;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id+ " " + this.vitesse + " Km/h";
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	} 
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public Double getVitesse() {
		return vitesse;
	}

	public void setVitesse(Double vitesse) {
		this.vitesse = vitesse;
	}

	public Double getDebut() {
		return debut;
	}
	public void setDebut(Double debut) {
		this.debut = debut;
	}
	public Double getFin() {
		return fin;
	}
	public void setFin(Double fin) {
		this.fin = fin;
	}
	public Double getMarge() {
		return marge;
	}
	public void setMarge(Double marge) {
		this.marge = marge;
	}

	public ArrayList<Station> getStations() {
		return stations;
	}

	public void setStations(ArrayList<Station> stations) {
		this.stations = stations;
	}

	public void addStation(Station station) {
		if(this.stations.contains(station))return;
		System.out.println("ligne has now station "+station.getId());
		if(this.debut!=null && this.fin!=null && marge!=null){
			Calendar date = Calendar.getInstance();		
			date.set(Calendar.HOUR_OF_DAY, this.debut.intValue());
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			station.getDeparts().add(date.getTime());
			while(date.get(Calendar.HOUR_OF_DAY)<this.fin){
				date.add(Calendar.MINUTE, this.marge.intValue());
				station.getDeparts().add(date.getTime());
			}
		}
		this.stations.add(station);
	}
	
	
}
