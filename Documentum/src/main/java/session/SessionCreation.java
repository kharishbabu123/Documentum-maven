package session;

import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.DfClient;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfLoginInfo;
import com.documentum.fc.common.IDfLoginInfo;

public class SessionCreation {
	// Method to get session
	public static IDfSession getSession() {
		IDfClient client = null;
		IDfSession session = null;
		try {
			client = DfClient.getLocalClient();
			IDfLoginInfo loginInfo = new DfLoginInfo();
			loginInfo.setUser("hbabu");
			loginInfo.setPassword("Harish-Babu@1998");
			session = client.newSession("HB_DEV", loginInfo);
			if (session != null) {
				System.out.println("\nSession created\n");
			}
		} catch (DfException e) {
			System.out.println(e.getMessage());
		}
		return session;
	}

	// Method to release session
	public static void releaseSession(IDfSession session) {
		if (session != null) {
			session.getSessionManager().release(session);
			System.out.println("\nSession released\n");
		}
	}

	public static void main(String[] args) throws DfException {
		IDfSession session = getSession();
		System.out.println("Session ID: " + session.getSessionId());
		System.out.println("User name: " + session.getLoginUserName());
		System.out.println("Server version: " + session.getServerVersion());
		System.out.println("DBMS Name: " + session.getDBMSName());
		System.out.println("Docbase Name: " + session.getDocbaseName());
		releaseSession(session);
	}
}
