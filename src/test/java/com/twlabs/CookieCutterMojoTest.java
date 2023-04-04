package com.twlabs;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.apache.maven.plugin.testing.MojoRule;
import org.junit.Rule;
import org.junit.Test;

public class CookieCutterMojoTest {

    private static final String HELLO_WORLD_PJ = "src/test/resources/spring-hello-world/pom.xml";
    @Rule
    public MojoRule rule = new MojoRule()
    {
      @Override
      protected void before() throws Throwable 
      {
      }
 
      @Override
      protected void after()
      {
      }
    };
    

    @Test
    public void test_method() throws Exception {
        CookieCutterMojo mojo = (CookieCutterMojo) rule.lookupMojo( "micci", HELLO_WORLD_PJ );
        assertNotNull( mojo );
        mojo.execute();
        
    }
}
