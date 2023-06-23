package user;

import com.documentum.fc.client.*;
import com.documentum.fc.common.*;
import session.SessionCreation;


public class DisableSSO {

	public void execute() throws DfException {
		IDfSession session = null;
        IDfCollection updateColl = null;
        int disable_status = 0;
        int updateCount = 0;
        int failedCount = 0;
		try {
			session = SessionCreation.getSession();
        	String[] users = { /*List of users */ };
            for (int i = 0; i < users.length; i++) {
                String user = users[i];
                String query = "update dm_user objects set user_login_name = '"+user.toLowerCase()+"', set user_os_name = '"+user.toLowerCase()+"', set user_source ='LDAP', set user_login_domain='LDAP Core Solution' where user_login_name='"+user+"';";
                // System.out.println(query);
                updateColl = runQuery(session, query, IDfQuery.DF_EXEC_QUERY);
                if(updateColl.next()) {
                	disable_status = updateColl.getInt("objects_updated");
                	if(disable_status == 1) { 
                		System.out.println(" Disabled SSO for user : "+user); 
                        updateCount++;
                    }
                	else { 
                		System.out.println("Failed to disable SSO for user : "+user); 
                        failedCount++;
                	}
                }
            }
		}
		catch (DfException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		finally{
			SessionCreation.releaseSession(session);
            System.out.println("Updated : "+updateCount+"\nFailed : "+failedCount);
		}			
	}
    
    
    public static IDfCollection runQuery(IDfSession dfcSession, String dql, int queryType ) throws DfException {
		IDfCollection coll = null;
		IDfQuery query = new DfQuery();
		query.setDQL(dql);
		try {
			coll = query.execute(dfcSession, queryType);
			} 
		catch (DfException dfe) {
			System.out.println("Error while running query\n");
			throw dfe;
			}
		return coll;
	}



    public static void main(String[] args) throws DfException {	
		new DisableSSO().execute();
    } 
    
}
