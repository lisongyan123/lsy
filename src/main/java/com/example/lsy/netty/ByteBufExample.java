package com.example.lsy.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ByteBufExample {

    public static void main(String[] args) {
        // 非池化，使用完后销毁
        ByteBuf byteBuf = Unpooled.buffer(10);

        // 写入数据
        for (int i = 10; i < 20; i++) byteBuf.writeByte(i);

        // 读取数据
        while (byteBuf.isReadable()) System.out.println(byteBuf.readByte());

    }
}
