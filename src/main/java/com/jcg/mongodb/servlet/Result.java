package com.jcg.mongodb.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.model.Filters;
//import com.mongodb.client.model.Updates;

@WebServlet("/resultServlet")
public class Result extends HttpServlet {
	
	
	private static final long serialVersionUID = 1L;

	// This method is called by the servlet container to process a 'post' request
		public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
		{
			
			handleRequest(request, response);
		}

		public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException 
		{

			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			int total = -1;
			String screening=null;

			// Reading post parameters from the request
			int first = Integer.parseInt(request.getParameter("first"));
			int second = Integer.parseInt(request.getParameter("second"));
			int third = Integer.parseInt(request.getParameter("third"));
			int fourth = Integer.parseInt(request.getParameter("fourth"));
			int fifth = Integer.parseInt(request.getParameter("fifth"));
			int sixth = Integer.parseInt(request.getParameter("sixth"));
			
			total = first + second + third + fourth + fifth + sixth;
			
			if (total > 4)
				screening ="yes";
			if (total <= 4)
				screening="no";

			

			// Creating a Mongo client
		    
		    try (MongoClient mongo = new MongoClient( "localhost" , 27017 ))
		    {
		    	// Accessing the database 
			    MongoDatabase database = mongo.getDatabase("TEJA"); 
			    
			    
			    try
			    {
			    	// Creating a collection
					database.createCollection("RegistrationInfo");
			    } catch (Exception exception) 
			    {
			        System.err.println("Collection:- RegistrationInfo already Exists");
			    }
			    
			    // Retrieving a collection
			    MongoCollection<Document> collection = database.getCollection("PatientInfo"); 
			    System.out.println("Collection RegistrationInfo selected successfully"); 
			    
			    try
			    {
			    	BasicDBObject searchQuery = new BasicDBObject("Aadhaar UID",Registration.aadhaar);
			    	
			    	BasicDBObject updateFields = new BasicDBObject();
			    	updateFields.append("First", first);
			    	updateFields.append("Second", second);
			    	updateFields.append("Third", third);
			    	updateFields.append("Fourth", fourth);
			    	updateFields.append("Fifth", fifth);
			    	updateFields.append("Sisth", sixth);
			    	updateFields.append("Total Score", total);
			    	updateFields.append("Screening", screening);
			    	BasicDBObject setQuery = new BasicDBObject();
			    	setQuery.append("$set", updateFields);
			    	collection.updateMany(searchQuery, setQuery);
			    	
			    	
			    	/*collection.updateMany(Filters.eq("Aadhaar UID",Registration.aadhaar), Updates.combine(
			    	        Updates.set("Smoke", smoke),
			    	        Updates.set("Alcohol", alcohol),
			    	        Updates.set("Waist", waist),
			    	        Updates.set("Physical Activity", phy_act),
					    	Updates.set("Family History", fam_his),
					    	Updates.set("Total Score", total),
					    	Updates.set("Screening", screening)
					    	
			    	    ));*/
			    	
			    }
			    catch (MongoException me) {
	                System.err.println("Unable to insert due to an error: " + me);
	            }
			    
		    }
		   
//		    out.println("<!DOCTYPE html>");
//			out.println("<html>");
//			out.println("<head>");
//			out.println("<title>NCD Risk Assessment Checklist</title> ");
//			out.println("</head>");
//			out.println("<body>");
//			out.println("<table align=center border=1>");
//			out.println("<tr>");
//			out.println("<th colspan=2 style=background-color:lightgreen>NCD Risk Assessment  Checklist Score</th>");
//			out.println("</tr>");
//
//			out.println("<tr style=background-color:skyblue>");
//			out.println("<th>Question</th>");
//			out.println("<th>Score</th>");
//			out.println("</tr>");
//
//			out.println("<tr>");
//			out.println("<td>1. What is your age? (in complete years)</td>");
//			out.println("<td>" + age + "</td> ");
//			out.println("</tr>");
//
//			out.println("<tr>");
//			out.println("<td>2. Do you smoke or Consume smokeless product like Gutka or Khaini?</td>");
//			out.println("<td>" + smoke + "</td>");
//			out.println("</tr>");
//
//			out.println("<tr>");
//			out.println("<td>3. Do you consume alcohol daily?</td>");
//			out.println("<td>" + alcohol + "</td>");
//			out.println("</tr>");
//
//			out.println("<tr>");
//			out.println("<td>4. Measurement of waist in (cm)</td>");
//			out.println("<td>" + waist + "</td>");
//			out.println("</tr>");
//
//			out.println("<tr>");
//			out.println("<td>5. Do you undertake any physical activities for a minimum of 150 minutes a week?</td>");
//			out.println("<td>" + phy_act + "</td>");
//			out.println("</tr>");
//
//			out.println("<tr>");
//			out.println(
//					"<td>6. Do you have a family history (any one of your parents or siblings) of high blood pressure, diabetes and heart disease?</td>");
//			out.println("<td>" + fam_his + "</td>");
//			out.println("</tr>");
//
//			out.println("<tr style=text-align:center>");
//			out.println("<th rowspan=2 style=background-color:lightpink align=left>Total Score: </th>");
//			out.println("<td>" + total + "</td>");
//			out.println("</tr>");
//
//			out.println("</table>");
//
//			if (total == -1)
//				out.println("<h2>Please answer the questions first.");
//			if (total > 4)
//				out.println("The person may be at higher risk of NCDs and needs to be prioritized for"
//						+ "attending screening.");
//			if (total < 4)
//				out.println("<br>The person is not at risk of NCDs and doesn't need screening.");
//
//			out.println("</h2><br><br><button onclick=location.href='checklist.jsp'; align=center>Previous</button>");
//			out.println(
//					"<button onclick=location.href='index.jsp'; align=center>Back to Registration Page</button>");
//			out.println("</body>");
//			out.println("</html>");
		   	    
	   }
}

