import org.junit.Before;
import org.junit.Test;
import org.mvpigs.pigcoin.BlockChain;
import org.mvpigs.pigcoin.GenSig;
import org.mvpigs.pigcoin.Transaction;
import org.mvpigs.pigcoin.Wallet;

import java.security.KeyPair;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class BlockChainTest {
    private Transaction trans = new Transaction();
    private Wallet monedero = new Wallet();
    private Wallet monedero2 = new Wallet();
    private Wallet origin = new Wallet();
    private KeyPair pair = GenSig.generateKeyPair();
    private BlockChain bloque = new BlockChain();

    @Before
    public void crearBloqueTest(){
        monedero.generateKeyPair();
        monedero2.generateKeyPair();
        origin.generateKeyPair();

        trans = new Transaction("hash_1", "0", monedero.getAddress(), monedero2.getAddress(), 20, "a flying pig!");
        bloque.addOrigin(trans);
        trans = new Transaction("hash_2", "1", origin.getAddress(), monedero2.getAddress(), 10, "spam spam spam");
        bloque.addOrigin(trans);
        trans = new Transaction("hash_3", "hash_1", monedero.getAddress(), monedero2.getAddress(), 20, "a flying pig!");
        bloque.addOrigin(trans);
    }


    @Test
    public void summarizeTest(){
        bloque.summarize();
    }
}
