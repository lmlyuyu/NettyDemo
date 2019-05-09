package com.maolin.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * 引导服务器类
 */
public class EchoClient {
    private final int port;

    private EchoClient(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws Exception {
        int port = args.length > 0 ? Integer.parseInt(args[0]) : 9091;
        new EchoClient(port).start();
    }

    private void start() throws Exception {
        //创建EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //创建ServerBootstrap
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    //指定NIO传输Channel
                    .channel(NioSocketChannel.class)
                    //用指定端口设置套接字地址
                    .remoteAddress(new InetSocketAddress("localhost", port))
                    //添加一个EchoServerHandler到子Channel的ChannelPipeline
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new EchoClientHandler());
                        }
                    });

            //异步绑定服务器，同步阻塞直到绑定完成
            ChannelFuture channelFuture = bootstrap.connect().sync();
            //获取ChannelCloseFuture阻塞到当前线程完成
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭EventLoopGroup并释放所有资源
            group.shutdownGracefully().sync();
        }
    }
}
