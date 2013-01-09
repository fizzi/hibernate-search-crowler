package org.geosdi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.util.Version;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;

import static org.junit.Assert.assertEquals;

/**
 * Example testcase for Hibernate Search
 */
public class IndexAndSearchTest {

    private EntityManagerFactory emf;
    private EntityManager em;
    private static Logger log = LoggerFactory.getLogger(IndexAndSearchTest.class);

    @Before
    public void setUp() {
        initHibernate();
    }

    @After
    public void tearDown() {
        //purge();
    }

    @Test
    public void testIndexAndSearch() throws Exception {
        List<Contact> contacts = new ArrayList<Contact>();

        
        index();
        double startTime = System.currentTimeMillis(); 
        contacts = search("assessorato");
        long endTime = System.currentTimeMillis(); 
        log.info("Tempo = " + (endTime - startTime)/1000);
        
        log.info("RISULTATI LETTI DALL'indice: " + contacts.size());


    }

//	@Test
//	public void testStemming() throws Exception {
//
//		//index();
//
//		
//	}
    private void initHibernate() {
        emf = Persistence.createEntityManagerFactory("hibernate-search-example");
        em = emf.createEntityManager();
    }

    private void index() {
        FullTextEntityManager ftEm = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        try {
            ftEm.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            log.error("Was interrupted during indexing", e);
        }
    }

    private void purge() {
        FullTextEntityManager ftEm = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        ftEm.purgeAll(Contact.class);
        ftEm.flushToIndexes();
        ftEm.close();
        emf.close();
    }

    private List<Contact> search(String searchQuery) throws ParseException {
        Query query = searchQuery(searchQuery);

        List<Contact> contacts = query.getResultList();

        for (Contact c : contacts) {
            log.info("Business Name: " + c.getBusinessName());
        }
        return contacts;
    }

    private Query searchQuery(String searchQuery) throws ParseException {

        String[] contactFields = {"businessName", "category"};

        //lucene part
        Map<String, Float> boostPerField = new HashMap<String, Float>(2);
        boostPerField.put(contactFields[0], (float) 4);
        boostPerField.put(contactFields[1], (float) .5);

        FullTextEntityManager ftEm = org.hibernate.search.jpa.Search.getFullTextEntityManager(em);
        Analyzer customAnalyzer = ftEm.getSearchFactory().getAnalyzer("customanalyzer");
        QueryParser parser = new MultiFieldQueryParser(
                Version.LUCENE_34, contactFields,
                customAnalyzer, boostPerField);

        org.apache.lucene.search.Query luceneQuery;
        luceneQuery = parser.parse(searchQuery);

        final FullTextQuery query = ftEm.createFullTextQuery(luceneQuery, Contact.class);

        return query;
    }
}
