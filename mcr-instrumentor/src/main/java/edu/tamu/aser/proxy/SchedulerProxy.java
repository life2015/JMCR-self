package edu.tamu.aser.proxy;

import java.lang.reflect.Method;

public class SchedulerProxy {

    static {
        try {
            Class clazz = Class.forName("edu.tamu.aser.reex.Scheduler");
            Method method = clazz.getDeclaredMethod("beforeFieldAccess", Boolean.TYPE, String.class, String.class, String.class);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }

    /**
     * Called before a field is accessed, it first needs to get the lock
     * it is instrumented to the class
     * @param isRead
     * @param owner
     * @param name
     * @param desc
     */
    public static void beforeFieldAccess(boolean isRead, String owner, String name, String desc) {
        System.out.println("Schedule Proxy beforeFieldAccess" + " isRead: " + isRead + " Owner: " + owner + " name: " + name + " desc" + desc);
    }

    public static void beforeArrayAccess(boolean isRead) {

    }


    /**
     * Executed after a field is accessed.
     *
     * @param isRead
     *            whether the access is going to be a read
     * @param owner
     *            the class to which the field belongs
     * @param name
     *            the name of the field
     * @param desc
     *            the description of the field
     */
    public static void afterFieldAccess(boolean isRead, String owner, String name, String desc) {
        System.out.println("Schedule Proxy AfterFieldAccess" + "isRead: " + isRead + "Owner: " + owner + "name: " + name + "desc" + desc);
    }

    public static void afterArrayAccess(boolean isRead) {

    }
}
