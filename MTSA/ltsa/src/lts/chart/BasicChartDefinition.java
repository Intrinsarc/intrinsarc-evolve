package lts.chart;

import java.util.*;

/**
 * Definition of a Basic Chart.
 * 
 * @author gsibay
 * 
 */
public class BasicChartDefinition {

	private List<Location> locations;

	public List<Location> getLocations() {
		return Collections.unmodifiableList(locations);
	}

	public BasicChartDefinition() {
		this.locations = new LinkedList<Location>();
	}

	public void addLocation(Location location) {
		this.locations.add(location);
	}
}
