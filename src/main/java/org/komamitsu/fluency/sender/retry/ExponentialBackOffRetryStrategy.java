package org.komamitsu.fluency.sender.retry;

public class ExponentialBackOffRetryStrategy
    extends RetryStrategy<ExponentialBackOffRetryStrategy.Config>
{
    private ExponentialBackOffRetryStrategy(Config config)
    {
        super(config);
    }

    @Override
    public long getNextIntervalMillis(int retryCount)
    {
        long interval = config.getBaseIntervalMillis() * ((int) Math.pow(2.0, (double) retryCount));
        if (interval > config.getMaxIntervalMillis()) {
            return config.getMaxIntervalMillis();
        }
        return interval;
    }

    public static class Config extends RetryStrategy.Config<ExponentialBackOffRetryStrategy, Config>
    {
        private long baseIntervalMillis = 400;
        private long maxIntervalMillis = 30 * 1000;

        public long getBaseIntervalMillis()
        {
            return baseIntervalMillis;
        }

        public Config setBaseIntervalMillis(long baseIntervalMillis)
        {
            this.baseIntervalMillis = baseIntervalMillis;
            return this;
        }

        public long getMaxIntervalMillis()
        {
            return maxIntervalMillis;
        }

        public Config setMaxIntervalMillis(long maxIntervalMillis)
        {
            this.maxIntervalMillis = maxIntervalMillis;
            return this;
        }

        @Override
        public ExponentialBackOffRetryStrategy createInstance()
        {
            return new ExponentialBackOffRetryStrategy(this);
        }
    }
}
