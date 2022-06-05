package pl.coderslab.finalproj.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

// Tworzymy klasę startową aplikacji, która implementuje interfejs WebApplicationInitializer
public class AppInitializer implements WebApplicationInitializer {

  @Override
  public void onStartup(ServletContext servletContext) throws ServletException {
    // Load Spring web application configuration

    // Tworzymy kontekst aplikacji.
    AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
    // Rejestrujemy klasę konfiguracji.
    context.register(AppConfig.class);
    // Ustawiamy kontekst servletów.
    context.setServletContext(servletContext);

    // Create and register the DispatcherServlet
    // Tworzymy i ustawiamy DispatcherServlet.
    DispatcherServlet servlet = new DispatcherServlet(context);
    ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("app", servlet);
    servletRegistration.setLoadOnStartup(1);
    // Tworzymy i ustawiamy DispatcherServlet .
    //servletRegistration.addMapping("/app/*");
    servletRegistration.addMapping("/");

    // Definicja filtra odpowiedzialnego za ustawienie prawidłowego kodowania.
    FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("encodingFilter",
      new CharacterEncodingFilter());
    filterRegistration.setInitParameter("encoding", "UTF-8");
    filterRegistration.setInitParameter("forceEncoding", "true");
    filterRegistration.addMappingForUrlPatterns(null, true, "/*");
  }
}