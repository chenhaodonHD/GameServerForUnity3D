package app;

import server.Unity3DServer;

/**
 * Created by HD on 2017/9/26.
 */
public class Unity3DApplication {

    private static Unity3DApplication instance = null;

    private boolean stop = false;

    private Unity3DApplication() {
    }

    public static synchronized Unity3DApplication getApplication() {
        if (instance == null) {
            instance = new Unity3DApplication();
        }
        return instance;
    }

    public void start() {
        init();
        new Thread(new Unity3DServer(), "U3d Server").start();

        while (!stop) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                getApplication().stopMe();
            }
        });
        getApplication().start();
    }

    public void stopMe() {
        stop = true;
        System.out.println("系统即将关闭...");
    }


    /**
     * 初始化系统
     */
    private void init() {

    }
}
