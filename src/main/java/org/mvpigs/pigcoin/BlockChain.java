package org.mvpigs.pigcoin;

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
}
