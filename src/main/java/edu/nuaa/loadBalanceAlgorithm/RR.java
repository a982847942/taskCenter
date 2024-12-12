package edu.nuaa.loadBalanceAlgorithm;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author brain
 * @version 1.0
 * @date 2024/10/5 19:23
 */
public class RR {
    private static AtomicInteger indexAtomic = new AtomicInteger(0);

    public static String getServer() {
        ArrayList<String> serverList = new ArrayList<>();
        Set<String> serverSet = ServerManager.serverMap.keySet();
        Iterator<String> iterator = serverSet.iterator();
        while (iterator.hasNext()) {
            String server = iterator.next();
            Integer weight = ServerManager.serverMap.get(server);
            for (int i = 0; i < weight; i++) {
                serverList.add(server);
            }
        }

        if (indexAtomic.get() >= serverList.size()) {
            indexAtomic.set(0);
        }
        String server = serverList.get(indexAtomic.getAndIncrement());
        return server;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            String server = getServer();
            System.out.println(server);
        }
    }
}
class ServerManager {
    public volatile static Map<String, Integer> serverMap = new TreeMap<>();

    static {
        serverMap.put("192.168.1.1", 1);
        serverMap.put("192.168.1.2", 2);
        serverMap.put("192.168.1.3", 3);
        serverMap.put("192.168.1.4", 4);
    }
}
