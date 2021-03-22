package me.totoku103.magicpacket.magicpacketservicerest.util;

import java.util.Arrays;

public class MagicPacketUtils {
    private static final String DELIMITER_REGEX = "[^0-9a-fA-F]";

    public static String[] split(String macAddress) {
        final String[] result = macAddress.split(DELIMITER_REGEX);
        if (result.length != 6) throw new IllegalArgumentException(String.format("macAddress: %s", macAddress));
        return result;
    }

    public static byte[] getMagicPacket(String macAddress) {
        final byte[] result = new byte[102];
        final byte[] synchronizationByteArray = getSynchronizationByteArray();
        final byte[] macByteArray = convertByteArray(macAddress);

        System.arraycopy(synchronizationByteArray, 0, result, 0, synchronizationByteArray.length);
        return fillByteArray(result, synchronizationByteArray.length, macByteArray);
    }

    public static byte[] fillByteArray(byte[] fillTarget, int startIndex, byte[] source) {
        for (int i = startIndex; i < fillTarget.length; i += source.length) {
            System.arraycopy(source, 0, fillTarget, i, source.length);
        }
        return fillTarget;
    }

    public static byte[] getSynchronizationByteArray() {
        byte[] result = new byte[6];
        Arrays.fill(result, (byte) 0xff);
        return result;
    }

    public static byte[] convertByteArray(String macAddress) {
        byte[] result = new byte[6];

        final String[] split = split(macAddress);
        int i = 0;
        for (String s : split) {
            result[i++] = (byte) Integer.parseInt(s, 16);
        }
        return result;
    }
}
