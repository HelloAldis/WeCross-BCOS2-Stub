import static junit.framework.TestCase.assertEquals;

import com.webank.wecross.stub.bcos.common.BCOSConstant;
import com.webank.wecross.stub.bcos.contract.SignTransaction;
import com.webank.wecross.stub.bcos.contract.StubFunction;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.fisco.bcos.web3j.abi.FunctionEncoder;
import org.fisco.bcos.web3j.abi.datatypes.Function;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ExtendedRawTransaction;
import org.fisco.bcos.web3j.crypto.ExtendedTransactionDecoder;
import org.fisco.bcos.web3j.crypto.gm.GenCredential;
import org.junit.Test;

public class TransactionSignTest {

    @Test
    public void transactionSignTest() throws IOException {
        Credentials credentials = GenCredential.create();
        BigInteger blockNumber = BigInteger.valueOf(1111111);

        SignTransaction signTransaction = new SignTransaction(credentials);

        Random r = ThreadLocalRandom.current();
        BigInteger randomid = new BigInteger(250, r);

        String funcName = "testFuncName";
        List<String> params = Arrays.asList("aaa", "bbbb", "ccc");
        Function function = StubFunction.newFunction(funcName, params);
        String abiData = FunctionEncoder.encode(function);

        String to = "0xb3c223fc0bf6646959f254ac4e4a7e355b50a344";
        String extraData = "extraData";

        String sign = signTransaction.sign(to, abiData, blockNumber);
        ExtendedRawTransaction decodeExtendedRawTransaction =
                ExtendedTransactionDecoder.decode(sign);

        assertEquals(SignTransaction.gasPrice, decodeExtendedRawTransaction.getGasPrice());
        assertEquals(SignTransaction.gasLimit, decodeExtendedRawTransaction.getGasLimit());
        assertEquals(to, decodeExtendedRawTransaction.getTo());
        assertEquals(BigInteger.ZERO, decodeExtendedRawTransaction.getValue());
        assertEquals(abiData, "0x" + decodeExtendedRawTransaction.getData());
        assertEquals(
                BigInteger.valueOf(BCOSConstant.BCOS_DEFAULT_GROUP_ID),
                decodeExtendedRawTransaction.getGroupId());
    }
}
