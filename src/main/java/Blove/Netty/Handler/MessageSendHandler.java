package Blove.Netty.Handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/*
 * @Time    : 2019/7/2 11:48 AM
 * @Author  : YouMing
 * @Email   : myoueva@gmail.com
 * @File    : MessageSendHandler.java
 * @Software: IntelliJ IDEA
 */
public class MessageSendHandler extends ChannelHandlerAdapter {
    /**
     * @Author myou<myoueva@gmail.com>
     * @Description //ChannelHandlerAdapter
     * ChannelHandlerAdapter实现了ChannelHandler接口，封装了Channel的事件处理方法(接收消息，处理异常等等)可选择重写来定制自己的Handler
     * 事件类型解析
     * channelInboundHandler:
     * channelRegistered:channel已经注册到eventLoop(bossGroup)上，可定制channel注册成功后执行逻辑
     * channelUnregistered:channel已经从eventLoop(bossGroup)注销，可定制channel下线注销后执行逻辑
     * channelActive:channel已经准备好了收发数据事件，定制数据的收发
     * channelInactive:已经注册的channel活跃性触发事件(当服务端发起的close和客户端的close都会触发改事件)，该事件触发即channel连接不可用，定制连接断开处理逻辑
     * channelRead:当前channel从远程读取到消息触发事件。可定制消息接收处理逻辑
     * channelReadComplete:当channel read消费完读取的io缓冲的数据时被触发，可定制一次完整的消息接收后触发逻辑
     * userEventTriggered:用户事件被触发时调用该事件
     * channelWritabilityChanged：一旦Channel的可写状态发生变化，就会调用它。 您可以使用Channel.isWritable()检查状态。
     * exceptionCaught：channelhandler处理异常会触发该事件
     * ChannelOutboundHandler:
     * bind:绑定操作执行前触发
     * connect:connect操作执行前被触发
     * disconnect:disconnect操作执行前被触发
     * close:close操作被执行前触发
     * deregister:ChannelHandler一但从当前注册的eventLoopGroup中取消注册即触发该事件
     * read：channelHandler.read()操作执行前被触发
     * write：一旦执行写操作就被触发。写操作将通过ChannelPipeline写入消息。一旦调用了Channel.flush()，就可以将它们刷新到实际的Channel
     * flush:一旦进行flush操作就调用。 flush操作将尝试将先前挂起的全部消息刷新到io缓存通道中。
     * @Date 11:05 AM 2019/7/4
     * @Param [ctx, cause]
     * @return void
     **/
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
    }
}
