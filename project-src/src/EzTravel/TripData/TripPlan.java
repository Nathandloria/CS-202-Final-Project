// This work is mine unless otherwise cited - Nathan Loria

package EzTravel.TripData;

import java.util.ArrayList;
import java.util.HashMap;

public class TripPlan {
  private String checkIn;
  private String checkOut;
  private double ppn;
  private int noa;
  private int noc;
  private int noi;
  private ArrayList<String> locations;
  private int[] daysAtCity;

  public TripPlan(String checkIn, String checkOut, double ppn, int noa, int noc, int noi, ArrayList<String> locations, int[] daysAtCity) {
    this.checkIn = checkIn;
    this.checkOut = checkOut;
    this.ppn = ppn;
    this.noa = noa;
    this.noc = noc;
    this.noi = noi;
    this.locations = locations;
    this.daysAtCity = daysAtCity;
  }

  public void setCheckIn(String checkIn) {
    this.checkIn = checkIn;
  }

  public void setCheckOut(String checkOut) {
    this.checkOut = checkOut;
  }

  public void setPricePerNight(double ppn) {
    this.ppn = ppn;
  }

  public void setNumberOfPeople(int noa, int noc, int noi) {
    this.noa = noa;
    this.noc = noc;
    this.noi = noi;
  }

  public void setLocations(ArrayList<String> locations) {
    this.locations = locations;
  }

  public void setDaysAtCity (int[] daysAtCity) {
    this.daysAtCity = daysAtCity;
  }

  public String getCheckIn() {
    return checkIn;
  }

  public String getCheckOut() {
    return checkOut;
  }

  public double getPricePerNight() {
    return ppn;
  }

  public int getNumberOfAdults() {
    return noa;
  }

  public int getNumberOfChildren() {
    return noc;
  }

  public int getNumberOfInfants() {
    return noi;
  }

  public ArrayList<String> getLocations() {
    return locations;
  }

  public int[] getDaysAtCity() {
    return daysAtCity;
  }
}
