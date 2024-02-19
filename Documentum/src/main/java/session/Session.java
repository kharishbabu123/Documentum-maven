package session;

//This code uses session manager to handle sessions

import com.documentum.com.DfClientX;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfLoginInfo;

public class Session {
 
	private String docbase;
	private String username;
	private String password;
	

	public Session() {
		System.out.println("Please enter repository details");
	}


	public Session(String docbase, String username, String password) {
		this.docbase = docbase;
		this.username = username;
		this.password = password;
	}


	@Override
	public String toString() {
		return "Session [docbase=" + docbase + ", username=" + username + ", password=" + password + "]";
	}


	public IDfSession getSession() throws DfException {

		DfClientX clientX = new DfClientX();
		IDfClient client = clientX.getLocalClient();
		IDfSessionManager sessionManager = client.newSessionManager();
		IDfLoginInfo loginInfo = clientX.getLoginInfo();

		loginInfo.setUser(username);
		loginInfo.setPassword(password);
		sessionManager.setIdentity(docbase, loginInfo);

		IDfSession session = sessionManager.getSession(docbase);

		System.out.println(
			"Session ID: " + session.getSessionId()+"\n" +
			"User name: " + session.getLoginUserName()+"\n" +
			"Server version: " + session.getServerVersion()+"\n" +
			"DBMS Name: " + session.getDBMSName()+ "\n" +
			"Docbase Name: " + session.getDocbaseName());

		return session;
	}

	public void releaseSession(IDfSession session) {
		if (session != null) {
			session.getSessionManager().release(session);
			System.out.println("\nSession released\n");
		}
	}


}
