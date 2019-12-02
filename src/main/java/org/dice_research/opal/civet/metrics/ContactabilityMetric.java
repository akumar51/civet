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
	private static final String DESCRIPTION = "Computes the quality of dataset as per contactability metric." 
			+ "Four kinds of ratings are awarded to the dataset which are following: "
			+ "Rating 5: Name, Email and Address is in the dataset."
			+ "Rating 4: If any two of the Name, Email and Address is in the dataset."
			+ "Rating 2: If any one of the Name, Email and Address is in the dataset."
			+ "Rating 0: If none of the Name, Email and Address is in the dataset.";

	@Override
	public Integer compute(Model model, String datasetUri) throws Exception {
		//creating a dataset
		LOGGER.info("Processing dataset " + datasetUri);
		int result = 0;
		Resource distribution = ResourceFactory.createResource(datasetUri);
		
		StmtIterator stmtItr = model.listStatements(new SimpleSelector
				(distribution,DCAT.contactPoint,(RDFNode) null)
//				{
//                    @Override
//                    public boolean selects(Statement s) {
//                            return s.getString().contentEquals("Татяна Стоянова");
//                    }
//				}
				);
		
		boolean nameFound=false;
		boolean emailFound=false;
		boolean adrFound=false;

		if(stmtItr.hasNext())
		{
			Statement stmt = stmtItr.nextStatement();
			RDFNode object = stmt.getObject();
			Resource objectAsResource = (Resource) object ;
			if(objectAsResource.hasProperty(VCARD.EMAIL))
			{
                String email = objectAsResource.getProperty
                        (VCARD.EMAIL).getObject().toString();
                if(!email.equals(""))
                {
					emailFound =  true ;
                }
			}
			if(objectAsResource.hasProperty(VCARD.NAME))
			{
                String name = objectAsResource.getProperty
                        (VCARD.NAME).getObject().toString();
                if(!name.equals(""))
                {
					nameFound =  true ;
                }
			}
			if(objectAsResource.hasProperty(VCARD.ADR))
			{
                String adr = objectAsResource.getProperty
                        (VCARD.ADR).getObject().toString();
                if(!adr.equals(""))
                {
					adrFound =  true ;
                }
			}
		}
		
		if(nameFound && emailFound && adrFound)
			return 5;
		else if((nameFound && emailFound)||(nameFound && adrFound)||(emailFound && adrFound))
			return 4;
		else if(nameFound || emailFound || adrFound )
			return 2;
		else 
			return 0;
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



