// This work is mine unless otherwise cited - Nathan Loria

package EzTravel;

import EzTravel.GraphData.*;
import EzTravel.LocationHandling.DistanceFinder;
import EzTravel.Scraper.WebScraper;
import EzTravel.TripData.TripPlan;

import java.text.SimpleDateFormat;

import java.time.temporal.ChronoUnit;

import java.util.*;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class TravelMain {
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);

    System.out.println("\nWelcome to EzTravel, thank you for choosing us!");
    try {
      Thread.sleep(900);
    } catch (Exception x) {
      System.out.println(x);
    }

    System.out.println("\nLet's begin. Please answer the following questions so we can plan the perfect trip for you!");
    try {
      Thread.sleep(900);
    } catch (Exception x) {
      System.out.println(x);
    }
    System.out.println("\nWhat city would you like to depart from? (city, state abbreviation[US locations], country [non-US locations]): ");
    String start = scan.nextLine();
    if (start.contains(" ")) {
      start = start.replaceAll("\\s+", "+");
    }

    System.out.println("\nPlease enter the addresses of all countries and cities that you would like to visit! Enter 'DONE' when you are done! "
    + "(city, state abbreviation [US locations], country [non-US locations]): ");
    ArrayList<String> locations = new ArrayList<String>();
    locations.add(start);
    boolean done = false;
    int count = 1;
    while (done == false) {
      System.out.println("- Destination #" + count + ": ");
      String add = scan.nextLine();
      if (!add.equals("DONE") && !add.equals("done")) {
        add = add.replaceAll("\\s+", "-");
        locations.add(add);
        count++;
      } else {
        done = true;
      }
    }

    System.out.println("\nPlease enter the date that you would like to begin your trip (xxxx-xx-xx (yr-month-day)): ");
    String checkIn = scan.next();

    System.out.println("\nPlease enter the date that you would like to end your trip (xxxx-xx-xx (yr-month-day)): ");
    String checkOut = scan.next();

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    Date d1 = null;
    Date d2 = null;

    try {
      d1 = formatter.parse(checkIn);
      d2 = formatter.parse(checkOut);
    } catch(Exception x) {
      System.out.println(x);
    }

    int x = 0;
    int days = (int)(d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24);
    int[] daysAtCity = new int[locations.size() - 1];
    for (int i = 0; i < days; i++) {
      if (x == locations.size() - 1) {
        x = 0;
      }
      daysAtCity[x] += 1;
      x++;
    }

    System.out.println("\nPlease enter the price per night that works for your budget! ($): ");
    Double ppn = scan.nextDouble();

    System.out.println("\nFinally, enter the number of adults, children, and infants going on the trip as a comma separated value (adults, children, infants)");
    scan.nextLine();
    String[] nop = scan.nextLine().split("\\s*,\\s*");
    int noa = Integer.parseInt(nop[0]);
    int noc = Integer.parseInt(nop[1]);
    int noi = Integer.parseInt(nop[2]);

    System.out.println("\nGot it! We are now working on planning your trip! One moment please...");

    double[][] d = new double[locations.size()][locations.size()];
    DistanceFinder df = new DistanceFinder();
    for (int i = 0; i < locations.size(); i++) {
      try {
        d[0][i] = df.getWeight(locations.get(0), locations.get(i));
        d[i][0] = df.getWeight(locations.get(0), locations.get(i));
        if (i != (locations.size() - 1) && i != 0) {
          d[i][i + 1] = df.getWeight(locations.get(i), locations.get(i + 1));
          d[i + 1][i] = df.getWeight(locations.get(i), locations.get(i + 1));
        }
      } catch (Exception ex) {
        System.out.println(ex);
      }
    }

    // for (int i = 0; i < d.length; i++) {
    //   for (int j = 0; j < d[0].length; j++) {
    //     System.out.print(d[i][j] + "\t");
    //   }
    //   System.out.println("");
    // }

    TripPath tp = new TripPath(0, d);
    tp.solve();

    ArrayList<Integer> path = tp.getPath();
    ArrayList<String> properLocations = new ArrayList<String>();
    locations.remove(0);
    path.remove(0);
    path.remove(path.size() - 1);

    for (int i = 0; i < locations.size(); i++) {
      properLocations.add(locations.get(path.get(i) - 1));
    }

    TripPlan tr = new TripPlan(checkIn, checkOut, ppn, noa, noc, noi, properLocations, daysAtCity);
    WebScraper ws = new WebScraper(tr);

    ArrayList<String> tripLinks = ws.scrapeData();

    System.out.println("\nThank you for your patience! EzTravel has devised the perfect trip for you! Here are the details: ");
    for (int i = 0; i < properLocations.size(); i++) {
      System.out.println("\n - Destination #" + (i + 1) + ": " + properLocations.get(i));
      System.out.println(" - Information: " + tripLinks.get(i));
    }
    System.out.println("\nThank you for choosing EzTravel! We hope you enjoy your trip!");
  }
}
