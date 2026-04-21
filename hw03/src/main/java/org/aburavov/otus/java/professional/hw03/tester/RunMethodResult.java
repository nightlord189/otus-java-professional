package org.aburavov.otus.java.professional.hw03.tester;

class RunMethodResult {
    String methodName;
    boolean success;
    Throwable exception;

    public RunMethodResult(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }
}
