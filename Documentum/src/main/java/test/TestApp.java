package test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

// import com.documentum.fc.client.IDfSession;
import com.documentum.fc.common.DfException;

import session.Session;


public class TestApp {

    @SuppressWarnings("all")
    public static void main(String[] args) throws DfException {
        ApplicationContext appContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        Session session = (Session) appContext.getBean("session");
        System.out.println(session);
        // session.getSession();
        // session.releaseSession((IDfSession) session);
    }
}
