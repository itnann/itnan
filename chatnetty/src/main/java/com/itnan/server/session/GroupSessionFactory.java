package com.itnan.server.session;

public class GroupSessionFactory {
    private static GroupSession groupSession = new GroupSessionMemoryImpl();

    public static GroupSession getGroupSession() {
        return groupSession;
    }
}
