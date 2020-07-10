package weizhi.example.hiquartz;

import java.io.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @author liweizhi
 * @date 2020/6/18
 */
public class ProduceSql {

    static String[] clusterArr = {"cluster001", "cluster002"};
    static String[] bizArr = {"757761", "777777"};
    static String[] instanceArr = {"instance001", "instance002"};
    static String[] namespaceArr = {"ns-121", "ns-325"};
    static ZoneOffset zoneOffset = ZoneOffset.of("+8");


    static LocalDateTime start = LocalDateTime.of(2020, 6, 1, 0, 0, 0);
    static LocalDateTime end = LocalDateTime.of(2020, 7, 30, 0, 0, 0);
    /*static LocalDateTime start = now;
    static LocalDateTime end = now.plusDays(10);*/

    static int batchSize = 1024 * 1000;

    public static void main(String[] args) {
//        makeMetricSql();
        makeQueryCountSql();

//        write2file("/Users/liweizhi/Desktop/test.sql", "qqq");

//        String a = "{\"bizData\":[{\"code\":\"5de309bbb5394f229e59904fc5734170\",\"name\":\"business3\",\"parentCode\":null,\"tag\":\"\",\"description\":\"ci\",\"auth\":\"0100\"},{\"code\":\"bb7ee2171dce4517bd6a765fa5706f36\",\"name\":\"business2\",\"parentCode\":null,\"tag\":\"\",\"description\":\"ci\",\"auth\":\"1100\"},{\"code\":\"cc067dde4ada42679d13c35c820c6e92\",\"name\":\"business4\",\"parentCode\":null,\"tag\":\"\",\"description\":\"ci\",\"auth\":\"1111\"}],\"modelData\":[{\"code\":\"application\",\"name\":\"应用\",\"parentCode\":\"a8f1317fe5a9464681289ff333e8d6c4\",\"tag\":\"2\",\"description\":\"\",\"auth\":\"11\"},{\"code\":\"business\",\"name\":\"业务\",\"parentCode\":\"a8f1317fe5a9464681289ff333e8d6c4\",\"tag\":\"2\",\"description\":\"\",\"auth\":\"01\"},{\"code\":\"mysql-node\",\"name\":\"MySQL服务节点\",\"parentCode\":\"f08513ca97f84f94bca931f4ab781a7f\",\"tag\":\"2\",\"description\":\"\",\"auth\":\"11\"}]}";
    }

    static void makeQueryCountSql() {
        StringBuilder sb = new StringBuilder(batchSize);
        sb.append("insert into apm_data (_cw_raw_time,_cw_cluster,_cw_biz, ns_name,count)")
                .append(" values ");

        while (start.isBefore(end)) {
            for (String cluster : clusterArr) {
                for (String biz : bizArr) {
                    for (String ns : namespaceArr) {
                        long time = start.toEpochSecond(zoneOffset) * 1000;
                        appendQuertCount(sb, time, cluster, biz, ns, (int) (Math.random() * 100000));
                    }
                }
            }
            start = start.plusMinutes(1);
            if (start.isBefore(end)) {
                sb.append(",");
            }
        }

        try {
            FileWriter fw = new FileWriter("/Users/liweizhi/Desktop/qc.sql");
            fw.write(sb.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void write2file(String saveFile, String content) {
        File file = new File(saveFile);
        FileOutputStream fos = null;
        OutputStreamWriter osw = null;

        try {
            if (!file.exists()) {
                boolean hasFile = file.createNewFile();
                if (hasFile) {
                    System.out.println("file not exists, create new file");
                }
                fos = new FileOutputStream(file);
            } else {
                System.out.println("file exists");
                fos = new FileOutputStream(file, true);
            }

            osw = new OutputStreamWriter(fos, "utf-8");
            osw.write(content); //写入内容
//            osw.write("\r\n");  //换行
        } catch (Exception e) {
            e.printStackTrace();
        } finally {   //关闭流
            try {
                if (osw != null) {
                    osw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    static void makeMetricSql() {
        StringBuilder metricSb = new StringBuilder(batchSize);
        metricSb.append("insert into tappss_prometheus_capacity_t (_cw_raw_time,_cw_cluster,_cw_biz,instance,namespace,metric_key,_cw_metric_value_number)")
                .append(" values ");

        while (start.isBefore(end)) {
            for (String cluster : clusterArr) {
                for (String biz : bizArr) {
                    for (String instance : instanceArr) {
                        for (String namespace : namespaceArr) {
                            long time = start.toEpochSecond(zoneOffset) * 1000;
                            appendMetric(metricSb, "container_cpu_usage", randomValue(1, 32), time, cluster, biz, instance, namespace);
                            metricSb.append(",");
                            appendMetric(metricSb, "container_resource_requests_cpu", 32, time, cluster, biz, instance, namespace);
                            metricSb.append(",");
                            appendMetric(metricSb, "container_resource_limits_cpu", 40, time, cluster, biz, instance, namespace);
                            metricSb.append(",");
                            appendMetric(metricSb, "container_memory_rss", randomValue(1, 1024 * 1024), time, cluster, biz, instance, namespace);
                            metricSb.append(",");
                            appendMetric(metricSb, "container_resource_requests_memory", 1024 * 1024, time, cluster, biz, instance, namespace);
                            metricSb.append(",");
                            appendMetric(metricSb, "container_resource_limits_memory", 1024 * 1024 * 2, time, cluster, biz, instance, namespace);

                            metricSb.append(",");
                            appendMetric(metricSb, "machine_cpu_cores", 32, time, cluster, biz, instance, namespace);
                            metricSb.append(",");
                            appendMetric(metricSb, "node_cpu_used_ratio", Math.random(), time, cluster, biz, instance, namespace);
                            metricSb.append(",");
                            appendMetric(metricSb, "node_memory_MemTotal", 1024 * 1024, time, cluster, biz, instance, namespace);
                            metricSb.append(",");
                            appendMetric(metricSb, "node_memory_used", randomValue(1, 1024 * 1024), time, cluster, biz, instance, namespace);
                        }
                    }
                }
            }
            start = start.plusMinutes(1);
            if (start.isBefore(end)) {
                metricSb.append(",");
            }

            if (metricSb.length() >= batchSize) {
                write2file("/Users/liweizhi/Desktop/metric.sql", metricSb.toString());
                metricSb.delete(0, metricSb.length());
            }
        }

        write2file("/Users/liweizhi/Desktop/metric.sql", metricSb.toString());

        /*try {
            FileWriter fw = new FileWriter("/Users/liweizhi/Desktop/metric.sql");
            fw.write(metricSb.toString());
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private static void appendMetric(StringBuilder sb, String metric, Number value, long time, String cluster, String biz, String instance, String namespace) {
        sb.append("(")
                .append(time)
                .append(",'").append(cluster).append("'");
        sb.append(",'").append(biz).append("'");
        sb.append(",'").append(instance).append("'");
        sb.append(",'").append(namespace).append("'");
        sb.append(",'").append(metric).append("'");
        sb.append(",").append(value)
                .append(")");
    }

    private static void appendQuertCount(StringBuilder sb, long time, String cluster, String biz, String ns, int count) {
        sb.append("(")
                .append(time)
                .append(",'").append(cluster).append("'")
                .append(",'").append(biz).append("'");
        if (ns != null) {
            sb.append(",'").append(ns).append("'");
        }
        sb.append(",").append(count)
                .append(")");
    }

    static int randomValue(int from, int to) {
        return (int) (Math.random() * to + from + 1);
    }
}
