package com.ruoyi.onvif;

import com.ruoyi.onvif.listeners.DiscoveryCallback;
import com.ruoyi.onvif.parsers.DiscoveryParser;
import com.ruoyi.onvif.responses.OnvifResponse;
import com.ruoyi.onvif.utils.PortPoolUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * Created by Tomas Verhelst on 05/09/2018.
 * Copyright (c) 2018 TELETASK BVBA. All rights reserved.
 */
public class DiscoveryThread extends Thread {

    // Constants
    public static final String TAG = DiscoveryThread.class.getSimpleName();

    // Attributes
    private DatagramSocket server;
    private int timeout;
    private DiscoveryParser parser;
    private DiscoveryCallback callback;
    private int port;

    // Constructors
    DiscoveryThread(DatagramSocket server, int port, int timeout, DiscoveryMode mode, DiscoveryCallback callback) {
        super();
        this.server = server;
        this.port = port;
        this.timeout = timeout;
        this.callback = callback;
        parser = new DiscoveryParser(mode);
    }

    @Override
    public void run() {
        try {
            boolean started = false;
            DatagramPacket packet = new DatagramPacket(new byte[4096], 4096);
            server.setSoTimeout(timeout);
            long timerStarted = System.currentTimeMillis();
            while (System.currentTimeMillis() - timerStarted < timeout) {
                if (!started) {
                    callback.onDiscoveryStarted();
                    started = true;
                }

                server.receive(packet);
                String response = new String(packet.getData(), 0, packet.getLength());
                parser.setHostName(packet.getAddress().getHostName());
                callback.onDevicesFound(parser.parse(new OnvifResponse(response)));
            }

        } catch (IOException ignored) {
        } finally {
            if (server != null && !server.isClosed()) {
                server.close();
            }
            PortPoolUtils.releasePort(port);
            callback.onDiscoveryFinished();
        }
    }
}
