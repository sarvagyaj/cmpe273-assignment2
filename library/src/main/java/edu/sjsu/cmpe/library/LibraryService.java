package edu.sjsu.cmpe.library;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;

import edu.sjsu.cmpe.library.api.resources.BookResource;
import edu.sjsu.cmpe.library.api.resources.RootResource;
import edu.sjsu.cmpe.library.config.LibraryServiceConfiguration;
import edu.sjsu.cmpe.library.repository.BookRepository;
import edu.sjsu.cmpe.library.repository.BookRepositoryInterface;
import edu.sjsu.cmpe.library.subscriber.SubscribeMessages;
import edu.sjsu.cmpe.library.ui.resources.HomeResource;
import edu.sjsu.cmpe.library.util.LibraryConfig;

public class LibraryService extends Service<LibraryServiceConfiguration> {

	public static void main(String[] args) throws Exception {
		new LibraryService().run(args);
	}

	@Override
	public void initialize(Bootstrap<LibraryServiceConfiguration> bootstrap) {
		bootstrap.setName("library-service");
		bootstrap.addBundle(new ViewBundle());
		bootstrap.addBundle(new AssetsBundle());
	}

	@Override
	public void run(LibraryServiceConfiguration configuration,
			Environment environment) throws Exception {
		// This is how you pull the configurations from library_x_config.yml

		LibraryConfig.setLibraryConfig(configuration);
		System.out.println("Connection with queue has been set up");

		/** Root API */
		environment.addResource(RootResource.class);
		/** Books APIs */
		final BookRepositoryInterface bookRepository = new BookRepository();
		environment.addResource(new BookResource(bookRepository));

		/** UI Resources */
		environment.addResource(new HomeResource(bookRepository));

		int numThreads = 1;
		ExecutorService executor = Executors.newFixedThreadPool(numThreads);

		Runnable backgroundTask = new Runnable() {

			@Override
			public void run() {
				System.out.println("Running the subscriber task");
				// call listener
				SubscribeMessages listener = new SubscribeMessages();
				listener.ListenMessages(bookRepository);
			}
		};

		executor.execute(backgroundTask);
		System.out.println("Submitted the background task");
	}

}
