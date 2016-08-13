package Ericsson.cdrDataGeneration;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

class Numbers {
    Random randnum;

    public Numbers() {
           randnum = new Random();

    }

    public int random(int i) {
           return randnum.nextInt(i);
    }
}


public class dataGeneration {

	static // Random value = new Random();
	Random rand = new Random();
	
	@SuppressWarnings("unchecked")
	public static void main(String arg[]) throws IOException {

		Numbers x = new Numbers();

		Scanner sc = new Scanner(System.in);

		JSONArray array = new JSONArray();

		System.out.println("enter the number of users");

		int users = sc.nextInt();

		long beginTime = Timestamp.valueOf("2016-01-01 00:00:00").getTime();

		long endTime = Timestamp.valueOf("2016-01-31 23:59:00").getTime();

		//long diff = endTime - beginTime+1;
		

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		for (int i = 0; i < users; i++) {

			//String targetIMSI = MCC+" "+MNC+" "+MSIN;
			long servedIMSI = 4057507392746291L + i;
			String location = "405"+""+"750"+Integer.toHexString(rand.nextInt(0xf000) + 0x0002);
			

		FileWriter file = new FileWriter("C:/Users/evhrkum/workspace/Detection/cdr_data/" + servedIMSI + ".txt");

			int number_of_records = Math.abs((int)(rand.nextGaussian()*319));
			
			
			
			System.out.println(number_of_records);

			//System.out.println("User " + servedIMSI + ", For records " + number_of_records);
			
			long seizureTimestamp = 0;
			long answerTimestamp = 0; 
			long releaseTimestamp = beginTime;

			for (int j = 0; j < number_of_records; j++) {
				
				if(answerTimestamp<endTime){
					

				JSONObject obj = new JSONObject();

				obj.put("servedIMSI", servedIMSI);
				
				obj.put("location", location);
			
				int MCC =  x.random(300) + x.random(400);
				int MNC =  x.random(10)+x.random(600);
				int MSIN = (int) (Math.random() * 387349291);
				
				String targetIMSI = MCC+" "+MNC+" "+MSIN;
				//System.out.println(targetIMSI);// imsi of
																	// called
																	// and
																	// caller
				obj.put("targetIMSI", targetIMSI);
								
				String LAICalled = MCC+""+MNC+""+Integer.toHexString(rand.nextInt(0xfff0) + 0x0002);
				//System.out.println(LAICalled);
				obj.put("locationEstimate", LAICalled);  
						
				
				seizureTimestamp = releaseTimestamp +60000 +ThreadLocalRandom.current().nextLong(300000L, 28846254L+1L);
				answerTimestamp = seizureTimestamp +  ThreadLocalRandom.current().nextLong(20000L);
				releaseTimestamp = answerTimestamp + ThreadLocalRandom.current().nextLong(500L, 7200000L+1L);
				
				
				Date seizureTime = new Date(seizureTimestamp);				//mandatory field for unsuccessful calls and
			    System.out.println(dateFormat.format(seizureTime));			//optional field for successful calls
				obj.put("seizureTimeStamp", dateFormat.format(seizureTime));								
				
				  	
				Date answerTime = new Date(answerTimestamp); 	
				obj.put("answerTimeStamp", dateFormat.format(answerTime));
				System.out.println(dateFormat.format(answerTime));

				Date releaseTime = new Date(releaseTimestamp); 
				obj.put("releaseTimeStamp", dateFormat.format(releaseTime));	//optional field for successful and 
				System.out.println(dateFormat.format(releaseTime)); 			//	unsuccessful calls												
				
				System.out.println();	
																			
				long callDuration = (releaseTimestamp-seizureTimestamp)/1000;
				System.out.println(callDuration);
				
				obj.put("callDuration", callDuration);
				
				int VAS_ID = x.random(2);				//it will be octet data originally, but in this case we are taking either 
														//0 or 1 as we consider only international pack

				obj.put("VAS_ID", VAS_ID);

				array.add(obj);
			}
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

