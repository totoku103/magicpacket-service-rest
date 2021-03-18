package me.totoku103.magicpacket.magicpacketservicerest.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MagicPacketUtilsTest {

    @ParameterizedTest
    @ValueSource(strings = {"66:56:11:1A:4C:82", "66-56-11-1A-4C-82"})
    @DisplayName("구분자로 문자열 나누기")
    public void test_split(String macAddress) {
        final String[] split = MagicPacketUtils.split(macAddress);
        assertThat(split, is(notNullValue()));
        assertThat(split.length, is(comparesEqualTo(6)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"66:56:11:1A:4C82", "6656-11-1A-4C-82", "6656-11-1A4C82"})
    @DisplayName("구분자로 문자열 나누기")
    public void test_split_badMacAddress(String macAddress) {
        assertThrows(IllegalArgumentException.class, () -> MagicPacketUtils.split(macAddress));
    }

    @ParameterizedTest
    @ValueSource(strings = {"66:56:11:1A:4C:A2", "01:e1:30:1f:12:31"})
    @DisplayName("MAC 주소를 Byte Array로 변환")
    public void test_macAddressToByteArray(String macAddress) {
        final byte[] bytes = MagicPacketUtils.getMacByteArray(macAddress);

        Assertions.assertEquals(96, bytes.length);

        Assertions.assertTrue(DatatypeConverter
                .printHexBinary(bytes)
                .startsWith(macAddress.replaceAll(":", "").toUpperCase()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"66:56:11:1A:4C:A2", "01:e1:30:1f:12:31"})
    @DisplayName("MAC 주소를 Byte Array로 변환")
    public void test_(String macAddress) {
        final byte[] emptyMagicPacket = MagicPacketUtils.getMagicPacket(macAddress);

        Assertions.assertEquals(102, emptyMagicPacket.length);

        final byte[] expectedSynchronizationBytes = new byte[6];
        Arrays.fill(expectedSynchronizationBytes, (byte) 0xFF);
        Assertions.assertArrayEquals(Arrays.copyOfRange(emptyMagicPacket, 0, 6), expectedSynchronizationBytes);

        Assertions.assertTrue(DatatypeConverter
                .printHexBinary(Arrays.copyOfRange(emptyMagicPacket, 6, emptyMagicPacket.length))
                .startsWith(macAddress.replaceAll(":", "").toUpperCase()));
    }

}