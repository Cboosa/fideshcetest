package com.example.fideshcetest;

import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.Stream;

public class FidesHceApduService extends HostApduService {

    private static HashMap<Integer, String> randNames;
    private static HashMap<Integer, String> randHellos;

    static {
        randHellos = new HashMap<>();
        randNames = new HashMap<>();

        randHellos.put(0, "Ahoj");
        randHellos.put(1, "Cauky");
        randHellos.put(2, "Nazdarek");

        randNames.put(0, "Verca");
        randNames.put(1, "Ivca");
        randNames.put(2, "Renca");
        randNames.put(3, "Danca");
    }

    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras) {

        // prikaz je SELECT
        if (commandApdu[1] == (byte)0xA4) {
            // odpovídáme pouze 0x90 0x00 OK
            return new byte[] { (byte)0x90, (byte)0x00 };
        }

        byte length = commandApdu[4];
        byte[] messageBytes = new byte[length];

        System.arraycopy(commandApdu, 5, messageBytes, 0, length);

        String helloMessage = new String(messageBytes);

        Intent intent = new Intent(Constants.NFC_HELLO_MESSAGE);
        intent.putExtra(Constants.NAME_STRING, helloMessage);

        sendBroadcast(intent);

        Random random = new Random();

        String response = String.format("%s, ja jsem %s", randHellos.get(random.nextInt(3)), randNames.get(random.nextInt(4)));

        byte[] payload = response.getBytes();
        byte[] status = new byte[] { (byte)0x90, (byte)0x00 };

        byte[] retBytes = Arrays.copyOf(payload, payload.length + status.length);
        System.arraycopy(status, 0, retBytes, payload.length, status.length);

        return retBytes;
    }

    @Override
    public void onDeactivated(int reason) {

    }
}
