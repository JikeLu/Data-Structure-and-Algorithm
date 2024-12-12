public class CrimeData {
    double x;
    double y;
    int time;
    String street;
    String offense;
    String date;
    int tract;
    double latitude;
    double longitude;

    public CrimeData(double x, double y, int time, String street, String offense, String date, int tract, double latitude, double longitude) {
        this.x = x;
        this.y = y;
        this.time = time;
        this.street = street;
        this.offense = offense;
        this.date = date;
        this.tract = tract;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getTime() {
        return time;
    }

    public int getTract() {
        return tract;
    }

    public String getDate() {
        return date;
    }

    public String getOffense() {
        return offense;
    }

    public String getStreet() {
        return street;
    }



    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
