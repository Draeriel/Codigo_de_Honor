package org.mvpigs.pigcoin;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Wallet {

    private PublicKey address = null;
    private static PrivateKey sKey = null;
    private double total_input = 0d;
    private double total_output = 0d;
    private double balance = 0d;
    private Set<Transaction> inputTransactions = new HashSet<>();
    private Set<Transaction> outputTransactions = new HashSet<>();
    private Set<Transaction> usedTransactions = new HashSet<>();

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

    public void loadCoins(BlockChain bChain){
        dineroEnviado(bChain);
        dineroRecibido(bChain);
    }

    public void dineroEnviado(BlockChain bChain) {
        Set<Transaction> senderPC = new HashSet<>(bChain.devuelveSenderPC(address));
        for (Transaction trans : senderPC) {
            if (usedTransactions.contains(trans) == false) {
                total_output += trans.getPigcoins();
                balance -= trans.getPigcoins();
                usedTransactions.add(trans);
            }
        }
    }

    public void dineroRecibido(BlockChain bChain) {
        Set<Transaction> senderPC = new HashSet<>(bChain.devuelveRecipientPC(address));
        for (Transaction trans : senderPC){
            if (usedTransactions.contains(trans) == false) {
                total_input += trans.getPigcoins();
                balance += trans.getPigcoins();
                usedTransactions.add(trans);
            }
        }
    }

    public void loadInputTransactions(BlockChain bChain) {
       inputTransactions = bChain.loadInputTransactions(address);
    }

    public Set<Transaction> getInputTransactions() {
        return inputTransactions;
    }

    public void loadOutputTransactions(BlockChain bChain){

        outputTransactions = bChain.loadOutputTransactions(address);
    }

    public Set<Transaction> getOutputTransactions() {
        return outputTransactions;
    }
}
    /*public void sendCoins(PublicKey address, double pigcoins, String message, BlockChain bChain) {

                   collectCoins(pigcoins);
                   System.out.println(usedTransactions);
    };

    public void collectCoins(double pigcoins){
        for (Transaction trans : outputTransactions) {
            if ((this.address == trans.getpKey_sender()) && !(usedTransactions.contains(trans))) {
                usedTransactions.add(trans);
            }
        }
    }
*/

