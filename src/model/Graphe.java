package model;

import java.util.ArrayList;

public class Graphe {
	public ArrayList<Station> stations = new ArrayList<>();
	public ArrayList<Lien> liens = new ArrayList<>();
	public ArrayList<Ligne> lignes = new ArrayList<>();

	public Station getStationById(String id) {
		for (Station n : stations) {
			if (n.getId().equals(id))
				return n;
		}
		return null;
	}

	public Ligne getLigneById(String id) {
		for (Ligne n : lignes) {
			if (n.getId().equals(id))
				return n;
		}
		return null;
	}

	public Station getStationByIndex(int index) {
		return stations.get(index);
	}

	int getStationCount() {
		return stations.size();
	}

	void addStation(Station noeud) {
		stations.add(noeud);
	}

}