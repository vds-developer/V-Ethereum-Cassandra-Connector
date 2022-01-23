package com.vconnector.cassandra.models;

import java.util.List;

public class BlockData {
    // time in milliseconds since epoch
    long timestamp;
    long baseFeePerGas;
    long difficulty;
    String extraData;
    long gasLimit;
    long gasUsed;
    String hash;
    String logsNloom;
    String miner;
    String mixHash;
    String nonce;
    String parentHash;
    String receiptsRoot;
    String sha3Uncles;
    long size;
    String stateRoot;
    long totalDifficulty;
    List<String> transactions;
    String transactionRoot;
    List<String> uncles;

}
