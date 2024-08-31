package io.city.hospital.metrics;

import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@InitializeMetrics(classes = MetricsConstants.class)
public final class MetricUtils {

    private static final Logger log = LoggerFactory.getLogger(MetricUtils.class);

    private MetricUtils() {
    }

    public static void incrementMetric(String metric) {

        try {
            var metricTypeTag = Tag.of("metric_type", "business");

            Metrics.counter(metric, List.of(metricTypeTag)).increment();
        } catch(Exception ex) {
            log.error(String.format("[Error] incrementing metric %s", metric), ex);
        }

    }
}
