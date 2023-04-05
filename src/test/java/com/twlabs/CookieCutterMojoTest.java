package com.twlabs;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.MojoRule;
import org.codehaus.plexus.PlexusTestCase;

public class CookieCutterMojoTest extends AbstractMojoTestCase {

    private static final String HELLO_WORLD_PJ = "/src/test/resources/spring-hello-world/pom.xml";

    protected void setUp() throws Exception {
        // required
        super.setUp();

    }

    /** {@inheritDoc} */
    protected void tearDown() throws Exception {
        // required
        super.tearDown();

    }

    public void test_method() throws Exception {
        CookieCutterMojo mojo = (CookieCutterMojo) this.lookupMojo("micci",
                PlexusTestCase.getBasedir() + HELLO_WORLD_PJ);
        assertNotNull(mojo);
        mojo.execute();

    }
}
