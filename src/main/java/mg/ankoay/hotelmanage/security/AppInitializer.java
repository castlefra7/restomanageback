/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.ankoay.hotelmanage.security;

import javax.servlet.ServletContext;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import mg.ankoay.hotelmanage.SecurityConfiguration;

/**
 *
 * @author lacha
 */
public class AppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext sc) {

        AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.register(SecurityConfiguration.class);

        sc.addListener(new ContextLoaderListener(root));

        sc.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"))
          .addMappingForUrlPatterns(null, false, "/*");
    }

}
