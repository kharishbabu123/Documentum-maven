package document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.documentum.fc.client.IDfDocument;
import com.documentum.fc.client.IDfSession;

import session.SessionCreation;

public class DocumentCreation {
	public void createDocument() {
		IDfSession session = null;
		FileReader reader = null;
		ByteArrayOutputStream outputStream = null;
		int buff = 0;
		try {
			session = SessionCreation.getSession();

			File file = new File(System.getProperty("user.home") + "/Desktop/TestDocument.docx");

			// Creating a Reader Object for the file
			reader = new FileReader(file);

			// Initializing the Output stream
			outputStream = new ByteArrayOutputStream();

			// Creating the ByteArrayOutputStream for the File
			while ((buff = reader.read()) != -1) {
				outputStream.write(buff);
			}

			// Creating the dm_document object
			IDfDocument document = (IDfDocument) session.newObject("dm_document");

			// Setting the Object Name for the Document
			document.setObjectName("TestDocument.docx");

			// Setting the Title for the Document
			document.setTitle("Harish Create Document Example");

			// Setting the Subject for the Document
			document.setSubject("Harish Create Document DFC Example");

			// Setting the content type to the object
			document.setContentType("msw12");

			// Setting the content to the object
			document.setContent(outputStream);

			// Linking the Document to the cabinet
			document.link("/TestCabinet");

			// Saving the Document Object
			document.save();

			System.out.println("Document saved");
		}

		catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			SessionCreation.releaseSession(session);
		}
	}

	public static void main(String[] args) {
		new DocumentCreation().createDocument();
	}
}