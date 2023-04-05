package com.itnan.server;

import com.itnan.protocol.MessageCodecSharable;
import com.itnan.protocol.ProtocolFrameDecoder;
import com.itnan.server.handler.ChatRequestMessageHandler;
import com.itnan.server.handler.GroupChatRequestMessageHandler;
import com.itnan.server.handler.GroupCreateRequestMessageHandler;
import com.itnan.server.handler.LoginRequestMessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatServer {
    public static void main(String[] args) {
        NioEventLoopGroup boss=new NioEventLoopGroup();
        NioEventLoopGroup worker=new NioEventLoopGroup();
        LoggingHandler LOGGER_HANDLER=new LoggingHandler(LogLevel.DEBUG);
        MessageCodecSharable MESSAGE_CODEC = new MessageCodecSharable();
        LoginRequestMessageHandler LOGIN_HANDLER = new LoginRequestMessageHandler();
        ChatRequestMessageHandler CHAT_HANDLER = new ChatRequestMessageHandler();
        GroupCreateRequestMessageHandler GROUP_CREATE_HANDLER = new GroupCreateRequestMessageHandler();
        GroupChatRequestMessageHandler GROUP_CHAT_HANDLER = new GroupChatRequestMessageHandler();
        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.channel(NioServerSocketChannel.class);
            serverBootstrap.group(boss,worker);
            serverBootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                       socketChannel.pipeline().addLast(new ProtocolFrameDecoder());
                       socketChannel.pipeline().addLast(LOGGER_HANDLER);
                       socketChannel.pipeline().addLast(MESSAGE_CODEC);
                       socketChannel.pipeline().addLast(new IdleStateHandler(200,0,0));
                       socketChannel.pipeline().addLast(new ChannelDuplexHandler(){
                           @Override
                           public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                               IdleStateEvent event = (IdleStateEvent) evt;
                               if(event.state() == IdleState.READER_IDLE){
                                   log.debug("已经五秒没有读到数据啦");
                                   ctx.channel().close();
                               }
                           }
                       });

                       socketChannel.pipeline().addLast(LOGIN_HANDLER);
                       socketChannel.pipeline().addLast(CHAT_HANDLER);
                       socketChannel.pipeline().addLast(GROUP_CREATE_HANDLER);
                       socketChannel.pipeline().addLast(GROUP_CHAT_HANDLER);
                }
            });
            Channel channel = serverBootstrap.bind(8080).sync().channel();
            channel.closeFuture().sync();
        }catch (InterruptedException e) {
            log.error("server error", e);

        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

}
