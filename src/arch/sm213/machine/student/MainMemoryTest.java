package arch.sm213.machine.student;

import machine.AbstractMainMemory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class MainMemoryTest {
    @Test
    public void testbytesToInteger() {
        MainMemory mm = new MainMemory(4);
        byte byte0 = (byte) 0x00;
        byte byte1 = (byte) 0x80;
        byte byte2 = (byte) 0xff;
        byte byte3 = (byte) 0x7f;
        assertEquals(mm.bytesToInteger(byte0, byte0, byte0, byte0), 0);
        assertEquals(mm.bytesToInteger(byte2, byte2, byte2, byte2), 0xffffffff);
        assertEquals(mm.bytesToInteger(byte1, byte0, byte0, byte0), 0x80000000);
        assertEquals(mm.bytesToInteger(byte3, byte1, byte0, byte0), 0x7f800000);
    }

    @Test
    public void testintegerToBytes() {
        MainMemory mm = new MainMemory(4);
        byte byte0 = (byte) 0x00;
        byte byte1 = (byte) 0x80;
        byte byte2 = (byte) 0xff;
        byte byte3 = (byte) 0x7f;
        assertEquals(mm.integerToBytes(0)[0], byte0);
        assertEquals(mm.integerToBytes(0)[1], byte0);
        assertEquals(mm.integerToBytes(0)[2], byte0);
        assertEquals(mm.integerToBytes(0)[3], byte0);
        assertEquals(mm.integerToBytes(-1)[0], byte2);
        assertEquals(mm.integerToBytes(-1)[1], byte2);
        assertEquals(mm.integerToBytes(-1)[2], byte2);
        assertEquals(mm.integerToBytes(-1)[3], byte2);
        assertEquals(mm.integerToBytes(0x80000000)[0], byte1);
        assertEquals(mm.integerToBytes(0x80000000)[1], byte0);
        assertEquals(mm.integerToBytes(0x80000000)[2], byte0);
        assertEquals(mm.integerToBytes(0x80000000)[3], byte0);
        assertEquals(mm.integerToBytes(0x7f800000)[0], byte3);
        assertEquals(mm.integerToBytes(0x7f800000)[1], byte1);
        assertEquals(mm.integerToBytes(0x7f800000)[2], byte0);
        assertEquals(mm.integerToBytes(0x7f800000)[3], byte0);
    }

    @Test
    public void testSet() throws AbstractMainMemory.InvalidAddressException {
        MainMemory mm0 = new MainMemory(4);
        MainMemory mm1 = new MainMemory(4);
        MainMemory mm2 = new MainMemory(4);
        byte byte0 = (byte) 0x00;
        byte byte1 = (byte) 0x80;
        byte byte2 = (byte) 0xff;
        byte byte3 = (byte) 0x7f;
        byte[] bytes0 = {byte2};
        byte[] bytes1 = {byte0, byte1, byte2, byte3};
        try {
            mm0.set(0, bytes0);
            assertEquals(mm0.get(0, 1)[0], byte2);
        } catch (AbstractMainMemory.InvalidAddressException e){
            fail();
        }
        try {
            mm1.set(-1, bytes0);
            fail();
        } catch (AbstractMainMemory.InvalidAddressException e){
            assertTrue(true);
        }
        try {
            mm1.set(4, bytes0);
            fail();
        } catch (AbstractMainMemory.InvalidAddressException e){
            assertTrue(true);
        }
        try {
            mm2.set(3, bytes0);
            assertEquals(mm2.get(3, 1)[0], byte2);
        } catch (AbstractMainMemory.InvalidAddressException e){
            fail();
        }
        try {
            mm1.set(1, bytes1);
            fail();
        } catch (AbstractMainMemory.InvalidAddressException e){
            assertTrue(true);
        }
        try {
            mm1.set(0, bytes1);
            assertEquals(mm1.get(0, 4)[0], byte0);
            assertEquals(mm1.get(0, 4)[1], byte1);
            assertEquals(mm1.get(0, 4)[2], byte2);
            assertEquals(mm1.get(0, 4)[3], byte3);
        } catch (AbstractMainMemory.InvalidAddressException e){
            fail();
        }
    }

    @Test
    public void testGet() {
        MainMemory mm0 = new MainMemory(4);
        byte byte0 = (byte) 0x00;
        byte byte1 = (byte) 0x80;
        byte byte2 = (byte) 0xff;
        byte byte3 = (byte) 0x7f;
        byte[] bytes1 = {byte0, byte1, byte2, byte3};
        try {
            mm0.set(0, bytes1);
        } catch (Exception e) {
            fail();
        }
        try {
            assertEquals(mm0.get(0, 4)[0], byte0);
            assertEquals(mm0.get(0, 4)[1], byte1);
            assertEquals(mm0.get(0, 4)[2], byte2);
            assertEquals(mm0.get(0, 4)[3], byte3);
        } catch (AbstractMainMemory.InvalidAddressException e){
            fail();
        }
        try {
            mm0.get(-1,1);
            fail();
        } catch (AbstractMainMemory.InvalidAddressException e) {
            assertTrue(true);
        }
        try {
            mm0.get(4,1);
            fail();
        } catch (AbstractMainMemory.InvalidAddressException e) {
            assertTrue(true);
        }
        try {
            mm0.get(0,5);
            fail();
        } catch (AbstractMainMemory.InvalidAddressException e) {
            assertTrue(true);
        }
    }

}
