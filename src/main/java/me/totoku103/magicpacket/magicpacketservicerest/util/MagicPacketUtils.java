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
        final byte[] synchronizationByteArray = getSynchronizationByteArray();
        final byte[] macByteArray = getMacByteArray(macAddress);

        byte[] result = new byte[getSynchronizationByteArray().length + getMacByteArray(macAddress).length];
        System.arraycopy(synchronizationByteArray, 0, result, 0, synchronizationByteArray.length);

        System.arraycopy(macByteArray, 0, result, synchronizationByteArray.length, macByteArray.length);

        return result;
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

    public static byte[] getMacByteArray(String macAddress) {
        byte[] result = new byte[96];

        final String[] split = split(macAddress);
        int i = 0;
        for (String s : split) {
            result[i++] = (byte) Integer.parseInt(s, 16);
        }
        return result;
    }
}
