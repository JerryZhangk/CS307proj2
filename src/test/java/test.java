public class test {
    public static void main(String[] args) {
        // 这里的代码还是运行在主线程中。
        for(int i = 0; i < 19; i++){
            MyThread t = new MyThread();
            // 启动线程
            //t.run(); // 不会启动线程，不会分配新的分支栈。（这种方式就是单线程。）
            t.start();
        }
    }
}

class MyThread extends Thread {
    @Override
    public void run() {
        // 编写程序，这段程序运行在分支线程中（分支栈）。
        Client client = new Client();
        client.test();
    }
}
