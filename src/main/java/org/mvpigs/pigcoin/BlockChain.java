package org.mvpigs.pigcoin;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class BlockChain {
    private Set<Transaction> bChain = new HashSet<>();

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

    public Set<Transaction> devuelveSenderPC(PublicKey address) {
        Set<Transaction> senderPC = new HashSet<>();
        for (Transaction trans : bChain){
            if (trans.getpKey_sender() == address) {
                senderPC.add(trans);
            }
        }
        return senderPC;
    }

    public Set<Transaction> devuelveRecipientPC(PublicKey address) {
        Set<Transaction> senderPC = new HashSet<>();
        for (Transaction trans : bChain){
            if (trans.getpKey_recipient() == address) {
                senderPC.add(trans);
            }
        }
        return senderPC;
    }

    public Set<Transaction> loadInputTransactions(PublicKey address) {
        Set<Transaction> recipientPC = new HashSet<>();
        for (Transaction trans : bChain){
            if (trans.getpKey_recipient() == address) {
               recipientPC.add(trans);
            }
        }
        return recipientPC;
    }

    public Set<Transaction> loadOutputTransactions(PublicKey address) {
        Set<Transaction> senderPC = new HashSet<>();
        for (Transaction trans : bChain){
            if (trans.getpKey_sender() == address) {
                senderPC.add(trans);
            }
        }
        return senderPC;
    }
}
