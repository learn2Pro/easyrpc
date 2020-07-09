package org.learn2pro.codeplayground.rpc.core;

import org.learn2pro.codeplayground.rpc.core.ann.Gen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @NAME :org.learn2pro.codeplayground.rpc.core.SnowflakeGenerator
 * @AUTHOR :tderong
 * @DATE :2020/7/7
 */
@Gen("SNOW_FLAKE")
public class SnowflakeGenerator implements Generator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SnowflakeGenerator.class);
    /**
     * the sequence in same host
     */
    private long sequence;
    /**
     * the host worker id
     */
    private long workerId;
    /**
     * the host in data center id
     */
    private long datacenterId;
    /**
     * the ts in last generate id
     */
    private long lastTimestamp = -1L;
    /**
     *                  ts-segment(42)                  dcId(5)    wId(5)     sequence(12)
     *   0 00000000000000000000000000000000000000000     00000     00000     000000000000
     */
    private final long workerIdBits = 5L;
    private final long datacenterIdBits = 5L;
    // -1 ^ (-1 << workerIdBits)
    private final long maxWorkerId = ~(-1L << workerIdBits);
    // -1 ^ (-1 << datacenterIdBits)
    private final long maxDatacenterId = ~(-1L << datacenterIdBits);
    private final long sequenceBits = 12L;

    private final long workerIdShift = sequenceBits;
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    // -1 ^ (-1 << sequenceBits)
    private final long sequenceMask = ~(-1L << sequenceBits);
    private final long twepoch = 1288834974657L;

    public SnowflakeGenerator(final long sequence, final long workerId, final long datacenterId) {
        this.sequence = sequence;
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        checkInputParam(workerId, datacenterId);
    }

    public SnowflakeGenerator(final long workerId, final long datacenterId) {
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = 0;
        checkInputParam(workerId, datacenterId);
    }

    public SnowflakeGenerator() {
        this.sequence = 0;
        this.workerId = 0;
        this.datacenterId = 0;
        checkInputParam(workerId, datacenterId);
    }

    private void checkInputParam(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0",
                    maxWorkerId));
        }

        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0",
                    maxDatacenterId));
        }
    }

    @Override
    public String generateId() {
        return String.valueOf(nextId());
    }

    private long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            LOGGER.error("clock is moving backwards.  Rejecting requests until {}.", lastTimestamp);
            throw new RuntimeException(
                    "Clock moved backwards.  Refusing to generate id for " + (lastTimestamp - timestamp) +
                            " milliseconds");
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0;
        }

        lastTimestamp = timestamp;
        return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) | sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}
