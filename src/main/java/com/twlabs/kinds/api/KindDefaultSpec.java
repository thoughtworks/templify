package com.twlabs.kinds.api;

import com.twlabs.kinds.api.KindDefaultSpec;
import java.io.Serializable;
import java.util.List;

/**
 * KindDefaultSpec
 */
public class KindDefaultSpec implements Serializable {

    private List<String> files;
    private List<KindPlaceholder> placeholders;

    public List<String> getFiles() {
        return files;
    }
    public List<KindPlaceholder> getPlaceholders() {
        return placeholders;
    }
}
