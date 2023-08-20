package com.twlabs.kinds;

import com.twlabs.model.settings.StepsKindTemplate;

/**
 * Convert StepsKindTemplate to specific kind model executor example: StepKindTemplate ->
 *
 * FileHandlerKindModel <T> is for generic implementations: FileHandlerKindModelFactory implements
 *
 * KindModelFactory<FileHandlerKindModel>
 */
public interface KindModelFactory<T> {

    public T build(StepsKindTemplate template);

}
