package com.readData.DataXML.Utility;

import com.readData.DataXML.commons.DataMapping;
import com.readData.DataXML.exceptionManager.InvalidQueryTypeException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class TallyRequest {

    public String createRequest(String requestType) throws IOException {
        if(DataMapping.typeXMLMapper.containsKey(requestType)) {
            return DataMapping.typeXMLMapper.get(requestType);
        } else throw new InvalidQueryTypeException("Invalid Query Type");
    }

    public InputStream makeRequest(String requestType) throws Exception {
        String Url = "http://localhost:9000";
        String SOAPAction = "";

        String inputXML =  Files.readString(Paths.get(createRequest(requestType)));

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


//        InputStreamReader isr = new InputStreamReader(httpConn.getInputStream());
//        BufferedReader in = new BufferedReader(isr);
//         String inputLine;
//        while ((inputLine = in.readLine()) != null) {
//            System.out.println(inputLine);
//        }

      //  in.close();
    }
}