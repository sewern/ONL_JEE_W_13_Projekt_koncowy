package pl.coderslab.finalproj.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "pl.coderslab.finalproj")
//@ComponentScan(basePackages={"pl.coderslab.finalproj.controller", "pl.coderslab.finalproj.model", "pl.coderslab.finalproj.repository"})
@EnableTransactionManagement
public class AppConfig implements WebMvcConfigurer {

  @Bean
  public LocalEntityManagerFactoryBean entityManagerFactory() {
    LocalEntityManagerFactoryBean entityManagerFactoryBean
      = new LocalEntityManagerFactoryBean();
    entityManagerFactoryBean.setPersistenceUnitName("productionPersistenceUnit");
    return entityManagerFactoryBean;
  }

  @Bean
  public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    JpaTransactionManager jpaTransactionManager =
      new JpaTransactionManager(entityManagerFactory);
    return jpaTransactionManager;
  }

  /*@Bean
  public ViewResolver viewResolver() {
    InternalResourceViewResolver bean = new InternalResourceViewResolver();

    bean.setViewClass(JstlView.class);
    bean.setPrefix("/WEB-INF/view/");
    bean.setSuffix(".jsp");

    return bean;
  }*/

  @Override
  public void configureViewResolvers(ViewResolverRegistry registry) {
    //registry.jsp("/WEB-INF/view/", ".jsp");
    registry.jsp("/WEB-INF/", ".jsp");
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addViewController("/").setViewName("index");
  }
}