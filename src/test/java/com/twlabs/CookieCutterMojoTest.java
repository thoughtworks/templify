package com.twlabs;

import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.MojoRule;
import org.codehaus.plexus.PlexusTestCase;

public class CookieCutterMojoTest extends AbstractMojoTestCase {

    private static final String HELLO_WORLD_PJ = "src/test/resources/spring-hello-world/pom.xml";
    private static final String EMPTY = "src/test/resources/no-project/pom.xml";

    protected void setUp() throws Exception {
        // required
        super.setUp();

    }

    /** {@inheritDoc} */
    protected void tearDown() throws Exception {
        // required
        super.tearDown();

    }

    public void test_mojo() throws Exception {
        CookieCutterMojo mojo = (CookieCutterMojo) this.lookupMojo("micci", HELLO_WORLD_PJ);
        assertNotNull(mojo);
        mojo.execute();

    }

    public void test_empty_mojo() throws Exception {
        CookieCutterMojo mojo = (CookieCutterMojo) this.lookupEmptyMojo("micci", EMPTY);
        assertNotNull(mojo);
        mojo.execute();

    }
}
