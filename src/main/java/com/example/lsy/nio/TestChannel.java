package com.example.lsy.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 视频5. 尚硅谷_NIO_通道(Channel)的原理与获取
 * 视频6. 尚硅谷_NIO_通道的数据传输与内存映射文件
 * 一、通道（Channel）：用于源节点与目标节点的连接。在 Java NIO 中负责缓冲区中数据的传输。
 * Channel 本身不存储数据，因此需要配合缓冲区进行传输。
 * <p>
 * 二、通道的主要实现类
 * java.nio.channels.Channel 接口：
 * |--FileChannel (本地)
 * |--SocketChannel (网络)
 * |--ServerSocketChannel (网络)
 * |--DatagramChannel (网络)
 * <p>
 * 三、获取通道
 * 1. Java 针对支持通道的类提供了 getChannel() 方法
 * 本地 IO：
 * FileInputStream/FileOutputStream
 * RandomAccessFile
 * <p>
 * 网络IO：
 * Socket
 * ServerSocket
 * DatagramSocket
 * <p>
 * 2. 在 JDK 1.7 中的 NIO.2 针对各个通道提供了静态方法 open()
 * 3. 在 JDK 1.7 中的 NIO.2 的 Files 工具类的 newByteChannel()
 * <p>
 * 四、通道之间的数据传输
 * transferFrom()
 * transferTo()
 * <p>
 * 视频7. 尚硅谷_NIO_分散读取与聚集写入
 * 五、分散(Scatter)与聚集(Gather)
 * 分散读取（Scattering Reads）：将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）：将多个缓冲区中的数据聚集到通道中
 * <p>
 * 视频8. 尚硅谷_NIO_字符集 Charset
 * 六、字符集：Charset
 * 编码：字符串 -> 字节数组
 * 解码：字节数组  -> 字符串
 */
public class TestChannel {

    // 视频8. 尚硅谷_NIO_字符集 Charset
    //字符集
    public static void test6() throws IOException {
        final Charset cs1 = Charset.forName("GBK");

        //获取编码器
        final CharsetEncoder ce = cs1.newEncoder();

        //获取解码器
        final CharsetDecoder cd = cs1.newDecoder();

        final CharBuffer cBuf = CharBuffer.allocate(1024);
        cBuf.put("尚硅谷威武！");
        cBuf.flip();

        //编码
        final ByteBuffer bBuf = ce.encode(cBuf);

        for (int i = 0; i < 12; i++) {
            System.out.println(bBuf.get());
        }

        //解码
        bBuf.flip();
        final CharBuffer cBuf2 = cd.decode(bBuf);
        System.out.println(cBuf2.toString());

        System.out.println("------------------------------------------------------");

        // 按GBK编码. 按UTF-8解码, 会乱码
        final Charset cs2 = Charset.forName("UTF-8");
        bBuf.flip();
        final CharBuffer cBuf3 = cs2.decode(bBuf);
        System.out.println(cBuf3.toString());
    }

    // 视频8. 尚硅谷_NIO_字符集 Charset
    public static void test5() {
        // 打印看看nio支持多少种字符集
        final Map<String, Charset> map = Charset.availableCharsets();

        final Set<Entry<String, Charset>> set = map.entrySet();

        for (final Entry<String, Charset> entry : set) {
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }

    // 视频7. 尚硅谷_NIO_分散读取与聚集写入
    // 分散和聚集, 以前是操作缓冲区, 这里是操作缓冲区数组
    public static void test4() throws IOException {
        final RandomAccessFile raf1 = new RandomAccessFile("1.txt", "rw");

        //1. 获取通道
        final FileChannel channel1 = raf1.getChannel();

        //2. 分配指定大小的缓冲区
        final ByteBuffer buf1 = ByteBuffer.allocate(100);
        final ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //3. 分散读取
        final ByteBuffer[] bufs = {buf1, buf2};
        channel1.read(bufs);

        for (final ByteBuffer byteBuffer : bufs) {
            byteBuffer.flip();
        }

        // 打印下看看是不是按顺序填满的
        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("-----------------");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        //4. 聚集写入
        final RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
        final FileChannel channel2 = raf2.getChannel();

        channel2.write(bufs);

        // 最后关闭通道的代码没写
    }

    // 视频6. 尚硅谷_NIO_通道的数据传输与内存映射文件
    // 通道之间的数据传输(直接缓冲区), 代码量超级少
    public static void test3() throws IOException {
        final FileChannel inChannel = FileChannel.open(Paths.get("d:/1.mkv"), StandardOpenOption.READ);
        final FileChannel outChannel = FileChannel.open(Paths.get("d:/2.mkv"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        // 这里transferTo和transferFrom的调用和传参正好是反的, 有点容易混
//		inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }

    // 视频6. 尚硅谷_NIO_通道的数据传输与内存映射文件
    // 使用直接缓冲区完成文件的复制(内存映射文件), 比非直接缓冲区的速度快, 但是可能存在垃圾回收没有及时释放, 导致内存占用过高
    public static void test2() throws IOException {//2127-1902-1777
        final long start = System.currentTimeMillis();

        // 这里用的open()获取通道, open()的参数是个可变参数
        final FileChannel inChannel = FileChannel.open(Paths.get("d:/1.mkv"), StandardOpenOption.READ);
        // StandardOpenOption.CREATE 表示目标文件存在就覆盖, 不存在就创建, 还有一个如果存在会报错, 另外通道要支持读写, 与下面outMappedBuf一致
        final FileChannel outChannel = FileChannel.open(Paths.get("d:/2.mkv"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);

        //内存映射文件(和allocateDirect()是一样的, 直接操作物理内存了)
        final MappedByteBuffer inMappedBuf = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
        final MappedByteBuffer outMappedBuf = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());

        //直接对缓冲区进行数据的读写操作, 因为直接对物理内存操作, 所以这里对通道的read和write直接省了
        final byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);

        inChannel.close();
        outChannel.close();

        final long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));
    }

    // 视频6. 尚硅谷_NIO_通道的数据传输与内存映射文件
    //利用通道完成文件的复制（非直接缓冲区）
    public static void test1() {//10874-10953
        final long start = System.currentTimeMillis();

        FileInputStream fis = null;
        FileOutputStream fos = null;
        //①获取通道
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("d:/1.mkv");
            fos = new FileOutputStream("d:/2.mkv");

            // 用 getChannel() 获取一个写入的通道和传出的通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            //②分配指定大小的缓冲区
            final ByteBuffer buf = ByteBuffer.allocate(1024);

            //③将通道中的数据存入缓冲区中
            while (inChannel.read(buf) != -1) {
                buf.flip(); //切换读取数据的模式
                //④将缓冲区中的数据写入通道中
                outChannel.write(buf);
                buf.clear(); //清空缓冲区
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            // 最后别忘了通道要关闭
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

            if (fos != null) {
                try {
                    fos.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }

            if (fis != null) {
                try {
                    fis.close();
                } catch (final IOException e) {
                    e.printStackTrace();
                }
            }
        }

        final long end = System.currentTimeMillis();
        System.out.println("耗费时间为：" + (end - start));

    }

}
