package org.mvpigs.pigcoin;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

public class Wallet {

    private PublicKey address = null;
    private static PrivateKey sKey = null;
    private double total_input = 0d;
    private double total_output = 0d;
    private double balance = 0d;

    public Wallet(){

    }

    public void setSK(PrivateKey sKey) {
        this.sKey = sKey;
    }

    public void setAddress(PublicKey pKey) {
        this.address = pKey;
    }

    public PublicKey getAddress() {
        return address;
    }

    public void generateKeyPair() {
        KeyPair pair = GenSig.generateKeyPair();
        setSK(pair.getPrivate());
        setAddress(pair.getPublic());
    }

    public double getTotal_input() {
        return total_input;
    }

    public double getTotal_output() {
        return total_output;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString(){
        return "Wallet = " + getAddress().hashCode() + "\n" +
                "Total input = " + getTotal_input() + "\n" +
                "Total output = " + getTotal_output() + "\n" +
                "Balance = " + getBalance() + "\n";
    }
}

