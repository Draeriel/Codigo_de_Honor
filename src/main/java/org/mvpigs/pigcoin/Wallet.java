package org.mvpigs.pigcoin;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.*;

public class Wallet {

    private PublicKey address = null;
    private static PrivateKey sKey = null;
    private double total_input = 0d;
    private double total_output = 0d;
    private double balance = 0d;
    private Set<Transaction> inputTransactions = new HashSet<>();
    private Set<Transaction> outputTransactions = new HashSet<>();
    private Set<Transaction> usedTransactionsIn = new HashSet<>();
    private Set<Transaction> usedTransactionsOut = new HashSet<>();

    public Wallet() {

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
    public String toString() {
        return "Wallet = " + getAddress().hashCode() + "\n" +
                "Total input = " + getTotal_input() + "\n" +
                "Total output = " + getTotal_output() + "\n" +
                "Balance = " + getBalance() + "\n";
    }

    public void loadCoins(BlockChain bChain) {
        dineroRecibido(bChain);
        dineroEnviado(bChain);
    }

    public void dineroEnviado(BlockChain bChain) {
        Set<Transaction> senderPC = new HashSet<>(bChain.devuelveSenderPC(address));
        for (Transaction trans : senderPC) {
            if (usedTransactionsOut.contains(trans) == false) {
                total_output += trans.getPigcoins();
                balance -= trans.getPigcoins();
                usedTransactionsOut.add(trans);
            }
        }
    }

    public void dineroRecibido(BlockChain bChain) {
        Set<Transaction> senderPC = new HashSet<>(bChain.devuelveRecipientPC(address));
        for (Transaction trans : senderPC) {
            if (usedTransactionsIn.contains(trans) == false) {
                total_input += trans.getPigcoins();
                balance += trans.getPigcoins();
                usedTransactionsIn.add(trans);
            }
        }
    }

    public void loadInputTransactions(BlockChain bChain) {
        Set<Transaction> recipientPC = bChain.loadInputTransactions(address);
        for (Transaction trans : recipientPC){
            inputTransactions.add(trans);
        }

    }

    public Set<Transaction> getInputTransactions() {
        return inputTransactions;
    }

    public void loadOutputTransactions(BlockChain bChain) {
        Set<Transaction> senderPC = bChain.loadInputTransactions(address);
        for (Transaction trans : senderPC){
            outputTransactions.add(trans);
        }
    }

    public Set<Transaction> getOutputTransactions() {
        return outputTransactions;
    }

    public void sendCoins(PublicKey pKey_recipient, Double pigcoins, String message, BlockChain bChain) {
        Map consumedCoins = collectCoins(pigcoins);
        byte[] signedTransaction = signTransaction(message);
        bChain.processTransactions(getAddress(), pKey_recipient, consumedCoins, message, signedTransaction);
    }

    public Map collectCoins(Double pigcoins) {
        Map<String, Double> consumedCoins = new HashMap<>();
        for (Transaction trans : getInputTransactions()) {
            if ((this.address == trans.getpKey_recipient()) && !(consumedCoins.containsKey(trans.getHash()))) {

                    if (trans.getPigcoins() <= pigcoins) {
                        consumedCoins.put(trans.getHash(), trans.getPigcoins());
                        pigcoins -= trans.getPigcoins();
                    } else {
                        while (pigcoins != 0) {
                        consumedCoins.put(trans.getHash(), pigcoins);
                        consumedCoins.put("CA_" + trans.getHash(), trans.getPigcoins() - pigcoins);
                        pigcoins -= pigcoins;
                    }
                }
            }
        }
        return consumedCoins;
    }

    public byte[] signTransaction(String message){
       return GenSig.sign(getsKey(), message);
    }

    public static PrivateKey getsKey() {
        return sKey;
    }
}


