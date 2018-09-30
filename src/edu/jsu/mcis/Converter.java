package edu.jsu.mcis;

import java.io.*;
import java.util.*;
import com.opencsv.*;
import org.json.simple.*;
import org.json.simple.parser.*;

public class Converter {
    
    
    @SuppressWarnings("unchecked")
    public static String csvToJson(String csvString) {
        
        String results = "";
        
        try {
            
            CSVReader reader = new CSVReader(new StringReader(csvString));
            List<String[]> full = reader.readAll();
            Iterator<String[]> iterator = full.iterator();
            
            // INSERT YOUR CODE HERE
        
            LinkedHashMap<String,ArrayList> jsonObject = new LinkedHashMap<>();
            
            ArrayList<String> colHeaders = new ArrayList<>();
            ArrayList<String> rowHeaders = new ArrayList<>();
            ArrayList<ArrayList> data = new ArrayList<>();
            ArrayList<Integer> row = null;
            
            String[] line = iterator.next();
            
            for(String field: line)
                colHeaders.add(field);
            
            while(iterator.hasNext()) {
                line = iterator.next();
                row = new ArrayList<>();
                for(int k = 0; k < line.length; ++k) {
                    if (k == 0)
                        rowHeaders.add(line[k]);
                    else
                        row.add(Integer.parseInt(line[k]));
                }
                data.add(row);
            }
        jsonObject.put("rowHeaders",rowHeaders);
        jsonObject.put("data", data);
        jsonObject.put("colHeaders", colHeaders);
        
        results = JSONValue.toJSONString(jsonObject);
        
        
        }        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }
    
    public static String jsonToCsv(String jsonString) {
        
        String results = "";
        
        try {

            StringWriter writer = new StringWriter();
            CSVWriter csvWriter = new CSVWriter(writer, ',', '"', '\n');
            
            // INSERT YOUR CODE HERE
            
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject)parser.parse(jsonString);
            
            
            JSONArray colHeaders = (JSONArray)jsonObject.get("colHeaders");
            JSONArray rowHeaders = (JSONArray)jsonObject.get("rowHeaders");
            JSONArray data = (JSONArray)jsonObject.get("data");

            
            String[] colStringArray = new String[colHeaders.size()];
            String[] rowStringArray = new String[rowHeaders.size()];
            String[] dataStringArray = new String[data.size()];
            
            // First, get the column headers, copying each into the
            // "colStringArray" container.

            for (int i = 0; i < colHeaders.size(); i++){
                colStringArray[i] = colHeaders.get(i).toString();
            }
            
            // Next, output the column headers to the CSV writer.  (This
            // is all we need to do with the column headers.)
            
            csvWriter.writeNext(colStringArray);
            

            
            for (int i = 0; i < rowHeaders.size(); i++){
            
                // Get next row header
            
                rowStringArray[i] = rowHeaders.get(i).toString();
                
                // Get next set of row data
                
                dataStringArray[i] = data.get(i).toString();
                
            }

            for (int i = 0; i < dataStringArray.length; i++) {
                
                JSONArray dataValues = (JSONArray)parser.parse(dataStringArray[i]);


                String[] row = new String[dataValues.size()+1];

                
                row[0] = rowStringArray[i];
                row[1] = dataValues.get(0).toString();
                row[2] = dataValues.get(1).toString();
                row[3] = dataValues.get(2).toString();
                row[4] = dataValues.get(3).toString();
                
                csvWriter.writeNext(row);
                
            }
            
            // Output the completed CSV data to a string
            
            results = writer.toString();
            
            
        }
        
        catch(Exception e) { return e.toString(); }
        
        return results.trim();
        
    }

}