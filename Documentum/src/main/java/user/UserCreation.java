package user;

import com.documentum.fc.client.*;
import com.documentum.fc.common.*;

import session.SessionCreation;

public class UserCreation extends SessionCreation {
	
	public void createUser() {
		IDfSession session = null;
		try {
			session = getSession();
			
			//Creating a new User Object
			IDfUser user = (IDfUser)session.newObject("dm_user");
			
			//Setting the User Name
			//This value will be set to dm_user.user_name
			user.setUserName("harish");
			
			//Setting the User Login Password
			//This value will be set to dm_user.user_password
			user.setUserPassword("P@$$w0rd");
			
			//Setting the User Login Name
			//This value will be set to dm_user.user_login_name
			user.setUserLoginName("harishdfcuserdemo");
			
			//Setting the User OS Name and Domain name
			//This value will be set to dm_user.user_os_name and dm_user.user_os_domain
			user.setUserOSName("harishdfcuserdemo",null);
			
			//Setting the User Email Address
			//This value will be set to dm_user.user_address
			user.setUserAddress("harishdfcuserdemo@volvocars.net");
			
			//Setting the Description for the user
			//This value will be set to dm_user.description
			user.setDescription("This User is created by DFC");
			
			//Setting the User DB Name
			//This value will be set to dm_user.user_db_name
			user.setUserDBName(session.getDocbaseName());
			
			//Setting the User's Client Capability
			//This value will be set to dm_user.client_capability
			//The available values are None, Consumer, Contributor, CoOrdinator and System Admin
			user.setClientCapability(IDfUser.DF_CAPABILITY_SYSTEM_ADMIN);
			
			//Setting the User Default Cabinet
			//This value will be set to dm_user.default_folder
			//The cabinet will be created if it does not exists,
			//Default is /Temp
			user.setDefaultFolder("/DFCUserDemo", true);
			
			//Setting the Home Docbase
			//This value will be set to dm_user.home_docbase
			user.setHomeDocbase(session.getDocbaseName());
			
			//Setting the User Privileges
			//This value will be set to dm_user.user_privileges
			//The available values are Create Type, Create Group, Create Cabinet
			//System Admin and Super User
			user.setUserPrivileges(IDfUser.DF_PRIVILEGE_SUPERUSER);
			
			//Setting the User Extended Privileges
			//This value will be set to dm_user.user_xprivileges
			user.setUserXPrivileges(IDfUser.DF_XPRIVILEGE_CONFIG_AUDIT 
						+ IDfUser.DF_XPRIVILEGE_VIEW_AUDIT 
						+IDfUser.DF_XPRIVILEGE_PURGE_AUDIT);
			
			//Setting the User Source
			//This value will be set to dm_user.user_source
			user.setString("user_source","inline password");
			
			//Saving the Object
			user.save();
			
			
		} 
		catch (DfException e) {
			e.printStackTrace();
		} 
		finally {
			releaseSession(session);
		}
	}
		

	public static void main(String[] args) {
		new UserCreation().createUser();
	}
}
