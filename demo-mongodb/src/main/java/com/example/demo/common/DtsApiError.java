package com.example.demo.common;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonTypeIdResolver;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.CUSTOM, property = "error", visible = true)
@JsonTypeIdResolver(LowerCaseClassNameResolver.class)
public class DtsApiError {

//    private String debugMessage;
    private String description;
    private String field;

//    private List<ApiSubError> subErrors;

    public DtsApiError() {

    }

    DtsApiError(Throwable ex) {
        this();
        this.description = "Unexpected error";
//        this.debugMessage = ex.getLocalizedMessage();
    }

    DtsApiError(String message, Throwable ex) {
        this();
        this.description = message;
//        this.debugMessage = ex.getLocalizedMessage();
    }

    DtsApiError(String message, String field, Throwable ex) {
        this();
        this.description = message;
//        this.debugMessage = ex.getLocalizedMessage();
        this.field = field;
    }

//    private void addSubError(ApiSubError subError) {
//        if (subErrors == null) {
//            subErrors = new ArrayList<>();
//        }
//        subErrors.add(subError);
//    }
//
//    public void addValidationError(String object, String field, Object rejectedValue, String message) {
//        addSubError(new ApiValidationError(object, field, rejectedValue, message));
//    }
//
//    private void addValidationError(String object, String message) {
//        addSubError(new ApiValidationError(object, message));
//    }
//
//    private void addValidationError(FieldError fieldError) {
//        this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(),
//                fieldError.getDefaultMessage());
//    }
//
//    void addValidationErrors(List<FieldError> fieldErrors) {
//        fieldErrors.forEach(this::addValidationError);
//    }
//
//    private void addValidationError(ObjectError objectError) {
//        this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
////    }
//
//    void addValidationError(List<ObjectError> globalErrors) {
//        globalErrors.forEach(this::addValidationError);
//    }
//
//    /**
//     * Utility method for adding error of ConstraintViolation. Usually when
//     * a @Validated validation fails.
//     *
//     * @param cv the ConstraintViolation
//     */
//    private void addValidationError(ConstraintViolation<?> cv) {
//        this.addValidationError(cv.getRootBeanClass().getSimpleName(),
//                ((PathImpl) cv.getPropertyPath()).getLeafNode().asString(), cv.getInvalidValue(), cv.getMessage());
//    }
//
//    void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
//        constraintViolations.forEach(this::addValidationError);
//    }

    abstract class ApiSubError {

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    @AllArgsConstructor
    public class ApiValidationError extends ApiSubError {
        private String object;
        private String field;
        private Object rejectedValue;
        private String message;

        ApiValidationError(String object, String message) {
            this.object = object;
            this.message = message;
        }
    }
}

class LowerCaseClassNameResolver extends TypeIdResolverBase {

    @Override
    public String idFromValue(Object value) {
        return value.getClass().getSimpleName().toLowerCase();
    }

    @Override
    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        return idFromValue(value);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return JsonTypeInfo.Id.CUSTOM;
    }
}