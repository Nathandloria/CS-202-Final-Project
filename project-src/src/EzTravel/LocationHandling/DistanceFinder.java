// This work is mine unless otherwise cited - Nathan Loria

package EzTravel.LocationHandling;

import java.io.*;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse;

public class DistanceFinder {
	public double getWeight(String ad1, String ad2) throws Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request;
		HttpResponse<String> response;
		String uri1 = "https://maps.googleapis.com/maps/api/geocode/xml?address=" + ad1 + "&key=AIzaSyD22wqdbx-jQooX3oiI55qFj67Q0OXNtuA";
		String uri2 = "https://maps.googleapis.com/maps/api/geocode/xml?address=" + ad2 + "&key=AIzaSyD22wqdbx-jQooX3oiI55qFj67Q0OXNtuA";
		request = HttpRequest.newBuilder().uri(URI.create(uri1)).build();
		response = client.send(request, BodyHandlers.ofString());
		String data1 = response.body();
		request = HttpRequest.newBuilder().uri(URI.create(uri2)).build();
		response = client.send(request, BodyHandlers.ofString());
		String data2 = response.body();
		Double lat1 = Double.valueOf(data1.substring(data1.indexOf("<lat>") + 5, data1.indexOf("</lat>")));
		Double lon1 = Double.valueOf(data1.substring(data1.indexOf("<lng>") + 5, data1.indexOf("</lng>")));
		Double lat2 = Double.valueOf(data2.substring(data2.indexOf("<lat>") + 5, data2.indexOf("</lat>")));
		Double lon2 = Double.valueOf(data2.substring(data2.indexOf("<lng>") + 5, data2.indexOf("</lng>")));

		double distance = distance(lat1, lon1, lat2, lon2);
		return distance;
	}

	private static double distance(double lat1, double lon1, double lat2, double lon2) {
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return 0;
		} else {
			double delt = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(delt));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			return dist;
		}
	}
}
