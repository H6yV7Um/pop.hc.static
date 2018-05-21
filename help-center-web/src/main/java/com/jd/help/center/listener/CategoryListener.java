package com.jd.help.center.listener;

import com.jd.help.center.domain.constants.UMPConstants;
import com.jd.help.center.manager.category.LocalHelpCategoryManager;
import com.jd.pop.limit.registry.ServiceRegistry;
import com.jd.ump.profiler.proxy.Profiler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by IntelliJ IDEA.
 * User: hanshichao
 * Date: 2010-11-30
 * Time: 19:05:32
 * To change this template use File | Settings | File Templates.
 */
public class CategoryListener implements ServletContextListener {

	private static final Log log = LogFactory.getLog(CategoryListener.class);

	private static final String LOCAL_CATEGORY_NAME = "localHelpCategoryManager";

	public void contextInitialized(ServletContextEvent servletContextEvent) {
		ServletContext servletContext = servletContextEvent.getServletContext();

		try {
			WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);

			// 初始化类目
			LocalHelpCategoryManager localHelpCategoryManager = (LocalHelpCategoryManager) wac.getBean(LOCAL_CATEGORY_NAME);
			localHelpCategoryManager.initLocalHelpCategory();

			Profiler.InitHeartBeats(UMPConstants.UMP_HELP_BEGIN);
			Profiler.registerJVMInfo(UMPConstants.UMP_KEY_POP_SYN_WARE_JVM);
			ServiceRegistry.registry(); //触发limit
		} catch (Exception e) {
			log.error("初始化类目失败----->", e);
		}

	}

	public void contextDestroyed(ServletContextEvent sce) {
		//To change body of implemented methods use File | Settings | File Templates.
	}
}
