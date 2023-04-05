package com.itnan.server.handler;

import com.itnan.message.ChatRequestMessage;
import com.itnan.message.ChatResponseMessage;
import com.itnan.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ChatRequestMessageHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ChatRequestMessage chatRequestMessage) throws Exception {
        String to = chatRequestMessage.getTo();
        String from = chatRequestMessage.getFrom();
        String content = chatRequestMessage.getContent();
        Channel channel = SessionFactory.getSession().getChannel(to);
        if(channel != null){
            channel.writeAndFlush(new ChatResponseMessage(from, content));
        }else{
            channel.writeAndFlush(new ChatResponseMessage(false,"该用户不在线"));
        }

    }
}
