package edu.tamu.aser.proxy;

public class RVRunTimeProxy {
    //Considering that after a read/write operation
    //we can update the store in other threads
    //this will make a mistake
    public static void updateFieldAcc(int ID, final Object o, int SID,
                                      final Object v, final boolean write, long tid) {
    }

    /**
     * @param ID
     * @param o
     * @param SID
     * @param v
     * @param write
     */
    public static void logFieldAcc(int ID, final Object o, int SID, final Object v, final boolean write) {
    }

    public static void logInitialWrite(int ID, final Object o, int SID, final Object v)
    {}

    public static void logBranch(int ID) {
    }

    public static void logThreadBegin()
    {


    }
    public static void logThreadEnd()
    {

    }


    /**
     * When starting a new thread, a consistent unique identifier of the thread
     * is created, and stored into a map with the thread id as the key. The
     * unique identifier, i.e, name, is a concatenation of the name of the
     * parent thread with the order of children threads forked by the parent
     * thread.
     *
     * @param ID
     * @param o
     */
    public static void logBeforeStart(int ID, final Object o) {

    }

    public static void logAfterStart(int ID, final Object o) {

    }

    public static void logSleep()
    {

    }
    public static void logJoin(int ID, final Object o) {

    }

    //the original version
    public static void logWait(int ID, final Object o) {

    }

    public static void logNotify(int ID, final Object o) {

    }

    public static void logNotifyAll(int ID, final Object o) {

    }

    public static void logStaticSyncLock(int ID, int SID) {
    }


}
