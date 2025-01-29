package com.thoughtworks.kinds.api;

import java.io.Serializable;
import java.util.List;
import com.thoughtworks.kinds.handlers.base.KindPlaceholder;

/**
 * DefaultSpecification
 */
public interface DefaultSpecification extends Serializable {

    public List<String> getFiles();

    public List<KindPlaceholder> getPlaceholders();
}
