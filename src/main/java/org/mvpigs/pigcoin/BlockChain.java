package org.mvpigs.pigcoin;

import java.security.PublicKey;
import java.util.ArrayList;

public class BlockChain {
    private ArrayList<Transaction> bChain = new ArrayList<>();

    public BlockChain(){

    }

    public void addOrigin(Transaction trx){
        bChain.add(trx);
    }

    public void summarize(){
        for (Transaction trans : bChain){
            System.out.println(trans.toString());
        }
    }

    public void summarize(Integer posicion){
        System.out.println(bChain.toArray()[posicion]);
    }

    public ArrayList<Double> devuelveSenderPC(PublicKey address) {
        ArrayList<Double> senderPC = new ArrayList<>();
        for (Transaction trans : bChain){
            if (trans.getpKey_sender() == address) {
                senderPC.add(trans.getPigcoins());
            }
        }
        return senderPC;
    }

    public ArrayList<Double> devuelveRecipientPC(PublicKey address) {
        ArrayList<Double> senderPC = new ArrayList<>();
        for (Transaction trans : bChain){
            if (trans.getpKey_recipient() == address) {
                senderPC.add(trans.getPigcoins());
            }
        }
        return senderPC;
    }

    public ArrayList<Transaction> loadInputTransactions(PublicKey address) {
        ArrayList<Transaction> recipientPC = new ArrayList<>();
        for (Transaction trans : bChain){
            if (trans.getpKey_recipient() == address) {
               recipientPC.add(trans);
            }
        }
        return recipientPC;
    }

    public ArrayList<Transaction> loadOutputTransactions(PublicKey address) {
        ArrayList<Transaction> senderPC = new ArrayList<>();
        for (Transaction trans : bChain){
            if (trans.getpKey_sender() == address) {
                senderPC.add(trans);
            }
        }
        return senderPC;
    }
}
