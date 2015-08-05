package com.txy.udp;

public class ControlSender {

    private String ip;
    private int port;

    private byte[] nplaymode = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x50,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x82, (byte) 0x00, (byte) 0x00 };

    private byte[] nshowmode = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x50,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x83, (byte) 0x00, (byte) 0x00 };

    private byte[] nmeetmode = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x50,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x81, (byte) 0x00, (byte) 0x00 };

    private byte[] nsavemode = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x50,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x80, (byte) 0x00, (byte) 0x00 };

    private byte[] offmode = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x50,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x04, (byte) 0x00, (byte) 0x00 };

    private byte[] savemode = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x50,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00 };

    private byte[] meetmode = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x50,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x01, (byte) 0x00, (byte) 0x00 };

    private byte[] playmode = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x50,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x02, (byte) 0x00, (byte) 0x00 };

    private byte[] showmode = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x50,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x03, (byte) 0x00, (byte) 0x00 };

    private byte[] typoweronCtrl = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x5c,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x08, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00 };

    private byte[] tyoffCtrl = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x5c,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x08, (byte) 0x01, (byte) 0x0a, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00 };

    private byte[] tyLeft = new byte[] { (byte) 0x1e, (byte) 0x1e, (byte) 0x48,
            (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00, (byte) 0x08,
            (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x5c, (byte) 0x01,
            (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x08,
            (byte) 0x01, (byte) 0x06, (byte) 0x00, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00 };

    private byte[] tydownCtrl = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x5c,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x08, (byte) 0x01, (byte) 0x05, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00 };

    private byte[] tyright = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x5c,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x08, (byte) 0x01, (byte) 0x07, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00 };

    private byte[] tyEnter = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x5c,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x08, (byte) 0x01, (byte) 0x08, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00 };

    private byte[] tyssCtrl = new byte[] { (byte) 0x1e, (byte) 0x1e,
            (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
            (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff,
            (byte) 0xff, (byte) 0xff, (byte) 0x00, (byte) 0x00, (byte) 0x5c,
            (byte) 0x01, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
            (byte) 0x08, (byte) 0x01, (byte) 0x0f, (byte) 0x00, (byte) 0x00,
            (byte) 0x00, (byte) 0x00, (byte) 0x00 };

    public ControlSender(String ip, int port) {

        this.ip = ip;
        this.port = port;

    }

    /**
     * 单独控制灯管指令
     *
     * @param byte25
     * @param byte26
     */
    public void sendLightCtrl(byte byte25, byte byte26) {

        byte[] windowsCtrl = new byte[] { (byte) 0x1d, (byte) 0x1d,
                (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53,
                (byte) 0x00, (byte) 0x08, (byte) 0x00, (byte) 0x00,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0x00, (byte) 0x00, (byte) 0x57, (byte) 0x01,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0x03, (byte) byte25, (byte) byte26, (byte) 0x00,
                (byte) 0x00 };

        startSend(windowsCtrl);
    }

    /**
     * 窗帘的控制
     *
     * @param byte23
     */
    public void sendWindowsCtrl(byte byte23) {

        byte[] windowsCtrl = new byte[] { (byte) 0x1a, (byte) 0x1a,
                (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53,
                (byte) 0x00, (byte) 0x08, (byte) 0x00, (byte) 0x00,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0x00, (byte) 0x00, (byte) 0x5d, (byte) 0x01,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0x02, byte23, (byte) 0x00, (byte) 0x00, };

        startSend(windowsCtrl);
    }

    /**
     * 时间的设置
     *
     * @param timeSetByte
     */
    public void sendTimeSet(byte[] timeSetByte) {

        byte[] timeSetCtrl = new byte[] { (byte) 0x21, (byte) 0x21,
                (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53,
                (byte) 0x00, (byte) 0x08, (byte) 0x00, (byte) 0x00,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0x00, (byte) 0x00, (byte) 0x55, (byte) 0x01,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0x08, timeSetByte[0], timeSetByte[1], timeSetByte[2],
                timeSetByte[3], timeSetByte[4], timeSetByte[5], timeSetByte[6],
                timeSetByte[7], (byte) 0x00, (byte) 0x00 };

        startSend(timeSetCtrl);
    }

    /**
     * 关闭模式
     *
     * @param winbyte
     * @param airbyte
     * @param tvbyte
     */
    public void sendOffModeSet(byte winbyte, byte airbyte, byte tvbyte) {

        byte[] offModeCtrl = new byte[] { (byte) 0x1c, (byte) 0x1c,
                (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53,
                (byte) 0x00, (byte) 0x08, (byte) 0x00, (byte) 0x00,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0x00, (byte) 0x00, (byte) 0x56, (byte) 0x01,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0x04, winbyte, airbyte, tvbyte, (byte) 0x00, (byte) 0x00 };

        startSend(offModeCtrl);
    }

    /**
     * 模式的选择
     *
     * @param modeset
     * @param curmode
     */
    public void sendModeSet(byte[] modeset, int curmode) {
        byte byte16 = 0;
        if (curmode == 0) {
            byte16 = 0x51;
        } else if (curmode == 1) {
            byte16 = 0x52;
        } else if (curmode == 2) {
            byte16 = 0x53;
        } else if (curmode == 3) {
            byte16 = 0x54;
        }
        byte[] modesetCtrl = new byte[] { (byte) 0x25, (byte) 0x25,
                (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53,
                (byte) 0x00, (byte) 0x08, (byte) 0x00, (byte) 0x00,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0x00, (byte) 0x00, byte16, (byte) 0x01, (byte) 0xff,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x0d, modeset[0],
                modeset[1], modeset[2], modeset[3], modeset[4], modeset[5],
                modeset[6], modeset[7], modeset[8], modeset[9], modeset[10],
                modeset[11], (byte) 0x00, (byte) 0x00

        };

        startSend(modesetCtrl);
    }

    /**
     * 发送红外指令 byte23电器ID ，byte控制电器地址，byte25控制电器指令
     *
     * @param byte23
     * @param byte24
     * @param byte25
     */
    public void sendInfrareCtrl(byte byte23, byte byte24, byte byte25) {
        byte[] infrareCtrl = new byte[] { (byte) 0x1c, (byte) 0x1c,
                (byte) 0x48, (byte) 0x4d, (byte) 0x49, (byte) 0x53,
                (byte) 0x00, (byte) 0x08, (byte) 0x00, (byte) 0x00,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0x00, (byte) 0x00, (byte) 0x5c, (byte) 0x01,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
                (byte) 0x03, byte23, byte24, byte25, (byte) 0x00, (byte) 0x00 };

        startSend(infrareCtrl);
    }

    /**
     * 投影仪的控制
     *
     * @param tyCtrl
     */
    public void sendTyCtrl(String tyCtrl) {
        if (tyCtrl.equalsIgnoreCase("typoweron")) {
            startSend(typoweronCtrl);
        } else if (tyCtrl.equalsIgnoreCase("tyoff")) {
            startSend(tyoffCtrl);
        } else if (tyCtrl.equalsIgnoreCase("tyleft")) {
            startSend(tyLeft);
        } else if (tyCtrl.equalsIgnoreCase("tydown")) {
            startSend(tydownCtrl);
        } else if (tyCtrl.equalsIgnoreCase("tyright")) {
            startSend(tyright);
        } else if (tyCtrl.equalsIgnoreCase("tyss")) {
            startSend(tyssCtrl);// 投影仪上升的控制
        }

    }

    public void sendMode(byte byte27) {
        byte[] modeCtrl = new byte[] { (byte) 0x1e, (byte) 0x1e, (byte) 0x48,
                (byte) 0x4d, (byte) 0x49, (byte) 0x53, (byte) 0x00,
                (byte) 0x08, (byte) 0x00, (byte) 0x00, (byte) 0xff,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x00,
                (byte) 0x00, (byte) 0x50, (byte) 0x01, (byte) 0xff,
                (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0x06,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
                (byte) byte27, (byte) 0x00, (byte) 0x00 };

        startSend(modeCtrl);

    }

    /**
     * 模式发送
     *
     * @param mode
     */
    public void sendMode(String mode) {
        if (mode.equals("savemode")) {
            startSend(savemode);

        } else if (mode.equals("meetmode")) {
            startSend(meetmode);
        } else if (mode.equals("playmode")) {

            startSend(playmode);

        } else if (mode.equals("showmode")) {

            startSend(showmode);

        } else if (mode.equals("offmode")) {

            startSend(offmode);

        } else if (mode.equals("nsavemode")) {
            startSend(nsavemode);

        } else if (mode.equals("nmeetmode")) {
            startSend(nmeetmode);

        } else if (mode.equals("nshowmode")) {
            startSend(nshowmode);

        } else if (mode.equals("nplaymode")) {
            startSend(nplaymode);
        }
    }

    /**
     * 启动发送线程
     *
     * @param modesetCtrl
     */
    private void startSend(byte[] modesetCtrl) {
        Sender infrareSend = new Sender(modesetCtrl, ip, port);
        Thread s1 = new Thread(infrareSend);
        s1.start();
        try {
            s1.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        s1.interrupt();
    }
}
