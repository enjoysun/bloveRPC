package Blove.Exception;

/*
 * @Time    : 2019/7/2 5:17 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : InvokeModuleException.java
 * @Software: IntelliJ IDEA
 */
public class InvokeModuleException extends RuntimeException {
    /**
     * @Author myou<myoueva@gmail.com>
     * @Description //自定义错误处理句柄(反射调用错误)
     * @Date 5:18 PM 2019/7/2
     * @Param []
     * @return
     **/
    public InvokeModuleException() {
        super();
    }

    public InvokeModuleException(String message) {
        super(message);
    }

    public InvokeModuleException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvokeModuleException(Throwable cause) {
        super(cause);
    }

    protected InvokeModuleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
