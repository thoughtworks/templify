package com.twlabs.kinds.handlers;

import com.twlabs.kinds.api.KindMappingTemplate;

/**
 * Convert StepsKindTemplate to specific kind model executor example: StepKindTemplate ->
 *
 * FileHandlerKindModel <T> is for generic implementations: FileHandlerKindModelFactory implements
 *
 * KindModelFactory<FileHandlerKindModel>
 */
public interface KindModelFactory<T> {

    public T build(KindMappingTemplate template);

}
