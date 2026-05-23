package org.aburavov.otus.java.professional.hw03.tester;

public class RunStats {
    String className;
    long all;
    long success;
    long failed;

    public RunStats (String className, long all, long success, long failed) {
        this.className = className;
        this.all = all;
        this.success = success;
        this.failed = failed;
    }

    public long getFailed() {
        return failed;
    }

    void setFailed(long failed) {
        this.failed = failed;
    }

    public long getSuccess() {
        return success;
    }

    void setSuccess(long success) {
        this.success = success;
    }

    public long getAll() {
        return all;
    }

    @Override
    public String toString() {
        return "RunStats{" +
                "class='" + className + '\'' +
                ", all=" + all +
                ", success=" + success +
                ", failed=" + failed +
                '}';
    }
}
