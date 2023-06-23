package user;

import java.util.Scanner;

import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;

import session.SessionCreation;

public class GetLoginTicketForUser {

	// Method to get Login Ticket
	public void getLoginTicket(String user) throws DfException {
		IDfSession session = null;
		try {
			session = SessionCreation.getSession();
			System.out.println(
					"\nLoginTicket for user " + user + " is :\n\n" + session.getLoginTicketForUser(user) + "\n");
		} catch (DfException e) {
			e.printStackTrace();
		} finally {
			SessionCreation.releaseSession(session);
		}
	}

	public static void main(String[] args) throws DfException {
		Scanner input = new Scanner(System.in);
		System.out.print("\nEnter username to get login ticket: ");
		String user = input.next();
		input.close();
		new GetLoginTicketForUser().getLoginTicket(user);
	}
}
