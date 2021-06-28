
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 利用SocketChannel和ServerSocketChannel实现客户端与服务器端简单通信
 * 先启动webServer 在启动webclient
 * https://blog.csdn.net/worldchinalee/article/details/108695356
 */
public class WebClient {
   public static void main(String[] args) throws IOException {
     //1.通过SocketChannel的open()方法创建一个SocketChannel对象
     SocketChannel socketChannel = SocketChannel.open();
     //2.连接到远程服务器（连接此通道的socket）
     socketChannel.connect(new InetSocketAddress("127.0.0.1", 3333));
     // 3.创建写数据缓存区对象
     ByteBuffer writeBuffer = ByteBuffer.allocate(128);
     writeBuffer.put("hello WebServer this is from WebClient".getBytes());
     writeBuffer.flip();
     socketChannel.write(writeBuffer);
     //创建读数据缓存区对象
     ByteBuffer readBuffer = ByteBuffer.allocate(128);
     socketChannel.read(readBuffer);
     //String 字符串常量，不可变；StringBuffer 字符串变量（线程安全），可变；StringBuilder 字符串变量（非线程安全），可变
     StringBuilder stringBuffer=new StringBuilder();
     //4.将Buffer从写模式变为可读模式
     readBuffer.flip();
     while (readBuffer.hasRemaining()) {
       stringBuffer.append((char) readBuffer.get());
     }
     System.out.println("从服务端接收到的数据："+stringBuffer);
    socketChannel.close();
  }
}