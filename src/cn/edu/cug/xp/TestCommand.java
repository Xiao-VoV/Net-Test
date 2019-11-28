/**
 * @author xp
 */

package cn.edu.cug.xp;

import java.io.BufferedReader;  
import java.io.InputStreamReader;
import java.util.ArrayList;

import redis.clients.jedis.Jedis;  
  


public class TestCommand {  
	private static Jedis jedis;

	public static /*void*/  ArrayList<String> exeCmd(String commandStr) {  
        BufferedReader br = null;  
        ArrayList<String> result = new ArrayList<String>();
        try {  
            Process p = Runtime.getRuntime().exec(commandStr);  
            br = new BufferedReader(new InputStreamReader(p.getInputStream()));  
            String line = null;  
            StringBuilder sb = new StringBuilder();  
	
			int i=0;
			while ((line = br.readLine()) != null && i<20 ) 
	           {  
	               sb.append(line + "\n"); 
	               result.add(line);
	               i++;
	               System.out.println(line);
	           }  
			//result.add("更多的不保存！！！");
			System.out.println("至多保存20条数据,更多的不保存！！！");
			//return result;
        //System.out.println(sb.toString());  
        } catch (Exception e) {  
            e.printStackTrace();  
        }   
        finally  
        {  
            if (br != null)  
            {  
                try {  
                    br.close();  
                } catch (Exception e) {  
                    e.printStackTrace();  
                }  
            }  
        }
		return result;  
    }  
    
    
    public static void RedisConnect( ArrayList<String> result) 
    {
    	System.out.println("-------------------------\n正在连接Redis\n-------------------------");
        jedis = new Jedis("localhost");
        
        for (int i = 0 ; i < result.size(); i++) 
        {
            //System.out.println(result.get(i));
        	jedis.set(i+"", result.get(i));     //存储数据
        }
        
        for (int i = 0 ; i < result.size(); i++) {
            //System.out.println(result.get(i));
        	System.out.println("redis存储的第"+i+""+"条数据为: "+ jedis.get(i+""));   //读取数据
        }
        
        
        
    }
  
    public static void main(String[] args) {  
    	
    	ArrayList<String> commandResult = new ArrayList<String>();
    	
        String commandStr1 = "ping -c 6 -4 -l 1  baidu.com "; //ping netstat traceroute ifconfig lsof [tcpdump] 
		/*
		 * -n count 发送指定的数据包数，默认发送四个。
		 * -4 使用IPv4
		 * -l size 指定发送的数据包的大小，默认发送的数据包大小为32byte。
		 */
        String commandStr2 = "netstat -a -t -l";  
		/*-a 显示所有选项，默认不显示listen相关
		-t （tcp）仅显示tcp相关
		-l 仅列出有在listen（监听）的服务状态*/
        String commandStr3 = "traceroute -m 10  -n  -p 6000 www.baidu.com";
		/*-m 设置检测数据包的最大存活数值TTL的大小。
		-n 直接使用IP地址而非主机名称。
		-p 设置UDP传输协议的通信端口。*/
        String commandStr4 = "ifconfig";
		/*
		 * -a 显示全部接口信息。
		 * -s 显示摘要信息（类似于 netstat -i）。
		 */
        String commandStr5 = "lsof -i:80"; 
        
        String commandStr6 = "sudo tcpdump -c 20 -i wlp15s0 -n";
		/*
		 * -c：指定要抓取的包数量。
		 * -i interface：指定tcpdump需要监听的接口。默认会抓取第一个网络接口
		 * -n：对地址以数字方式显式，否则显式为主机名，也就是说-n选项不做主机名解析。
		 */
        
        System.out.println("执行命令为：\n"+commandStr1+ "\n输出为:");
        commandResult = TestCommand.exeCmd(commandStr1);
        RedisConnect(commandResult);
    }  
}
