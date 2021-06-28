import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 1. 得到Channel
 * 2. 申请Buffer
 * 3. 建立Channel和Buffer的读/写关系
 * 4. 关闭
 */
public class Buffer {
    public static void main(String[] args) throws Exception {
        FileInputStream fin = new FileInputStream(new File(
                "C:\\Users\\w\\Desktop\\settings.xml"));
        FileChannel fc = fin.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        fc.read(byteBuffer);
        fc.close();
        byteBuffer.flip(); //读写转换
    }
}
