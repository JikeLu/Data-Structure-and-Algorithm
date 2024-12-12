package part1;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * The class read data from csv file and store it as an adjacent matrix
 */
public class TSPChecker {
	ArrayList<String[]> memory;
	
	public TSPChecker() throws IOException{
		memory = new ArrayList<String[]>();
		CSVReader reader = new CSVReader(new FileReader("src/CrimeLatLonXY1990.csv"), ',');
		
		//read all lines at once
        List<String[]> records = reader.readAll();
        Iterator<String[]> iterator = records.iterator();
        
        //skip header row
        iterator.next();
        
        while(iterator.hasNext()){
            memory.add(iterator.next());
        }
         
        reader.close();
        
	}
	

	
	

}
