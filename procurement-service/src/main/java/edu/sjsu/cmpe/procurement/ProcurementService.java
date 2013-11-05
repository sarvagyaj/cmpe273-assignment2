package edu.sjsu.cmpe.procurement;

import com.sun.jersey.api.client.Client;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

import de.spinscale.dropwizard.jobs.JobsBundle;
import edu.sjsu.cmpe.procurement.config.ProcurementServiceConfiguration;
import edu.sjsu.cmpe.procurement.util.ProcurementConfig;

public class ProcurementService extends
		Service<ProcurementServiceConfiguration> {
	public static void main(String[] args) throws Exception {
		new ProcurementService().run(args);
	}

	@Override
	public void initialize(Bootstrap<ProcurementServiceConfiguration> bootstrap) {
		bootstrap.setName("procurement-service");
		bootstrap.addBundle(new JobsBundle("edu.sjsu.cmpe.procurement.broker"));
	}

	@Override
	public void run(ProcurementServiceConfiguration configuration,
			Environment environment) throws Exception {
		Client client = Client.create();
		configuration.setClient(client);
		ProcurementConfig.setProcurementConfig(configuration);
		System.out.println("Connection with apollo broker has been set up.");
	}
}
