//package com.project_study.my.for_my.netty;
//
//import io.netty.bootstrap.ServerBootstrap;
//import io.netty.channel.*;
//import io.netty.channel.nio.NioEventLoopGroup;
//import io.netty.channel.socket.nio.NioServerSocketChannel;
//import io.netty.handler.codec.http.HttpObjectAggregator;
//import io.netty.handler.codec.http.HttpServerCodec;
//import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
//import io.netty.handler.stream.ChunkedWriteHandler;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.DisposableBean;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.WebSocketHandler;
//
//import java.nio.channels.SocketChannel;
//
//
///**
// * @ClassName  Netty服务器配置
// * @Description: TODO
// * @Author zyl
// * @Date 2020/1/19
// * @Version V1.0
// **/
//@Slf4j
//@Component
//public class NettyServer implements InitializingBean, DisposableBean {
//
//    @Value("${websocket.port}")
//    private int serverPort;
//
//    private EventLoopGroup bossGroup;
//
//    private EventLoopGroup workerGroup;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        init();
//    }
//
//    public void init() throws Exception {
//        bossGroup = new NioEventLoopGroup();
//        workerGroup = new NioEventLoopGroup();
//        try {
//            ServerBootstrap sb = new ServerBootstrap();
//            sb.option(ChannelOption.SO_BACKLOG, 1024);
//            sb.group(workerGroup, bossGroup) // 绑定线程池
//                    .channel(NioServerSocketChannel.class) // 指定使用的channel
//                    .localAddress(this.serverPort)// 绑定监听端口
//                    .childHandler(new ChannelInitializer<Channel>() { // 绑定客户端连接时候触发操作
//                        protected void initChannel(Channel ch) throws Exception {
//                            System.out.println("收到新连接");
//                            //websocket协议本身是基于http协议的，所以这边也要使用http解编码器
//                            ch.pipeline().addLast(new HttpServerCodec());
//                            //以块的方式来写的处理器
//                            ch.pipeline().addLast(new ChunkedWriteHandler());
//                            ch.pipeline().addLast(new HttpObjectAggregator(8192));
//                            ch.pipeline().addLast(new WebSocketServerProtocolHandler("/ws", null, true, 65536 * 10));
//                            ch.pipeline().addLast(new WebSocketServerHandler());
//                        }
//                    });
//            ChannelFuture cf = sb.bind().sync(); // 服务器异步创建绑定
//            System.out.println(NettyServer.class + " 启动正在监听： " + cf.channel().localAddress());
//            cf.channel().closeFuture().sync(); // 关闭服务器通道
//        } finally {
//            bossGroup.shutdownGracefully().sync(); // 释放线程池资源
//            bossGroup.shutdownGracefully().sync();
//        }
//    }
//
//    @Override
//    public void destroy() throws Exception {
//        bossGroup.shutdownGracefully().sync(); // 释放线程池资源
//        bossGroup.shutdownGracefully().sync();
//    }
//
//
//}
//
