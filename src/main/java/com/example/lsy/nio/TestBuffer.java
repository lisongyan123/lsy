package com.example.lsy.nio;

import java.nio.ByteBuffer;

/**
 * 视频1. 尚硅谷_NIO_NIO 与 IO 区别
 * NIO支持面向缓冲区的、基于通道的IO操作
 * IO								NIO
 * 面向流(Stream Oriented)			面向缓冲区(Buffer Oriented)
 * 阻塞IO(Blocking IO)				非阻塞IO(NonBlocking IO)
 * (无)								选择器(Selectors)
 * <p>
 * Channel 负责传输，Buffer 负责存储
 * <p>
 * 视频2. 尚硅谷_NIO_缓冲区(Buffer)的数据存取
 * 一、缓冲区（Buffer）：在 Java NIO 中负责数据的存取。缓冲区就是数组。用于存储不同数据类型的数据
 * <p>
 * 根据数据类型不同（boolean 除外），提供了相应类型的缓冲区：
 * ByteBuffer(最常用)
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 * <p>
 * 上述缓冲区的管理方式几乎一致，通过 allocate() 获取缓冲区
 * <p>
 * 二、缓冲区存取数据的两个核心方法：
 * put() : 存入数据到缓冲区中
 * get() : 获取缓冲区中的数据
 * <p>
 * 三、缓冲区中的四个核心属性：
 * capacity : 容量，表示缓冲区中最大存储数据的容量。一旦声明不能改变。
 * limit : 界限，表示缓冲区中可以操作数据的大小。（limit 后数据不能进行读写）
 * position : 位置，表示缓冲区中正在操作数据的位置。
 * <p>
 * mark : 标记，表示记录当前 position 的位置。可以通过 reset() 恢复到 mark 的位置
 * <p>
 * 0 <= mark <= position <= limit <= capacity
 * <p>
 * 视频4. 尚硅谷_NIO_直接缓冲区与非直接缓冲区
 * 四、直接缓冲区与非直接缓冲区：
 * 非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
 * 直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
 */
public class TestBuffer {


    public static void test3() {
        //分配直接缓冲区
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        // isDirect可以判断是否是直接缓冲区
        System.out.println(buf.isDirect());
    }


    public static void test2() {
        final String str = "abcde";

        ByteBuffer buf = ByteBuffer.allocate(1024);

        buf.put(str.getBytes());

        buf.flip();

        byte[] dst = new byte[buf.limit()];
        buf.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2));
        System.out.println(buf.position());

        //mark() : 标记
        buf.mark();

        buf.get(dst, 2, 2);
        System.out.println(new String(dst, 2, 2));
        System.out.println(buf.position());

        //reset() : 恢复到 mark 的位置
        buf.reset();
        System.out.println("reset位置: " + buf.position());

        //hasRemaining(): 判断缓冲区中是否还有剩余数据
        //获取缓冲区中可以操作的数量
        if (buf.hasRemaining()) System.out.println(buf.remaining());
    }

    // Buffer 的常用方法

    public static void test1() {
        final String str = "abcde";

        //1. 分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // 打印三个缓冲区的关键属性
        System.out.println("-----------------allocate()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //2. 利用 put() 存入数据到缓冲区中
        buf.put(str.getBytes());

        System.out.println("-----------------put()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit()); // 存入后limit会改变
        System.out.println(buf.capacity());

        //3. 切换读取数据模式
        buf.flip();

        System.out.println("-----------------flip()----------------");
        System.out.println(buf.position()); // 切换读后position会回到起始位置准备读取
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //4. 利用 get() 读取缓冲区中的数据
        // 创建一个byte数组, 长度以缓冲区的limit为准
        byte[] dst = new byte[buf.limit()];
        // get(byte[] dst) 有好几个重载方法, 这里是将缓冲区中的数据直接读到 dst 中
        buf.get(dst);
        System.out.println(new String(dst, 0, dst.length));

        System.out.println("-----------------get()----------------");
        System.out.println(buf.position());// 读取后position会来到limit的位置
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //5. rewind() : 可重复读, 读完position再回到起始位置
        buf.rewind();

        System.out.println("-----------------rewind()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //6. clear() : 清空缓冲区. 回到最初状态, 但是缓冲区中的数据依然存在，但是处于“被遗忘”状态, 只是等着被覆盖
        buf.clear();

        System.out.println("-----------------clear()----------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println((char) buf.get()); // 这里get一个字节, 仍然是有的

    }

}
