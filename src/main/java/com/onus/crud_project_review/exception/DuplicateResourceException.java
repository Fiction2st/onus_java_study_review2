package com.onus.crud_project_review.exception;

public class DuplicateResourceException extends RuntimeException {
  public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
    super(String.format("%s already exists with %s: '%s'", resourceName, fieldName, fieldValue));
  }
}