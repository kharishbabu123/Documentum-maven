package dql;

import  java.io.*;

import com.documentum.fc.client.*;
import com.documentum.fc.common.*;

import  org.apache.poi.xssf.usermodel.XSSFSheet;  
import  org.apache.poi.xssf.usermodel.XSSFWorkbook;
import  org.apache.poi.xssf.usermodel.XSSFRow; 
import  org.apache.poi.ooxml.POIXMLProperties;

import session.SessionCreation;

public class DqlOutputToExcel  {
	
	public static void write(String filename, String dql) throws DfException {
		System.out.println("\n"+filename+"\n");
		System.out.println(dql+"\n");
		IDfCollection collection = null;	
		IDfSession session = null;
		try {
			session = SessionCreation.getSession();
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet();
			POIXMLProperties xmlProps = workbook.getProperties();    
			POIXMLProperties.CoreProperties coreProps =  xmlProps.getCoreProperties();
			coreProps.setCreator("HBABU2@volvocars.com");
			
			// Creating Header Row
			XSSFRow rowhead = sheet.createRow((short)0);
			rowhead.createCell(0).setCellValue("Object ID");  
			rowhead.createCell(1).setCellValue("Object Name");  
			rowhead.createCell(2).setCellValue("Creation Date");  
			rowhead.createCell(3).setCellValue("Size");
			
			// Running query to fetch results from repository
			System.out.println("Running query now...\n");
			collection = runQuery(session, dql, IDfQuery.DF_READ_QUERY);
			System.out.println("Query ran successfully :)\n");
			int rowid = 1;
			
			while (collection.next()) {
				String id = collection.getString("r_object_id");
				String name = collection.getString("object_name");
				String date = collection.getString("r_creation_date");
				String size = collection.getString("r_full_content_size");
				
				// Creating and inserting data in the each row
				XSSFRow row = sheet.createRow((short)rowid++);   
				row.createCell(0).setCellValue(id);  
				row.createCell(1).setCellValue(name);  
				row.createCell(2).setCellValue(date);  
				row.createCell(3).setCellValue(size);
			}
			FileOutputStream fileOut = new FileOutputStream(filename);  
			workbook.write(fileOut);
			workbook.close();
			fileOut.close(); 			
			System.out.println("Excel file has been processed successfully.");  
		}   
		catch (Exception e) {  
			e.printStackTrace();  
			System.out.println(e.getMessage());
		}
		finally {
			collection.close(); 
			SessionCreation.releaseSession(session);
		}
	}
	
	
	// Method for running DQL query
	public static IDfCollection runQuery(IDfSession session, String dql,int queryType ) throws DfException {
		IDfCollection coll = null;
		IDfQuery query = new DfQuery();
		query.setDQL(dql);
		try { 
			coll = query.execute(session, queryType); 
		} 
		catch (DfException dfe) { 
			System.out.println("Error while running query\n"); 
			throw dfe; 
		}
		return coll;
	}

	
	public static void main(String[] args) throws DfException {
		try {
			String filename = System.getProperty("user.home")+File.separator+"Desktop"+File.separator+"DQL query output.xlsx";
			String dql = "select r_object_id, object_name , r_creation_date, r_full_content_size from dm_document enable(return_top 200);"; 
			DqlOutputToExcel.write(filename,dql);
		} 
		catch (Exception e) { 
			e.printStackTrace(); 
		}
	}
}
