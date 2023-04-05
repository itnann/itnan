package com.itnan.server.handler;

import com.itnan.message.GroupChatRequestMessage;
import com.itnan.message.GroupChatResponseMessage;
import com.itnan.server.session.GroupSession;
import com.itnan.server.session.GroupSessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Set;
@ChannelHandler.Sharable
public class GroupChatRequestMessageHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupChatRequestMessage groupChatRequestMessage) throws Exception {
        String from = groupChatRequestMessage.getFrom();
        String groupName = groupChatRequestMessage.getGroupName();
        String content = groupChatRequestMessage.getContent();
        GroupSession groupSession = GroupSessionFactory.getGroupSession();

        List<Channel> membersChannels = groupSession.getMembersChannel(groupName);
        for (Channel channel : membersChannels) {
            channel.writeAndFlush(new GroupChatResponseMessage(from, content));
        }

    }
}
