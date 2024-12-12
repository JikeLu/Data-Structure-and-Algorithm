import java.util.ArrayList;
import java.util.List;

public class ListOfCrimes {
    private List<CrimeData> crimes;

    public ListOfCrimes() {
        this.crimes = new ArrayList<>();
    }

    public void addCrime(CrimeData crime) {
        crimes.add(crime);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (CrimeData crime : crimes) {
            sb.append(crime.toString()).append("\n");
        }
        return sb.toString();
    }

    public int length() {
        return crimes.size();
    }

    public String toKML() {
        StringBuilder kml = new StringBuilder();
        kml.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        kml.append("<kml xmlns=\"http://earth.google.com/kml/2.2\">\n");
        kml.append("<Document>\n");
        kml.append("<Style id=\"style1\">\n");
        kml.append("<IconStyle>\n");
        kml.append("<Icon>\n");
        kml.append("<href>http://maps.gstatic.com/intl/en_ALL/mapfiles/ms/micons/blue-dot.png</href>\n");
        kml.append("</Icon>\n");
        kml.append("</IconStyle>\n");
        kml.append("</Style>\n");

        for (CrimeData crime : crimes) {
            kml.append("<Placemark>\n");
            kml.append("<name>").append(crime.getOffense()).append("</name>\n");
            kml.append("<description>").append(crime.getStreet()).append("</description>\n");
            kml.append("<styleUrl>#style1</styleUrl>\n");
            kml.append("<Point>\n");
            kml.append("<coordinates>").append(crime.getLongitude()).append(",").append(crime.getLatitude()).append(",0.000000</coordinates>\n");
            kml.append("</Point>\n");
            kml.append("</Placemark>\n");
        }

        kml.append("</Document>\n");
        kml.append("</kml>");

        return kml.toString();
    }
}
