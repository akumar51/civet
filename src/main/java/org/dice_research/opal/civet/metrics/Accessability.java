package org.dice_research.opal.civet.metrics;

import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.NodeIterator;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.vocabulary.DCAT;
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
public class Accessability implements Metric {

	private static final Logger LOGGER = LogManager.getLogger();
	private static final String DESCRIPTION = "Computes the number of keywords. "
			+ "If exactly one keyword is given, 4 stars are awarded. "
			+ "If more than one keyword is given, 5 stars are awarded.";

	@Override
	public Integer compute(Model model, String datasetUri) throws Exception {

		LOGGER.info("Processing dataset " + datasetUri);

		//	Resource dataset = ResourceFactory.createResource(datasetUri);
		Resource distribution = ResourceFactory.createResource(datasetUri);

//		NodeIterator nodeIterator = model.listObjectsOfProperty(distribution, DCAT.contactPoint);
		
		Statement statement = model.getProperty(distribution, DCAT.accessURL);


		
		String accessUrl = "";
 		if(statement != null)
 			accessUrl = String.valueOf(statement.getObject());
 		
 		int result = 0;
		try {
			URL urlObj = new URL(accessUrl);
			HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
			con.setRequestMethod("GET");
                        // Set connection timeout
			con.setConnectTimeout(3000);
			con.connect();
			
			int code = con.getResponseCode();
			if (code == 200) {
				result = 5;
			}
		} catch (Exception e) {
			

			result = 0;
		}
		
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