package com.testhbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class Testhbase {
	
	
	 // ������̬����
    static Configuration conf = null;
    static {
        conf =  HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "centos");
    }

    /*
     * ������
     * 
     * @tableName ����
     * 
     * @family �����б�
     */
    public static void creatTable(String tableName, String[] family)
            throws Exception {
        HBaseAdmin admin = new HBaseAdmin(conf);
        HTableDescriptor Tdesc = new HTableDescriptor(TableName.valueOf(tableName));
        for (int i = 0; i < family.length; i++) {
            Tdesc.addFamily(new HColumnDescriptor(family[i]));
        }
        if (admin.tableExists(tableName)) {
            System.out.println("table "+tableName+" Exists!");
            System.exit(0);
        } else {
            admin.createTable(Tdesc);
            System.out.println("create table "+tableName+" Success!");
        }
    }


    
    /*
     * Ϊ���������
     * 
     * @rowKey rowKey
     * 
     * @tableName ����
     * 
     * @columnFamily ������
     * 
     * @columns ������
     * 
     * @values ��ֵ��
     */
    public static void addData(String rowKey, String tableName,String columnFamily,
    		String[] columns, String[] values)
    				throws IOException {
    	Put put = new Put(Bytes.toBytes(rowKey));// ����rowkey
    	HTable table = new HTable(conf, Bytes.toBytes(tableName));// HTabel�������¼��صĲ�������ɾ�Ĳ��//
    	// ��ȡ��
    	HColumnDescriptor[] columnFamilies = table.getTableDescriptor() // ��ȡ���е�����
    			.getColumnFamilies();
    	
    	for (int i = 0; i < columnFamilies.length; i++) {
    		String familyName = columnFamilies[i].getNameAsString(); // ��ȡ������
    		if (familyName.equals(columnFamily)) { // article����put����
    			for (int j = 0; j < columns.length; j++) {
    				put.add(Bytes.toBytes(familyName),
    						Bytes.toBytes(columns[j]), Bytes.toBytes(values[j]));
    			}
    		}
    	}
    	table.put(put);
    	System.out.println(tableName + " "+ columnFamily + " add data Success!");
    }

//
//    /*
//     * ������ѯhbase���rowkey��Χ
//     * 
//     * @tableName ����
//     */
    public static void getResultScann(String tableName, String start_rowkey,
            String stop_rowkey) throws IOException {
        Scan scan = new Scan();
        scan.setStartRow(Bytes.toBytes(start_rowkey));
        scan.setStopRow(Bytes.toBytes(stop_rowkey));
        ResultScanner rs = null;
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        try {
            rs = table.getScanner(scan);
            for (Result r : rs) {
                for (KeyValue kv : r.list()) {
//                    System.out.print("row:" + Bytes.toString(kv.getRow()));
                    System.out.print(Bytes.toString(kv.getFamily())+":"+Bytes.toString(kv.getQualifier()));
                    System.out.print("              ");
                    System.out
                            .print("value=" + Bytes.toString(kv.getValue())+"   ");
                    System.out.println("timestamp=" + kv.getTimestamp());
                }
                System.out
                .println("-------------------------------------------");
            }
        } finally {
            rs.close();
        }
    }

    
    /*
     * ��ѯ���е�ĳһ����
     * 
     * @tableName ����
     * 
     * @rowKey rowKey
     * 
     * @familyName ������
     */
    public static void getResultByColumnFamily(String tableName, String rowKey,
            String familyName) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Get get = new Get(Bytes.toBytes(rowKey));
        get.addFamily(Bytes.toBytes(familyName));
        Result result = table.get(get);
        for (KeyValue kv : result.list()) {
            System.out.print(Bytes.toString(kv.getFamily())+":"+Bytes.toString(kv.getQualifier()));
            System.out.print("              ");
            System.out
                    .print("value=" + Bytes.toString(kv.getValue())+"   ");
            System.out.println("timestamp=" + kv.getTimestamp());
        }
        System.out.println("-------------------------------------------");
    }
//
//    /*
//     * ɾ��ָ������
//     * 
//     * @tableName ����
//     * 
//     * @rowKey rowKey
//     * 
//     * @familyName ������
//     * 
//     * @columnName ����
//     */
    public static void deleteColumn(String tableName, String rowKey,
            String familyName, String columnName) throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Delete deleteColumn = new Delete(Bytes.toBytes(rowKey));
        deleteColumn.deleteColumns(Bytes.toBytes(familyName),
                Bytes.toBytes(columnName));
        table.delete(deleteColumn);
        System.out.println(familyName + ":" + columnName + "is deleted!");
    }
//
    /*
     * ɾ�����е���
     * 
     * @tableName ����
     * 
     * @rowKey rowKey
     */
    public static void deleteAllColumn(String tableName, String rowKey)
            throws IOException {
        HTable table = new HTable(conf, Bytes.toBytes(tableName));
        Delete deleteAll = new Delete(Bytes.toBytes(rowKey));
        table.delete(deleteAll);
        System.out.println(tableName  + " " + rowKey + " all columns are deleted!");
    }
//
    /*
     * ɾ����
     * 
     * @tableName ����
     */
    public static void deleteTable(String tableName) throws IOException {
    	HBaseAdmin admin = new HBaseAdmin(conf);
    	admin.disableTable(TableName.valueOf(tableName));
    	admin.deleteTable(TableName.valueOf(tableName));
        System.out.println("Table "+tableName + " is deleted!");
    }

    public static void main(String[] args) throws Exception {

        String tableName = "default:cards2";
        String[] family = { "tradeInfo", "customerInfo" };
        
        // ������
        creatTable(tableName, family);    

        // Ϊ���������
        String customerFamily = "customerInfo";
        String[] column1 = { "name", "idcardnum" };
        String[] value1 = { "tom", "123154564646" };
        String[] value2 = { "jerry", "89794654" };
        String[] value3 = { "lisi", "4564131" };
        
        addData("tom", tableName, customerFamily, column1, value1);//rowkey table colfamily cols values
        addData("jerry", tableName, customerFamily,column1, value2);
        addData("lisi", tableName, customerFamily,column1, value3);
        
        String tradeFamily="tradeInfo";
        String[] column2 = { "act", "amount", "score" };
        String[] value2_1 = {"withdraw","1000", "0" };
        String[] value2_2 = {"save","1000","10" };
        String[] value2_3 = {"save","10000","100" };
        
        addData("tom", tableName, tradeFamily, column2, value2_1);//rowkey table colfamily cols values
        addData("tom", tableName, tradeFamily, column2, value2_2);
        addData("tom", tableName, tradeFamily ,column2, value2_3);
        
        //������ѯtom������   
        String start_rowkey = "tom";
        String end_rowkey = "tom";
        getResultScann(tableName, start_rowkey, end_rowkey);
        
        //�޸�tom��trade���� ����dealer��
        getResultByColumnFamily(tableName, "tom", tradeFamily);//��ѯtom��trade����   
        addData("tom", tableName, tradeFamily,new String[]{"dealer"},new String[] {"clerk1"});
        getResultByColumnFamily(tableName, "tom", tradeFamily);//��ѯtom��trade����   
        
        //ɾ��tom��dealer��:
        deleteColumn(tableName, "tom", tradeFamily, "dealer");
        getResultByColumnFamily(tableName, "tom", tradeFamily);//��ѯtom��trade����   
        
      // ɾ��������
      deleteAllColumn("default:cards2", "tom");
      deleteAllColumn("default:cards2", "jerry");
      deleteAllColumn("default:cards2", "lisi");
      // ɾ����
      deleteTable(tableName); 
        

    }

}

//��ѯtom��trade:score���ݵĶ���汾
//getResultByVersion(tableName, "tom", "trade", "score");
//
//// ������ѯ
//getResultScann("blog2", "rowkey4", "rowkey5");
//// ����row key��Χ������ѯ
//getResultScann("blog2", "rowkey4", "rowkey5");
//
//// ��ѯĳһ�е�ֵ
//getResultByColumn("blog2", "rowkey1", "author", "name");
//
//// ������
//updateTable("blog2", "rowkey1", "author", "name", "bin");
//
//// ��ѯĳһ�е�ֵ
//getResultByColumn("blog2", "rowkey1", "author", "name");
//
//// ��ѯĳ�еĶ�汾
//getResultByVersion("blog2", "rowkey1", "author", "name");
//
//
//
//// ɾ����
//deleteTable("blog2");


//private static void deleteColumn(String string, String string2,
//	String string3, String string4) {
//// TODO Auto-generated method stub
//
//}
//
///*
//* ����rwokey��ѯ
//* 
//* @rowKey rowKey
//* 
//* @tableName ����
//*/
//public static Result getResult(String tableName, String rowKey)
//      throws IOException {
//  Get get = new Get(Bytes.toBytes(rowKey));
//  HTable table = new HTable(conf, Bytes.toBytes(tableName));// ��ȡ��
//  Result result = table.get(get);
//  for (KeyValue kv : result.list()) {
//      System.out.print("family:" + Bytes.toString(kv.getFamily()));
//      System.out
//              .print("  qualifier:" + Bytes.toString(kv.getQualifier()));
//      System.out.print("  value:" + Bytes.toString(kv.getValue()));
//      System.out.println("  Timestamp:" + kv.getTimestamp());
//  }
//  System.out.println("-------------------------------------------");
//  return result;
//}
//
///*
//* ������ѯhbase��
//* 
//* @tableName ����
//*/
//public static void getResultScann(String tableName) throws IOException {
//  Scan scan = new Scan();
//  ResultScanner rs = null;
//  HTable table = new HTable(conf, Bytes.toBytes(tableName));
//  try {
//      rs = table.getScanner(scan);
//      for (Result r : rs) {
//          for (KeyValue kv : r.list()) {
//              System.out.println("row:" + Bytes.toString(kv.getRow()));
//              System.out.println("family:"
//                      + Bytes.toString(kv.getFamily()));
//              System.out.println("qualifier:"
//                      + Bytes.toString(kv.getQualifier()));
//              System.out
//                      .println("value:" + Bytes.toString(kv.getValue()));
//              System.out.println("timestamp:" + kv.getTimestamp());
//              System.out
//                      .println("-------------------------------------------");
//          }
//      }
//  } finally {
//      rs.close();
//  }
//}

//
///*
//* ��ѯ���е�ĳһ��
//* 
//* @tableName ����
//* 
//* @rowKey rowKey
//*/
//public static void getResultByColumn(String tableName, String rowKey,
//      String familyName, String columnName) throws IOException {
//  HTable table = new HTable(conf, Bytes.toBytes(tableName));
//  Get get = new Get(Bytes.toBytes(rowKey));
//  get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName)); // ��ȡָ������������η���Ӧ����
//  Result result = table.get(get);
//  for (KeyValue kv : result.list()) {
//      System.out.println("family:" + Bytes.toString(kv.getFamily()));
//      System.out
//              .println("qualifier:" + Bytes.toString(kv.getQualifier()));
//      System.out.println("value:" + Bytes.toString(kv.getValue()));
//      System.out.println("Timestamp:" + kv.getTimestamp());
//      System.out.println("-------------------------------------------");
//  }
//}
//
///*
//* ���±��е�ĳһ��
//* 
//* @tableName ����
//* 
//* @rowKey rowKey
//* 
//* @familyName ������
//* 
//* @columnName ����
//* 
//* @value ���º��ֵ
//*/
//public static void updateTable(String tableName, String rowKey,
//      String familyName, String columnName, String value)
//      throws IOException {
//  HTable table = new HTable(conf, Bytes.toBytes(tableName));
//  Put put = new Put(Bytes.toBytes(rowKey));
//  put.add(Bytes.toBytes(familyName), Bytes.toBytes(columnName),
//          Bytes.toBytes(value));
//  table.put(put);
//  System.out.println("update table Success!");
//}
//
///*
//* ��ѯĳ�����ݵĶ���汾
//* 
//* @tableName ����
//* 
//* @rowKey rowKey
//* 
//* @familyName ������
//* 
//* @columnName ����
//*/
//public static void getResultByVersion(String tableName, String rowKey,
//      String familyName, String columnName) throws IOException {
//  HTable table = new HTable(conf, Bytes.toBytes(tableName));
//  Get get = new Get(Bytes.toBytes(rowKey));
//  get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName));
//  get.setMaxVersions(5);
//  Result result = table.get(get);
//  for (KeyValue kv : result.list()) {
//      System.out.println("family:" + Bytes.toString(kv.getFamily()));
//      System.out
//              .println("qualifier:" + Bytes.toString(kv.getQualifier()));
//      System.out.println("value:" + Bytes.toString(kv.getValue()));
//      System.out.println("Timestamp:" + kv.getTimestamp());
//      System.out.println("-------------------------------------------");
//  }
/*
* List<?> results = table.get(get).list(); Iterator<?> it =
* results.iterator(); while (it.hasNext()) {
* System.out.println(it.next().toString()); }
*/
//}

/*
 * Ϊ��������ݣ��ʺ�֪���ж�������Ĺ̶���
 * 
 * @rowKey rowKey
 * 
 * @tableName ����
 * 
 * @column1 ��һ�������б�
 * 
 * @value1 ��һ���е�ֵ���б�
 * 
 * @column2 �ڶ��������б�
 * 
 * @value2 �ڶ����е�ֵ���б�
 */
//public static void addData(String rowKey, String tableName,
//        String[] column1, String[] value1, String[] column2, String[] value2)
//        throws IOException {
//    Put put = new Put(Bytes.toBytes(rowKey));// ����rowkey
//    HTable table = new HTable(conf, Bytes.toBytes(tableName));// HTabel�������¼��صĲ�������ɾ�Ĳ��//
//                                                                // ��ȡ��
//    HColumnDescriptor[] columnFamilies = table.getTableDescriptor() // ��ȡ���е�����
//            .getColumnFamilies();
//
//    for (int i = 0; i < columnFamilies.length; i++) {
//        String familyName = columnFamilies[i].getNameAsString(); // ��ȡ������
//        if (familyName.equals("article")) { // article����put����
//            for (int j = 0; j < column1.length; j++) {
//                put.add(Bytes.toBytes(familyName),
//                        Bytes.toBytes(column1[j]), Bytes.toBytes(value1[j]));
//            }
//        }
//        if (familyName.equals("author")) { // author����put����
//            for (int j = 0; j < column2.length; j++) {
//                put.add(Bytes.toBytes(familyName),
//                        Bytes.toBytes(column2[j]), Bytes.toBytes(value2[j]));
//            }
//        }
//    }
//    table.put(put);
//    System.out.println("add data Success!");
//}

//        String[] value1 = {
//                "Head First HBase",
//                "HBase is the Hadoop database. Use it when you need random, realtime read/write access to your Big Data.",
//                "Hadoop,HBase,NoSQL" };