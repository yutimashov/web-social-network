package com.getjavajob.training.timashovy.socialnetwork.dao.interfaces;

/**
 * Interface for checking table constraints.
 */
public interface TableConstraintsValidator {

    <E> void validateEntityFieldUniqueness(String fieldName, E fieldValue);

}
