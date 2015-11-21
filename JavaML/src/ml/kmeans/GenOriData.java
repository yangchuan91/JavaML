package ml.kmeans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * 用来产生原始数据集合，以方便测试聚类算法
 * @author yangchuan
 *
 */
public class GenOriData {
	
	public static void main(String[] args) {
		writeDataSet("E:/player.txt",5000,50);
		//System.out.println(getOriPlaysData("E:/player.txt"));
	}
	/**
	 * 用来读取文件中的数据，构造原始数据
	 * @param source
	 * @return
	 */
	public static List<Point> getOriPlaysData(String source){
		List<Point> players=new ArrayList<Point>();
		List<String> listStr=readFile(source);
		for(String str:listStr){
			if(str!=null){
				String[] playersinfo=str.split(",");
				if(playersinfo.length==2){
					Point player=new Point(Integer.parseInt(playersinfo[0]),Integer.parseInt(playersinfo[1]));
					players.add(player);
				}
			}
		}
		return players;
	}
	
	public static List<String> readFile(String source){
		List<String> listStr=new ArrayList<String>();
		FileReader in=null;
		BufferedReader br=null;
		try {
			in=new FileReader(source);
			br=new BufferedReader(in);
			String line=null;
			while ((line=br.readLine())!=null) {
				listStr.add(line);
			}
		} catch (FileNotFoundException e) {
			System.out.println("文件不存在");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("读取文件异常");
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return listStr;
	}
	/**
	 * 随机向文件中写入数据集
	 * @param path  表示文件的路径
	 * @param num   num表示向文件中写入的行数
	 * @param range 表示随机写入文件的值的最大范围
	 */
	public static void writeDataSet(String path,int num,int range){
		FileWriter out=null;
		BufferedWriter bw=null;
		try {
			out = new FileWriter(path);
			bw=new BufferedWriter(out);
			int i=0;
			while (i<num) {
				String line=(int)(Math.random()*range)+","+(int)(Math.random()*range);
				System.out.println("line中的数据为"+line);
				bw.write(line);
				bw.newLine();
				i++;
			}
			bw.flush();
		} catch (IOException e) {
			System.out.println("写入文件异常");
			e.printStackTrace();
		}finally{
			if(out!=null){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		 
	}

}
