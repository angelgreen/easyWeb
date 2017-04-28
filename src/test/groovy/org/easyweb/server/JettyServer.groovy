package org.easyweb.server

import org.mortbay.jetty.*
import org.mortbay.jetty.webapp.*

class JettyServer {

    def running = true
    def thread

    def start(def latch) {

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (running) {
                    def server = new Server(8089)
                    def context = new WebAppContext()

                    context.setContextPath("/")
                    context.setWar("./src/main/webapp")

                    server.setHandler(context)

                    server.start()
                    latch.countDown()

                    try {
                        server.join()
                    } catch (InterruptedException e) {
                        e.printStackTrace()
                        Thread.currentThread().interrupt()
                    }
                }
            }
        });

        thread.name = "jetty"
        thread.start()
    }

    def stop() {
        running = false
        if (null != thread) thread.interrupt()
    }

    def start() {
        def server = new Server(8080)
        def context = new WebAppContext()

        context.setContextPath("/")
        context.setWar("./src/main/webapp")

        server.setHandler(context)
        server.start()
        server.join()
    }

    public static void main(String... args) {
        def server = new JettyServer()

        def registry = new ZookeeperRegistry()

        registry.startZK()

        registry.registerService("/service/userAPI", "localhost:8080")

        server.start() //stop it ..

        registry.stopZK() //stop it
    }
}
