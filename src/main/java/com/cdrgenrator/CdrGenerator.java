package com.cdrgenrator;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;




public class CdrGenerator {

       // Random value = new Random();

       
       @SuppressWarnings("unchecked")
       public static void main(String arg[]) throws IOException {

              Numbers x = new Numbers();

              Scanner sc = new Scanner(System.in);

              JSONArray array = new JSONArray();

              System.out.println("enter the number of users");

              int users = sc.nextInt();

              long beginTime = Timestamp.valueOf("2016-01-01 00:00:00").getTime();

              long endTime = Timestamp.valueOf("2016-01-31 00:58:00").getTime();

              long diff = endTime - beginTime+x.random(10);
              

              SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

              for (int i = 0; i < users; i++) {

                     long servedIMSI = 4057507392746291L + i;

                     FileWriter file = new FileWriter("C:/Users/evhrkum/workspace/Detection/cdr_data/" + servedIMSI + ".txt");

                     int number_of_records = x.random(70) +x.random(1000);
                     System.out.println("number:"+number_of_records);

                     //System.out.println("User " + servedIMSI + ", For records " + number_of_records);
                     

                     for (int j = 0; j < number_of_records; j++) {

                           JSONObject obj = new JSONObject();

                           obj.put("servedIMSI", servedIMSI);
                     
                           int MCC =  x.random(300) + x.random(400);
                           int MNC =  x.random(10)+x.random(600);
                           int MSIN = (int) (Math.random() * 387349291);
                           
                           String targetIMSI = MCC+" "+MNC+" "+MSIN;
                           //System.out.println(targetIMSI);// imsi of
                                                                                                                     // called
                                                                                                                     // and
                                                                                                                     // caller
                           obj.put("targetIMSI", targetIMSI);
                                                       
                           long seizureTimestamp = beginTime + (long) (Math.random() * diff);
                           long answerTimestamp = seizureTimestamp+x.random(20000) ; 
                           long releaseTimestamp = answerTimestamp + 5000 + x.random(5429843);
                           
                           
                     //     System.out.println(timestamp);
                           
                           Date seizureTime = new Date(seizureTimestamp); // seizureTimestamp
                     
                           obj.put("seizureTimeStamp", dateFormat.format(seizureTime));  /*mandatory field for unsuccessful calls and
                                                                                                                                      optional field for successful calls*/       

                           Date answerTime = new Date(answerTimestamp);    // answerTimestamp
                                                
                           obj.put("answerTimeStamp", dateFormat.format(answerTime));

                           Date releaseTime = new Date(releaseTimestamp); // releaseTimestamp
                     
                           obj.put("releaseTimeStamp", dateFormat.format(releaseTime)); /*optional field for successful and 
                                                                                                                                   unsuccessful calls*/
                           
                           long callDuration = (releaseTimestamp-seizureTimestamp)/1000;
                     //     System.out.println(callduration);
                           
                           obj.put("callDuration", callDuration);
                           
                           int VAS_ID = x.random(2);                       //it will be octet data originally, but in this case we are taking either 
                                                                                                //0 or 1 as we consider only international pack

                           obj.put("VAS_ID", VAS_ID);

                           array.add(obj);

                     }

                     file.write(array.toJSONString());

                     file.flush();
                     array.clear();

                     file.close();

              }

              sc.close();

              System.out.println("Finished");

       }

}

/*     obj.put("call_duration", duration_seconds); not considering as we can derive from above and as we are take 
 *                                                                                not taking zero second calls, only incomplete and complete calls 
 *                                                                                are considered.
       
       System.out.println(duration_seconds);*/


