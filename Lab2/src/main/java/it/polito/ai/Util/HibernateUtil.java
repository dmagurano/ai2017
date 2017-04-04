package it.polito.ai.Util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import it.polito.ai.Lab2.Entities.BusLine;
import it.polito.ai.Lab2.Entities.BusLineStop;
import it.polito.ai.Lab2.Entities.BusStop;

public class HibernateUtil {
	
	private static final SessionFactory sessionFactory = buildSessionFactory();
	private static SessionFactory buildSessionFactory(){
		
		try{
		ServiceRegistry registry = new StandardServiceRegistryBuilder()
    			.applySetting(Environment.DRIVER,"org.postgresql.Driver")
    			.applySetting(Environment.USER, "postgres")
    			.applySetting(Environment.PASS, "ai-user-password")
    			.applySetting(Environment.HBM2DDL_AUTO, "validate")
    			.applySetting(Environment.FORMAT_SQL, "true")
    			.applySetting(Environment.SHOW_SQL, "true")
    			.applySetting(Environment.DIALECT, "org.hibernate.dialect.PostgreSQLDialect")
    			.applySetting(Environment.URL, "jdbc:postgresql://localhost:5432/trasporti")
    			.build();
    	
    	Metadata metadata = new MetadataSources(registry)
    			.addAnnotatedClass(BusLine.class)
    			.addAnnotatedClass(BusLineStop.class)
    			.addAnnotatedClass(BusStop.class)
    			.getMetadataBuilder()
    			.applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
    			.build();
    	
    	return metadata.getSessionFactoryBuilder().build();
    	
		}catch(Throwable ex){
			
			System.err.println("Initial SessionFactory creation failed, " + ex);
			throw new ExceptionInInitializerError(ex);
			
		}
    	
		
	}
	
	public static SessionFactory getSessionFactory(){
		
		return sessionFactory;
	}

}
