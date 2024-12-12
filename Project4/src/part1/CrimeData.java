package part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CrimeData {
    ArrayList<String[]> linesOfData;

    // Constructor to initialize CrimeData object and read crime data from file
    public CrimeData() throws IOException {
        linesOfData = new ArrayList<String[]>();
        BufferedReader reader = new BufferedReader(new FileReader("CrimeLatLonXY1990.csv"));

        // Skipping the header line
        reader.readLine();

        // Reading each line of the file and storing data in linesOfData ArrayList
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            linesOfData.add(data);
        }

        // Closing the BufferedReader
        reader.close();
    }

    // Method to find the index of the start date in the crime data
    public int findStartDateIndex(String startDate) {
        for (int i = 0; i < linesOfData.size(); i++) {
            if (linesOfData.get(i)[5].equals(startDate)) {
                return i;
            }
        }
        return -1; // Returning -1 if start date is not found
    }

    // Method to find the index of the end date in the crime data
    public int findEndDateIndex(String endDate) {
        for (int i = linesOfData.size() - 1; i >= 0; i--) {
            if (linesOfData.get(i)[5].equals(endDate)) {
                return i;
            }
        }
        return -1; // Returning -1 if end date is not found
    }
}
