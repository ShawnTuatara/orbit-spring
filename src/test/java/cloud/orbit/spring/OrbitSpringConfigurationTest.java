package cloud.orbit.spring;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import cloud.orbit.actors.Stage;
import cloud.orbit.actors.extensions.ActorExtension;

/*
 * Very simple (poor) tests to validate that the SpringLifecyleExtension is being properly bound to the Stage
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ContextConfiguration(classes = OrbitSpringConfiguration.class)
public class OrbitSpringConfigurationTest {
  @Autowired
  private ApplicationContext applicationContext;

  @Test
  public void loadSpringLifecycleExtensions() throws Exception {
    Map<String, ActorExtension> actorExtensionBeans = applicationContext.getBeansOfType(ActorExtension.class);
    assertTrue(actorExtensionBeans.keySet().contains("springLifecycleExtension"));
  }

  @Test
  public void validateStageHasSpringLifecycleExtension() throws Exception {
    Stage stage = applicationContext.getBean(Stage.class);
    List<ActorExtension> actorExtensions = stage.getAllExtensions(ActorExtension.class);
    long count = actorExtensions.stream()
        .filter(actorExtension -> actorExtension.getClass().isAssignableFrom(SpringLifetimeExtension.class)).count();
    assertTrue(count == 1);
  }
}
