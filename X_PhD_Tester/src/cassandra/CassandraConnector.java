package cassandra;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.Session;

import static java.lang.System.out;

/**
 * Class used for connecting to Cassandra database.
 */
public class CassandraConnector
{
   /** Cassandra Cluster. */
   private Cluster cluster;

   /** Cassandra Session. */
   private Session session;

   /**
    * Connect to Cassandra Cluster specified by provided node IP
    * address and port number.
    *
    * @param node Cluster node IP address.
    * @param port Port of cluster host.
    */
   public void connect(String node, int port)
   {
	  node = "192.168.100.10";
	  port = 9042;
	  
      this.cluster = Cluster.builder().addContactPoint(node).withPort(port).build();
      final Metadata metadata = cluster.getMetadata();
      out.printf("Connected to cluster: %s\n", metadata.getClusterName());
      for (final Host host : metadata.getAllHosts())
      {
         out.printf("Datacenter: %s; Host: %s; Rack: %s\n",
            host.getDatacenter(), host.getAddress(), host.getRack());
      }
      session = cluster.connect();
   }

   /**
    * Provide my Session.
    *
    * @return My session.
    */
   public Session getSession()
   {
      return this.session;
   }

   /** Close cluster. */
   public void close()
   {
      cluster.close();
   }
   
   public static void main(final String[] args)
   {
      final CassandraConnector client = new CassandraConnector();
      //final String ipAddress = args.length > 0 ? args[0] : "localhost";
      //final int port = args.length > 1 ? Integer.parseInt(args[1]) : 9042;
      out.println("Connecting to IP Address ...");
      client.connect("192.168.100.10", 9142);
      
      String word = "Plum";

      for (int i = 0; i < 10; i++){
	      client.getSession().execute(
	    	      "INSERT INTO phd.peakouttest (word, count_num) VALUES (?, ?)",
	    	      word, i);
	      word = word + Integer.toString(i);
      }
      
      client.close();
   }
   
}