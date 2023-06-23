package session;

//This code uses session manager to handle sessions

import com.documentum.com.DfClientX;
import com.documentum.com.IDfClientX;
import com.documentum.fc.client.IDfClient;
import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfSessionManager;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.IDfLoginInfo;

public class SessionCreationLatest {
 
 IDfSessionManager getSession(String docbase, String username, String password) throws DfException {

     IDfClientX clientX = new DfClientX();
		IDfLoginInfo loginInfo = clientX.getLoginInfo();

		loginInfo.setUser(username);
		loginInfo.setPassword(password);
		loginInfo.setDomain(null);

		IDfClient client = clientX.getLocalClient();
		IDfSessionManager sessionManager = client.newSessionManager();
		sessionManager.setIdentity(docbase, loginInfo);

		System.out.println("Session created to docbase '"+docbase+"' as user '" + username + "'");

		return sessionManager;

 }

 public static void main (String[] args) throws DfException {

     SessionCreationLatest sc = new SessionCreationLatest();
     
     IDfSessionManager sessionManager = sc.getSession("CS_DEV", "test_user_dev", "Dev.u$3r");
     IDfSession session = sessionManager.newSession("CS_DEV");

     System.out.println(
         "Session ID: " + session.getSessionId()+"\n" +
		    "User name: " + session.getLoginUserName()+"\n" +
		    "Server version: " + session.getServerVersion()+"\n" +
		    "DBMS Name: " + session.getDBMSName()+ "\n" +
		    "Docbase Name: " + session.getDocbaseName()
     );

		sessionManager.release(session);
 }
}
