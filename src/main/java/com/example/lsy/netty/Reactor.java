package com.example.lsy.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class Reactor implements Runnable {

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;

    /**
     * Acceptor的功能是：
     * 接收客户端连接，分配合适的处理器handler来响应I/O事件
     * SelectionKey代表I/O事件的状态
     */
    class Acceptor implements Runnable {
        @Override
        public void run() {
            SocketChannel socketChannel = null;
            try {
                socketChannel = Reactor.this.serverSocketChannel.accept();
                if (socketChannel != null) {
                    new Handler(socketChannel, Reactor.this.selector);
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * Handler处理I/O读写事件，需要持有SocketChannel，并注册到Selector
     */
    class Handler {

        //真正的连接对象
        private final SocketChannel socketChannel;

        private final SelectionKey selectionKey;

        Handler(final SocketChannel socketChannel, final Selector selector) throws IOException {
            this.socketChannel = socketChannel;
            this.socketChannel.configureBlocking(false);
            this.selectionKey = this.socketChannel.register(selector, SelectionKey.OP_READ);
            //添加 handler为附件
            this.selectionKey.attach(this);
        }

        public void run() {
            final ByteBuffer buffer = ByteBuffer.allocate(128);
            try {
                int read = 0;
                while ((read = this.socketChannel.read(buffer)) > 0) {
                    buffer.flip();

                    final Charset charset = Charset.forName("utf-8");
                    final CharBuffer charBuffer = charset.decode(buffer);
                    System.out.println(charBuffer.toString());

                    buffer.clear();
                }

            } catch (final IOException e) {
                e.printStackTrace();
            }

        }
    }

    public Reactor(final int port) throws Exception {
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().bind(new InetSocketAddress(port));
        this.serverSocketChannel.configureBlocking(false);
        final SelectionKey selectionKey = this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);

        //在"接受连接"事件的SelectionKey中添加一个Acceptor作为附件
        selectionKey.attach(new Acceptor());
    }

    @Override
    public void run() {
        //死循环，等待客户端连接
        while (true) {
            try {
                //阻塞方法，获取IO事件
                this.selector.select();
                final Set<SelectionKey> selectedKeys = this.selector.selectedKeys();
                final Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    final SelectionKey selectionKey = iterator.next();
                    //拿到selectionKey的附件并执行
                    final Object attachment = selectionKey.attachment();
    
                    //获取的附件可能是Acceptor，也可能是Handler
                    final Runnable runnable = (Runnable) attachment;
                    runnable.run();
    
                    //处理完IO事件后，从事件集合中删除selectionKey
                    iterator.remove();
                }
    
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }
}