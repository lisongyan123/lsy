package com.example.lsy.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

public class Netty {
    public static void main(String[] args) {

        //定义主线程组，用于接收客户端请求，但是并不做任何逻辑处理
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //从线程组，主线程组会把相应的请求转交给该线程组，由从线程组去做任务
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //创建netty服务器
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            //服务器设置绑定两个线程组，并设置相应的助手类【handler】
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)              //设置nio双向通道
                    .childHandler(new HelloServerInitializer());        //子处理器

            //启动server并绑定端口号
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            //关闭监听的channel
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    static class MyHandler extends SimpleChannelInboundHandler<HttpObject> {

        @Override
        protected void messageReceived(ChannelHandlerContext context, HttpObject msg)
                throws Exception {

            //通过context的上下文获取channel
            Channel channel = context.channel();

            //获取请求地址
            if (msg instanceof HttpRequest) System.out.println(channel.remoteAddress());

            //自定义相应客户端信息
            ByteBuf content = Unpooled.copiedBuffer("hello neyy <<<>>>", CharsetUtil.UTF_8);

            //侯建httpResponse对象
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK,
                    content);

            //设置response对象的头信息
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().setInt(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            //将数据刷到客户端
            context.writeAndFlush(response);

        }

    }

    static class HelloServerInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel channel) throws Exception {

            //channel获取相应的管道
            ChannelPipeline pipeline = channel.pipeline();

            //为管道增加相应的handler，可理解为是拦截器，或者监听器，监听客户端建立连接后的信息
            //当请求到服务端，我们需要对写出到客户端的数据做编码处理
            pipeline.addLast("httpCode", new HttpServerCodec());

            //添加自定义助手类，可添加多个
            pipeline.addLast("myHandler", new MyHandler());

        }

    }
}




