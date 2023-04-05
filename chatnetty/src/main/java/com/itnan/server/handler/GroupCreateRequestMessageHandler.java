package com.itnan.server.handler;

import com.itnan.message.GroupChatRequestMessage;
import com.itnan.message.GroupCreateRequestMessage;
import com.itnan.message.GroupCreateResponseMessage;
import com.itnan.server.session.Group;
import com.itnan.server.session.GroupSession;
import com.itnan.server.session.GroupSessionFactory;
import com.itnan.server.session.SessionFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.List;
import java.util.Set;
@ChannelHandler.Sharable
public class GroupCreateRequestMessageHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GroupCreateRequestMessage groupCreateRequestMessage) throws Exception {

        String groupName = groupCreateRequestMessage.getGroupName();
        Set<String> members = groupCreateRequestMessage.getMembers();

        GroupSession groupSession = GroupSessionFactory.getGroupSession();
        Group group = groupSession.createGroup(groupName, members);
        if(group ==null){
            // 发生成功消息
            channelHandlerContext.writeAndFlush(new GroupCreateResponseMessage(true, groupName + "创建成功"));
            // 发送拉群消息
            List<Channel> channels = groupSession.getMembersChannel(groupName);
            for (Channel channel : channels) {
                channel.writeAndFlush(new GroupCreateResponseMessage(true, "您已经被拉入群聊"+groupName));
            }
        }else {
            channelHandlerContext.writeAndFlush(new GroupCreateResponseMessage(false, "群聊已经创建"+groupName));
        }
    }
}
