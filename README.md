# appengine-spring-boot

Sample Google App Engine (standard) application using :

 * Java 8 : requires to be among alpha testers of the Java 8 standard runtime
 * Spring Boot 1.5 : the application is packaged as an executable WAR also deployable on servlet containers
 * JSP : just to prove it works, you should probably use another template engine like thymeleaf
 * GWT 2.8.0 : **TODO Just to prove it's still there**

## How to deploy

To deploy on App Engine, run `mvn appengine:update -Dappengine.app.id=your_appengine_application_id`.  
You can also add `-Dappengine.app.version=X` to override the default version (1).

If you only have one environment, you can set these properties directly in `pom.xml` or `appengine-web.xml`.

## What's in there

The home page is dull, it just proves Java 8 + Spring Boot + JSPs work.

You can also hit `/aliens` to see a dumb HTTP API example.

You can also curl the hell out of the actuator endpoints :

 * Health : `curl -i "https://your_appengine_application_id.appspot.com/health`
 * Sensitive endpoints (credentials in `application.yml`) : `curl -i "https://your_appengine_application_id.appspot.com/env --user "administrator:M4rSuP1aL-EsTh3T1qUE"` 

## Notes / known issues

 * Can't launch the app locally using `mvn appengine:devserver` because the maven plugin has unresolved issues with the Java 8 runtime (should be fixed by next version)
 * The Spring Context is initialized by declaring Spring MVC's `DispatcherServlet` in `web.xml`. This should not be necessary because the Spring Boot application class extends `SpringBootServletInitializer`.
 * If you want to  add global security constraints using the App Engine user APIs, you still need a `web.xml`
 * Can't see much in logs when deployed on app engine, Spring Boot auto-configures JUL but in the end only some useless Jetty logs and exceptions can be seen in stackdriver... not sure it is the runtime's fault though, PR welcome
