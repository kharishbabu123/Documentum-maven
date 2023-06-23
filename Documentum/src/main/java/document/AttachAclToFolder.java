package document;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfId;
import com.documentum.fc.common.IDfId;

import session.SessionCreation;

public class AttachAclToFolder {
	public void attachACL(IDfId folderID) {
		IDfSession session = null;
		try {
			session = SessionCreation.getSession();
			IDfId idObj = session.getIdByQualification("dm_folder where r_object_id='" + folderID + "'");
			IDfSysObject sysObj = (IDfSysObject) session.getObject(idObj);
			StringBuffer results = new StringBuffer("");
			results.append("Previous value: " + sysObj.getValue("acl_name").toString());
			sysObj.setString("acl_name", "Harish_test_acl");
			if (sysObj.fetch(null)) {
				results = new StringBuffer("Object is no longer current.");
			} else {
				sysObj.save();
				results.append("\nNew value: " + "Harish_test_acl");
			}
		} catch (DfException e) {
			e.printStackTrace();
		} finally {
			SessionCreation.releaseSession(session);
		}
	}

	public static void main(String[] args) {
		IDfId folderID = new DfId("0b01331383c66311");
		new AttachAclToFolder().attachACL(folderID);
	}
}
