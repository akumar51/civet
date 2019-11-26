package org.dice_research.opal.civet.metrics;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.SimpleSelector;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.DCAT;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.VCARD;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dice_research.opal.civet.Metric;
import org.dice_research.opal.common.vocabulary.Opal;

/**
 * The CategorizationMetric awards stars based on the number of keywords of a
 * dataset.
 * 
 * @author Adrian Wilke
 */
public class ContactabilityMetric implements Metric {

	private static final Logger LOGGER = LogManager.getLogger();
	private static final String DESCRIPTION = "Computes the number of keywords. "
			+ "If exactly one keyword is given, 4 stars are awarded. "
			+ "If more than one keyword is given, 5 stars are awarded.";

	@Override
	public Integer compute(Model model, String datasetUri) throws Exception {

		LOGGER.info("Processing dataset " + datasetUri);

		//Resource dataset = ResourceFactory.createResource(datasetUri);
		Resource distribution = ResourceFactory.createResource(datasetUri);

//		NodeIterator nodeIterator = model.listObjectsOfProperty(distribution, DCAT.contactPoint);
		
//		Statement statement = model.getProperty(distribution, DCAT.contactPoint);
		StmtIterator IteratorOverPublisher = model
				.listStatements(new SimpleSelector(null, DCAT.contactPoint, (RDFNode) null));
		int result = 0;
		System.out.println("Success1");


		if (IteratorOverPublisher.hasNext()) {
			System.out.println("Success2");


			while (IteratorOverPublisher.hasNext()) {
				System.out.println("Success while");


				Statement StatementWithPublisher = IteratorOverPublisher.nextStatement();
				RDFNode Publisher = StatementWithPublisher.getObject();
				
				if (Publisher.isAnon()) {
					System.out.println("Success is anon");

					Resource PublisherBlankNode = (Resource) Publisher;
					
					if ((PublisherBlankNode.hasProperty(RDF.type, VCARD.NAME)))
						 {
						
//						&&(PublisherBlankNode.hasProperty(RDF.type, VCARD.EMAIL))
						System.out.println("vcard found");

						result++;
						
						System.out.println(result);

					}
					
					}
						
			}}
//
//	//	
////		String accessUrl = "";
////			if(statement != null)
////				accessUrl = String.valueOf(statement.getObject());
//		RDFNode Publisher = statement.getObject();
//
//			System.out.println("Success1");
//
//		
//			Resource PublisherBlankNode = (Resource) Publisher;
//			System.out.println("Success2");
//
//			if (PublisherBlankNode.hasProperty(RDF.type, VCARD.NAME)) {
//				result=5;
//				System.out.println("Success3");
//			}
//			
//			
//	
//		
		return result;
		}
	

	@Override
	public String getDescription() {
		return DESCRIPTION;
	}

	@Override
	public String getUri() throws Exception {
		return Opal.OPAL_METRIC_CATEGORIZATION.getURI();
	}

}






















