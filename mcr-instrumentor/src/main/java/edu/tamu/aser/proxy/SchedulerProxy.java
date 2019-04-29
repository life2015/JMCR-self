package edu.tamu.aser.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SchedulerProxy {

    public static Method beforeFieldAccessMethod;

    static {
        try {
            Class clazz = Class.forName("edu.tamu.aser.reex.Scheduler");
            beforeFieldAccessMethod = clazz.getDeclaredMethod("beforeFieldAccess", Boolean.TYPE, String.class, String.class, String.class, Object.class, Boolean.TYPE);
            Method method2 = clazz.getDeclaredMethod("beforeArrayAccess", Boolean.TYPE);
            Method method3 = clazz.getDeclaredMethod("afterFieldAccess", Boolean.TYPE, String.class, String.class, String.class);
            Method method4 = clazz.getDeclaredMethod("afterArrayAccess", Boolean.TYPE);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    /**
     * Called before a field is accessed, it first needs to get the lock
     * it is instrumented to the class
     *
     * @param isRead
     * @param owner
     * @param name
     * @param desc
     */
    public static void beforeFieldAccess(boolean isRead, String owner, String name, String desc, Object value) {
        System.out.println("Schedule Proxy beforeFieldAccess" + " isRead: " + isRead + " Owner: " + owner + " name: " + name + " desc: " + desc + "value: " + value);
        System.out.println("Current Thread" + Thread.currentThread().getName());
//        Thread.dumpStack();
        System.out.println("\n");

        try {
            beforeFieldAccessMethod.invoke(null, isRead, owner, name, desc, value, /*pause*/ true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // test method for dump value
    public static void logValue(String desc, Object value) {
        System.out.println("Desc -> " + desc + "Value -> " + value);
    }

    public static void beforeArrayAccess(boolean isRead) {

    }


    /**
     * Executed after a field is accessed.
     *
     * @param isRead whether the access is going to be a read
     * @param owner  the class to which the field belongs
     * @param name   the name of the field
     * @param desc   the description of the field
     */
    public static void afterFieldAccess(boolean isRead, String owner, String name, String desc) {
        System.out.println("Schedule Proxy AfterFieldAccess" + "isRead: " + isRead + "Owner: " + owner + "name: " + name + "desc" + desc);
    }

    public static void afterArrayAccess(boolean isRead) {

    }
}
