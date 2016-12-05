package model;

public class Lien {
	private String id, stationFrom, stationTo, ligne;
	private Double tempTrajet;
	private Design designe;
	
	public Double distance(Station stationFrom, Station stationTo) {
		double lat1 = stationFrom.getDesigne().getLatitude(), lon1 = stationFrom.getDesigne().getLongitude(), 
			lat2 = stationTo.getDesigne().getLatitude(), lon2 = stationTo.getDesigne().getLongitude();

		double R = 6371;// kilometre
		double a1 = Math.toRadians(lat1);
		double a2 = Math.toRadians(lat2);
		double ba = Math.toRadians(lat2 - lat1);
		double bc = Math.toRadians(lon2 - lon1);

		double a = Math.sin(ba / 2) * Math.sin(ba / 2)
				+ Math.cos(a1) * Math.cos(a2) * Math.sin(bc / 2) * Math.sin(bc / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		double d = R * c;
		return d;
	} 
	
	public Lien(String id, String stationTo, Double temps,String ligne){
		super();
		this.id=id;
		this.stationTo=stationTo;
		this.tempTrajet=temps;
		this.ligne = ligne;
	}
			
	public Lien(String id, Station stationFrom, Station stationTo,Ligne ligne) {
		super();
		this.id = id;
		this.stationFrom = stationFrom.getId();
		this.stationTo = stationTo.getId();
		this.ligne = ligne.toString();
		this.tempTrajet = (distance(stationFrom, stationTo)/ligne.getVitesse())*60*60;// km/h > v  ;
		stationFrom.addSuccesseur(this);
		this.designe = stationTo.getDesigne();

		Lien lien = new Lien(null,this.stationFrom,this.tempTrajet,ligne.toString());
		lien.setStationFrom(this.stationTo);
		lien.setDesigne(this.designe);
		stationTo.addSuccesseur(lien);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return stationFrom + " to "+stationTo+" takes "+tempTrajet;
	}

	public String getStationTo() {
		return stationTo;
	}

	public void setStationTo(String stationTo) {
		this.stationTo = stationTo;
	}

	public String getStationFrom() {
		return stationFrom;
	}

	public void setStationFrom(String stationFrom) {
		this.stationFrom = stationFrom;
	}

	public Double getTempTrajet() {
		return tempTrajet;
	}

	public void setTempTrajet(Double tempTrajet) {
		this.tempTrajet = tempTrajet;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
}

