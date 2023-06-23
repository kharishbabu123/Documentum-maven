package user;

import java.util.Scanner;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.client.IDfUser;
import com.documentum.fc.common.DfException;

import session.SessionCreation;

public class GetSSOStatus {

	IDfUser userObject = null;
	private boolean isSSOEnabled = false;

	public boolean getStatus(String userCDSID) throws DfException {
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
			// new UpdateSSOStatus().updateSSO(isSSOEnabled, userObject);
		} catch (DfException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			SessionCreation.releaseSession(session);
		}
		return isSSOEnabled;
	}

	public static void main(String[] args) throws DfException {
		Scanner input = new Scanner(System.in);
		System.out.print("\nEnter user CDS ID : ");
		String user = input.next();
		input.close();
		new GetSSOStatus().getStatus(user);
	}
}
