package com.twlabs.model;

import java.util.List;

public class StepsKindTemplate {

    String kind;

    Metadata metadata = new Metadata();

    class Placeholder {
        String match, replace;
    }

    class Spec {

        List<String> files;
        List<Placeholder> placeholders;

    }
}
