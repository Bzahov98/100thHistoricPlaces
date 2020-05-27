package com.pmu.api.context;

import java.util.Objects;

public class ContextHolder {

    private static final ThreadLocal<Context> THREADLOCAL = new InheritableThreadLocal<>();

    public static Context get() {
        return Objects.isNull(THREADLOCAL.get()) ? Context.builder().build() : THREADLOCAL.get();
    }

    public static void set(Context context) {
        THREADLOCAL.set(context);
    }

    public static void clear() {
        THREADLOCAL.remove();
    }
}
