// This work is mine unless otherwise cited - Nathan Loria

package EzTravel.Scraper;

import EzTravel.TripData.TripPlan;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WebScraper {
  private String checkIn;
  private String checkOut;
  private double ppn;
  private int noa;
  private int noc;
  private int noi;
  private String URL;
  private ArrayList<Integer> prices;
  private ArrayList<String> links;
  private ArrayList<String> housingData;
  private ArrayList<String> locations;
  private int[] daysAtCity;

  public WebScraper(TripPlan tr) {
    this.checkIn = tr.getCheckIn();
    this.checkOut = tr.getCheckOut();
    this.ppn = tr.getPricePerNight();
    this.noa = tr.getNumberOfAdults();
    this.noc = tr.getNumberOfChildren();
    this.noi = tr.getNumberOfInfants();
    this.locations = tr.getLocations();
    this.daysAtCity = tr.getDaysAtCity();
    URL = null;
    housingData = new ArrayList<String>();
  }

  public ArrayList<String> scrapeData() {
    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String link = null;
    try {
      c.setTime(sdf.parse(checkIn));
    } catch (Exception x) {
      System.out.println(x);
    }
    for (int i = 0; i < locations.size(); i++) {
      checkIn = sdf.format(c.getTime());
      c.add(Calendar.DAY_OF_MONTH, daysAtCity[i]);
      checkOut = sdf.format(c.getTime());
      URL = "https://www.airbnb.com/s/" + locations.get(i) + "/homes?checkin=" + checkIn + "&checkout=" + checkOut + "&adults=" + noa + "&children=" + noc + "&infants=" + noi + "&price_max=" + Math.round(ppn);
      try {
        Document doc = Jsoup.connect(URL).followRedirects(false).timeout(0).get();
        Elements resultLinks = doc.select(("a[class=_i24ijs]"));
        link = "https://www.airbnb.com" + resultLinks.first().attr("href");
      } catch (Exception x) {
        System.out.println(x);
      }
      housingData.add(link);
    }
    return housingData;
  }
}
