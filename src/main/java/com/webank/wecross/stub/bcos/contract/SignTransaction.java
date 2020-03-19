package com.webank.wecross.stub.bcos.contract;

import com.webank.wecross.stub.bcos.common.BCOSConstant;
import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.crypto.ExtendedRawTransaction;
import org.fisco.bcos.web3j.tx.ExtendedRawTransactionManager;
import org.fisco.bcos.web3j.utils.BlockLimit;

public class SignTransaction extends ExtendedRawTransactionManager {

    public static final BigInteger gasPrice = new BigInteger("300000000000");
    public static final BigInteger gasLimit = new BigInteger("300000000000");

    private BigInteger groupId;
    private BigInteger chainId;

    public SignTransaction(Credentials credentials, BigInteger groupId, BigInteger chainId) {
        super(null, credentials, groupId, chainId);
        this.groupId = groupId;
        this.chainId = chainId;
    }

    public SignTransaction(Credentials credentials) {
        this(
                credentials,
                BigInteger.valueOf(BCOSConstant.BCOS_DEFAULT_GROUP_ID),
                BigInteger.valueOf(BCOSConstant.BCOS_DEFAULT_CHAIN_ID));
    }

    /**
     * create and sign the transaction
     *
     * @param contractAddress
     * @param abiData
     * @return
     */
    public String sign(String contractAddress, String abiData, BigInteger blockNumber) {

        Random r = ThreadLocalRandom.current();
        BigInteger randomid = new BigInteger(250, r);
        BigInteger blockLimit = blockNumber.add(BigInteger.valueOf(BlockLimit.blockLimit));

        ExtendedRawTransaction rawTransaction =
                ExtendedRawTransaction.createTransaction(
                        randomid,
                        SignTransaction.gasPrice,
                        SignTransaction.gasLimit,
                        blockLimit,
                        contractAddress,
                        BigInteger.ZERO,
                        abiData,
                        getGroupId(),
                        getChainId(),
                        "");

        return sign(rawTransaction);
    }

    public BigInteger getGroupId() {
        return groupId;
    }

    public BigInteger getChainId() {
        return chainId;
    }
}
