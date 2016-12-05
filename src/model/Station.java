package model;

import java.util.ArrayList;
import java.util.Date;

public class Station implements Comparable<Station>{
	private ArrayList<Lien> successeurs = new ArrayList<>();
	private ArrayList<Date> departs = new ArrayList<>();
	private String id,ligne=null,predecesseur="";
	private Date arrived;
	private Design designe;

	public Station(String id) {
		this.id = id;
	}

	public Station(String id,String type,Double x,Double y,Double lat,Double lon) {
		this.id = id;
		this.designe = new Design(type, x, y, lat, lon);
	}

	void addSuccesseur(Lien successeur) {
		successeur.setStationFrom(this.id);
		successeurs.add(successeur);
	}

	Lien getSuccessorByIndex(int index) {
		return successeurs.get(index);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.id +" arr "+ this.arrived + " prd "+this.predecesseur;
	}

	public ArrayList<Lien> getSuccesseurs() {
		return successeurs;
	}

	public void setSuccesseurs(ArrayList<Lien> listSuccesseurs) {
		this.successeurs = listSuccesseurs;
	}

	public ArrayList<Date> getDeparts() {
		return departs;
	}

	public void setDeparts(ArrayList<Date> listDeparts) {
		this.departs = listDeparts;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPredecesseur() {
		return predecesseur;
	}

	public void setPredecesseur(String pred) {
		this.predecesseur = pred;
	}

	public Date getArrived() {
		return arrived;
	}

	public void setArrived(Date arrived) {
		this.arrived = arrived;
	}

	public Design getDesigne() {
		return designe;
	}

	public void setDesigne(Design designe) {
		this.designe = designe;
	}

	public String getLigne() {
		return ligne;
	}

	public void setLigne(String ligne) {
		this.ligne = ligne;
	}

	@Override
	public int compareTo(Station o) {
		// TODO Auto-generated method stub
		return this.getArrived().compareTo(o.getArrived());
	}	
	
}
