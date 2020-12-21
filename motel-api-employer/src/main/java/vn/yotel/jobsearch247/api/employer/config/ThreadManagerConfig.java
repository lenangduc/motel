package vn.yotel.jobsearch247.api.employer.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import vn.yotel.thread.ThreadManager;

import java.util.List;

/**
 * 
 */
@Configuration
@Profile("JobSearch")
public class ThreadManagerConfig {
	
	@Value("#{'${threadmanager.configfiles}'.split(',')}") 
    private List<String> threadConfigFiles;
	
	@Value("${threadmanager.server_mode}")
	private String serverMode;
	
	@Bean(name = "threadManager", initMethod = "startManager", destroyMethod = "stopManager")
	@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
	@Primary
	public ThreadManager threadManager() {
		ThreadManager threadManager = new ThreadManager(threadConfigFiles);
		threadManager.setServerMode(serverMode);
		return threadManager;
	}
}
