package de.sb.HBaseTool;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class Test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	    TableName tableName = TableName.valueOf("stock-prices");
	    
	    Configuration conf = HBaseConfiguration.create();
	    conf.set("hbase.zookeeper.property.clientPort", "2181");
	    conf.set("hbase.zookeeper.quorum", "sandbox.hortonworks.com");
	    conf.set("zookeeper.znode.parent", "/hbase-unsecure");
	    Connection conn = ConnectionFactory.createConnection(conf);
	    Admin admin = conn.getAdmin();
	    if (!admin.tableExists(tableName)) {
	        admin.createTable(new HTableDescriptor(tableName).addFamily(new HColumnDescriptor("cf")));
	    }
	 
	    Table table = conn.getTable(tableName);
	    Put p = new Put(Bytes.toBytes("AAPL10232015"));
	    p.addColumn(Bytes.toBytes("cf"), Bytes.toBytes("close"), Bytes.toBytes(119));
	    table.put(p);
	 
	    Result r = table.get(new Get(Bytes.toBytes("AAPL10232015")));
	    System.out.println(r);
	}

}
