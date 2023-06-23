package user;

import java.util.Scanner;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfUser;
import com.documentum.fc.common.DfException;

import session.SessionCreation;

public class UpdateSSOStatus {

	IDfUser userObject = null;
	private boolean isSSOEnabled = false;

	public void getStatus(String userCDSID) throws DfException {
		IDfSession session = null;
		try {
			userObject = null;
			isSSOEnabled = false;
			session = SessionCreation.getSession();

			String usercdsid = userCDSID.toLowerCase();
			IDfUser userObject = session.getUserByLoginName(usercdsid, null);
			if (userObject == null)
				userObject = session.getUserByLoginName(usercdsid.toUpperCase(), null);

			if (userObject == null) {
				System.out.println("Error : Please provide a valid CDS ID.");
			} else {
				this.userObject = userObject;
			}

			System.out.println("User Name : " + userObject.getUserName());
			System.out.println("User Login Name : " + userObject.getUserLoginName());
			System.out.println("E-Mail Address : " + userObject.getUserAddress());

			if (userObject.getUserLoginName().equals(userObject.getUserLoginName().toUpperCase())
					&& userObject.getUserOSName().equals(userObject.getUserOSName().toUpperCase())
					&& (userObject.getUserSourceAsString() == null || userObject.getUserSourceAsString().length() == 0
					|| userObject.getUserSourceAsString().equals(""))
					&& (userObject.getString("user_login_domain") == null
					|| userObject.getString("user_login_domain").length() == 0
					|| userObject.getString("user_login_domain").equals(""))) {
				System.out.println("SSO Status : Enabled");
				isSSOEnabled = true;
			} else {
				System.out.println("SSO Status : Disabled");
				isSSOEnabled = false;
			}

			// To update the SSO status
			if (isSSOEnabled) {

				// Disabling SSO
				try {
					userObject.setUserLoginName(userObject.getUserLoginName().toLowerCase());
					userObject.setUserOSName(userObject.getUserOSName().toLowerCase(), null);
					session.getObjectByQualification(userCDSID);
					userObject.setString("user_source", "LDAP");
					userObject.setString("user_login_domain", "LDAP Core Solution");
					userObject.save();
					System.out.println("User SSO has been disabled");
				} catch (DfException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			} else {
				// Enabling SSO
				userObject.setUserLoginName(userObject.getUserLoginName().toUpperCase());
				userObject.setUserOSName(userObject.getUserOSName().toUpperCase(), null);
				userObject.setString("user_source", "");
				userObject.setString("user_login_domain", "");
				userObject.save();
				System.out.println("User SSO has been enabled");
			}
		} catch (DfException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			SessionCreation.releaseSession(session);
		}
	}

	public static void main(String[] args) throws DfException {
		Scanner input = new Scanner(System.in);
		System.out.print("\nEnter user CDS ID : ");
		String user = input.next();
		input.close();
		new GetSSOStatus().getStatus(user);
	}

}
