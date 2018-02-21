package org.mvpigs.pigcoin;

import java.security.PublicKey;
import java.util.*;

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

    public void processTransactions(PublicKey pKey_sender, PublicKey pKey_recipient, Map consumedCoins, String message, byte[] signedTransaction){
        if (isSignatureValid(pKey_sender, message, signedTransaction)){
            if (isConsumedCoinValid(consumedCoins)){
                createTransaction(pKey_sender, pKey_recipient, consumedCoins,message, signedTransaction);
            }
        } else {
            System.out.println("Transacción no completada.");
        }
    }

    public boolean isSignatureValid(PublicKey pKey_sender, String message, byte[] signedTransaction){
        //return GenSig.verify(pKey_sender, message, signedTransaction);
        boolean mock = true;
        return mock;
    }

    public boolean isConsumedCoinValid(Map<String,Double> consumedCoins){
        for (Transaction trans : this.bChain){
            for (Map.Entry<String, Double> entrada : consumedCoins.entrySet()){
                if (entrada.getKey().equals(trans.getHash())){
                    return true;
                }
            }
        }
        return false;
    }

    public void createTransaction(PublicKey pKey_sender, PublicKey pKey_recipient, Map<String, Double> consumedCoins, String message, byte[] signedTransaction){
        for (Map.Entry<String, Double> consumed : consumedCoins.entrySet()) {
            String hash = "hash_" + (bChain.size() + 1);
            if (!consumed.getKey().toString().substring(0, 2).equals("CA")){
                Transaction trans = new Transaction(hash, consumed.getKey(), pKey_sender, pKey_recipient, consumed.getValue(), message);
                addOrigin(trans);
            } else {
                Transaction trans = new Transaction(hash, consumed.getKey(), pKey_sender, pKey_sender, consumed.getValue(), message);
                addOrigin(trans);
            }
        }
    }
}
