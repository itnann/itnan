package com.itnan.server.session;

import io.netty.channel.Channel;

/**
 * 会话管理接口
 *
 * @author itnan
 * @date 2023/03/28
 */
public interface Session {

    /**
     * 绑定会话
     *
     * @param channel  哪个channel,要绑定会话
     * @param username 会话绑定的用户名
     */
    void bind(Channel channel,String username);

    /**
     * 解绑会话
     *
     * @param channel 哪个channel要解绑会话
     */
    void unbind(Channel channel);

    /**
     * 获取属性
     *
     * @param channel 哪个channel
     * @param name   属性名
     * @return {@link Object}属性值
     */
    Object getAttribute(Channel channel,String name);

    /**
     * 设置属性
     *
     * @param channel 哪个channel
     * @param name    属性名
     * @param value   属性值
     */
    void setAttribute(Channel channel,String name,Object value);


    /**
     * 根据用户名获取channel
     *
     * @param username 用户名
     * @return {@link Channel}
     */
    Channel getChannel(String username);






}
