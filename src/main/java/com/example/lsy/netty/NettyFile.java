package com.example.lsy.netty;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class NettyFile {

    private static final int BYTE_BUFFER_LENGTH = 1024;
    private static final String FILE_PATH = "C:\\Users\\w\\Desktop\\NettyTest.txt";

    public static void main(final String[] args) {
        NettyFile.dataToFile("aaaaaaaaaaaaasdgfdgbsdf gdf", NettyFile.FILE_PATH);
        NettyFile.dataFromFile(NettyFile.FILE_PATH);
    }

    // 数据 -> 文件
    private static void dataToFile(final String data, final String filePath) {
        // 构建输出流  ->   从输出流中获取 channel
        try (final FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             final FileChannel fileChannel = fileOutputStream.getChannel()) {

            // 设置缓冲区
            final ByteBuffer byteBuffer = ByteBuffer.allocate(NettyFile.BYTE_BUFFER_LENGTH);

            // 将需要读写的数据放到缓冲区
            final int i = 0;
            final int length = data.getBytes().length;
            // 一次就可以读完
            // 一次读不完  需要循环读取
            if (NettyFile.BYTE_BUFFER_LENGTH > data.getBytes().length) {
                byteBuffer.put(data.getBytes(), i, data.getBytes().length);
                byteBuffer.flip();
                fileChannel.write(byteBuffer);
            } else {
                for (int temp = 0; temp < data.getBytes().length; temp += NettyFile.BYTE_BUFFER_LENGTH) {
                    byteBuffer.clear();
                    byteBuffer.put(data.getBytes(), temp, NettyFile.BYTE_BUFFER_LENGTH);
                    // 翻转缓冲区，可以对外读
                    // 这里的 flip() 是重点，其可以将Buffer的属性重置，可以对外写
                    byteBuffer.flip();
                    // 将缓冲区内的数据写到 channel中
                    fileChannel.write(byteBuffer);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    // 文件 -> 内存
    private static void dataFromFile(final String filePath) {
        final File file = new File(filePath);
        // 从输入流中获取 channel
        try (final FileInputStream fileInputStream = new FileInputStream(file);
             final FileChannel channel = fileInputStream.getChannel()) {

            // 分配缓冲区
            final ByteBuffer byteBuffer = ByteBuffer.allocate(NettyFile.BYTE_BUFFER_LENGTH);
            final StringBuilder result = new StringBuilder();
            while (true) {
                byteBuffer.clear();
                // 将 channel数据写到buffer中
                final int read = channel.read(byteBuffer);
                // 因为byteBuffer大小原因，因此需要用一个中间字符串接受一下
                result.append(new String(byteBuffer.array()));
                if (read == -1) {
                    break;
                }
            }

            NettyFile.log.info("从文本读取结果：{}", result);
        } catch (final Exception e) {
            NettyFile.log.error("文件读取错误，错误原因 ：{}", e);
        }
    }

    // 将两个channel通过byteBuffer进行转移
    private static void copyFileUseBuffer(final String sourceFilePath, final String targetFilePath) {
        final File source = new File(sourceFilePath);
        final File target = new File(targetFilePath);
        // 获取文件输入输出流
        // 从输入输出流中获取输入输出 channel

        try (final FileInputStream fileInputStream = new FileInputStream(source);
             final FileOutputStream fileOutputStream = new FileOutputStream(target);
             final FileChannel fileInputStreamChannel = fileInputStream.getChannel();
             final FileChannel fileOutputStreamChannel = fileOutputStream.getChannel()) {

            // 分配缓冲区
            final ByteBuffer byteBuffer = ByteBuffer.allocate(NettyFile.BYTE_BUFFER_LENGTH);
            // 将输入流中的数据写到缓冲区
            // 这里需要循环读取，如果是大文件，不能直接建立一个很大的内存空间，直接全部放进去，并且还可能放不进去
            while (true) {
                byteBuffer.clear();

                final int read = fileInputStreamChannel.read(byteBuffer);
                if (read == -1) {
                    break;
                }
                // 翻转缓冲区
                byteBuffer.flip();

                // 将翻转后可以对外写的缓存区的内容写到输出流，从而形成文件
                fileOutputStreamChannel.write(byteBuffer);
            }
        } catch (final Exception e) {
            NettyFile.log.error("文件复制错误，错误原因 ：{0}", e);
        }
    }

    // 直接用channel的复制完成文件复制
    private static void copyFileUseChannelTransfer(final String sourceFilePath, final String targetFilePath) {
        final File source = new File(sourceFilePath);
        final File target = new File(targetFilePath);
        // 获取文件输入输出流
        // 从输入输出流中获取输入输出 channel
        try (final FileInputStream fileInputStream = new FileInputStream(source);
             final FileOutputStream fileOutputStream = new FileOutputStream(target);
             final FileChannel fileInputStreamChannel = fileInputStream.getChannel();
             final FileChannel fileOutputStreamChannel = fileOutputStream.getChannel()) {

            // 直接将输入channel复制到输出channel
            fileOutputStreamChannel.transferFrom(fileInputStreamChannel, fileInputStreamChannel.position(), fileInputStreamChannel.size());

        } catch (final Exception e) {
            NettyFile.log.error("文件复制错误，错误原因 ：{0}", e);
        }
    }
}
