package document;

import com.documentum.fc.client.IDfFolder;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;

import session.SessionCreation;

public class FolderCreation {
	public void createFolder(String folderName) {
		IDfSession session = null;
		String path = null;
		try {
			session = SessionCreation.getSession();
			path = session.getUser(session.getLoginUserName()).getDefaultFolder();
			IDfFolder newFolder = (IDfFolder) session.newObject("dm_folder");
			newFolder.setObjectName(folderName);
			newFolder.link(path);
			newFolder.save();
			
			System.out.println("Folder '" + folderName + "' created successfully in "+path+"\n");
			System.out.println("r_object_id of folder is " + newFolder.getId("r_object_id"));
		} catch (DfException e) {
			e.printStackTrace();
		} finally {
			SessionCreation.releaseSession(session);
		}
	}

	public static void main(String[] args) {
		String folderName = "Test Folder - DFC";
		new FolderCreation().createFolder(folderName);
	}
}
