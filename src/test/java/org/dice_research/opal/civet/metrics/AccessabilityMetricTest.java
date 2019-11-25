package org.dice_research.opal.civet.metrics;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.vocabulary.DCAT;
import org.apache.jena.vocabulary.RDF;
import org.dice_research.opal.civet.TestData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests {@link CategorizationMetric}.
 * 
 * @author Adrian Wilke
 */
public class AccessabilityMetricTest {

	TestData testdata;

	private static final String TEST_EDP_ICE = "model4881.ttl";
	private static final String TEST_EDP_ICE_DATASET = "http://projekt-opal.de/distribution/https___europeandataportal_eu_set_distribution_aaa86820_459c_4636_9cc4_5c55236fc898";
	
	
	
	@Before
	public void setUp() throws Exception {
		testdata = new TestData();
	}

	/**
	 * Tests all 3 cases.
	 */
	@Test
	public void testCases() throws Exception {
		Accessability metric = new Accessability();

		Model model = ModelFactory.createDefaultModel();
		String datasetUri = "https://example.org/dataset-1";
		Resource dataset = ResourceFactory.createResource(datasetUri);
		model.add(dataset, RDF.type, DCAT.Dataset);

		Assert.assertEquals("No keywords", 0, metric.compute(model, datasetUri).intValue());

		model.addLiteral(dataset, DCAT.keyword, ResourceFactory.createPlainLiteral("keyword-1"));
		Assert.assertEquals("One keyword", 4, metric.compute(model, datasetUri).intValue());

		model.addLiteral(dataset, DCAT.keyword, ResourceFactory.createPlainLiteral("keyword-2"));
		Assert.assertEquals("Two keywords", 5, metric.compute(model, datasetUri).intValue());
	}

	/**
	 * Tests all 3 cases.
	 */
	@Test
	public void testEdpIce() throws Exception {
		
		// Compute stars
		Accessability metric = new Accessability();
		Integer stars = metric.compute(testdata.getModel(TEST_EDP_ICE), TEST_EDP_ICE_DATASET);

		// Keywords in test dataset:
		// "Iceland" , "Downloadable Data" , "land use" , "land cover"
		// Should result in 5 stars
//		Assert.assertEquals(TEST_EDP_ICE, 5, stars.intValue());
		Assert.assertEquals(TEST_EDP_ICE, 5, stars.intValue());
	}

}