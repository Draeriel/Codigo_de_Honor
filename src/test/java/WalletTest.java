import org.junit.Before;
import org.junit.Test;
import org.mvpigs.pigcoin.GenSig;
import org.mvpigs.pigcoin.Wallet;

import java.security.KeyPair;

import static org.junit.Assert.assertEquals;

public class WalletTest {
    private Wallet monedero = new Wallet();
    private Wallet monedero2 = new Wallet();
    private KeyPair pair = GenSig.generateKeyPair();

    @Before
    public void crearMonedero(){
        monedero = new Wallet();
        monedero2 = new Wallet();


    }

    @Test
    public void setSKSetAddressTest() {
        monedero.setSK(pair.getPrivate());
        monedero.setAddress(pair.getPublic());
        System.out.println("\n Direccion del monedero: \n" +  monedero.getAddress().hashCode());
    }

    @Test
    public void generateKeyPairTest() {
        monedero2.generateKeyPair();
        System.out.println("\n Direccion del monedero2: \n" +monedero2.getAddress().hashCode());
    }

    @Test
    public void toStringTest(){
        monedero.generateKeyPair();
        monedero2.generateKeyPair();
        System.out.println("Wallet_1: \n" + monedero.toString());
        System.out.println("Wallet_2: \n" + monedero2.toString());
    }


}
