import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.util.Scanner;
import java.net.Socket;
public class OpenPortsChecker {
    public static void main(String[] args) {
        // السكانر
        Scanner scanner = new Scanner(System.in);
        // إختيار الطور
        System.out.println("اختار 1- للبحث اليدوي 2- للبحث التلقائي");
        int choice = scanner.nextInt();
        scanner.nextLine();
        // متغيرات النطاق العام
        String DnsOrIp = "";
        int port = 0;

        if (choice == 1) {
            System.out.println("أكتب الآي بي أو الدي إن إس ");
            DnsOrIp = scanner.nextLine();
            System.out.println("أكتب المنفذ ");
            port = scanner.nextInt();
            try {
                Socket socket = new Socket(DnsOrIp, port);
                System.out.println("مفتوح");
                socket.close();
            } catch (IOException e) {
                System.out.println("مغلق");
            }
        } else if (choice == 2) {
            System.out.println("أكتب الآي بي أو الدي إن إس ");
            DnsOrIp = scanner.nextLine();
            // الخيوط لجعل عملية البحث سريعة
            ExecutorService executor = Executors.newFixedThreadPool(50);
            //لإستخدام الخيوط يجب التأكد من أن المتغير نهائي
            // الخريطة لربط المنفذ المفتوح بمعناه
            Map<Integer, String> commonPorts = Map.ofEntries(
                    Map.entry(20, "FTP-Data"),
                    Map.entry(21, "FTP"),
                    Map.entry(22, "SSH"),
                    Map.entry(23, "Telnet"),
                    Map.entry(25, "SMTP"),
                    Map.entry(53, "DNS"),
                    Map.entry(69, "TFTP"),
                    Map.entry(80, "HTTP"),
                    Map.entry(110, "POP3"),
                    Map.entry(123, "NTP"),
                    Map.entry(135, "RPC"),
                    Map.entry(137, "NetBIOS-NS"),
                    Map.entry(138, "NetBIOS-DGM"),
                    Map.entry(139, "NetBIOS-SSN"),
                    Map.entry(143, "IMAP"),
                    Map.entry(161, "SNMP"),
                    Map.entry(389, "LDAP"),
                    Map.entry(443, "HTTPS"),
                    Map.entry(445, "SMB"),
                    Map.entry(636, "LDAPS"),
                    Map.entry(989, "FTPS-Data"),
                    Map.entry(990, "FTPS"),
                    Map.entry(1433, "MSSQL"),
                    Map.entry(1521, "OracleDB"),
                    Map.entry(2049, "NFS"),
                    Map.entry(2082, "cPanel"),
                    Map.entry(2083, "cPanel-SSL"),
                    Map.entry(2181, "Zookeeper"),
                    Map.entry(3000, "Dev-Web"),
                    Map.entry(3306, "MySQL"),
                    Map.entry(3389, "RDP"),
                    Map.entry(4444, "Metasploit"),
                    Map.entry(5000, "Flask/Dev"),
                    Map.entry(5432, "PostgreSQL"),
                    Map.entry(5601, "Kibana"),
                    Map.entry(6379, "Redis"),
                    Map.entry(6667, "IRC"),
                    Map.entry(7001, "WebLogic"),
                    Map.entry(8000, "Python-HTTP"),
                    Map.entry(8008, "HTTP-Alt"),
                    Map.entry(8080, "HTTP-Proxy"),
                    Map.entry(8081, "HTTP-Alt2"),
                    Map.entry(8443, "HTTPS-Alt"),
                    Map.entry(9000, "SonarQube"),
                    Map.entry(9090, "Web-Admin"),
                    Map.entry(9200, "Elasticsearch"),
                    Map.entry(27017, "MongoDB")
            );
            for (port = 1; port < 65535; port++) {
               final int finalPort = port;
               final String finalDns = DnsOrIp;
               // لبدأ استخدامهم
                executor.submit(() -> {
                    System.out.println("\u001B[33m" + "Scanning " + finalPort);
                    // التحقق من المنفذ
                    try {
                        Socket socket = new Socket();
                        socket.connect(new java.net.InetSocketAddress(finalDns, finalPort), 500);
                        socket.close();
                        String service = commonPorts.getOrDefault(finalPort, "Unknown");
                        System.out.println("\u001B[32m" + "Port " + finalPort + " is open (" + service + ")");

                    } catch (IOException e) {

                    }
                });

            }
            // لتحرير الخيوط
            executor.shutdown();
        }
    }
}


