import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Copy {
    public static void main(String[] args) {
        ByteBuffer b = ByteBuffer.allocate( 15 ); // 15个字节大小的缓冲区
        System.out.println( "limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());
        for ( int i = 0 ; i < 10 ; i++) {
            // 存入10个字节数据
            b.put(( byte ) i);
        }
        System.out.println( "limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());
        b.flip(); // 重置position
        System.out.println( "limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());
        for ( int i = 0 ; i < 5 ; i++) {
            System.out.print(b.get());
        }
        System.out.println();
        System.out.println( "limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());
        b.flip();
        System.out.println( "limit=" + b.limit() + " capacity=" + b.capacity()
                + " position=" + b.position());
    }

    public static void nioCopyFile(String resource, String destination)
            throws IOException {
        FileInputStream fis = new FileInputStream(resource);
        FileOutputStream fos = new FileOutputStream(destination);
        FileChannel readChannel = fis.getChannel(); // 读文件通道
        FileChannel writeChannel = fos.getChannel(); // 写文件通道
        ByteBuffer buffer = ByteBuffer.allocate( 1024 ); // 读入数据缓存
        while ( true ) {
            buffer.clear();
            int len = readChannel.read(buffer); // 读入数据
            if (len == - 1 ) {
                break ; // 读取完毕
            }
            buffer.flip();
            writeChannel.write(buffer); // 写入文件
        }
        readChannel.close();
        writeChannel.close();
    }
}
