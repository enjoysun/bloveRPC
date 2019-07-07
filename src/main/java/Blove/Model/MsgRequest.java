package Blove.Model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Arrays;

/*
 * @Time    : 2019/6/28 4:13 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MsgRequest.java
 * @Software: IntelliJ IDEA
 */
public class MsgRequest {

    /**
     * @Author myou<myoueva@gmail.com>
     * @Description netty通信服务端接收信息结构体
     * @Date 4:19 PM 2019/6/28
     * @Param
     * @return
     **/
    private MsgHeader header;
    private String messageId;

    @Override
    public String toString() {
        /**
         * @Author myou<myoueva@gmail.com>
         * @Description ReflectionToStringBuilder为apache提供的重写to string方法，本案例中希望排除string数组内的元素属性
         * @Date 4:28 PM 2019/6/28
         * @Param []
         * @return java.lang.String
         **/
        return ReflectionToStringBuilder.toStringExclude(this, new String[]{"typeParameters", "valParameters"});
    }

    private String className;
    private String methodName;
    private Class<?>[] typeParameters;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getTypeParamaters() {
        return typeParameters;
    }

    public void setTypeParamaters(Class<?>[] typeParamaters) {
        this.typeParameters = typeParamaters;
    }

    public Object[] getValParameters() {
        return valParameters;
    }

    public void setValParameters(Object[] valParameters) {
        this.valParameters = valParameters;
    }

    private Object[] valParameters;
}
