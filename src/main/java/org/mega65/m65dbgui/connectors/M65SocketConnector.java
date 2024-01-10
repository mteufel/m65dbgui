package org.mega65.m65dbgui.connectors;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.mega65.m65dbgui.operations.M65Memory;
import org.mega65.m65dbgui.operations.M65Registers;
import org.mega65.m65dbgui.operations.Operation;
import org.mega65.m65dbgui.util.Util;
import org.slf4j.Logger;

import java.io.*;
import java.net.Socket;

@Singleton
public class M65SocketConnector implements Connector {

    Logger logger = Util.getLogger(M65SocketConnector.class);

    Socket socket;

    private boolean connected = false;
    private boolean pause = false;
    private String host = "localhost";
    private int port = 4510;


    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void connect(boolean connected) {

        this.connected = connected;
        if (connected) {
            try {
                socket = new Socket(host, port);
                logger.info("Connected to: " + this.host + ":" + this.port);
            } catch ( Exception e ) {
                logger.error("Connection error" , e);
                this.connected = false;
            }
        } else {
            try {
                if (socket.isConnected()) {
                    socket.close();
                    logger.info("Connection closed");
                }
            } catch (Exception e) {
                logger.error("Connection error" , e);
            }
        }
    }

    @Override
    synchronized public Operation sendReceive(Operation operation) {

        //if (operation instanceof M65Memory) {
            if (pause) {
                return operation;
            }
        //}

        //if (operation instanceof M65Registers) {
        //    return operation;
        //}





        try {
            OutputStream output = socket.getOutputStream();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(operation.getCommand());
            writer.flush();
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            operation.operate(reader);
        } catch (Exception e) {
            logger.error("error", e);
        }
        return operation;
    }

    public boolean isConnected() {
        return connected;
    }
}
