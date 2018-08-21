package ru.macrohome.server;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.macrohome.common.Answers;
import ru.macrohome.common.Entities;
import ru.macrohome.common.Tables;
import ru.macrohome.entity.Contact;

import java.util.ArrayList;
import java.util.List;

public class DataBaseUtility {
    public static Answers save(Entities entity) {
        Session session = null;
        SessionFactory sessionFactory = Connector.getSessionFactory();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(entity);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return Answers.ERROR;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Answers.OK;
    }

    public static List<Entities> getList(Tables t){
        switch (t){
            case Contacts:
                return getContacts();
            default:
                return null;
        }
    }

    private static List<Entities> getContacts(){
        SessionFactory sessionFactory = Connector.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Contact> contactList = session.createQuery("from Contact").list();


        ArrayList<Entities> list = new ArrayList<>();
        for (Contact contact : contactList) {
            list.add(contact);
        }
        return list;
    }

    public static Answers delete(Entities entity) {
        Session session = null;
        SessionFactory sessionFactory = Connector.getSessionFactory();
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.delete(entity);
            session.flush();
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            session.getTransaction().rollback();
            return Answers.ERROR;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return Answers.OK;
    }
}
