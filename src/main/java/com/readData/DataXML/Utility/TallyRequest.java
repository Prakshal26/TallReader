package com.readData.DataXML.Utility;

import com.readData.DataXML.commons.DataMapping;
import com.readData.DataXML.exceptionManager.CustomException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class TallyRequest {

    private String createRequest(String requestType) throws IOException {
        if(DataMapping.typeXMLMapper.containsKey(requestType)) {
            return DataMapping.typeXMLMapper.get(requestType);
        } else throw new CustomException("Invalid Query Type");
    }

    public InputStream makeRequest(String requestType) {
        try {
            String Url = "http://localhost:9000";
            String SOAPAction = "";
            String inputXML="";
            try {
                inputXML = Files.readString(Paths.get(createRequest(requestType)));
            } catch (FileNotFoundException | CustomException  e) {
                throw new CustomException("Request File not Found " + DataMapping.typeXMLMapper.get(requestType));
            }

            URL url = new URL(Url);
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) connection;

            ByteArrayInputStream bin = new ByteArrayInputStream(inputXML.getBytes());
            ByteArrayOutputStream bout = new ByteArrayOutputStream();

            bin.transferTo(bout);

            byte[] b = bout.toByteArray();

            httpConn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
            httpConn.setRequestProperty("SOAPAction", SOAPAction);
            httpConn.setRequestMethod("GET");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);

            OutputStream out = httpConn.getOutputStream();
            out.write(b);
            out.close();

            return httpConn.getInputStream();
        } catch(Exception e) {
            throw new CustomException("Unable to make Request to tally: Most likely tally server is down. "+e.getMessage());
        }

//        InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
//        BufferedReader in = new BufferedReader(isr);
//         String inputLine;
//        while ((inputLine = in.readLine()) != null) {
//            System.out.println(inputLine);
//        }

      //  in.close();
    }
}