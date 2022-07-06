package com.itheima.reggie.common;

/** User save and get current login user id
 * @author Diyang Li
 * @create 2022-07-06 12:14 PM
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    /**
     * Sets the current thread's copy of this thread-local variable to the specified value.
     * @param id
     */
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }

    /**
     * Returns the current thread's "initial value" for this thread-local variable.
     * @return
     */
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
