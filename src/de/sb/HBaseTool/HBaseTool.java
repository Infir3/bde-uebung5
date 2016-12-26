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

public class HBaseTool {
	
	public static void main(String[] args) {
		
		Configuration conf = HBaseConfiguration.create();
		Connection conn;
		try {
			conn = ConnectionFactory.createConnection(conf);
			Admin admin = conn.getAdmin();
			HTableDescriptor tableDescriptor = new HTableDescriptor(TableName.valueOf("customer"));
			tableDescriptor.addFamily(new HColumnDescriptor("id"));
			tableDescriptor.addFamily(new HColumnDescriptor("name"));
			tableDescriptor.addFamily(new HColumnDescriptor("beers"));
			admin.createTable(tableDescriptor);
			
			 System.out.println("Table customer has been successfully created.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		

		
	}
	

}
