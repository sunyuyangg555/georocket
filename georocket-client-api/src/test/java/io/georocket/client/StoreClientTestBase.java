package io.georocket.client;

import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;

import org.junit.Before;
import org.junit.Rule;

import com.github.tomakehurst.wiremock.client.VerificationException;
import com.github.tomakehurst.wiremock.junit.WireMockRule;

import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;


/**
 * Base class for unit tests that test the {@link StoreClient}
 * @author Michel Kraemer
 */
public abstract class StoreClientTestBase {
  private static final int PORT = 12345;
  
  /**
   * Run the test on a Vert.x test context
   */
  @Rule
  public RunTestOnContext rule = new RunTestOnContext();
  
  /**
   * Run a mock HTTP server
   */
  @Rule
  public WireMockRule wireMockRule = new WireMockRule(PORT);
  
  /**
   * The client under test
   */
  protected GeoRocketClient client;
  
  /**
   * Set up test
   * @throws Exception if something goes wrong
   */
  @Before
  public void setUp() throws Exception {
    client = new GeoRocketClient("localhost", PORT, rule.vertx());
  }
  
  /**
   * Verify that a certain request has been made
   * @param url the request URL
   * @param context the current test context
   */
  protected void verifyRequested(String url, TestContext context) {
    try {
      verify(getRequestedFor(urlEqualTo(url)));
    } catch (VerificationException e) {
      context.fail(e);
    }
  }
}
