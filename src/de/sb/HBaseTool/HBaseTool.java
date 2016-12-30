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
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseTool {

	public static void main(String[] args) throws IOException {

		Configuration conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.property.clientPort", "2181");
		conf.set("hbase.zookeeper.quorum", "sandbox.hortonworks.com");
		conf.set("zookeeper.znode.parent", "/hbase-unsecure");

		Connection conn = ConnectionFactory.createConnection(conf);
		Admin admin = conn.getAdmin();

		TableName tableName = TableName.valueOf("customer");

		// create table "customer" if it doesn't exist yet
		if (!admin.tableExists(tableName)) {
			HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
			tableDescriptor.addFamily(new HColumnDescriptor("personal"));
			tableDescriptor.addFamily(new HColumnDescriptor("order"));
			admin.createTable(tableDescriptor);
			System.out.println("Table " + tableName.getNameAsString() + " has been successfully created.");
		} else {
			System.out.println("Table " + tableName.getNameAsString() + " exists.");
		}

		if (admin.tableExists(tableName)) {

			// put some data into the table
			Table table = conn.getTable(tableName);
			Put put = new Put(Bytes.toBytes("row1")); // row id
			put.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("id"), Bytes.toBytes("00001"));
			put.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("name"), Bytes.toBytes("Hans Mustermann"));
			put.addColumn(Bytes.toBytes("order"), Bytes.toBytes("beer"), Bytes.toBytes("5"));
			table.put(put);

			// read the data
			Scan scan = new Scan();
			scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("id"));
			scan.addColumn(Bytes.toBytes("personal"), Bytes.toBytes("name"));
			scan.addColumn(Bytes.toBytes("order"), Bytes.toBytes("beer"));
			ResultScanner scanner = table.getScanner(scan);
			for (Result result : scanner) {
				System.out.println("Found row : " + result);
			}

			scanner.close();
			table.close();

		}

		conn.close();

	}

}
