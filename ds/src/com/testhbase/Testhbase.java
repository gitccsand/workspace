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
	
	
	 // 声明静态配置
    static Configuration conf = null;
    static {
        conf =  HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "centos");
    }

    /*
     * 创建表
     * 
     * @tableName 表名
     * 
     * @family 列族列表
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
     * 为表添加数据
     * 
     * @rowKey rowKey
     * 
     * @tableName 表名
     * 
     * @columnFamily 列族名
     * 
     * @columns 列名表
     * 
     * @values 列值表
     */
    public static void addData(String rowKey, String tableName,String columnFamily,
    		String[] columns, String[] values)
    				throws IOException {
    	Put put = new Put(Bytes.toBytes(rowKey));// 设置rowkey
    	HTable table = new HTable(conf, Bytes.toBytes(tableName));// HTabel负责跟记录相关的操作如增删改查等//
    	// 获取表
    	HColumnDescriptor[] columnFamilies = table.getTableDescriptor() // 获取所有的列族
    			.getColumnFamilies();
    	
    	for (int i = 0; i < columnFamilies.length; i++) {
    		String familyName = columnFamilies[i].getNameAsString(); // 获取列族名
    		if (familyName.equals(columnFamily)) { // article列族put数据
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
//     * 遍历查询hbase表的rowkey范围
//     * 
//     * @tableName 表名
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
     * 查询表中的某一列族
     * 
     * @tableName 表名
     * 
     * @rowKey rowKey
     * 
     * @familyName 列族名
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
//     * 删除指定的列
//     * 
//     * @tableName 表名
//     * 
//     * @rowKey rowKey
//     * 
//     * @familyName 列族名
//     * 
//     * @columnName 列名
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
     * 删除所有的列
     * 
     * @tableName 表名
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
     * 删除表
     * 
     * @tableName 表名
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
        
        // 创建表
        creatTable(tableName, family);    

        // 为表添加数据
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
        
        //遍历查询tom的数据   
        String start_rowkey = "tom";
        String end_rowkey = "tom";
        getResultScann(tableName, start_rowkey, end_rowkey);
        
        //修改tom的trade数据 增加dealer列
        getResultByColumnFamily(tableName, "tom", tradeFamily);//查询tom的trade数据   
        addData("tom", tableName, tradeFamily,new String[]{"dealer"},new String[] {"clerk1"});
        getResultByColumnFamily(tableName, "tom", tradeFamily);//查询tom的trade数据   
        
        //删除tom的dealer列:
        deleteColumn(tableName, "tom", tradeFamily, "dealer");
        getResultByColumnFamily(tableName, "tom", tradeFamily);//查询tom的trade数据   
        
      // 删除所有列
      deleteAllColumn("default:cards2", "tom");
      deleteAllColumn("default:cards2", "jerry");
      deleteAllColumn("default:cards2", "lisi");
      // 删除表
      deleteTable(tableName); 
        

    }

}

//查询tom的trade:score数据的多个版本
//getResultByVersion(tableName, "tom", "trade", "score");
//
//// 遍历查询
//getResultScann("blog2", "rowkey4", "rowkey5");
//// 根据row key范围遍历查询
//getResultScann("blog2", "rowkey4", "rowkey5");
//
//// 查询某一列的值
//getResultByColumn("blog2", "rowkey1", "author", "name");
//
//// 更新列
//updateTable("blog2", "rowkey1", "author", "name", "bin");
//
//// 查询某一列的值
//getResultByColumn("blog2", "rowkey1", "author", "name");
//
//// 查询某列的多版本
//getResultByVersion("blog2", "rowkey1", "author", "name");
//
//
//
//// 删除表
//deleteTable("blog2");


//private static void deleteColumn(String string, String string2,
//	String string3, String string4) {
//// TODO Auto-generated method stub
//
//}
//
///*
//* 根据rwokey查询
//* 
//* @rowKey rowKey
//* 
//* @tableName 表名
//*/
//public static Result getResult(String tableName, String rowKey)
//      throws IOException {
//  Get get = new Get(Bytes.toBytes(rowKey));
//  HTable table = new HTable(conf, Bytes.toBytes(tableName));// 获取表
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
//* 遍历查询hbase表
//* 
//* @tableName 表名
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
//* 查询表中的某一列
//* 
//* @tableName 表名
//* 
//* @rowKey rowKey
//*/
//public static void getResultByColumn(String tableName, String rowKey,
//      String familyName, String columnName) throws IOException {
//  HTable table = new HTable(conf, Bytes.toBytes(tableName));
//  Get get = new Get(Bytes.toBytes(rowKey));
//  get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName)); // 获取指定列族和列修饰符对应的列
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
//* 更新表中的某一列
//* 
//* @tableName 表名
//* 
//* @rowKey rowKey
//* 
//* @familyName 列族名
//* 
//* @columnName 列名
//* 
//* @value 更新后的值
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
//* 查询某列数据的多个版本
//* 
//* @tableName 表名
//* 
//* @rowKey rowKey
//* 
//* @familyName 列族名
//* 
//* @columnName 列名
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
 * 为表添加数据（适合知道有多少列族的固定表）
 * 
 * @rowKey rowKey
 * 
 * @tableName 表名
 * 
 * @column1 第一个列族列表
 * 
 * @value1 第一个列的值的列表
 * 
 * @column2 第二个列族列表
 * 
 * @value2 第二个列的值的列表
 */
//public static void addData(String rowKey, String tableName,
//        String[] column1, String[] value1, String[] column2, String[] value2)
//        throws IOException {
//    Put put = new Put(Bytes.toBytes(rowKey));// 设置rowkey
//    HTable table = new HTable(conf, Bytes.toBytes(tableName));// HTabel负责跟记录相关的操作如增删改查等//
//                                                                // 获取表
//    HColumnDescriptor[] columnFamilies = table.getTableDescriptor() // 获取所有的列族
//            .getColumnFamilies();
//
//    for (int i = 0; i < columnFamilies.length; i++) {
//        String familyName = columnFamilies[i].getNameAsString(); // 获取列族名
//        if (familyName.equals("article")) { // article列族put数据
//            for (int j = 0; j < column1.length; j++) {
//                put.add(Bytes.toBytes(familyName),
//                        Bytes.toBytes(column1[j]), Bytes.toBytes(value1[j]));
//            }
//        }
//        if (familyName.equals("author")) { // author列族put数据
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