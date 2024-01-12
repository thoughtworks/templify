package com.twlabs.kinds.api;

import java.io.Serializable;
import java.util.List;
import com.twlabs.kinds.handlers.base.KindPlaceholder;

/**
 * DefaultSpecification
 */
public interface DefaultSpecification extends Serializable {

    public List<String> getFiles();

    public List<KindPlaceholder> getPlaceholders();
}
