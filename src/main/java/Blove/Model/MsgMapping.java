package Blove.Model;

import java.io.Serializable;
import java.util.Map;

/*
 * @Time    : 2019/6/28 4:33 PM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MsgMapping.java
 * @Software: IntelliJ IDEA
 */
public class MsgMapping implements Serializable {
    /**
     * @Author myou<myoueva@gmail.com>
     * @Description rpc服务方法映射容器(接口定义、服务接口实现类绑定关系的容器。提供给spring作为容器使用)
     * 参考AbstractOwnableSynchronizer设计
     * @Date 4:33 PM 2019/6/28
     * @Param
     * @return
     **/
    private transient Map<String, Object> messageMapping;

    protected final void setMessageMapping(Map<String, Object> map){
        messageMapping = map;
    }

    protected final Map<String, Object> getMessageMapping(){
        return messageMapping;
    }
}
