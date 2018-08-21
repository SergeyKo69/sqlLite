package ru.macrohome.server;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Properties;

public class Connector {
    private static SessionFactory sessionFactory = null;

    private Connector() {
    }

    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null){
            try {
                Configuration configuration = new Configuration();
                configuration.configure().configure();

                Properties properties = configuration.getProperties();

                sessionFactory = configuration.buildSessionFactory();

            }catch (HibernateException e){
                e.printStackTrace();
            }
            return sessionFactory;
        }else{
            return sessionFactory;
        }
    }

    public static void closeSessionFactory(){
        if (sessionFactory != null){
            sessionFactory.close();
        }
    }
}
