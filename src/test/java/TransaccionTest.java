import org.junit.Before;
import org.junit.Test;
import org.mvpigs.pigcoin.GenSig;
import org.mvpigs.pigcoin.Transaction;
import org.mvpigs.pigcoin.Wallet;

import java.security.KeyPair;

import static org.junit.Assert.assertEquals;

public class TransaccionTest {
    private Transaction trans = new Transaction();
    private Wallet monedero = new Wallet();
    private Wallet monedero2 = new Wallet();
    private KeyPair pair = GenSig.generateKeyPair();

    @Before
    public void creatTransaction(){
        monedero.generateKeyPair();
        monedero2.generateKeyPair();
        trans = new Transaction("hash_1", "0", monedero.getAddress(), monedero2.getAddress(), 20, "a flying pig!");
    }

    @Test
    public void toStringTest(){
        System.out.println(trans.toString());
    }
}

